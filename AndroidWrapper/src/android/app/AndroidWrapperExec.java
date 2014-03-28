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
        String resourcePath = "/home/cesarano/workspace/AndroidWrapper/src/android/androidCode/" + project + "/";

        AndroidWrapper androidWrapper = new AndroidWrapper(resourcePath, packagePath);      
        androidWrapper.startAndroidActivity();
    }
    
    
    

}
