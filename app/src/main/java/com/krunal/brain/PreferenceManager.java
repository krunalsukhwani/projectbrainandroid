package com.krunal.brain;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static String TAG = "PreferenceManager";

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    public PreferenceManager(Context context) {
        mSharedPreferences = context.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }

    public void saveLoginData(String username, String firstname, String lastname, String email, String location) {
        mEditor.putString("username", "" + username);
        mEditor.putString("email", "" + email);
        mEditor.putString("firstname", "" + firstname);
        mEditor.putString("lastname", "" + lastname);
        mEditor.putString("location", "" + location);
        mEditor.commit();
    }

    public String getUsername() {
        return "" + mSharedPreferences.getString("username", "");
    }

    public String get(String key) {
        return "" + mSharedPreferences.getString("" + key,"");
    }

    public void set(String key, String value) {
        mEditor.putString("" + key, "" + value);
        mEditor.commit();
    }

    public void clear() {
        mEditor.clear();
        mEditor.commit();
    }
}
