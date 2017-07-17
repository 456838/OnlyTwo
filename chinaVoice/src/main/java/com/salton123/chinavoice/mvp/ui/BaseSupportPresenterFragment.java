package com.salton123.chinavoice.mvp.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.mvp.presenter.BasePresenter;
import com.salton123.chinavoice.mvp.view.BaseView;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/12 15:22
 * Time: 15:22
 * Description:
 */
public abstract class BaseSupportPresenterFragment<T extends BasePresenter> extends BaseSupportFragment implements BaseView {

    protected T mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) mPresenter.detachView();
    }
}
