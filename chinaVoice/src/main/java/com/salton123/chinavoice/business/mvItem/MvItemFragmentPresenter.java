package com.salton123.chinavoice.business.mvItem;

import com.salton123.chinavoice.model.api.ChinaVoiceApi;
import com.salton123.chinavoice.model.domain.MVListBean;
import com.salton123.chinavoice.mvp.presenter.RxPresenter;
import com.salton123.util.LogUtils;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 10:32
 * Time: 10:32
 * Description:
 */
public class MvItemFragmentPresenter extends RxPresenter<MvItemFragmentContract.View> implements MvItemFragmentContract.Presenter {
//    private MvItemFragmentContract.View mvView;

//    public MvItemFragmentPresenter(MvItemFragmentContract.View mvView) {
//        this.mvView = mvView;
//    }

    @Override
    public void getData(int offset, int size, String areaCode) {
        Subscription subscription = ChinaVoiceApi.GetMusicApiService().getMVListUrl(ChinaVoiceApi.deviceinfo, areaCode, offset, size).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MVListBean>() {
                    @Override
                    public void call(MVListBean bean) {
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
