package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.mv.MvFragmentContract;
import com.salton123.chinavoice.model.domain.AreaBean;
import com.salton123.chinavoice.mv.MvFragmentPresenter;
import com.salton123.chinavoice.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.chinavoice.view.adapter.MVViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/14 21:58
 * Time: 21:58
 * Description:
 */
public class MvFragment extends BaseSupportPresenterFragment<MvFragmentPresenter> implements MvFragmentContract.View {
    TabLayout tabLayout;
    ViewPager mvPager;

    private MVViewPagerAdapter pagerAdapter;
    ArrayList<MvItemFragment> fragments = new ArrayList<>();

    @Override
    public int GetLayout() {
        return R.layout.mv_page_fragment;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new MvFragmentPresenter();
    }

    @Override
    public void InitViewAndData() {
        mvPager = f(R.id.mv_pager);
        tabLayout = f(R.id.tabLayout);
        mPresenter.getData();
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void setData(List<AreaBean> list) {
        for (AreaBean area :list) fragments.add(BaseSupportFragment.newInstance(MvItemFragment.class, area));
        initViewPager(fragments, list);
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(getActivity(), "获取数据失败:" + msg, Toast.LENGTH_SHORT).show();
    }


    private void initViewPager(ArrayList<MvItemFragment> fragments, List<AreaBean> areaBeanArrayList) {
        pagerAdapter = new MVViewPagerAdapter(getFragmentManager(), fragments, areaBeanArrayList);
        mvPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mvPager);
    }
}
