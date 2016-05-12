package com.deserteaglefe.seventhweek;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.deserteaglefe.seventhweek.Adapter.CityInfoAdapter;
import com.deserteaglefe.seventhweek.Adapter.ProvinceAdapter;
import com.deserteaglefe.seventhweek.GsonDataClass.CityData;

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
    private int mProvinceId;
    private List<CityData.CityInfo> mCityList;
    private CityInfoAdapter mCityInfoAdapter;

    private CityFoundReceiver mCityFoundReceiver;

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(Constants.CITY_SELECTOR_TITLE);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_selector);
        setReceiver();     // 设置广播接收器
        getOriginalCity(); // 获取Intent传递的数据
        findViews();       // 关联视图
        setAdapter();      // 设置Adapter
        setListeners();    // 设置监听器
    }

    private void getOriginalCity() {
        Intent intent = getIntent();
        mCitySelected = intent.getStringExtra(Constants.ORIGINAL_CITY);
        mProvinceId = intent.getIntExtra(Constants.ORIGINAL_PROVINCE, 0);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Constants.QUERY_PROV_ACTION);
        sendIntent.putExtra(Constants.PROV_ID, mProvinceId);
        sendBroadcast(sendIntent);
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
                Intent intent = new Intent();
                intent.setAction(Constants.QUERY_PROV_ACTION);
                intent.putExtra(Constants.PROV_ID, mProvinceId);
                sendBroadcast(intent);
                Log.i(Constants.TAG, "On Click");
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

    private void setReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.FOUND_PROV_ACTION);
        mCityFoundReceiver = new CityFoundReceiver();
        registerReceiver(mCityFoundReceiver, intentFilter);
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
        Log.i(Constants.TAG, "refreshData");
        mCityInfoAdapter.refreshData(mCityList);

        Log.i(Constants.TAG, "notifyDataSetChanged");
        mCityInfoAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        returnData();
        unregisterReceiver(mCityFoundReceiver);
    }

    private void returnData() {
        Intent intent = new Intent();
        intent.putExtra(Constants.CITY_SELECTED, mCitySelected);
        intent.putExtra(Constants.PROVINCE_SELECTED, mProvinceId);
        setResult(RESULT_OK, intent);
        finish();
    }

    public class CityFoundReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            // receive broadcast, handle data
            Log.i(Constants.TAG, "Selector On Receive");
            if (intent != null) {
                mCityList = intent.getParcelableArrayListExtra(Constants.CITY_LIST);
                Log.i(Constants.TAG,"How many cities: " + mCityList.size());
                mTextView.setText(Constants.CITY_SELECTOR_TEXT);
                refreshData();
            }
        }
    }
}
