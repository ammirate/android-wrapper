package android.androidCode.aicas;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {
    
    String[] options = { "JamaicaVM", "JamaicaCAR", "VeriFlux",  "About Us"};
 
    private ListView listView;
    private ArrayAdapter arrayAdapter;

    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        final Activity thisActivity = this;
        
        listView = (ListView) findViewById(R.id.listView1);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, options);
        listView.setAdapter(arrayAdapter);
        /*
        listView.setOnItemClickListener(new OnItemClickListener() 
        {
            
            public void onItemClick(AdapterView parentAdapter, View view, int position,long id) {
              
                TextView clickedView = (TextView) view;
                    
                Log.d("Clicked",clickedView.getText().toString());
                Intent intent = null;
                
                if(clickedView.getText().equals("JamaicaVM"))
                {
                    intent = new Intent(thisActivity,JamaicavmActivity.class);
                }
                else if(clickedView.getText().equals("JamaicaCAR"))
                {
                    intent = new Intent(thisActivity,JamaicaCarActivity.class);
                }
                else if(clickedView.getText().equals("VeriFlux"))
                {
                    intent = new Intent(thisActivity,VerifluxActivity.class);
                }
                else if(clickedView.getText().equals("About Us"))
                {
                    intent = new Intent(thisActivity,AboutUsActivity.class);
                }
                startActivity(intent);

            }
        });
         */
        OnItemClickListener l = new OnItemClickListener() {
            
            @Override
            public void onItemClick(AdapterView parentAdapter, View view, int position,long id) {

                TextView clickedView = (TextView) view;
                
                Log.d("Clicked",clickedView.getText().toString());
                Intent intent = null;
                
                if(clickedView.getText().equals("JamaicaVM"))
                {
                    intent = new Intent(thisActivity,JamaicavmActivity.class);
                }
                else if(clickedView.getText().equals("JamaicaCAR"))
                {
                    intent = new Intent(thisActivity,JamaicaCarActivity.class);
                }
                else if(clickedView.getText().equals("VeriFlux"))
                {
                    intent = new Intent(thisActivity,VerifluxActivity.class);
                }
                else if(clickedView.getText().equals("About Us"))
                {
                    intent = new Intent(thisActivity,AboutUsActivity.class);
                }
                startActivity(intent);                
            }
        };
        listView.setOnItemClickListener(l);
        
    }



}
