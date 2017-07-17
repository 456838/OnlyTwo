package com.salton123.chinavoice.ui.fm;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.salton123.base.BaseSupportFragment;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.chinavoice.view.adapter.MvRelativeAdapter;
import com.salton123.util.EventUtil;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 14:56
 * Time: 14:56
 * Description:
 */
public class MvRelativeFragment extends BaseSupportFragment {
    private RecyclerView recycler;
    private MvRelativeAdapter mAdapter;
    @Override
    public int GetLayout() {
        return R.layout.fm_mv_relation;
    }
    MVDetailBean detailBean;
    @Override
    public void InitVariable(Bundle savedInstanceState) {
        detailBean = getArguments().getParcelable(ARG_ITEM);
    }

    @Override
    public void InitViewAndData() {
        recycler=f(R.id.recycler);
        mAdapter = new MvRelativeAdapter(recycler);
        recycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        recycler.setAdapter(mAdapter);
        if (detailBean!=null)
        mAdapter.addNewData(detailBean.getRelatedVideos());
//        recycler.addItemDecoration(new RecycleViewDivider(getActivity(),LinearLayoutManager.HORIZONTAL));
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                EventUtil.sendEvent(mAdapter.getItem(position));
            }
        });
    }

    @Override
    public void InitListener() {

    }
}
