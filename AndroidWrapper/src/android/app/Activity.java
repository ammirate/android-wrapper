package android.app;

import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JComponent;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.view.View;


/**
 * The class Activity is a wrapper of an Android activity.
 * This class allows to generate the graphic components present in the original 
 * activity source code, bringing the information form a Bundle object.
 * 
 * All Android activity extend this class.
 * 
 * Besides it allows to start a second activity from a first one
 *  
 * @author Antonio Cesarano
 *
 */
public class Activity extends Context
{
    /**
     * id of this activity
     */
    private int id;


    /**
     * Used to draw the graphic components
     */
    private GraphicDrawer graphicDrawer;

    /**
     * Contains the informations necessary to start a second activity
     */
    private static Intent intent;

    /**
     * Contains the informations necessary to create an activity
     */
    private Bundle bundle;
    
    /**
     * 
     */
    private Resources resources;

    
    /**
     * 
     */
     private String projectPath;
     

    /**
     * Constructor that generate the graphic layout bringing the information 
     * from the bundle object taken in input
     * 
     * @param savedInstanceState a Bundle containing all information about the 
     * necessary resources to generate the graphic layout
     */
    protected void onCreate(Bundle savedInstanceState)
    {   
        bundle = savedInstanceState;
        graphicDrawer = new GraphicDrawer(savedInstanceState);
        graphicDrawer.setOwnerActivity(this);
        graphicDrawer.setPreviusFrame(savedInstanceState.getPreviousFrame());
        graphicDrawer.generateGraphic();
        
        resources = new Resources(graphicDrawer.getRHashMap(), bundle.getResourcePath());

        Graphics g = graphicDrawer.getCurrentFrame().getGraphics();
        graphicDrawer.getCurrentFrame().paintComponents(g);
        
        super.setGraphics(g);
    }


    


