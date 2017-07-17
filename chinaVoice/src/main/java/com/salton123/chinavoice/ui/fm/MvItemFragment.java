package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.business.mvItem.MvItemFragmentContract;
import com.salton123.chinavoice.business.mvItem.MvItemFragmentPresenter;
import com.salton123.chinavoice.model.domain.AreaBean;
import com.salton123.chinavoice.model.domain.MVListBean;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.chinavoice.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.chinavoice.util.BGAUtil;
import com.salton123.chinavoice.view.adapter.MvItemAdapter;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 9:36
 * Time: 9:36
 * Description:
 */
public class MvItemFragment extends BaseSupportPresenterFragment<MvItemFragmentPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate, MvItemFragmentContract.View {
    private BGARefreshLayout bgaRefresh;
    private RecyclerView recycler;
    private MvItemAdapter mAdapter;
    protected static final int SIZE = 20;
    protected int mOffset = 0;

//    public MvItemFragment newInstance(AreaBean areaBean) {
//        MvItemFragment fragment = new MvItemFragment();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(ARG_ITEM, areaBean);
//        fragment.setArguments(bundle);
//        return fragment;
//    }


    @Override
    public int GetLayout() {
        return R.layout.fm_mv_pager_item;
    }

    AreaBean areaBean;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new MvItemFragmentPresenter();
        areaBean = getArguments().getParcelable(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        bgaRefresh = f(R.id.bgaRefresh);
        recycler = f(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bgaRefresh.setDelegate(this);
        bgaRefresh.setRefreshViewHolder(BGAUtil.getNormalRefreshViewHolder(_mActivity));
        bgaRefresh.setIsShowLoadingMoreView(true);
        mAdapter = new MvItemAdapter(recycler);
        recycler.setAdapter(mAdapter);
        mPresenter.getData(mOffset, SIZE, areaBean.getCode());
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                VideoBean videoBean = mAdapter.getItem(position);
                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(MvDetailFragment.class, videoBean)));
            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mOffset = 0;
        mAdapter.clear();
        mPresenter.getData(mOffset, SIZE, areaBean.getCode());
        bgaRefresh.beginRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.getData(mOffset, SIZE, areaBean.getCode());
        return false;
    }

    boolean loadMore = true;

    @Override
    public void setData(MVListBean bean) {
        if (bean.getVideos().size() < SIZE) {
            loadMore = false;

        }
        mAdapter.addMoreData(bean.getVideos());
        bgaRefresh.endRefreshing();
        bgaRefresh.endLoadingMore();
        mOffset += bean.getVideos().size();
    }

    @Override
    public void setError(String msg) {
        toast(msg);
    }
}
