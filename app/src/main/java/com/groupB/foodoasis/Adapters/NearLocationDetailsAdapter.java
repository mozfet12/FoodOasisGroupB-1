package com.groupB.foodoasis.Adapters;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.R;

import java.util.ArrayList;

public class NearLocationDetailsAdapter extends RecyclerView.Adapter<NearLocationDetailsAdapter.Holder> {

    Context context;
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;
    FusedLocationProviderClient fusedLocationProviderClient;

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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

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
        holder.mv_dst.setText(lat);
        holder.mv_website.setText(lng);

        holder.mv_btn_add_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StoreListingDBAdapter db = new StoreListingDBAdapter(context);
                if (db.selectRecordFavouriteFromTable(place_id)) {
                    Toast.makeText(context, "Already added to Favourite.", Toast.LENGTH_SHORT).show();
//                    Log.e("Repeat ", "Already added.");
                } else {
                    nearLocationDetailsModelClass.setIs_favourite(1);
                    db.insertFavouriteInTable(nearLocationDetailsModelClass);
                    Toast.makeText(context, "Added to Favourite...", Toast.LENGTH_SHORT).show();
//                    Log.e("First time ", "Added to Favourite.");
                }
            }
        });
    }

    private String[] getLiveLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return null;
        }
        String[] latlng = new String[2];
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latlng[0] = location.getLatitude() + "";
                    latlng[1] = location.getLongitude() + "";
                }
            }
        });
        return latlng;
    }

    @Override
    public int getItemCount() {
        return nearLocationDetailsModelClassArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        //        TextView mv_tv_icon, mv_tv_name, mv_tv_place_id, mv_tv_latitude, mv_tv_longitude;
        TextView mv_tv_name, mv_dst, mv_website;
        Button mv_btn_add_fav, mv_btn_get_direction, mv_btn_call;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mv_tv_name = (TextView) itemView.findViewById(R.id.mv_tv_name);
            mv_dst = (TextView) itemView.findViewById(R.id.mv_dst);
            mv_website = (TextView) itemView.findViewById(R.id.mv_website);
            mv_btn_add_fav = (Button) itemView.findViewById(R.id.mv_btn_add_fav);
            mv_btn_get_direction = (Button) itemView.findViewById(R.id.mv_btn_get_direction);
            mv_btn_call = (Button) itemView.findViewById(R.id.mv_btn_call);
        }
    }
}
