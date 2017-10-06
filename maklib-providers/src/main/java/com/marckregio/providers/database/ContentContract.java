package com.marckregio.providers.database;


import android.content.UriMatcher;
import android.net.Uri;

/**
 * Created by eCopy on 10/6/2017.
 */

public  class ContentContract {

    //TODO : Change Authority and add Tables in the dependendent classes
    static String AUTHORITY = "com.beehive.maklib.provider";
    static String TABLE = "beehive";
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE);

    static int TASK = 100;
    static int TASK_ID = 101;


    //TODO : Modify and add Tables in the dependent classes
    public static String COLUMN_ID = "_id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_VALUE = "value";

    static String CREATE_TABLE = "" +
            "CREATE TABLE if not exists " + TABLE + "(" +
            COLUMN_ID + " integer PRIMARY KEY autoincrement, " +
            COLUMN_NAME + "  text, "+
            COLUMN_VALUE + "  text "+
            ");";

    //TODO : add Uris in the dependent classes
    public static UriMatcher createUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, TABLE, TASK);
        uriMatcher.addURI(AUTHORITY, TABLE + "/#", TASK_ID);
        return uriMatcher;
    }

}
