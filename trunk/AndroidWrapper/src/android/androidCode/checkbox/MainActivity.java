package android.androidCode.checkbox;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CheckBox c1 = (CheckBox) findViewById(R.id.checkBox1);
        CheckBox c2 = (CheckBox) findViewById(R.id.checkBox2);
        
        c1.setSelected(true);
        c1.setText("Yes");
        c2.setText("No");
    }



}
