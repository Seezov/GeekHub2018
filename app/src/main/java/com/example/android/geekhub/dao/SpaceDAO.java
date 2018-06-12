package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.entities.Dimension;
import com.example.android.geekhub.entities.Space;

import java.util.ArrayList;
import java.util.List;

public class SpaceDAO {

    public static final String TAG = "SpaceDAO";

    // Database fields
    private SQLiteDatabase mDatabase;
    private DBHelper mDbHelper;
    private Context mContext;
    private String[] mAllColumns = {
            DBHelper.COLUMN_SPACE_TYPE_ID,
            DBHelper.COLUMN_SPACE_TYPE_NAME};

    public SpaceDAO(Context context) {
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

    public Space createSpace(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SPACE_TYPE_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_SPACE_TYPES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SPACE_TYPES, mAllColumns,
                DBHelper.COLUMN_SPACE_TYPE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Space newSpace = cursorToSpace(cursor);
        cursor.close();
        return newSpace;
    }

    public Boolean updateSpace(Long id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SPACE_TYPE_NAME, name);
        int result = mDatabase.update(DBHelper.TABLE_SPACE_TYPES, values, DBHelper.COLUMN_SPACE_TYPE_ID + id, null);
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

    public List<Space> getAllSpaces() {
        List<Space> listSpaces = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_SPACE_TYPES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Space space = cursorToSpace(cursor);
                listSpaces.add(space);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listSpaces;
    }

    public Space getSpaceById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SPACE_TYPES, mAllColumns,
                DBHelper.COLUMN_SPACE_TYPE_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToSpace(cursor);
    }

    protected Space cursorToSpace(Cursor cursor) {
        Space space = new Space();
        space.setId(cursor.getLong(0));
        space.setName(cursor.getString(1));
        return space;
    }
}
