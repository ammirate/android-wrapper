package android.graphics;

import java.awt.BasicStroke;
import java.awt.Color;

/**
 * The Paint class holds the style and color information about how to draw 
 * geometries, text and bitmaps. 
 * 
 * @author Antonio Cesarano
 *
 */
public class Paint 
{   
    public static final String ANTI_ALIAS_FLAG = "antialias";
    public static final String Style = null;

    /**
     * Default color 
     */
    private Color color = Color.black;

    /**
     * Default stroke
     */
    private BasicStroke stroke = new BasicStroke(0);
    private int textSize = 12;


    /**
     * Create a new paint with default settings
     */
    public Paint()
    {
        color = Color.black;
        stroke = new BasicStroke(0);
    }

    /**
     * 
     * @param constant
     */
    public Paint(String constant) 
    {
        if(constant.equals(ANTI_ALIAS_FLAG))
        {
            //do something
        }
        color = Color.black;
        stroke = new BasicStroke(0);
    }

    /**
     * Return the paint's color. Note that the color is a 32bit value containing
     * alpha as well as r,g,b. This 32bit value is not premultiplied, meaning 
     * that its alpha can be any value, regardless of the values of r,g,b. 
     * See the Color class for more details.
     * 
     * @return the paint's color.
     */
    public Color getColor() 
    {
        return color;
    }

    /**
     * Set the paint's color. Note that the color is an int containing alpha as 
     * well as r,g,b. This 32bit value is not premultiplied, meaning that its 
     * alpha can be any value, regardless of the values of r,g,b. See the Color 
     * class for more details.
     * 
     * @param color The new color (including alpha) to set in the paint. 
     */
    public void setColor(Color color) 
    {
        this.color = color;
    }


    /**
     * Set the width for stroking. Pass 0 to stroke in hairline mode. 
     * @param width the stroke's width
     */
    public void setStrokeWidth(int width) 
    {
        if(width >= 0)
        {
            stroke = new BasicStroke(width);
        }
    }
    
    
    
    public void setStrokeWidth(float width) 
    {
        if(width >= 0)
        {
            stroke = new BasicStroke(width);
        }
    }


    /**
     * Return the stroke width
     * @return the paint's stroke width
     */
    public int getStrokeWidth()
    {
        return (int) stroke.getLineWidth();
    }


    /**
     * Return the width for stroking. 
     * @return the paint's stroke.
     */
    public BasicStroke getStroke() {
        return stroke;
    }



    /**
     * Set a new stroke for this paint
     * @param stroke the new stroke.
     */
    public void setStroke(BasicStroke stroke) {
        this.stroke = stroke;
    }

    /**
     * Set the paint's color. 
     * Note that the color is an int containing alpha as well as r,g,b. 
     * This 32bit value is not premultiplied, meaning that its alpha can be any 
     * value, regardless of the values of r,g,b. 
     * See the Color class for more details.
     * 
     * @param i The new color to set in the paint. 
     */
    public void setColor(int i) 
    {
        this.color = new Color(i);
    }

    /**
     * 
     * @param i
     */
    public void setTextSize(int i) 
    {
        this.textSize  = i;
    }

    /**
     * antiAlias property non implemented
     * @param b
     */
    public void setAntiAlias(boolean b) 
    {
        
    }




}
