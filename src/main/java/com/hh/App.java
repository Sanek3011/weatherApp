package com.hh;


import com.hh.client.WeatherClient;
import com.hh.client.WorkMode;
import com.hh.dto.WeatherData;

public class App
{
    public static void main( String[] args ) {
        WeatherClient client = new WeatherClient("9fe375ad5de2ad61d7b0ad8e46ddd268", WorkMode.ON_DEMAND);
        WeatherData moscow = client.getWeather("Moscow");
        WeatherData mosco = client.getWeather("London");
        WeatherData mosc = client.getWeather("Detroit");
        WeatherData mos = client.getWeather("Rybinsk");
        WeatherData mo = client.getWeather("Alanya");
        WeatherData m = client.getWeather("Antalya");
        WeatherData mosj = client.getWeather("Sochi");
        WeatherData mosd = client.getWeather("Kioto");
        System.out.println(moscow);
    }
}
