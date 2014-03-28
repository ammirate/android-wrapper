package android.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.text.BadLocationException;
import javax.swing.text.Position;
import javax.swing.text.Segment;
import javax.swing.undo.UndoableEdit;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.AndroidWidget;
import android.widget.WidgetProperties;


/**
 * Class used for the event management. It contains the informations
 * about the event triggering. It is used by Android listeners method, so it 
 * allows to event management system to have all informations about the widgets 
 * that trigger events
 * @author Antonio Cesarano
 *
 */
public class View extends Container
{
    /**
     * Interface definition for a callback to be invoked when a touch event is
     * dispatched to this view. The callback will be invoked before the touch
     * event is given to the view.
     */
    public interface OnTouchListener 
    {
        /**
         * Called when a touch event is dispatched to a view. This allows listeners to
         * get a chance to respond before the target view.
         *
         * @param v The view the touch event has been dispatched to.
         * @param event The MotionEvent object containing full information about
         *        the event.
         * @return True if the listener has consumed the event, false otherwise.
         */
        boolean onTouch(View v, MotionEvent event);
    }


    public static final String VISIBLE = null;

    private Context context;

    private Canvas canvas;

    private int mID;

    private OnTouchListener touchListener;


    /**
     * Empty Constructor
     */
    public View()
    {
        context = null;
        createMouselistener();
    }

    public void getFive() { System.out.println(5); }
 
    private ActionEvent actionEvent;

    /**
     * Constructor that initialize a new View. It will contains all information 
     * about a triggered event
     * @param source id the widget that triggers the event
     * @param id is the event id (unused, it's substituted with the source's id)
     * @param command the event command
     */
    public View(Object source, int id, String command) 
    {
        actionEvent = new ActionEvent(source, ((AndroidWidget)source).getId() , command); 
        mID = id;
    }


    public View(Context context) 
    {
        this.context = context;


    }


    public View(Context context, AttributeSet attrs) 
    {
        this.context = context;

    }


    /**
     * Listener interface used by Button. 
     * It's the same of ActionListener
     */
    public interface OnClickListener
    {   
        /**
         * Method to implement for the event onClick
         * It's the same of anctionPerformed
         * @param v the View that represents the event
         */
        public void onClick(View v);
    }

    /**
     * Returns the event id
     * @return the event id
     */
    public int getId() 
    {
        return actionEvent.getID();
    }



    public View findViewById(int fieldId) 
    {

        if(fieldId == mID)
        {
            return this;
        }
        else 
        {
            return null;
        }
    }

    public void setBackgroundColor(Color color)
    {
        this.setBackground(color);
    }


    /**
     * Implement this to do your drawing.
     *
     * @param canvas the canvas on which the background will be drawn
     */
    public void onDraw(Canvas canvas) 
    {
        this.canvas = canvas;        
    }

    /**
     * 
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    public void onSizeChanged(int w, int h, int oldw, int oldh) 
    {
        // TODO Auto-generated method stub
    }

    /**
     * 
     * @return
     */
    public Resources getResources()
    {
        return context.getResources();
    }


    /**
     * 
     */
    public void invalidate() 
    {
        super.invalidate();
    }


    //#########################################################################

    public Position createPosition(int offset) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }


    public void getChars(int where, int len, Segment txt)
            throws BadLocationException {
        // TODO Auto-generated method stub

    }


    public String getString(int where, int len) throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }


    public UndoableEdit insertString(int where, String str)
            throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
    }


    public int length() {
        // TODO Auto-generated method stub
        return 0;
    }


    public UndoableEdit remove(int where, int nitems)
            throws BadLocationException {
        // TODO Auto-generated method stub
        return null;
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
                    touchListener.onTouch((View)ev.getSource(), ev);
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((View)ev.getSource(), ev);
                }
            }
        });  
        
        
        this.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((View)ev.getSource(), ev);
                }
            }

            public void mouseDragged(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((View)ev.getSource(), ev);
                }
            }
        });
    }



}



