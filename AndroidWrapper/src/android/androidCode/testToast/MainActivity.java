package android.androidCode.testToast;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Button bShort = (Button) findViewById(R.id.button1);
		Button bLong = (Button) findViewById(R.id.button2);

		bShort.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Short Toast", Toast.LENGTH_SHORT).show();;

			}
		});

		bLong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "Long Toast", Toast.LENGTH_LONG).show();;

			}
		});

	}


}
