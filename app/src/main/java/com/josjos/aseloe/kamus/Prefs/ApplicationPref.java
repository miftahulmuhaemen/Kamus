package com.josjos.aseloe.kamus.Prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.josjos.aseloe.kamus.R;

public class ApplicationPref {
    private SharedPreferences prefs;
    private Context context;
    private String key = "first_run";
    private String language = "language";

    public ApplicationPref(Context context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context);
        this.context = context;
    }

    public void setFirstRun(Boolean input) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, input);
        editor.commit();
    }

    public Boolean getFirstRun() {
        return prefs.getBoolean(key, true);
    }

    public void setLanguage(boolean input){
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(language, input);
        editor.apply();
    }

        public Boolean getLanguage() {
        return prefs.getBoolean(language, true);
    }

}
