package android.androidCode.analogClock_Example;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AnalogClock;
 
//IS2 project - MAEESTRO 2013/2014
public class MainActivity extends Activity {
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
 
		AnalogClock ac = (AnalogClock) findViewById(R.id.analogClock1);
		//what can i do with AnalogClock?
 

	}
 
}