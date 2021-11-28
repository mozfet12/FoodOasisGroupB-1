package com.groupB.foodoasis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.groupB.foodoasis.Adapters.NearLocationDetailsAdapter;
import com.groupB.foodoasis.Adapters.StoreListingDBAdapter;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.R;

import java.util.ArrayList;

public class FavouriteLocationListing extends AppCompatActivity {

    RecyclerView rv_fav_store_listing;
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_location_listing);
        String name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);

        initializeVariables();

        StoreListingDBAdapter db = new StoreListingDBAdapter(FavouriteLocationListing.this);
        ArrayList<NearLocationDetailsModelClass> list = db.selectStoreFromTable();
//        Log.e("data from DB ", list.toString());

        for (int i = 0; i < list.size(); i++) {
            NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();

            if(list.get(i).getIs_favourite() == 1) {

            nearLocationDetailsModelClass.setName(list.get(i).getName());
            nearLocationDetailsModelClass.setIcon(list.get(i).getIcon());
            nearLocationDetailsModelClass.setPlace_id(list.get(i).getPlace_id());
            nearLocationDetailsModelClass.setLatitude(list.get(i).getLatitude());
            nearLocationDetailsModelClass.setLongitude(list.get(i).getLongitude());
            nearLocationDetailsModelClass.setIs_favourite(list.get(i).getIs_favourite());

            nearLocationDetailsModelClassArrayList.add(nearLocationDetailsModelClass);
            }
        }

        setNearLocationDeatilsAdapter();
    }

    private void setNearLocationDeatilsAdapter() {
        rv_fav_store_listing.setLayoutManager(new LinearLayoutManager(FavouriteLocationListing.this));
        NearLocationDetailsAdapter nearLocationDetailsAdapter = new NearLocationDetailsAdapter(FavouriteLocationListing.this, nearLocationDetailsModelClassArrayList);
        rv_fav_store_listing.setAdapter(nearLocationDetailsAdapter);
    }

    private void initializeVariables() {
        rv_fav_store_listing = (RecyclerView) findViewById(R.id.rv_fav_store_listing);
        nearLocationDetailsModelClassArrayList = new ArrayList<NearLocationDetailsModelClass>();
    }
}