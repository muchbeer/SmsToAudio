package king.muchbeer.aac;


import java.util.Locale;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

import king.muchbeer.aac.*;

import king.muchbeer.aac.headphone.SettingActivityHeadphone;
import king.muchbeer.aac.readsms.*;
import king.muchbeer.aac.sendsms.*;

public class MainActivityTab extends TabActivity implements OnInitListener {
	  //TTS object
	 public static TextToSpeech myTTS;
	     //status check code
	 public int MY_DATA_CHECK_CODE = 0;

	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tab);
		

		Resources res = getResources();
		TabHost tabHost= getTabHost();
		
		//Messages to Audio
		//must return to SettingsActivity
		
		Intent intentAndroid = new Intent().setClass (this, MainActivitySpeak.class);
		TabSpec tabSpecAndroid = tabHost
				.newTabSpec("Sms2Audio")
				.setIndicator("", res.getDrawable(R.drawable.icon_android_config2))
				.setContent(intentAndroid);
		
		//Next Activity to come
		Intent intentApple = new Intent().setClass(this, MainActivitySend.class);
		TabSpec tabSpecApple = tabHost
				.newTabSpec("Talk")
				.setIndicator("", res.getDrawable(R.drawable.icon_apple))
				.setContent(intentApple);
		
		//Next Activity to come
				Intent intentHeadphone = new Intent().setClass(this, SettingActivityHeadphone.class);
				TabSpec tabSpecHeadphone = tabHost
						.newTabSpec("Headphone")
						.setIndicator("", res.getDrawable(R.drawable.head4n))
						.setContent(intentHeadphone);
		
		//add all tabs
		tabHost.addTab(tabSpecAndroid);
		tabHost.addTab(tabSpecApple);
		tabHost.addTab(tabSpecHeadphone);
		
		Intent checkTTSIntent = new Intent();
	     checkTTSIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
	     startActivityForResult(checkTTSIntent, MY_DATA_CHECK_CODE);

		
		}
	
	
	
	//act on result of TTS data check
	 protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	     if (requestCode == MY_DATA_CHECK_CODE) {
	         if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
	             //the user has the necessary data - create the TTS
	         myTTS = new TextToSpeech(this, this);
	         }
	         else {
	                 //no data - install it now
	             Intent installTTSIntent = new Intent();
	             installTTSIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
	             startActivity(installTTSIntent);
	         }
	     }
	 }

	 
	  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.langEnglish:
			myTTS.setLanguage(Locale.US);
			 Toast m_toast = Toast.makeText(this, "Read Inbox sms in English", Toast.LENGTH_SHORT);
			m_toast.setGravity(Gravity.CENTER, 0, 0);
			m_toast.show();
			return true;
			
		case R.id.langFrench:
			myTTS.setLanguage(Locale.FRENCH);
			m_toast = Toast.makeText(this, "Read Inbox sms in French", Toast.LENGTH_SHORT);
			m_toast.setGravity(Gravity.CENTER, 0, 0);
			m_toast.show();
			return true;
			
		default:
			return super.onOptionsItemSelected(item);
		}
	
	}


	@Override
	public void onInit(int initStatus) {
		// TODO Auto-generated method stub
		//check for successful instantiation
	     if (initStatus == TextToSpeech.SUCCESS) {
	         Toast.makeText(this,"Text to speech engine is initialized ", Toast.LENGTH_LONG).show();
	         }
	     else if (initStatus == TextToSpeech.ERROR) {
	         Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
	     }
	}
	   
	
	public static TextToSpeech getmTTS() {
	    return myTTS;
	}
	
	
	}
