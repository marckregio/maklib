package com.marckregio.providers.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by eCopy on 10/6/2017.
 */

public class SQLHelper extends SQLiteOpenHelper{

    static String DATABASE = "Beehive.db";
    static int VERSION = 1;

    public SQLHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //TODO : add tables here
        sqLiteDatabase.execSQL(ContentContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ContentContract.TABLE);
    }
}
