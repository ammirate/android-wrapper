package android.widget;

import javax.swing.JCheckBox;


public class CheckBox extends JCheckBox implements SimpleComponent{

    private int id;
    
    public CheckBox(int id)
    {
        super();
        this.id = id;
    }
    
    public CheckBox(int id, String text)
    {
        super(text);
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

}
