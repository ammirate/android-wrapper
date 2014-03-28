package android.androidCode.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    
    public final static String EXTRA_MESSAGE = "Extra_Message";

    private EditText text;
    private Button b;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        text = (EditText) findViewById(R.id.edit_message);
        text.setText("Write here");
        b = (Button) findViewById(R.id.button1);
        System.out.println(b.getText());
        
        OnClickListener l = new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DisplayMessageActivity.class);
                String message = text.getText().toString();
                intent.putExtra(EXTRA_MESSAGE, message);
                startActivity(intent);
            }
        };
        
       b.setOnClickListener(l);
    }



}
