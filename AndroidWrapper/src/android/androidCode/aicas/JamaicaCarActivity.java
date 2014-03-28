package android.androidCode.aicas;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class JamaicaCarActivity extends Activity 
{

    
    private TextView link;
    private RadioButton page;
    private RadioButton pdf;
    
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jamaica_car);
        
        link = (TextView) findViewById(R.id.lblLinkCar);
        page = (RadioButton) findViewById(R.id.radio0);
        pdf = (RadioButton) findViewById(R.id.radio1);
                
        OnClickListener listener = new OnClickListener() 
        {
            
            public void onClick(View v) {
                if(v.getId() == R.id.radio0)
                {
                    link.setText("http://www.aicas.com/jamaicacar.html");
                }
                else
                {
                    link.setText("http://www.aicas.com/info/Infoblatt_JamaicaCAR_2011_en_web.pdf");
                }
            }
        };
        page.setOnClickListener(listener);
        pdf.setOnClickListener(listener);
        
        link.setText("http://www.aicas.com/jamaicacar.html");
        
    }



}
