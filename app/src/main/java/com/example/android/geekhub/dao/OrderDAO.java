package com.example.android.geekhub.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.android.geekhub.db.DBHelper;
import com.example.android.geekhub.entities.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends BaseDAO {

    public static final String TAG = "OrderDAO";

    // Database fields
    private String[] mAllColumns = {
            DBHelper.COLUMN_ORDER_AD_ID,
            DBHelper.COLUMN_ORDER_SHOP_ID,
            DBHelper.COLUMN_ORDER_MATERIAL_TYPE_ID,
            DBHelper.COLUMN_ORDER_DIMENSION_ID,
            DBHelper.COLUMN_ORDER_DESIGN_ID,
            DBHelper.COLUMN_ORDER_QUANTITY,
            DBHelper.COLUMN_ORDER_MATERIAL_PRICE,
            DBHelper.COLUMN_ORDER_PRINT_PRICE};

    public OrderDAO(Context context) {
        super(context);
    }

    public Boolean createOrder(Long idAd,
                               Long idShop,
                               Long idMaterialType,
                               Long idDimension,
                               Long idDesign,
                               Long quantity,
                               Long materialPrice,
                               Long printPrice) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ORDER_AD_ID, idAd);
        values.put(DBHelper.COLUMN_ORDER_SHOP_ID, idShop);
        values.put(DBHelper.COLUMN_ORDER_MATERIAL_TYPE_ID, idMaterialType);
        values.put(DBHelper.COLUMN_ORDER_DIMENSION_ID, idDimension);
        values.put(DBHelper.COLUMN_ORDER_DESIGN_ID, idDesign);
        values.put(DBHelper.COLUMN_ORDER_QUANTITY, quantity);
        values.put(DBHelper.COLUMN_ORDER_MATERIAL_PRICE, materialPrice);
        values.put(DBHelper.COLUMN_ORDER_PRINT_PRICE, printPrice);

        long insertId = mDatabase
                .insert(DBHelper.TABLE_ORDERS, null, values);

        if (insertId > 0) {
            AdsInShopsDAO mAdsInShopsDAO = new AdsInShopsDAO(mContext);
            return mAdsInShopsDAO.createAdInShop(idAd,idShop);
        } else {
            return false;
        }
    }

/*    public void deleteEmployee(Employee employee) {
        long id = employee.getId();
        System.out.println("the deleted employee has the id: " + id);
        mDatabase.delete(DBHelper.TABLE_EMPLOYEES, DBHelper.COLUMN_EMPLOYE_ID
                + " = " + id, null);
    }*/

    public List<Order> getAllOrders() {
        List<Order> listOrders = new ArrayList<>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ORDERS, mAllColumns,
                null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Order order = cursorToOrder(cursor);
            listOrders.add(order);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listOrders;
    }

    protected Order cursorToOrder(Cursor cursor) {
        Order order = new Order();
        order.setIdAd(cursor.getLong(0));
        order.setIdShop(cursor.getLong(1));
        order.setIdMaterialType(cursor.getLong(2));
        order.setIdDimension(cursor.getLong(3));
        order.setIdDesign(cursor.getLong(4));
        order.setQuantity(cursor.getLong(5));
        order.setMaterialPrice(cursor.getLong(6));
        order.setPrintPrice(cursor.getLong(7));
        return order;
    }

    public List<Order> getOrdersByAd(long idAd) {
        List<Order> listOrders = new ArrayList<>();
        Cursor cursor = mDatabase.query(DBHelper.TABLE_ORDERS, mAllColumns,
                DBHelper.COLUMN_ORDER_AD_ID + " = ?",
                new String[]{String.valueOf(idAd)}, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Order order = cursorToOrder(cursor);
            listOrders.add(order);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return listOrders;
    }
}
