# weather_forecast
学着做个天气预报
-----
API来自http://apistore.baidu.com/apiworks/servicedetail/478.html<br>
接口地址http://apis.baidu.com/heweather/weather/free<br>
请求示例参考了所给出的示例。<br>
用GsonFormat快速创造出解析获取的json所对应的类<br>
由于城市信息一般不会变化，因此城市信息表采用查询后获取的json文件，存储于raw目录下，以减少每次查询所需的时间以及网络访问次数。<br>
只是一个初步完成基本功能，还有很大的改进空间<br>
