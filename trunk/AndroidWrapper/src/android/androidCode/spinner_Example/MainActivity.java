package android.androidCode.spinner_Example;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends Activity {
 
	Spinner spnr;
  String[] celebrities = {
      "Chris Hemsworth",
      "Jennifer Lawrence",
      "Jessica Alba",
      "Brad Pitt",
      "Tom Cruise",
      "Johnny Depp",
      "Megan Fox",
      "Paul Walker",
      "Vin Diesel"
  };
  
  @Override
  protected void onCreate(Bundle savedInstanceState) {
	  
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    spnr = (Spinner)findViewById(R.id.spinner);
    
    ArrayAdapter<String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_item, celebrities);
    spnr.setAdapter(adapter);
    spnr.setOnItemSelectedListener(
              new AdapterView.OnItemSelectedListener() {
                  @Override
                  public void onItemSelected(AdapterView<?> arg0, View arg1,
                          int arg2, long arg3) {
                    int position = spnr.getSelectedItemPosition();
                    Toast.makeText(getApplicationContext(),"You have selected "+celebrities[+position],Toast.LENGTH_LONG).show();
                      // TODO Auto-generated method stub
                  }
                  @Override
                  public void onNothingSelected(AdapterView<?> arg0) {
                      // TODO Auto-generated method stub
                  }
              }
          );
  }
}