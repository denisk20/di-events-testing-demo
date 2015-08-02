package com.denisk.android.weatherdemo.common.di;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

/**
 * @author denisk
 * @since 8/2/15.
 */
@Module(
        library = true
)
public class CommonModule {
    private Context context;

    public CommonModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }
}
