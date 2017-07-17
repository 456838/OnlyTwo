package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.business.homePage.HomePageFragmentContract;
import com.salton123.chinavoice.business.homePage.HomePageFragmentPresenter;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.chinavoice.mvp.ui.BaseSupportPresenterFragment;
import com.salton123.chinavoice.util.BGAUtil;
import com.salton123.chinavoice.view.adapter.HomePageAdapter;
import com.salton123.event.StartBrotherEvent;
import com.salton123.util.EventUtil;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/17 10:25
 * Time: 10:25
 * Description:
 */
public class HomePageFragment extends BaseSupportPresenterFragment<HomePageFragmentPresenter> implements BGARefreshLayout.BGARefreshLayoutDelegate, HomePageFragmentContract.View {
    private BGARefreshLayout bgaRefresh;
    private RecyclerView recycler;
    private HomePageAdapter mAdapter;
    protected static final int SIZE = 20;
    protected int mOffset = 0;

    @Override
    public int GetLayout() {
        return R.layout.fm_mv_pager_item;
    }

//    AreaBean areaBean;

    @Override
    public void InitVariable(Bundle savedInstanceState) {
        mPresenter = new HomePageFragmentPresenter();
//        areaBean = getArguments().getParcelable(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        bgaRefresh = f(R.id.bgaRefresh);
        recycler = f(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(_mActivity));
        bgaRefresh.setDelegate(this);
        bgaRefresh.setRefreshViewHolder(BGAUtil.getNormalRefreshViewHolder(_mActivity));
        bgaRefresh.setIsShowLoadingMoreView(true);
        mAdapter = new HomePageAdapter(recycler);
        recycler.setAdapter(mAdapter);
        mPresenter.getData(mOffset, SIZE);
    }

    @Override
    public void InitListener() {
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                VideoBean videoBean = mAdapter.getItem(position);
                switch (mAdapter.getTag(videoBean)){
                    case 0:
                    case 4:
                    case 10:
                        EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(WebFragment.class, videoBean)));
                        break;
                    case 1:
                    case 5:
                    case 7:
                        EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(MvDetailFragment.class, videoBean)));
                        break;
                    case 2:
                    case 3:
                        EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(YueDanFragment.class, videoBean)));
                        break;
                    default:
                        return;
                }
//                EventUtil.sendEvent(new StartBrotherEvent(BaseSupportFragment.newInstance(MvDetailFragment.class, videoBean)));


            }
        });
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout refreshLayout) {
        mOffset = 0;
        mAdapter.clear();
        mPresenter.getData(mOffset, SIZE);
        bgaRefresh.beginRefreshing();
    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout refreshLayout) {
        mPresenter.getData(mOffset, SIZE);
        return false;
    }

    boolean loadMore = true;


    @Override
    public void setData(List<VideoBean> bean) {
        if (bean.size() < SIZE) {
            loadMore = false;
        }
        mAdapter.addMoreData(bean);
        bgaRefresh.endRefreshing();
        bgaRefresh.endLoadingMore();
        mOffset += bean.size();
    }

    @Override
    public void setError(String msg) {
        toast(msg);
    }
}
