package com.dozen.dozendemo.clear;

/**
 * Created by Dozen on 2019/06/04 22:01.
 * Describe:
 */
import android.graphics.drawable.Drawable;

public class CacheListItem {

    private long mCacheSize;
    private String mPackageName, mApplicationName;
    private Drawable mIcon;


    public CacheListItem(String packageName, String applicationName, Drawable icon, long cacheSize) {
        mCacheSize = cacheSize;
        mPackageName = packageName;
        mApplicationName = applicationName;
        mIcon = icon;
    }


    public Drawable getApplicationIcon() {
        return mIcon;
    }


    public String getApplicationName() {
        return mApplicationName;
    }


    public long getCacheSize() {
        return mCacheSize;
    }


    public String getPackageName() {
        return mPackageName;
    }
}
