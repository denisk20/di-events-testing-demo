package com.denisk.android.weatherdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.denisk.android.weatherdemo.common.di.CommonModule;
import com.denisk.android.weatherdemo.di.WeatherActivityModule;
import com.denisk.android.weatherdemo.managers.WeatherManager;
import com.denisk.android.weatherdemo.managers.event.WeatherUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import dagger.ObjectGraph;

import javax.inject.Inject;

public class WeatherActivity extends AppCompatActivity {

    @Inject
    WeatherManager weatherManager;
    @Inject
    Bus bus;
    private ObjectGraph objectGraph;
    private TextView temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);
        temp = (TextView) findViewById(R.id.temp);

        objectGraph = ObjectGraph.create(new WeatherActivityModule(), new CommonModule(this));
        objectGraph.inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onDestroy() {
        // Eagerly clear the reference to the activity graph to allow it to be garbage collected as
        // soon as possible.
        objectGraph = null;

        super.onDestroy();
    }

    @Subscribe
    public void onWeatherUpdated(WeatherUpdatedEvent event) {
        temp.setText(event.getCurrentWeather().getTemp() + "");
    }
}
