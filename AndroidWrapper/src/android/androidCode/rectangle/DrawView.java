package android.androidCode.rectangle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View 
{
    public int x = 30 ,y = 30;
    Paint paint = new Paint();

    public DrawView(Context context) 
    {
        super(context);            
    }

    public  int getX() {
        return x;
    }

    public  void setX(int x) {
        this.x = x;
    }

    public  int getY() {
        return y;
    }

    public  void setY(int y) {
        this.y = y;
    }
    

    @Override
    public void onDraw(Canvas canvas) 
    {       
        paint.setColor(Color.BLACK);       
        paint.setStrokeWidth(3);        
        canvas.drawRect(x, y, 80, 80, paint);        
        paint.setStrokeWidth(0);       
        paint.setColor(Color.CYAN);        
        canvas.drawRect(x+2, y+2, 77, 77, paint );       
        paint.setColor(Color.YELLOW);        
        canvas.drawRect(x+2, y+2, 77, 60, paint );
    }

}