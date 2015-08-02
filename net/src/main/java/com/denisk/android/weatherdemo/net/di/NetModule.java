package com.denisk.android.weatherdemo.net.di;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.denisk.android.weatherdemo.common.di.CommonModule;
import com.denisk.android.weatherdemo.net.IWeatherNetClient;
import com.denisk.android.weatherdemo.net.ImageCache;
import com.denisk.android.weatherdemo.net.WeatherNetClient;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author denisk
 * @since 8/2/15.
 */
@Module(
        includes = CommonModule.class,
        library = true
)
public class NetModule {
    @Provides
    @Singleton
    RequestQueue provideRequestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    @Provides
    @Singleton
    IWeatherNetClient provideNetworkClient(RequestQueue requestQueue) {
        return new WeatherNetClient(requestQueue);
    }

    @Provides
    @Singleton
    public ImageLoader.ImageCache provideImageCache() {
        return new ImageCache();
    }

    @Provides
    @Singleton
    public ImageLoader provideImageLoader(
            RequestQueue requestQueue,
            ImageLoader.ImageCache imageCache
    ) {
        return new ImageLoader(requestQueue, imageCache);
    }
}
