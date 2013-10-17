package com.syyazilim.runout.activity;

import com.syyazilim.runout.R;
import com.syyazilim.runout.R.id;
import com.syyazilim.runout.R.layout;
import com.syyazilim.runout.database.DBAdapterForUser;
import com.syyazilim.runout.domain.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SettingsActivity extends Activity implements OnClickListener {

	 User user ;
	 DBAdapterForUser adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        adapter = new DBAdapterForUser(getApplicationContext());
        ((Button) findViewById(R.id.settings_save_button)).setOnClickListener(this);
         user = getUserFromDb();
        ((TextView) findViewById(R.id.usernameForSettings)).setText(user.getUsername());
        ((TextView) findViewById(R.id.nameForSettings)).setText(user.getName());
        ((TextView) findViewById(R.id.surnameForSettings)).setText(user.getSurname());
        ((TextView) findViewById(R.id.weightForSettings)).setText(user.getWeight());
        ((TextView) findViewById(R.id.heightForSettings)).setText(user.getTall());
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.settings_save_button:
                	Boolean userUpdated = updateUser(user);
                	if(userUpdated){
                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                    intent.putExtra("com.syyazilim.runout.currentuser", user);
                    startActivity(intent);
                    break;
                	}
            }
        }
    }
    
    public User getUserFromDb(){   
    	user = new User();
    	adapter.open();
    	user  = adapter.getLastUser(); 
    	return user;
    }
    
    public Boolean updateUser(User user){
    	try {
    	 String userName = ((TextView)findViewById(R.id.usernameForSettings)).getText().toString();
		 String name = ((TextView)findViewById(R.id.nameForSettings)).getText().toString();
		 String surname = ((TextView)findViewById(R.id.surnameForSettings)).getText().toString();
		 String weight = ((TextView)findViewById(R.id.weightForSettings)).getText().toString();
		 String tall = ((TextView)findViewById(R.id.heightForSettings)).getText().toString();
		 if(userName.equals("") || name.equals("") || surname.equals("")){
			 Toast.makeText(getApplicationContext(), R.string.login_warning, 20).show();
			 return false;
		 }  
         user.setUsername(userName);
         user.setName(name);
         user.setSurname(surname);
         user.setWeight(weight);
         user.setTall(tall);
         adapter.updateUser(user.getId(),user.getUsername(), user.getName(), user.getSurname(), user.getWeight(), user.getTall());
         adapter.close();
    
    } catch (Exception e) {
		e.printStackTrace();
		return false;
	}
        return true;
   }
}

