package king.muchbeer.aac.headphone;

import android.app.backup.BackupManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import king.muchbeer.aac.*;
public class SettingActivityHeadphone extends PreferenceActivity {
	  @Override
	  protected void onCreate(Bundle state){
	    super.onCreate(state);
	    addPreferencesFromResource(R.xml.preferences);
	 
	    Preference pTTS = findPreference(getString(R.string.tts_settings));
	    Intent ttsIntent = getTtsIntent();
		if (ttsIntent != null) {
			pTTS.setIntent(ttsIntent);
		} else {
			pTTS.setEnabled(false);
			pTTS.setSummary("Unable to find TTS settings! If it exists on your device");
		}
	  }

	  
	  //TTS Setting
		private Intent getTtsIntent() {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			int sdkVer = android.os.Build.VERSION.SDK_INT;
			if (isClassExist("com.android.settings.TextToSpeechSettings")) {
				if (sdkVer >= 11 & sdkVer <= 13) {
					intent.setAction(android.provider.Settings.ACTION_SETTINGS);
					intent.putExtra(EXTRA_SHOW_FRAGMENT, "com.android.settings.TextToSpeechSettings");
					intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, intent.getExtras());
				} else intent.setClassName("com.android.settings", "com.android.settings.TextToSpeechSettings");
			} else if (isClassExist("com.android.settings.Settings$TextToSpeechSettingsActivity")) {
				if (sdkVer == 14) {
					intent.setAction(android.provider.Settings.ACTION_SETTINGS);
					intent.putExtra(EXTRA_SHOW_FRAGMENT, "com.android.settings.tts.TextToSpeechSettings");
					intent.putExtra(EXTRA_SHOW_FRAGMENT_ARGUMENTS, intent.getExtras());
				} else intent.setClassName("com.android.settings", "com.android.settings.Settings$TextToSpeechSettingsActivity");
			} else if (isClassExist("com.google.tv.settings.TextToSpeechSettingsTop")) {
				intent.setClassName("com.google.tv.settings", "com.google.tv.settings.TextToSpeechSettingsTop");
			} else return null;
			return intent;
		}
		
		//tts number 2
		private boolean isClassExist(String name) {
			try {
				PackageInfo pkgInfo = getPackageManager().getPackageInfo(
					name.substring(0, name.lastIndexOf(".")), PackageManager.GET_ACTIVITIES);
				if (pkgInfo.activities != null) {
					for (int n = 0; n < pkgInfo.activities.length; n++) {
						if (pkgInfo.activities[n].name.equals(name)) return true;
					}
				}
			} catch (PackageManager.NameNotFoundException e) {}
			return false;
		}
	  @Override
	  protected void onResume() {
	    super.onResume();

	    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
	    updateView(sharedPreferences);

	    sharedPreferences.registerOnSharedPreferenceChangeListener(new OnSharedPreferenceChangeListener() {
	      @Override
	      public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
	        OnOffAppWidgetProvider.update(getApplicationContext());

	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
	          BackupManager.dataChanged(getPackageName());
	        }

	        onContentChanged();
	        updateView(sharedPreferences);
	      }
	    });
	  }

	  // TODO(smike): Find a cleaner way to update preferences that might have been changed in
	  // elsewhere. There must be a way to do all prefs automatically.
	  private void updateView(SharedPreferences sharedPreferences) {
	    // enabled is the only setting that could have changed (by the widget).
	    boolean enabled = SettingsUtil.isEnabled(this);

	    String key = getString(R.string.prefsKey_enabled);
	    CheckBoxPreference enabledPreference = (CheckBoxPreference)this.findPreference(key);
	    enabledPreference.setChecked(enabled);

	    key = getString(R.string.prefsKey_activationMode);
	    this.findPreference(key).setEnabled(enabled);

	    key = getString(R.string.prefsKey_volume);
	    this.findPreference(key).setEnabled(enabled);
	    
	    key = getString(R.string.prefsKey_preferSco);
	    this.findPreference(key).setEnabled(enabled);
	    
	    //key = getString(R.string.volKey);
	    //this.findPreference(key).setEnabled(enabled);
	  }
	}
//25  17  
