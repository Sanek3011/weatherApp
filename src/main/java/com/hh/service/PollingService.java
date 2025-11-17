package com.hh.service;

import com.hh.cache.CityWeatherCache;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PollingService {

    private ScheduledExecutorService schedule = Executors.newSingleThreadScheduledExecutor();
    private CityWeatherCache cache = CityWeatherCache.getINSTANCE();
    private WeatherManager manager;

    public PollingService(WeatherManager manager) {
        this.manager = manager;
    }

    public void startPolling() {
        schedule.scheduleAtFixedRate(() -> {
            for (String cityName : cache.cachedCities()) {
                cache.addCity(manager.getRawData(cityName));
            }
        },0, 5, TimeUnit.MINUTES);
    }

    public void stopPolling() {
        schedule.shutdownNow();
    }
}
