package android.androidCode.radioButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final RadioButton yes = (RadioButton) findViewById(R.id.radioYes);
        final RadioButton no = (RadioButton) findViewById(R.id.radioNo);
        final RadioButton maybe = (RadioButton) findViewById(R.id.radioMaybe);
        
        final Button button = (Button) findViewById(R.id.button1);
        button.setEnabled(false);
        final TextView label = (TextView) findViewById(R.id.textView1);
        
        OnClickListener radioListener = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case R.id.radioYes: 
                        {
                            button.setEnabled(true);
                            label.setText("Do you accept this conditions?");
                            break;
                        }
                    case R.id.radioNo:
                    {
                        button.setEnabled(false);
                        label.setText("Do you accept this conditions?");
                        break;
                    }
                    case R.id.radioMaybe:
                    {
                        button.setEnabled(false);
                        label.setText("Are you joking???");
                        break;
                    }
                }
                
            }
        };
        
        yes.setOnClickListener(radioListener);
        no.setOnClickListener(radioListener);
        maybe.setOnClickListener(radioListener);
        
        button.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                label.setText("Thanks :-) ");
            }
        });
        
        
    }



}
