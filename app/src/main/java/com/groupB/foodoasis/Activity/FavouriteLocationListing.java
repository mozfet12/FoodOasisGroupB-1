package com.groupB.foodoasis.Activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.groupB.foodoasis.Adapters.FavouriteLocationDetailsAdapter;
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
        ArrayList<NearLocationDetailsModelClass> list = db.selectFavouriteFromTable();
//        Log.e("data from DB ", list.toString());

        for (int i = 0; i < list.size(); i++) {
            NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();

            nearLocationDetailsModelClass.setName(list.get(i).getName());
            nearLocationDetailsModelClass.setIcon(list.get(i).getIcon());
            nearLocationDetailsModelClass.setPlace_id(list.get(i).getPlace_id());
            nearLocationDetailsModelClass.setLatitude(list.get(i).getLatitude());
            nearLocationDetailsModelClass.setLongitude(list.get(i).getLongitude());
            nearLocationDetailsModelClass.setIs_favourite(list.get(i).getIs_favourite());

            nearLocationDetailsModelClassArrayList.add(nearLocationDetailsModelClass);
        }

        setFavouriteLocationDeatilsAdapter();
    }

    private void setFavouriteLocationDeatilsAdapter() {
        rv_fav_store_listing.setLayoutManager(new LinearLayoutManager(FavouriteLocationListing.this));
        FavouriteLocationDetailsAdapter favouriteLocationDetailsAdapter = new FavouriteLocationDetailsAdapter(FavouriteLocationListing.this, nearLocationDetailsModelClassArrayList);
        rv_fav_store_listing.setAdapter(favouriteLocationDetailsAdapter);
    }

    private void initializeVariables() {
        rv_fav_store_listing = (RecyclerView) findViewById(R.id.rv_fav_store_listing);
        nearLocationDetailsModelClassArrayList = new ArrayList<NearLocationDetailsModelClass>();
    }
}