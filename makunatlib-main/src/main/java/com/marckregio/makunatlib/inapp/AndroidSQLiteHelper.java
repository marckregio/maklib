package com.marckregio.makunatlib.inapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by makregio on 14/06/2017.
 */

public class AndroidSQLiteHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "androidpurchases.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_ENTITLEMENTS = "entitlements";
    public static final String COLUMN_ITEMTYPE = "itemtype";
    public static final String COLUMN_ORDERID = "orderid";
    public static final String COLUMN_TOKEN = "token";
    public static final String COLUMN_RENEWABLE = "renewable";
    public static final String COLUMN_PURCHASETIME = "purchasetime";
    public static final String COLUMN_SKU = "skuName";

    public AndroidSQLiteHelper(final Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_CREATE = "create table " + TABLE_ENTITLEMENTS
            + "("
            + COLUMN_ORDERID
            + " text primary key not null, "
            + COLUMN_SKU
            + " text, "
            + COLUMN_ITEMTYPE
            + " text not null, "
            + COLUMN_TOKEN
            + " text, "
            + COLUMN_RENEWABLE
            + " text, "
            + COLUMN_PURCHASETIME
            + " integer, "
            + ");";

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("AndroidSQLiteHelper", "Upgrading database from version " + oldVersion
                + " to "
                + newVersion
        );
    }
}
