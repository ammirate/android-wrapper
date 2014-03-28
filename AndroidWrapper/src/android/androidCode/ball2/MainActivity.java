package android.androidCode.ball2;

import android.app.Activity;
import android.os.Bundle;


public class MainActivity extends Activity {

    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);        
        AnimatedView av = new AnimatedView(this,null);
        setContentView(av);

   }

}