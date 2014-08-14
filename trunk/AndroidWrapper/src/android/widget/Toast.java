package android.widget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import android.app.GraphicDrawer;
import android.content.Context;


/**
 * This class allows to Android applications to show a text message for a short time
 * in a little frame.
 * 
 * @author Antonio Cesarano
 *
 * IS2 project - MAEESTRO 2013/2014
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
	
	private static final int CHARS_IN_A_LINE = 20;

	
	private Context context;
	
	private static JTextArea textLabel;
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
    	textLabel = new JTextArea("  " + text.toString());
    	textLabel.setForeground(Color.white);
    	textLabel.setBackground(Color.BLACK);
    	textLabel.setEditable(false);
    	textLabel.setAlignmentX(JTextArea.CENTER_ALIGNMENT);
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
    	textLabel.setText(getFormattedString(textLabel.getText()));
    	mytoast.getContentPane().add(textLabel, BorderLayout.CENTER);

    	
    	KillerThread kt = new KillerThread(mytoast, mDuration);
    	kt.start();
    }
    

    
    
    private String getFormattedString(String s) {
		char[] mytext = s.toCharArray();
		String toReturn = "";
		int rows = 1;
		
		for(int i=0; i< mytext.length; i++){	
//			System.out.println(mytext[i] + " ... and the index i = " + i );
			if(mytext[i] == ' ' && 
					(i>=(CHARS_IN_A_LINE*rows) - (2*rows) && 
						i< (CHARS_IN_A_LINE*rows) + (2*rows))){
				toReturn += "\n";
			}
			toReturn += mytext[i];
		}
//		System.out.println("TOAST... testo iniziale: " + s + "\ntesto calcolato: "+ toReturn);
		return toReturn;
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
