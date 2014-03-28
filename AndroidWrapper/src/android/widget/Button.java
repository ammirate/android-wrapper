package android.widget;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InterfaceAddress;

import javax.swing.JButton;

import android.view.View;
import android.view.View.OnClickListener;

/**
 *
 * Extends JButton of swing libraries and permits to map the OnClickListener
 * into an ActionListener
 * @author Antonio Cesarano
 */
public class Button extends JButton implements SimpleComponent
{   
    /**
     * Default listener for Android Button
     */
    private OnClickListener onClickListener;
    
    /**
     * Represents the id value in R.java class that identifies thie component
     */
    private int id;
    
    
    /**
     * Empty constructor. It call the JButton constructor
     */
    public Button()
    {
        super();
    }
    
    /**
     * Construcotr that takes the Button id value written in R.java file
     * @param id is the Button id
     */
    public Button(int id)
    {
        super();
        this.id = id;
    }
    

    /**
     * Add an OnClickListener object to the component and maps it into an 
     * ActionListener invokable in Java
     * @param onClickListener the listener that we wont on this Button
     */
    public void setOnClickListener(final OnClickListener onClickListener)
    {
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
     * Returns the onClickListener handler of this component
     * @return onClickListener associated to the button
     */
    public OnClickListener getOnclickListener() 
    {
        return onClickListener;
    }

    


    
    /**
     * Returns the id of this component that corresponds to the id written 
     * in the R.java file that refers this button
     * @return the if of this button
     */
    public int getId() 
    {
        return id;
    }

    
    /**
     * Sets the id of the component
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }
    
}
