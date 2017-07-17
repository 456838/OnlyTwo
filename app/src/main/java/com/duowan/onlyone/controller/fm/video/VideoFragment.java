package com.duowan.onlyone.controller.fm.video;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;

import com.duowan.onlyone.R;
import com.duowan.onlyone.controller.fm.MainFragment;
import com.duowan.onlyone.controller.fm.base.BaseHomeFragment;
import com.salton123.event.StartBrotherEvent;
import com.duowan.onlyone.event.TabSelectedEvent;
import com.duowan.onlyone.model.api.OnlyOneApi;
import com.duowan.onlyone.model.entity.VideoListBean;
import com.duowan.onlyone.model.entity.kaiyanBean.ItemListBean;
import com.duowan.onlyone.view.adapter.VideoAdapter;
import com.duowan.onlyone.view.callback.EndLessOnScrollListener;
import com.salton123.base.BaseRecycerViewAdapter;
import com.salton123.base.BaseSupportFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/6/7 13:47
 * Time: 13:47
 * Description:
 */
public class VideoFragment extends BaseHomeFragment {
    RecyclerView recycler;
    SwipeRefreshLayout refresh;

    private String date;
    private VideoAdapter adapter;
    private List<ItemListBean> mVideoListBean;

    @Override
    public int GetLayout() {
        return R.layout.video_home_fragment;
    }

    @Override
    public void InitVariable(Bundle savedInstanceState) {

    }

    @Override
    public void InitViewAndData() {
        recycler = f(R.id.recycler);
        refresh = f(R.id.refresh);
//        refresh.setProgressViewOffset(false, 100, 200);
        refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadData("");
            }
        });
        recycler.setHasFixedSize(true);
        final int space = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 0.5f, getResources().getDisplayMetrics());
        recycler.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.set(0, 0, 0, space);
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recycler.setLayoutManager(linearLayoutManager);
        mVideoListBean = new ArrayList<>();

        adapter = new VideoAdapter(getActivity(), mVideoListBean);
        adapter.setOnItemClickListener(new BaseRecycerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View view, RecyclerView.ViewHolder vh) {
                ItemListBean videoListBean = mVideoListBean.get(position);
                EventBus.getDefault().post(new StartBrotherEvent(BaseSupportFragment.newInstance(VideoDetailFragment.class, videoListBean.getData())));
            }
        });
        recycler.setAdapter(adapter);
        recycler.addOnScrollListener(new EndLessOnScrollListener(linearLayoutManager, 0) {
            @Override
            public void onLoadMore() {
                loadData(date);
            }
        });

    }



    @Override
    public void InitListener() {

    }


    private void loadData(String date) {
        Observable<VideoListBean> observable = OnlyOneApi.GetKyApiService().getVideoList(date);
        Subscription subscription = observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<VideoListBean>() {
                    @Override
                    public void call(VideoListBean videoListBean) {
                        showContent(videoListBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        refresh.setRefreshing(false);
                        refresh.setEnabled(true);
                    }
                });
        mCompositeSubscription.add(subscription);
    }

    public void showContent(VideoListBean videoListBean) {
        if (refresh.isRefreshing()) {
            refresh.setRefreshing(false);
            mVideoListBean.clear();
            adapter.clear();
            adapter.addAll(videoListBean.getItemList());
            recycler.setAdapter(adapter);
        } else {
            adapter.addAll(videoListBean.getItemList());
        }
        mVideoListBean.addAll(videoListBean.getItemList());

        int end = videoListBean.getNextPageUrl().lastIndexOf("&num");
        int start = videoListBean.getNextPageUrl().lastIndexOf("date=");
        date = videoListBean.getNextPageUrl().substring(start + 5, end);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadData("");
    }

    /**
     * Reselected Tab
     */
    @Subscribe
    public void onTabSelectedEvent(TabSelectedEvent event) {
        if (event.position != MainFragment.FIRST) return;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recycler.setAdapter(null);
        EventBus.getDefault().unregister(this);
    }

}
