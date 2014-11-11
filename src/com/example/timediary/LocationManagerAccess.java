package com.example.timediary;

import android.app.Application;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class LocationManagerAccess implements LocationListener {

	private String TAG="TimeDiary";
	private LocationManager mLocationManager;
	private String provider;
	static Location currentLocation=null;
	public LocationManagerAccess(){
		Log.d(TAG,"constructor called for location manager");
	}
	
	public Location getCurrentLocation(){
				
		Application app=CommonData.getApplicationObject();
		mLocationManager=(LocationManager)app.getApplicationContext().getSystemService(app.getApplicationContext().LOCATION_SERVICE);
		Criteria mCriteria=new Criteria();
		
		provider=mLocationManager.getBestProvider(mCriteria, false);
		currentLocation=mLocationManager.getLastKnownLocation(provider);
		return currentLocation;
		
	}
	
	@Override
	public void onLocationChanged(Location arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onLocationChanged calledddd");
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onProviderDisabled called");
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

}
