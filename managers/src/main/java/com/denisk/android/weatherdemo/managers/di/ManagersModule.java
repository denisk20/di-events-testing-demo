package com.denisk.android.weatherdemo.managers.di;

import android.content.Context;
import android.content.SharedPreferences;
import com.denisk.android.weatherdemo.managers.IPreferencesManager;
import com.denisk.android.weatherdemo.managers.PreferencesManager;
import com.denisk.android.weatherdemo.managers.WeatherManager;
import com.denisk.android.weatherdemo.net.IWeatherNetClient;
import com.denisk.android.weatherdemo.net.di.NetModule;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author denisk
 * @since 8/2/15.
 */
@Module(
        includes = NetModule.class,
        library = true
)
public class ManagersModule {
    @Provides
    @Singleton
    SharedPreferences provideSharedPreferences(Context context) {
        return context.getSharedPreferences("weather_prefs", Context.MODE_PRIVATE);
    }

    @Provides
    @Singleton
    IPreferencesManager providePreferencesManager(SharedPreferences sharedPreferences) {
        return new PreferencesManager(sharedPreferences);
    }

    @Provides
    @Singleton
    Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    WeatherManager provideWeatherManager(
            IPreferencesManager preferencesManager,
            IWeatherNetClient weatherNetClient,
            Bus bus
    ) {
        return new WeatherManager(
                weatherNetClient,
                preferencesManager,
                bus
        );
    }
}
