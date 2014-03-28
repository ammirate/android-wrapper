package android.content.res;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import backup_old_classes.ImageIcontest;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.DrawableContainer;

public class Resources 
{

    private HashMap<Integer, String> hashMap;
    private String resourcePath;

    public Resources(HashMap<Integer, String> map, String resoucePath)
    {
        this.hashMap = map;
        this.resourcePath = resoucePath;
    }

    public CharSequence getText(int resId)
    {
        return null;
    }

    public String getString(int resId)
    {
        return null;
    }

    public String getString(int resId, Object[] formatArgs)
    {
        return null;
    }


    public DrawableContainer getDrawable(int id) 
    {
        
        String name = hashMap.get(id);
        String imagePath = resourcePath + name + ".png";
         
        ImageIcon ii = new ImageIcon(getClass().getResource(imagePath));
        BitmapDrawable image = new BitmapDrawable(ii);
  
        return image;
    }

    public CharSequence[] getTextArray(int textArrayResId) {
        // TODO Auto-generated method stub
        return null;
    }

    


}
