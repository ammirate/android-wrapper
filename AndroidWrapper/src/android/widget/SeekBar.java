package android.widget;

import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import android.app.Activity;

/**
 * 
 * Android widget that allows to chose a value from a range using a slider
 * @author Antonio Cesarano
 *
 */
public class SeekBar extends JSlider implements SimpleComponent{
    
    private int id;
    
    
    public SeekBar(int id)
    {
        super();
        this.id = id;
    }

    /**
     * Inner class for event management
     * @onProgressChanged is the Android event for stateChange() of a JSlider
     * @onStartTrackingTouch is called when the user start to move the slider
     * @onStopTrackingToucj is called when the user stop to move the slider
     * 
     */
    public interface OnSeekBarChangeListener {

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
        
        public void onStartTrackingTouch(SeekBar seekBar);
        
        public void onStopTrackingTouch(SeekBar seekBar);
    }

    /**
     * Object from which start the event manager
     */
    private OnSeekBarChangeListener onSeekBarChangeListener;
    
    
    
    /**
     * Returns the actual value of the slider
     * @return the value of the slider
     */
    public int getProgress() {
        return this.getValue();
    }   

    
    /**
     * Sets a new listener for the event onSeekBarChange
     * @param listener a OnSeekBarChangeListener listener
     */
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        
        final int currentValue = this.getValue();
        this.onSeekBarChangeListener = listener;
        
        ChangeListener l = new ChangeListener() {
            
            public void stateChanged(ChangeEvent e) {
                onSeekBarChangeListener.onProgressChanged((SeekBar) e.getSource(),currentValue , true);
            }
        };
        
        this.addChangeListener(l);
    }


    public int getId() 
    {
        return id;
    }
    
    public int getMax()
    {
        return super.getMaximum();
    }
    
    

}
