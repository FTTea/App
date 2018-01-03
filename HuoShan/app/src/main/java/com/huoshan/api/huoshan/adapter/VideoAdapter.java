package com.huoshan.api.huoshan.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.huoshan.api.huoshan.Bean.LiveBean;
import com.huoshan.api.huoshan.Bean.VideoBeans;
import com.huoshan.api.huoshan.R;
import com.huoshan.api.huoshan.video.VideoFragment;
import com.huoshan.api.huoshan.video.VideoingActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by geting on 2018/1/1.
 */

public class VideoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    private List<VideoBeans.DataBeanX> list ;
    private Context context;
    private LayoutInflater inflater;
    private OnItemClickListener mOnItemClickListener = null;
    private int normalType = 0;     // 第一种ViewType，正常的item
    private int footType = 1;       // 第二种ViewType，底部的提示View

    private boolean hasMore = true;   // 变量，是否有更多数据
    private boolean fadeTips = false; // 变量，是否隐藏了底部的提示

    private Handler mHandler = new Handler(Looper.getMainLooper()); //获取主线程的Handler


    public VideoAdapter( List<VideoBeans.DataBeanX> list , Context context,boolean hasMore) {
        this.list = list;
        this.context = context;
        this.hasMore = hasMore;
        inflater=LayoutInflater.from(context);
    }
    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // 根据返回的ViewType，绑定不同的布局文件，这里只有两种
        if (viewType == normalType) {
            View view =inflater.inflate(R.layout.video_adapter_item, null);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return myViewHolder;
        } else {
            View view =inflater.inflate(R.layout.footview, null);
            FootHolder footHolder = new FootHolder(view);
            //将创建的View注册点击事件
            view.setOnClickListener(this);
            return footHolder;
        }
        //View view=inflater.inflate(R.layout.video_adapter_item,parent,false);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {


        if (holder instanceof MyViewHolder) {
            VideoAdapter.MyViewHolder myViewHolder = (VideoAdapter.MyViewHolder) holder;
            VideoBeans.DataBeanX dataBeanX = list.get(position);
            String s = dataBeanX.getData().getAuthor().getAvatar_jpg().getUrl_list().get(0);
            Uri uri = Uri.parse(s);
            //将position保存在itemView的Tag中，以便点击时进行获取
            myViewHolder.itemView.setTag(position);
            myViewHolder.vi_item.setImageURI(uri);

            //设置
            String s1 = dataBeanX.getData().getAuthor().getAvatar_jpg().getUrl_list().get(0);
            Uri uri1 = Uri.parse(s1);
            myViewHolder.vi_bt_img.setImageURI(uri1);
            String city = dataBeanX.getData().getAuthor().getCity();
            myViewHolder.location.setText(city);
            String ti = dataBeanX.getData().getTitle();
            String n = dataBeanX.getData().getAuthor().getNickname();
            myViewHolder.vi_bt_ti.setText(n);
            myViewHolder.vi_ti.setText(ti);
        } else {
            FootHolder footHolder = (FootHolder) holder;
            // 之所以要设置可见，是因为我在没有更多数据时会隐藏了这个footView
            ((FootHolder) holder).tips.setVisibility(View.VISIBLE);
            // 只有获取数据为空时，hasMore为false，所以当我们拉到底部时基本都会首先显示“正在加载更多...”
            if (hasMore == true) {
                // 不隐藏footView提示
                fadeTips = false;
                if (list.size() > 0) {
                    // 如果查询数据发现增加之后，就显示正在加载更多
                    ((FootHolder) holder).tips.setText("正在加载更多...");
                }
            } else {
                if (list.size() > 0) {
                    // 如果查询数据发现并没有增加时，就显示没有更多数据了
                    ((FootHolder) holder).tips.setText("没有更多数据了");

                    // 然后通过延时加载模拟网络请求的时间，在500ms后执行
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // 隐藏提示条
                            ((FootHolder) holder).tips.setVisibility(View.GONE);
                            // 将fadeTips设置true
                            fadeTips = true;
                            // hasMore设为true是为了让再次拉到底时，会先显示正在加载更多
                            hasMore = true;
                        }
                    }, 500);
                }
            }


        }

    }
    // 获取条目数量，之所以要加1是因为增加了一条footView

    // 自定义方法，获取列表中数据源的最后一个位置，比getItemCount少1，因为不计上footView
    public int getRealLastPosition() {
        return list.size();
    }
    // 根据条目位置返回ViewType，以供onCreateViewHolder方法内获取不同的Holder
    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return footType;
        } else {
            return normalType;
        }
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
    // 暴露接口，改变fadeTips的方法
    public boolean isFadeTips() {
        return fadeTips;
    }

    // 暴露接口，下拉刷新时，通过暴露方法将数据源置为空
    public void resetDatas() {
        list = new ArrayList<>();
    }

    // 暴露接口，更新数据源，并修改hasMore的值，如果有增加数据，hasMore为true，否则为false
    public void updateList(List<VideoBeans.DataBeanX> newDatas, boolean hasMore) {
        // 在原有的数据之上增加新数据
        if (newDatas != null) {
            list.addAll(newDatas);
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    class MyViewHolder extends  RecyclerView.ViewHolder{
        SimpleDraweeView vi_item,vi_bt_img;
        TextView location,vi_bt_ti,vi_ti;
        public MyViewHolder(View itemView) {
            super(itemView);
            vi_item=itemView.findViewById(R.id.vi_item);
            vi_bt_img=itemView.findViewById(R.id.vi_bt_img);
            location=itemView.findViewById(R.id.location);
            vi_bt_ti=itemView.findViewById(R.id.vi_bt_ti);
            vi_ti=itemView.findViewById(R.id.vi_ti);

        }
    }
    // // 底部footView的ViewHolder，用以缓存findView操作
    class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;

        public FootHolder(View itemView) {
            super(itemView);
            tips = (TextView) itemView.findViewById(R.id.tips);
        }
    }
}
