package com.duowan.onlyone.view.adapter;

import android.support.v7.widget.RecyclerView;

import com.duowan.onlyone.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.common.image.FrescoImageLoader;
import com.duowan.onlyone.model.entity.YYHomeIndex;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:57
 * Time: 22:57
 * Description:
 */
public class HotLiveRadioAdapter extends BGARecyclerViewAdapter<YYHomeIndex.DataBeanX.DataBean> {
    public HotLiveRadioAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_item_home_gridview);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, YYHomeIndex.DataBeanX.DataBean model) {
//        ImageView living_thumb_asr  = helper.getImageView(R.id.iv_thumbnail);
//        ImageLoaderUtils.display(living_thumb_asr,model.getThumb());
        SimpleDraweeView iv_thumbnail = helper.getView(R.id.iv_thumbnail);
        FrescoImageLoader.displayInCenterInside(mContext, iv_thumbnail, model.getThumb());
        helper.setText(R.id.tv_fans, model.getUsers() + "");
        if (model.getUsers() == 0) {
            helper.setText(R.id.tv_fans, model.getFans() + "");
        }
        helper.setText(R.id.tv_desc, model.getDesc());
    }

//    @Override
//    protected void setItemChildListener(BGAViewHolderHelper viewHolderHelper) {
//        viewHolderHelper.setItemChildClickListener(R.id.iv_item_play_voice);
//        super.setItemChildListener(viewHolderHelper);
//    }

//    @Override
//    protected void fillData(BGAViewHolderHelper viewHolderHelper, int position, YcProgram model) {
//        ImageView imageView = viewHolderHelper.getView(R.id.sdv_thumbnail);
//        x.image().bind(imageView,model.getThumbnailUrl());
//        int total = (int) model.getTotalPeople();
//        viewHolderHelper
//                .setText(R.id.tv_title_info, model.getTitleInfo())
//                .setText(R.id.tv_program_owner, model.getOwnerName())
//                .setText(R.id.tv_reply_count, "当前人数:" + total);
//    }

}
