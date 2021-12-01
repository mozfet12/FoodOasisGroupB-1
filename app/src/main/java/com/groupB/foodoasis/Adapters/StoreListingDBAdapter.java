package com.groupB.foodoasis.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.groupB.foodoasis.Classes.NearLocationDetailsModelClass;

import java.util.ArrayList;

public class StoreListingDBAdapter extends SQLiteOpenHelper {
    //constants used for db
    private static int DB_VERSION = 1;
    private static final String DB_NAME = "FoodOasis";
    private static final String TBL_STORE_LIST = "tbl_store_list";
    private static final String TBL_FAVOURITE_LIST = "tbl_favourite_list";
    private static final String COL_PLACE_ID = "place_id";
    private static final String COL_PLACE_NAME = "place_name";
    private static final String COL_ICON = "icon";
    private static final String COL_LATITUDE = "lat";
    private static final String COL_LONGITUDE = "lng";
    private static final String COL_IS_FAVOURITE = "is_favourite"; //0 = false, 1 =true

    public StoreListingDBAdapter(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //database is created
        String create_query_store_listing = "CREATE TABLE " + TBL_STORE_LIST + " (" +
                COL_PLACE_ID + " TEXT PRIMARY KEY, " +
                COL_PLACE_NAME + " TEXT, " +
                COL_ICON + " TEXT, " +
                COL_LATITUDE + " TEXT, " +
                COL_LONGITUDE + " TEXT, " +
                COL_IS_FAVOURITE + " INTEGER)";
        String create_query_favourite_listing = "CREATE TABLE " + TBL_FAVOURITE_LIST + " (" +
                COL_PLACE_ID + " TEXT PRIMARY KEY, " +
                COL_PLACE_NAME + " TEXT, " +
                COL_ICON + " TEXT, " +
                COL_LATITUDE + " TEXT, " +
                COL_LONGITUDE + " TEXT, " +
                COL_IS_FAVOURITE + " INTEGER)";
        Log.e("create_query_store_listing", create_query_store_listing);
        Log.e("create_query_favourite_listing", create_query_favourite_listing);

        try {
            //execute create query to create a table
            db.execSQL(create_query_store_listing);
            db.execSQL(create_query_favourite_listing);
        } catch (Exception e) {
            Log.e("Exception while CREATE query ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //called when you upgrade the database mostly when update the schema
        String create_query_store_listing = "CREATE TABLE " + TBL_STORE_LIST + " (" + COL_PLACE_ID + " TEXT PRIMARY KEY, " + COL_PLACE_NAME + " TEXT, " + COL_ICON + " TEXT, " + COL_LATITUDE + " TEXT, " + COL_LONGITUDE + " TEXT, " + COL_IS_FAVOURITE + " INTEGER)";
//        String create_query_favourite_listing = "CREATE TABLE " + TBL_FAVOURITE_LIST + " (" + COL_PLACE_ID + " TEXT PRIMARY KEY, " + COL_PLACE_NAME + " TEXT, " + COL_ICON + " TEXT, " + COL_LATITUDE + " TEXT, " + COL_LONGITUDE + " TEXT, " + COL_IS_FAVOURITE + " INTEGER)";
//        Log.e("create_query_store_listing", create_query_store_listing);

        try {
            //execute create query to create a table
            db.execSQL(create_query_store_listing);
//            db.execSQL(create_query_favourite_listing);
        } catch (Exception e) {
            Log.e("Exception while CREATE query ", e.getMessage());
        }
    }


    //INSERT query for the store list table
    public void insertStoreInTable(NearLocationDetailsModelClass data) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insert_query_store_listing = "INSERT OR IGNORE INTO " + TBL_STORE_LIST + "(" + COL_PLACE_ID + ", " + COL_PLACE_NAME + ", " + COL_ICON + ", " + COL_LATITUDE + ", " + COL_LONGITUDE + ", " + COL_IS_FAVOURITE + ") VALUES (\"" + data.getPlace_id() + "\", \"" + data.getName() + "\", \"" + data.getIcon() + "\", \"" + data.getLatitude() + "\", \"" + data.getLongitude() + "\", " + data.getIs_favourite() + ")";
//        Log.e("insert_query_store_listing ", insert_query_store_listing);
        db.execSQL(insert_query_store_listing);
        db.close();
    }

    //SELECT query for the store list table
    public ArrayList<NearLocationDetailsModelClass> selectStoreFromTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select_query_store_listing = "SELECT * FROM " + TBL_STORE_LIST;
        Cursor cursor = db.rawQuery(select_query_store_listing, null);
        ArrayList<NearLocationDetailsModelClass> arrayList = new ArrayList<NearLocationDetailsModelClass>();

        //check if the data is available
        if (cursor.moveToFirst()) {
            //get all the data from the table
            do {
                NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();
                nearLocationDetailsModelClass.setPlace_id(cursor.getString(0));
                nearLocationDetailsModelClass.setName(cursor.getString(1));
                nearLocationDetailsModelClass.setIcon(cursor.getString(2));
                nearLocationDetailsModelClass.setLatitude(Double.parseDouble(cursor.getString(3)));
                nearLocationDetailsModelClass.setLongitude(Double.parseDouble(cursor.getString(4)));
                nearLocationDetailsModelClass.setIs_favourite(Integer.parseInt(cursor.getString(5)));
                arrayList.add(nearLocationDetailsModelClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    //DELETE records from store list table
    public void deleteStoreListing() {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_query_store_listing = "DELETE FROM " + TBL_STORE_LIST;
        Log.e("delete_query_store_listing", delete_query_store_listing);
        db.execSQL(delete_query_store_listing);
        db.close();
    }

//    //DELETE non favourite rows
//    public void deleteNonFavStoreListing(int is_favourite) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String delete_query_store_listing = "DELETE FROM " + TBL_STORE_LIST + " WHERE " + COL_IS_FAVOURITE + " = " + is_favourite;
//        Log.e("delete_query_store_listing", delete_query_store_listing);
//        db.execSQL(delete_query_store_listing);
//        db.close();
//    }

//    //DELETE all rows from store list
//    public void deleteNonFavouriteRows() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        //delete only non favourite rows
//        db.execSQL("DELETE FROM " + TBL_STORE_LIST);
//        db.close();
//    }

    //UPDATE record from store list table
    public void updateStoreListing(String col_name, int value, String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update_query_store_listing = "UPDATE " + TBL_STORE_LIST + " SET " + col_name + " = \"" + value + "\" WHERE " + COL_PLACE_ID + " = \"" + place_id + "\"";
        Log.e("update_query_store_listing ", update_query_store_listing);
        db.execSQL(update_query_store_listing);
        db.close();
    }

    //============================================================================================
    //============================================================================================

    //INSERT query for the favourite list table
    public void insertFavouriteInTable(NearLocationDetailsModelClass data) {
        SQLiteDatabase db = this.getWritableDatabase();

        String insert_query_store_listing = "INSERT OR IGNORE INTO " + TBL_FAVOURITE_LIST + "(" + COL_PLACE_ID + ", " + COL_PLACE_NAME + ", " + COL_ICON + ", " + COL_LATITUDE + ", " + COL_LONGITUDE + ", " + COL_IS_FAVOURITE + ") VALUES (\"" + data.getPlace_id() + "\", \"" + data.getName() + "\", \"" + data.getIcon() + "\", \"" + data.getLatitude() + "\", \"" + data.getLongitude() + "\", " + data.getIs_favourite() + ")";
        Log.e("insert_query_store_listing ", insert_query_store_listing);
        db.execSQL(insert_query_store_listing);
        db.close();
    }

    //SELECT query for the favourite list table
    public ArrayList<NearLocationDetailsModelClass> selectFavouriteFromTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        String select_query_store_listing = "SELECT * FROM " + TBL_FAVOURITE_LIST;
        Cursor cursor = db.rawQuery(select_query_store_listing, null);
        ArrayList<NearLocationDetailsModelClass> arrayList = new ArrayList<NearLocationDetailsModelClass>();

        //check if the data is available
        if (cursor.moveToFirst()) {
            //get all the data from the table
            do {
                NearLocationDetailsModelClass nearLocationDetailsModelClass = new NearLocationDetailsModelClass();
                nearLocationDetailsModelClass.setPlace_id(cursor.getString(0));
                nearLocationDetailsModelClass.setName(cursor.getString(1));
                nearLocationDetailsModelClass.setIcon(cursor.getString(2));
                nearLocationDetailsModelClass.setLatitude(Double.parseDouble(cursor.getString(3)));
                nearLocationDetailsModelClass.setLongitude(Double.parseDouble(cursor.getString(4)));
                nearLocationDetailsModelClass.setIs_favourite(Integer.parseInt(cursor.getString(5)));
                arrayList.add(nearLocationDetailsModelClass);
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    //SELECT query for the favourite list table for 1 record
    public boolean selectRecordFavouriteFromTable(String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String select_query_store_listing = "SELECT * FROM " + TBL_FAVOURITE_LIST + " WHERE " + COL_PLACE_ID + " = \"" + place_id + "\"";
        Cursor cursor = db.rawQuery(select_query_store_listing, null);

        //check if the data is available
        if (cursor.moveToFirst()) {
            //get all the data from the table
            return true;
        }
        return false;
    }

    //DELETE records from favourite list table
    public void deleteFavouriteListing(String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String delete_query_store_listing = "DELETE FROM " + TBL_FAVOURITE_LIST + " WHERE " + COL_PLACE_ID + " = \"" + place_id + "\"";
        Log.e("delete_query_store_listing", delete_query_store_listing);
        db.execSQL(delete_query_store_listing);
        db.close();
    }

//    //DELETE non favourite rows
//    public void deleteNonFavStoreListing(int is_favourite) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        String delete_query_store_listing = "DELETE FROM " + TBL_STORE_LIST + " WHERE " + COL_IS_FAVOURITE + " = " + is_favourite;
//        Log.e("delete_query_store_listing", delete_query_store_listing);
//        db.execSQL(delete_query_store_listing);
//        db.close();
//    }
//
//    //DELETE all rows from favourite list table
//    public void deleteNonFavouriteRows() {
//        SQLiteDatabase db = this.getWritableDatabase();
//        //delete only non favourite rows
//        db.execSQL("DELETE FROM " + TBL_STORE_LIST);
//        db.close();
//    }

    //UPDATE record from favourite list table
    public void updateFavouriteListing(String col_name, int value, String place_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String update_query_store_listing = "UPDATE " + TBL_STORE_LIST + " SET " + col_name + " = \"" + value + "\" WHERE " + COL_PLACE_ID + " = \"" + place_id + "\"";
        Log.e("update_query_store_listing ", update_query_store_listing);
        db.execSQL(update_query_store_listing);
        db.close();
    }
}
