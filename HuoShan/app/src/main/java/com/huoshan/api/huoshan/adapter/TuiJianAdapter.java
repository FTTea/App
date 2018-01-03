package com.huoshan.api.huoshan.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;

import java.util.List;

/**
 * Created by geting on 2018/1/2.
 */

public class TuiJianAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private List<VideoBeans.DataBeanX> list ;
    private Context context;
    private LayoutInflater inflater;
    private VideoAdapter.OnItemClickListener mOnItemClickListener = null;
    public TuiJianAdapter( List<VideoBeans.DataBeanX> list , Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }
    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.tuijian_item,parent,false);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final MyViewHolder myViewHolder= (MyViewHolder) holder;
        VideoBeans.DataBeanX dataBeanX = list.get(position);
        String s = dataBeanX.getData().getAuthor().getAvatar_jpg().getUrl_list().get(0);
        Uri uri=Uri.parse(s);
        //将position保存在itemView的Tag中，以便点击时进行获取
        myViewHolder.itemView.setTag(position);
        //设置图片
        myViewHolder.tui_img.setImageURI(uri);
        String nickname = dataBeanX.getData().getAuthor().getPay_grade().getName();
        myViewHolder.tui_name.setText(nickname);
        String grade_describe = dataBeanX.getData().getAuthor().getConstellation();
        myViewHolder.tui_desc.setText(grade_describe);
        myViewHolder.tui_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = myViewHolder.tui_btn.getText().toString();
                if("关注".equals(s1)){
                    myViewHolder.tui_btn.setText("已关注");
                }else{
                    myViewHolder.tui_btn.setText("关注");
                }
            }
        });

    }
    //点击事件
    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(VideoAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        SimpleDraweeView tui_img;
        TextView tui_name,tui_desc;
        Button tui_btn;
        public MyViewHolder(View itemView) {
            super(itemView);
            tui_img=itemView.findViewById(R.id.tui_img);
            tui_name=itemView.findViewById(R.id.tui_name);
            tui_desc=itemView.findViewById(R.id.tui_desc);
            tui_btn=itemView.findViewById(R.id.tui_btn);

        }
    }
}
