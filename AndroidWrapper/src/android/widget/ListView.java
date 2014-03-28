package android.widget;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JViewport;
import javax.swing.SwingConstants;

import android.widget.AdapterView.OnItemClickListener;


/**
 * 
 * @author cesarano
 *
 */
public class ListView extends JScrollPane implements SimpleComponent{

    private List elements;
    private HashMap<TextView,Integer> buttons;
    private OnItemClickListener listener;
    private JPanel panel;
    private GridLayout layout;
    private int id;

    private int width = WidgetProperties.getFRAME_WIDTH() - 5;
    private int height = WidgetProperties.getACTIVITY_HEIGHT() - 5;

    
    /**
     * 
     * @param id
     */
    public ListView(int id)
    {
        this(id, new JPanel());
        JViewport viewport =super.getViewport();
        Component[] list = viewport.getComponents();
        
        for(int i=0; i<list.length;i++)
        {
            if(list[i] instanceof JPanel)
            {
                panel = (JPanel) list[i];
            }
        }
    }
    
    
    public ListView(int id, JPanel panel)
    {
        super(panel);
        this.id = id;
        // System.out.println("Dimension = " + width +"x" + height );
        setAutoscrolls(true);
        setOpaque(false);
        elements = new ArrayList<Object>();
        buttons = new HashMap<TextView,Integer>();

        BoxLayout layout = new BoxLayout(panel, BoxLayout.Y_AXIS);
        panel.setLayout(layout);
    }

    /**
     * 
     * @param arrayAdapter
     */
    public void setAdapter(ArrayAdapter arrayAdapter) {

        int numElements = arrayAdapter.getCount();
        
        Dimension dimension = calculateDimension(numElements);
        setPreferredSize(dimension);

        //the table will have only one column and numElements rows
        for(int i=0; i<numElements; i++)
        {
            elements.add(arrayAdapter.getItem(i));
            TextView button = new TextView(i,elements.get(i).toString());
            
            panel.add(Box.createVerticalStrut(20));
            panel.add(button);
            panel.add(Box.createVerticalStrut(20));
            panel.add(new JSeparator(SwingConstants.HORIZONTAL));

            buttons.put(button,i);
        }
    }

    
    /**
     * 
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) 
    {
        this.listener = onItemClickListener;

        MouseListener l = new MouseListener() 
        {
            public void mouseClicked(MouseEvent e) 
            {
                int position = buttons.get(e.getSource());
                int id = ((TextView)e.getSource()).getId();
                TextView source = (TextView)e.getSource();
                listener.onItemClick(null, source, buttons.get(source), source.getId());
            }

            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}

            public void mousePressed(MouseEvent e) 
            {
                ((TextView)e.getSource()).setOpaque(true);
                ((TextView)e.getSource()).setBackground(Color.blue);
            }

            public void mouseReleased(MouseEvent e) 
            {
                ((TextView)e.getSource()).setBackground(null);
            }
        };
        //add the listener to all the buttons of the list
        Set<TextView> keys = buttons.keySet();
        for(TextView b : keys)
        {
            b.addMouseListener(l);
        }
    }

    
    /**
     * 
     * @return
     */
    public int getListSize()
    {
        return elements.size();
    }

    
    /**
     * 
     */
    public int getId() {
        return id;
    }
    
    
    private Dimension calculateDimension(int numElements)
    {
        int w = width;
        int h = height;
        int lineHeight = WidgetProperties.getROW_HEIGHT();
        int heightNeeded = numElements * lineHeight;
        
        if(heightNeeded < h)
        {
            h = heightNeeded;
        }
        return new Dimension(w,h);
    }

}
