package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;

public class AdsInShopsDAO extends BaseDAO {

    public static final String TAG = "AdsInShopsDAO";


    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_ADS_IN_SHOPS_AD_ID,
            DBHelper.COLUMN_ADS_IN_SHOPS_SHOP_ID};

    public AdsInShopsDAO(Context context) {
        super(context);
    }

    public Boolean createAdInShop(Long idAd, Long idShop) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ADS_IN_SHOPS_AD_ID, idAd);
        values.put(DBHelper.COLUMN_ADS_IN_SHOPS_SHOP_ID, idShop);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_ADS_IN_SHOPS, null, values);
        return insertId > 0;
    }

/*    public void deleteEmployee(Employee employee) {
        long id = employee.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYE_ID
                + " = " + id, null);
    }*/

    // TODO: FIND BY ALL ID'S
    public int getNumberOfAdsInShop(long idShop) {
        int result = 0;
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS_IN_SHOPS, mAllColumns,
                DBHelper.COLUMN_ADS_IN_SHOPS_SHOP_ID + " = ?",
                new String[]{String.valueOf(idShop)}, null, null, null);
        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }

    public int getNumberOfShopWithAd(long idAd) {
        int result = 0;
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ADS_IN_SHOPS, mAllColumns,
                DBHelper.COLUMN_ADS_IN_SHOPS_AD_ID + " = ?",
                new String[]{String.valueOf(idAd)}, null, null, null);
        if (cursor != null) {
            result = cursor.getCount();
            cursor.close();
        }
        return result;
    }
}
