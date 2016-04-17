package com.deserteaglefe.seventhweek.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deserteaglefe.seventhweek.GsonDataClass.CityData;
import com.deserteaglefe.seventhweek.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Function: ListView Adapter
 * Create date on 2016/4/16.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class CityInfoAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    private List<CityData.CityInfo> mCityInfos = new ArrayList<>();

    public CityInfoAdapter(Context context, List<CityData.CityInfo> cityInfos) {
        mCityInfos = cityInfos;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // 有多少条数据
        return mCityInfos.size();
    }

    @Override
    public Object getItem(int position) {
        // 返回某一条数据对象
        return mCityInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public int getItemViewType(int position) {


        return super.getItemViewType(position);
    }

    /**
     * 每一行数据显示在界面，用户能够看到时
     *
     * @param position : 位置
     * @param convertView : convertView
     * @param parent : ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // 返回一个视图

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.city_item, null);
            viewHolder = new ViewHolder();
            // 获取控件
            viewHolder.cityNameTextView = (TextView) convertView.findViewById(R.id.city_name);
            viewHolder.latitudeTextView = (TextView) convertView.findViewById(R.id.latitude);
            viewHolder.longitudeTextView = (TextView) convertView.findViewById(R.id.longitude);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 和数据之间进行绑定
        viewHolder.cityNameTextView.setText (mCityInfos.get(position).getCity());
        viewHolder.latitudeTextView.setText (String.valueOf(mCityInfos.get(position).getLat()));
        viewHolder.longitudeTextView.setText(String.valueOf(mCityInfos.get(position).getLon()));

        return convertView;
    }

    class ViewHolder {
        TextView cityNameTextView;
        TextView latitudeTextView;
        TextView longitudeTextView;
    }


    /**
     * 刷新数据
     *
     * @param cityInfos : List of cityInfo
     */
    public void refreshData(List<CityData.CityInfo> cityInfos) {
        mCityInfos = cityInfos;
    }
}
