package com.deserteaglefe.seventhweek.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.deserteaglefe.seventhweek.Constants;
import com.deserteaglefe.seventhweek.R;

/**
 * Function: ListView Adapter
 * Create date on 2016/4/16.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class ProvinceAdapter extends BaseAdapter {

    private LayoutInflater mLayoutInflater;

    public ProvinceAdapter(Context context) {
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // 有多少条数据
        return Constants.PROVINCE_ARRAY.length;
    }

    @Override
    public Object getItem(int position) {
        // 返回某一条数据对象
        return Constants.PROVINCE_ARRAY[position];
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
            convertView = mLayoutInflater.inflate(R.layout.province_item, null);
            viewHolder = new ViewHolder();
            // 获取控件
            viewHolder.provinceTextView = (TextView) convertView.findViewById(R.id.province_item);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 和数据之间进行绑定
        viewHolder.provinceTextView.setText (Constants.PROVINCE_ARRAY[position]);

        return convertView;
    }

    class ViewHolder {
        TextView provinceTextView;
    }
}
