package com.salton123.chinavoice.business.homePage;

import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.chinavoice.mvp.presenter.BasePresenter;
import com.salton123.chinavoice.mvp.view.BaseView;

import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:58
 * Time: 14:58
 * Description:
 */
public interface HomePageFragmentContract {
    interface View extends BaseView {
        void setData(List<VideoBean> dataList);
        void setError(String msg);
    }

    interface Presenter extends BasePresenter<View> {
        public void getData(int offset, int size);
    }

}
