package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.entities.Ad;
import com.example.android.geekhub.entities.SpaceForAds;

import java.util.ArrayList;
import java.util.List;

public class SpacesForAdsDAO {

    public static final String TAG = "SpacesForAdsDAO";

    private Context mContext;

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private String[] mAllColumns = {
            DBHelper.COLUMN_SPACES_FOR_ADS_SHOP_ID,
            DBHelper.COLUMN_SPACES_FOR_ADS_DIMENSION_ID,
            DBHelper.COLUMN_SPACES_FOR_ADS_SPACE_TYPE_ID,
            DBHelper.COLUMN_SPACES_FOR_ADS_QUANTITY};

    public SpacesForAdsDAO(Context context) {
        mDbHelper = new DBHelper(context);
        this.mContext = context;
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Boolean createSpaceForAd(Long idShop, Long idDimension, Long idSpaceType, Long quantity) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SPACES_FOR_ADS_SHOP_ID, idShop);
        values.put(DBHelper.COLUMN_SPACES_FOR_ADS_DIMENSION_ID, idDimension);
        values.put(DBHelper.COLUMN_SPACES_FOR_ADS_SPACE_TYPE_ID, idSpaceType);
        values.put(DBHelper.COLUMN_SPACES_FOR_ADS_QUANTITY, quantity);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_SPACES_FOR_ADS, null, values);
        return insertId > 0;
    }

/*    public void deleteEmployee(Employee employee) {
        long id = employee.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYE_ID
                + " = " + id, null);
    }*/

    public List<SpaceForAds> getAllSpacesForAds() {
        List<SpaceForAds> listSpacesForAds = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            SpaceForAds spaceForAds = cursorToAd(cursor);
            listSpacesForAds.add(spaceForAds);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listSpacesForAds;
    }
    // TODO: FIND BY ALL ID'S
    public int getNumberOfSpacesInShop(long idShop) {
        int result = 0;
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SPACES_FOR_ADS, mAllColumns,
                DBHelper.COLUMN_SPACES_FOR_ADS_SHOP_ID + " = ?",
                new String[]{String.valueOf(idShop)}, null, null, null);
        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }

    protected SpaceForAds cursorToAd(Cursor cursor) {
        SpaceForAds spaceForAds = new SpaceForAds();
        spaceForAds.setIdShop(cursor.getLong(0));
        spaceForAds.setIdDimension(cursor.getLong(1));
        spaceForAds.setIdSpaceType(cursor.getLong(2));
        spaceForAds.setQuantity(cursor.getLong(3));
        return spaceForAds;
    }
}
