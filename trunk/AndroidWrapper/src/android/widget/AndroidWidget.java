package android.widget;

/**
 * Interface used to abstract from any Widget 
 * @author cesarano
 *
 */
public interface AndroidWidget{
    
    /**
     * Each widget in Android has an Id used to be recognized in event management system
     * @return
     */
    public int getId();
}
