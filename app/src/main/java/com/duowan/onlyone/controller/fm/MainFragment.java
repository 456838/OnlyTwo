package com.duowan.onlyone.controller.fm;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.duowan.onlyone.R;
import com.duowan.onlyone.controller.fm.video.VideoFragment;
import com.duowan.onlyone.event.TabSelectedEvent;
import com.qihoo360.replugin.RePlugin;
import com.salton123.base.BaseSupportFragment;
import com.salton123.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * User: 巫金生(newSalton@163.com)
 * Date: 2017/6/15 22:30
 * Description:
 * Updated:
 */
public class MainFragment extends BaseSupportFragment {
    BottomNavigationBar mNavigationView;
    private SupportFragment[] mFragments = new SupportFragment[4];
    public static final int ZERO = 0;
    public static final int FIRST = 1;
    public static final int SECOND = 2;
    public static final int THIRD = 3;

    @Override
    public int GetLayout() {
        return R.layout.fm_main;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mFragments[ZERO] = BaseSupportFragment.newInstance(FirstFragment.class);
            mFragments[FIRST] = (BaseSupportFragment.newInstance(SecondFragment.class));
            mFragments[SECOND] = (BaseSupportFragment.newInstance(ThirdFragment.class));
            mFragments[THIRD] = (BaseSupportFragment.newInstance(FourthFragment.class));
            loadMultipleRootFragment(R.id.act_container, ZERO
                    , mFragments[ZERO]
                    , mFragments[FIRST]
                    , mFragments[SECOND]
                    , mFragments[THIRD]
            );
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用,也可以通过getSupportFragmentManager.getFragments()自行进行判断查找(效率更高些),用下面的方法查找更方便些
            mFragments[ZERO] = findChildFragment(VideoFragment.class);
            mFragments[FIRST] = findChildFragment(SecondFragment.class);
            mFragments[SECOND] = findChildFragment(ThirdFragment.class);
            mFragments[THIRD] = findChildFragment(FourthFragment.class);
        }
    }

    @Override
    public void InitViewAndData() {
        EventBus.getDefault().register(this);
        mNavigationView = f(R.id.bottom_navigation_bar);
        mNavigationView
                .addItem(new BottomNavigationItem(R.drawable.movie_icon, "热门").setActiveColor("#485A66").setInActiveColor("#CCCCCC"))
                .addItem(new BottomNavigationItem(R.drawable.music_icon, "直播").setActiveColor("#485A66"))
                .addItem(new BottomNavigationItem(R.drawable.book_icon, "小视频").setActiveColor("#485A66"))
                .addItem(new BottomNavigationItem(R.drawable.newspaper_icon, "个人").setActiveColor("#485A66"))
                .initialise();
        mNavigationView.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_RIPPLE);
        mNavigationView.setMode(BottomNavigationBar.MODE_FIXED);
        mNavigationView.setAutoHideEnabled(false);
    }

    @Override
    public void InitListener() {
        mNavigationView.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                showHideFragment(mFragments[position]);
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 这里推荐使用EventBus来实现 -> 解耦
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBus.getDefault().post(new TabSelectedEvent(position));
            }
        });
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(_mActivity);
        builder.
                setTitle("Hi").
                setMessage(Html.fromHtml("要退出App吗？")).
                setPositiveButton("嗯", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _mActivity.finish();
                        System.exit(0);
                    }
                }).setNegativeButton("再看看", null)
                .show();
        return true;
    }

    public void test(){
        //注册相关Fragment的类
        //注册一个全局Hook用于拦截系统对XX类的寻找定向到Demo1中的XX类主要是用于在xml中可以直接使用插件中的类
        try {
            RePlugin.registerHookingClass("com.qihoo360.replugin.sample.demo1.fragment.DemoFragment", RePlugin.createComponentName("demo1", "com.qihoo360.replugin.sample.demo1.fragment.DemoFragment"), null);
            //代码使用插件Fragment
            ClassLoader d1ClassLoader = RePlugin.fetchClassLoader("demo1");//获取插件的ClassLoader
            BaseSupportFragment.newInstance(d1ClassLoader.loadClass("com.qihoo360.replugin.sample.demo1.fragment.DemoCodeFragment").asSubclass(Fragment.class));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
//
//    public interface OnBackToFirstListener {
//        void onBackToFirstFragment();
//    }
}
