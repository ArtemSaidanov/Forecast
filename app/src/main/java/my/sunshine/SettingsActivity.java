package my.sunshine;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;



/**
 * Created by Godfrid on 11.10.2016.
 */
public class SettingsActivity extends PreferenceActivity  implements Preference.OnPreferenceChangeListener{


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref);
        bindPreferenceSummaryToValue(findPreference(getString(R.string.preference_location_key)));
        bindPreferenceSummaryToValue(findPreference(getString(R.string.preferences_unitSettings_key)));


    }
    private void bindPreferenceSummaryToValue(Preference preference){
        /**Set the listener to watch for value change*/
        preference.setOnPreferenceChangeListener(this);

        /**Trigger the listener immediately with the preference's current value*/
        onPreferenceChange(preference, PreferenceManager
        .getDefaultSharedPreferences(preference.getContext())
        .getString(preference.getKey(),""));
    }
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String stringValue = newValue.toString();

        if(preference instanceof ListPreference){
            /**For the ListPreferences, look u the correct display value in the preference's
             * 'entries' list(since they have separate labels/values)*/
        ListPreference listPreference =(ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(stringValue);
            if(prefIndex >= 0){
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
        else {
            preference.setSummary(stringValue);
        }
        return true;
    }
}
