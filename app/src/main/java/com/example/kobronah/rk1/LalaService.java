package com.example.kobronah.rk1;

import android.app.IntentService;
import android.content.Intent;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class LalaService extends IntentService {

    public static final String CHANGE_WEATHER = "lala";

    public LalaService() {
        super("LalaService");
    }

    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        WeatherStorage weatherStorage = WeatherStorage.getInstance(this);

        try {
            City city = weatherStorage.getCurrentCity();
            Weather weather = WeatherUtils.getInstance().loadWeather(city);
            weatherStorage.saveWeather(city, weather);

            Intent newIntent = new Intent(CHANGE_WEATHER);
            sendBroadcast(newIntent);
        } catch (Exception ignored) {

        }
    }


}
