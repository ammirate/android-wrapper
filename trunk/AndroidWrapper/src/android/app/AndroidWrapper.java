package android.app;


import java.awt.AWTEvent;
import java.awt.Event;
import java.awt.EventQueue;
import java.util.logging.Logger;

import javax.swing.JFrame;

import com.sun.media.sound.Toolkit;

import android.graphic.GraphicDrawer;
import android.os.Bundle;
/**
 * The AndroidWrapper class is a wrapper that allows to execute code written 
 * for Android on Linux based systems. 
 * 
 * To run the first time an Android app it is necessary to invoke the 
 * constructor AndroidWrapper(String xmlPath, String packagePath) 
 * that automatically call the MainActivity class of the project and then it's 
 * possible to run it with the method startAndroidActivity()
 * 
 * @author Antonio Cesarano
 *
 */
public class AndroidWrapper 
{   
    private  Activity activity;

    /**
     * A Bundle object necessary to create a new Activity and run it
     */

    private Bundle bundle;

    /**
     * Logger to test the class
     */
    private Logger logger = Logger.getLogger("global");

    /**
     * The Class file og the activity to run
     */
    private Class activityClass = null;

    /**
     * 
     */
    private Class R = null;


    /**
     * Constructor. Use this constructor to run the main activity of your 
     * Android app.
     * 
     * It needs the original Android source code (an Activity), written in a 
     * MainActivity class and the R class that contains all layout component 
     * references. It assumes that these two classes are in the same package.
     * 
     * Besides an AndroidWrapper needs the xml file, usually called 
     * 'activity_main.xml', that contains all the graphic component details.
     * It can reside in a different location from the MainActivity and R classes
     * 
     * To execute the Android code, call the method startAndroidActivity() 
     * on this object.
     * 
     * @param projectPath absolute path of the source code 
     *     e.g. "/home/workspace/HelloWorld/src/"
     *  
     * @param packagePath path of the package that contains the 
     *  MainActivity.java and the R.java files
     *     e.g. com.aicas.HelloWorld
     *  
     * @throws ClassNotFoundException if a class isn't in the specified path
     * 
     * @throws IllegalAccessException if is not possible to access to the class
     * 
     * @throws InstantiationException if is not possible to instantiate a 
     * new Activity
     */
    public AndroidWrapper(String projectPath, String packagePath) 
    {

        String xmlPath = projectPath + "activity_main.xml";
        try 
        {
            //take the R class from the package
            R = Class.forName(packagePath + ".R");
        }
        catch (ClassNotFoundException e)
        {
            logger.severe("R.java class not found in "+packagePath);
            logger.severe("Please control the package path argument. " +
                    "Program will exit");
            System.exit(1);
        }
        try 
        {
            //take the MainActivity class from the package
            activityClass = Class.forName(packagePath + ".MainActivity");
        } 
        catch (ClassNotFoundException e) 
        {
            logger.severe("MainActivity.java class not found in "+packagePath);
            logger.severe("Please control the package path argument or " +
                    "rename your activity in 'MainActivity.java'. Program will exit");
            System.exit(1);
        }

        this.bundle = new Bundle(xmlPath, R);
        this.bundle.setProjectPath(projectPath);

        try 
        {     
            //sets the activity to run
            this.activity = (Activity) activityClass.newInstance();      
        } 
        catch (InstantiationException e) 
        {
            logger.severe("Impossible to instantiate the activity. " +
                    "Program will exit");
            System.exit(1);      
        } 
        catch (IllegalAccessException e) {
            logger.severe("Impossible to access at MainActivity. Program will exit");
            System.exit(1);
        }
    }


    /**
     * This constructor takes directly in input the necessary class to be 
     * executed. It can be used to run whatever activity.
     * 
     * @param activity the Class object of the activity to run
     * @param R the R Class object
     * @param xmlPath the absolute path of the xml file reguarding the activity
     * @param previousFrame JFrame object reachable pressing the Back button on 
     * the main JFrame. If the activity is the MainActivity, set it null
     */
    public AndroidWrapper(Class activity, Class R, String xmlPath, JFrame previousFrame)
    {
        this.activityClass = activity;
        this.R = R;
        this.bundle = new Bundle(xmlPath, R);
        this.bundle.setPreviousFrame(previousFrame);
        try
        {
            this.activity = (Activity) activityClass.newInstance();           
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        } 
        catch (IllegalAccessException e) {
            e.printStackTrace();
        }       

    }


    /**
     * Creates and starts the the activity
     */
    public void startAndroidActivity()
    {
        activity.onCreate(this.bundle);
        activity.onStart();
        activity.onResume();

    }


}










