package com.groupB.foodoasis.Adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.groupB.foodoasis.Activity.StoreListing;
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

        String icon = nearLocationDetailsModelClassArrayList.get(position).getIcon();
        String place_name = nearLocationDetailsModelClassArrayList.get(position).getName();
        String place_id = nearLocationDetailsModelClassArrayList.get(position).getPlace_id();
        String lat = nearLocationDetailsModelClassArrayList.get(position).getLatitude() + "";
        String lng = nearLocationDetailsModelClassArrayList.get(position).getLongitude() + "";
        int is_favourite = nearLocationDetailsModelClassArrayList.get(position).getIs_favourite();
        if(is_favourite == 1){
//            holder.mv_btn_add_fav.setVisibility(View.INVISIBLE);
            holder.mv_btn_add_fav.setText("Added to Favourite");
            holder.mv_btn_add_fav.setEnabled(false);
            holder.mv_btn_rmv_fav.setText("Remove from Favourite");
            holder.mv_btn_rmv_fav.setEnabled(true);
        }else {
            holder.mv_btn_add_fav.setText("Add to Favourite");
            holder.mv_btn_add_fav.setEnabled(true);
            holder.mv_btn_rmv_fav.setText("Removed from Favourite");
            holder.mv_btn_rmv_fav.setEnabled(false);
        }

        holder.mv_tv_icon.setText(icon);
        holder.mv_tv_name.setText(place_name);
        holder.mv_tv_place_id.setText(place_id);
        holder.mv_tv_latitude.setText(lat);
        holder.mv_tv_longitude.setText(lng);

        holder.mv_btn_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context,"Clicked on add to fav",Toast.LENGTH_SHORT);
//                Log.e("clicked ","on add to fav");
                StoreListingDBAdapter db = new StoreListingDBAdapter(context);
                db.updateStoreListing("is_favourite",1,place_id);
//                db.insertFavouriteInTable(nearLocationDetailsModelClass);
            }
        });

        holder.mv_btn_rmv_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreListingDBAdapter db = new StoreListingDBAdapter(context);
                db.updateStoreListing("is_favourite",0,place_id);
            }
        });
    }

    @Override
    public int getItemCount() {
        return nearLocationDetailsModelClassArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView mv_tv_icon, mv_tv_name, mv_tv_place_id, mv_tv_latitude, mv_tv_longitude;
        Button mv_btn_add_fav, mv_btn_rmv_fav;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mv_tv_icon = (TextView) itemView.findViewById(R.id.mv_tv_icon);
            mv_tv_name = (TextView) itemView.findViewById(R.id.mv_tv_name);
            mv_tv_place_id = (TextView) itemView.findViewById(R.id.mv_tv_place_id);
            mv_tv_latitude = (TextView) itemView.findViewById(R.id.mv_tv_latitude);
            mv_tv_longitude = (TextView) itemView.findViewById(R.id.mv_tv_longitude);
            mv_btn_add_fav = (Button) itemView.findViewById(R.id.mv_btn_add_fav);
            mv_btn_rmv_fav = (Button) itemView.findViewById(R.id.mv_btn_rmv_fav);
        }
    }
}
