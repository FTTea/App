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
import com.huoshan.api.huoshan.Bean.SearchBean;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.owner.UserOwnerActivity;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.List;

/**
 * Created by lenovo on 2018/1/6.
 */

public class RecommendUserAdatpter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<SearchBean.DataBean.RecommendUserBean> list;
    private Context context;
    private LayoutInflater inflater;

    public RecommendUserAdatpter(List<SearchBean.DataBean.RecommendUserBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.recommenduser_item,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyViewHolder myholder= (MyViewHolder) holder;
        final SearchBean.DataBean.RecommendUserBean bean = list.get(position);
        myholder.avatar.setImageURI(Uri.parse(bean.getUser().getAvatar_jpg().getUrl_list().get(0)));
        myholder.nick.setText(bean.getUser().getNickname());
        myholder.title.setText(bean.getDescription());

        //封面照片
        myholder.imag1.setImageURI(Uri.parse(bean.getItems().get(0).getVideo().getCover().getUrl_list().get(0)));
        myholder.imag2.setImageURI(Uri.parse(bean.getItems().get(1).getVideo().getCover().getUrl_list().get(0)));
        myholder.imag3.setImageURI(Uri.parse(bean.getItems().get(2).getVideo().getCover().getUrl_list().get(0)));

        myholder.all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //数据存储
                SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("nick",bean.getUser().getNickname());
                edit.putString("title",bean.getDescription());
                edit.putString("avatar",bean.getUser().getAvatar_jpg().getUrl_list().get(0));
                edit.putString("city",bean.getUser().getCity());
                edit.putString("birthday",bean.getUser().getBirthday_description());

                edit.commit();
                //跳转
                Intent intent=new Intent(context, UserOwnerActivity.class);

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView avatar;
        TextView nick;
        TextView title;
        SimpleDraweeView imag1;
        SimpleDraweeView imag2;
        SimpleDraweeView imag3;
        AutoLinearLayout all;
        public MyViewHolder(View itemView) {
            super(itemView);
            avatar=itemView.findViewById(R.id.ri_avatar);
            nick=itemView.findViewById(R.id.ri_nick);
            title=itemView.findViewById(R.id.ri_title);
            imag1=itemView.findViewById(R.id.ri_img1);
            imag2=itemView.findViewById(R.id.ri_img2);
            imag3=itemView.findViewById(R.id.ri_img3);
            all=itemView.findViewById(R.id.ri_all);
        }
    }
}
