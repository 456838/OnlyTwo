package com.duowan.onlyone.controller.fm.video;

import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.duowan.onlyone.R;
import com.duowan.onlyone.model.entity.kaiyanBean.DataBean;
import com.duowan.onlyone.model.entity.utils.DateUtil;
import com.salton123.base.BaseSupportSwipeBackFragment;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_ERROR;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_NORMAL;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_PAUSE;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_PLAYING;


/**
 * Created bycuieney on 17/2/25.
 */

public class VideoDetailFragment extends BaseSupportSwipeBackFragment {

    TextView title;
    TextView type;
    TextView description;
    TextView tv_title ;
    JCVideoPlayerStandard videoplayer;
    private DataBean dataBean;

    @Override
    public int GetLayout() {
        return R.layout.video_home_detail_fragment;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        dataBean = getArguments().getParcelable(ARG_ITEM);
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void InitViewAndData() {
        title = f(R.id.title);
        type = f(R.id.type);
        description = f(R.id.description);
        videoplayer = f(R.id.videoplayer);
        tv_title = f(R.id.tv_title);
        videoplayer.setUp(dataBean.getPlayUrl(), JCVideoPlayer.SCREEN_LAYOUT_NORMAL, dataBean.getCover().getDetail());
        videoplayer.thumbImageView.setImageURI(Uri.parse(dataBean.getCover().getDetail()));
        title.setText(dataBean.getTitle());
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("#").append(dataBean.getCategory())
                .append(" ")
                .append(" / ")
                .append(" ")
                .append(DateUtil.formatTime2(dataBean.getDuration()));
        type.setText(stringBuilder.toString());
        description.setText(dataBean.getDescription());
        tv_title.setText(dataBean.getCategory());
    }

    @Override
    public void InitListener() {
        f(R.id.tv_title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop();
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoplayer.currentState == CURRENT_STATE_PLAYING) {
            JCMediaManager.instance().mediaPlayer.pause();
        } else if (videoplayer.currentState == CURRENT_STATE_PAUSE) {
            JCMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        JCMediaManager.instance().mediaPlayer.start();
        if (videoplayer.currentState == CURRENT_STATE_NORMAL || videoplayer.currentState == CURRENT_STATE_ERROR) {
//            JCMediaManager.instance().mediaPlayer.start();
        } else if (videoplayer.currentState == CURRENT_STATE_PLAYING) {
            JCMediaManager.instance().mediaPlayer.pause();
        } else if (videoplayer.currentState == CURRENT_STATE_PAUSE) {
            JCMediaManager.instance().mediaPlayer.start();
        } else if (videoplayer.currentState == CURRENT_STATE_AUTO_COMPLETE) {
            JCMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        hideSoftInput();
        JCVideoPlayer.releaseAllVideos();
    }
}
