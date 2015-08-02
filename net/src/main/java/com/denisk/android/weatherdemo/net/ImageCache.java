package com.denisk.android.weatherdemo.net;

import android.graphics.Bitmap;
import android.util.LruCache;
import com.android.volley.toolbox.ImageLoader;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class ImageCache implements ImageLoader.ImageCache {
    public static final int MAX_CACHE_SIZE = 10; //10 images

    private final LruCache<String, Bitmap> cache = new LruCache<>(MAX_CACHE_SIZE);

    @Override
    public Bitmap getBitmap(String url) {
        return cache.get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        cache.put(url, bitmap);
    }
}
