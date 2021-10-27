package com.groupB.foodoasis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.R;

import java.util.ArrayList;

public class NearLocationDetailsAdapter extends RecyclerView.Adapter<NearLocationDetailsAdapter.Holder> {

    Context context;
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;

    public NearLocationDetailsAdapter(Context context, ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList) {
        this.context = context;
        this.nearLocationDetailsModelClassArrayList = nearLocationDetailsModelClassArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.nearlocationdetailsmodelview, parent, false);

        return new NearLocationDetailsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.mv_tv_icon.setText(nearLocationDetailsModelClassArrayList.get(position).getIcon());
        holder.mv_tv_name.setText(nearLocationDetailsModelClassArrayList.get(position).getName());
        holder.mv_tv_place_id.setText(nearLocationDetailsModelClassArrayList.get(position).getPlace_id());
        holder.mv_tv_latitude.setText(nearLocationDetailsModelClassArrayList.get(position).getIcon());
        holder.mv_tv_longitude.setText(nearLocationDetailsModelClassArrayList.get(position).getIcon());
    }

    @Override
    public int getItemCount() {
        return nearLocationDetailsModelClassArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView mv_tv_icon, mv_tv_name, mv_tv_place_id, mv_tv_latitude, mv_tv_longitude;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mv_tv_icon = (TextView) itemView.findViewById(R.id.mv_tv_icon);
            mv_tv_name = (TextView) itemView.findViewById(R.id.mv_tv_name);
            mv_tv_place_id = (TextView) itemView.findViewById(R.id.mv_tv_place_id);
            mv_tv_latitude = (TextView) itemView.findViewById(R.id.mv_tv_latitude);
            mv_tv_longitude = (TextView) itemView.findViewById(R.id.mv_tv_longitude);
        }
    }
}
