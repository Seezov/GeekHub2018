package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopDAO extends BaseDAO {

    public static final String TAG = "ShopDAO";

    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_SHOP_ID,
            DBHelper.COLUMN_SHOP_NAME};

    public ShopDAO(Context context) {
        super(context);
    }

    public Shop createShop(String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SHOP_NAME, name);
        long insertId = mDatabase
                .insert(DBHelper.TABLE_SHOPS, null, values);
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SHOPS, mAllColumns,
                DBHelper.COLUMN_SHOP_ID + " = " + insertId, null, null,
                null, null);
        cursor.moveToFirst();
        Shop newShop = cursorToShop(cursor);
        cursor.close();
        return newShop;
    }

    public Boolean updateShop(Long id, String name) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_SHOP_NAME, name);
        int result = mDatabase.update(DBHelper.TABLE_SHOPS, values, DBHelper.COLUMN_SHOP_ID + id, null);
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

    public List<Shop> getAllShops() {
        List<Shop> listShops = new ArrayList<>();

        Cursor cursor = mDatabase.query(DBHelper.TABLE_SHOPS, mAllColumns,
                null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Shop shop = cursorToShop(cursor);
                listShops.add(shop);
                cursor.moveToNext();
            }

            // make sure to close the cursor
            cursor.close();
        }
        return listShops;
    }

    public Shop getShopById(long id) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SHOPS, mAllColumns,
                DBHelper.COLUMN_SHOP_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToShop(cursor);
    }

    public Shop getShopByName(String shopName) {
        Cursor cursor = mDatabase.query(DBHelper.TABLE_SHOPS, mAllColumns,
                DBHelper.COLUMN_SHOP_NAME + " = ?",
                new String[]{shopName}, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursorToShop(cursor);
    }

    protected Shop cursorToShop(Cursor cursor) {
        Shop shop = new Shop();
        shop.setId(cursor.getLong(0));
        shop.setName(cursor.getString(1));
        return shop;
    }
}
