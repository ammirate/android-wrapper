package android.androidCode.ball;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new BallBounce(this));

    }
}


class BallBounce extends View {
    int screenW;
    int screenH;
    int X;
    int Y;
    int initialY ;
    int ballW;
    int ballH;
    int angle;
    float dY;
    float acc;
    Bitmap ball, bgr;

    private int ballX;
    private int ballY;
    private boolean ballFingerMove = false;

    public BallBounce(Context context) {
        super(context);
        ball = BitmapFactory.decodeResource(getResources(),R.drawable.football); //load a ball image
        bgr = BitmapFactory.decodeResource(getResources(),R.drawable.sky_bgr); //load a background
        ballW = ball.getWidth();
        ballH = ball.getHeight();
        acc = 0.2f; //acceleration
        dY = 0; //vertical speed
        initialY = 100; //Initial vertical position.
        angle = 0; //Start value for rotation angle.


    }

    @Override
    public void onSizeChanged (int w, int h, int oldw, int oldh) 
    {
        super.onSizeChanged(w, h, oldw, oldh);
        screenW = w;
        screenH = h;
        bgr = Bitmap.createScaledBitmap(bgr, w, h, true); //Resize background to fit the screen.
        X = (int) (screenW /2) - (ballW / 2) ; //Centre ball into the centre of the screen.
        Y = initialY;
    }

    
    
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //Draw background.
        canvas.drawBitmap(bgr, 0, 0, null);


        if(!ballFingerMove)
        {
            //Compute roughly ball speed and location.
            Y+= (int) dY; //Increase or decrease vertical position.
            if (Y > (screenH - ballH)) {
                dY=(-1)*dY; //Reverse speed when bottom hit.
            }
            dY+= acc; //Increase or decrease speed.
            
            if (angle++ >360)
                angle =0;
        }
        //Increase rotating angle.


        //Draw ball
        canvas.save(); //Save the position of the canvas.
        canvas.rotate(angle, X + (ballW / 2), Y + (ballH / 2)); //Rotate the canvas.
        canvas.drawBitmap(ball, X, Y, null); //Draw the ball on the rotated canvas.
        canvas.restore(); //Rotate the canvas back so that it looks like ball has rotated.


        int a = X + (ballW / 2);
        int b=Y + (ballH / 2);

        //Log.d("coordinate", a + " , " + b );


        //Call the next frame.
        invalidate();
    }



    @Override
    public synchronized boolean onTouchEvent(MotionEvent ev) 
    {

        switch (ev.getAction()) 
        {

        case MotionEvent.ACTION_UP:
        {
            ballFingerMove = false;
            dY = 0;
            break;
        }

        case MotionEvent.ACTION_MOVE: 
        {
            X = (int) ev.getX() - ballW/2;
            Y = (int) ev.getY() - ballH/2;

            break;
        }
        case MotionEvent.ACTION_DOWN: 
        {
            X = (int) ev.getX() - ballW/2;
            Y = (int) ev.getY() - ballH/2;

            ballFingerMove  = true;
            break;
        }
        }
        return true;
    }






}