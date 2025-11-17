package com.hh.dto;

import com.hh.util.WindUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WeatherData {

    private Weather weather;
    private Temperature temperature;
    private Integer visibility;
    private Wind wind;
    private Long datetime;
    private SystemParameters sys;
    private Integer timezone;
    private String name;

    public WeatherData(WeatherResponse weatherResponse) {
        this.datetime = weatherResponse.getDt();
        this.name = weatherResponse.getCityName();
        this.sys = weatherResponse.getSys();
        this.temperature = weatherResponse.getTemperature();
        this.timezone = weatherResponse.getTimezone();
        this.visibility = weatherResponse.getVisibility();
        this.weather = weatherResponse.getWeather();
        this.wind = weatherResponse.getWind();
    }
}
