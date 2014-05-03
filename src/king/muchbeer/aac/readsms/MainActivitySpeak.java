package king.muchbeer.aac.readsms;

import java.util.ArrayList;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import king.muchbeer.aac.readsms.data.*;
import king.muchbeer.aac.MainActivityTab;

public class MainActivitySpeak extends ListActivity {
	 

		public String speeding;
		MainActivityTab speak = new MainActivityTab();
		 
		
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		 SharedPreferences getData = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
			
			speeding = getData.getString("speed", "speed_N");
		
		List<SMSData> smsList = new ArrayList<SMSData>();
		
		Uri uri = Uri.parse("content://sms/inbox");
		Cursor c= getContentResolver().query(uri, null, null ,null,null);
		startManagingCursor(c);
		
		// Read the sms data and store it in the list
				if(c.moveToFirst()) {
					for(int i=0; i < c.getCount(); i++) {
						SMSData sms = new SMSData();
						sms.setBody(c.getString(c.getColumnIndexOrThrow("body")).toString());
						sms.setNumber(c.getString(c.getColumnIndexOrThrow("address")).toString());
						smsList.add(sms);
						
						c.moveToNext();
					}
				}
		
		// Set smsList in the ListAdapter
				setListAdapter(new ListAdapter(this, smsList));
				//setListAdapter(new ListAdapter(this, smsList));
			
	}

	protected void onListItemClick(ListView l, View v, int position, long id) {
		SMSData sms = (SMSData)getListAdapter().getItem(position);
		
		Toast.makeText(getApplicationContext(), sms.getBody(), Toast.LENGTH_LONG).show();
		// String words = enteredText.getText().toString();
		String words = sms.getBody();
		
		//String words2 = sms.toString();
		//speakWords(words2);
		//myTTS.speak(words,TextToSpeech.QUEUE_ADD, null);
		
		//speak.getmTTS().setSpeechRate(1.0f);
		if(speeding.contentEquals("speed_VS")) {
			speak.getmTTS().setSpeechRate(0.1f); 
			speak.getmTTS().speak(words, TextToSpeech.QUEUE_ADD, null);
	}	else if(speeding.contentEquals("speed_S")) {
		speak.getmTTS().setSpeechRate(0.5f); 
		speak.getmTTS().speak(words, TextToSpeech.QUEUE_ADD, null);
}else if(speeding.contentEquals("speed_N")) {
	speak.getmTTS().setSpeechRate(1.0f); 
	speak.getmTTS().speak(words, TextToSpeech.QUEUE_ADD, null);
}else if(speeding.contentEquals("speed_F")) {
	speak.getmTTS().setSpeechRate(1.5f); 
	speak.getmTTS().speak(words, TextToSpeech.QUEUE_ADD, null);
}else if(speeding.contentEquals("speed_VF")) {
	speak.getmTTS().setSpeechRate(2.0f); 
	speak.getmTTS().speak(words, TextToSpeech.QUEUE_ADD, null);
}
	
		
		
	}
	
	  @Override
	    protected void onPause() {
	        super.onPause();
	    
	    }

	    @Override
	    protected void onStop() {
	        super.onStop();
	       
	    }

	    @Override
	    protected void onDestroy() {
	        super.onDestroy();
	   
	    }
 
 
}
