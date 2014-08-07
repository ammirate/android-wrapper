package android.widget;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.sql.Time;
import java.util.Calendar;

import javax.swing.JComponent;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;


/**
 * 
 * @author Antonio Cesarano
 *
 * IS2 project - MAEESTRO 2013/2014

 */
public class AnalogClock extends JComponent {

		private Context context;
	

	    
	    //============================================================ constants
	    private static final double TWO_PI   = 2.0 * Math.PI;
	    
	    private static final int    UPDATE_INTERVAL = 100;  // Millisecs
	    
	    private static int WIDTH = WidgetProperties.getACTIVITY_WIDTH() / 3;
	    private static int HEIGHT = WIDTH;	    
	    //=============================================================== fields
	    
	    
	    private Calendar _now = Calendar.getInstance();  // Current time.
	    
	    private int _diameter;                 // Height and width of clock face
	    private int _centerX;                  // x coord of middle of clock
	    private int _centerY;                  // y coord of middle of clock
	    private BufferedImage _clockImage;     // Saved image of the clock face.
	    
	    private javax.swing.Timer _timer;      // Fires to update clock.
	    
	    
	    
	    
	    //==================================================== Clock constructor
	    
	    
	    
	    public AnalogClock(Context context) {
	        setPreferredSize(new Dimension(WIDTH,HEIGHT));
	        
	        _timer = new javax.swing.Timer(UPDATE_INTERVAL, new ActionListener() {
	            public void actionPerformed(ActionEvent e) {
	                updateTime();
	                repaint();
	            }
	        });
	        this.context = context;
	        this.start();
	    }
	    
	    
	    
	    //================================================================ start
	    /** Start the timer. */
	    public void start() {
	        _timer.start(); 
	    }
	    
	    //================================================================= stop
	    /** Stop the timer. */
	    public void stop() {
	        _timer.stop(); 
	    }
	    
	    //=========================================================== updateTime
	    private void updateTime() {
	        //... Avoid creating new objects.
	        _now.setTimeInMillis(System.currentTimeMillis());
	    }
	    
	    //======================================================= paintComponent
	    @Override public void paintComponent(Graphics g) {
	        Graphics2D g2 = (Graphics2D)g;
	        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                            RenderingHints.VALUE_ANTIALIAS_ON);
	        
	        //... The panel may have been resized, get current dimensions
	        int w = getWidth();
	        int h = getHeight();
	        _diameter = ((w < h) ? w : h);
	        _centerX = _diameter / 2;
	        _centerY = _diameter / 2;
	        
	        //... Create the clock face background image if this is the first time,
	        //    or if the size of the panel has changed
	        if (_clockImage == null
	                || _clockImage.getWidth() != w
	                || _clockImage.getHeight() != h) {
	            _clockImage = (BufferedImage)(this.createImage(w, h));
	            
	            //... Get a graphics context from this image
	            Graphics2D g2a = _clockImage.createGraphics();
	            g2a.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	                                 RenderingHints.VALUE_ANTIALIAS_ON);
	            drawClockFace(g2a);
	        }
	        
	        //... Draw the clock face from the precomputed image
	        g2.drawImage(_clockImage, null, 0, 0);
	        
	        //... Draw the clock hands dynamically each time.
	        drawClockHands(g2);
	    }
	    
	    //====================================== convenience method drawClockHands
	    private void drawClockHands(Graphics2D g2) {
	        //... Get the various time elements from the Calendar object.
	        int hours   = _now.get(Calendar.HOUR);
	        int minutes = _now.get(Calendar.MINUTE);
	        int seconds = _now.get(Calendar.SECOND);
	        int millis  = _now.get(Calendar.MILLISECOND);
	        
	        //... second hand
	        int handMin = _diameter / 8;    // Second hand doesn't start in middle.
	        int handMax = _diameter / 2;    // Second hand extends to outer rim.
	        double fseconds = (seconds + (double)millis/1000) / 60.0;
	        drawRadius(g2, fseconds, 0, handMax);
	        
	        //... minute hand
	        handMin = 0;                    // Minute hand starts in middle.
	        handMax = _diameter / 3; 
	        double fminutes = (minutes + fseconds) / 60.0;
	        drawRadius(g2, fminutes, 0, handMax);
	        
	        //... hour hand
	        handMin = 0;
	        handMax = _diameter / 4;
	        drawRadius(g2, (hours + fminutes) / 12.0, 0, handMax);
	    }
	    
	    //======================================= convenience method drawClockFace
	    private void drawClockFace(Graphics2D g2) {
	        // ... Draw the clock face.  Probably into a buffer.        
	        g2.setColor(Color.WHITE);
	        g2.fillOval(0, 0, _diameter, _diameter);
	        g2.setColor(Color.BLACK);
	        g2.drawOval(0, 0, _diameter, _diameter);
	        
	        int radius = _diameter / 2;
	        
	        //... Draw the tick marks around the circumference.
	        for (int sec = 0; sec < 60; sec++) {
	            int tickStart;
	            if (sec%5 == 0) {
	                tickStart = radius - 10;  // Draw long tick mark every 5.
	            } else {
	                tickStart = radius - 5;   // Short tick mark.
	            }
	            drawRadius(g2, sec / 60.0, tickStart , radius);
	        }
	    }
	    
	    //==================================== convenience method drawRadius
	    // This draw lines along a radius from the clock face center.
	    // By changing the parameters, it can be used to draw tick marks,
	    // as well as the hands.
	    private void drawRadius(Graphics2D g2, double percent,
	                            int minRadius, int maxRadius) {
	        //... percent parameter is the fraction (0.0 - 1.0) of the way
	        //    clockwise from 12.   Because the Graphics2D methods use radians
	        //    counterclockwise from 3, a little conversion is necessary.
	        //    It took a little experimentation to get this right.
	        double radians = (0.5 - percent) * TWO_PI;
	        double sine   = Math.sin(radians);
	        double cosine = Math.cos(radians);
	        
	        int dxmin = _centerX + (int)(minRadius * sine);
	        int dymin = _centerY + (int)(minRadius * cosine);
	        
	        int dxmax = _centerX + (int)(maxRadius * sine);
	        int dymax = _centerY + (int)(maxRadius * cosine);
	        g2.drawLine(dxmin, dymin, dxmax, dymax);
	    }
	    
	    
	    
	    
	    
	    //################### Android Interface ############################
	    
	    AttributeSet attrs;
	    int defStyle;
	    
	    
	    public AnalogClock(Context context, AttributeSet attrs) {
	    	this(context);
	    	this.attrs = attrs;
	    }

	    public AnalogClock(Context context, AttributeSet attrs, int defStyle) {
	    	this(context);
	    	this.attrs = attrs;
	    	this.defStyle = defStyle;
	    }

	    
	    protected void onAttachedToWindow() {

	    }

	    
	    protected void onDetachedFromWindow() {

	    }

	    
	    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

	    }

	    
	    public void onSizeChanged(int w, int h, int oldw, int oldh) {
	    	super.onSizeChanged(w, h, oldw, oldh);
	    }

	    
	    public void onDraw(Canvas canvas) {
	    	this.paintComponent(getGraphics());
	    }

	    private void onTimeChanged() {

	    }



	    private void updateContentDescription(Time time) {

	    }


}
