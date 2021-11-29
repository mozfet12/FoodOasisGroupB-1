package com.groupB.foodoasis.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.groupB.foodoasis.Adapters.NearLocationDetailsAdapter;
import com.groupB.foodoasis.Adapters.StoreListingDBAdapter;
import com.groupB.foodoasis.Classes.NearLocatedPlacesFromGoogleMap;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;
import com.groupB.foodoasis.Classes.USDADatabase;
import com.groupB.foodoasis.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // create variables
    SupportMapFragment supportMapFragment;
    GoogleMap gMap;
    PlacesClient placesClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    double currentLatitude = 0, currentLongitude = 0;
    RecyclerView rv_near_places_list;
    String nearByStoreUrl, addr_pincode = "", nearByRadius = "";
    String placeType = "farmersmarkets";
    ArrayList<NearLocationDetailsModelClass> nearLocationDetailsModelClassArrayList;
    EditText et_addr_or_pincode, et_radius;
    Button btn_curr_location, btn_search;
    FloatingActionButton fab_favourite, fab_next;
    boolean validation_successful = false;
    TextInputLayout til_addr_zipcode_err, til_radius_err;
    TextInputEditText tiet_addr_zipcode, tiet_radius;
    StoreListingDBAdapter db;

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isAcceptingText()) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeVariables();
        onClickEventsOfButtons();

        //checking permission is granted or not
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLiveLocation();
        } else {
            //requesting the permission from the user
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void onClickEventsOfButtons() {
        btn_curr_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                til_addr_zipcode_err.setError(null);
                til_radius_err.setError(null);
                db = new StoreListingDBAdapter(MainActivity.this);
                db.deleteStoreListing();
                fab_next.setVisibility(View.VISIBLE);
                getEditTextData();
                hideSoftKeyboard(MainActivity.this);
                getLiveLocation();
            }
        });

        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new StoreListingDBAdapter(MainActivity.this);
                db.deleteStoreListing();
                getEditTextData();
                til_addr_zipcode_err.setError(null);
                til_radius_err.setError(null);
                if (validateDetailsFromUser()) {
                    fab_next.setVisibility(View.VISIBLE);
                    getLocationFromAddress(addr_pincode);
                    hideSoftKeyboard(MainActivity.this);
//                    Log.e("lat-lng: ", currentLatitude + " : " + currentLongitude + "");
                }


            }
        });

        fab_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StoreListing.class);
                intent.putExtra("name", "Store List");
                startActivity(intent);
            }
        });

        fab_favourite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavouriteLocationListing.class);
                intent.putExtra("name", "Favourite Store List");
                startActivity(intent);
            }
        });
    }

    private boolean validateDetailsFromUser() {
        if (addr_pincode.length() == 0) {
            til_addr_zipcode_err.setError("Please enter the valid address");
            return false;
        }
        til_addr_zipcode_err.setError(null);
        return true;
    }

    public void getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null || address.size() == 0) {
                til_addr_zipcode_err.setError("Please enter the valid address");
                return;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            LatLng latlng = new LatLng(location.getLatitude(), location.getLongitude());

            currentLatitude = latlng.latitude;
            currentLongitude = latlng.longitude;
//            Log.e("lat: ",currentLatitude+"");
//            Log.e("lng: ",currentLongitude+"");


            supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(@NonNull GoogleMap googleMap) {
                    gMap = googleMap;
                    gMap.animateCamera(CameraUpdateFactory
                            .newLatLngZoom(
                                    new LatLng(
                                            currentLatitude, currentLongitude), 12));
                }
            });


            nearByStoreUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                    currentLatitude + "," + currentLongitude +
                    "&radius=" + nearByRadius +
                    "&type=" + placeType +
                    "&key=" + getResources().getString(R.string.google_map_key);

            new PlaceTask().execute(nearByStoreUrl);
