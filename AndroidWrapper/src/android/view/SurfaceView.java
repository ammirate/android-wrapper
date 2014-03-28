package android.view;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View.OnTouchListener;

/**
 * 
 * @author cesarano
 *
 */
public class SurfaceView extends JPanel
{
    private Context context;
    private Graphics graphicsFromContext;
    private Canvas canvas;
    private Surface surface;
    private Resources resources;
    private int id;
    private OnTouchListener touchListener;
    
    
    /**
     * 
     * @param context
     */
    public SurfaceView(Context context)
    {
        super();
        surface = new Surface();
        graphicsFromContext = context.getGraphics();
        this.context = context;
        canvas = new Canvas(graphicsFromContext);
        this.resources = context.getResources();
        createMouselistener();
    }


    /**
     * 
     * @param context
     * @param attrs
     */
    public SurfaceView(Context context, AttributeSet attrs)
    {
        super();
        surface = new Surface();
        graphicsFromContext = context.getGraphics();
        this.context = context;
        canvas = new Canvas(graphicsFromContext);
        this.resources = context.getResources();
        this.context = context;
        createMouselistener();
    }

    
    public SurfaceView(int id)
    {
        super();
        surface = new Surface();
        graphicsFromContext = context.getGraphics();
        canvas = new Canvas(graphicsFromContext);
        this.resources = context.getResources();
        this.id = id;
        createMouselistener();
    }


    /**
     * 
     * @return
     */
    public SurfaceHolder getHolder() 
    {
        return surfaceHolder;
    }

    /**
     * 
     */
    public void setFocusable(boolean focusable) 
    {
        super.setFocusable(focusable);
    }

    /**
     * 
     * @param hasWindowFocus
     */
    public void onWindowFocusChanged(boolean hasWindowFocus) 
    {
        super.setFocusable(hasWindowFocus);
    }


    
    
    public Resources getResources()
    {
        return resources;
    }


    /**
     * 
     */
    private SurfaceHolder surfaceHolder = new SurfaceHolder() {

        public boolean isCreating() {
            return true;
        }

        public void addCallback(Callback callback) {
        }

        public void removeCallback(Callback callback) {
        }

        public void setFixedSize(int width, int height) {
        }

        public void setSizeFromLayout() {
        }

        public void setFormat(int format) {
        }

        public void setType(int type) {
        }

        public void setKeepScreenOn(boolean screenOn) {
        }

        public Canvas lockCanvas() {
            return canvas;
        }

        public Canvas lockCanvas(Rect dirty) {
            return canvas;
        }

        public void unlockCanvasAndPost(Canvas canvas) {
        }

        public Surface getSurface() {
            return surface;
        }

        public Rect getSurfaceFrame() {
            return null;
        }
    };
    

    
    


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
                    touchListener.onTouch((SurfaceView)ev.getSource(), ev);
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((SurfaceView)ev.getSource(), ev);
                }
            }
        });  
        
        this.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((SurfaceView)ev.getSource(), ev);
                }
            }

            public void mouseDragged(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((SurfaceView)ev.getSource(), ev);
                }
            }
        });
    }
    
    public void onDraw(Canvas canvas)
    {
    }
}
