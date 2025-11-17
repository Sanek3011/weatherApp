package com.hh.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class ExceptionFactory {

    private static final Map<Integer, String> defaultMessages;

    static {
        defaultMessages = Map.of(400, "Ошибка в запросе",
                401, "Неавторизован. Получите новый токен",
                404, "Город не найден",
                500, "Внутрення ошибка сервера",
                503, "Сервис временно недоступен");
    }

    public static WeatherSdkException httpException(int statusCode, String body) {
        String message = extractMessageFromBody(body, statusCode);
        switch (statusCode) {
            case 404:
                return new CityNotFoundException(message);
            case 401:
                return new InvalidApiKeyException(message);
            default:
                return new WeatherSdkException(message);
        }
    }

    private static String extractMessageFromBody(String body, int statusCode) {
        if (body == null || body.isEmpty()) {
            return defaultMessages.getOrDefault(statusCode, "Ошибка:"+statusCode);
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            JsonNode jsonNode = mapper.readTree(body);
            if (jsonNode.has("message")) {
                return jsonNode.get("message").asText();
            }
        }catch (JsonProcessingException ignored) {

        }
        return defaultMessages.getOrDefault(statusCode, "Ошибка: " + statusCode);
    }
}
