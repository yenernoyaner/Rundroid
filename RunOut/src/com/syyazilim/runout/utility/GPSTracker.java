package com.syyazilim.runout.utility;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
public class GPSTracker extends Service implements LocationListener {

	private final Context mContext;	
	boolean isGPSEnabled = false;	
	boolean isNetworkEnabled = false;
	boolean canGetLocation = false;

	Location currentLocation; 
	Location previousLocaiton;
	Location startLocation;
	double latitude; 
	double longitude;	
	float currentDistance;
	float currentSpeed;
	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 5 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 1 * 1; // 1 sec

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			

			if (!isGPSEnabled) {				
				//showSettingsAlert();
			} else {
				this.canGetLocation = true;						
				if (isGPSEnabled) {
					if (startLocation == null) {
						locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
								this);
						Log.d("GPS Enabled", "GPS Enabled");
						if (locationManager != null) {
							startLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (startLocation != null) {
								latitude = startLocation.getLatitude();
								longitude = startLocation.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return currentLocation;
	}

	public double getLatitude() {
		if (currentLocation != null) {
			latitude = currentLocation.getLatitude();
		}

		return latitude;
	}

	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	/**
	 * Function to show settings alert dialog
	 * */
	public void showSettingsAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);		
		alertDialog.setTitle("GPS  settings");	
		alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");       
	
		alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				mContext.startActivity(intent);
			}
		});

		alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});

		
		alertDialog.create().show();
	}
	
	 public void stopUsingGPS(){
	        if(locationManager != null){
	            locationManager.removeUpdates(GPSTracker.this);
	        }       
	    }

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (currentLocation != null) {
			longitude = currentLocation.getLongitude();
		}

		// return longitude
		return longitude;
	}

	@Override
	public void onLocationChanged(Location location) {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES,
								this);
		currentSpeed = location.getSpeed();
		currentLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		currentDistance = startLocation.distanceTo(currentLocation);
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public Location getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(Location currentLocation) {
		this.currentLocation = currentLocation;
	}

	public Location getPreviousLocaiton() {
		return previousLocaiton;
	}

	public void setPreviousLocaiton(Location previousLocaiton) {
		this.previousLocaiton = previousLocaiton;
	}

	public Location getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(Location startLocation) {
		this.startLocation = startLocation;
	}

	public float getCurrentDistance() {
		return currentDistance;
	}

	public void setCurrentDistance(float currentDistance) {
		this.currentDistance = currentDistance;
	}

	public float getCurrentSpeed() {
		return currentSpeed;
	}

	public void setCurrentSpeed(float currentSpeed) {
		this.currentSpeed = currentSpeed;
	}
	
	
	
}
