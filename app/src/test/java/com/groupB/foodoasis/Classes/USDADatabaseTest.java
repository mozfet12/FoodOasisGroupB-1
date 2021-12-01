package com.groupB.foodoasis.Classes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class USDADatabaseTest {

    private USDADatabase usdaDatabaseUnderTest;

    @Before
    public void setUp() {
        usdaDatabaseUnderTest = new USDADatabase();
    }

    @Test
    public void testDoInBackground() {
        assertEquals("result", usdaDatabaseUnderTest.doInBackground("strings"));
    }

    @Test
    public void testOnPostExecute() {
        // Setup

        // Run the test
        usdaDatabaseUnderTest.onPostExecute("s");

        // Verify the results
    }

    @Test
    public void testReadJsonFromUrl() throws Exception {
        // Setup

        // Run the test
        final JSONObject result = USDADatabase.readJsonFromUrl("url");

        // Verify the results
    }

    @Test
    public void testReadJsonFromUrl_ThrowsIOException() {
        // Setup

        // Run the test
        assertThrows(IOException.class, () -> USDADatabase.readJsonFromUrl("url"));
    }

    @Test
    public void testReadJsonFromUrl_ThrowsJSONException() {
        // Setup

        // Run the test
        assertThrows(JSONException.class, () -> USDADatabase.readJsonFromUrl("url"));
    }
}
