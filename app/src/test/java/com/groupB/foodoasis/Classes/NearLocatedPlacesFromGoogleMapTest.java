package com.groupB.foodoasis.Classes;

import static org.junit.Assert.assertEquals;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NearLocatedPlacesFromGoogleMapTest {

    private NearLocatedPlacesFromGoogleMap nearLocatedPlacesFromGoogleMapUnderTest;

    @Before
    public void setUp() {
        nearLocatedPlacesFromGoogleMapUnderTest = new NearLocatedPlacesFromGoogleMap();
    }

    @Test
    public void testParseJsonArray() {
        // Setup
        final JSONArray jsonArray = new JSONArray(List.of());
        final List<HashMap<String, String>> expectedResult = List.of(new HashMap<>(Map.ofEntries(Map.entry("value", "value"))));

        // Run the test
        final List<HashMap<String, String>> result = nearLocatedPlacesFromGoogleMapUnderTest.parseJsonArray(jsonArray);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    public void testParseResult() {
        // Setup
        final JSONObject jsonObject = new JSONObject(Map.ofEntries());
        final List<HashMap<String, String>> expectedResult = List.of(new HashMap<>(Map.ofEntries(Map.entry("value", "value"))));

        // Run the test
        final List<HashMap<String, String>> result = nearLocatedPlacesFromGoogleMapUnderTest.parseResult(jsonObject);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
