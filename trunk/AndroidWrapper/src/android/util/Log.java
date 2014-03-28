package android.util;

import java.util.logging.Logger;

/**
 * 
 * @author cesarano
 *
 */
public class Log 
{
    private static Logger logger = Logger.getLogger("global");
    
    /**
     * Send a DEBUG message to the stdout
     * @param tag Used to identify the source of a log message. 
     * It usually identifies the class or activity where the log call occurs.
     * @param msg The message you would like logged.
     * @return
     */
    public static void d(String tag, String msg)
    {
        logger.info(tag + ":  " + msg);
    }

    public static void w(String tag, String string, RuntimeException mStack) {
        logger.info(tag + ":  " + string + "\n" + mStack);
    }

    public static void e(String tag, String string) {

        logger.info(tag + ":  " + string);

    }
    
}
