package com.huoshan.api.huoshan.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


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
                String rtmp_pull_url = dataBeanX.getData().getStream_url().getRtmp_pull_url();
                String nickname = dataBeanX.getData().getOwner().getNickname();
                String avatar = dataBeanX.getData().getOwner().getAvatar_jpg().getUrl_list().get(0);
                //数据存储
                SharedPreferences sp = context.getSharedPreferences("owner", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("name",nickname);
                edit.putString("avatar",avatar);
                edit.commit();
                //跳转传值
                Intent intent=new Intent(context, LiveActivity.class);
                intent.putExtra("stream_addr",rtmp_pull_url);
                context.startActivity(intent);
            }
        });
        myViewHolder.title.setText(dataBeanX.getData().getTitle());
        myViewHolder.city.setText(dataBeanX.getData().getOwner().getCity());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends  RecyclerView.ViewHolder{

        TextView title;
        TextView city;
        SimpleDraweeView image;
        public MyViewHolder(View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.la_fresco);
            title=itemView.findViewById(R.id.la_title);
            city=itemView.findViewById(R.id.la_city);
        }
    }
}
