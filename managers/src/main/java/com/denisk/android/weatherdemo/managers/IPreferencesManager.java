package com.denisk.android.weatherdemo.managers;

import com.denisk.android.weatherdemo.CurrentWeather;

/**
 * @author denisk
 * @since 8/2/15.
 */
public interface IPreferencesManager {
    void persistWeather(CurrentWeather weather);

    CurrentWeather getWeather();

    int getSelectedCityId();

    void setSelectedCityId(int cityId);
}
