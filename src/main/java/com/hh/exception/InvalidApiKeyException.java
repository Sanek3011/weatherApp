package com.hh.exception;

public class InvalidApiKeyException extends WeatherSdkException{

    public InvalidApiKeyException(String message) {
        super(message);
    }
}
