package android.androidCode.switchExample;
import android.app.Activity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Switch.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends Activity {

	private TextView switchStatus;
	private Switch mySwitch;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		switchStatus = (TextView) findViewById(R.id.switchStatus);
		mySwitch = (Switch) findViewById(R.id.mySwitch);

		//set the switch to ON 
		mySwitch.setChecked(true);
		//attach a listener to check for changes in state

		mySwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

				if(isChecked){
					switchStatus.setText("Switch is currently ON");
				}else{
					switchStatus.setText("Switch is currently OFF");
				}
			}
		});

		//check the current state before we display the screen
		if(mySwitch.isChecked()){
			switchStatus.setText("Switch is currently ON");
		}
		else {
			switchStatus.setText("Switch is currently OFF");
		}
	}



}