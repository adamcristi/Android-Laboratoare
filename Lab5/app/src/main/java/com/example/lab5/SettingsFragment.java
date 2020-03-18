package com.example.lab5;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
    public static final String KEY_NOTIFICATIONS = "notifications";
    public static final String KEY_MUSIC = "music";

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        // Load the preferences from XML resource
        //addPreferencesFromResource(R.xml.preferences);
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SettingsActivity.bindSummaryValue(findPreference(SettingsFragment.KEY_NOTIFICATIONS));
        SettingsActivity.bindSummaryValue(findPreference(SettingsFragment.KEY_MUSIC));

        /*Context context = this.getActivity();
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        Boolean notificationPreference = sharedPreferences.getBoolean(SettingsFragment.KEY_NOTIFICATIONS, false);
        Toast.makeText(context, "Notification" + notificationPref.toString(), Toast.LENGTH_SHORT).show();
        Log.d("VALOARE", "Notification pref " + notificationPreference.toString());
        Boolean musicPreference = sharedPreferences.getBoolean(SettingsFragment.KEY_MUSIC, false);
        Toast.makeText(context, "Music" + musicPref.toString(), Toast.LENGTH_SHORT).show();
        Log.d("VALOARE", "Music pref " + musicPreference.toString());*/


        /*SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());
        Boolean notificationPref = sharedPref.getBoolean(SettingsFragment.KEY_NOTIFICATIONS, false);
        Log.d("VALOARE", "Notification default shared " + notificationPref.toString());
        Boolean musicPref = sharedPref.getBoolean(SettingsFragment.KEY_MUSIC, false);
        Log.d("VALOARE", "Music default shared " + musicPref.toString());*/


    }

}
