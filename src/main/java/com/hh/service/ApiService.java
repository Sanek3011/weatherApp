package com.hh.service;


import com.hh.dto.WeatherResponse;

import java.net.URISyntaxException;

public interface ApiService {

    WeatherResponse parseWeather(String city) throws URISyntaxException;
}
