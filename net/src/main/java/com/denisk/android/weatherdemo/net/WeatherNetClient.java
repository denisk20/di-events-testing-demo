package com.denisk.android.weatherdemo.net;

import android.net.Uri;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.denisk.android.weatherdemo.CurrentWeather;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class WeatherNetClient implements IWeatherNetClient {
    public static final String WEATHER_API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static final String ACCESS_TOKEN = "16f7300ce82fc1067fdab62bafdc8df4";

    private RequestQueue requestQueue;

    public WeatherNetClient(RequestQueue requestQueue) {
        this.requestQueue = requestQueue;
    }

    private String getUrl(int cityId) {
        return Uri.parse(WEATHER_API_URL)
                .buildUpon()
                .appendQueryParameter("id", cityId + "")
                .appendQueryParameter("units", "metric")
                .appendQueryParameter("APPID", ACCESS_TOKEN)
                .build()
                .toString();
    }

    @Override
    public void fetchCurrentWeather(int cityId, final Response.Listener<CurrentWeather> successListener, final Response.ErrorListener errorListener) {
        requestQueue.add(new JsonObjectRequest(Request.Method.GET, getUrl(cityId), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String city = response.getString("name");

                    JSONObject weather = response.getJSONArray("weather").getJSONObject(0);
                    String description = weather.getString("main");
                    String detailedDescription = weather.getString("description");
                    String icon = weather.getString("icon");

                    JSONObject main = response.getJSONObject("main");
                    float temp = (float) main.getDouble("temp");
                    float tempMin = (float) main.getDouble("temp_min");
                    float tempMax = (float) main.getDouble("temp_max");
                    float humidity = (float) main.getDouble("humidity");
                    float pressure = (float) main.getDouble("pressure");

                    JSONObject wind = response.getJSONObject("wind");
                    float speed = (float) wind.getDouble("speed");
                    float direction = (float) wind.getDouble("deg");

                    CurrentWeather currentWeather = new CurrentWeather();
                    currentWeather.setCity(city);
                    currentWeather.setDescription(description);
                    currentWeather.setDetailedDescription(detailedDescription);
                    currentWeather.setIconId(icon);
                    currentWeather.setTemp(temp);
                    currentWeather.setMinTemp(tempMin);
                    currentWeather.setMaxTemp(tempMax);
                    currentWeather.setHumidity(humidity);
                    currentWeather.setPressure(pressure);
                    currentWeather.setWind(speed);
                    currentWeather.setWindDirection(direction);

                    successListener.onResponse(currentWeather);

                } catch (JSONException e) {
                    errorListener.onErrorResponse(new ParseError(new Exception("Can't parse response: " + response, e)));
                }
            }
        }, errorListener));
    }
}
