package com.wmp.classTools.importPanel.weatherInfo.control;

public record WeatherInfo(String cityCode, String key) {
    public WeatherInfo getNewWInfo(String cityCode, String key){
        cityCode = cityCode == null ? this.cityCode : cityCode;
        key = key == null ? this.key : key;
        return new WeatherInfo(cityCode, key);
    }
}
