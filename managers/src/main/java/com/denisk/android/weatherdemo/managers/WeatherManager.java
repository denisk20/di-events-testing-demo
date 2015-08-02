package com.denisk.android.weatherdemo.managers;

import com.android.volley.Response;
import com.denisk.android.weatherdemo.CurrentWeather;
import com.denisk.android.weatherdemo.managers.event.WeatherUpdatedEvent;
import com.denisk.android.weatherdemo.net.IWeatherNetClient;
import com.squareup.otto.Bus;
import com.squareup.otto.Produce;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class WeatherManager {
    private IWeatherNetClient weatherNetClient;
    private IPreferencesManager preferencesManager;

    private Bus bus;

    public WeatherManager(
            IWeatherNetClient weatherNetClient,
            IPreferencesManager preferencesManager,
            Bus bus
    ) {
        this.weatherNetClient = weatherNetClient;
        this.preferencesManager = preferencesManager;
        this.bus = bus;

        bus.register(this);
    }

    public void fetchWeather(Response.ErrorListener errorListener) {
        weatherNetClient.fetchCurrentWeather(new Response.Listener<CurrentWeather>() {
            @Override
            public void onResponse(CurrentWeather currentWeather) {
                preferencesManager.persistWeather(currentWeather);

                bus.post(new WeatherUpdatedEvent(currentWeather));
            }
        }, errorListener);
    }

    @Produce
    public WeatherUpdatedEvent produceCurrentWeather() {
        return new WeatherUpdatedEvent(preferencesManager.getWeather());
    }
}
