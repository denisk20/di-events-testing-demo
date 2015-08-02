package com.denisk.android.weatherdemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.android.volley.Response;
import com.android.volley.VolleyError;
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
    @Bind(R.id.temp) TextView temp;
    @Bind(R.id.progress) ProgressBar progress;
    @Bind(R.id.root) View root;
    @Bind(R.id.fab) FloatingActionButton fab;
    @Bind(R.id.toolbar) Toolbar toolbar;
    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

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
        progress.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.fab)
    public void refreshWeather() {
        weatherManager.fetchWeather(new Response.Listener<CurrentWeather>() {
            @Override
            public void onResponse(CurrentWeather response) {
                Snackbar.make(root, R.string.weather_fetched, Snackbar.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Snackbar.make(root, R.string.fetch_failed, Snackbar.LENGTH_SHORT).show();
                progress.setVisibility(View.INVISIBLE);
                Log.e(WeatherActivity.class.getName(), "Couldn't fetch weather", error);
            }
        });
        progress.setVisibility(View.VISIBLE);
    }
}
