package com.syyazilim.runout.activity;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.syyazilim.runout.R;
import com.syyazilim.runout.database.DBAdapterForUser;
import com.syyazilim.runout.database.DBAdapterForUserSession;
import com.syyazilim.runout.domain.User;
import com.syyazilim.runout.domain.UserSession;

public class ResultActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ((Button) findViewById(R.id.result_save_button)).setOnClickListener(this);
        ((Button) findViewById(R.id.result_cancel_button)).setOnClickListener(this);
       
        ActionBar actionBar = getActionBar();
        actionBar.hide();


        LinearLayout linearLayoutSub = (LinearLayout) findViewById(R.id.result_linear_layout_sub);
        LinearLayout linearLayoutSuper = (LinearLayout) findViewById(R.id.result_linear_layout_super);

        ObjectAnimator colorFadeSuper = ObjectAnimator.ofObject(linearLayoutSuper, "backgroundColor", new ArgbEvaluator(), Color.argb(255, 90, 255, 250), 0xFFFFFF);
       
        colorFadeSuper.setDuration(3000);        
        colorFadeSuper.start();
       

        Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        linearLayoutSub.setVisibility(View.VISIBLE);
        linearLayoutSub.startAnimation(animFadeIn);

        Animation animMove = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        TextView resultMessageView = (TextView)findViewById(R.id.result_message);
        resultMessageView.setVisibility(View.VISIBLE);
        resultMessageView.startAnimation(animMove);
        UserSession session = (UserSession) getIntent().getExtras().getSerializable("com.syyazilim.runout.currentusersession");
        ((TextView) findViewById(R.id.calorie)).setText(session.getCalories().toString());
        ((TextView)findViewById(R.id.chronometerCounter)).setText(session.getTime().toString());
        ((TextView)findViewById(R.id.speedCounter)).setText(session.getSpeed());
        ((TextView)findViewById(R.id.distance)).setText(session.getDistance());
        ((TextView)findViewById(R.id.tempo)).setText(session.getTempo());
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	Intent intent;
		switch (item.getItemId()) {
		case 1:
			 intent = new Intent(getApplicationContext(),SessionHistoryListActivity.class);
			 startActivity(intent);
			 return true;
		case 2:
			intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		case 3:
			System.exit(0);			
			return true;
		default:
			return super.onOptionsItemSelected(item);
		
	}
    }

    @Override
    public void onClick(View view) {
        if (view != null) {
            switch (view.getId()) {
                case R.id.result_save_button:
                	saveSessionToDB();    
                	redirect(MainActivity.class);
                    break;
                case R.id.result_cancel_button:
                	redirect(MainActivity.class);                    
            }
        }
    }
    private void redirect(Class clazz){
    	DBAdapterForUser adapterForUser = new DBAdapterForUser(getApplicationContext());
    	adapterForUser.open();
    	User currentUser  = adapterForUser.getLastUser();
    	adapterForUser.close();
    	User user = new User();
    	user.setId(currentUser.getId());
    	user.setName(currentUser.getName());
    	user.setSurname(currentUser.getSurname());
    	user.setUsername(currentUser.getUsername());
        Intent intent = new Intent(getApplicationContext(), clazz);
        intent.putExtra("com.syyazilim.runout.currentuser",user);
        startActivity(intent);
    }
    
    public boolean saveSessionToDB() {
    	try {
    		 UserSession  userSession = (UserSession) getIntent().getExtras().getSerializable("com.syyazilim.runout.currentusersession");
    		 DBAdapterForUserSession adapter = new DBAdapterForUserSession(getApplicationContext());    	       
    	     adapter.open();
	         adapter.insertUserSession(userSession.getCalories(), userSession.getSpeed(), userSession.getDistance(),
	        		    userSession.getTempo(), userSession.getTime(),userSession.getDate());
	         adapter.close();      
    	} catch (Exception e) {
    		e.printStackTrace();
			return false;
		}
    	
    	return true;
    }

}
