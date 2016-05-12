package com.deserteaglefe.seventhweek;

import android.graphics.Color;

import java.util.HashMap;

/**
 * Function: All Constants
 * Create date on 2016/4/12.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class Constants {
    // TAG
    public static final String TAG = MainActivity.class.getSimpleName();

    // Broadcast type
    public static final int MESSAGE_CODE_MAIN_THREAD = 0x00000000;
    public static final int SERVICE_PREPARE = 0x00000001;
    public static final int SERVICE_READY = 0x00000002;
    public static final int FIND_NEAREST_CITY = 0x00000003;
    public static final int FOUND_YOU = 0x00000004;
    public static final int QUERY_CITY = 0x00000005;

    public static final int REQUEST_CITY_SELECTOR = 0x00000010;
    public static final int AUTO_REFRESH_PERIOD = 3600000; // 刷新周期：1小时

    // Intent constants
    public static final String ORIGINAL_CITY = "original_city";
    public static final String ORIGINAL_PROVINCE = "original_province";
    public static final String CITY_SELECTED = "city_selected";
    public static final String PROVINCE_SELECTED = "province_selected";
    public static final String LOCATION = "location";
    public static final String QUERY_PROV_ACTION = "query_prov_action";
    public static final String PROV_ID = "prov_id";
    public static final String FOUND_PROV_ACTION = "found_prov_action";
    public static final String CITY_LIST = "city_list";

    // String constants
    public static final String HTTP_URL = "http://apis.baidu.com/heweather/weather/free";
    public static final String DEGREE_NAME = "℃";
    public static final String AQI_TEXT = "空气质量：";
    public static final String CITY_SELECTOR_TITLE = "选择城市";
    public static final String CITY_SELECTOR_TEXT = "请选择城市：";
    public static final String PLEASE_WAIT = "请等待查询结束";

    // SharedPreferences 相关常量
    public static final String PREFERENCE_NAME = "preference";
    public static final String CITY_NAME = "city_name";
    public static final String DEFAULT_CITY_NAME = "beijing";
    public static final String WEATHER_CODE = "weather_code";
    public static final int DEFAULT_WEATHER_CODE = 999;
    public static final String WEATHER_NAME = "weather_name";
    public static final String DEFAULT_WEATHER_NAME = "未知";
    public static final String TEMPERATURE = "temperature";
    public static final int DEFAULT_TEMPERATURE = 0;
    public static final String PROVINCE = "province";
    public static final int DEFAULT_PROVINCE_ID = 0;
    public static final String DEFAULT_DATE_TODAY = "2016-04-14";
    public static final String AQI = "aqi";
    public static final int DEFAULT_AQI = 0;
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";


    // URI 常量
    public  static final String CONTENT = "content://";
    public  static final String AUTHORITY = "com.deserteaglefe.seventhweek";
    public  static final String DAILY_URI = CONTENT + AUTHORITY + "/" + Constants.DAILY_TABLE_NAME;
    public static final int URI_MATCH_DAILY = 1;
    public static final int URI_MATCH_HOURLY = 2;

    // 数据库相关字段
    public static final String DATABASE_NAME = "weather.db";
    public static final int VERSION = 2;
    public static final String DAILY_TABLE_NAME = "daily_table";
    public static final String HOURLY_TABLE_NAME = "hourly_table";
    public static final String CITY_TABLE_NAME = "city_table";
    public static final String DATE_NAME = "date_name";
    public static final String WEATHER_CODE_DAY = "weather_code_day";
    public static final String WEATHER_CODE_NIGHT = "weather_code_night";
    public static final String WEATHER_TEXT_DAY = "weather_text_day";
    public static final String WEATHER_TEXT_NIGHT = "weather_text_night";
    public static final String MAX_TEMPERATURE = "max_temperature";
    public static final String MIN_TEMPERATURE = "min_temperature";
    public static final String SUN_RISE = "sun_rise";
    public static final String SUN_SET = "sun_set";
    public static final String HUMIDITY = "humidity";
    public static final String PRECIPITATION = "precipitation";
    public static final String PRECIPITATE_PROBABILITY = "precipitation_probability";
    public static final String PRESSURE = "pressure";
    public static final String VISIBILITY = "visibility";
    public static final String WIND_DEGREE = "wind_degree";
    public static final String WIND_DIRECTION = "wind_direction";
    public static final String WIND_SCALE = "wind_scale";
    public static final String WIND_SPEED = "wind_speed";

    // Toast 常量
    public static String getToast(int i){
        if(i>=0 && i<=5){
            return TOAST_STRING[i];
        }else{
            return TOAST_STRING[6];
        }
    }

    // 免费版服务器不反悔小城市/城镇的空气质量详细数据(含等级名称)

    public static final String[] AIR_QUALITY_NAME = new String[]{
            "优",
            "良",
            "轻度污染",
            "中度污染",
            "重度污染",
            "重度污染", // 注意 重度污染 为 201-300，占有相当于别的两个长度的段
            "严重污染"
    };
    public static final int[] AQI_TEXT_COLOR = {
            Color.parseColor("#00FF00"),
            Color.parseColor("#88DD00"),
            Color.parseColor("#C3AC00"),
            Color.parseColor("#FF8800"),
            Color.parseColor("#FF0000"),
            Color.parseColor("#FF0000"), // 同上
            Color.parseColor("#700000"),
    };

    private static final String[] TOAST_STRING = new String[]{
            "天气更新成功",
            "天气更新失败：URL协议错误",
            "天气更新失败：字符编码错误",
            "天气更新失败：网络协议错误",
            "天气更新失败：请检查网络连接",
            "服务器拒绝访问",
            "天气更新失败：未知错误"
    };

    // 省份常量
    public static final String[] PROVINCE_ARRAY = new String[]{
            "北京市", "上海市", "天津市", "重庆市", "黑龙江", "吉林", "辽宁", "内蒙古", "河北", "河南", "山西",
            "山东", "江苏", "浙江", "福建", "江西", "安徽", "湖北", "湖南", "广东", "广西", "海南",
            "贵州", "云南", "四川", "西藏", "陕西", "宁夏", "甘肃", "青海", "新疆", "台湾", "特区"
    };

    // 天气图标哈希表
    private static HashMap<Integer, Integer> weatherMap = new HashMap<>();

    static {
        weatherMap.put(100, R.drawable.pic100);
        weatherMap.put(101, R.drawable.pic101);
        weatherMap.put(102, R.drawable.pic102);
        weatherMap.put(103, R.drawable.pic103);
        weatherMap.put(104, R.drawable.pic104);
        weatherMap.put(200, R.drawable.pic200);
        weatherMap.put(201, R.drawable.pic201);
        weatherMap.put(202, R.drawable.pic202);
        weatherMap.put(203, R.drawable.pic203);
        weatherMap.put(204, R.drawable.pic204);
        weatherMap.put(205, R.drawable.pic205);
        weatherMap.put(206, R.drawable.pic206);
        weatherMap.put(207, R.drawable.pic207);
        weatherMap.put(208, R.drawable.pic208);
        weatherMap.put(209, R.drawable.pic209);
        weatherMap.put(210, R.drawable.pic210);
        weatherMap.put(211, R.drawable.pic211);
        weatherMap.put(212, R.drawable.pic212);
        weatherMap.put(213, R.drawable.pic213);
        weatherMap.put(300, R.drawable.pic300);
        weatherMap.put(301, R.drawable.pic301);
        weatherMap.put(302, R.drawable.pic302);
        weatherMap.put(303, R.drawable.pic303);
        weatherMap.put(304, R.drawable.pic304);
        weatherMap.put(305, R.drawable.pic305);
        weatherMap.put(306, R.drawable.pic306);
        weatherMap.put(307, R.drawable.pic307);
        weatherMap.put(308, R.drawable.pic308);
        weatherMap.put(309, R.drawable.pic309);
        weatherMap.put(310, R.drawable.pic310);
        weatherMap.put(311, R.drawable.pic311);
        weatherMap.put(312, R.drawable.pic312);
        weatherMap.put(313, R.drawable.pic313);
        weatherMap.put(400, R.drawable.pic400);
        weatherMap.put(401, R.drawable.pic401);
        weatherMap.put(402, R.drawable.pic402);
        weatherMap.put(403, R.drawable.pic403);
        weatherMap.put(404, R.drawable.pic404);
        weatherMap.put(405, R.drawable.pic405);
        weatherMap.put(406, R.drawable.pic406);
        weatherMap.put(407, R.drawable.pic407);
        weatherMap.put(500, R.drawable.pic500);
        weatherMap.put(501, R.drawable.pic501);
        weatherMap.put(502, R.drawable.pic502);
        weatherMap.put(503, R.drawable.pic503);
        weatherMap.put(504, R.drawable.pic504);
        weatherMap.put(507, R.drawable.pic507);
        weatherMap.put(508, R.drawable.pic508);
        weatherMap.put(900, R.drawable.pic900);
        weatherMap.put(901, R.drawable.pic901);
        weatherMap.put(999, R.drawable.pic999);
    }

    // 哈希表接口
    public static int getIcon(int iconId) {
        if(weatherMap.containsKey(iconId)){
            return weatherMap.get(iconId);
        }else {
            return weatherMap.get(999);
        }
    }
}
