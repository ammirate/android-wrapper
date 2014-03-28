package android.androidCode.sayHello;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {

    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView label =  (TextView) findViewById(R.id.textView1);
        final Button button = (Button) findViewById(R.id.button1);
        
        
        OnClickListener l = new OnClickListener() {
            
            int i=0;
            public void onClick(View v) {
                label.setText("Pressed "+i+" times");
                i++;
            }
        };
        
        button.setOnClickListener(l);
        
    }


}
