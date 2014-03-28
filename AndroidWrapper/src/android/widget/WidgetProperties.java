package android.widget;

import java.awt.Font;


public class WidgetProperties 
{

    //##################### Reference Dimension ################################
    private static int REAL_HEIGHT = 800;
    private static int REAL_WIDTH = 480;
    
    //##################### Main Frame properties ############################
   
    private static   int FRAME_HEIGHT            = 600; //at least 400 for good rendering
    private static   int FRAME_WIDTH             = (int) (FRAME_HEIGHT / 1.6667);
    

    private static   int ACTIVITY_HEIGHT         =  (int) (FRAME_HEIGHT * 0.9) ;
    private static   int ACTIVITY_WIDTH          = (int) FRAME_WIDTH;

    private static   int COMMAND_HEIGHT          =  (int) (FRAME_HEIGHT * 0.1);
    private static   int COMMAND_WIDTH           = (int) COMMAND_HEIGHT;
    
    private static   int COMMAND_BUTTON_SIZE     = COMMAND_HEIGHT - 16;
    
    //default is portrait
    private static  int biggerSide = FRAME_HEIGHT;
    private static  int smallerSide = FRAME_WIDTH;
    
    //###################### TextView properties ##############################
    
    /**
     * fontSize is the size of the font calculating from the frame height
     * 
     * e.g. FRAME_HEIGHT = 600
     *          fontSize = (int) (1.87 * 10) - 3 = 15
     */
    private static  int fontSize                  = (int) (Math.log10((biggerSide/10)) * 10 ) - 3;
    private static   Font FONT                    = new Font(Font.SERIF, Font.CENTER_BASELINE, fontSize);
    private static   Font FONT_CLICKED            = new Font(Font.SERIF, Font.CENTER_BASELINE,fontSize + 1);    
    private static  int CHARS_IN_LINE             = (FRAME_WIDTH / 10) ;

    
    //####################### EditText properties ###########################
    private static  int factor                    = (int) biggerSide / 300;
    private static  int NUM_COLUMNS               = (int)((smallerSide / 10) - (biggerSide / (Math.pow(2, factor) *10)) );
    

    //####################### ListView properties ###########################
    private static int ROW_HEIGHT = fontSize + 60;
    
    private static boolean isLandscape = false;
    
    /**
     * 
     */
    private static  void recalc()
    {        
        ACTIVITY_HEIGHT     =  (int) (FRAME_HEIGHT * 0.9);
        ACTIVITY_WIDTH      = (int) (ACTIVITY_HEIGHT / 1.6667);
        COMMAND_HEIGHT      =  (int) (FRAME_HEIGHT * 0.1);
        COMMAND_WIDTH       = (int) FRAME_WIDTH;
        COMMAND_BUTTON_SIZE = COMMAND_HEIGHT;
        factor              = (int) FRAME_WIDTH / 300;
        fontSize            = (int) (Math.log10((biggerSide/10)) * 10 - (4-factor)) ;    
        FONT                = new Font(Font.SERIF, Font.CENTER_BASELINE, fontSize);
        FONT_CLICKED         = new Font(Font.SERIF, Font.CENTER_BASELINE,fontSize+1);    
        CHARS_IN_LINE       = (FRAME_WIDTH /9) - 5;
        NUM_COLUMNS         = (int)(FRAME_WIDTH / fontSize) - 6;
        ROW_HEIGHT = fontSize + 60;
    }
    
    
    public static  void setLandscape()
    {
        if(FRAME_HEIGHT > FRAME_WIDTH)
        {
            int temp = FRAME_WIDTH;
            FRAME_WIDTH = FRAME_HEIGHT;
            FRAME_HEIGHT = temp;
            
            smallerSide = FRAME_HEIGHT;
            biggerSide = FRAME_WIDTH;
            isLandscape = true;
        }
        recalc();
    }
    
    
    public static  void setPortrait()
    {
        if(FRAME_WIDTH > FRAME_HEIGHT)
        {
            int temp = FRAME_WIDTH;
            FRAME_WIDTH = FRAME_HEIGHT;
            FRAME_HEIGHT = temp;
            isLandscape = false;
        }      
        biggerSide = FRAME_HEIGHT;
        smallerSide = FRAME_WIDTH;
        recalc();
    }
    
    
    /**
     * 
     * @param height
     */
    public static  void setHeight(int height)
    {
        FRAME_HEIGHT = height;
        FRAME_WIDTH  = (int) (FRAME_HEIGHT / 1.6667) + 1;
        recalc();
    }

    
    // ###################### GETTERS ##########################


    public static int getFRAME_HEIGHT() {
        return FRAME_HEIGHT;
    }


    public static int getFRAME_WIDTH() {
        return FRAME_WIDTH;
    }


    public static int getACTIVITY_HEIGHT() {
        return ACTIVITY_HEIGHT;
    }


    public static int getACTIVITY_WIDTH() {
        return ACTIVITY_WIDTH;
    }


    public static int getCOMMAND_HEIGHT() {
        return COMMAND_HEIGHT;
    }


    public static int getCOMMAND_WIDTH() {
        return COMMAND_WIDTH;
    }


    public static int getCOMMAND_BUTTON_SIZE() {
        return COMMAND_BUTTON_SIZE;
    }


    public static int getVERTICAL_SIDE() {
        return biggerSide;
    }


    public static int getHORIZONTAL_SIDE() {
        return smallerSide;
    }


    public static int getFONT_SIZE() {
        return fontSize;
    }


    public static Font getFONT() {
        return FONT;
    }


    public static Font getFONT_CLICKED() {
        return FONT_CLICKED;
    }


    public static int getCHARS_IN_LINE() {
        return CHARS_IN_LINE;
    }


    public static int getFactor() {
        return factor;
    }


    public static int getNUM_COLUMNS() {
        return NUM_COLUMNS;
    }
    
    
    public static int getROW_HEIGHT()
    {
        return ROW_HEIGHT;
    }
    
    
    public static boolean isLandscape()
    {
        return isLandscape;
    }


    public static int getREAL_HEIGHT() {
        if(!isLandscape)return REAL_HEIGHT;
        else return REAL_WIDTH;
    }



    public static int getREAL_WIDTH() 
    {
        if(!isLandscape) return REAL_WIDTH;
        else return REAL_HEIGHT;
    }


    
    
    
}






