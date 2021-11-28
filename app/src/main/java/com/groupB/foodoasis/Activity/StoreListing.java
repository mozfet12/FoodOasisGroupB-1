package com.groupB.foodoasis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.groupB.foodoasis.Adapters.NearLocationDetailsAdapter;
import com.groupB.foodoasis.Adapters.StoreListingDBAdapter;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.R;

import java.util.ArrayList;

public class StoreListing extends AppCompatActivity {

    RecyclerView rv_store_listing;
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_listing);
        String name = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(name);

        initializeVariables();

        StoreListingDBAdapter db = new StoreListingDBAdapter(StoreListing.this);
        ArrayList<NearLocationDetailsModelClass> list = db.selectStoreFromTable();
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

        setNearLocationDeatilsAdapter();
    }

    private void setNearLocationDeatilsAdapter() {
        rv_store_listing.setLayoutManager(new LinearLayoutManager(StoreListing.this));
        NearLocationDetailsAdapter nearLocationDetailsAdapter = new NearLocationDetailsAdapter(StoreListing.this, nearLocationDetailsModelClassArrayList);
        rv_store_listing.setAdapter(nearLocationDetailsAdapter);
    }

    private void initializeVariables() {
        rv_store_listing = (RecyclerView) findViewById(R.id.rv_store_listing);
        nearLocationDetailsModelClassArrayList = new ArrayList<NearLocationDetailsModelClass>();
        StoreListingDBAdapter db = new StoreListingDBAdapter(StoreListing.this);
    }
}