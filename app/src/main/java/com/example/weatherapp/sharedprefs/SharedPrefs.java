package com.example.weatherapp.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {
    SharedPreferences accountPreferences;
    SharedPreferences.Editor accountPreferencesEditor;
    Context context;

    public SharedPrefs(Context context){
        this.context=context;
        accountPreferences=context.getSharedPreferences("Account", Context.MODE_PRIVATE);
        accountPreferencesEditor=accountPreferences.edit();
    }

    public void clear(){
        accountPreferencesEditor.clear();
        accountPreferencesEditor.apply();
    }
}
