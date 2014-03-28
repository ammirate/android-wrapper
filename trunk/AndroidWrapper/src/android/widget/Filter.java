package android.widget;

import java.util.ArrayList;


public class Filter<T> 
{

    public class FilterResults
    {
        
        public ArrayList<T> values;
        public int count;

        public FilterResults()
        {
            
        }
    }

    protected FilterResults performFiltering(CharSequence prefix) 
    {
       
        return null;
    }
    
}
