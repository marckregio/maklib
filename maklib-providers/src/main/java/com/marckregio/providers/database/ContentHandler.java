package com.marckregio.providers.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by eCopy on 10/6/2017.
 */

public class ContentHandler extends ContentProvider {

    private SQLHelper sqlHelper = null;
    private UriMatcher uriMatcher = null;

    @Override
    public boolean onCreate() {
        sqlHelper = new SQLHelper(getContext());
        uriMatcher = ContentContract.createUriMatcher();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] columns, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        int uriRequest = uriMatcher.match(uri);
        Cursor cursor;

        if (uriRequest == ContentContract.TASK){
            cursor = db.query(ContentContract.TABLE, columns, selection, selectionArgs, null, null, sortOrder);
        } else {
            long id = ContentUris.parseId(uri);
            String [] ids = {id + ""};
            cursor = db.query(ContentContract.TABLE, columns, ContentContract.COLUMN_ID + " = ?", ids, null, null, sortOrder);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == ContentContract.TASK){
            return ContentContract.AUTHORITY;
        } else {
            return ContentContract.AUTHORITY;
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        int uriRequest = uriMatcher.match(uri);
        Uri insertUri;
        long insertid;

        if (uriRequest == ContentContract.TASK){
            insertid = db.insert(ContentContract.TABLE, null, contentValues);
            if (insertid > 0){
                insertUri = ContentUris.withAppendedId(ContentContract.CONTENT_URI, insertid);
            } else {
                throw new SQLException("Failed to insert row into " + uri.getPath());
            }
        } else {
            throw new UnsupportedOperationException("unknown uri");
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return insertUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        int uriRequest = uriMatcher.match(uri);
        int count = 0;
        String defSelected;
        if (selection.equals("")){
            defSelected = "1";
        } else {
            defSelected = selection;
        }

        if (uriRequest == ContentContract.TASK){
            count = db.delete(ContentContract.TABLE, defSelected, selectionArgs);
        } else {
            throw new UnsupportedOperationException("unknown uri");
        }

        if (count > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = sqlHelper.getReadableDatabase();
        int uriRequest = uriMatcher.match(uri);
        int count = 0;
        String defSelected;
        if (selection.equals("")){
            defSelected = "1";
        } else {
            defSelected = selection;
        }

        if (uriRequest == ContentContract.TASK){
            count = db.update(ContentContract.TABLE, contentValues, defSelected, selectionArgs);
        } else {
            throw new UnsupportedOperationException("unknown uri");
        }

        if (count > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return count;
    }
}
