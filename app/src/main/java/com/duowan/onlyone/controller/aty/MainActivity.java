package com.duowan.onlyone.controller.aty;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import com.duowan.onlyone.R;
import com.duowan.onlyone.controller.fm.MainFragment;
import com.jaeger.library.StatusBarUtil;
import com.salton123.base.BaseSupportActivity;
import com.salton123.base.BaseSupportFragment;
import com.tbruyelle.rxpermissions.Permission;
import com.tbruyelle.rxpermissions.RxPermissions;

import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.helper.FragmentLifecycleCallbacks;
import rx.functions.Action1;

public class MainActivity extends BaseSupportActivity {

    @Override
    public int GetLayout() {
        return R.layout.fm_container;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        checkPermission();
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(MainFragment.class));
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
        StatusBarUtil.setColor(MainActivity.this, getResources().getColor(R.color.colorPrimary));
    }

    @Override
    public void InitViewAndData() {
    }

    @Override
    public void InitListener() {

    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            RxPermissions rxPermissions = new RxPermissions(this);
            rxPermissions.setLogging(true);
            rxPermissions.requestEach(Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECORD_AUDIO)
                    .subscribe(new Action1<Permission>() {
                        @Override
                        public void call(Permission permission) {
                            if (permission.name.equals(Manifest.permission.READ_PHONE_STATE) && !permission.granted) {
                                toast("暂无摄像头权限");
                            } else if (permission.name.equals(Manifest.permission.RECORD_AUDIO) && !permission.granted) {
                                toast("暂无录音权限");
                            }
                        }
                    });
        }

    }


}
