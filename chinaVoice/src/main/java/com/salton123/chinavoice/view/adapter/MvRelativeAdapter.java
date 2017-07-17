package com.salton123.chinavoice.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.DraweeView;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.model.domain.MVDetailBean;
import com.salton123.common.image.FrescoImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 17:05
 * Time: 17:05
 * Description:
 */
public class MvRelativeAdapter extends BGARecyclerViewAdapter<MVDetailBean.RelatedVideosBean>{
    public MvRelativeAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_relative_mv);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, MVDetailBean.RelatedVideosBean model) {
        helper
                .setText(R.id.title,model.getTitle())
                .setText(R.id.artistName,model.getArtistName())
                .setText(R.id.play_count,"播放次数：" + model.getTotalViews());
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.sdv_thumbnail),model.getPosterPic());
    }
}
