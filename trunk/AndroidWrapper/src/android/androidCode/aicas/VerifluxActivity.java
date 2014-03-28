package android.androidCode.aicas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class VerifluxActivity extends Activity {
    
    private String part1 = "VeriFlux is an automatic static " +
    		"code analysis tool for use in complete applications, " +
    		"as well as custom program parts. ";
    
    private String part2 = "Nothing needs to be " +
                "changed in the application to carry out the analysis.";
    
    private String part3 = "Error sources such as run-time errors or " +
            "thread-related errors are highlighted in the source code.";


    private SeekBar bar;
    private TextView text;
    
    
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
        Log.d("VeriFlux Activity", "entered");
        
        setContentView(R.layout.activity_veriflux);
        
        bar = (SeekBar) findViewById(R.id.seekBar1);
        text = (TextView) findViewById(R.id.vfText);
        text.setText(part1);
        
        bar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
        {
            public void onStopTrackingTouch(SeekBar seekBar) {}
            public void onStartTrackingTouch(SeekBar seekBar) {}
            
            public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) 
            {
                int max = bar.getMax();
                int thirdPart = max/3;
                int current = bar.getProgress();
                
                if(current<thirdPart)
                {
                    text.setText(part1);
                }
                else if(current>=thirdPart && current< (thirdPart*2))
                {
                    text.setText(part1 + part2);
                }
                else
                {
                    text.setText(part1 + part2 + part3);
                }
                
            }
        });
        
    }

}
