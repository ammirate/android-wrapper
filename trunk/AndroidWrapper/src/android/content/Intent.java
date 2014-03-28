package android.content;

import java.util.HashMap;

import android.androidCode.activities.DisplayMessageActivity;
import android.app.Activity;


/**
 * An intent is a description of an operation to be performed.
 * It is used to contain the informations to start a new Activity
 * @author Antonio Cesarano
 *
 */
public class Intent {

    /**
     * The Activity who want start a new one
     */
    private Activity caller;
    
    /**
     * The Activity to create and rin
     */
    private Class activityTarget;
    
    /**
     * A map of messages
     */
    private HashMap<String,String> messages;
    
    
    private Context context;
    
    /**
     * Create an Intent with the caller and target classes
     * @param mainActivity the activity who want start a new activity
     * @param target the new activity to run
     */
    public Intent(Context context, Class<?> target) 
    {
        this.context = context;
        this.activityTarget = target;
        this.messages = new HashMap<String, String>();
        
    }

    /**
     * Puts an entry <Extramessage,message> in the messages map
     */
    public void putExtra(String extraMessage, String message) {
        messages.put(extraMessage, message);
    }

    
    /**
     * Gets the message with key extraMessage
     * @param extraMessage key of the message
     * @return the message with key extraMessage
     */
    public String getStringExtra(String extraMessage) {
        return messages.get(extraMessage);
    }

    /**
     * Returns the activity caller
     * @return the activity caller
     */
    public Activity getCaller() {
        return caller;
    }

    /**
     * Sets a new Activity as caller
     * @param caller the new caller activity
     */
    public void setCaller(Activity caller) {
        this.caller = caller;
    }

    /**
     * Returns the activity target
     * @return the activity target
     */
    public Class getActivityTarget() {
        return activityTarget;
    }

    /**
     * Sets a new Activity as caller
     * @param activityTarget the new target activity
     */
    public void setActivityTarget(Class activityTarget) {
        this.activityTarget = activityTarget;
    }


}
