package com.hh.cache;

import com.hh.dto.WeatherResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CityWeatherCacheTest {
    private CityWeatherCache cache;

    @BeforeEach
    void setup() {
        cache = CityWeatherCache.getINSTANCE();
        cache.clearCache();
    }

    @Test
    void cacheOnlyTenCitiesTest() {
        CityWeatherCache cache = CityWeatherCache.getINSTANCE();

        for (int i = 0; i < 11; i++) {
            WeatherResponse response = WeatherResponse.builder()
                    .cityName("City" + i)
                    .fetchedTime(Instant.now().getEpochSecond())
                    .build();
            cache.addCity(response);
        }
        assertNull(cache.getCacheData("City0"));
        assertNotNull(cache.getCacheData("City10"));
    }

    @Test
    void cacheOnlyFreshCitiesTest() {
        CityWeatherCache cache = CityWeatherCache.getINSTANCE();

        for (int i = 0; i < 5; i++) {
            WeatherResponse response = WeatherResponse.builder()
                    .cityName("City" + i)
                    .fetchedTime(Instant.now().minus(20, ChronoUnit.MINUTES).getEpochSecond())
                    .build();
            cache.addCity(response);
        }
        assertNull(cache.getCacheData("City0"));
        assertNull(cache.getCacheData("City4"));
    }

    @Test
    void shouldReturnNullWhenCityNotInCache() {
        WeatherResponse result = cache.getCacheData("city");
        assertNull(result);
    }

    @Test
    void shouldReturnNullWhenDataIsExpired() {
        WeatherResponse expired = WeatherResponse.builder()
                .cityName("Moscow")
                .fetchedTime(Instant.now().minusSeconds(11 * 60).getEpochSecond()) // 11 минут назад
                .build();

        cache.addCity(expired);

        WeatherResponse result = cache.getCacheData("Moscow");
        assertNull(result);
    }
}