    public String getProjectPath() {
        return projectPath;
    }





    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }





    /**
     * Returns the id of this activity
     * @returnthe id of this activity
     */
    public int getId() {
        return id;
    }


    /**
     * Sets a new id for this activity
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Start the execution of the activity
     */
    protected void onStart() 
    {
        this.graphicDrawer.getCurrentFrame().pack();
    }

    /**
     * Resumes the execution after put in state Pause
     */
    protected void onResume() {
        
    }


    /**
     * Sets on state Pause the activity
     */
    protected void onPause() {
        try {
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Restarts the activity after it was stopped.
     * This method is not implemented because Java doesn't allow to a stopped
     * Thread to restart. For more information see Java API on java.lang.Thread.
     */
    protected void onRestart() {
        //the same thread can't be restarted in java
    }

    /**
     * Stops the activity. Deprecated.
     */
    protected void onStop() {
    }

    /**
     *  Destroys the activity.Deprecated.
     */
    protected void onDestroy() {
    }

    /**
     * Add to the current view (current frame) the graphic component which has
     * id = view
     * @param view the id of the graphic component to add to the graphic frame
     */
    public void setContentView(int view)
    {
        //get the name of xml file to add
        graphicDrawer.setContentView(view);
    }

    /**
     * Returns the graphic component identified by the id passed in input
     * @param id the component's id 
     * @return the reference of a JComponent drawn in the graphic frame
     */
    public JComponent findViewById(int id)
    {
        //System.out.println("Activity.FindElementById- "+id);
        Object o = graphicDrawer.findElementById(id);
        return (JComponent)o;
    }   


    /**
     * Creates and starts a new activity, bringing the information from an 
     * Intent object. It should contain the class that want to generate the new 
     * activity and the class file of the new activity
     * @param intent
     */
    protected void startActivity(Intent intent) 
    {
        this.intent = intent;
        Class toInvoke = intent.getActivityTarget();

        //The bundle is used to take the R class
        Bundle bundle = graphicDrawer.getBundle();

        /* The xml string should be of this form:
         * "/android/androidCode/activities/activity_display_message.xml";
         */
        String xml = getXMLNameFromActivity();
        System.out.println("into intent "+xml);

        /* Call the constructor passing it the activity class, the R class, the 
         * xml path and the current frame that will be  the previous frame for 
         * the new activity
         */
        AndroidWrapper wrapper = new AndroidWrapper(toInvoke, bundle.getR(), xml, graphicDrawer.getCurrentFrame());
        wrapper.startAndroidActivity();
    }


    /**
     * Returns the GraphicDrawer of this object
     * @return the {@link GraphicDrawer} of this activity
     */
    public GraphicDrawer getGraphicDrawer()
    {
        return graphicDrawer;
    }


    /**
     * Returns the Intent of this activity. If it doesn't generate another 
     * activity, its intent will be null.
     * @return the {@link Intent} of this activity. It could be null.
     */
    protected Intent getIntent() 
    {
        return intent;
    }


    /**
     * Sets the {@link GraphicDrawer} of this class with a new one
     * @param graphicDrawer the new GraphicDrawer 
     */
    public void setGraphicDrawer(GraphicDrawer graphicDrawer) {
        this.graphicDrawer = graphicDrawer;
    }


    /**
     * Tells to the GraphicDrawer to add the object in input to the 
     * graphic frame
     * @param view the graphic component to add to the graphic frame
     */
    protected void setContentView(View view) 
    {     
        this.getGraphicDrawer().setContentView(view);
   
    }


    /**
     * Takes the activity target from the intent and generate the string 
     * that contains the path of the relative xml file
     * 
     * This method guarantees a correct path if are used the standard naming
     * for the classes and the relative xml files.
     *  
     *      e.g.   Activity name = MainActivity
     *             XML file name = activity_main.xml
     * 
     * @return the path of the xml file used by a new activity
     */
    private String getXMLNameFromActivity()
    {
        //take the activity name to create the String in format activity_class_name.xml
        String name = intent.getActivityTarget().getName();   
        String pack = intent.getActivityTarget().getPackage().toString() + ".";
        pack = pack.replace("package ", "");

        /* Removes the package path and the last part of the class 
         * name (it's always 'Activity')
         */
        name = name.replace(pack, "");
        name = name.replace("Activity", "");

        //  System.out.println("Name: "+name);
        //  System.out.println("XML path: " + pack);
        char[] chars = name.toCharArray();

        String xmlNameFile = "activity_" + Character.toLowerCase(chars[0]);

        /*
         * While the current char is lowerCase it will be added to the target 
         * string. If the current char is upperCase than is starting a new word
         * so will be added an '_' 
         */
        for(int i=1; i<chars.length; i++)
        {
            if(Character.isLowerCase(chars[i]))
            {
                xmlNameFile += chars[i];
            }
            else
            {
                xmlNameFile += "_";
                xmlNameFile += Character.toLowerCase(chars[i]);
            }
        }
        //add the xml extension
        xmlNameFile += ".xml";          

        /* add to the xml file name the package path 
         * e.g.  xmlFilename = '/home/workspace/app/' + 'activity_name_file.xml'
         */
        pack = bundle.getXmlPath();
        int lastSlash = pack.lastIndexOf("/");
        pack = pack.substring(0, lastSlash+1);
        xmlNameFile = pack + xmlNameFile;

        return xmlNameFile;
    }
    
    
    
    @Override
    public Resources getResources() 
    {
        return resources;
    }



// all abstract methods to implement


    public boolean onCreateOptionsMenu(Menu menu)
    {
        return false;
    }

    
    
    public boolean onPrepareOptionsMenu (Menu menu)
    {
        return false;
    }




    @Override
    public Context getApplicationContext() {
        return null;
    }




    @Override
    public void setTheme(int resid) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public ClassLoader getClassLoader() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public String getPackageName() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public String getPackageResourcePath() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public String getPackageCodePath() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getSharedPrefsFile(String name) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public FileInputStream openFileInput(String name)
            throws FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public FileOutputStream openFileOutput(String name, int mode)
            throws FileNotFoundException {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public boolean deleteFile(String name) {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public File getFileStreamPath(String name) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getFilesDir() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getExternalFilesDir(String type) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getObbDir() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getCacheDir() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getExternalCacheDir() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public String[] fileList() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public File getDir(String name, int mode) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public boolean deleteDatabase(String name) {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public File getDatabasePath(String name) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public String[] databaseList() {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumWidth() {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    @Deprecated
    public int getWallpaperDesiredMinimumHeight() {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    @Deprecated
    public void setWallpaper(InputStream data) throws IOException {
        // TODO Auto-generated method stub
        
    }




    @Override
    @Deprecated
    public void clearWallpaper() throws IOException {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void startActivities(Intent[] intents) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void startActivities(Intent[] intents, Bundle options) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendBroadcast(Intent intent) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendBroadcast(Intent intent, String receiverPermission) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendOrderedBroadcast(Intent intent, String receiverPermission) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendBroadcastAsUser(Intent intent, UserHandle user,
            String receiverPermission) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendStickyBroadcast(Intent intent) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void removeStickyBroadcast(Intent intent) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void sendStickyBroadcastAsUser(Intent intent, UserHandle user) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void removeStickyBroadcastAsUser(Intent intent, UserHandle user) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public ComponentName startService(Intent service) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public boolean stopService(Intent service) {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public ComponentName startServiceAsUser(Intent service, UserHandle user) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public boolean stopServiceAsUser(Intent service, UserHandle user) {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public boolean startInstrumentation(ComponentName className,
            String profileFile, Bundle arguments) {
        // TODO Auto-generated method stub
        return false;
    }




    @Override
    public Object getSystemService(String name) {
        // TODO Auto-generated method stub
        return null;
    }




    @Override
    public int checkPermission(String permission, int pid, int uid) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public int checkCallingPermission(String permission) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public int checkCallingOrSelfPermission(String permission) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public void enforcePermission(String permission, int pid, int uid,
            String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void enforceCallingPermission(String permission, String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void enforceCallingOrSelfPermission(String permission, String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void grantUriPermission(String toPackage, Uri uri, int modeFlags) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void revokeUriPermission(Uri uri, int modeFlags) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public int checkUriPermission(Uri uri, int pid, int uid, int modeFlags) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public int checkCallingUriPermission(Uri uri, int modeFlags) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public int checkCallingOrSelfUriPermission(Uri uri, int modeFlags) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public int checkUriPermission(Uri uri, String readPermission,
            String writePermission, int pid, int uid, int modeFlags) {
        // TODO Auto-generated method stub
        return 0;
    }




    @Override
    public void enforceUriPermission(Uri uri, int pid, int uid, int modeFlags,
            String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void enforceCallingUriPermission(Uri uri, int modeFlags,
            String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void enforceCallingOrSelfUriPermission(Uri uri, int modeFlags,
            String message) {
        // TODO Auto-generated method stub
        
    }




    @Override
    public void enforceUriPermission(Uri uri, String readPermission,
            String writePermission, int pid, int uid, int modeFlags,
            String message) {
        // TODO Auto-generated method stub
        
    }







    public boolean onMenuOpened(int featureId, Menu menu) {
        // TODO Auto-generated method stub
        return false;
    }




    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        return false;
    }
    
    
    protected final boolean requestWindowFeature(String title) {
        return false;
        
    }


}














