package com.syyazilim.runout.activity;

import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener;
import com.syyazilim.runout.R;
import com.syyazilim.runout.R.id;
import com.syyazilim.runout.R.layout;
import com.syyazilim.runout.R.string;
import com.syyazilim.runout.database.DBAdapterForUser;
import com.syyazilim.runout.domain.User;
import com.syyazilim.runout.listener.CustomOnItemSelectedListener;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends Activity implements OnClickListener {
    
	DBAdapterForUser adapter ; 
	User user = new User();
	NumberPicker numberPickerForTall;
	NumberPicker numberPickerForWeight;
	boolean  userSaved = false;
	private Spinner spinner1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	 
    	adapter = new DBAdapterForUser(getApplicationContext());	
    	adapter.open();   
    	SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
    	String userloggedin = preferences.getString("userloggedin", "false");
    	 user = (User) adapter.getLastUser(); 	
    	if(user!=null){
    		 Intent intent = new Intent(getApplicationContext(),MainActivity.class);
             intent.putExtra("com.syyazilim.runout.currentuser", user);
             startActivity(intent);   
			this.finish();
		}

		super.onCreate(savedInstanceState);
		setContentView(R.layout.registration);
		((Button) findViewById(R.id.signup)).setOnClickListener(this);
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
    }
    
    public boolean saveUserToDBandSharedPreferences() {
    	try {	  
    		 String userName = ((TextView)findViewById(R.id.editText)).getText().toString();
    		 String name = ((TextView)findViewById(R.id.editText2)).getText().toString();
    		 String surname = ((TextView)findViewById(R.id.editText3)).getText().toString();
    		 String weight = ((TextView)findViewById(R.id.editText4)).getText().toString();
    		 String tall = ((TextView)findViewById(R.id.editText5)).getText().toString();
    		 String sex = ((Spinner)findViewById(R.id.spinner_sex)).getSelectedItem().toString();
    		 if(userName.equals("") || name.equals("") || surname.equals("")){
    			 Toast.makeText(getApplicationContext(), R.string.login_warning, 20).show();
    			 return false;
    		 }  
    		 user = new User();
	         user.setUsername(userName);
	         user.setName(name);
	         user.setSurname(surname);
	         user.setWeight(weight);
	         user.setTall(tall);
	         user.setSex(sex);
	         adapter.insertUser(user.getUsername(), user.getName(), user.getSurname(), user.getWeight(), user.getTall(),user.getSex());
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
    
    public void addListenerOnSpinnerItemSelection() {
    	spinner1 = (Spinner) findViewById(R.id.spinner_sex);
    	spinner1.setOnItemSelectedListener(new CustomOnItemSelectedListener());
      }


    }
    
    
   

