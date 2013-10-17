package com.syyazilim.runout.activity;

import java.util.ArrayList;
import java.util.List;

import com.syyazilim.runout.database.DBAdapterForUserSession;
import com.syyazilim.runout.domain.UserSession;
import com.syyazilim.runout.utility.CustomListAdapter;
import com.syyazilim.runout.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SessionHistoryListActivity  extends Activity implements OnClickListener{
	ArrayList<UserSession> sessionHistory = new ArrayList<UserSession>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.session_history_list);
 
        sessionHistory = getSessionHistoryList();
        final ListView lv1 = (ListView) findViewById(R.id.session_history_list_view);
        lv1.setAdapter(new CustomListAdapter(this.getApplicationContext(), sessionHistory));
        lv1.setOnItemClickListener(new OnItemClickListener() { 
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                Object o = lv1.getItemAtPosition(position);
                UserSession userSession = (UserSession) o;
                //Toast.makeText(SessionHistoryListActivity.this, "Selected :" + " " + userSession, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(),SessionHistoryDetailActivity.class);
                intent.putExtra("com.syyazlim.runout.usersessiondetail", userSession);
                startActivity(intent);              
                
            }	
        });
 
    }
	
	public ArrayList<UserSession> getSessionHistoryList(){
		List<UserSession> historyList = new ArrayList<UserSession>();
		DBAdapterForUserSession adapterForUserSession = new DBAdapterForUserSession(getApplicationContext());
		adapterForUserSession.open();
		return (ArrayList<UserSession>) adapterForUserSession.getSessionHistoryList();
		
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}

}
