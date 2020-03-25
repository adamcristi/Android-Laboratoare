package com.example.lab6;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String KEY_BACKGROUND = "background";
    public static final String KEY_NOTIFICATIONS = "notifications";
    public static final String KEY_MUSIC = "music";
    private static View savedView;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from XML resource
        //addPreferencesFromResource(R.xml.preferences);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SettingsActivity settingsActivity = new SettingsActivity();
        settingsActivity.bindSummaryValue(findPreference(SettingsFragment.KEY_BACKGROUND));
        settingsActivity.bindSummaryValue(findPreference(SettingsFragment.KEY_NOTIFICATIONS));
        settingsActivity.bindSummaryValue(findPreference(SettingsFragment.KEY_MUSIC));

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        boolean backgroundPref = sharedPref.getBoolean(SettingsFragment.KEY_BACKGROUND, false);

        if(backgroundPref) {
            view.setBackgroundColor(Color.CYAN);
        } else {
            view.setBackgroundColor(Color.WHITE);
        }

        savedView = view;
        return view;
    }

    @Override
    public View getView() {
        return savedView;
    }

}
