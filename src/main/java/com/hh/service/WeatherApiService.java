package com.hh.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hh.cache.CityWeatherCache;
import com.hh.dto.WeatherResponse;

import com.hh.exception.ExceptionFactory;
import com.hh.exception.WeatherSdkException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.HttpHeaders;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.net.URIBuilder;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;


public class WeatherApiService implements ApiService {

    private static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private final String apiKey;
    private CloseableHttpClient client = HttpClients.createDefault();
    private ObjectMapper mapper = new ObjectMapper();


    public WeatherApiService(String apiKey) {
        this.apiKey = apiKey;
    }

    public WeatherResponse parseWeather(String city) throws URISyntaxException {
        try (CloseableHttpResponse execute = client.execute(createWeatherRequest(city))) {
            if (isSuccess(execute.getCode())) {
                String responseJson = EntityUtils.toString(execute.getEntity());
                WeatherResponse weatherResponse = mapper.readValue(responseJson, WeatherResponse.class);
                weatherResponse.setFetchedTime(Instant.now().getEpochSecond());
                return weatherResponse;
            }else{
                throw ExceptionFactory.httpException(execute.getCode(), EntityUtils.toString(execute.getEntity()));
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new WeatherSdkException("Ошибка при чтении");
        } catch (ParseException e) {
            e.printStackTrace();
            throw new WeatherSdkException("Ошибка при распарсинге");
        }
    }

    private boolean isSuccess(int statusCode) {
        return statusCode >= 200 && statusCode < 300;
    }

    private HttpGet createWeatherRequest(String city) throws URISyntaxException {
        String url = new URIBuilder(WEATHER_API_URL)
                .addParameter("APPID", apiKey)
                .addParameter("q", city)
                .addParameter("units", "metric")
                .build().toString();
        HttpGet request = new HttpGet(url);
        request.addHeader(HttpHeaders.ACCEPT, "application/json");
        request.addHeader(HttpHeaders.USER_AGENT, "weatherSDK");
        return request;
    }

}
