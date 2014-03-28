package android.graphics;

import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JPanel;

import android.widget.WidgetProperties;

/**
 * The Canvas class holds the "draw" calls.
 *  To draw something, you need 4 basic components: 
 *  - a Bitmap to hold the pixels (not implemented yet)
 *  - a Canvas to host the draw calls (use Graphic class instead Bitmap)
 *  - a drawing primitive (e.g. Rect, Path, text, Bitmap)
 *  - a paint (to describe the colors and styles for the drawing)
 *  
 *  The drawing area is represented by the graphic area of the 
 *  JPanel that is superclass of this object.
 *  
 * @author Antonio Cesarano
 *
 */
public class Canvas extends JPanel
{
    private double scalefactor = 1;

    /**
     * Graphic object of the frame in which draw everything
     */
    private Graphics2D graphic ;

    /**
     * Paint object containing all the drawing properties
     */
    private Paint paint = new Paint();

    /*
     * Width and height of the activity screen. 
     * They are used to don't draw outside the screen area
     */
    private int height = WidgetProperties.getACTIVITY_HEIGHT();
    private int width = WidgetProperties.getACTIVITY_WIDTH();


    private Logger logger = Logger.getLogger("Global");

    private int rotation = 0;
    private int rotateX = 0;
    private int rotateY = 0;


    /**
     * Create a JPanel with the correct size and initialize the graphic 
     * component that will be used for drawing.
     */
    public Canvas()
    {
        super();
        JLabel l = new JLabel("");
        super.add(l);
        /* if there isn't any component the graphic object returned by 
         * getGraphics() will be null;  */
        this.graphic = (Graphics2D) super.getGraphics();
        super.setSize(new Dimension(width,height));

        logger.info("CANVAS : Graphic may be null here");
    }


    /**
     *  
     * Create a JPanel with the correct size and initialize the graphic 
     * component with the one passed as argument.
     *
     * @param g the graphic element that allows to draw shapes
     */
    public Canvas(Graphics g)
    {
        super();
        this.graphic = (Graphics2D) g;
        super.setSize(new Dimension(width,height));

        //allows to see the correctly the png images with some transparent parts
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        graphic.setComposite(ac);
    }



    /**
     * Draw a rectangle in the point x and y of the screen.
     * The width and height values will be adapted in proportion to
     * the screen size., using the @getScaledValue method
     * 
     * @param x horizontal space from the origin of the panel
     * @param y vertical space from the origin of the panel
     * @param width the width of the rectangle to draw 
     * @param height the height of the rectangle to draw
     * @param paint the paint component contains informations for drawing 
     * correctly the shape
     */
    public void drawRect(int x, int y, int width, int height, Paint paint) 
    {
        this.paint = paint;

        x = getScaledX(x);
        y = getScaledY(y);
        width = getScaledX(width);
        height = getScaledY(height);

        width += paint.getStrokeWidth();
        height += paint.getStrokeWidth();

        this.graphic.setColor(paint.getColor());
        this.graphic.setStroke(paint.getStroke());
        this.graphic.fillRect(x, y, width, height);   

    }



    /**
     * Draw a point in the (x,y) position.
     * 
     * @param x the abscissa value
     * @param ythe ordinate value
     * @param paint the paint component contains informations for drawing 
     * correctly the shape
     */
    public void drawPoint(int x, int y, Paint paint) 
    {
        this.paint = paint;         
        graphic.setColor(paint.getColor());

        x = getScaledX(x);
        y = getScaledY(y);

        if(!isOutScreen(x, y))
        {
            this.graphic.fillOval(x, y, paint.getStrokeWidth(), paint.getStrokeWidth());
        }
    }



    /**
     * Return the value in input, for example the height of a rectangle, scaled
     * for the screen size.
     * 
     * e.g. value = 80, screenHeight = 800, realHeight = 1000
     * 
     *          value : realHeight = x :  screenHeight
     *          80 : 1000 = x : 800
     *          x = (800*800)/1000 = 64
     * 
     * @param value the value to scale
     * @return the new value scaled for the screen size
     */
    public int getScaledY(int value)
    {   
        int scaledValue = 0;
        int realWidth = WidgetProperties.getREAL_WIDTH();
        int realHeight = WidgetProperties.getREAL_HEIGHT();

        if(!WidgetProperties.isLandscape())
        {
            scaledValue = (value * height) / realHeight;
        }
        else
        {
            scaledValue = (value * height) / realWidth;

        }
        /*   
        System.out.println("\nReaHeight: " + realHeight + " but height= "+height);
        System.out.println("Value: " + value);
        System.out.println("Scaled: " + scaledValue+ "\n");
         */
        return (int) scaledValue;
    }


    /**
     * 
     * @param value
     * @return
     */
    public int getScaledX(int value)
    {   
        int scaledValue = 0;
        int realWidth = WidgetProperties.getREAL_WIDTH();
        int realHeight = WidgetProperties.getREAL_HEIGHT();


        if(!WidgetProperties.isLandscape())
        {
            scaledValue = (value * width) / realWidth;
        }
        else
        {
            // System.out.println("X | scaledvalue = (" + value + " * "+ realHeight +")/"+width);

            scaledValue = (value * width) / realHeight;
        }
        /* 
        System.out.println("    RealWidth: " + realWidth + " but width=" + width);
        System.out.println("    Value: " + value);
        System.out.println("    Scaled: " + scaledValue+ "\n");
         */
        return scaledValue;
    }



