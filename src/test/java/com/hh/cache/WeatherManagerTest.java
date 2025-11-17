package com.hh.cache;

import com.hh.dto.MainParameters;
import com.hh.dto.WeatherData;
import com.hh.dto.WeatherResponse;
import com.hh.dto.Wind;
import com.hh.service.ApiService;
import com.hh.service.WeatherManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.lang.reflect.Field;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class WeatherManagerTest {
    @Mock
    private ApiService apiService;

    @InjectMocks
    private WeatherManager manager;

    private CityWeatherCache cache;
    private WeatherData weatherData;
    private WeatherResponse weatherResponse;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        cache = CityWeatherCache.getINSTANCE();
        cache.clearCache();
        weatherData = WeatherData.builder()
                .cityName("Moscow")
                .weatherCondition("conditions")
                .temperature(2D)
                .feelsLikeTemp(2D)
                .visibility(10)
                .windSpeed(11)
                .windDirection("NE")
                .build();
        MainParameters temp = MainParameters.builder()
                .realTemperature(2D)
                .feelsLikeTemperature(3D).build();
        weatherResponse = WeatherResponse.builder()
                .cityName("Moscow")
                .temperature(temp)
                .weatherCondition("cloudy")
                .visibility(25)
                .wind(new Wind(50, 15, 5))
                .fetchedTime(System.currentTimeMillis() / 1000)
                .build();
    }

    @Test
    void shouldReturnFromCacheIfPresent() throws URISyntaxException {
        cache.addCity(weatherResponse);
        when(apiService.parseWeather("Moscow")).thenReturn(weatherResponse);
        WeatherData result = manager.getWeather("Moscow");

        assertNotNull(result);
        assertEquals("Moscow", result.getCityName());
        verify(apiService, never()).parseWeather(anyString());
    }

    @Test
    void shouldCallApiIfCacheEmpty() throws URISyntaxException {
        WeatherResponse apiResponse = new WeatherResponse();
        apiResponse.setCityName("Moscow");

        when(apiService.parseWeather("Moscow")).thenReturn(weatherResponse);
        WeatherData result = manager.getWeather("Moscow");

        assertNotNull(result);
        assertEquals("Moscow", result.getCityName());
        verify(apiService).parseWeather("Moscow");
        assertTrue(cache.cachedCities().contains("Moscow"));
    }

    @Test
    void getRawDataReturnsApiResponse() throws URISyntaxException {
        WeatherResponse apiResponse = new WeatherResponse();
        apiResponse.setCityName("Moscow");

        when(apiService.parseWeather("Moscow")).thenReturn(apiResponse);

        WeatherResponse result = manager.getRawData("Moscow");

        assertEquals("Moscow", result.getCityName());
    }

    @Test
    void getRawDataWrapsURISyntaxException() throws URISyntaxException {
        when(apiService.parseWeather("Moscow")).thenThrow(new URISyntaxException("url", "err"));

        assertThrows(RuntimeException.class, () -> manager.getRawData("Moscow"));
    }
}
