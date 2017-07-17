package com.salton123.chinavoice.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

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
public class HomePageAdapter extends BGARecyclerViewAdapter<VideoBean> {
    public HomePageAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.adapter_home_page);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, VideoBean model) {
        helper.setText(R.id.home_recommend_item_title, model.getTitle()).setText(R.id.home_recommend_item_description, model.getDescription());
        ImageView homeRecommendItemType = helper.getImageView(R.id.home_recommend_item_type);
        String type = model.getType();
        FrescoImageLoader.display((DraweeView) helper.getView(R.id.home_recommend_item_jpg), model.getPosterPic());
        final int tag;
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_activity);
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_video);
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_star);
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_playlist);
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_ad);
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_program);
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_bulletin);
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_fanart);
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_live);
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            tag = 9;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_live_new);
        } else if ("INVENTORY".equalsIgnoreCase(model.getType())) {//打开页面
            tag = 10;
            homeRecommendItemType.setImageResource(R.mipmap.home_page_project);
        } else {
            tag = -100;
            homeRecommendItemType.setImageResource(0);
        }
    }

    public int getTag(VideoBean model) {
        int tag;
        String type = model.getType();
        if ("ACTIVITY".equalsIgnoreCase(type)) {//打开页面
            tag = 0;
        } else if ("VIDEO".equalsIgnoreCase(type)) {//首播，点击进去显示MV描述，相关MV
            tag = 1;
        } else if ("WEEK_MAIN_STAR".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 2;
        } else if ("PLAYLIST".equalsIgnoreCase(type)) {//(悦单)点击进去跟显示悦单详情一样
            tag = 3;
        } else if ("AD".equalsIgnoreCase(type)) {
            tag = 4;
        } else if ("PROGRAM".equalsIgnoreCase(type)) {//跳到MV详情
            tag = 5;
        } else if ("bulletin".equalsIgnoreCase(type)) {
            tag = 6;
        } else if ("fanart".equalsIgnoreCase(type)) {
            tag = 7;
        } else if ("live".equalsIgnoreCase(type)) {
            tag = 8;
        } else if ("LIVENEW".equalsIgnoreCase(type) || ("LIVENEWLIST".equals(type))) {
            tag = 9;
        } else if ("INVENTORY".equalsIgnoreCase(model.getType())) {//打开页面
            tag = 10;
        } else {
            tag = -100;
        }
        return tag;
    }
}

//    @Bind(R.id.home_recommend_item_jpg)
//    ImageView homeRecommendItemJpg;
//    @Bind(R.id.home_recommend_item_type)
//    ImageView homeRecommendItemType;
//    @Bind(R.id.home_recommend_item_title)
//    TextView homeRecommendItemTitle;
//    @Bind(R.id.home_recommend_item_description)
//    TextView homeRecommendItemDescription;
//    @Bind(R.id.home_recommend_item_transbg)
//    ImageView homeRecommendItemTransbg;
//    @Bind(R.id.item_root)
//    RelativeLayout itemRoot;