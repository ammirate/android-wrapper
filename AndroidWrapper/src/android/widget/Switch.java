package android.widget;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.FlowLayout;


/**
 * This class allows the use of the Switch widget into MAEESTRO.
 * 
 * @author Antonio Cesarano
 *
 * IS2 project - MAEESTRO 2013/2014
 */ 
public class Switch extends JPanel implements SimpleComponent, Checkable{

	/**
	 * Initial state of the switch button
	 */
	private boolean state = true; // true is ON, false is OFF
	private JButton offButton, onButton;
	private OnCheckedChangeListener onCheckedChangeListener;
	private JPanel textPanel;
	private JPanel switchPanel;
	private JLabel switchText;

	/**
	 * Create the panel in which there are the two separates buttons, which simulates the switch button
	 */
	public Switch() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		Dimension d = new Dimension(WidgetProperties.getACTIVITY_WIDTH(), WidgetProperties.getROW_HEIGHT()/2);
		setSize(d);

		textPanel = new JPanel();
		add(textPanel);

		switchText = new JLabel("");
		textPanel.add(switchText);

		switchPanel = new JPanel();
		add(switchPanel);
		switchPanel.setLayout(new GridLayout(0, 2, 0, 0));

		//set to the start state the ON and OFF buttons
		offButton = new JButton("OFF");
		switchPanel.add(offButton);
		offButton.setEnabled(state ? false : true);

		onButton = new JButton("ON");
		switchPanel.add(onButton);
		onButton.setEnabled(!offButton.isEnabled()); 

		addChangeListener();
	}

	public void setChecked(boolean b) {
		onButton.setEnabled(b);
		offButton.setEnabled(!onButton.isEnabled());
		state = b;
		
		if(onCheckedChangeListener != null)
			onCheckedChangeListener.onCheckedChanged(null, b);
		
	}

	public boolean isChecked() {
		//System.out.println("Switch state: " + state);
		return state;
	}



	public void setOnCheckedChangeListener(OnCheckedChangeListener occl) {
		onCheckedChangeListener = occl;

	}


	void addChangeListener(){
		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setChecked(!isChecked());
			}
		};
		onButton.addActionListener(l);
		offButton.addActionListener(l);
	}

	@Override
	public void toggle() {
		setChecked(!isChecked());
	}



	/**
	 * Interface definition for a callback to be invoked when the checked state
	 * of a compound button changed.
	 */
	public interface OnCheckedChangeListener {
		/**
		 * Called when the checked state of a compound button has changed.
		 *
		 * @param buttonView The compound button view whose state has changed.
		 * @param isChecked  The new checked state of buttonView.
		 */
		public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) ;
	}
	
	public void setText(String text){
		this.switchText.setText(text);
	}
	
	public String getText(){
		return switchText.getText();
	}

}




