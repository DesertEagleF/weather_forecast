package com.deserteaglefe.seventhweek.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.deserteaglefe.seventhweek.Constants;
import com.deserteaglefe.seventhweek.GsonDataClass.CityData;
import com.deserteaglefe.seventhweek.Location;
import com.deserteaglefe.seventhweek.R;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

/**
 *
 * Created by zyl on 2016/5/11.
 */
public class WeatherService extends Service {
    private CityData mCityData;
    private ArrayList<CityData.CityInfo> mCityList = new ArrayList<>();
    private int mProvinceId;

    private IncomingHandler mIncomingHandler = new IncomingHandler(this);
    private Messenger mMessenger = new Messenger(mIncomingHandler);
    private static Messenger serviceMessenger;

    private WeatherBroadcastReceiver mWeatherBroadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        setReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mWeatherBroadcastReceiver);
    }

    public void load(){
        new LoadJsonTask().execute();
    }

    public void queryCity(int position){
        new QueryJsonTask().execute(position);
    }
    private void queryData(int position) {
        mCityList.clear();
        for (CityData.CityInfo ci : mCityData.getCity_info()) {
            if(TextUtils.equals(ci.getProv(),Constants.PROVINCE_ARRAY[position])){
                mCityList.add(ci);
            }
        }
    }


    private void setReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.QUERY_PROV_ACTION);
        mWeatherBroadcastReceiver = new WeatherBroadcastReceiver();
        registerReceiver(mWeatherBroadcastReceiver, intentFilter);
    }

    private void find(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double distance = Double.MAX_VALUE;
        String city = null;
        int provId = 0;
        for (int i = 0; i < mCityData.getCity_info().size(); i++) {
            float lat = mCityData.getCity_info().get(i).getLat();
            float lon = mCityData.getCity_info().get(i).getLon();
            double dis = (lat - latitude) * (lat - latitude) + (lon - longitude) * (lon - longitude);
            if(dis < distance){
                city = mCityData.getCity_info().get(i).getCity();
                distance = dis;
                for (int j = 0; j < Constants.PROVINCE_ARRAY.length; j++) {
                    if(TextUtils.equals(mCityData.getCity_info().get(i).getProv(),Constants.PROVINCE_ARRAY[j])){
                        provId = j;
                        break;
                    }
                }
            }
        }
        Log.i(Constants.TAG,"Service Find you at " + city);
        mProvinceId = provId;
        Message mMessage = Message.obtain();
        mMessage.what = Constants.FOUND_YOU;
        mMessage.arg1 = provId;
        Bundle bundle = new Bundle();
        bundle.putString(Constants.CITY_NAME, city);
        mMessage.setData(bundle);
        try {
            serviceMessenger.send(mMessage);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private int request() {
        int code;
        StringBuilder sbf = new StringBuilder();

        try {
            InputStream is = getResources().openRawResource(R.raw.city);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            String result = sbf.toString();
            Gson gson = new Gson();
            mCityData = gson.fromJson(result, CityData.class);
            code = 0;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            code = 2;
        } catch (IOException e) {
            e.printStackTrace();
            code = 4;
        }
        return code;
    }

    public class WeatherBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // receive broadcast, handle data
            if (intent != null) {
                Log.i(Constants.TAG, "Service On Receive");
                new QueryJsonTask().execute(intent.getIntExtra(Constants.PROV_ID, mProvinceId));
            }
        }
    }

    static class IncomingHandler extends Handler {
        private WeakReference<WeatherService> mWeatherServiceWeakReference;

        public IncomingHandler(WeatherService weatherService) {
            mWeatherServiceWeakReference = new WeakReference<>(weatherService);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            serviceMessenger = msg.replyTo;
            WeatherService weatherService = mWeatherServiceWeakReference.get();
            // 处理消息
            switch (msg.what) {
                case Constants.SERVICE_PREPARE:
                    Log.i(Constants.TAG,"Service Preparing...");
                    weatherService.load();
                    break;
                case Constants.FIND_NEAREST_CITY:
                    Log.i(Constants.TAG,"Service Finding...");
                    msg.getData().setClassLoader(getClass().getClassLoader());
                    weatherService.find((Location) msg.getData().getParcelable(Constants.LOCATION));
                    break;
                case Constants.QUERY_CITY:
                    weatherService.queryCity(msg.arg1);
                    break;
            }
        }
    }

    // 异步任务处理类：加载城市信息
    class LoadJsonTask extends AsyncTask<Integer, Integer, Integer> {
        // 在执行后台工作之前，在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        // 后台执行，必须实现的方法
        @Override
        protected Integer doInBackground(Integer[] params) {
            return request();
        }

        // 在执行后台工作之后，在主线程中
        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            Message mMessage = Message.obtain();
            mMessage.what = Constants.SERVICE_READY;
            try {
                serviceMessenger.send(mMessage);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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

    // 异步任务处理类
    class QueryJsonTask extends AsyncTask<Integer, Integer, Integer> {
        // 在执行后台工作之前，在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // UI Loading
        }

        // 后台执行，必须实现的方法
        @Override
        protected Integer doInBackground(Integer[] params) {
            queryData(params[0]);
            return params[0];
        }

        // 在执行后台工作之后，在主线程中
        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            mProvinceId = i;
            Log.i(Constants.TAG,"Province: " + Constants.PROVINCE_ARRAY[i]);
            Intent intent = new Intent();
            intent.setAction(Constants.FOUND_PROV_ACTION);
            intent.putParcelableArrayListExtra(Constants.CITY_LIST, mCityList);
            Log.i(Constants.TAG,"How many cities: " + mCityList.size());
            sendBroadcast(intent);
            Log.i(Constants.TAG,"Service Send Broadcast");
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMessenger.getBinder();
    }
}
