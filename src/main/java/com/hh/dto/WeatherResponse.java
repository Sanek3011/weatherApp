package com.hh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("coord")
    private Coordinates coordinates;
    @JsonProperty("weather")
    private Weather weather;
    @JsonProperty("main")
    private Temperature temperature;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("sys")
    private SystemParameters sys;
    @JsonProperty("timezone")
    private Integer timezone;
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("id")
    private Long cityId;
    @JsonProperty("dt")
    private Long dt;
    private long fetchedTime;
}
