package android.widget;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class ImageView extends JPanel
{
    private Drawable background;
    private int id;
    Context context;
    int x = WidgetProperties.getACTIVITY_WIDTH();
    int y = WidgetProperties.getACTIVITY_HEIGHT();
    private OnTouchListener touchListener;

    public ImageView(int id)
    {
        super();
        this.id = id;
        this.setPreferredSize(new Dimension(x,y));
        this.setOpaque(false);
        createMouselistener();
    }

    public ImageView(Context context, AttributeSet attrs) 
    {
        super();
        this.context = context;
        this.setPreferredSize(new Dimension(x,y));
        this.setOpaque(false);
        createMouselistener();
    }

    public void setBackground(AnimationDrawable background) 
    {
        if(background != null){
            super.add(background);
        }
    }


    public void setBackground(Drawable background) 
    {
        if(background != null){
            JLabel l = new JLabel();
            l.setIcon(background);
            super.add(l);
        }
    }

    /**
     * Deprecated but used
     * @param background
     */
    public void setBackgroundDrawable(AnimationDrawable background) 
    {
        if(background != null){
            super.add(background);
        }
    }

    /**
     * Deprecated but used
     * @param background
     */
    public void setBackgroundDrawable(Drawable background) 
    {
        if(background != null){
            JLabel l = new JLabel();
            l.setIcon(background);
            super.add(l);
        }
    }

    public void onDraw(Canvas c)
    {

    }
    
    
    

    /**
     * 
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) 
    {
        return false;
    }
    
    
    public void setOnTouchListener(OnTouchListener touchListener) 
    {
        this.touchListener = touchListener;
        createMouselistener();

    }
    

    private void createMouselistener()
    {
        this.addMouseListener(new MouseAdapter() 
        {
            
            public void mousePressed(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((ImageView)ev.getSource(), ev);
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((ImageView)ev.getSource(), ev);
                }
            }
        });  
        
        this.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((ImageView)ev.getSource(), ev);
                }
            }

            public void mouseDragged(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((ImageView)ev.getSource(), ev);
                }
            }
        });
    }

}
