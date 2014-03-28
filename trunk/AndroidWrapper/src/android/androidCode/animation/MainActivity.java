package android.androidCode.animation;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener {

    ImageView img;

    Button strtbtn,stpbtn;

    AnimationDrawable mAnimation;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = (ImageView) findViewById(R.id.iv);
        
        strtbtn = (Button) findViewById(R.id.strtbtn);
        strtbtn.setOnClickListener(this);
        stpbtn = (Button) findViewById(R.id.stpbtn);
        stpbtn.setOnClickListener(this);

        BitmapDrawable frame0 = (BitmapDrawable)getResources().getDrawable(R.drawable.f0);
        BitmapDrawable frame1 = (BitmapDrawable)getResources().getDrawable(R.drawable.f1);
        BitmapDrawable frame2 = (BitmapDrawable)getResources().getDrawable(R.drawable.f2);
        BitmapDrawable frame3 = (BitmapDrawable)getResources().getDrawable(R.drawable.f3);
        BitmapDrawable frame4 = (BitmapDrawable)getResources().getDrawable(R.drawable.f4);
        BitmapDrawable frame5 = (BitmapDrawable)getResources().getDrawable(R.drawable.f5);
        BitmapDrawable frame6 = (BitmapDrawable)getResources().getDrawable(R.drawable.f6);
        BitmapDrawable frame7 = (BitmapDrawable)getResources().getDrawable(R.drawable.f7);
        BitmapDrawable frame8 = (BitmapDrawable)getResources().getDrawable(R.drawable.f8);
        BitmapDrawable frame9 = (BitmapDrawable)getResources().getDrawable(R.drawable.f9);
        BitmapDrawable frame10 = (BitmapDrawable)getResources().getDrawable(R.drawable.f10);
        BitmapDrawable frame11 = (BitmapDrawable)getResources().getDrawable(R.drawable.f11);
        BitmapDrawable frame12 = (BitmapDrawable)getResources().getDrawable(R.drawable.f12);
        BitmapDrawable frame13 = (BitmapDrawable)getResources().getDrawable(R.drawable.f13);
        

        int reasonableDuration = 50;
        mAnimation = new AnimationDrawable();
        mAnimation.addFrame(frame0, reasonableDuration);
        mAnimation.addFrame(frame1, reasonableDuration);
        mAnimation.addFrame(frame2, reasonableDuration);
        mAnimation.addFrame(frame3, reasonableDuration);
        mAnimation.addFrame(frame4, reasonableDuration);
        mAnimation.addFrame(frame5, reasonableDuration);
        mAnimation.addFrame(frame6, reasonableDuration);
        mAnimation.addFrame(frame7, reasonableDuration);
        mAnimation.addFrame(frame8, reasonableDuration);
        mAnimation.addFrame(frame9, reasonableDuration);
        mAnimation.addFrame(frame10, reasonableDuration);
        mAnimation.addFrame(frame11, reasonableDuration);       
        mAnimation.addFrame(frame12, reasonableDuration);
        mAnimation.addFrame(frame13, reasonableDuration);
        

        img.setBackgroundDrawable(mAnimation);  
        

    }

    @Override
    public void onClick(View v) {

      if(v.getId()== R.id.strtbtn){

          mAnimation.start();
          mAnimation.setOneShot(false);
      }
      else
        mAnimation.stop();
    }  
}