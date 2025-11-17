package com.hh.cache;

import com.hh.dto.WeatherData;
import com.hh.dto.WeatherResponse;
import lombok.Getter;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class CityWeatherCache {


    @Getter
    private static final CityWeatherCache INSTANCE = new CityWeatherCache();

    private CityWeatherCache() {}

    private final Map<String, WeatherResponse> cache = Collections.synchronizedMap(
            new LinkedHashMap<>(16, 0.75f, true) {
                @Override
                protected boolean removeEldestEntry(Map.Entry<String, WeatherResponse> eldest) {
                    return size() >= 10;
                }
            }
    );

    public void addCity(WeatherResponse data) {
       cache.put(data.getCityName(), data);
    }

    public WeatherResponse getCacheData(String cityName) {
        WeatherResponse weatherResponse = cache.get(cityName);
        if (weatherResponse == null || Instant.ofEpochSecond(weatherResponse.getFetchedTime())
                .isBefore(Instant.now().minus(Duration.ofMinutes(10)))) {
            return null;
        }
        return weatherResponse;
    }

    public Set<String> cachedCities() {
        return cache.keySet();
    }

    public void clearCache() {
        cache.clear();
    }
}
