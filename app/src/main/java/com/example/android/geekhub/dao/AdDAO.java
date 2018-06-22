package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Ad;

import java.util.ArrayList;
import java.util.List;

public class AdDAO extends BaseDAO {

    public static final String TAG = "AdDAO";

    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_AD_ID,
            DBHelper.COLUMN_AD_DATE_START,
            DBHelper.COLUMN_AD_DATE_END,
            DBHelper.COLUMN_AD_NAME};

    public AdDAO(Context context) {
        super(context);
    }

    public Ad createAd(String name, Long dateStart, Long dateEnd) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_AD_DATE_START, dateStart);
        values.put(DBHelper.COLUMN_AD_DATE_END, dateEnd);
        values.put(DBHelper.COLUMN_AD_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_ADS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS, mAllColumns,
                DBHelper.COLUMN_AD_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Ad newAd = cursorToAd(cursor);
        cursor.close();
        return newAd;
    }

/*    public void deleteEmployee(Employee employee) {
        long id = employee.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYE_ID
                + " = " + id, null);
    }*/

    public List<Ad> getAllAds() {
        List<Ad> listAds = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Ad ad = cursorToAd(cursor);
            listAds.add(ad);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listAds;
    }

    public Ad getAdById(long idAd) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS, mAllColumns,
                DBHelper.COLUMN_AD_ID + " = ?",
                new String[]{String.valueOf(idAd)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToAd(cursor);
    }

    public Ad getAdByName(String adName) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS, mAllColumns,
                DBHelper.COLUMN_AD_NAME + " = ?",
                new String[]{adName}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToAd(cursor);
    }

    protected Ad cursorToAd(Cursor cursor) {
        Ad ad = new Ad();
        ad.setId(cursor.getLong(0));
        ad.setStartDate(cursor.getLong(1));
        ad.setEndDate(cursor.getLong(2));
        ad.setName(cursor.getString(3));
        return ad;
    }
}
