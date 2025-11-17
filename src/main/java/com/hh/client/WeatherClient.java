package com.hh.client;


import com.hh.dto.WeatherData;
import com.hh.dto.WeatherResponse;
import com.hh.service.PollingService;
import com.hh.service.WeatherManager;

import java.util.concurrent.TimeUnit;

public class WeatherClient {

    private final WorkMode workMode;
    private final WeatherManager manager;
    private PollingService pollingService;

    protected WeatherClient(String apiKey, WorkMode workMode) {
        this.workMode = workMode;
        this.manager = new WeatherManager(apiKey);
        if (workMode.equals(WorkMode.POLLING)) {
            pollingService = new PollingService(this.manager);
            startPolling(5, TimeUnit.MINUTES);
        }
    }

    private void startPolling(long time, TimeUnit unit) {
        if (pollingService != null) {
            pollingService.startPolling(time, unit);
        }
    }

    public void changeIntervalPolling(long time, TimeUnit unit) {
        stopPolling();
        startPolling(time, unit);
    }

    public WeatherData getWeather(String city) {
        return manager.getWeather(city);
    }

    public WeatherResponse getRawData(String city) {
        return manager.getRawData(city);
    }

    public void stopPolling() {
        if (pollingService != null) {
            pollingService.stopPolling();
        }
    }
}
