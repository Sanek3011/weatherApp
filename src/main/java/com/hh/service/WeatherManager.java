package com.hh.service;


import com.hh.cache.CityWeatherCache;
import com.hh.dto.WeatherData;
import com.hh.dto.WeatherResponse;

import java.net.URISyntaxException;

public class WeatherManager {

    private final ApiService weatherApiService;
    private final CityWeatherCache cache = CityWeatherCache.getINSTANCE();

    public WeatherManager(String apiKey) {
        this.weatherApiService = new WeatherApiService(apiKey);
    }

    public WeatherManager(ApiService weatherApiService) {
        this.weatherApiService = weatherApiService;
    }

    public WeatherData getWeather(String city) {
        WeatherResponse weatherResponse = cache.getCacheData(city);
        if (weatherResponse == null) {
            try {
                weatherResponse = weatherApiService.parseWeather(city);
                cache.addCity(weatherResponse);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        }
        return new WeatherData(weatherResponse);
    }

    public WeatherResponse getRawData(String city) {
        try {
            return weatherApiService.parseWeather(city);
        }catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