//            new USDADatabase().execute(currentLatitude+"",currentLongitude+"");

        } catch (IOException e) {
            validateDetailsFromUser();
            e.printStackTrace();
        }
    }

    private void getEditTextData() {
        addr_pincode = tiet_addr_zipcode.getText().toString().trim();
        String r = tiet_radius.getText().toString().trim();
        int radius;
        if (r.length() == 0) {
            r = "5";
            radius = Integer.parseInt(r);
        } else {
            radius = Integer.parseInt(r);
        }
        radius *= 1609;
        nearByRadius = radius + "";
    }

    private void initializeVariables() {
        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(MainActivity.this);
        rv_near_places_list = (RecyclerView) findViewById(R.id.rv_near_places_list);
        Places.initialize(MainActivity.this, String.valueOf(R.string.google_map_key));
        placesClient = Places.createClient(MainActivity.this);
        nearLocationDetailsModelClassArrayList = new ArrayList<NearLocationDetailsModelClass>();
//        et_addr_or_pincode = findViewById(R.id.et_addr_or_pincode);
//        et_radius = findViewById(R.id.et_radius);
        til_addr_zipcode_err = findViewById(R.id.til_addr_zipcode_err);
        til_radius_err = findViewById(R.id.til_radius_err);
        tiet_addr_zipcode = findViewById(R.id.tiet_addr_zipcode);
        tiet_radius = findViewById(R.id.tiet_radius);
        btn_curr_location = findViewById(R.id.btn_curr_location);
        btn_search = findViewById(R.id.btn_search);
        fab_next = findViewById(R.id.fab_next);
        fab_next.setVisibility(View.GONE);
        fab_favourite = findViewById(R.id.fab_favourite);
        nearByStoreUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                currentLatitude + "," + currentLongitude +
                "&key=" + getResources().getString(R.string.google_map_key);
        db = new StoreListingDBAdapter(MainActivity.this);

    }

    private void getLiveLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLatitude = location.getLatitude();
                    currentLongitude = location.getLongitude();

                    //link the map with the fragment
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(@NonNull GoogleMap googleMap) {
                            gMap = googleMap;
                            gMap.animateCamera(CameraUpdateFactory
                                    .newLatLngZoom(
                                            new LatLng(
                                                    currentLatitude, currentLongitude), 12));
                        }
                    });
                }


                nearByStoreUrl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=" +
                        currentLatitude + "," + currentLongitude +
                        "&radius=" + nearByRadius +
                        "&type=" + placeType +
                        "&key=" + getResources().getString(R.string.google_map_key);

                new PlaceTask().execute(nearByStoreUrl);

            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 44) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLiveLocation();
            }
        }
    }

    //download the jsonArray `result` from the given API(one record-wise)
    public String downloadUrl(String string) throws IOException {
        URL url = new URL(string);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.connect();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder stringBuilder = new StringBuilder();
        String line = "";

        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
//            Log.e("line in buffer read: ",line.toString());
        }

        String data = stringBuilder.toString();
        bufferedReader.close();

        return data;
    }

    private void setNearLocationDeatilsAdapter() {
        rv_near_places_list.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        NearLocationDetailsAdapter nearLocationDetailsAdapter = new NearLocationDetailsAdapter(MainActivity.this, nearLocationDetailsModelClassArrayList);
        rv_near_places_list.setAdapter(nearLocationDetailsAdapter);
    }

    //perform task to get the data from API.
    public class PlaceTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            String data = null;
            try {
                data = downloadUrl(strings[0]);
//                Log.e("data received: ", data);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(String s) {
            //executing parser task: to store data in hashmap format
//            Log.e("string ",s);
            new ParserTask().execute(s);
        }
    }

    public class ParserTask extends AsyncTask<String, Integer, List<HashMap<String, String>>> {

        @Override
        protected List<HashMap<String, String>> doInBackground(String... strings) {
            NearLocatedPlacesFromGoogleMap nearLocatedPlacesFromGoogleMap = new NearLocatedPlacesFromGoogleMap();
            List<HashMap<String, String>> mapList = null;
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(strings[0]);
                mapList = nearLocatedPlacesFromGoogleMap.parseResult(jsonObject);
//                Log.e("mapList of json parser:", mapList.toString());
//                Log.e("json object: ", jsonObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return mapList;
        }

        @Override
        protected void onPostExecute(List<HashMap<String, String>> hashMaps) {
            gMap.clear();

            //after getting nearer stores show them on google map
            for (int i = 0; i < hashMaps.size(); i++) {
                HashMap<String, String> hashMap = hashMaps.get(i);

                //get details for the location
                String name = hashMap.get("name");
                String icon = hashMap.get("icon");
                String place_id = hashMap.get("place_id");
                double lat = Double.parseDouble(hashMap.get("lat"));
                double lng = Double.parseDouble(hashMap.get("lng"));

                //join lat and lng
                LatLng latLng = new LatLng(lat, lng);
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(name);
                gMap.addMarker(markerOptions);
//                Log.e("name: ", name);
//                Log.e("icon", icon);
//                Log.e("place_id", place_id);
//                Log.e("latitude: ", lat + "");
//                Log.e("longitude: ", lng + "");


                //add details in model class
                NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();
                nearLocationDetailsModelClass.setName(name);
                nearLocationDetailsModelClass.setIcon(icon);
                nearLocationDetailsModelClass.setPlace_id(place_id);
                nearLocationDetailsModelClass.setLatitude(lat);
                nearLocationDetailsModelClass.setLongitude(lng);


//                List<Place.Field> placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.WEBSITE_URI, Place.Field.PHONE_NUMBER, Place.Field.PRICE_LEVEL);
//                FetchPlaceRequest request = FetchPlaceRequest.newInstance(place_id, placeFields);
//                placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//                    Log.e("status","worked till now");
//                    Place place = response.getPlace();
//                    nearLocationDetailsModelClass.setIcon(place.getWebsiteUri().toString());
//                    nearLocationDetailsModelClass.setName(place.getPhoneNumber());
//                    Log.e("phone number ",place.getPhoneNumber());
//                });

//                nearLocationDetailsModelClassArrayList.add(nearLocationDetailsModelClass);

                //add data into the SQLite db
                db.insertStoreInTable(nearLocationDetailsModelClass);

            }
            new USDADatabase().execute(currentLatitude + "", currentLongitude + "");
//            set the adapter
//            setNearLocationDeatilsAdapter();
        }
    }
}