package android.widget;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.BadLocationException;

import android.app.Activity;
import android.content.Context;
import android.widget.AdapterView.OnItemSelectedListener;


/**
 * 
 * @author Antonio Cesarano
 * 
 *IS2 project - MAEESTRO 2013/2014
 */
public class Spinner extends JComboBox<String> implements SimpleComponent{
	
    private static final String TAG = "Spinner";

    // Only measure this many items to get a decent max width.
    private static final int MAX_ITEMS_MEASURED = 15;

    /**
     * Use a dialog window for selecting spinner options.
     */
    public static final int MODE_DIALOG = 0;

    /**
     * Use a dropdown anchored to the Spinner for selecting spinner options.
     */
    public static final int MODE_DROPDOWN = 1;
    
    /**
     * Use the theme-supplied value to select the dropdown mode.
     */
    private static final int MODE_THEME = -1;
    
    private Context context;
    private int mode;
    private ArrayAdapter<String> adapter;
    private OnItemSelectedListener onItemSelectedListener;

    /**
     * Construct a new spinner with the given context's theme.
     *
     * @param context The Context the view is running in, through which it can
     *        access the current theme, resources, etc.
     */
    public Spinner(Context context) {
        super();    
        this.context = context;
    }
    
    
    public Spinner(Context context, int mode) {
    	super();
        this.context = context;    
        this.mode = mode;
    }
    
    
	public void setAdapter(ArrayAdapter<String> adapter_state) {
		adapter = adapter_state;		
		
		
		
		for(int i=0; i< adapter.getCount(); i++){
			super.addItem(adapter.getItem(i).toString());
		}
		
	}

	public void setOnItemSelectedListener(OnItemSelectedListener listener) {
		this.onItemSelectedListener = listener;
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				onItemSelectedListener.onItemSelected(null, null, getSelectedIndex(), arg0.getID());
			}
		});
	}
	

	public void setSelection(int position) {
		this.setSelectedIndex(position);
	}


	public int getSelectedItemPosition() {
		return super.getSelectedIndex();
	}
	

}
