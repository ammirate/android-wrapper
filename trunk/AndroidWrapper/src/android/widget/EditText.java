package android.widget;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

import android.view.View;
import android.view.View.OnClickListener;

public class EditText extends JTextField implements SimpleComponent
{
 
    /**
     * Represents the id value in R.java class that identifies thie component
     */
    private int id;
    private OnClickListener onclickListener;

    
    

    public EditText()
    {
        super();
        this.setColumns(WidgetProperties.getNUM_COLUMNS());
    }
    
    public EditText(int id)
    {
        super();
        this.id = id;
        this.setColumns(WidgetProperties.getNUM_COLUMNS());
    }
    

    public int getId() 
    {
        return id;
    }

    public void setId(int id) 
    {
        this.id = id;
    }
    
    
    /**
     * Add an OnClickListener object to the component and maps it into an 
     * ActionListener invokable in Java
     * @param onClickListener the listener that we wont on this Button
     */
    public void setOnClickListener(final OnClickListener onClickListener)
    {
        this.onclickListener = onClickListener;

        MouseListener l = new MouseListener() 
        {
            @Override
            public void mouseClicked(MouseEvent e) {
                onclickListener.onClick(new View(e.getSource(), e.getID(), null));
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {}

            @Override
            public void mouseExited(MouseEvent e) 
            {}

            @Override
            public void mousePressed(MouseEvent e) 
            {}

            @Override
            public void mouseReleased(MouseEvent e) 
            {}   
        };

        this.addMouseListener(l);
    }

    public OnClickListener getOnclickListener() 
    {
        return onclickListener;
    }

    public void setOnclickListener(OnClickListener onclickListener) 
    {
        this.onclickListener = onclickListener;
    }
    
}
