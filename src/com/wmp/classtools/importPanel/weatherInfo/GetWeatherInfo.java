package com.wmp.classTools.importPanel.weatherInfo;

import com.wmp.publicTools.printLog.Log;
import com.wmp.publicTools.web.GetWebInf;
import com.wmp.classTools.importPanel.weatherInfo.control.WeatherInfo;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetWeatherInfo {

    public static String errCode = "";


    /**
     * 获取天气信息
     *
     * @param info 天气信息对象
     * @return JSONObject -> lives(JSONArray) -> province 省份名,city 城市名,adcode 地区编码,weather 天气,temperature 温度,winddirection 风向,windpower 风力,humidity 湿度,reporttime 数据更新时间
     */
    public static JSONObject getNowWeather(WeatherInfo info) {

        // 构造请求URL (使用一个公开的免费天气接口示例)
        String nowCloud = String.format("https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s", info.key(), info.cityCode());

        // 解析JSON响应
        JSONObject jsonResponse = new JSONObject(GetWebInf.getWebInf(nowCloud, false));
        Log.info.print("GetWeatherInfo", "获取天气成功: " + jsonResponse);
        if (jsonResponse.getInt("status") == 1) { // 假设1000为成功状态码
            JSONArray lives = jsonResponse.getJSONArray("lives");
            if (!lives.isEmpty()) return lives.optJSONObject(0);
        } else {
            errCode = jsonResponse.getString("info");
        }
        return null;
    }

    /**
     * 获取当天所有天气信息
     *
     * @param info 天气信息对象
     * @return JSONObject -> dayweather 白天-天气, daywind 白天-风向, daytemp 白天-温度, daypower 白天-风力, <br> nightweather 夜间-天气, nightwind 夜间-风向, nighttemp 夜间-温度, nightpower 夜间-风力,
     */
    public static JSONObject getTodayOtherWeather(WeatherInfo info){


        JSONArray weatherForecasts = getWeatherForecasts(info);
        if (weatherForecasts == null) return null;
        return weatherForecasts.getJSONObject(0);
    }

    /**
     * 获取预测天气信息
     *
     * @param info 天气信息对象
     * @return JSONArray(casts) -> JSONObject -> date 日期, dayweather 白天-天气, daywind 白天-风向, daytemp 白天-温度, daypower 白天-风力, <br> nightweather 夜间-天气, nightwind 夜间-风向, nighttemp 夜间-温度, nightpower 夜间-风力
     */
    public static JSONArray getWeatherForecasts(WeatherInfo  info) {
        // 构造请求URL (使用一个公开的免费天气接口示例)
        String nowCloud = String.format("https://restapi.amap.com/v3/weather/weatherInfo?key=%s&city=%s&extensions=all", info.key(), info.cityCode());


        // 解析JSON响应
        JSONObject jsonResponse = new JSONObject(GetWebInf.getWebInf(nowCloud, false));
        Log.info.print("GetWeatherInfo", "获取天气成功: " + jsonResponse);
        if (jsonResponse.getInt("status") == 1) { // 假设1000为成功状态码
            JSONArray forecast = jsonResponse.getJSONArray("forecasts");
            return forecast.getJSONObject(0).getJSONArray("casts");
        } else {
            errCode = jsonResponse.getString("infocode");
        }

        return null;
    }
}
