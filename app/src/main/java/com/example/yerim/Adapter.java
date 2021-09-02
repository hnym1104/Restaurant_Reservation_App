package com.example.yerim;
;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {   // restaurant list adapter for recylerview
    Activity activity;
    private List<restaurant> arrayList;
    String userID, userName;

    public Adapter(Activity activity, List<restaurant> arrayList, String userID, String userName) {
        this.arrayList = arrayList;
        this.activity = activity;
        this.userID = userID;
        this.userName = userName;   // initialize value
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {   // create viewh holder
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.res_list, parent, false);
        ViewHolder viewholder = new ViewHolder(context, view, userID, userName);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        restaurant data = arrayList.get(position);
        holder.resName.setText(data.getResName());
        holder.resLocation.setText(data.getResLocation());
        holder.resKind.setText(data.getResKind());
        holder.resPrice.setText(data.getResPrice());
        holder.resWho.setText(data.getResWho());
        holder.resHost.setText(data.getResHost());   // match each item with view holder
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }   // return array size

}
