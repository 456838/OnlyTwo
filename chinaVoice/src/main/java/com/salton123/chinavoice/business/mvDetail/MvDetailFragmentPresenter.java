package com.salton123.chinavoice.business.mvDetail;

import com.salton123.chinavoice.model.api.ChinaVoiceApi;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.chinavoice.mvp.presenter.RxPresenter;
import com.salton123.util.LogUtils;

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
public class MvDetailFragmentPresenter extends RxPresenter<MvDetailFragmentContract.View> implements MvDetailFragmentContract.Presenter {

    @Override
    public void getData(int videoId) {
        Subscription subscription = ChinaVoiceApi.GetMusicApiService().getRelativeVideoListUrl(ChinaVoiceApi.deviceinfo, videoId).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MVDetailBean>() {
                    @Override
                    public void call(MVDetailBean bean) {
                        if (bean != null) {
                            mView.startPlay(bean);
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
