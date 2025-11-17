package com.hh.client;

import java.util.concurrent.ConcurrentHashMap;

public class WeatherClientFactory {

    private static ConcurrentHashMap<String, WeatherClient> clients = new ConcurrentHashMap<>();

    public static WeatherClient createClient(String apiKey, WorkMode workMode) {
        return clients.computeIfAbsent(apiKey, key -> new WeatherClient(key, workMode));
    }

    public static void removeClient(String apiKey) {
        WeatherClient client = clients.remove(apiKey);
        if (client != null) {
            client.stopPolling();
        }
    }

    public static boolean containsClient(String apiKey) {
        return clients.containsKey(apiKey);
    }
}
