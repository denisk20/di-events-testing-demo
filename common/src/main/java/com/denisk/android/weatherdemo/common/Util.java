package com.denisk.android.weatherdemo.common;

/**
 * @author denisk
 * @since 8/2/15.
 */
public class Util {
    public int indexOf(int[] array, int element) {
        for (int i = 0; i < array.length; i++) {
            if(element == array[i]) {
                return i;
            }
        }
        return -1;
    }
}
