package android.androidCode.helloWorld;


import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;


public class MainActivity extends Activity 
{  
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);  
        TextView textView = (TextView) findViewById(R.id.textView1);
        textView.setText("Hello World");
    }
}
