package com.duowan.onlyone.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duowan.onlyone.R;
import com.duowan.onlyone.model.entity.yinyuetai.VideoBean;

import org.xutils.x;

import java.util.ArrayList;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;


/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/5/11
 * YinYueTai
 */
public class HotMusicItemAdapter extends RecyclerView.Adapter<HotMusicItemAdapter.ViewHolder> {

    private ArrayList<VideoBean> videoList = new ArrayList<>();

    public HotMusicItemAdapter(ArrayList<VideoBean> videoList) {
        this.videoList = videoList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_item_hot_music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final VideoBean videoBean = videoList.get(position);
//        x.image().bind(holder.videoplayer.coverImageView, videoBean.getThumbnailPic());
//        holder.videoplayer.titleTextView.setText(videoBean.getTitle());
        if (TextUtils.isEmpty(videoBean.getShdUrl())) {
            if (TextUtils.isEmpty(videoBean.getUhdUrl())) {
                if (TextUtils.isEmpty(videoBean.getHdUrl())) {
                    holder.videoplayer.setUp(videoBean.getUrl()
                            , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
                } else {
                    holder.videoplayer.setUp(videoBean.getHdUrl()
                            , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
                }
            } else {
                holder.videoplayer.setUp(videoBean.getUhdUrl()
                        , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
            }
        } else {
            holder.videoplayer.setUp(videoBean.getShdUrl()
                    , JCVideoPlayer.SCREEN_LAYOUT_LIST, videoBean.getTitle());
        }
        x.image().bind(holder.videoplayer.thumbImageView, videoBean.getAlbumImg());
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        JCVideoPlayerStandard videoplayer;

        public ViewHolder(View view) {
            super(view);
            videoplayer = (JCVideoPlayerStandard) view.findViewById(R.id.videoplayer);
        }
    }
}
