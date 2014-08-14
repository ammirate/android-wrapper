package android.androidCode.testSwitch;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Switch.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {


	private Switch sw1;
	private Switch sw2;
	private TextView tw1;
	private EditText et2;
	private Button settext;
	private Button onoff;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);


		sw1 = (Switch) findViewById(R.id.switch1);
		tw1 = (TextView) findViewById(R.id.textView1);

		sw2 = (Switch) findViewById(R.id.switch2);
		et2 = (EditText) findViewById(R.id.editText1);
		onoff = (Button) findViewById(R.id.button1);
		settext = (Button) findViewById(R.id.button2);

		testSwitch1();
		testSwitch2();

	}

	private void testSwitch1(){
		sw1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				tw1.setText("Switch1 value: " + (sw1.isChecked() ? "On" : "Off") );
			}
		});
	}
	
	
	private void testSwitch2(){
		settext.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String text = et2.getText().toString();
				sw2.setText(text);
			}
		});
		
		
		onoff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sw2.toggle();
				Toast.makeText(getApplicationContext(), "On/Off pressed", Toast.LENGTH_SHORT).show();;
			}
		});
	}
 



}
