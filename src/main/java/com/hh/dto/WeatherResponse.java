package com.hh.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("coord")
    private Coordinates coordinates;
    private String weatherCondition;
    @JsonProperty("main")
    private MainParameters temperature;
    @JsonProperty("visibility")
    private Integer visibility;
    @JsonProperty("wind")
    private Wind wind;
    @JsonProperty("sys")
    private OtherInformation otherInformation;
    @JsonProperty("timezone")
    private Integer timezone;
    @JsonProperty("name")
    private String cityName;
    @JsonProperty("id")
    private Long cityId;
    private long fetchedTime;

    @JsonProperty("weather")
    private void weatherDesc(List<Map<String, String>> weather) {
        if (weather != null && !weather.isEmpty()) {
            this.weatherCondition = weather.get(0).get("description");
        }
    }
}
