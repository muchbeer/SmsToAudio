package king.muchbeer.aac.sendsms;

import java.util.ArrayList;

import king.muchbeer.aac.*;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Contacts.People;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.telephony.SmsManager;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivitySend extends Activity {

	private static final int CONTACT_ACTIVITY = 700;
	  public static final int PICK_CONTACT = 1;

	protected static final int RESULT_SPEECH = 2;
	Button buttonSend;
	 private TextView meLabel;
	//contacts 
	EditText textPhoneNo;
	Button button;
	//other staff
	EditText textSMS;
	Button button2Voice;
	
	ImageButton btnSpeak;
	MainActivityTab speak = new MainActivityTab();
	
	
	//Finest UI 
	
	    private ViewGroup messagesContainer;
	    private ScrollView scrollContainer;
	    	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendsms);
	
		
		// UI stuff
        messagesContainer = (ViewGroup) findViewById(R.id.messagesContainer);
        scrollContainer = (ScrollView) findViewById(R.id.scrollContainer);


		buttonSend = (Button) findViewById(R.id.buttonSend);
		 buttonSend.setOnClickListener(onSendMessageClickListener);
		
		 //contacts
		textPhoneNo = (EditText) findViewById(R.id.editTextPhoneNo);
		button= (Button) findViewById(R.id.btnGetContact);
		button.setOnClickListener(new OnClickListener() {

			public void onClick(View _view) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_PICK,
						Uri.parse("content://contacts/"));
				startActivityForResult(intent, PICK_CONTACT);
			
			}
			
		});
		//others
				textSMS = (EditText) findViewById(R.id.editTextSMS);
				textSMS.requestFocus();
				btnSpeak = (ImageButton) findViewById(R.id.btnSpeak);
			buttonSend.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String phoneNo = textPhoneNo.getText().toString();
					
					String sms = textSMS.getText().toString();

					try {
						 
						SmsManager smsManager = SmsManager.getDefault();
						smsManager.sendTextMessage(phoneNo, null, sms, null, null);
						Toast.makeText(getApplicationContext(), "SMS Sent!",
								Toast.LENGTH_LONG).show();
						sendMessage();
					} catch (Exception e) {
											speak.getmTTS().speak("SMS send fail, please try again later", TextToSpeech.QUEUE_ADD, null);
						Toast.makeText(getApplicationContext(),
								"SMS failed, please try again later!",
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}

				}
			});
			btnSpeak.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {

					Intent intent = new Intent(
							RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

					intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");

					try {
						startActivityForResult(intent, RESULT_SPEECH);
						//txtText.setText("");
					} catch (ActivityNotFoundException a) {
						Toast t = Toast.makeText(getApplicationContext(),
								"Ops! Your device doesn't support Speech to Text",
								Toast.LENGTH_SHORT);
						t.show();
					}
				}
			});
		
	}

	  private void sendMessage() {
	        if (textSMS != null) {
	            String messageString = textSMS.getText().toString();
	            textSMS.setText("");
	            showMessage(messageString, true);
	        }
	  } 	
	  
	  private void showMessage(String message, boolean leftSide) {
          final TextView textView = new TextView(MainActivitySend.this);
          textView.setTextColor(Color.BLACK);
          textView.setText(message);

          int bgRes = R.drawable.right_message_bg;

          LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                  LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

          if (!leftSide) {
              bgRes = R.drawable.right_message_bg;
              params.gravity = Gravity.RIGHT;
          }

          textView.setLayoutParams(params);

          textView.setBackgroundResource(bgRes);

          runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  messagesContainer.addView(textView);

                  // Scroll to bottom
                  if (scrollContainer.getChildAt(0) != null) {
                      scrollContainer.scrollTo(scrollContainer.getScrollX(), scrollContainer.getChildAt(0).getHeight());
                  }
                  scrollContainer.fullScroll(View.FOCUS_DOWN);
              }
          });
      }
	  private View.OnClickListener onSendMessageClickListener = new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              sendMessage();
          }
      };
      
  	@Override
  	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
  		super.onActivityResult(requestCode, resultCode, data);

  		switch (requestCode) {
  		case RESULT_SPEECH: {
  			if (resultCode == RESULT_OK && null != data) {

  				ArrayList<String> text = data
  						.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

  				//txtText.setText(text.get(0));
  				textSMS.setText(text.get(0));
  			}
  			break;
  		}
  		case(CONTACT_ACTIVITY): {
  			if(resultCode == Activity.RESULT_OK) {
  				//want to get the number an put it in the editText
  				//textPhoneNo.getText();
  			}
  		break;
  		}
  		
  		case(PICK_CONTACT) : {
  			if(resultCode ==Activity.RESULT_OK) {
  				Uri contactData = data.getData();
  				Cursor c = managedQuery(contactData,null,null,null,null);
  				c.moveToFirst();
  				String name = c.getString(c.getColumnIndexOrThrow(People.NAME));
  				String name2 = c.getString(c.getColumnIndexOrThrow(People.NUMBER));
  				
  				textPhoneNo.setText(name2);
  				//TextView tv = (TextView)findViewById(R.id.selected_contact_textview);
  		        //tv.setText(name);
  			}
  		}
  		}
  	}
}
