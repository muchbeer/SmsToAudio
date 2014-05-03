package king.muchbeer.aac.sendsms;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import king.muchbeer.aac.*;
public class ContactPicker extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sendmain);
		

		   Intent intent = getIntent();
		    String dataPath = intent.getData().toString();
		    
		    final Uri data = Uri.parse(dataPath + "people/");
		    final Cursor c = managedQuery(data, null, null, null, null);
		        
		    String[] from = new String[] {People.NAME};
		    int[]  to = new int[] { R.id.itemTextView };
		        
		    SimpleCursorAdapter adapter = new SimpleCursorAdapter(this,
		                                                          R.layout.activity_listitemlayout,
		                                                          c,
		                                                          from,
		                                                          to);
		    ListView lv = (ListView)findViewById(R.id.contactListView);
		    lv.setAdapter(adapter);
		    lv.setOnItemClickListener(new OnItemClickListener() {
		     
					@Override
			public void onItemClick(AdapterView<?> parent, View view, int pos,
					long id) {
				// TODO Auto-generated method stub
				// Move the cursor to the selected item
		        c.moveToPosition(pos);
		        // Extract the row id.
		        int rowId = c.getInt(c.getColumnIndexOrThrow("_id"));
		        // Construct the result URI.
		        Uri outURI = Uri.parse(data.toString() + rowId);
		        Intent outData = new Intent();
		        outData.setData(outURI);
		        setResult(Activity.RESULT_OK, outData);
		        finish();
			}
		    });
		
	}

}
