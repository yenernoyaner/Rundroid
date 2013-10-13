package com.syyazilim.runout.activity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Chronometer.OnChronometerTickListener;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.internal.view.menu.ActionMenuView;
import com.actionbarsherlock.internal.widget.IcsSpinner;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.syyazilim.runout.R;
import com.syyazilim.runout.R.anim;
import com.syyazilim.runout.R.id;
import com.syyazilim.runout.R.layout;
import com.syyazilim.runout.R.raw;
import com.syyazilim.runout.domain.User;
import com.syyazilim.runout.domain.UserSession;
import com.syyazilim.runout.utility.Distance;
import com.syyazilim.runout.utility.GPSTracker;

public class MainActivity extends SherlockActivity implements View.OnClickListener {
     
	private Handler handler = new Handler();
	private AlertDialog countDownDialog;
    private UserSession userSession;
	private Chronometer chronometer;
	private long timeWhenStopped = 0;
	private int resultCode = 1;
	Distance distance = new Distance();
	GPSTracker gps;

	static final String[] COUNTRIES = new String[] { "Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",
			"Canada", "France", "Spain" };

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		User user = (User) getIntent().getExtras().getSerializable("com.syyazilim.runout.currentuser");
		Toast toast = Toast.makeText(getApplicationContext(), "Hi, " + user.getUsername(), 10);
		toast.setGravity(Gravity.TOP | Gravity.LEFT, 0, 200);
		toast.show();

		chronometer = (Chronometer) findViewById(R.id.chronometerCounter);
		chronometer.setFormat("00:00:00");
		chronometer.setOnChronometerTickListener(new OnChronometerTickListener() {
			@Override
			public void onChronometerTick(Chronometer cArg) {
				long t = SystemClock.elapsedRealtime() - cArg.getBase();
				int h = (int) (t / 3600000);
				int m = (int) (t - h * 3600000) / 60000;
				int s = (int) (t - h * 3600000 - m * 60000) / 1000;
				String hh = h < 10 ? "0" + h : h + "";
				String mm = m < 10 ? "0" + m : m + "";
				String ss = s < 10 ? "0" + s : s + "";
				cArg.setText(hh + ":" + mm + ":" + ss);

			}
		});
		chronometer.setBase(SystemClock.elapsedRealtime());
		getActionBar().setHomeButtonEnabled(true);

		((Button) findViewById(R.id.main_start_button)).setOnClickListener(this);
		((Button) findViewById(R.id.main_stop_button_pause)).setOnClickListener(this);
		((Button) findViewById(R.id.main_stop_button_resume)).setOnClickListener(this);
		((Button) findViewById(R.id.main_resume_button)).setOnClickListener(this);
		((Button) findViewById(R.id.main_pause_button)).setOnClickListener(this);

		LinearLayout linearLayoutSub = (LinearLayout) findViewById(R.id.main_linear_layout_sub);

