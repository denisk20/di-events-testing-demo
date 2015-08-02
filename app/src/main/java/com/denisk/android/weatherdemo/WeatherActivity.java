package com.denisk.android.weatherdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemSelected;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.denisk.android.weatherdemo.common.di.CommonModule;
import com.denisk.android.weatherdemo.di.WeatherActivityModule;
import com.denisk.android.weatherdemo.managers.IPreferencesManager;
import com.denisk.android.weatherdemo.managers.WeatherManager;
import com.denisk.android.weatherdemo.managers.event.WeatherUpdatedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;
import dagger.ObjectGraph;

import javax.inject.Inject;
import java.util.Arrays;

public class WeatherActivity extends AppCompatActivity {
    public static final String WEATHER_IMAGE_URL = "http://openweathermap.org/img/w/";

    @Inject IPreferencesManager preferencesManager;
    @Inject WeatherManager weatherManager;
    @Inject Bus bus;
    @Inject ImageLoader imageLoader;

    @Bind(R.id.city) TextView city;
    @Bind(R.id.icon) NetworkImageView icon;
    @Bind(R.id.description) TextView description;
    @Bind(R.id.description_detailed) TextView descriptionDetailed;
    @Bind(R.id.temp) TextView temp;
    @Bind(R.id.temp_high) TextView tempHigh;
    @Bind(R.id.temp_low) TextView tempLow;
    @Bind(R.id.humidity) TextView humidity;
    @Bind(R.id.pressure) TextView pressure;
    @Bind(R.id.wind) TextView wind;
    @Bind(R.id.wind_direction) TextView windDirection;

    @Bind(R.id.progress) ProgressBar progress;
    @Bind(R.id.root) View root;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.city_selector) Spinner citySelector;

    //todo add @BindArray when it's available
    private int[] cityIds;

    private ObjectGraph objectGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //todo remove when @BindArray is available in Butterknife
        cityIds = getResources().getIntArray(R.array.city_ids);

        objectGraph = ObjectGraph.create(new WeatherActivityModule(), new CommonModule(this));
        objectGraph.inject(this);

        int selectedCityId = preferencesManager.getSelectedCityId();
        if (selectedCityId == 0) {
            selectedCityId = cityIds[0];
            preferencesManager.setSelectedCityId(selectedCityId);
        }
        citySelector.setSelection(Arrays.binarySearch(cityIds, selectedCityId));
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
        CurrentWeather currentWeather = event.getCurrentWeather();

        city.setText(currentWeather.getCity());
        icon.setImageUrl(WEATHER_IMAGE_URL + currentWeather.getIconId() + ".png", imageLoader);
        temp.setText(currentWeather.getTemp() + " C");
        tempHigh.setText(currentWeather.getMaxTemp() + " C");
        tempLow.setText(currentWeather.getMinTemp() + " C");
        description.setText(currentWeather.getDescription());
        descriptionDetailed.setText(currentWeather.getDetailedDescription());
        humidity.setText(currentWeather.getHumidity() + " %");
        pressure.setText(currentWeather.getPressure() + " hPa");
        wind.setText(currentWeather.getWind() + " m/s");
        windDirection.setText(currentWeather.getWindDirection() + " dgr");

        progress.setVisibility(View.INVISIBLE);
    }

    @OnClick(R.id.fab)
    public void refreshWeather() {
        weatherManager.fetchWeather(cityIds[citySelector.getSelectedItemPosition()], new Response.Listener<CurrentWeather>() {
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

    @OnItemSelected(R.id.city_selector)
    public void onCitySelected(int position) {
        preferencesManager.setSelectedCityId(cityIds[position]);
    }
}
