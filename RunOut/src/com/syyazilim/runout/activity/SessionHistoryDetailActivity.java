package com.syyazilim.runout.activity;

import com.syyazilim.runout.R;
import com.syyazilim.runout.database.DBAdapterForUser;
import com.syyazilim.runout.domain.User;
import com.syyazilim.runout.domain.UserSession;

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
import android.widget.LinearLayout;
import android.widget.TextView;

public class SessionHistoryDetailActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.session_history_row_detail);
		UserSession session = (UserSession) getIntent().getExtras().getSerializable("com.syyazlim.runout.usersessiondetail");
		((TextView) findViewById(R.id.calorie)).setText(session.getCalories().toString());
		((TextView) findViewById(R.id.chronometerCounter)).setText(session.getTime().toString());
		((TextView) findViewById(R.id.speedCounter)).setText(session.getSpeed());
		((TextView) findViewById(R.id.distance)).setText(session.getDistance());
		((TextView) findViewById(R.id.tempo)).setText(session.getTempo());

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		return super.onOptionsItemSelected(item);
	}
}
