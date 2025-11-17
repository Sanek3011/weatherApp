package com.hh.exception;

public class CityNotFoundException extends WeatherSdkException{

    public CityNotFoundException(String message) {
        super(message);
    }
}
