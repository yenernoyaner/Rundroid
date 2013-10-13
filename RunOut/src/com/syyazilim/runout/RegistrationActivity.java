package com.syyazilim.runout;



import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Chronometer.OnChronometerTickListener;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

//import com.google.android.gms.common.ConnectionResult;
//import com.google.android.gms.common.GooglePlayServicesClient;

public class RegistrationActivity extends Activity implements OnClickListener {
    
	DBAdapterForUser adapter ; 
	User user = new User();
	NumberPicker numberPickerForTall;
	NumberPicker numberPickerForWeight;
	boolean  userSaved = false;
	

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 
    	adapter = new DBAdapterForUser(getApplicationContext());	
    	adapter.open();   
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String userloggedin = preferences.getString("userloggedin", "false");
    	if(userloggedin.equals("true")){
    		 user = (User) adapter.getLastUser(); 	
    		 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
             intent.putExtra("com.syyazilim.runout.currentuser", user);
             startActivity(intent);   
    	}
    	
    	 super.onCreate(savedInstanceState);      
        setContentView(R.layout.registration); 
             
      
        ((Button) findViewById(R.id.signup)).setOnClickListener(this);
        /*numberPickerForTall.setMinValue(100);
        numberPickerForTall.setMaxValue(230);
        numberPickerForWeight.setMinValue(35);
        numberPickerForWeight.setMaxValue(150);*/
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.signup:     
                    userSaved = saveUserToDBandSharedPreferences();
                    if(userSaved){  
                    	 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                         intent.putExtra("com.syyazilim.runout.currentuser", user);
                         startActivity(intent);                   
                    }
                    break;
            }
        }
    }
    
    @Override 
    public void onStop(){
    	super.onStop();
    	/*((TextView)findViewById(R.id.editText)).setText("");
    	((TextView)findViewById(R.id.editText2)).setText("");
    	((TextView)findViewById(R.id.editText3)).setText("");
    	((TextView)findViewById(R.id.editText4)).setText("");
    	((TextView)findViewById(R.id.editText5)).setText("");  */ 
    	  adapter.close();
    }
    
    public boolean saveUserToDBandSharedPreferences() {
    	try {	  
    		 String userName = ((TextView)findViewById(R.id.editText)).getText().toString();
    		 String name = ((TextView)findViewById(R.id.editText2)).getText().toString();
    		 String surname = ((TextView)findViewById(R.id.editText3)).getText().toString();
    		 String weight = ((TextView)findViewById(R.id.editText4)).getText().toString();
    		 String tall = ((TextView)findViewById(R.id.editText5)).getText().toString();
    		 if(userName.equals("") || name.equals("") || surname.equals("")){
    			 Toast.makeText(getApplicationContext(), R.string.login_warning, 20).show();
    			 return false;
    		 }  
	         user.setUsername(((TextView)findViewById(R.id.editText)).getText().toString());
	         user.setName(((TextView)findViewById(R.id.editText2)).getText().toString());
	         user.setSurname(((TextView)findViewById(R.id.editText3)).getText().toString());
	         user.setWeight(((TextView)findViewById(R.id.editText4)).getText().toString());
	         user.setTall(((TextView)findViewById(R.id.editText5)).getText().toString());
	         adapter.insertUser(user.getUsername(), user.getName(), user.getSurname(), user.getWeight(), user.getTall());
	         adapter.close();
	         SharedPreferences sharedPreferences = PreferenceManager
                     .getDefaultSharedPreferences(this);
             Editor editor = sharedPreferences.edit();
             editor.putString("userloggedin", "true");
             editor.commit();   
	       
    	} catch (Exception e) {
    		e.printStackTrace();
			return false;
		}
    	
    	return true;
    }
    }
    
    
   

