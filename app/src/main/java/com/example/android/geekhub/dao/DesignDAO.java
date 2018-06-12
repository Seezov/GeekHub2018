package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Design;

import java.util.ArrayList;
import java.util.List;

public class DesignDAO extends BaseDAO {

    public static final String TAG = "DesignDAO";

    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_DESIGN_ID,
            DBHelper.COLUMN_DESIGN_DIMENSION_ID,
            DBHelper.COLUMN_DESIGN_AD_ID,
            DBHelper.COLUMN_DESIGN_NAME,
            DBHelper.COLUMN_DESIGN_PRICE};

    public DesignDAO(Context context) {
       super(context);
    }

    public Design createDesign(Long idDimension, Long idAd, String name, Long price) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DESIGN_DIMENSION_ID, idDimension);
        values.put(DBHelper.COLUMN_DESIGN_AD_ID, idAd);
        values.put(DBHelper.COLUMN_DESIGN_NAME, name);
        values.put(DBHelper.COLUMN_DESIGN_PRICE, price);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_DESIGNS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DESIGNS, mAllColumns,
                DBHelper.COLUMN_DESIGN_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Design newDesign = cursorToDesign(cursor);
        cursor.close();
        return newDesign;
    }

    public Boolean updateDesign(Long idDesign, Long idDimension, Long idAd, String name, Long price) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_DESIGN_DIMENSION_ID, idDimension);
        values.put(DBHelper.COLUMN_DESIGN_AD_ID, idAd);
        values.put(DBHelper.COLUMN_DESIGN_NAME, name);
        values.put(DBHelper.COLUMN_DESIGN_PRICE, price);
        int result = mDatabase.update(DBHelper.TABLE_DESIGNS, values, DBHelper.COLUMN_DESIGN_ID + idDesign, null);
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

    public List<Design> getAllDesigns() {
        List<Design> listDesigns = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_DESIGNS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Design design = cursorToDesign(cursor);
                listDesigns.add(design);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listDesigns;
    }

    public Design getDesignById(long idDesign) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_DESIGNS, mAllColumns,
                DBHelper.COLUMN_DESIGN_ID + " = ?",
                new String[]{String.valueOf(idDesign)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToDesign(cursor);
    }

    protected Design cursorToDesign(Cursor cursor) {
        Design newDesign = new Design();
        newDesign.setId(cursor.getLong(0));
        newDesign.setIdDimension(cursor.getLong(1));
        newDesign.setIdAd(cursor.getLong(2));
        newDesign.setName(cursor.getString(3));
        newDesign.setPrice(cursor.getLong(4));
        return newDesign;
    }
}
