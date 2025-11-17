package com.hh.exception;

public class WeatherSdkException extends RuntimeException {
    public WeatherSdkException() {
    }

    public WeatherSdkException(String message) {
        super(message);
    }
}
