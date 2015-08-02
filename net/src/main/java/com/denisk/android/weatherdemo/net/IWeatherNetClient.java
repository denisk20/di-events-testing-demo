package com.denisk.android.weatherdemo.net;

import com.android.volley.Response;
import com.denisk.android.weatherdemo.CurrentWeather;

/**
 * @author denisk
 * @since 8/2/15.
 */
public interface IWeatherNetClient {
    void fetchCurrentWeather(Response.Listener<CurrentWeather> successListener, Response.ErrorListener errorListener);
}
