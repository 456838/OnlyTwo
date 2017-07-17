package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.common.image.FrescoImageLoader;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:56
 * Time: 14:56
 * Description:
 */
public class MvDescribeFragment extends BaseSupportFragment {
    SimpleDraweeView sdv_thumnail;
    TextView tv_artist_name, tv_play_count, tv_play_pc_count, tv_play_mobile_count, tv_describe;
    MVDetailBean detailBean;

    @Override
    public int GetLayout() {
        return R.layout.fm_mv_describe;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        detailBean = getArguments().getParcelable(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        sdv_thumnail = f(R.id.profile_image);
        tv_artist_name = f(R.id.artist_name);
        tv_play_count = f(R.id.play_count);
        tv_play_pc_count = f(R.id.play_pc_count);
        tv_play_mobile_count = f(R.id.play_mobile_count);
        tv_describe = f(R.id.describe);
        if (detailBean != null) {
            tv_artist_name.setText(detailBean.getArtistName());
            tv_play_count.setText("播放次数：" + String.valueOf(detailBean.getTotalViews()));
            tv_play_pc_count.setText("pc端：" + String.valueOf(detailBean.getTotalPcViews()));
            tv_play_mobile_count.setText("移动端：" + String.valueOf(detailBean.getTotalMobileViews()));
            tv_describe.setText(detailBean.getDescription());
            FrescoImageLoader.display(sdv_thumnail,detailBean.getArtists().get(0).getArtistAvatar());
        }


    }

    @Override
    public void InitListener() {

    }
}
