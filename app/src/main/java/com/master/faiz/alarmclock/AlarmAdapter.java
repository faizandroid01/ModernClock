package com.master.faiz.alarmclock;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by faiz on 24/8/17.
 */

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>{


    private LayoutInflater inflater;
    List<Information> data= Collections.emptyList();
    public AlarmAdapter(Context context,List<Information> data)
    {
     inflater=  LayoutInflater.from(context);
        this.data=data;
    }

    @Override
    public AlarmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.alarm_custom_row,parent,false);
        AlarmViewHolder holder=new AlarmViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(AlarmViewHolder holder, int position) {

        Information current=data.get(position);
        holder.time.setText(current.timeId);
        holder.occurrence.setText(current.occurenceId);
        holder.title.setText(current.titleId);


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class AlarmViewHolder extends RecyclerView.ViewHolder {

        TextView time;
        TextView occurrence;
        TextView title;

        public AlarmViewHolder(View itemView) {
            super(itemView);
            time= (TextView) itemView.findViewById(R.id.alarm_recycler_view_time);
            occurrence= (TextView) itemView.findViewById(R.id.alarm_recycler_view_occurence);
            title= (TextView) itemView.findViewById(R.id.alarm_recycler_view_title);

        }
    }
}


