package android.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

import android.app.GraphicDrawer;
import android.content.Context;


/**
 * This class allows to Android applications to show a text message for a short time
 * in a little frame.
 * 
 * @author antonio
 *
 * Class added for the project of Software Engineering 2 
 */ 
public  class Toast  extends JFrame{

	/**
	 * 
	 */
	/**
	 * Time constants used to displaying Toast
	 */
	public static final int LENGTH_SHORT = 2000;
	public static final int LENGTH_LONG = 5000;
	
	private static final int SIZE_X = 160;
	private static final int SIZE_Y = 40;
	
	
	
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
    	
    	Point p = calculateToastOrigin(); 
    	mytoast.setLocation( (int)(p.getX()),  (int)(p.getY()) );
    	mytoast.setVisible(true);
    	mytoast.setSize(SIZE_X, SIZE_Y);
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
    
   
   /**
    * This method calculate the origin point (x,y) in which the toast will appear
    * 
    * @return an array of 2 elements, made by x and y coordinates.
    */
   private Point calculateToastOrigin(){
	   	   
	   GraphicDrawer gd = GraphicDrawer.getInstance();
	   Point p = gd.getCurrentFrame().getLocation();
	   int x = (int) (p.getX() - SIZE_X/2 + (WidgetProperties.getFRAME_WIDTH()/2));
	   int y = (int) (p.getY() - SIZE_Y/2 + (WidgetProperties.getFRAME_HEIGHT() * 4/5));
	   
	   return new Point(x, y);
   }
   

}
