package com.example.android.geekhub.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TAG = "DBHelper";

    private static final String DATABASE_NAME = "shops.db";
    private static final int DATABASE_VERSION = 1;

    // columns of the shops table
    public static final String TABLE_SHOPS = "shops";
    public static final String COLUMN_SHOP_ID = "idShop";
    public static final String COLUMN_SHOP_NAME = "name";

    // columns of the ads table
    public static final String TABLE_ADS = "ads";
    public static final String COLUMN_AD_ID = "idAd";
    public static final String COLUMN_AD_DATE_START = "dateStart";
    public static final String COLUMN_AD_DATE_END = "dateEnd";
    public static final String COLUMN_AD_NAME = "name";

    // columns of the dimensions table
    public static final String TABLE_DIMENSIONS = "dimensions";
    public static final String COLUMN_DIMENSION_ID = "idDimension";
    public static final String COLUMN_DIMENSION_NAME = "name";

    // columns of the spaceTypes table
    public static final String TABLE_SPACE_TYPES = "spaceTypes";
    public static final String COLUMN_SPACE_TYPE_ID = "idSpaceType";
    public static final String COLUMN_SPACE_TYPE_NAME = "name";

    // columns of the spacesForAds table
    public static final String TABLE_SPACES_FOR_ADS = "spacesForAds";
    public static final String COLUMN_SPACES_FOR_ADS_SHOP_ID = COLUMN_SHOP_ID;
    public static final String COLUMN_SPACES_FOR_ADS_DIMENSION_ID = COLUMN_DIMENSION_ID;
    public static final String COLUMN_SPACES_FOR_ADS_SPACE_TYPE_ID = COLUMN_SPACE_TYPE_ID;
    public static final String COLUMN_SPACES_FOR_ADS_QUANTITY = "quantity";

    // columns of the adsInShops table
    public static final String TABLE_ADS_IN_SHOPS = "adsInShops";
    public static final String COLUMN_ADS_IN_SHOPS_AD_ID = COLUMN_AD_ID;
    public static final String COLUMN_ADS_IN_SHOPS_SHOP_ID = COLUMN_SHOP_ID;


    // columns of the materialTypes table
    public static final String TABLE_MATERIAL_TYPES = "materialTypes";
    public static final String COLUMN_MATERIAL_TYPE_ID = "idMaterialType";
    public static final String COLUMN_MATERIAL_TYPE_NAME = "name";

    // columns of the designs table
    public static final String TABLE_DESIGNS = "designs";
    public static final String COLUMN_DESIGN_ID = "idDesign";
    public static final String COLUMN_DESIGN_DIMENSION_ID = COLUMN_DIMENSION_ID;
    public static final String COLUMN_DESIGN_AD_ID = COLUMN_AD_ID;
    public static final String COLUMN_DESIGN_NAME = "name";
    public static final String COLUMN_DESIGN_PRICE = "price";

    // columns of the orders table
    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_AD_ID = COLUMN_AD_ID;
    public static final String COLUMN_ORDER_SHOP_ID = COLUMN_SHOP_ID;
    public static final String COLUMN_ORDER_MATERIAL_TYPE__ID = COLUMN_MATERIAL_TYPE_ID;
    public static final String COLUMN_ORDER_DIMENSION_ID = COLUMN_DIMENSION_ID;
    public static final String COLUMN_ORDER_DESIGN_ID = COLUMN_DESIGN_ID;
    public static final String COLUMN_ORDER_QUANTITY = "quantity";
    public static final String COLUMN_ORDER_MATERIAL_PRICE = "materialPrice";
    public static final String COLUMN_ORDER_PRINT_PRICE = "printPrice";

    //region SQL Create tables start

    // SQL statement of the shops table creation
    private static final String SQL_CREATE_TABLE_SHOPS = "CREATE TABLE " + TABLE_SHOPS + "("
            + COLUMN_SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SHOP_NAME + " TEXT NOT NULL "
            + ");";

    // SQL statement of the ads table creation
    // NOTE: USE cursor.getLong() TO GET A DATE
    private static final String SQL_CREATE_TABLE_ADS = "CREATE TABLE " + TABLE_ADS + "("
            + COLUMN_AD_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_AD_DATE_START + " INTEGER NOT NULL, "
            + COLUMN_AD_DATE_END + " INTEGER NOT NULL, "
            + COLUMN_AD_NAME + " TEXT NOT NULL "
            + ");";

    // SQL statement of the dimensions table creation
    private static final String SQL_CREATE_TABLE_DIMENSIONS = "CREATE TABLE " + TABLE_DIMENSIONS + "("
            + COLUMN_DIMENSION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DIMENSION_NAME + " TEXT NOT NULL "
            + ");";

    // SQL statement of the spaceTypes table creation
    private static final String SQL_CREATE_TABLE_SPACE_TYPES = "CREATE TABLE " + TABLE_SPACE_TYPES + "("
            + COLUMN_SPACE_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_SPACE_TYPE_NAME + " TEXT NOT NULL "
            + ");";

    // SQL statement of the spacesForAds table creation
    private static final String SQL_CREATE_TABLE_SPACES_FOR_ADS = "CREATE TABLE " + TABLE_SPACES_FOR_ADS + "("
            + COLUMN_SPACES_FOR_ADS_SHOP_ID + " INTEGER, "
            + COLUMN_SPACES_FOR_ADS_DIMENSION_ID + " INTEGER, "
            + COLUMN_SPACES_FOR_ADS_SPACE_TYPE_ID + " INTEGER, "
            + COLUMN_SPACES_FOR_ADS_QUANTITY + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_SPACES_FOR_ADS_SHOP_ID + ") REFERENCES " + TABLE_SHOPS + "( " + COLUMN_SHOP_ID + "),"
            + "FOREIGN KEY(" + COLUMN_SPACES_FOR_ADS_DIMENSION_ID + ") REFERENCES " + TABLE_DIMENSIONS + "( " + COLUMN_DIMENSION_ID + ")"
            + ");";

    // SQL statement of the adsInShops table creation
    private static final String SQL_CREATE_TABLE_ADS_IN_SHOPS = "CREATE TABLE " + TABLE_ADS_IN_SHOPS + "("
            + COLUMN_ADS_IN_SHOPS_AD_ID + " INTEGER, "
            + COLUMN_ADS_IN_SHOPS_SHOP_ID + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ADS_IN_SHOPS_AD_ID + ") REFERENCES " + TABLE_ADS + "( " + COLUMN_AD_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ADS_IN_SHOPS_SHOP_ID + ") REFERENCES " + TABLE_SHOPS + "( " + COLUMN_SHOP_ID + ")"
            + ");";

    // SQL statement of the materialTypes table creation
    private static final String SQL_CREATE_TABLE_MATERIAL_TYPES = "CREATE TABLE " + TABLE_MATERIAL_TYPES + "("
            + COLUMN_MATERIAL_TYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_MATERIAL_TYPE_NAME + " TEXT NOT NULL "
            + ");";

    // SQL statement of the designs table creation
    private static final String SQL_CREATE_TABLE_DESIGNS = "CREATE TABLE " + TABLE_DESIGNS + "("
            + COLUMN_DESIGN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DESIGN_DIMENSION_ID + " INTEGER, "
            + COLUMN_DESIGN_AD_ID + " INTEGER, "
            + COLUMN_DESIGN_NAME + " TEXT NOT NULL, "
            + COLUMN_DESIGN_PRICE + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_DESIGN_DIMENSION_ID + ") REFERENCES " + TABLE_DIMENSIONS + "( " + COLUMN_DIMENSION_ID + "),"
            + "FOREIGN KEY(" + COLUMN_DESIGN_AD_ID + ") REFERENCES " + TABLE_ADS + "( " + COLUMN_AD_ID + ")"
            + ");";

    // SQL statement of the orders table creation
    private static final String SQL_CREATE_TABLE_ORDERS = "CREATE TABLE " + TABLE_ORDERS + "("
            + COLUMN_ORDER_AD_ID + " INTEGER, "
            + COLUMN_ORDER_SHOP_ID  + " INTEGER, "
            + COLUMN_ORDER_MATERIAL_TYPE__ID  + " INTEGER, "
            + COLUMN_ORDER_DIMENSION_ID  + " INTEGER, "
            + COLUMN_ORDER_DESIGN_ID  + " INTEGER, "
            + COLUMN_ORDER_QUANTITY  + " INTEGER, "
            + COLUMN_ORDER_MATERIAL_PRICE  + " INTEGER, "
            + COLUMN_ORDER_PRINT_PRICE  + " INTEGER, "
            + "FOREIGN KEY(" + COLUMN_ORDER_AD_ID + ") REFERENCES " + TABLE_ADS + "( " + COLUMN_AD_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ORDER_SHOP_ID + ") REFERENCES " + TABLE_SHOPS + "( " + COLUMN_SHOP_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ORDER_MATERIAL_TYPE__ID + ") REFERENCES " + TABLE_MATERIAL_TYPES + "( " + COLUMN_MATERIAL_TYPE_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ORDER_DIMENSION_ID + ") REFERENCES " + TABLE_DIMENSIONS + "( " + COLUMN_DIMENSION_ID + "),"
            + "FOREIGN KEY(" + COLUMN_ORDER_DESIGN_ID + ") REFERENCES " + TABLE_DESIGNS + "( " + COLUMN_DESIGN_ID + ")"
            + ");";

    //endRegion
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(SQL_CREATE_TABLE_SHOPS);
        database.execSQL(SQL_CREATE_TABLE_DIMENSIONS);
        database.execSQL(SQL_CREATE_TABLE_SPACE_TYPES);
        database.execSQL(SQL_CREATE_TABLE_SPACES_FOR_ADS);
        database.execSQL(SQL_CREATE_TABLE_ADS);
        database.execSQL(SQL_CREATE_TABLE_ADS_IN_SHOPS);
        database.execSQL(SQL_CREATE_TABLE_MATERIAL_TYPES);
        database.execSQL(SQL_CREATE_TABLE_DESIGNS);
        database.execSQL(SQL_CREATE_TABLE_ORDERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG,
                "Upgrading the database from version " + oldVersion + " to " + newVersion);
        // clear all data
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIMENSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACE_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DIMENSIONS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPACES_FOR_ADS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADS_IN_SHOPS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MATERIAL_TYPES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DESIGNS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);

        // recreate the tables
        onCreate(db);
    }

    public DBHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }
}
