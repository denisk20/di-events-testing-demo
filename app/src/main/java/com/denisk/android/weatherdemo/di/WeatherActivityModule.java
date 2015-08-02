package com.denisk.android.weatherdemo.di;

import com.denisk.android.weatherdemo.WeatherActivity;
import com.denisk.android.weatherdemo.managers.di.ManagersModule;
import dagger.Module;

/**
 * @author denisk
 * @since 8/2/15.
 */
@Module(
        includes = ManagersModule.class,
        injects = WeatherActivity.class
)
public class WeatherActivityModule {
}
