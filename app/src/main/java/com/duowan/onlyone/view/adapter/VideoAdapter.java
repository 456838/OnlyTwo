package com.duowan.onlyone.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.duowan.onlyone.R;
import com.duowan.onlyone.model.entity.kaiyanBean.DataBean;
import com.duowan.onlyone.model.entity.kaiyanBean.ItemListBean;
import com.duowan.onlyone.model.entity.utils.DateUtil;
import com.facebook.drawee.view.SimpleDraweeView;
import com.salton123.base.BaseRecycerViewAdapter;
import com.salton123.common.image.FrescoImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class VideoAdapter extends BaseRecycerViewAdapter<ItemListBean, RecyclerView.ViewHolder> {

    public List<Bitmap> mBitmap;
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(5);

    public VideoAdapter(Context context, List<ItemListBean> list) {
        super(context, list);
        mBitmap = new ArrayList<>();
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("video")) {
//            if (position == 0) {
//                return R.layout.top_item;
//            }
            return R.layout.video_item;
        } else {
            return R.layout.null_item;
        }
    }

    @Override
    public RecyclerView.ViewHolder getCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == R.layout.video_item) {
            return new MyHolder(inflater.inflate(viewType, parent, false));
        }
        return new TopHolder(inflater.inflate(viewType, parent, false));
    }

    @Override
    public void getBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemListBean itemListBean = list.get(position);
        if (holder instanceof MyHolder) {
            final MyHolder myHolder = (MyHolder) holder;
            ViewCompat.setTransitionName(myHolder.imageView, String.valueOf(position) + "_image");
            DataBean data = itemListBean.getData();
            FrescoImageLoader.display(myHolder.imageView, data.getCover().getDetail());
            myHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick(position, v, myHolder);
                    }
                }
            });


            myHolder.textView.setText(data.getTitle());
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("#").append(data.getCategory())
                    .append(" ")
                    .append(" / ")
                    .append(" ")
                    .append(DateUtil.formatTime2(data.getDuration()));
            myHolder.description.setText(stringBuilder.toString());
        }
    }


    public static class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public TextView description;
        public SimpleDraweeView imageView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.title));
            description = ((TextView) itemView.findViewById(R.id.description));
            imageView = ((SimpleDraweeView) itemView.findViewById(R.id.img));
        }
    }


    public static class TopHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public TopHolder(View itemView) {
            super(itemView);
            textView = ((TextView) itemView.findViewById(R.id.name));
        }
    }

}
