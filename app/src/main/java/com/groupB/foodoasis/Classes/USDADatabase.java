package com.groupB.foodoasis.Classes;


import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;


public class USDADatabase extends AsyncTask<String, Integer, String> {

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);

            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    @Override
    protected String doInBackground(String... strings) {

        //System.out.println(json.get("results"));
        //Miles that the User is willing to travel
        double milesTraveled = 20;
        //Creates an API call to Google Geocoding
        GeoApiContext context = new GeoApiContext.Builder()
//                .apiKey("AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM")
                .apiKey("AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM")
                .build();
//        GeocodingResult[] results = GeocodingApi.geocode(context,
//                "1714 Heddon Falls Dr, Sugar Land, TX").await();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        //System.out.println(gson.toJson(results[0].geometry.location.lat));
        //Saves myLat as the Latitude and myLong as the longitude
        String myLat = strings[0];
        String myLong = strings[1];

//        String myLat = "29.6871385";
//        String myLong = "-95.4447542";

        //System.out.println(myLat);
        //System.out.println(myLong);
        //Creates a string variable with USDA lat long link
        //String urlUSDA ="http://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + myLat + "&lng=" + myLong;
        //System.out.println(urlUSDA);
        //System.out.println("https://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=37.422388&lng=-122.0841883");

        //Create arraylists bc they're dynamic
        ArrayList<String> strResult = new ArrayList<String>();
        ArrayList<String> idResult = new ArrayList<String>();
        ArrayList<String> addrResult = new ArrayList<String>();
        ArrayList<String> distResult = new ArrayList<String>();
        ArrayList<String> latResult = new ArrayList<String>();
        ArrayList<String> lonResult = new ArrayList<String>();
        try {
            String link = "https://search.ams.usda.gov/farmersmarkets/v1/data.svc/locSearch?lat=" + myLat + "&lng=" + myLong;
            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();

            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
                String output;
                String totalString = "";
                while ((output = br.readLine()) != null) {
                    totalString += output;
                }
                //System.out.println(totalString);
                //Splits elements of JSONString and stores elements in jsonArray[]
                String jsonArray[] = totalString.split("\\{");

                //System.out.println(jsonArray[2]);

                int resultStr = 0;
                int idStr = 0;
                int resultStr2 = 0;
                String varStrResult = "";
                String varStrResult2 = "";
                String splitString = "";
                double dist = 0.0;
                for (int i = 2; i < jsonArray.length; i++) {
                    resultStr = (jsonArray[i].indexOf("marketname")) + 1;
                    idStr = (jsonArray[i].indexOf("id") - 1);
                    resultStr2 = jsonArray[i].indexOf("}");
                    varStrResult = jsonArray[i].substring(resultStr + 12, resultStr2 - 1);

                    varStrResult2 = varStrResult.replaceAll("\\d", "");
                    varStrResult2 = varStrResult2.replaceAll("\\.", "");

                    splitString = varStrResult.replaceAll("[^\\d.]", "");
                    dist = Double.parseDouble(splitString);

                    if (dist <= milesTraveled) {
                        idResult.add(jsonArray[i].substring(idStr + 6, idStr + 13));
                        strResult.add(varStrResult2);
                        distResult.add(splitString);

                    }

                }

                String urlTobeSent;
                String jsonString = "";
                String address;
                int addrStr;
                int googStr;
                JSONArray obj = new JSONArray();
                for (int i = 0; i < idResult.size(); i++) {
                    urlTobeSent = "https://search.ams.usda.gov/farmersmarkets/v1/data.svc/mktDetail?id=" + idResult.get(i);
                    JSONObject json = readJsonFromUrl(urlTobeSent);
                    jsonString = json.toString();
                    addrStr = jsonString.indexOf("Address");
                    googStr = jsonString.indexOf("Schedule");
                    address = (jsonString.substring(addrStr + 10, googStr - 3));
                    //System.out.println(address);
                    GeoApiContext context2 = new GeoApiContext.Builder()
//                            .apiKey("AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM")
                            .apiKey("AIzaSyAgmp-JgcYmE73aC3Y2qZtpcIPYyFM-KkM")
                            .build();
                    GeocodingResult[] results2 = GeocodingApi.geocode(context,
                            address).await();
                    Gson gson2 = new GsonBuilder().setPrettyPrinting().create();
                    //System.out.println(gson.toJson(results[0].geometry.location.lat));
                    myLat = gson.toJson(results2[0].geometry.location.lat);
                    myLong = gson.toJson(results2[0].geometry.location.lng);

                    latResult.add(myLat);
                    lonResult.add(myLong);

                    //Creates JSON Array
                    try {
                        JSONObject locData = new JSONObject();
                        locData.put("number", String.valueOf(i));

                        locData.put("name", strResult.get(i).trim());
                        locData.put("lon", myLong);
                        locData.put("lat", myLat);
                        obj.put(locData);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }


                }
                //Converts JSON to string and prints it
//                System.out.println(obj.toString());
                Log.e("Data: ", obj.toString());
                return obj.toString();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not Successful";
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
    }
}