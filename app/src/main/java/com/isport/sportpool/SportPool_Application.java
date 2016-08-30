package com.isport.sportpool;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by bom on 1/6/2016.
 */
public class SportPool_Application extends Application {

    private static GoogleAnalytics analytics;
    private static Tracker tracker;

    public static GoogleAnalytics analytics() {
        return analytics;
    }

    public static Tracker tracker() {
        return tracker;
    }

    synchronized public Tracker getDefaultTracker() {
        if (tracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            tracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
        }
        return tracker;
    }

}
