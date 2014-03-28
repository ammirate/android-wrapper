package android.os;

import javax.swing.JFrame;

/**
 * 
 * A Bundle object in Android represents a pack of system information; 
 * this class is used to contain the path of the xml file that contains the
 * Android layout and the R class that contains all the references for the
 * graphic components.
 * 
 * @author Antonio Cesarano
 */
public class Bundle 
{

    private String xmlPath;
    private String projectPath;
    private Class R;
    private JFrame previousFrame;
    /**
     * Constructor for the Bundle object.
     * @param xmlPath is the activity_main.xml file path 
     * @param R is the R.java file auto-built for the Android app
     */
    public Bundle(String xmlPath, Class R)
    {
        this.xmlPath = xmlPath;
        this.R = R;
    }


    public Bundle() 
    {
    }


    public Bundle(Parcel parcel, int length) {
        // TODO Auto-generated constructor stub
    }


    public Class getR() {
        return R;
    }

    public void setR(Class r) {
        R = r;
    }

    public String getXmlPath() {
        return xmlPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }


    public JFrame getPreviousFrame() {
        return previousFrame;
    }
    
    public void setPreviousFrame(JFrame frame)
    {
        this.previousFrame = frame;
    }
    
    

    public String getProjectPath() {
        return projectPath;
    }


    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }
    
    public String getResourcePath()
    {
        return projectPath + "drawable-hdpi/";
    }


    public void putString(String string, String scoreBoard) 
    {
        
    }


    public void putInt(String string, String visible) {
        // TODO Auto-generated method stub
        
    }


    public boolean containsKey(String string) {
        // TODO Auto-generated method stub
        return false;
    }


    public Object getInt(String string) {
        // TODO Auto-generated method stub
        return null;
    }


    public String getString(String string) {
        // TODO Auto-generated method stub
        return null;
    }


    public void writeToParcel(Parcel parcel, int i) {
        // TODO Auto-generated method stub
        
    }


    public void setClassLoader(ClassLoader loader) {
        // TODO Auto-generated method stub
        
    }



}
