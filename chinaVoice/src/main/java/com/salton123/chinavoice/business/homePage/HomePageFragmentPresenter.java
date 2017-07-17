package com.salton123.chinavoice.business.homePage;

import com.salton123.chinavoice.model.api.ChinaVoiceApi;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.chinavoice.mvp.presenter.RxPresenter;
import com.salton123.util.LogUtils;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:58
 * Time: 14:58
 * Description:
 */
public class HomePageFragmentPresenter extends RxPresenter<HomePageFragmentContract.View> implements HomePageFragmentContract.Presenter {

    @Override
    public void getData(int offset, int size) {
        Subscription subscription = ChinaVoiceApi.GetMusicApiService().getMainPageUrl(ChinaVoiceApi.deviceinfo, offset,size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<VideoBean>>() {
                    @Override
                    public void call(List<VideoBean> bean) {
                        if (bean != null) {
                            mView.setData(bean);
                        } else {
                            mView.setError("网络错误，请重试");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mView.setError(throwable.getLocalizedMessage());
                        LogUtils.e(throwable.getMessage());
                    }
                });
        addSubscrebe(subscription);
    }
}
