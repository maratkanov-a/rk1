package com.example.kobronah.rk1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.Weather;
import ru.mail.weather.lib.WeatherStorage;
import ru.mail.weather.lib.WeatherUtils;

public class MainActivity extends AppCompatActivity {

    public final static String CHANGE_WEATHER = "lala";

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            TextView weather = (TextView) findViewById(R.id.weather);
            Button cityButton = (Button) findViewById(R.id.city);

            City cityName = WeatherStorage.getInstance(MainActivity.this).getCurrentCity();

            cityButton.setText(cityName.name());

            Weather be = WeatherStorage.getInstance(MainActivity.this).getLastSavedWeather(cityName);
            String text = String.format("%s %s", be.getTemperature(), be.getDescription());

            weather.setText(text);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(MainActivity.this, LalaService.class);
        startService(intent);
        WeatherStorage weatherStorage = WeatherStorage.getInstance(MainActivity.this);

    }

    @Override
    public void onStart() {
        super.onStart();
        setContentView(R.layout.main);
        WeatherStorage weatherStorage = WeatherStorage.getInstance(MainActivity.this);
        Button cityButton = (Button) findViewById(R.id.city);
        Button update = (Button) findViewById(R.id.update);
        Button dontUpdate = (Button) findViewById(R.id.dontUpdate);

        IntentFilter intentFilter = new IntentFilter(CHANGE_WEATHER);

        registerReceiver(receiver, intentFilter);

        cityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CityActivity.class);
                startActivity(intent);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LalaService.class);
                WeatherUtils.getInstance().schedule(MainActivity.this, intent);
            }
        });

        dontUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LalaService.class);
                WeatherUtils.getInstance().unschedule(MainActivity.this, intent);
            }
        });


    }

    @Override
    public void onStop() {
        super.onStop();
        unregisterReceiver(receiver);

    }
}
