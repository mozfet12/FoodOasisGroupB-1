package com.groupB.foodoasis.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.R;

import java.util.ArrayList;

public class FavouriteLocationDetailsAdapter extends RecyclerView.Adapter<FavouriteLocationDetailsAdapter.Holder> {

    Context context;
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;

    public FavouriteLocationDetailsAdapter(Context context, ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList) {
        this.context = context;
        this.nearLocationDetailsModelClassArrayList = nearLocationDetailsModelClassArrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.favouritemodelview, parent, false);

        return new FavouriteLocationDetailsAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

        String icon = nearLocationDetailsModelClassArrayList.get(position).getIcon();
        String place_name = nearLocationDetailsModelClassArrayList.get(position).getName();
        String place_id = nearLocationDetailsModelClassArrayList.get(position).getPlace_id();
        String lat = nearLocationDetailsModelClassArrayList.get(position).getLatitude() + "";
        String lng = nearLocationDetailsModelClassArrayList.get(position).getLongitude() + "";
        int is_favourite = nearLocationDetailsModelClassArrayList.get(position).getIs_favourite();

        NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();
        nearLocationDetailsModelClass.setPlace_id(place_id);
        nearLocationDetailsModelClass.setName(place_name);
        nearLocationDetailsModelClass.setIcon(icon);
        nearLocationDetailsModelClass.setLatitude(Double.parseDouble(lat));
        nearLocationDetailsModelClass.setLongitude(Double.parseDouble(lng));
        nearLocationDetailsModelClass.setIs_favourite(is_favourite);

        holder.mv_tv_name.setText(place_name);
        holder.mv_dst.setText(lat + " mile(s)");
        holder.mv_website.setText(icon);
//        holder.mv_tv_icon.setText(icon);
//        holder.mv_tv_place_id.setText(place_id);
//        holder.mv_tv_latitude.setText(lat);
//        holder.mv_tv_longitude.setText(lng);

        holder.mv_btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreListingDBAdapter db = new StoreListingDBAdapter(context);
                nearLocationDetailsModelClass.setIs_favourite(0);
                nearLocationDetailsModelClassArrayList.remove(holder.getAdapterPosition());
                db.deleteFavouriteListing(place_id);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearLocationDetailsModelClassArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        //        TextView mv_tv_icon, mv_tv_name, mv_tv_place_id, mv_tv_latitude, mv_tv_longitude;
        TextView mv_tv_name, mv_dst, mv_website;
        Button mv_btn_delete, mv_btn_get_direction, mv_btn_call;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mv_tv_name = (TextView) itemView.findViewById(R.id.mv_tv_name);
            mv_dst = (TextView) itemView.findViewById(R.id.mv_dst);
            mv_website = (TextView) itemView.findViewById(R.id.mv_website);
//            mv_tv_icon = (TextView) itemView.findViewById(R.id.mv_tv_icon);
//            mv_tv_place_id = (TextView) itemView.findViewById(R.id.mv_tv_place_id);
//            mv_tv_latitude = (TextView) itemView.findViewById(R.id.mv_tv_latitude);
//            mv_tv_longitude = (TextView) itemView.findViewById(R.id.mv_tv_longitude);
            mv_btn_delete = (Button) itemView.findViewById(R.id.mv_btn_delete);
            mv_btn_get_direction = (Button) itemView.findViewById(R.id.mv_btn_get_direction);
            mv_btn_call = (Button) itemView.findViewById(R.id.mv_btn_call);
        }
    }
}
