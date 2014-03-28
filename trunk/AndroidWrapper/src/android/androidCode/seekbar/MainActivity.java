package android.androidCode.seekbar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;


public class MainActivity extends Activity {

    private SeekBar mSeekBar;
    private TextView label;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSeekBar = (SeekBar) findViewById(R.id.seek_bar);

        mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) {
                int value = mSeekBar.getProgress();
                label.setText("Value: " + value);
            }
        });



        label = (TextView) findViewById(R.id.label);
        setContentView(label);
        label.setText("Hello");
        label.setText("Value: " + mSeekBar.getProgress());

    }
}