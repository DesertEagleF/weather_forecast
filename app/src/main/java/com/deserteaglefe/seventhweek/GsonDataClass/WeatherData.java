package com.deserteaglefe.seventhweek.GsonDataClass;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Function: the weather class which converted from json
 * Create date on 2016/4/12.
 *
 * @author DesertEagleF
 * @version 1.0
 */

public class WeatherData {
    @SerializedName("HeWeather data service 3.0")
    private List<HeWeatherData> heWeatherData;

        public WeatherData objectFromData(String str) {
        return new Gson().fromJson(str, WeatherData.class);
    }

    public List<HeWeatherData> getHeWeatherData() {
        return heWeatherData;
    }

    public void setHeWeatherData(List<HeWeatherData> heWeatherData) {
        this.heWeatherData = heWeatherData;
    }

    public class HeWeatherData {

        @SerializedName("aqi")
        private AqiBean aqi;

        @SerializedName("basic")
        private BasicBean basic;

        @SerializedName("now")
        private NowBean now;

        @SerializedName("status")
        private String status;

        @SerializedName("suggestion")
        private SuggestionBean suggestion;

        @SerializedName("daily_forecast")
        private List<DailyForecastBean> daily_forecast;

        @SerializedName("hourly_forecast")
        private List<HourlyForecastBean> hourly_forecast;

        public HeWeatherData objectFromData(String str) {

            return new Gson().fromJson(str, HeWeatherData.class);
        }

        public AqiBean getAqi() {
            return aqi;
        }

        public void setAqi(AqiBean aqi) {
            this.aqi = aqi;
        }

        public BasicBean getBasic() {
            return basic;
        }

        public void setBasic(BasicBean basic) {
            this.basic = basic;
        }

        public NowBean getNow() {
            return now;
        }

