package com.groupB.foodoasis.Activity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.groupB.foodoasis.Adapters.StoreListingDBAdapter;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivityTest {

    private MainActivity mainActivityUnderTest;

    @Before
    public void setUp() {
        mainActivityUnderTest = new MainActivity();
        mainActivityUnderTest.supportMapFragment = mock(SupportMapFragment.class);
        mainActivityUnderTest.gMap = mock(GoogleMap.class);
        mainActivityUnderTest.placesClient = mock(PlacesClient.class);
        mainActivityUnderTest.fusedLocationProviderClient = mock(FusedLocationProviderClient.class);
        mainActivityUnderTest.currentLatitude = 0.0;
        mainActivityUnderTest.currentLongitude = 0.0;
        mainActivityUnderTest.rv_near_places_list = mock(RecyclerView.class);
        mainActivityUnderTest.nearByStoreUrl = "nearByStoreUrl";
        mainActivityUnderTest.addr_pincode = "addr_pincode";
        mainActivityUnderTest.nearByRadius = "nearByRadius";
        mainActivityUnderTest.placeType = "placeType";
        mainActivityUnderTest.nearLocationDetailsModelClassArrayList = new ArrayList<>(List.of(new NearLocationDetailsModelClass("name", "icon", "place_id", 0.0, 0.0, 0)));
        mainActivityUnderTest.et_addr_or_pincode = mock(EditText.class);
        mainActivityUnderTest.et_radius = mock(EditText.class);
        mainActivityUnderTest.btn_curr_location = mock(Button.class);
        mainActivityUnderTest.btn_search = mock(Button.class);
        mainActivityUnderTest.fab_favourite = mock(FloatingActionButton.class);
        mainActivityUnderTest.fab_next = mock(FloatingActionButton.class);
        mainActivityUnderTest.validation_successful = false;
        mainActivityUnderTest.btn_press = false;
        mainActivityUnderTest.til_addr_zipcode_err = mock(TextInputLayout.class);
        mainActivityUnderTest.til_radius_err = mock(TextInputLayout.class);
        mainActivityUnderTest.tiet_addr_zipcode = mock(TextInputEditText.class);
        mainActivityUnderTest.tiet_radius = mock(TextInputEditText.class);
        mainActivityUnderTest.db = mock(StoreListingDBAdapter.class);
    }

    @Test
    public void testGetLocationFromAddress() {
        // Setup

        // Run the test
        mainActivityUnderTest.getLocationFromAddress("strAddress");

        // Verify the results
        verify(mainActivityUnderTest.til_addr_zipcode_err).setError("errorText");
        verify(mainActivityUnderTest.supportMapFragment).getMapAsync(any(OnMapReadyCallback.class));
        verify(mainActivityUnderTest.gMap).animateCamera(any(CameraUpdate.class));
    }

    @Test
    public void testOnRequestPermissionsResult() {
        // Setup
        when(mainActivityUnderTest.fusedLocationProviderClient.getLastLocation()).thenReturn(null);

        // Run the test
        mainActivityUnderTest.onRequestPermissionsResult(0, new String[]{"value"}, new int[]{0});

        // Verify the results
        verify(mainActivityUnderTest.supportMapFragment).getMapAsync(any(OnMapReadyCallback.class));
        verify(mainActivityUnderTest.gMap).animateCamera(any(CameraUpdate.class));
    }

    @Test
    public void testDownloadUrl() throws Exception {
        assertEquals("result", mainActivityUnderTest.downloadUrl("string"));
        assertThrows(IOException.class, () -> mainActivityUnderTest.downloadUrl("string"));
    }

    @Test
    public void testOnCreate() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);
        when(mainActivityUnderTest.tiet_addr_zipcode.getText()).thenReturn(null);
        when(mainActivityUnderTest.tiet_radius.getText()).thenReturn(null);
        when(mainActivityUnderTest.fusedLocationProviderClient.getLastLocation()).thenReturn(null);

        // Run the test
        mainActivityUnderTest.onCreate(savedInstanceState);

        // Verify the results
        verify(mainActivityUnderTest.fab_next).setVisibility(0);
        verify(mainActivityUnderTest.btn_curr_location).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.til_addr_zipcode_err).setError("errorText");
        verify(mainActivityUnderTest.til_radius_err).setError("errorText");
        verify(mainActivityUnderTest.db).deleteStoreListing();
        verify(mainActivityUnderTest.supportMapFragment).getMapAsync(any(OnMapReadyCallback.class));
        verify(mainActivityUnderTest.gMap).animateCamera(any(CameraUpdate.class));
        verify(mainActivityUnderTest.btn_search).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_next).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_favourite).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testOnCreate_Tiet_addr_zipcodeReturnsNull() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);
        when(mainActivityUnderTest.tiet_addr_zipcode.getText()).thenReturn(null);
        when(mainActivityUnderTest.tiet_radius.getText()).thenReturn(null);
        when(mainActivityUnderTest.fusedLocationProviderClient.getLastLocation()).thenReturn(null);

        // Run the test
        mainActivityUnderTest.onCreate(savedInstanceState);

        // Verify the results
        verify(mainActivityUnderTest.fab_next).setVisibility(0);
        verify(mainActivityUnderTest.btn_curr_location).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.til_addr_zipcode_err).setError("errorText");
        verify(mainActivityUnderTest.til_radius_err).setError("errorText");
        verify(mainActivityUnderTest.db).deleteStoreListing();
        verify(mainActivityUnderTest.supportMapFragment).getMapAsync(any(OnMapReadyCallback.class));
        verify(mainActivityUnderTest.gMap).animateCamera(any(CameraUpdate.class));
        verify(mainActivityUnderTest.btn_search).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_next).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_favourite).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testOnCreate_Tiet_radiusReturnsNull() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);
        when(mainActivityUnderTest.tiet_addr_zipcode.getText()).thenReturn(null);
        when(mainActivityUnderTest.tiet_radius.getText()).thenReturn(null);
        when(mainActivityUnderTest.fusedLocationProviderClient.getLastLocation()).thenReturn(null);

        // Run the test
        mainActivityUnderTest.onCreate(savedInstanceState);

        // Verify the results
        verify(mainActivityUnderTest.fab_next).setVisibility(0);
        verify(mainActivityUnderTest.btn_curr_location).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.til_addr_zipcode_err).setError("errorText");
        verify(mainActivityUnderTest.til_radius_err).setError("errorText");
        verify(mainActivityUnderTest.db).deleteStoreListing();
        verify(mainActivityUnderTest.supportMapFragment).getMapAsync(any(OnMapReadyCallback.class));
        verify(mainActivityUnderTest.gMap).animateCamera(any(CameraUpdate.class));
        verify(mainActivityUnderTest.btn_search).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_next).setOnClickListener(any(View.OnClickListener.class));
        verify(mainActivityUnderTest.fab_favourite).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void testHideSoftKeyboard() {
        // Setup
        final Activity activity = new Activity();

        // Run the test
        MainActivity.hideSoftKeyboard(activity);

        // Verify the results
    }
}
