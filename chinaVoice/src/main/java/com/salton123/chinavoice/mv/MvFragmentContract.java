package com.salton123.chinavoice.mv;

import com.salton123.chinavoice.model.domain.AreaBean;
import com.salton123.chinavoice.mvp.presenter.BasePresenter;
import com.salton123.chinavoice.mvp.view.BaseView;

import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 9:37
 * Time: 9:37
 * Description:
 */
public interface MvFragmentContract {

    interface View extends BaseView{
        void setData(List<AreaBean> areaBeanArrayList);
        void setError(String msg);
    }

    interface Presenter extends BasePresenter<MvFragmentContract.View>{
        void getData();
    }

}
