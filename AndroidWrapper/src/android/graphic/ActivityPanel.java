package android.graphic;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.HierarchyBoundsListener;
import java.awt.event.HierarchyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.WidgetProperties;


/**
 * Extension of JPanel used for storage the elements to draw 
 * @author Antonio Cesarano
 *
 */
public class ActivityPanel extends JPanel 
{

    /**
     * List in which are stored the elements to draw
     */
    private List<View> componentsDrawed = new ArrayList<View>();

    /**
     * Canvas component necessary to call the View.onDraw method of the 
     * stored elements
     */
    private Canvas canvas;


    /**
     * Constructor. It recalls the super construcotr
     * @param layout is the layout manager for the panel
     */
    public ActivityPanel(FlowLayout layout) 
    {
        super(layout);
        //redirect onSizeChanged on all view elements
        createOnSizeChangeListener();
        
        //redirect mouseclick to all view elements
        createMouselistener();
        
    }


    /**
     * 
     */
    private void createOnSizeChangeListener() {
       
        
        this.addHierarchyBoundsListener(new HierarchyBoundsListener() 
        {
            @Override
            public void ancestorResized(HierarchyEvent e) 
            {
                int x = WidgetProperties.getACTIVITY_WIDTH();
                int y = WidgetProperties.getACTIVITY_HEIGHT();
                                
               // System.out.println(componentsDrawed.size());
                for(Object o : componentsDrawed)
                {  
                    if(o instanceof View)
                    {
                        View v = (View)o;
                        int activityHeight = WidgetProperties.getREAL_HEIGHT();
                        int activityWidth = WidgetProperties.getREAL_WIDTH();
                        v.onSizeChanged(activityWidth, activityHeight, x, y);
                        repaint();
                    }
                }
            }

            @Override
            public void ancestorMoved(HierarchyEvent e) 
            {

            }
            
            
        });
    }


    /**
     * Appends the specified component to the end of this container.
     * If the component is an element to draw, it will be stored in the
     * @componentsDrawed list.
     * 
     * @param comp the component to be added
     * @return the component argument
     */
    @Override
    public Component add(Component c)
    {
        if(c instanceof View)
        {
            View v = (View) c;
            componentsDrawed.add(v);
        }
        return super.add(c);
    }


    /**
     * Appends the specified component to the end of this container, using the 
     * constraint for the layout in use.
     * If the component is an element to draw, it will be stored in the
     * @componentsDrawed list.
     * 
     * @param comp the component to be added
     * @return the component argument
     */
    public void add(Component c, Object constraints)
    {
        if(c instanceof View)
        {
            View v = (View) c;
            componentsDrawed.add(v);
        }
        super.add(c,constraints);
    }



    /**
     * Calls the UI delegate's paint method
     */
    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        int toDrawElements = componentsDrawed.size();

        //check if there are dome components to draw
        if(toDrawElements > 0)
        {
            canvas = new Canvas(g);
            //call the onDraw method on each View
            for(Object v : componentsDrawed)
            {
                if(v instanceof View)
                {
                    ((View)v).onDraw(canvas);
                }
                
                else if(v instanceof JPanel)
                {
                    if(v instanceof ImageView)
                    {
                        ((ImageView)v).onDraw(canvas);
                    }
                    else if(v instanceof Surface)
                    {
                        System.out.println("do nothing");
                    }
                }
                
            }
            this.updateUI();
        }
    }

    /**
     * 
     */
    private void createMouselistener()
    {
        this.addMouseListener(new MouseAdapter() 
        {
            
            public void mousePressed(MouseEvent e)
            {
                sendEventToAll(e);
            }
            public void mouseReleased(MouseEvent e)
            {
                sendEventToAll(e);
            }
        });  
        
        this.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                sendEventToAll(e);
            }

            public void mouseDragged(MouseEvent e) {
                sendEventToAll(e);
            }
        });
    }
    
    
    /**
     * Send the mouseEvent to all the graphic components 
     * @param e
     */
    private void sendEventToAll(MouseEvent e) {
        MotionEvent ev = new MotionEvent(e);
        for(Object o : componentsDrawed)
        {
            if(o instanceof View)
            {
                View v = (View)o;
                v.onTouchEvent(ev);
            }
        }
    }


    /**
     * Returns the list of stored elements to draw
     */
    public List<View> getComponentsDrawed() 
    {
        return componentsDrawed;
    }


    /**
     * 
     * @return the Canvas component
     */
    public Canvas getCanvas() 
    {
        return canvas;
    }

    /**
     * Sets a new Canvas component
     * @param canvas the new Canvas component
     */
    public void setCanvas(Canvas canvas) 
    {
        this.canvas = canvas;
    }

    
    public void addViewToComponentsList(View v)
    {
        if(v != null)
        {
            for(Object o : componentsDrawed)
            {
                if(o.equals(v))
                {
                    return;
                }
            }
            componentsDrawed.add(v);
        }
        
        
    }


}
