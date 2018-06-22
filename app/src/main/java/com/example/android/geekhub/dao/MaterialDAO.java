package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Material;

import java.util.ArrayList;
import java.util.List;

public class MaterialDAO extends BaseDAO {

    public static final String TAG = "MaterialDAO";

    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_MATERIAL_TYPE_ID,
            DBHelper.COLUMN_MATERIAL_TYPE_NAME};

    public MaterialDAO(Context context) {
        super(context);
    }

    public Material createMaterial(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MATERIAL_TYPE_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_MATERIAL_TYPES, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_MATERIAL_TYPES, mAllColumns,
                DBHelper.COLUMN_MATERIAL_TYPE_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Material newMaterial = cursorToMaterial(cursor);
        cursor.close();
        return newMaterial;
    }

    public Boolean updateMaterial(Long id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_MATERIAL_TYPE_NAME, name);
        int result = mDatabase.update(DBHelper.TABLE_MATERIAL_TYPES, values, DBHelper.COLUMN_MATERIAL_TYPE_ID + id, null);
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

    public List<Material> getAllMaterials() {
        List<Material> listMaterials = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_MATERIAL_TYPES, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Material material = cursorToMaterial(cursor);
                listMaterials.add(material);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listMaterials;
    }

    public Material getMaterialById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_MATERIAL_TYPES, mAllColumns,
                DBHelper.COLUMN_MATERIAL_TYPE_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToMaterial(cursor);
    }

    public Material getMaterialByName(String name) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_MATERIAL_TYPES, mAllColumns,
                DBHelper.COLUMN_MATERIAL_TYPE_NAME + " = ?",
                new String[]{name}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursorToMaterial(cursor);
    }

    protected Material cursorToMaterial(Cursor cursor) {
        Material material = new Material();
        material.setId(cursor.getLong(0));
        material.setName(cursor.getString(1));
        return material;
    }
}
