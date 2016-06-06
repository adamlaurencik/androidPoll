package cz.muni.fi.pv239.androidpoll;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Created by Adam on 05.05.2016.
 */
public class AndroidPollApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        SharedPrefsContainer.getSharedPreferences(this);
    }
}
