package com.groupB.foodoasis.Activity;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.os.Bundle;

import androidx.recyclerview.widget.RecyclerView;

import com.groupB.foodoasis.Adapters.NearLocationDetailsAdapter;
import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class StoreListingTest {

    private StoreListing storeListingUnderTest;

    @Before
    public void setUp() {
        storeListingUnderTest = new StoreListing();
        storeListingUnderTest.rv_store_listing = mock(RecyclerView.class);
        storeListingUnderTest.nearLocationDetailsModelClassArrayList = new ArrayList<>(List.of(new NearLocationDetailsModelClass("name", "icon", "place_id", 0.0, 0.0, 0)));
    }

    @Test
    public void testOnCreate() {
        // Setup
        final Bundle savedInstanceState = new Bundle(0);

        // Run the test
        storeListingUnderTest.onCreate(savedInstanceState);

        // Verify the results
        verify(storeListingUnderTest.rv_store_listing).setLayoutManager(any(RecyclerView.LayoutManager.class));
        verify(storeListingUnderTest.rv_store_listing).setAdapter(any(NearLocationDetailsAdapter.class));
    }
}
