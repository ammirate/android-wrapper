package android.widget;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JComponent;

public class RadioGroup extends ButtonGroup implements ComplexComponent
{
    private HashMap<Integer,AndroidWidget> map;
    private int id;
    private String name;
    
    public RadioGroup()
    {
        super();
        map = new HashMap<Integer, AndroidWidget>();
    }
    
    
    public RadioGroup(Object Context)
    {
        
    }
    

    public int getId() {
        return id;
    }
    
    public void setId(int id){
        this.id = id;
    }


    /**
     * 
     */
    public List<AndroidWidget> getChildren() {
        
        Enumeration<AbstractButton> enumeration = this.getElements();
        List<AndroidWidget> toReturn = new ArrayList<AndroidWidget>();
        
        while(enumeration.hasMoreElements()){
            toReturn.add((AndroidWidget) enumeration.nextElement());
        }
        return toReturn;
    }

    
    /**
     * Add a RadioButton component to the ButtonGroup
     * @param child the RadioButton component to add into the RadioGroup
     * @param index is the index of the RadioButton
     * @param params list of params for RadioButton (Not implemented yet)
     */
    public void addView (Object child, int index, Object params) 
    {
        if(child instanceof RadioButton)
        {
            super.add((AbstractButton) child);
            map.put(index, (AndroidWidget) child);
        }
    }
    
    
    /**
     * Checks the RadioButton whit the id equal to the value in input
     * @param id id of the RadioButton to check
     */
    public void check (int id)
    {
        super.setSelected((ButtonModel) map.get(id), true);
    }
    
    
    public void add(Object button)
    {
        super.add((AbstractButton) button);
    }


    public void setName(String androidId) 
    {
        this.name = name;
    }
    
    public String getName()
    {
        return this.name;
    }

}
