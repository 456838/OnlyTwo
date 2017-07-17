package com.salton123.chinavoice.mv;

import com.salton123.chinavoice.model.api.ChinaVoiceApi;
import com.salton123.chinavoice.model.domain.AreaBean;
import com.salton123.chinavoice.mvp.presenter.RxPresenter;
import com.salton123.util.LogUtils;

import java.util.List;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 9:38
 * Time: 9:38
 * Description:
 */
public class MvFragmentPresenter extends RxPresenter<MvFragmentContract.View> implements MvFragmentContract.Presenter {

//    private MvFragmentContract.View mvView;

//    public MvFragmentPresenter(MvFragmentContract.View mvView) {
//        this.mvView = mvView;
//    }

    @Override
    public void getData() {
        Subscription subscription = ChinaVoiceApi.GetMusicApiService().getAreaList(ChinaVoiceApi.deviceinfo).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AreaBean>>() {
                    @Override
                    public void call(List<AreaBean> bean) {
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
