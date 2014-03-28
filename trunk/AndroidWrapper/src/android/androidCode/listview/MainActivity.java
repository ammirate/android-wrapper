package android.androidCode.listview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends Activity {


    // Initialize the array
    String[] monthsArray = { "January", "February" , "March", "April", "May", "June","July","August","September","October","November","Dicember"};
 
    // Declare the UI components
    private ListView listView;
 
    private ArrayAdapter arrayAdapter;
    
 
    /** Called when the activity is first created. */

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Activity thisActivity = this;
 
        // Initialize the UI components
        listView = (ListView) findViewById(R.id.listView1);
        // For this moment, you have ListView where you can display a list.
        // But how can we put this data set to the list?
        // This is where you need an Adapter
 
        // context - The current context.
        // resource - The resource ID for a layout file containing a layout
                // to use when instantiating views.
        // From the third parameter, you plugged the data set to adapter
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, monthsArray);
 
        // By using setAdapter method, you plugged the ListView with adapter
        listView.setAdapter(arrayAdapter);
        
        
        listView.setOnItemClickListener(new OnItemClickListener() {
            
            public void onItemClick(AdapterView parentAdapter, View view, int position,long id) {
             // We know the View is a TextView so we can cast it
                TextView clickedView = (TextView) view;
                    
                Log.d("Month clicked",clickedView.getText().toString());
                
                Intent intent = new Intent(thisActivity,DisplayMessageActivity.class);
                
                String day = clickedView.getText().toString();
                intent.putExtra("day", day);
                startActivity(intent);

            }
        }); 
        
    }

}















