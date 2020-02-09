package com.example.gpstest;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.CustomViewHolder> {

protected ArrayList<GetGpsData> mList = null;
protected Activity context = null;


public UsersAdapter(Activity context, ArrayList<GetGpsData> list) {
        this.context = context;
        this.mList = list;
        }

class CustomViewHolder extends RecyclerView.ViewHolder {
    protected TextView latitude;
    protected TextView longtitude;


    public CustomViewHolder(View view) {
        super(view);
        this.latitude = (TextView) view.findViewById(R.id.textView_list_latitude);
        this.longtitude = (TextView) view.findViewById(R.id.textView_list_longtitude);

    }
}


    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }

    //recycleview에 위도,경도 불러온 값 확인 하는 코드
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        //viewholder.latitude.setText(mList.get(position).getMember_latitude());
       // viewholder.longtitude.setText(mList.get(position).getMember_longtitude());

    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

}
