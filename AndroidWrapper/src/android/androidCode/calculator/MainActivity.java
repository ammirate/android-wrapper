package android.androidCode.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button add = (Button) findViewById(R.id.button1);
        final Button sub = (Button) findViewById(R.id.button2);
        final Button mul = (Button) findViewById(R.id.button3);
        final Button div = (Button) findViewById(R.id.button4);

        final EditText text1 = (EditText) findViewById(R.id.editText1);
        final EditText text2 = (EditText) findViewById(R.id.editText2);

        final TextView tv = (TextView) findViewById(R.id.textView1);
        
        float n1 = 0;
        float n2 = 0;
        

        OnClickListener calculate = new OnClickListener() {

            public void onClick(View v) {
                float n1 = Float.parseFloat(text1.getText().toString());
                float n2 = Float.parseFloat(text2.getText().toString());
           
                switch (v.getId()){
                case R.id.button1 :
                    tv.setText(String.valueOf(n1+n2));
                    break;
                    
                case R.id.button2 :
                    
                    tv.setText(String.valueOf(n1-n2));
                    break;
                    
                case R.id.button3 :
                    tv.setText(String.valueOf(n1*n2));
                    break;
                    
                case R.id.button4 :
                    tv.setText(String.valueOf(n1/n2));
                    break;
                
                }
            }
        };
        
        add.setOnClickListener(calculate);
        sub.setOnClickListener(calculate);
        mul.setOnClickListener(calculate);
        div.setOnClickListener(calculate);
        
        
        OnClickListener emptyBox = new OnClickListener() {
            
            public void onClick(View v) {
                switch(v.getId()){
                case R.id.editText1:
                    text1.setText("");
                    break;
                case R.id.editText2:
                    text2.setText("");
                    break;
                }
            }
        };
        text1.setOnClickListener(emptyBox);
        text2.setOnClickListener(emptyBox);

    }



}
