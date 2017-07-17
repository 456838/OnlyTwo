package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.event.StartBrotherEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/14 21:53
 * Time: 21:53
 * Description:
 */
public class YinYueTaiFragment extends BaseSupportFragment {
    @Override
    public int GetLayout() {
        return R.layout.fm_yin_yue_tai;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
    }

    @Override
    public void InitViewAndData() {
        loadRootFragment(R.id.fl_container, BaseSupportFragment.newInstance(HomePageFragment.class));
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void onDestroyView() {
        EventBus.getDefault().unregister(this);
        super.onDestroyView();
    }

    /**
     * start other BrotherFragment
     */
    @Subscribe
    public void startBrother(StartBrotherEvent event) {
        start(event.targetFragment);
    }
}