        public void setNow(NowBean now) {
            this.now = now;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public SuggestionBean getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(SuggestionBean suggestion) {
            this.suggestion = suggestion;
        }

        public List<DailyForecastBean> getDaily_forecast() {
            return daily_forecast;
        }

        public void setDaily_forecast(List<DailyForecastBean> daily_forecast) {
            this.daily_forecast = daily_forecast;
        }

        public List<HourlyForecastBean> getHourly_forecast() {
            return hourly_forecast;
        }

        public void setHourly_forecast(List<HourlyForecastBean> hourly_forecast) {
            this.hourly_forecast = hourly_forecast;
        }

        public class AqiBean {
            @SerializedName("city")
            private CityBean city;

            public AqiBean objectFromData(String str) {

                return new Gson().fromJson(str, AqiBean.class);
            }

            public CityBean getCity() {
                return city;
            }

            public void setCity(CityBean city) {
                this.city = city;
            }

            public class CityBean {

                @SerializedName("aqi")
                private int aqi;

                @SerializedName("co")
                private int co;

                @SerializedName("no2")
                private int no2;

                @SerializedName("o3")
                private int o3;

                @SerializedName("pm10")
                private int pm10;

                @SerializedName("pm2.5")
                private int pm25;

                @SerializedName("qlty")
                private String qlty;

                @SerializedName("so2")
                private int so2;

                public CityBean objectFromData(String str) {

                    return new Gson().fromJson(str, CityBean.class);
                }

                public int getAqi() {
                    return aqi;
                }

                public void setAqi(int aqi) {
                    this.aqi = aqi;
                }

                public int getCo() {
                    return co;
                }

                public void setCo(int co) {
                    this.co = co;
                }

                public int getNo2() {
                    return no2;
                }

                public void setNo2(int no2) {
                    this.no2 = no2;
                }

                public int getO3() {
                    return o3;
                }

                public void setO3(int o3) {
                    this.o3 = o3;
                }

                public int getPm10() {
                    return pm10;
                }

                public void setPm10(int pm10) {
                    this.pm10 = pm10;
                }

                public int getPm25() {
                    return pm25;
                }

                public void setPm25(int pm25) {
                    this.pm25 = pm25;
                }

                public String getQlty() {
                    return qlty;
                }

                public void setQlty(String qlty) {
                    this.qlty = qlty;
                }

                public int getSo2() {
                    return so2;
                }

                public void setSo2(int so2) {
                    this.so2 = so2;
                }
            }
        }

        public class BasicBean {
            @SerializedName("city")
            private String city;

            @SerializedName("cnty")
            private String cnty;

            @SerializedName("id")
            private String id;

            @SerializedName("lat")
            private double lat;

            @SerializedName("lon")
            private double lon;

            @SerializedName("update")
            private UpdateBean update;

            public BasicBean objectFromData(String str) {
                return new Gson().fromJson(str, BasicBean.class);
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCnty() {
                return cnty;
            }

            public void setCnty(String cnty) {
                this.cnty = cnty;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }

            public double getLon() {
                return lon;
            }

            public void setLon(double lon) {
                this.lon = lon;
            }

            public UpdateBean getUpdate() {
                return update;
            }

            public void setUpdate(UpdateBean update) {
                this.update = update;
            }

            public class UpdateBean {

                @SerializedName("loc")
                private String loc;

                @SerializedName("utc")
                private String utc;

                public UpdateBean objectFromData(String str) {

                    return new Gson().fromJson(str, UpdateBean.class);
                }

                public String getLoc() {
                    return loc;
                }

                public void setLoc(String loc) {
                    this.loc = loc;
                }

                public String getUtc() {
                    return utc;
                }

                public void setUtc(String utc) {
                    this.utc = utc;
                }
            }
        }

        public class NowBean {

            @SerializedName("cond")
            private NowCondBean cond;

            @SerializedName("fl")
            private int fl;

            @SerializedName("hum")
            private int hum;

            @SerializedName("pcpn")
            private float pcpn;

            @SerializedName("pres")
            private int pres;

            @SerializedName("tmp")
            private int tmp;

            @SerializedName("vis")
            private int vis;

            @SerializedName("wind")
            private WindBean wind;

            public NowBean objectFromData(String str) {

                return new Gson().fromJson(str, NowBean.class);
            }

            public NowCondBean getCond() {
                return cond;
            }

            public void setCond(NowCondBean cond) {
                this.cond = cond;
            }

            public int getFl() {
                return fl;
            }

            public void setFl(int fl) {
                this.fl = fl;
            }

            public int getHum() {
                return hum;
            }

            public void setHum(int hum) {
                this.hum = hum;
            }

            public float getPcpn() {
                return pcpn;
            }

            public void setPcpn(float pcpn) {
                this.pcpn = pcpn;
            }

            public int getPres() {
                return pres;
            }

            public void setPres(int pres) {
                this.pres = pres;
            }

            public int getTmp() {
                return tmp;
            }

            public void setTmp(int tmp) {
                this.tmp = tmp;
            }

            public int getVis() {
                return vis;
            }

            public void setVis(int vis) {
                this.vis = vis;
            }

            public WindBean getWind() {
                return wind;
            }

            public void setWind(WindBean wind) {
                this.wind = wind;
            }

            public class NowCondBean {

                @SerializedName("code")
                private int code;

                @SerializedName("txt")
                private String txt;

                public NowCondBean objectFromData(String str) {
                    return new Gson().fromJson(str, NowCondBean.class);
                }

                public int getCode() {
                    return code;
                }

                public void setCode(int code) {
                    this.code = code;
                }

                public String getTxt() {
                    return txt;
                }

                public void setTxt(String txt_d) {
                    this.txt = txt;
                }
            }
        }
    }

    public class SuggestionBean {
        @SerializedName("comf")
        private ComfBean comf;

        @SerializedName("cw")
        private CwBean cw;

        @SerializedName("drsg")
        private DrsgBean drsg;

        @SerializedName("flu")
        private FluBean flu;

        @SerializedName("sport")
        private SportBean sport;

        @SerializedName("trav")
        private TravBean trav;

        @SerializedName("uv")
        private UvBean uv;

        public SuggestionBean objectFromData(String str) {
            return new Gson().fromJson(str, SuggestionBean.class);
        }

        public ComfBean getComf() {
            return comf;
        }

        public void setComf(ComfBean comf) {
            this.comf = comf;
        }

        public CwBean getCw() {
            return cw;
        }

        public void setCw(CwBean cw) {
            this.cw = cw;
        }

        public DrsgBean getDrsg() {
            return drsg;
        }

        public void setDrsg(DrsgBean drsg) {
            this.drsg = drsg;
        }

        public FluBean getFlu() {
            return flu;
        }

        public void setFlu(FluBean flu) {
            this.flu = flu;
        }

        public SportBean getSport() {
            return sport;
        }

        public void setSport(SportBean sport) {
            this.sport = sport;
        }

        public TravBean getTrav() {
            return trav;
        }

        public void setTrav(TravBean trav) {
            this.trav = trav;
        }

        public UvBean getUv() {
            return uv;
        }

        public void setUv(UvBean uv) {
            this.uv = uv;
        }

        public class ComfBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public ComfBean objectFromData(String str) {
                return new Gson().fromJson(str, ComfBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class CwBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public CwBean objectFromData(String str) {

                return new Gson().fromJson(str, CwBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class DrsgBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public DrsgBean objectFromData(String str) {
                return new Gson().fromJson(str, DrsgBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class FluBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public FluBean objectFromData(String str) {

                return new Gson().fromJson(str, FluBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class SportBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public SportBean objectFromData(String str) {

                return new Gson().fromJson(str, SportBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class TravBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public TravBean objectFromData(String str) {

                return new Gson().fromJson(str, TravBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }

        public class UvBean {

            @SerializedName("brf")
            private String brf;

            @SerializedName("txt")
            private String txt;

            public UvBean objectFromData(String str) {

                return new Gson().fromJson(str, UvBean.class);
            }

            public String getBrf() {
                return brf;
            }

            public void setBrf(String brf) {
                this.brf = brf;
            }

            public String getTxt() {
                return txt;
            }

            public void setTxt(String txt) {
                this.txt = txt;
            }
        }
    }

    public class DailyForecastBean {
        @SerializedName("astro")
        private AstroBean astro;

        @SerializedName("cond")
        private CondBean cond;

        @SerializedName("date")
        private String date;

        @SerializedName("hum")
        private int hum;

        @SerializedName("pcpn")
        private float pcpn;

        @SerializedName("pop")
        private int pop;

        @SerializedName("pres")
        private int pres;

        @SerializedName("tmp")
        private TmpBean tmp;

        @SerializedName("vis")
        private int vis;

        @SerializedName("wind")
        private WindBean wind;

        public DailyForecastBean objectFromData(String str) {
            return new Gson().fromJson(str, DailyForecastBean.class);
        }

        public AstroBean getAstro() {
            return astro;
        }

        public void setAstro(AstroBean astro) {
            this.astro = astro;
        }

        public CondBean getCond() {
            return cond;
        }

        public void setCond(CondBean cond) {
            this.cond = cond;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getHum() {
            return hum;
        }

        public void setHum(int hum) {
            this.hum = hum;
        }

        public float getPcpn() {
            return pcpn;
        }

        public void setPcpn(float pcpn) {
            this.pcpn = pcpn;
        }

        public int getPop() {
            return pop;
        }

        public void setPop(int pop) {
            this.pop = pop;
        }

        public int getPres() {
            return pres;
        }

        public void setPres(int pres) {
            this.pres = pres;
        }

        public TmpBean getTmp() {
            return tmp;
        }

        public void setTmp(TmpBean tmp) {
            this.tmp = tmp;
        }

        public int getVis() {
            return vis;
        }

        public void setVis(int vis) {
            this.vis = vis;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }

        public class AstroBean {

            @SerializedName("mr")
            private String mr;

            @SerializedName("ms")
            private String ms;

            @SerializedName("sr")
            private String sr;

            @SerializedName("ss")
            private String ss;

            public AstroBean objectFromData(String str) {

                return new Gson().fromJson(str, AstroBean.class);
            }

            public String getMr() {
                return mr;
            }

            public void setMr(String mr) {
                this.mr = mr;
            }

            public String getMs() {
                return ms;
            }

            public void setMs(String ms) {
                this.ms = ms;
            }

            public String getSr() {
                return sr;
            }

            public void setSr(String sr) {
                this.sr = sr;
            }

            public String getSs() {
                return ss;
            }

            public void setSs(String ss) {
                this.ss = ss;
            }
        }
        public class TmpBean {

            @SerializedName("max")
            private int max;

            @SerializedName("min")
            private int min;

            public TmpBean objectFromData(String str) {

                return new Gson().fromJson(str, TmpBean.class);
            }

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }
    }

    public class CondBean {

        @SerializedName("code_d")
        private int code_d;

        @SerializedName("code_n")
        private int code_n;

        @SerializedName("txt_d")
        private String txt_d;

        @SerializedName("txt_n")
        private String txt_n;

        public CondBean objectFromData(String str) {

            return new Gson().fromJson(str, CondBean.class);
        }

        public int getCode_d() {
            return code_d;
        }

        public void setCode_d(int code_d) {
            this.code_d = code_d;
        }

        public int getCode_n() {
            return code_n;
        }

        public void setCode_n(int code_n) {
            this.code_n = code_n;
        }

        public String getTxt_d() {
            return txt_d;
        }

        public void setTxt_d(String txt_d) {
            this.txt_d = txt_d;
        }

        public String getTxt_n() {
            return txt_n;
        }

        public void setTxt_n(String txt_n) {
            this.txt_n = txt_n;
        }
    }

    public class HourlyForecastBean {
        @SerializedName("date")
        private String date;

        @SerializedName("hum")
        private int hum;

        @SerializedName("pop")
        private float pop;

        @SerializedName("pres")
        private int pres;

        @SerializedName("tmp")
        private int tmp;

        @SerializedName("wind")
        private WindBean wind;

        public HourlyForecastBean objectFromData(String str) {

            return new Gson().fromJson(str, HourlyForecastBean.class);
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public int getHum() {
            return hum;
        }

        public void setHum(int hum) {
            this.hum = hum;
        }

        public float getPop() {
            return pop;
        }

        public void setPop(float pop) {
            this.pop = pop;
        }

        public int getPres() {
            return pres;
        }

        public void setPres(int pres) {
            this.pres = pres;
        }

        public int getTmp() {
            return tmp;
        }

        public void setTmp(int tmp) {
            this.tmp = tmp;
        }

        public WindBean getWind() {
            return wind;
        }

        public void setWind(WindBean wind) {
            this.wind = wind;
        }
    }

    public class WindBean {
        @SerializedName("deg")
        private int deg;

        @SerializedName("dir")
        private String dir;

        @SerializedName("sc")
        private String sc;

        @SerializedName("spd")
        private int spd;

        public WindBean objectFromData(String str) {
            return new Gson().fromJson(str, WindBean.class);
        }

        public int getDeg() {
            return deg;
        }

        public void setDeg(int deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public int getSpd() {
            return spd;
        }

        public void setSpd(int spd) {
            this.spd = spd;
        }
    }
}
