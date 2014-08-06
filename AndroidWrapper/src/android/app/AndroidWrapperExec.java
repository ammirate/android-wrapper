package android.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.widget.WidgetProperties;

public class AndroidWrapperExec {

    public static void main(String args[]) throws IOException
    {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("~~~~~~~~~~~~~~~~~~~~~~ ANDROID WRAPPER ~~~~~~~~~~~~~~~~~~~\n ");

    //    System.out.print("Insert the screen height: ");
    //    String height = in.readLine();
    //    int heightValue = Integer.parseInt(height);
        int heightValue = 600;
        WidgetProperties.setHeight(heightValue);
        boolean CONTINUE = false;
    /*    
        do 
        {
            System.out.print("Select the Screen rotation: 'p'=Portrait / 'l'=Landscape  ");
            String rotation = in.readLine();
            if(rotation.equalsIgnoreCase("p"))
            {
                WidgetProperties.setPortrait();
                CONTINUE = true;
            }
            else if(rotation.equalsIgnoreCase("l"))
            {
                WidgetProperties.setLandscape();
                CONTINUE = true;
            }
            else
            {
                System.out.println("Value not valid!\n\n");
            }
        }while(!CONTINUE);
*/
        System.out.print("Insert the project name: ");//[package: android.androidCode]
        String project = in.readLine();

        System.out.println("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n ");


        executeAndroidApp(project); 
    }

    private static void executeAndroidApp(String project) 
    {
        String packagePath = "android.androidCode." + project;
        String resourcePath = getProjectPath(project);
        
        AndroidWrapper androidWrapper = new AndroidWrapper(resourcePath, packagePath);      
        androidWrapper.startAndroidActivity();
    }
    
    
    
    /**
     * Get the current working path to load correctly all the files
     * to execute the android app. The string returned changes in base of
     * the Operating system in use.
     * 
     * @return the correct project path for the current operative system
     */
   private static String getProjectPath(String project)
   {
       String OS = System.getProperty("os.name").toLowerCase();
       String currentPath =  System.getProperty("user.dir");

      // System.out.println("CurrentPath: " + currentPath);
       if(OS.contains("win"))
       {
           return /*currentPath +*/ "\\android\\androidCode\\" + project + "\\";
       }
       else if(OS.contains("nix") || OS.contains("nux"))
       {
          return /*currentPath +*/ "/android/androidCode/" + project + "/";
       }
       else
       {
           throw new RuntimeException("Operating system not supported");
       }
   }
    
    

}
