package com.duowan.onlyone.controller.fm.music;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;

import com.duowan.onlyone.R;
import com.duowan.onlyone.model.api.OnlyOneApi;
import com.duowan.onlyone.model.entity.yinyuetai.AreaBean;
import com.duowan.onlyone.model.entity.yinyuetai.MVListBean;
import com.duowan.onlyone.model.entity.yinyuetai.VideoBean;
import com.duowan.onlyone.view.adapter.HotMusicItemAdapter;
import com.orhanobut.logger.Logger;
import com.salton123.base.BaseSupportFragment;
import com.salton123.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/10 10:47
 * Time: 10:47
 * Description:
 */
public class HotMusicItemFragment extends BaseSupportFragment {
    RecyclerView rv_hot_music_item;
    SwipeRefreshLayout srl_hot_music_item;
    private AreaBean mAreaBean;
    private HotMusicItemAdapter mHotMusicItemAdapter;
    private ArrayList<VideoBean> videosList = new ArrayList<>();

    protected boolean refresh;
    protected int lastVisibleItem;
    protected boolean hasMore = true;
    protected static final int SIZE = 20;
    protected int mOffset = 0;

    @Override
    public int GetLayout() {
        return R.layout.fm_hot_music_item;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        JCVideoPlayer.FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
    }

    @Override
    public void InitViewAndData() {
        srl_hot_music_item = f(R.id.srl_hot_music_item);
        rv_hot_music_item = f(R.id.rv_hot_music_item);
        mHotMusicItemAdapter = new HotMusicItemAdapter(videosList);
        rv_hot_music_item.setLayoutManager(new LinearLayoutManager(getActivity()));
        srl_hot_music_item.setColorSchemeResources(R.color.colorPrimary);
        srl_hot_music_item.setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 48, getResources()
                        .getDisplayMetrics()));
        srl_hot_music_item.setRefreshing(true);
    }


    @Override
    public void InitListener() {
        rv_hot_music_item.setAdapter(mHotMusicItemAdapter);
        rv_hot_music_item.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {

                }
                if (newState == RecyclerView.SCROLL_STATE_IDLE && (lastVisibleItem + 1 == mHotMusicItemAdapter.getItemCount()) && hasMore) {
                    loadHotMusic(mAreaBean.code, mOffset + 1, SIZE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();

            }
        });
        srl_hot_music_item.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh = true;
                videosList.clear();
                mOffset = 0;
                JCVideoPlayer.releaseAllVideos();
                loadHotMusic(mAreaBean.code, mOffset, SIZE);
                Logger.e("onRefresh");

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mAreaBean = getArguments().getParcelable(ARG_ITEM);
        loadHotMusic(mAreaBean.code, mOffset, SIZE);
    }

    //初始化音乐
    private void loadHotMusic(String areaCode, int offset, int size) {
        showLoading();
        Observable<MVListBean> observable = OnlyOneApi.GetMusicApiService().getVideoList(OnlyOneApi.deviceinfo, areaCode, offset, size);
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<MVListBean>() {
                    @Override
                    public void call(MVListBean bean) {
                        if (bean != null) {
                            setData(bean.getVideos());
                        } else {
                            toast("网络错误，请重试");
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

    public void setData(List<VideoBean> videoList) {
        dismissLoading();
        if (refresh) {
            refresh = false;
            mOffset = 0;
            videosList.clear();
        }
        if (videoList == null || videoList.size() == 0) {
            hasMore = false;
        } else {
            hasMore = true;
            int pos = videosList.size() - 1;
            videosList.addAll(videoList);
            mHotMusicItemAdapter.notifyItemRangeChanged(pos, videoList.size());
            mOffset += videoList.size();
        }
    }

    public void showLoading() {
        srl_hot_music_item.setRefreshing(true);
    }

    public void dismissLoading() {
        srl_hot_music_item.setRefreshing(false);
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }
}
