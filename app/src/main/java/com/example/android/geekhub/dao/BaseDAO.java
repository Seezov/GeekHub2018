package com.example.android.geekhub.dao;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;

public abstract class BaseDAO {

    public static final String TAG = "BaseDAO";

    // Database fields
    private DBHelper mDbHelper;
    SQLiteDatabase mDatabase;
    Context mContext;

    BaseDAO(Context context) {
        this.mContext = context;
        mDbHelper = new DBHelper(context);
        // open the database
        try {
            open();
        } catch (SQLException e) {
            Log.e(TAG, "SQLException on opening database " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    private void close() {
        mDbHelper.close();
    }
}
