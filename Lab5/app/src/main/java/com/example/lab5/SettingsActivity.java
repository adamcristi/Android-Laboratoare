package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load fragment
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(android.R.id.content, settingsFragment);
        fragmentTransaction.commit();
    }

    public static void bindSummaryValue(Preference preference) {
        preference.setOnPreferenceChangeListener(listener);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        listener.onPreferenceChange(preference, prefs.getBoolean(preference.getKey(), false));
    }

    private static Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            SharedPreferences sharedPreferences = preference.getSharedPreferences();

            if(sharedPreferences.getBoolean(preference.getKey(), false) != (Boolean) newValue) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(preference.getKey(), (Boolean) newValue);
                editor.apply();

                String message = "Value of " + preference.getKey() + " updated to: " + newValue.toString();
                Toast.makeText(preference.getContext(), message, Toast.LENGTH_SHORT).show();
                Log.d("Status", message);
            } else {
                String message = "Value of " + preference.getKey() + " obtained from saving: " + newValue.toString();
                Log.d("Status", message);
            }

            return true;
        }
    };


    /*private static SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Log.d("Cheie", "key" + key.toString());
        }
    };*/

}