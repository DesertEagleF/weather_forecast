package com.deserteaglefe.seventhweek;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.deserteaglefe.seventhweek.Adapter.CityInfoAdapter;
import com.deserteaglefe.seventhweek.Adapter.ProvinceAdapter;
import com.deserteaglefe.seventhweek.GsonDataClass.CityData;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Function: An activity for city selection of Main UI
 * 城市列表一般不会变，因此采用本地json存储，避免网络查询，减少等待时间减少耗费流量
 * Create date on 2016/4/12.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class CitySelectorActivity extends AppCompatActivity {
    String mCitySelected = Constants.DEFAULT_CITY_NAME;
    private TextView mTextView;
    private ListView mListView;
    private GridView mGridView;
    private CityData mCityData;
    private int mProvinceId;
    private List<CityData.CityInfo> mCityList;
    private CityInfoAdapter mCityInfoAdapter;
    private boolean isFirstLoad = true;
    private int mCode = 0;

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(Constants.CITY_SELECTOR_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        getOriginalCity(); // 获取Intent传递的数据
        findViews();       // 关联视图
        setData();         // 设置数据
        setAdapter();      // 设置Adapter
        setListeners();    // 设置监听器
    }

    private void getOriginalCity() {
        Intent intent = getIntent();
        mCitySelected = intent.getStringExtra(Constants.ORIGINAL_CITY);
        mProvinceId = intent.getIntExtra(Constants.ORIGINAL_PROVINCE, 0);
    }

    private void findViews() {
        mTextView = (TextView) findViewById(R.id.enter_city_tips_text);
        mListView = (ListView) findViewById(R.id.list_view);
        mGridView = (GridView) findViewById(R.id.grid_view);
    }

    private void setListeners() {
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mProvinceId = position;
                new LoadJsonTask().execute(position);
                Log.i(Constants.TAG,"On Click");
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mCitySelected = mCityList.get(position).getCity();
                returnData();
            }
        });
    }

    private void queryData(int position) {
        mCityList.clear();
        for (CityData.CityInfo ci : mCityData.getCity_info()) {
            if(TextUtils.equals(ci.getProv(),Constants.PROVINCE_ARRAY[position])){
                mCityList.add(ci);
            }
        }
    }

    private void setData() {
        new LoadJsonTask().execute(mProvinceId);
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

    private void toastCityData(int code) {
        mCode = code;
        if(code != 0){
            Toast.makeText(CitySelectorActivity.this, Constants.getToast(code), Toast.LENGTH_SHORT).show();
        }
    }

    public void setAdapter() {
        mCityList = new ArrayList<>();
        Log.i(Constants.TAG, "setAdapter");
        ProvinceAdapter provinceAdapter = new ProvinceAdapter(CitySelectorActivity.this);
        mGridView.setAdapter(provinceAdapter);

        mCityInfoAdapter = new CityInfoAdapter(CitySelectorActivity.this, mCityList);
        mListView.setAdapter(mCityInfoAdapter);
    }

    public void refreshData() {
        Log.i(Constants.TAG,"refreshData");
        mCityInfoAdapter.refreshData(mCityList);

        Log.i(Constants.TAG,"notifyDataSetChanged");
        mCityInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        returnData();
    }

    private void returnData() {
        Intent intent = new Intent();
        intent.putExtra(Constants.CITY_SELECTED, mCitySelected);
        intent.putExtra(Constants.PROVINCE_SELECTED, mProvinceId);
        setResult(RESULT_OK, intent);
        finish();
    }

    // 异步任务处理类
    class LoadJsonTask extends AsyncTask<Integer, Integer, Integer> {
        // 在执行后台工作之前，在主线程中
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // UI Loading
        }

        // 后台执行，必须实现的方法
        @Override
        protected Integer doInBackground(Integer[] params) {
            int code = mCode;
            if(isFirstLoad){
                code = request();
                isFirstLoad = false;
            }
            queryData(mProvinceId);
            return code;
        }

        // 在执行后台工作之后，在主线程中
        @Override
        protected void onPostExecute(Integer i) {
            super.onPostExecute(i);
            mTextView.setText(Constants.CITY_SELECTOR_TEXT);
            refreshData();
            toastCityData(i);
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
