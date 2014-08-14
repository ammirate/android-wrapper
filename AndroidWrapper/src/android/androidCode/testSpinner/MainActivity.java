package android.androidCode.testSpinner;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
public class MainActivity extends Activity {
	Spinner spnr;
	TextView index;
	TextView item;
	
	String[] items = {
			"item1",
			"item2",
			"item3",
			"item4",
			"item5"
	};


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		spnr = (Spinner)findViewById(R.id.spinner);
		index = (TextView)findViewById(R.id.index);
		item = (TextView)findViewById(R.id.item);
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				this, android.R.layout.simple_spinner_item, items);
		spnr.setAdapter(adapter);
		spnr.setOnItemSelectedListener(
				new AdapterView.OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						int position = spnr.getSelectedItemPosition();
						Toast.makeText(getApplicationContext(),"You have selected "+items[+position],Toast.LENGTH_LONG).show();
						index.setText(Integer.toString(position));
						item.setText(spnr.getSelectedItem().toString());

					}
					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
					}
				}
				);
	}
}