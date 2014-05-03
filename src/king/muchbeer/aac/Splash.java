package king.muchbeer.aac;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.EditText;

public class Splash extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		

		EditText output = (EditText) findViewById(R.id.edText);
		output.setText("TALKING SMS WHEN WALKING. " +
					""	+
				"This app helps to change sms to " +
				""	+
				"speech helping you to listen to " +
				""	+
				"sms text when doing other stuff "	   +
				""	+
				"It works when you plug headset "  +
				""	+
				"on your phone, send and read sms using audio. ENJOY!!!"); 
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(7000);
					
				}catch(InterruptedException e) {
					e.printStackTrace();
				}finally {
					// Intent myIntent = new Intent(Splash.this, SettingsActivity.class);
					 Intent myIntent = new Intent(Splash.this, MainActivityTab.class);
		                startActivity(myIntent);
		                finish();
		            
				}
			}
		}; timer.start();
	}

	  @Override
	    protected void onPause() {
	        super.onDestroy();
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