		ActionBar actionBar = getActionBar();

		Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
		linearLayoutSub.setVisibility(View.VISIBLE);
		linearLayoutSub.startAnimation(animFadeIn);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("MENU");
		sub.add(0, 1, 0, "Settings");
		sub.add(0, 2, 0, "Log Out");
		sub.getItem().setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		switch (item.getItemId()) {
		case 1:
			Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
			startActivity(intent);
			return true;
		case 2:
			System.exit(1);
			return true;
		}
		return false;
	}

	@Override
	public void onClick(View view) {
		if (view != null) {
			switch (view.getId()) {
			case R.id.main_start_button:
				gps = new GPSTracker(MainActivity.this);
				if (gps.canGetLocation()) {
					Toast.makeText(getApplicationContext(), gps.getStartLocation().getLatitude() + "," + gps.getStartLocation().getLongitude() + "",
							5).show();
				} else {
					gps.showSettingsAlert();
				}
				if (gps.canGetLocation()) {		
					MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.countdown);
					mp.start();	
					redirect(CountDownActivity.class);
					handler.postDelayed(new Runnable() {
						
			            @Override
			            public void run() {		            	
			            	chronometer.setBase(SystemClock.elapsedRealtime());
							chronometer.start();	
			                
			            }
			        },3000);
					

					view.setVisibility(View.INVISIBLE);
					((Button) findViewById(R.id.main_stop_button_pause)).setVisibility(View.VISIBLE);
					((Button) findViewById(R.id.main_stop_button_resume)).setVisibility(View.VISIBLE);
					((Button) findViewById(R.id.main_pause_button)).setVisibility(View.VISIBLE);
					((Button) findViewById(R.id.main_resume_button)).setVisibility(View.INVISIBLE);

					((TextView) findViewById(R.id.speedCounter)).setText(String.valueOf(gps.getCurrentDistance()));
					((TextView) findViewById(R.id.textView6)).setText(String.valueOf(gps.getCurrentSpeed()));

				}
				break;
			case R.id.main_stop_button_pause:
				chronometer.stop();
				timeWhenStopped = 0;
				chronometer.setBase(timeWhenStopped);
				((Button) findViewById(R.id.main_start_button)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_pause_button)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_resume)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_pause)).setVisibility(View.INVISIBLE);
				break;
			case R.id.main_stop_button_resume:
				chronometer.stop();
				timeWhenStopped = 0;
				chronometer.setBase(timeWhenStopped);
				((Button) findViewById(R.id.main_start_button)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_pause_button)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_resume)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_pause)).setVisibility(View.INVISIBLE);
				userSession = new UserSession();
				userSession.setDistance(String.valueOf(gps.getCurrentDistance()));
				userSession.setSpeed(String.valueOf(gps.getCurrentSpeed()));
				userSession.setTime(String.valueOf(chronometer.getBase()));
				redirect(ResultActivity.class);
				break;
			case R.id.main_pause_button:
				chronometer.stop();
				timeWhenStopped = chronometer.getBase() - SystemClock.elapsedRealtime();
				((Button) findViewById(R.id.main_resume_button)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_pause_button)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_pause)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_stop_button_resume)).setVisibility(View.VISIBLE);
				break;
			case R.id.main_resume_button:
				chronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
				chronometer.start();
				((Button) findViewById(R.id.main_pause_button)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_resume_button)).setVisibility(View.INVISIBLE);
				((Button) findViewById(R.id.main_stop_button_pause)).setVisibility(View.VISIBLE);
				((Button) findViewById(R.id.main_stop_button_resume)).setVisibility(View.VISIBLE);
			}
		}
	}

	private void redirect(Class clazz) {
		Intent intent = new Intent(getApplicationContext(), clazz);
		intent.putExtra("com.syyazilim.runout.currentusersession", userSession);
		startActivity(intent);
	}

	private void countDown(final TextView tv, final int count) {
		if (tv != null) {
			if (count == 0) {
				tv.setText(""); // Note: the TextView will be visible again here.
				if (countDownDialog != null) {
					// countDownDialog.hide();
					countDownDialog.dismiss();
				}
				return;
			}
			tv.setText(count + "");
			AlphaAnimation animation = new AlphaAnimation(1.0f, 0.0f);
			animation.setDuration(1000);
			animation.setAnimationListener(new Animation.AnimationListener() {
				@Override
				public void onAnimationStart(Animation animation) {

				}

				public void onAnimationEnd(Animation anim) {
					countDown(tv, count - 1);
				}

				@Override
				public void onAnimationRepeat(Animation animation) {

				}
			});

			tv.startAnimation(animation);
			// showCountDownDialog(count + "");

		}
	}

	private void showCountDownDialog(View dialogView, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView);
		builder.setInverseBackgroundForced(true);
		countDownDialog = builder.create();
		countDownDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		countDownDialog.show();
	}

	private void showCountDownDialog(View dialogView) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setView(dialogView);
		builder.setInverseBackgroundForced(true);
		countDownDialog = builder.create();
		TextView textView = new TextView(this);
		countDownDialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		countDownDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		countDownDialog.show();
	}
	
	@Override
	  public void onBackPressed() {
	   super.onBackPressed();	   
	   }
	
		
}
