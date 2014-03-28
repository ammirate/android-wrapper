package android.androidCode.aicas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class JamaicavmActivity extends Activity {
    
    private  Button linkbutton;
    private TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamaicavm);
        // Show the Up button in the action bar.
        
        
        linkbutton = (Button) findViewById(R.id.button1);
        link = (TextView) findViewById(R.id.lblLink);
        
        linkbutton.setOnClickListener(new OnClickListener() {
            
            public void onClick(View arg0) {
                link.setText("www.aicas.com/jamaica.html");
            }
        });
    }


}
