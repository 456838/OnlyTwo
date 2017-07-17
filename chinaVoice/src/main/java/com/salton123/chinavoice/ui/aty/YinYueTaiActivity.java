package com.salton123.chinavoice.ui.aty;

import android.os.Bundle;
import android.util.Log;

import com.jaeger.library.StatusBarUtil;
import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.ui.fm.YinYueTaiFragment;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;

public class YinYueTaiActivity extends BaseSupportActivity {

    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(YinYueTaiFragment.class));
        }

        // 可以监听该Activity下的所有Fragment的18个 生命周期方法
        registerFragmentLifecycleCallbacks(new FragmentLifecycleCallbacks() {

            @Override
            public void onFragmentSupportVisible(SupportFragment fragment) {
                Log.i("MainActivity", "onFragmentSupportVisible--->" + fragment.getClass().getSimpleName());
            }

            @Override
            public void onFragmentCreated(SupportFragment fragment, Bundle savedInstanceState) {
                super.onFragmentCreated(fragment, savedInstanceState);
            }
            // 省略其余生命周期方法
        });
        StatusBarUtil.setColor(YinYueTaiActivity.this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void InitViewAndData() {
    }

    @Override
    public void InitListener() {

    }


}

