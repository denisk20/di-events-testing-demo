package com.denisk.android.weatherdemo.managers;

import android.content.SharedPreferences;
import com.denisk.android.weatherdemo.CurrentWeather;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class PreferencesManager implements IPreferencesManager {
    public static final String CITY = "city";
    public static final String DESCRIPTION = "description";
    public static final String DETAILED_DESCRIPTION = "detailed_description";
    public static final String TEMP = "temp";
    public static final String MIN_TEMP = "min_temp";
    public static final String MAX_TEMP = "max_temp";
    public static final String HUMIDITY = "humidity";
    public static final String PRESSURE = "pressure";
    public static final String WIND = "wind";
    public static final String WIND_DIRECTION = "wind_direction";
    public static final String ICON = "icon";
    public static final String CITY_ID = "city_id";

    private SharedPreferences prefs;

    public PreferencesManager(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    @Override
    public void persistWeather(CurrentWeather weather) {
        SharedPreferences.Editor edit = prefs.edit();

        edit.putString(CITY, weather.getCity());
        edit.putString(DESCRIPTION, weather.getDescription());
        edit.putString(DETAILED_DESCRIPTION, weather.getDetailedDescription());
        edit.putFloat(TEMP, weather.getTemp());
        edit.putFloat(MIN_TEMP, weather.getMinTemp());
        edit.putFloat(MAX_TEMP, weather.getMaxTemp());
        edit.putFloat(HUMIDITY, weather.getHumidity());
        edit.putFloat(PRESSURE, weather.getPressure());
        edit.putFloat(WIND, weather.getWind());
        edit.putFloat(WIND_DIRECTION, weather.getWindDirection());
        edit.putString(ICON, weather.getIconId());

        edit.apply();
    }

    @Override
    public CurrentWeather getWeather() {
        CurrentWeather currentWeather = new CurrentWeather();

        currentWeather.setCity(prefs.getString(CITY, ""));
        currentWeather.setDescription(prefs.getString(DESCRIPTION, ""));
        currentWeather.setDetailedDescription(prefs.getString(DETAILED_DESCRIPTION, ""));
        currentWeather.setTemp(prefs.getFloat(TEMP, 0));
        currentWeather.setMinTemp(prefs.getFloat(MIN_TEMP, 0));
        currentWeather.setMaxTemp(prefs.getFloat(MAX_TEMP, 0));
        currentWeather.setHumidity(prefs.getFloat(HUMIDITY, 0));
        currentWeather.setPressure(prefs.getFloat(PRESSURE, 0));
        currentWeather.setWind(prefs.getFloat(WIND, 0));
        currentWeather.setWindDirection(prefs.getFloat(WIND_DIRECTION, 0));
        currentWeather.setIconId(prefs.getString(ICON, "01d"));

        return currentWeather;
    }

    @Override
    public int getSelectedCityId() {
        return prefs.getInt(CITY_ID, 0);
    }

    @Override
    public void setSelectedCityId(int cityId) {
        prefs.edit().putInt(CITY_ID, cityId).apply();
    }
}
