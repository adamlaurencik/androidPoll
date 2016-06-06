package cz.muni.fi.pv239.androidpoll;

import android.content.Context;

import com.securepreferences.SecurePreferences;

/**
 * Created by Adam on 06.06.2016.
 */
public class SharedPrefsContainer {
    private static SecurePreferences preferences = null;

    public static  SecurePreferences getSharedPreferences(Context context){
        if(preferences==null){
            preferences= new SecurePreferences(context);
        }
        return preferences;
    }
}
