package android.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Rectangle;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

public  class Toast  extends JFrame{

	/**
	 * 
	 */
	/**
	 * Time constants used to displaying Toast
	 */
	public static final int LENGTH_SHORT = 2000;
	public static final int LENGTH_LONG = 5000;
	
	private Context context;
	
	private static JLabel textLabel;
	private static JFrame mytoast;
	private static int mDuration;
	
	
    /**
     * Construct an empty Toast object.
     */
    public Toast(Context context){
    	super();
    	this.context = context;
    	
    }
    
    
    
    /**
     * Make a standard toast that just contains a text view.
     *
     * @param context  The context to use. 
     * @param text     The text to show.  Can be formatted text.
     * @param duration How long to display the message.  Either {@link #LENGTH_SHORT} or
     *                 {@link #LENGTH_LONG}
     *
     */
    public static Toast makeText(Context context, CharSequence text, int duration) {
    	textLabel = new JLabel("   " + text.toString());
    	textLabel.setForeground(Color.white);
    	mDuration = duration;
    	return new Toast(context);
    }
    
    
    /**
     * Show the view for the specified duration.
     */
    public void show() {
    	mytoast = new JFrame();
    	mytoast.setUndecorated(true);
    	mytoast.getContentPane().setBackground(Color.black);
    	mytoast.setLayout(new BorderLayout());
    	
    	int xLoc = (int) (WidgetProperties.getX_LOCATION() + WidgetProperties.getACTIVITY_WIDTH() * 0.25);
    	int yLoc = (int) (WidgetProperties.getY_LOCATION() + WidgetProperties.getACTIVITY_HEIGHT() * 0.70);
    	mytoast.setLocation(xLoc, yLoc );
    	mytoast.setVisible(true);
    	mytoast.setSize(150, 40);
    	mytoast.getContentPane().add(textLabel, BorderLayout.CENTER);
    	
    	KillerThread kt = new KillerThread(mytoast, mDuration);
    	kt.start();
    }
    


    
    /**
     * Set how long to show the view for.
     * @see #LENGTH_SHORT
     * @see #LENGTH_LONG
     */
    public void setDuration(int duration) {
    	this.mDuration = duration;
    }
    
    
    /**
     * Return the duration.
     * @see #setDuration
     */
    public int getDuration() {
        return mDuration;
    }
    
    
    
   class KillerThread extends Thread{
	   
	   private JFrame frame;
	   private int time;
	   
	   public KillerThread(JFrame f, int t){
		   super();
		   this.frame = f;
		   this.time = t;
	   }
	   
	   public void run(){
		   try {
			this.sleep(time);
			frame.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		   
	   }
   }
    
   

}
