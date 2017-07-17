package com.salton123.chinavoice.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.facebook.drawee.view.DraweeView;
import com.salton123.chinavoice.R;
import com.salton123.chinavoice.model.domain.VideoBean;
import com.salton123.common.image.FrescoImageLoader;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/7/15 10:44
 * Time: 10:44
 * Description:
 */
public class MvItemAdapter extends BGARecyclerViewAdapter<VideoBean> {
    public MvItemAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_mv_item);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, VideoBean model) {
        helper.setText(R.id.name, model.getTitle()).setText(R.id.author, model.getDescription()).setText(R.id.play_count, model.isAd() ? "" : model.getTotalViews() + "");
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.poster_img),model.isAd()?model.getThumbnailPic():model.getAlbumImg());
    }
}
