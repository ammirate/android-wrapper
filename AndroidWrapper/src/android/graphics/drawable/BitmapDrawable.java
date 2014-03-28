package android.graphics.drawable;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

import android.graphics.Bitmap;

public class BitmapDrawable extends DrawableContainer
{
    
    public BitmapDrawable(BufferedImage image)
    {
        super(image);
    }
    
    public BitmapDrawable(String path) 
    {
        super(path);
    }

    public BitmapDrawable(ImageIcon image) 
    {
        super(image);
    }

    public Bitmap getBitmap() 
    {
        return new Bitmap(super.getImage());
    }

}
