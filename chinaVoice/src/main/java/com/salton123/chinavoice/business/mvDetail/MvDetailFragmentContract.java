package com.salton123.chinavoice.business.mvDetail;

import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.chinavoice.mvp.presenter.BasePresenter;
import com.salton123.chinavoice.mvp.view.BaseView;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:58
 * Time: 14:58
 * Description:
 */
public interface MvDetailFragmentContract {
    interface View extends BaseView {
        void startPlay(MVDetailBean bean);

        void setError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(int videoId);
    }

}
