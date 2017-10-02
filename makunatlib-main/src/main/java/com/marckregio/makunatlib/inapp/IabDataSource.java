package com.marckregio.makunatlib.inapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makregio on 14/06/2017.
 */

public class IabDataSource {

    private SQLiteDatabase database;
    private final AndroidSQLiteHelper dbHelper;

    private final String [] allColumns = { AndroidSQLiteHelper.COLUMN_ORDERID,
            AndroidSQLiteHelper.COLUMN_ITEMTYPE, AndroidSQLiteHelper.COLUMN_TOKEN,
            AndroidSQLiteHelper.COLUMN_RENEWABLE, AndroidSQLiteHelper.COLUMN_PURCHASETIME};

    public IabDataSource(final Context context){
        dbHelper = new AndroidSQLiteHelper(context);
    }

    public void open() throws SQLException{
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    private Purchase cursorToPurchase(final Cursor cursor){
        final Purchase purchase = new Purchase();
        purchase.setOrderId(cursor.getString(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_ORDERID)));
        purchase.setItemType(cursor.getString(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_ITEMTYPE)));
        purchase.setToken(cursor.getString(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_TOKEN)));
        purchase.setPurchaseTime(cursor.getInt(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_PURCHASETIME)));
        purchase.setmIsAutoRenewing(Boolean.parseBoolean(cursor.getString(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_RENEWABLE))));
        purchase.setSku(cursor.getString(cursor.getColumnIndex(AndroidSQLiteHelper.COLUMN_SKU)));
        return purchase;
    }

    public void insertOrUpdatePurchases(Purchase purchase){
        final String where = AndroidSQLiteHelper.COLUMN_ORDERID + " = ? ";

        final Cursor cursor = database.query(AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                allColumns,
                where,
                new String [] { purchase.getOrderId() },
                null,
                null,
                null);

        final int count = cursor.getCount();
        cursor.close();

        if (count > 0){

        } else {
            final ContentValues values = new ContentValues();
            values.put(AndroidSQLiteHelper.COLUMN_ORDERID, purchase.getOrderId());
            values.put(AndroidSQLiteHelper.COLUMN_SKU, purchase.getSku());
            values.put(AndroidSQLiteHelper.COLUMN_ITEMTYPE, purchase.getItemType());
            values.put(AndroidSQLiteHelper.COLUMN_TOKEN, purchase.getToken());
            values.put(AndroidSQLiteHelper.COLUMN_PURCHASETIME, purchase.getPurchaseTime());
            values.put(AndroidSQLiteHelper.COLUMN_RENEWABLE, purchase.isAutoRenewing() + "");

            database.insertWithOnConflict(AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
        }
    }

    public Purchase getPurchaseRecord(final String sku){
        final String where = AndroidSQLiteHelper.COLUMN_SKU + "= ?";
        final Cursor cursor = database.query(AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                allColumns,
                where,
                new String[]{ sku },
                null,null,null);

        final Purchase result;
        cursor.moveToFirst();
        if (cursor.isAfterLast()){
            result = null;
        } else {
            result = cursorToPurchase(cursor);
        }

        cursor.close();
        return result;
    }

    public List<Purchase> getAllPurchaseRecords(){
        Cursor cursor = database.query(AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                allColumns, "", null, null, null, null);

        List<Purchase> results = new ArrayList<>();
        try{
            while (cursor.moveToNext()){
                results.add(cursorToPurchase(cursor));
            }
        } finally {
            cursor.close();
        }

        return results;
    }

    public void deletePurchaseRecord(String sku){
        final String where = AndroidSQLiteHelper.COLUMN_SKU + "= ?";
        database.delete(
                AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                where,
                new String [] { sku });
    }

    public void deleteAllPurchaseRecords(){
        database.delete(
                AndroidSQLiteHelper.TABLE_ENTITLEMENTS,
                "",
                null);
    }
}