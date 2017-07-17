package com.duowan.onlyone.controller.fm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.duowan.onlyone.R;
import com.duowan.onlyone.controller.fm.base.BaseHomeFragment;
import com.qihoo360.replugin.RePlugin;
import com.salton123.base.BaseSupportFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/15 11:35
 * Time: 11:35
 * Description:
 */
public class ThirdFragment extends BaseHomeFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }


    @Override
    public void InitViewAndData() {
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if (savedInstanceState == null) {
//            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(HotMusicFragment.class));
            //注册相关Fragment的类
            //注册一个全局Hook用于拦截系统对XX类的寻找定向到Demo1中的XX类主要是用于在xml中可以直接使用插件中的类
            try {
                RePlugin.registerHookingClass("com.salton123.chinavoice.ui.fm.YinYueTaiFragment", RePlugin.createComponentName("chinavoice", "com.salton123.chinavoice.ui.fm.YinYueTaiFragment"), null);
                //代码使用插件Fragment
                ClassLoader d1ClassLoader = RePlugin.fetchClassLoader("chinavoice");//获取插件的ClassLoader
                loadRootFragment(R.id.fl_container, (SupportFragment) BaseSupportFragment.newInstance(d1ClassLoader.loadClass("com.salton123.chinavoice.ui.fm.YinYueTaiFragment").asSubclass(Fragment.class)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
