package com.example.kobronah.rk1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import ru.mail.weather.lib.City;
import ru.mail.weather.lib.WeatherStorage;

public class CityActivity extends AppCompatActivity {

    private static final String CHANGE_WEATHER = "lala";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.city_layout);
        ViewGroup layout = (ViewGroup) findViewById(R.id.city_layout);

        for (final City city : City.values()){
            Button button = new Button(this);
            button.setText(city.name());

            layout.addView(button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CityActivity.this, LalaService.class);
//                    intent.putExtra("CityName", city);

                    WeatherStorage.getInstance(CityActivity.this).setCurrentCity(city);

                    startService(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

}
