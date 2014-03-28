package android.graphics;

import java.awt.color.ColorSpace;


/**
 * The Color class defines methods for creating and converting color ints.
 * This Class simply inherits the java.awt.Color behavior.
 * 
 * @author Antonio Cesarano
 *
 */
public class Color extends java.awt.Color{

   /**
    * Constructor that creates a color from an exadecimal code
    * 
    * @param code the code of the color that you want to create
    */
    public Color(int code)
    {
        super(code);
    }

}
