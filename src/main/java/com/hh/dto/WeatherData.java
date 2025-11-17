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

    private String cityName;
    private Double temperature;
    private Double feelsLikeTemp;
    private String weatherCondition;
    private Integer windSpeed;
    private String windDirection;
    private Integer visibility;

    public WeatherData(WeatherResponse weatherResponse) {
        this.cityName = weatherResponse.getCityName();
        this.temperature = weatherResponse.getTemperature().getRealTemperature();
        this.feelsLikeTemp = weatherResponse.getTemperature().getFeelsLikeTemperature();
        this.weatherCondition = weatherResponse.getWeatherCondition();
        this.windSpeed = weatherResponse.getWind().getSpeed();
        this.windDirection = WindUtils.getWindDirection(weatherResponse.getWind().getDegrees());
        this.visibility = weatherResponse.getVisibility();
    }

}
