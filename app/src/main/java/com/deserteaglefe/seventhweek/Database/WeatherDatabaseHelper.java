package com.deserteaglefe.seventhweek.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.deserteaglefe.seventhweek.Constants;


/**
 * Function: Database Helper Class
 * Create date on 2016/4/14.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class WeatherDatabaseHelper extends SQLiteOpenHelper {

    public WeatherDatabaseHelper(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(Constants.TAG,"SQL OnCreate");
        db.execSQL("create table " + Constants.DAILY_TABLE_NAME + "(" +
                Constants.DATE_NAME + " varchar(11) not null, " +
                Constants.WEATHER_CODE_DAY + " int not null, " +
                Constants.WEATHER_CODE_NIGHT + " int not null, " +
                Constants.MAX_TEMPERATURE + " int not null, " +
                Constants.MIN_TEMPERATURE + " int not null, " +
                Constants.WEATHER_TEXT_DAY + " varchar(30), " +
                Constants.WEATHER_TEXT_NIGHT + " varchar(30), " +
                Constants.SUN_RISE + " varchar(6), " +
                Constants.SUN_SET + " varchar(6), " +
                Constants.HUMIDITY + " int, " +
                Constants.PRECIPITATION + " float, " +
                Constants.PRECIPITATE_PROBABILITY + " int, " +
                Constants.PRESSURE + " float, " +
                Constants.VISIBILITY + " int, " +
                Constants.WIND_DEGREE + " int, " +
                Constants.WIND_DIRECTION + " varchar(12), " +
                Constants.WIND_SCALE + "  varchar(12), " +
                Constants.WIND_SPEED + " int);");

        db.execSQL("create table " + Constants.HOURLY_TABLE_NAME + "(" +
                Constants.DATE_NAME + " varchar(20) not null, " +
                Constants.HUMIDITY + " int, " +
                Constants.PRECIPITATE_PROBABILITY + " int, " +
                Constants.PRESSURE + " float, " +
                Constants.WIND_DEGREE + " int, " +
                Constants.WIND_DIRECTION + " varchar(12), " +
                Constants.WIND_SCALE + "  varchar(12), " +
                Constants.WIND_SPEED + " int);");

        db.execSQL("create table " + Constants.CITY_TABLE_NAME + "(" +
                Constants.CITY_NAME + " varchar(20) not null, " +
                Constants.LATITUDE + " float, " +
                Constants.LONGITUDE + " float, " +
                Constants.PROVINCE + " varchar(12) not null);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
