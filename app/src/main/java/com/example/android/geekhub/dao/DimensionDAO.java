package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.entities.Dimension;

import java.util.ArrayList;
import java.util.List;

public class DimensionDAO {

    public static final String TAG = "DimensionDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DBHelper.COLUMN_DIMENSION_ID,
            DBHelper.COLUMN_DIMENSION_NAME};

    public DimensionDAO(Context context) {
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

    public void open() throws SQLException {
        mDatabase = mDbHelper.getWritableDatabase();
    }

    public void close() {
        mDbHelper.close();
    }

    public Dimension createDimension(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DIMENSION_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_DIMENSIONS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIMENSIONS, mAllColumns,
                DBHelper.COLUMN_DIMENSION_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Dimension newDimension = cursorToDimension(cursor);
        cursor.close();
        return newDimension;
    }

    public Boolean updateDimension(Long id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SHOP_NAME, name);
        int result = mDatabase.update(DBHelper.TABLE_DIMENSIONS, values, DBHelper.COLUMN_DIMENSION_ID + id, null);
        return result > 0;
    }

/*    public void deleteCompany(Company company) {
        long id = company.getId();
        // delete all employees of this company
        EmployeeDAO employeeDao = new EmployeeDAO(mContext);
        List<Employee> listEmployees = employeeDao.getEmployeesOfCompany(id);
        if (listEmployees != null && !listEmployees.isEmpty()) {
            for (Employee e : listEmployees) {
                employeeDao.deleteEmployee(e);
            }
        }

        System.out.println("the deleted company has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_COMPANIES, DBHelper.COLUMN_COMPANY_ID
                + " = " + id, null);
    }*/

    public List<Dimension> getAllShops() {
        List<Dimension> listDimensions = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIMENSIONS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Dimension dimension = cursorToDimension(cursor);
                listDimensions.add(dimension);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listDimensions;
    }

    public Dimension getDimensionById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DIMENSIONS, mAllColumns,
                DBHelper.COLUMN_DIMENSION_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToDimension(cursor);
    }

    protected Dimension cursorToDimension(Cursor cursor) {
        Dimension dimension = new Dimension();
        dimension.setId(cursor.getLong(0));
        dimension.setName(cursor.getString(1));
        return dimension;
    }
}
