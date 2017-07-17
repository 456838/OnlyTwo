package com.salton123.chinavoice.business.mvItem;

import com.salton123.chinavoice.model.domain.MVListBean;
import com.salton123.chinavoice.mvp.presenter.BasePresenter;
import com.salton123.chinavoice.mvp.view.BaseView;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 10:26
 * Time: 10:26
 * Description:
 */
public interface MvItemFragmentContract {
    interface View extends BaseView {
        void setData(MVListBean bean);
        void setError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        void getData(int offset, final int size,String areaCode);
    }
}
