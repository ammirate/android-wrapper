package android.androidCode.coordinate;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

public class MainActivity extends Activity{

    private  TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = new TextView(getApplicationContext());
        tv.setText("Coordinate: ");
        

        setContentView(tv);
        
        tv.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View view, MotionEvent ev)
            {
                tv.setText("(" + ev.getX() + "," +  ev.getY() + ")");
                return true;
            }
        });

    }
}
