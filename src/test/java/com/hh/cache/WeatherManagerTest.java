package com.hh.cache;

import com.hh.dto.*;
import com.hh.service.ApiService;
import com.hh.service.WeatherManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
                .name("Moscow")
                .weather(new Weather())
                .temperature(new Temperature())
                .visibility(10)
                .wind(new Wind())
                .build();
        Temperature temp = Temperature.builder()
                .realTemperature(2D)
                .feelsLikeTemperature(3D).build();
        weatherResponse = WeatherResponse.builder()
                .cityName("Moscow")
                .temperature(temp)
                .weather(new Weather())
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
        assertEquals("Moscow", result.getName());
        verify(apiService, never()).parseWeather(anyString());
    }

    @Test
    void shouldCallApiIfCacheEmpty() throws URISyntaxException {
        WeatherResponse apiResponse = new WeatherResponse();
        apiResponse.setCityName("Moscow");

        when(apiService.parseWeather("Moscow")).thenReturn(weatherResponse);
        WeatherData result = manager.getWeather("Moscow");

        assertNotNull(result);
        assertEquals("Moscow", result.getName());
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