    /**
     * Draw the specified bitmap, with its top/left corner at (x,y), 
     * using the specified paint.
     * 
     * @param bitmap  The bitmap to be drawn
     * @param left The position of the left side of the bitmap being drawn
     * @param top The position of the top side of the bitmap being drawn
     * @param paint The paint used to draw the bitmap (may be null) 
     */
    public void drawBitmap (Bitmap bitmap, int left, int top, Paint paint) 
    {

        Image img = bitmap.getImage();       
        int imageWidth = getScaledX(img.getWidth(null));
        int imageHeight = getScaledY(img.getHeight(null)); 


        int x = getScaledX(left);
        int y = getScaledY(top);

      //  System.out.println("\ncoordinate original: "+ left + "," + top);
      //  System.out.println("New coordinate: " + x + "," + y);


        if(paint == null) paint = new Paint();
        this.paint = paint;
        graphic.setColor(paint.getColor());

        //allows to see the correctly the png images with some transparent parts
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1);
        graphic.setComposite(ac);

        //  if(left>0 && top>0)System.out.println("["+left+","+top+"]     " + "scaled ["+x+","+y+"]" );

        graphic.drawImage(img, x, y, imageWidth, imageHeight, null, null);
    }


    /**
     * 
     * @param x
     * @param y
     * @return
     */
    private boolean isOutScreen(float x, float y) 
    {  
        int screenW = WidgetProperties.getACTIVITY_WIDTH();
        int screenH = WidgetProperties.getACTIVITY_HEIGHT();
        if(x > screenW  || x < 0)
        {
            System.out.println("X OUT THE SCREEN: expected x in [0,"+screenW+"] but X="+x);
            return true;
        }
        if( y > screenH || y < 0 )
        {
            System.out.println("Y OUT THE SCREEN: expected Y in [0,"+screenH+"] but Y="+y);
            return true;
        }
        return false;
    }




    /**
     * 
     * @param bitmap
     * @param fromRect1
     * @param toRect1
     * @param paint
     */
    public void drawBitmap(Bitmap bitmap, Rect fromRect1, Rect toRect1, Paint paint) 
    {
        java.awt.Image img = bitmap.getImage();
        graphic.setColor(paint.getColor());
        java.awt.Image toDraw;

        int width = getScaledX(img.getWidth(null));
        int height = getScaledY(img.getHeight(null));


        if(fromRect1 != null)
        {
            int x = Math.abs(fromRect1.width() - toRect1.width());
            int y = Math.abs(fromRect1.height() - toRect1.height());
            toDraw = img.getScaledInstance(x, y, java.awt.Image.SCALE_FAST);
            graphic.drawImage(toDraw, toRect1.centerX(), toRect1.centerY(), x, y, graphic.getColor(), null);
        }
        else
        {
            toDraw = img.getScaledInstance(toRect1.width(), toRect1.height(), java.awt.Image.SCALE_FAST);
            graphic.drawImage(toDraw,toRect1.centerX(), toRect1.centerY(), width, height, graphic.getColor(), null);
        }
    }




    /**
     * Return the current graphic oobject setted for this Canvas
     */
    public Graphics2D getGraphics() 
    {
        return this.graphic;
    }

    /**
     * Set a new Graphic object for this Canvas
     * 
     * @param graphic the new Graphic object. It could be an instance of the
     * Grapgic object instead a Graphic2D instance.
     */
    public void setGraphics(Graphics2D graphic) 
    {
        this.graphic = graphic;
    }

    /**
     * Returns the current paint object used by this Canvas for drawing shapes.
     * 
     * @return the current paint object.
     */
    public Paint getPaint() 
    {
        return paint;
    }

    /**
     * Set a new Paint object for this Canvas. A new Paint object will changes 
     * the properties of all the shapes that will be drawn on this Canvas.
     * 
     * @param paint the new Paint object
     */
    public void setPaint(Paint paint) 
    {
        this.paint = paint;
    }


    public void save() 
    {

    }


    public void rotate(int angle, int x, int y) 
    {
        this.rotation = angle;
        this.rotateX = getScaledX(x);
        this.rotateY = getScaledY(y);

        graphic.rotate(Math.toRadians(angle), rotateX, rotateY);
    }


    public void restore()
    {   
        graphic.rotate(Math.toRadians(-rotation), -rotateX, - rotateY);   
    }





    public void drawText(String string, int x, int y, Paint paint) 
    {
        this.paint = paint;
        graphic.setColor(paint.getColor());
        graphic.drawString(string, x, y);
    }

    

    public void drawPath(Path path, Paint paint) 
    {
        this.paint = paint;
        List<Path.Line> lines = path.getLines();
        
        for(Path.Line l : lines)
        {
            int x1 = l.getStart().getX();
            int y1 = l.getStart().getY();
            int x2 = l.getEnd().getX();
            int y2 = l.getEnd().getY();
            graphic.drawLine(x1, y1, x2, y2);
        }
    }

    
    
}



















