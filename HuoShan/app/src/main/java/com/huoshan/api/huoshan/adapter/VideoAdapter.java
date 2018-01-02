package com.huoshan.api.huoshan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.LiveBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.video.VideoFragment;
import com.huoshan.api.huoshan.video.VideoingActivity;

import java.util.List;

/**
 * Created by geting on 2018/1/1.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private List<VideoBeans.DataBeanX> list ;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener = null;
    public VideoAdapter( List<VideoBeans.DataBeanX> list , Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.video_adapter_item,parent,false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new VideoAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        VideoAdapter.MyViewHolder myViewHolder= (VideoAdapter.MyViewHolder) holder;
        VideoBeans.DataBeanX dataBeanX = list.get(position);
        String s = dataBeanX.getData().getAuthor().getAvatar_jpg().getUrl_list().get(0);
        Uri uri=Uri.parse(s);
        //将position保存在itemView的Tag中，以便点击时进行获取
        myViewHolder.itemView.setTag(position);
        myViewHolder.vi_item.setImageURI(uri);
    }
    //点击事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        SimpleDraweeView vi_item;
        public MyViewHolder(View itemView) {
            super(itemView);
            vi_item=itemView.findViewById(R.id.vi_item);
        }
    }
}
