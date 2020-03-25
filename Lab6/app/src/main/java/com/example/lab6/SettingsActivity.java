package com.example.lab6;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.PreferenceScreen;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load fragment
        //settingsFragment = new SettingsFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(android.R.id.content, settingsFragment, "preferences");
        fragmentTransaction.show(settingsFragment);
        fragmentTransaction.commit();

    }


    public void bindSummaryValue(Preference preference) {
        preference.setOnPreferenceChangeListener(listener);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
        listener.onPreferenceChange(preference, prefs.getBoolean(preference.getKey(), false));
    }

    private Preference.OnPreferenceChangeListener listener = new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference, Object newValue) {
            SharedPreferences sharedPreferences = preference.getSharedPreferences();

            if(sharedPreferences.getBoolean(preference.getKey(), false) != (Boolean) newValue) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(preference.getKey(), (Boolean) newValue);
                editor.apply();

                if (preference.getKey().equals("background")) {
                    changeBackgroundPreferencesActivity((Boolean) newValue);
                }

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


    public void changeBackgroundPreferencesActivity(boolean preferenceValue) {
        if(preferenceValue) {
            settingsFragment.getView().setBackgroundColor(Color.CYAN);
        } else {
            settingsFragment.getView().setBackgroundColor(Color.WHITE);
        }

    }

}