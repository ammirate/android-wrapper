package android.widget;

import javax.swing.JComponent;

import android.view.View;

public class AdapterView {
    
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

}
