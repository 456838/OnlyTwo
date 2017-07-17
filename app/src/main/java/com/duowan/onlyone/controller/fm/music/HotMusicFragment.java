package com.duowan.onlyone.controller.fm.music;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.duowan.onlyone.R;
import com.duowan.onlyone.model.api.OnlyOneApi;
import com.duowan.onlyone.model.entity.yinyuetai.AreaBean;
import com.duowan.onlyone.util.TabLayoutUtil;
import com.duowan.onlyone.view.adapter.BaseFragmentAdapter;
import com.orhanobut.logger.Logger;
import com.salton123.base.BaseSupportFragment;
import com.salton123.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/10 10:14
 * Time: 10:14
 * Description:
 */
public class HotMusicFragment extends BaseSupportFragment {

    ViewPager vp_hot_music;
    TabLayout tab_hot_music;
    BaseFragmentAdapter adapter;

    @Override
    public int GetLayout() {
        return R.layout.fm_hot_music;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        vp_hot_music = f(R.id.vp_hot_music);
        tab_hot_music = f(R.id.tab_hot_music);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        initTabItemData();
    }

    private void initTabItemData() {
        Observable<List<AreaBean>> observable =   OnlyOneApi.GetMusicApiService().getAreaList(OnlyOneApi.deviceinfo);
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<AreaBean>>() {
                    @Override
                    public void call(List<AreaBean> areaBeen) {
                        if (areaBeen != null) {
                            initViewPager(areaBeen);
                        } else {
                            Logger.e("areaBeen is null ");
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        toast("网络异常，请重试！");
                        throwable.printStackTrace();
                        LogUtils.e(throwable.getMessage());
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void InitListener() {

    }

    public void initViewPager(List<AreaBean> areaList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        if (areaList != null) {
            //除了固定的其他频道被选中，添加
            for (AreaBean area : areaList) {
                final HotMusicItemFragment fragment=BaseSupportFragment.newInstance(HotMusicItemFragment.class,area);
                fragments.add(fragment);
                titles.add(area.name);
            }
        }

        if (vp_hot_music.getAdapter() == null) {
            //初始化ViewPager
            adapter = new BaseFragmentAdapter(getChildFragmentManager(),
                    fragments, titles);
            vp_hot_music.setAdapter(adapter);
        } else {
            adapter = (BaseFragmentAdapter) vp_hot_music.getAdapter();
            adapter.updateFragments(fragments, titles);
        }
        vp_hot_music.setCurrentItem(0, false);
        tab_hot_music.setupWithViewPager(vp_hot_music);
        tab_hot_music.setScrollPosition(0, 0, true);
        TabLayoutUtil.dynamicSetTabLayoutMode(tab_hot_music);
    }
}
