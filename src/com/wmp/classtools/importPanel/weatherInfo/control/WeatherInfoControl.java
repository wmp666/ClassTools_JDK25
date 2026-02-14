package com.wmp.classTools.importPanel.weatherInfo.control;

import com.wmp.publicTools.CTInfo;
import com.wmp.publicTools.appFileControl.CTInfoControl;
import com.wmp.publicTools.io.IOForInfo;
import com.wmp.publicTools.printLog.Log;

import java.io.File;
import java.io.IOException;

public class WeatherInfoControl extends CTInfoControl<WeatherInfo> {

    //bfaf25fc5c695452951e7edb57ddcd49
    private void setWeatherInfo(String cityCode, String key) {
        {
            IOForInfo io = new IOForInfo(CTInfo.DATA_PATH + "WeatherInfo\\cityCode.txt");
            try {
                io.setInfo(cityCode);
            } catch (IOException e) {
                Log.err.print(WeatherInfoControl.class, "天气信息保存失败", e);
            }
        }
        {
            IOForInfo io = new IOForInfo(CTInfo.DATA_PATH + "WeatherInfo\\key.txt");
            try {
                io.setInfo(key);
            } catch (IOException e) {
                Log.err.print(WeatherInfoControl.class, "天气信息保存失败", e);
            }
        }
    }

    private String getKey() {
        IOForInfo io = new IOForInfo(CTInfo.DATA_PATH + "WeatherInfo\\key.txt");
        try {
            String infos = io.getInfos();
            if (infos == null || infos.equals("err")) {
                return "";
            }
            return infos;
        } catch (IOException e) {
            Log.err.print(WeatherInfoControl.class, "天气信息获取失败", e);
        }
        return null;
    }

    private String getCityCode() {
        IOForInfo io = new IOForInfo(CTInfo.DATA_PATH + "WeatherInfo\\cityCode.txt");
        try {
            String infos = io.getInfos();
            if (infos == null || infos.equals("err")) {
                io.setInfo("360100");
                return "360100";
            }
            return infos;
        } catch (IOException e) {
            Log.err.print(WeatherInfoControl.class, "天气信息获取失败", e);
        }
        return null;
    }

    @Override
    public File getInfoBasicFile() {
        return new File(CTInfo.DATA_PATH , "WeatherInfo");
    }

    @Override
    public void setInfo(WeatherInfo weatherInfo) {
        setWeatherInfo(weatherInfo.cityCode(), weatherInfo.key());
    }

    @Override
    protected WeatherInfo refreshInfo() {
        return new WeatherInfo(getCityCode(), getKey());
    }

    /**
     * 天气信息
     *
     * @param date         日期(yyyy-MM-dd)(2025-10-26)
     * @param daypower     白天-天气
     * @param daywind      白天-风向
     * @param daytemp      白天-温度
     * @param daypower     白天-风力
     * @param nightweather 夜间-天气
     * @param nightwind    夜间-风向
     * @param nighttemp    夜间-温度
     * @param nightpower   夜间-风力
     */
    public record ForecastsWeatherInfo(String date, String dayweather, String daywind, String daytemp, String daypower,
                                       String nightweather, String nightwind, String nighttemp, String nightpower) {
    }
}
