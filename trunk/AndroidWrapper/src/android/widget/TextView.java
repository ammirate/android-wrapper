package android.widget;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JTextArea;

import org.w3c.dom.css.ViewCSS;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class TextView extends JTextArea implements SimpleComponent
{

    /**
     * Represents the id value in R.java class that identifies the component
     */
    private int id;
    private Context context;
    
    private View.OnTouchListener touchListener;


    public TextView(int id)
    {
        super();
        this.id = id;
        setProprertyForLabel();
        
        this.setEditable(false);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setFont(WidgetProperties.getFONT());
    }

    public TextView(int id,String text)
    {
        super();
        this.id = id;
        setProprertyForLabel();
        this.setText(text);
        
        this.setEditable(false);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setFont(WidgetProperties.getFONT());

    }


    public TextView(Context applicationContext) 
    {
        this.context = applicationContext;
        this.setEditable(false);
        this.setFocusable(false);
        this.setOpaque(false);
        this.setFont(WidgetProperties.getFONT());
    }



    public int getId() 
    {
        return id;
    }


    public void setId(int id) 
    {
        this.id = id;
    }

    public void setTextSize(int size)
    {

    }

    private void setProprertyForLabel()
    {
        this.setOpaque(false);
        this.setFocusable(false);
        this.setEditable(false);
        this.setFont(WidgetProperties.getFONT());
    }


    public void setText(String text)
    {
        String newtext = generateHtmlText(text);
        super.setText(newtext);
    }

    
    private String generateHtmlText(String text)
    {
        String finaltext ="";
        if(text.length() > 0)
        {
            finaltext += text.charAt(0);

            int numChars = WidgetProperties.getCHARS_IN_LINE();
            for(int i=1; i<text.length();i++)
            {
                finaltext += text.charAt(i);
                if(i % numChars == 0)
                {
                    finaltext += "\n";
                }
            }
        }
       //System.out.println(finaltext);
        return finaltext;
    }
   
    public String getText()
    {
        String toReturn = super.getText();
        toReturn = toReturn.replaceAll("\n", "");

        return toReturn;
    }

    public void setVisibility(Object int1) {
        // TODO Auto-generated method stub
        
    }


    /**
     * 
     * @param event
     * @return
     */
    public boolean onTouchEvent(MotionEvent event) 
    {
        return false;
    }
    
    
    public void setOnTouchListener(OnTouchListener touchListener) 
    {
        this.touchListener = touchListener;
        createMouselistener();
    }
    

    private void createMouselistener()
    {
        this.addMouseListener(new MouseAdapter() 
        {
            
            public void mousePressed(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((TextView)ev.getSource(), ev);
                }
            }
            public void mouseReleased(MouseEvent e)
            {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((TextView)ev.getSource(), ev);
                }
            }
        });  
        
        this.addMouseMotionListener(new MouseMotionListener() {
            
            public void mouseMoved(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((TextView)ev.getSource(), ev);
                }
            }

            public void mouseDragged(MouseEvent e) {
                MotionEvent ev = new MotionEvent(e);
                onTouchEvent(ev);
                if(touchListener != null)
                {
                    touchListener.onTouch((TextView)ev.getSource(), ev);
                }
            }
        });
    }
}



























