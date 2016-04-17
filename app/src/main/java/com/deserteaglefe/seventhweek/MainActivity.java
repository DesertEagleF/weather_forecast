package com.deserteaglefe.seventhweek;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.deserteaglefe.seventhweek.Database.WeatherDatabaseHelper;
import com.deserteaglefe.seventhweek.GsonDataClass.WeatherData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Weather Forecast App
 * Create date on 2016/4/12.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    // UI 项
    private String mCityString;  // 城市名 City Name
    private int mWeatherCode;    // 天气码 Weather Code
    private String mWeatherName; // 天气名 Weather
    private int mTemperature;    // 温度 Temperature
    private int mAqi;            // 空气质量 Air Quality Index
    private int mProvinceId;
    private String[] mDate = new String[7];
    private int[] mWeather_day = new int[7];
    private int[] mWeather_night = new int[7];
    private int[] mMaxTemperature = new int[7];
    private int[] mMinTemperature = new int[7];
    private TextView mCityTextView;
    private ImageView mWeatherImageView;
    private TextView mTemperatureTextView;
    private TextView mWeatherTextView;
    private TextView mAqiTextView;
    private TextView[] mDailyTextView = new TextView[7];
    private ImageView[] mDayWeatherImageView = new ImageView[7];
    private TextView[] mDailyMaxTextView = new TextView[7];
    private TextView[] mDailyMinTextView = new TextView[7];
    private ImageView[] mNightWeatherImageView = new ImageView[7];
    private Button mRefreshButton;
    private Button mCitySelectorButton;
    private TextView mStatusTextView;
    private boolean isFirstQuery = true;
    private boolean isFinishQuery = true;

    // Handler
    private WeatherHandler mWeatherHandler;
    private QueryHandler mQueryHandler;

    // 存储、数据库
    private SharedPreferences mSharedPreferences;
    private WeatherData mWeatherData;
    private SQLiteDatabase mSqLiteDatabase;
    private String mToastString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        // AppCompatActivity 隐藏标题栏的方法
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findViews();    // 关联视图
        init();         // 初始化成员
        loadData();     // 加载本地缓存数据并刷新
        setListeners(); // 设置监听器
        exeQuery();     // 从网络获取数据并刷新
    }

    // 初始化成员
    private void init() {
        mWeatherHandler = new WeatherHandler(MainActivity.this);
        ContentResolver contentResolver = getContentResolver();
        mQueryHandler = new QueryHandler(contentResolver, this);
        WeatherDatabaseHelper weatherDatabaseHelper = new WeatherDatabaseHelper(this);
        mSqLiteDatabase = weatherDatabaseHelper.getWritableDatabase();
    }

    // Load SharedPreferences
    private void loadData() {
        mSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        mCityString = mSharedPreferences.getString(Constants.CITY_NAME, Constants.DEFAULT_CITY_NAME);
        mWeatherCode = mSharedPreferences.getInt(Constants.WEATHER_CODE, Constants.DEFAULT_WEATHER_CODE);
        mWeatherName = mSharedPreferences.getString(Constants.WEATHER_NAME, Constants.DEFAULT_WEATHER_NAME);
        mTemperature = mSharedPreferences.getInt(Constants.TEMPERATURE, Constants.DEFAULT_TEMPERATURE);
        mAqi = mSharedPreferences.getInt(Constants.AQI, Constants.DEFAULT_AQI);
        mProvinceId = mSharedPreferences.getInt(Constants.PROVINCE, Constants.DEFAULT_PROVINCE_ID);

        // 异步查询数据库
        mQueryHandler.startQuery(0, null, Uri.parse(Constants.DAILY_URI), null, null, null, null);

        // 更新视图
        refreshView();
    }

    // 加载异步查询数据库所返回的结果
    private void loadSQL(Cursor cursor) {
        int count = 0;
        if (cursor != null && cursor.moveToFirst()) {
            count = cursor.getCount();
            for (int i = 0; i < Math.min(7, count); i++) {
                mDate[i] = cursor.getString(cursor.getColumnIndexOrThrow(Constants.DATE_NAME));
                mWeather_day[i] = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.WEATHER_CODE_DAY));
                mWeather_night[i] = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.WEATHER_CODE_NIGHT));
                mMaxTemperature[i] = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.MAX_TEMPERATURE));
                mMinTemperature[i] = cursor.getInt(cursor.getColumnIndexOrThrow(Constants.MIN_TEMPERATURE));
                Log.i(Constants.TAG, "Load SQL #" + i);
                cursor.moveToNext();
            }
        }
        if (cursor != null) {
            cursor.close();
        }

        // SQL数据不存在的时候
        if (count < 7) {
            for (int i = count; i < 7; i++) {
                mDate[i] = Constants.DEFAULT_DATE_TODAY;
                mWeather_day[i] = Constants.getIcon(999);
                mWeather_night[i] = Constants.getIcon(999);
                mMaxTemperature[i] = Constants.DEFAULT_TEMPERATURE;
                mMinTemperature[i] = Constants.DEFAULT_TEMPERATURE;
                Log.i(Constants.TAG, "Load Default #" + i);
            }
        }
        refreshView(); // 更新视图
    }

    // 存储数据
    private void saveData() {
        mSharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(Constants.CITY_NAME, mCityString);
        editor.putInt(Constants.WEATHER_CODE, mWeatherCode);
        editor.putString(Constants.WEATHER_NAME, mWeatherName);
        editor.putInt(Constants.TEMPERATURE, mTemperature);
        editor.putInt(Constants.AQI, mAqi);
        editor.putInt(Constants.PROVINCE, mProvinceId);
        editor.apply();

        mSqLiteDatabase.execSQL("delete from " + Constants.DAILY_TABLE_NAME);

        // 异步写入数据库
        for (int i = 0; i < 7; i++) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(Constants.DATE_NAME, mDate[i]);
            contentValues.put(Constants.WEATHER_CODE_DAY, mWeather_day[i]);
            contentValues.put(Constants.WEATHER_CODE_NIGHT, mWeather_night[i]);
            contentValues.put(Constants.MAX_TEMPERATURE, mMaxTemperature[i]);
            contentValues.put(Constants.MIN_TEMPERATURE, mMinTemperature[i]);
            mQueryHandler.startInsert(1, null, Uri.parse(Constants.DAILY_URI), contentValues);
            Log.i(Constants.TAG, "save SQL #" + i + ": " + contentValues.toString());
        }
    }

    // Update data after the query
    private void updateData() {
        mCityString = mWeatherData.getHeWeatherData().get(0).getBasic().getCity();
        mWeatherCode = mWeatherData.getHeWeatherData().get(0).getNow().getCond().getCode();
        mWeatherName = mWeatherData.getHeWeatherData().get(0).getNow().getCond().getTxt();
        mTemperature = mWeatherData.getHeWeatherData().get(0).getNow().getTmp();
        if( mWeatherData.getHeWeatherData().get(0).getAqi() !=  null) {
            mAqi = mWeatherData.getHeWeatherData().get(0).getAqi().getCity().getAqi();
        }else{
            mAqi = -50;
        }
        for (int i = 0; i < 7; i++) {
            mDate[i] = mWeatherData.getHeWeatherData().get(0).getDaily_forecast().get(i).getDate();
            mWeather_day[i] = mWeatherData.getHeWeatherData().get(0).getDaily_forecast().get(i).getCond().getCode_d();
            mWeather_night[i] = mWeatherData.getHeWeatherData().get(0).getDaily_forecast().get(i).getCond().getCode_n();
            mMaxTemperature[i] = mWeatherData.getHeWeatherData().get(0).getDaily_forecast().get(i).getTmp().getMax();
            mMinTemperature[i] = mWeatherData.getHeWeatherData().get(0).getDaily_forecast().get(i).getTmp().getMin();
        }
    }

    // Associate views with variables
    private void findViews() {
        mCityTextView = (TextView) findViewById(R.id.city_text_view);
        mWeatherImageView = (ImageView) findViewById(R.id.weather_imageView);
        mTemperatureTextView = (TextView) findViewById(R.id.temperature_text_view);
        mWeatherTextView = (TextView) findViewById(R.id.weather_text_view);
        mAqiTextView = (TextView) findViewById(R.id.air_quality_index);
        mDailyTextView[0] = (TextView) findViewById(R.id.date_0);
        mDailyTextView[1] = (TextView) findViewById(R.id.date_1);
        mDailyTextView[2] = (TextView) findViewById(R.id.date_2);
        mDailyTextView[3] = (TextView) findViewById(R.id.date_3);
        mDailyTextView[4] = (TextView) findViewById(R.id.date_4);
        mDailyTextView[5] = (TextView) findViewById(R.id.date_5);
        mDailyTextView[6] = (TextView) findViewById(R.id.date_6);
        mDayWeatherImageView[0] = (ImageView) findViewById(R.id.weather_day_0);
        mDayWeatherImageView[1] = (ImageView) findViewById(R.id.weather_day_1);
        mDayWeatherImageView[2] = (ImageView) findViewById(R.id.weather_day_2);
        mDayWeatherImageView[3] = (ImageView) findViewById(R.id.weather_day_3);
        mDayWeatherImageView[4] = (ImageView) findViewById(R.id.weather_day_4);
        mDayWeatherImageView[5] = (ImageView) findViewById(R.id.weather_day_5);
        mDayWeatherImageView[6] = (ImageView) findViewById(R.id.weather_day_6);
        mNightWeatherImageView[0] = (ImageView) findViewById(R.id.weather_night_0);
        mNightWeatherImageView[1] = (ImageView) findViewById(R.id.weather_night_1);
        mNightWeatherImageView[2] = (ImageView) findViewById(R.id.weather_night_2);
        mNightWeatherImageView[3] = (ImageView) findViewById(R.id.weather_night_3);
        mNightWeatherImageView[4] = (ImageView) findViewById(R.id.weather_night_4);
        mNightWeatherImageView[5] = (ImageView) findViewById(R.id.weather_night_5);
        mNightWeatherImageView[6] = (ImageView) findViewById(R.id.weather_night_6);
        mDailyMaxTextView[0] = (TextView) findViewById(R.id.temperature_max_0);
        mDailyMaxTextView[1] = (TextView) findViewById(R.id.temperature_max_1);
        mDailyMaxTextView[2] = (TextView) findViewById(R.id.temperature_max_2);
        mDailyMaxTextView[3] = (TextView) findViewById(R.id.temperature_max_3);
        mDailyMaxTextView[4] = (TextView) findViewById(R.id.temperature_max_4);
        mDailyMaxTextView[5] = (TextView) findViewById(R.id.temperature_max_5);
        mDailyMaxTextView[6] = (TextView) findViewById(R.id.temperature_max_6);
        mDailyMinTextView[0] = (TextView) findViewById(R.id.temperature_min_0);
        mDailyMinTextView[1] = (TextView) findViewById(R.id.temperature_min_1);
        mDailyMinTextView[2] = (TextView) findViewById(R.id.temperature_min_2);
        mDailyMinTextView[3] = (TextView) findViewById(R.id.temperature_min_3);
        mDailyMinTextView[4] = (TextView) findViewById(R.id.temperature_min_4);
        mDailyMinTextView[5] = (TextView) findViewById(R.id.temperature_min_5);
        mDailyMinTextView[6] = (TextView) findViewById(R.id.temperature_min_6);

        mRefreshButton = (Button) findViewById(R.id.immediately_refresh);
        mCitySelectorButton = (Button) findViewById(R.id.select_city_activity_button);
        mStatusTextView = (TextView) findViewById(R.id.status_text_view);
    }

    // set Listeners for each Button
    private void setListeners() {
        mRefreshButton.setOnClickListener(this);
        mCitySelectorButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (isFinishQuery) {
            switch (v.getId()) {
                case R.id.immediately_refresh:
                    isFinishQuery = false;
                    exeQuery();    // 从网络获取数据并刷新
                    break;
                case R.id.select_city_activity_button:
                    isFinishQuery = false;
                    Intent intent = new Intent(MainActivity.this, CitySelectorActivity.class);
                    intent.putExtra(Constants.ORIGINAL_CITY, mCityString);
                    intent.putExtra(Constants.ORIGINAL_PROVINCE, mProvinceId);
                    startActivityForResult(intent, Constants.REQUEST_CITY_SELECTOR);
                    break;
            }
        } else {
            Toast.makeText(MainActivity.this, Constants.PLEASE_WAIT, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.REQUEST_CITY_SELECTOR && resultCode == RESULT_OK) {
            mProvinceId = data.getIntExtra(Constants.PROVINCE_SELECTED, 0);
            exeQuery(data.getStringExtra(Constants.CITY_SELECTED));    // 从网络获取数据并刷新
        }else{
            isFinishQuery = true;
        }
    }

    // refresh view after data changed.
    private void refreshView() {
        mCityTextView.setText(mCityString);
        mWeatherImageView.setImageResource(Constants.getIcon(mWeatherCode));
        String temperatureText = mTemperature + Constants.DEGREE_NAME;
        mTemperatureTextView.setText(temperatureText);
        mWeatherTextView.setText(mWeatherName);

        // 免费版服务器不反回小城市/城镇的空气质量详细数据(含等级名称)，因此需自行处理
        int index = mAqi / 50;
        int color;
        String airQuality;
        if(index < 0){
            mAqiTextView.setText(" ");
        }else if(index < 6){
            airQuality = Constants.AIR_QUALITY_NAME[index];
            color = Constants.AQI_TEXT_COLOR[index];
            String aqiText = Constants.AQI_TEXT + mAqi + "\n" + airQuality;
            mAqiTextView.setText(aqiText);
            mAqiTextView.setTextColor(color);
        }else{
            airQuality = Constants.AIR_QUALITY_NAME[6];
            color = Constants.AQI_TEXT_COLOR[6];
            String aqiText = Constants.AQI_TEXT + mAqi + " " + airQuality;
            mAqiTextView.setText(aqiText);
            mAqiTextView.setTextColor(color);
        }

        for (int i = 0; i < 7; i++) {
            mDailyTextView[i].setText(mDate[i]);
            mDayWeatherImageView[i].setImageResource(Constants.getIcon(mWeather_day[i]));
            mNightWeatherImageView[i].setImageResource(Constants.getIcon(mWeather_night[i]));
            mDailyMaxTextView[i].setText(String.valueOf(mMaxTemperature[i]));
            mDailyMinTextView[i].setText(String.valueOf(mMinTemperature[i]));
        }
    }

    public void exeQuery() {
        exeQuery(mCityString);
    }

    public void exeQuery(String city) {
        new RequireNetworkDataTask().execute(city);
    }

    private int request(String city) {
        int code;
        StringBuilder sbf = new StringBuilder();

        try {
            String query_city = URLEncoder.encode(city, "utf-8");
            String httpUrl = Constants.HTTP_URL + "?city=" + query_city;
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入 apikey 到HTTP header
            connection.setRequestProperty("apikey", "100cf35cfb6afa8f88e34329c51d9b9e");
            connection.connect();
            Log.i(Constants.TAG, connection.toString());
            InputStream is = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            String result = sbf.toString();
            Gson gson = new Gson();
            mWeatherData = gson.fromJson(result, WeatherData.class);
            code = 0;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            code = 1;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            code = 2;
        } catch (ProtocolException e) {
            e.printStackTrace();
            code = 3;
        } catch (IOException e) {
            e.printStackTrace();
            code = 4;
        }
        return code;
    }

    private void setWeatherData(int code) {

        if (code == 0) {
            if (TextUtils.equals(mWeatherData.getHeWeatherData().get(0).getStatus(), "ok")) {
                updateData();
                saveData();
            } else {
                code = 5;
            }
        }
        mToastString = Constants.getToast(code);
    }

    private static class QueryHandler extends AsyncQueryHandler {
        MainActivity mActivity;

        public QueryHandler(ContentResolver cr, MainActivity activity) {
            super(cr);
            mActivity = activity;
        }

        @Override
        protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
            super.onQueryComplete(token, cookie, cursor);
            // 更新mAdapter的Cursor
            mActivity.loadSQL(cursor);
        }

        @Override
        protected void onInsertComplete(int token, Object cookie, Uri uri) {
            super.onInsertComplete(token, cookie, uri);
        }
    }

    public static class WeatherHandler extends Handler {
        public final WeakReference<MainActivity> mMainActivityWeakReference;

        public WeatherHandler(MainActivity activity) {
            mMainActivityWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity mainActivity = mMainActivityWeakReference.get();

            super.handleMessage(msg);
            Log.i(Constants.TAG, "handleMessage");

            //接收消息
            switch (msg.what) {
                case Constants.MESSAGE_CODE_MAIN_THREAD:
                    mainActivity.exeQuery();
                    // 固定时间刷新
                    Message message = Message.obtain();
                    message.arg1 = 0;
                    message.what = Constants.MESSAGE_CODE_MAIN_THREAD;
                    sendMessageDelayed(message, Constants.AUTO_REFRESH_PERIOD);
                    break;
            }
        }
    }

    // 异步任务处理类
    class RequireNetworkDataTask extends AsyncTask<String, Integer, Integer> {
        // 在执行后台工作之前，在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mStatusTextView.setText("请求中…");
            // UI Loading
        }

        // 后台执行，必须实现的方法
        @Override
        protected Integer doInBackground(String[] params) {
            int code = request(params[0]);
            setWeatherData(code);
            if (isFirstQuery) {
                Message message = mWeatherHandler.obtainMessage();
                message.arg1 = 0;
                message.what = Constants.MESSAGE_CODE_MAIN_THREAD;
                mWeatherHandler.sendMessageDelayed(message, Constants.AUTO_REFRESH_PERIOD);
                isFirstQuery = false;
            }
            return code;
        }

        // 在执行后台工作之后，在主线程中
        // s 为 doInBackground()的返回值
        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            isFinishQuery = true;
            refreshView(); // 更新视图
            Toast.makeText(MainActivity.this, mToastString, Toast.LENGTH_SHORT).show();
            mStatusTextView.setText("");
        }

        // 取消
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        // 更新
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}