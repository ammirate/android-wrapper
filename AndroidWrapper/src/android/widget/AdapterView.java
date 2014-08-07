package android.widget;

import javax.swing.JComponent;

import android.view.View;

public class AdapterView<T extends Adapter> {
    
    int id;
    int position;
    View view;
    AdapterView adapter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public AdapterView getAdapter() {
        return adapter;
    }

    public void setAdapter(AdapterView adapter) {
        this.adapter = adapter;
    }

    public interface OnItemClickListener 
    {
        
        public abstract void onItemClick (AdapterView parentAdapter, View view, int position,long id);

    }
    
    
    //start_edit: IS2 project - MAEESTRO 2013/2014

    /**
     * Interface definition for a callback to be invoked when
     * an item in this view has been selected.
     */
    public interface OnItemSelectedListener {
        /**
         * <p>Callback method to be invoked when an item in this view has been
         * selected. This callback is invoked only when the newly selected
         * position is different from the previously selected position or if
         * there was no selected item.</p>
         *
         * Impelmenters can call getItemAtPosition(position) if they need to access the
         * data associated with the selected item.
         *
         * @param parent The AdapterView where the selection happened
         * @param view The view within the AdapterView that was clicked
         * @param position The position of the view in the adapter
         * @param id The row id of the item that is selected
         */
        void onItemSelected(AdapterView<?> parent, View view, int position, long id);

        /**
         * Callback method to be invoked when the selection disappears from this
         * view. The selection can disappear for instance when touch is activated
         * or when the adapter becomes empty.
         *
         * @param parent The AdapterView that now contains no selected item.
         */
        void onNothingSelected(AdapterView<?> parent);
    }
    //end_edit: IS2 project - MAEESTRO 2013/2014

}
