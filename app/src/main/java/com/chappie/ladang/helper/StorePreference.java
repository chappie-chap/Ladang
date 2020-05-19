package com.chappie.ladang.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class StorePreference {
    private Context mContext;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mPreferenceEditor;

    @SuppressLint("CommitPrefEdits")
    public StorePreference(Context aContext) {
        mContext = aContext;
        mSharedPreferences = mContext.getSharedPreferences("LADANG", Context.MODE_PRIVATE);
        mPreferenceEditor = mSharedPreferences.edit();

    }

    public void setString(String key, String value) {
        mPreferenceEditor.putString(key, value);
        mPreferenceEditor.commit();
    }

    public void setInt(String key, int value) {
        mPreferenceEditor.putInt(key, value);
        mPreferenceEditor.commit();
    }

    public String getStringValue(String key) {
        String aName = mSharedPreferences.getString(key, null);
        return aName;
    }

    public int getIntValue(String key) {
        return mSharedPreferences.getInt(key, -1);
    }

    public void setBoolean(String key, boolean value) {
        mPreferenceEditor.putBoolean(key, value);
        mPreferenceEditor.commit();
    }

    public boolean getBooleanValue(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void clearEditor() {
        SharedPreferences sharedPrefs = mContext.getSharedPreferences("LADANG", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPrefs.edit();
        editor.clear();
        editor.apply();

    }

}