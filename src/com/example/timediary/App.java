package com.example.timediary;

import android.app.Application;
import android.content.Context;
import android.util.Log;

public class App extends Application {

	public static Context mContext;
	private String TAG="TimeDiary";
	public static DBTools mDBTools=new DBTools(mContext,null,null,1);
	
	@Override
	public void onCreate(){
		super.onCreate();
		Log.d(TAG,"onCreate method called");
		mContext=getApplicationContext();		 
		Log.d(TAG,"onCreate method called for application will call constructor now");
		
	}
	
	public Context getAppContext(){
		return getApplicationContext();
	}
}
