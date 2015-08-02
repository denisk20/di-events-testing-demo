package com.denisk.android.weatherdemo.managers.event;

import com.denisk.android.weatherdemo.CurrentWeather;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class WeatherUpdatedEvent {
    private CurrentWeather currentWeather;

    public WeatherUpdatedEvent(CurrentWeather currentWeather) {
        this.currentWeather = currentWeather;
    }

    public CurrentWeather getCurrentWeather() {
        return currentWeather;
    }
}
