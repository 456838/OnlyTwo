package com.salton123.chinavoice.ui.fm;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.business.mvDetail.MvDetailFragmentContract;
import com.salton123.chinavoice.business.mvDetail.MvDetailFragmentPresenter;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.chinavoice.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.util.BlankUtil;
import com.salton123.util.EventUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import fm.jiecao.jcvideoplayer_lib.JCMediaManager;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

import static com.salton123.chinavoice.R.id.videoplayer;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_AUTO_COMPLETE;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_ERROR;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_NORMAL;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_PAUSE;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.CURRENT_STATE_PLAYING;
import static fm.jiecao.jcvideoplayer_lib.JCVideoPlayer.SCREEN_LAYOUT_NORMAL;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:29
 * Time: 14:29
 * Description:
 */
public class MvDetailFragment extends BaseSupportPresenterFragment<MvDetailFragmentPresenter> implements View.OnClickListener, MvDetailFragmentContract.View {
    private JCVideoPlayerStandard videoPlayer;
    private LinearLayout navigation;
    private ImageView mv_description, mv_comment, mv_relatiion;
    VideoBean mVideoBean;
    private MvDescribeFragment mvDescribeFragment;
    private MvRelativeFragment mvRelativeFragment;

    @Override
    public int GetLayout() {
        return R.layout.fm_mv_detail;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mVideoBean = getArguments().getParcelable(ARG_ITEM);
        mPresenter = new MvDetailFragmentPresenter();
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        EventUtil.register(this);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mPresenter.getData(mVideoBean.getId());
    }

    @Override
    public void InitViewAndData() {
        videoPlayer = f(videoplayer);
        navigation = f(R.id.navigation);
        mv_description = f(R.id.mv_description);
        mv_comment = f(R.id.mv_comment);
        mv_relatiion = f(R.id.mv_relatiion);

    }

    @Override
    public void InitListener() {
        mv_description.setOnClickListener(this);
        mv_comment.setOnClickListener(this);
        mv_relatiion.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mv_description:
                setImageBackground(mv_description, R.mipmap.player_mv_p);
                setImageBackground(mv_comment, R.mipmap.player_comment);
                setImageBackground(mv_relatiion, R.mipmap.player_relative_mv);
                setFragment(mvDescribeFragment);
                break;
            case R.id.mv_comment:
                setImageBackground(mv_description, R.mipmap.player_mv);
                setImageBackground(mv_comment, R.mipmap.player_comment_p);
                setImageBackground(mv_relatiion, R.mipmap.player_relative_mv);
                break;
            case R.id.mv_relatiion:
                setImageBackground(mv_description, R.mipmap.player_mv);
                setImageBackground(mv_comment, R.mipmap.player_comment);
                setImageBackground(mv_relatiion, R.mipmap.player_relative_mv_p);
                setFragment(mvRelativeFragment);
                break;
        }
    }

    private void setImageBackground(ImageView imageView, int resId) {
        imageView.setBackgroundResource(resId);
    }

    private void setFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (fragment.isAdded() && fragment.isVisible()) {
            return;
        }
        if (fragment.isAdded()) {
            transaction.show(fragment);
        } else {
            transaction.replace(R.id.fragment_content, fragment);
        }
        transaction.commit();
    }

    @Override
    public void startPlay(MVDetailBean bean) {
//        videoPlayer.initUIState();
        videoPlayer.setUp(getTheBestUrl(bean), SCREEN_LAYOUT_NORMAL, bean.getTitle());
        videoPlayer.startButton.performClick();
        mvDescribeFragment = BaseSupportFragment.newInstance(MvDescribeFragment.class, bean);
        mvRelativeFragment = BaseSupportFragment.newInstance(MvRelativeFragment.class, bean);
        if (flag) {
            flag = !flag;
        } else setFragment(mvDescribeFragment);

    }

    @Override
    public void setError(String msg) {
        toast(msg);
    }

    private String getTheBestUrl(MVDetailBean bean) {
        if (bean == null) return "";
        String shdUrl = BlankUtil.isBlank(bean.getShdUrl()) ? "" : bean.getShdUrl();
        String uhdUrl = BlankUtil.isBlank(bean.getUhdUrl()) ? shdUrl : bean.getUhdUrl();
        String url = BlankUtil.isBlank(bean.getUrl()) ? uhdUrl : bean.getUrl();
        String hdUrl = BlankUtil.isBlank(bean.getHdUrl()) ? url : bean.getHdUrl();
        return hdUrl;
    }


    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
            JCMediaManager.instance().mediaPlayer.pause();
        } else if (videoPlayer.currentState == CURRENT_STATE_PAUSE) {
            JCMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
//        JCMediaManager.instance().mediaPlayer.start();
        if (videoPlayer.currentState == CURRENT_STATE_NORMAL || videoPlayer.currentState == CURRENT_STATE_ERROR) {
//            JCMediaManager.instance().mediaPlayer.start();
        } else if (videoPlayer.currentState == CURRENT_STATE_PLAYING) {
            JCMediaManager.instance().mediaPlayer.pause();
        } else if (videoPlayer.currentState == CURRENT_STATE_PAUSE) {
            JCMediaManager.instance().mediaPlayer.start();
        } else if (videoPlayer.currentState == CURRENT_STATE_AUTO_COMPLETE) {
            JCMediaManager.instance().mediaPlayer.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        _mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        hideSoftInput();
        JCVideoPlayer.releaseAllVideos();
        EventUtil.unregister(this);
    }

    boolean flag = false;

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(MVDetailBean.RelatedVideosBean relatedVideosBean) {
        mPresenter.getData(relatedVideosBean.getId());
        flag = true;
    }


}
