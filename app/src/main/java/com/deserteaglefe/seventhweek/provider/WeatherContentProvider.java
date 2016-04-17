package com.deserteaglefe.seventhweek.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.deserteaglefe.seventhweek.Constants;
import com.deserteaglefe.seventhweek.Database.WeatherDatabaseHelper;

/**
 * Function: the Content Provider
 * Create date on 2016/4/12.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class WeatherContentProvider extends ContentProvider {
    private static UriMatcher sUriMatcher;

    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        sUriMatcher.addURI(Constants.AUTHORITY, Constants.DAILY_TABLE_NAME, Constants.URI_MATCH_DAILY);
        sUriMatcher.addURI(Constants.AUTHORITY, Constants.HOURLY_TABLE_NAME, Constants.URI_MATCH_HOURLY);
    }

    private WeatherDatabaseHelper mWeatherDatabaseHelper;

    @Override
    public boolean onCreate() {
        mWeatherDatabaseHelper = new WeatherDatabaseHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String tableName =getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            return null;
        }
        return mWeatherDatabaseHelper.getReadableDatabase()
                .query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }


    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        String tableName =getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            return null;
        }

        long id = mWeatherDatabaseHelper.getWritableDatabase().insert(tableName, null, values);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String tableName =getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            return -1;
        }
        return mWeatherDatabaseHelper.getWritableDatabase().delete(tableName,selection,selectionArgs);
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String tableName =getTableName(uri);
        if(TextUtils.isEmpty(tableName)){
            return -1;
        }
        return mWeatherDatabaseHelper.getWritableDatabase().update(tableName,values,selection,selectionArgs);
    }

    private String getTableName(Uri uri){
        int type = sUriMatcher.match(uri);
        switch (type){
            case Constants.URI_MATCH_DAILY:
                return Constants.DAILY_TABLE_NAME;
            case Constants.URI_MATCH_HOURLY:
                return Constants.HOURLY_TABLE_NAME;
            default:
                return null;
        }
    }
}
