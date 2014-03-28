package android.androidCode.surfaceview;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainActivity extends Activity {

    MySurfaceView mySurfaceView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mySurfaceView = new MySurfaceView(this);
        setContentView(mySurfaceView);
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        mySurfaceView.onResumeMySurfaceView();
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        mySurfaceView.onPauseMySurfaceView();
    }
//------------------------------------------------------------------------------
    
    class MySurfaceView extends SurfaceView implements Runnable{

        Thread thread = null;
        SurfaceHolder surfaceHolder;
        volatile boolean running = false;

        private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Random random;

        public MySurfaceView(Context context) {
            super(context);
            surfaceHolder = getHolder();
            random = new Random();
        }

        public void onResumeMySurfaceView(){
            running = true;
            thread = new Thread(this);
            thread.start();
        }

        public void onPauseMySurfaceView(){
            boolean retry = true;
            running = false;
            while(retry){
                try {
                    thread.join();
                    retry = false;
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        @Override
        public void run() {
            // TODO Auto-generated method stub
            while(running){
                if(surfaceHolder.getSurface().isValid()){
                    Canvas canvas = surfaceHolder.lockCanvas();
                    //... actual drawing on canvas

                    paint.setStrokeWidth(3);

                    int w = canvas.getWidth();
                    int h = canvas.getHeight();                   
                    int x = random.nextInt(w-1);
                    int y = random.nextInt(h);                           
                    int r = random.nextInt(255);
                    int g = random.nextInt(255);
                    int b = random.nextInt(255);
                    paint.setColor(0xff000000 + (r << 16) + (g << 8) + b);
                    canvas.drawPoint(x, y, paint);

                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }

    }
}