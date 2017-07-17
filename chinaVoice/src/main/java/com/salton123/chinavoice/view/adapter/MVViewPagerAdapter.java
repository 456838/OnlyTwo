package com.salton123.chinavoice.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.salton123.chinavoice.model.domain.AreaBean;

import java.util.ArrayList;
import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 9:34
 * Time: 9:34
 * Description:
 */
public class MVViewPagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<Fragment> fragments=new ArrayList<>();
    ArrayList<AreaBean> areaBeanArrayList = new ArrayList<>();

    public MVViewPagerAdapter(FragmentManager fm, ArrayList<? extends Fragment> fragments, List<AreaBean> areaBeanArrayList) {
        super(fm);
        this.fragments.addAll(fragments);
        this.areaBeanArrayList.addAll(areaBeanArrayList);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return areaBeanArrayList.get(position).getName();
    }
}
