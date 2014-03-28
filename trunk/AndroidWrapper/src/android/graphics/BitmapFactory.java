package android.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import android.content.res.Resources;

public class BitmapFactory {

    public static Bitmap decodeResource(Resources resources, int imageId) 
    {
        Image img = resources.getDrawable(imageId).getImage();
        return new Bitmap(img);
        
    }

}



