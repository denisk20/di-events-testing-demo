package com.denisk.android.weatherdemo.managers;

import android.content.SharedPreferences;
import com.denisk.android.weatherdemo.CurrentWeather;
import dagger.Module;
import dagger.ObjectGraph;
import dagger.Provides;
import org.junit.Before;
import org.junit.Test;

import javax.inject.Inject;
import javax.inject.Singleton;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyFloat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class PreferencesManagerTest {

    @Inject PreferencesManager preferencesManager;
    @Inject SharedPreferences sharedPreferences;
    @Inject SharedPreferences.Editor editor;

    @Before
    public void setUp() throws Exception {
        ObjectGraph.create(new PreferencesManagerTestModule()).inject(this);

    }

    @Test
    public void testGetWeather() throws Exception {
        float temp = 22f;
        when(sharedPreferences.getFloat(eq(PreferencesManager.TEMP), anyFloat())).thenReturn(temp);

        assertEquals(temp, preferencesManager.getWeather().getTemp(), 0);
    }

    @Test
    public void testPersistWeather() {
        CurrentWeather weather = new CurrentWeather();
        String city = "MyCity";
        weather.setCity(city);
        preferencesManager.persistWeather(weather);

        verify(editor, times(1)).putString(PreferencesManager.CITY, city);
    }

    @Module(
            injects = PreferencesManagerTest.class
    )
    static class PreferencesManagerTestModule {
        @Provides
        @Singleton
        SharedPreferences.Editor provideEditor() {
            SharedPreferences.Editor editor = mock(SharedPreferences.Editor.class);

            when(editor.putString(anyString(), anyString())).thenReturn(editor);
            when(editor.putFloat(anyString(), anyFloat())).thenReturn(editor);
            when(editor.putInt(anyString(), anyInt())).thenReturn(editor);

            return editor;
        }

        @Provides
        @Singleton
        SharedPreferences provideSharedPreferences(SharedPreferences.Editor editor) {
            SharedPreferences sharedPreferences = mock(SharedPreferences.class);

            when(sharedPreferences.edit()).thenReturn(editor);

            return sharedPreferences;
        }

        @Provides
        @Singleton
        PreferencesManager providePreferencesManager(SharedPreferences sharedPreferences) {
            return new PreferencesManager(sharedPreferences);
        }
    }
}