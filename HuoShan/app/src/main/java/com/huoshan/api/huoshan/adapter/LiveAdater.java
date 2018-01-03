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
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.live.LiveActivity;

import java.util.List;

/**
 * Created by lenovo on 2017/12/29.
 */

public class LiveAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<LiveBean.DataBeanX> list;
    private Context context;
    private LayoutInflater inflater;

    public LiveAdater(List<LiveBean.DataBeanX> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.live_adapter_item,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       MyViewHolder myViewHolder= (MyViewHolder) holder;
        final LiveBean.DataBeanX dataBeanX = list.get(position);
        String s = dataBeanX.getData().getOwner().getAvatar_large().getUrl_list().get(0);
        Uri uri=Uri.parse(s);
        myViewHolder.image.setImageURI(uri);
        myViewHolder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, LiveActivity.class);
                String rtmp_pull_url = dataBeanX.getData().getStream_url().getRtmp_pull_url();
                intent.putExtra("stream_addr",rtmp_pull_url);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder{
        SimpleDraweeView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.la_fresco);
        }
    }
}