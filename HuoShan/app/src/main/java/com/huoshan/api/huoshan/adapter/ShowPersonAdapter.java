package com.huoshan.api.huoshan.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.huoshan.api.huoshan.R;

import java.util.List;

/**
 * Created by geting on 2018/1/2.
 */

public class ShowPersonAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;

    public ShowPersonAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.person_item,parent,false);
        MyViewHoler myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
         MyViewHoler myViewHoler = (MyViewHoler) holder;
        String s = list.get(position);
         myViewHoler.name.setText(s);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHoler extends RecyclerView.ViewHolder{
      private TextView name;
        public MyViewHoler(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
        }
    }
}
