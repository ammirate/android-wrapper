package android.graphics.drawable;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import android.widget.WidgetProperties;



/**
 * 
 * @author Antonio Cesarano
 *
 */
public class AnimationDrawable extends JLabel
{
    /**
     * List of all the frames that compose the animation
     */
    private List<ImageIcon> frames = new ArrayList<ImageIcon>();

    /**
     * List of the duration of the frames
     */
    private List<Integer> duration = new ArrayList<Integer>();

    /**
     * flag used for playing once time the animation
     */
    private boolean oneShot = false;

    /**
     * Used for color
     */
    private int alpha = 0;

    /**
     * index of the current frame
     */
    private int frameIndex = 0;

    /**
     * Thread used for running the animation
     */
    Thread animations;

    /**
     * Flag to stop the animation
     */
    private static boolean stop = false;

    /**
     * Flag used for check if the animation is already running
     */
    private static boolean running = false;


    /**
     * Create an empty Animation Drawable
     */
    public AnimationDrawable()
    {
        super();
    }


    /**
     * Add a frame to the frame list of this animation
     * 
     * @param frame the frame to add 
     * @param reasonableDuration the duration of the frame
     */
    public void addFrame(BitmapDrawable frame, int reasonableDuration) 
    {
        Image scaledFrame = scaleImageToWidth(frame.getImage());        
        frame.setImage(scaledFrame);

        frames.add(frame);
        duration.add(reasonableDuration);
        if(getIcon() == null)
        {
            setIcon(frame);
        }
    }

    /**
     * Start the animation
     */
    public void start() 
    {
        stop = false;
        //check if the animation is already running
        if(!running)
        {        
            /*To start from the first frame.
             * If you want that your animation has to start from the last played 
             * frame, remove the following line.
             */
            frameIndex = 0;
            
            running = true;
            
            //new thread that will changes the frames
            Thread animations = new Thread()
            {
                public void run()
                {
                    int size = frames.size();
                    while(!stop)
                    {
                        setIcon(frames.get(frameIndex));                   
                        frameIndex = (frameIndex + 1) % size;
                        try 
                        {
                            Thread.sleep(duration.get(frameIndex));
                        } 
                        catch (InterruptedException e) 
                        {
                            e.printStackTrace();
                        }
                        /* if the current frame is the last and the flag oneShot
                         * is set to true, the animation will stop
                         */
                        if(oneShot && frameIndex==size-1)
                        {
                            stop = true;
                        }
                    }//end while

                }//end run()
            };
            //launch the thread execution
            animations.start();
        }//end if
    }


    /**
     * Set the oneShot flag for playing tha animation only once or continiously
     * @param b true if you want to play the animation only once, else false
     */
    public void setOneShot(boolean b) 
    {
        this.oneShot = b;
    }

    /**
     * Stop the animation
     */
    public void stop() 
    {
        stop = true;
        running = false;
    }


    private Image scaleImageToWidth(Image image)
    {
        int w = image.getWidth(null);
        int h = image.getHeight(null);
        // System.out.println("old dimension: " + w + " x " + h);

        int screenWidth = WidgetProperties.getACTIVITY_WIDTH();
        int screenHeight = WidgetProperties.getACTIVITY_HEIGHT();

        if(screenWidth < screenHeight) //portrait
        {
            int newWidth = screenWidth - 5;
            // proportion: h : w = newHeight : newWidth
            int newHeight = (h * newWidth) / w;
            Image newimg = image.getScaledInstance(newWidth, newHeight,java.awt.Image.SCALE_FAST);  
            return newimg;
        }
        else
        {
            int newHeight = screenHeight - 15;
            //proportion:  h : w = newHeight : newWidth
            int newWidth = (w * newHeight) / h;
            Image newimg = image.getScaledInstance(newWidth, newHeight,java.awt.Image.SCALE_FAST);  
            return newimg;
        }


    }



}
