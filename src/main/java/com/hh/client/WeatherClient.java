package com.hh.client;


import com.hh.dto.WeatherData;
import com.hh.dto.WeatherResponse;
import com.hh.service.PollingService;
import com.hh.service.WeatherManager;

public class WeatherClient {

    private final WorkMode workMode;
    private final WeatherManager manager;
    private PollingService pollingService;

    public WeatherClient(String apiKey, WorkMode workMode) {
        this.workMode = workMode;
        this.manager = new WeatherManager(apiKey);
        if (workMode.equals(WorkMode.POLLING)) {
            pollingService = new PollingService(this.manager);
            startPolling();
        }
    }

    private void startPolling() {
        if (pollingService != null) {
            pollingService.startPolling();
        }
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
