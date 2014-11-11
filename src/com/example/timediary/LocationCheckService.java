package com.example.timediary;

import java.util.ArrayList;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.IBinder;
import android.util.Log;

public class LocationCheckService extends Service {

	
	class TaskDetails{
		private int id;
		private String name;
		private Location mLocation;
		private int distance;
		
		public TaskDetails(int id,String name,Location taskLocation,int distance){
			mLocation=taskLocation;
			this.id=id;
			this.name=name;
			this.distance=distance;
		}			
		
		public String getTaskName(){
			return name;
		}
	}
	
	public static String TAG="TimeDiary";
	private ArrayList<TaskDetails> mRunningTasks=new ArrayList<TaskDetails>();
	
	private int isTaskRunning(String name){
		for(int i=0;i<mRunningTasks.size();i++){
			boolean equalStatus=mRunningTasks.get(i).getTaskName().equals(name);
			if(equalStatus)
				return i;
		}	
		return -1;
	}
	
	private	void checkAllTasksForLocation(){
		float[] results=new float[]{0f,0f,0f};
		
		LocationManagerAccess locationManagerAccess=new LocationManagerAccess();
		Location currentLocation=locationManagerAccess.getCurrentLocation();
		Location tempLocation=locationManagerAccess.getCurrentLocation();
		
		Cursor cursor=App.mDBTools.getAllTasksLocation();
		cursor.moveToFirst();
		if(cursor.getCount()>0){
			do{
				Log.d(TAG,"Values are "+cursor.getString(0)+" "+cursor.getDouble(2)+cursor.getDouble(3));
				tempLocation.setLatitude(cursor.getDouble(2));
				tempLocation.setLongitude(cursor.getDouble(3));
				int runningStatus=isTaskRunning(cursor.getString(1));
				Location.distanceBetween(currentLocation.getLatitude(), currentLocation.getLongitude(),tempLocation.getLatitude(), tempLocation.getLongitude(),results);
				if(results[0] <= cursor.getInt(4)){
					if(runningStatus>0)
						mRunningTasks.add(new TaskDetails(cursor.getInt(0),cursor.getString(1),tempLocation,cursor.getInt(4)));					
				}else{
					if(runningStatus>0)
					{
						//make an entry in database
						mRunningTasks.remove(runningStatus);
					}
				}
			}while(cursor.moveToNext());
		}
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.d(TAG,"Location Check Service called onBind");
		return null;
	}
	
	@Override
	public void onCreate(){
		Log.d(TAG,"Location Check Service called onCreate");
	}
	
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		Log.d(TAG,"Location Check Service onStartCommand");
		checkAllTasksForLocation();
		return 0;
	}

	@Override
	public void onDestroy(){
		Log.d(TAG,"Location Check Service called onDestroy");
	}
}
