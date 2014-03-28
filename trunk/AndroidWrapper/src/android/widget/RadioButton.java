package android.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JRadioButton;

import android.view.View;
import android.view.View.OnClickListener;
/**
 * 
 */

public class RadioButton extends JRadioButton implements SimpleComponent
{

    /**
     * Default listener for Android Button
     */
    private OnClickListener onClickListener;
    
    /**
     * Represents the id value in R.java class that identifies thie component
     */
    private int id;
    
    
    public RadioButton()
    {
        super();
    }
    
    
    public RadioButton(int id)
    {
        this.id = id;
    }
    
    
    /**
     * Add an OnClickListener object to the component and maps it into an 
     * ActionListener invokable in Java
     * @param onClickListener the listener that we wont on this Button
     */
    public void setOnClickListener(final OnClickListener onClickListener) {
        this.onClickListener = onClickListener;

        ActionListener l = new ActionListener() 
        {   
            public void actionPerformed(ActionEvent e) 
            {                 
               onClickListener.onClick(new View(e.getSource(), e.getID(), e.getActionCommand()));
            }
        };

        this.addActionListener(l);
    }
    
    

    
    /**
     * Returns the id of this component that corresponds to the id written 
     * in the R.java file that refers this button
     * @return the if of this button
     */
    public int getId() {
        return id;
    }
    
    
    public String toString()
    {
        return "android.widget.RadioButton: " +
                "[id: " +this.getId() + " ]" +
                "[Text: "+this.getText()+"]-"+
                "[checked: " + this.isSelected() + "]";
    }

}
