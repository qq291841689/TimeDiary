package com.example.timediary;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class AlarmLocationListener extends BroadcastReceiver {

	private static String TAG="TimeDiary";
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onReceive received for Location Listener starting service");
		Intent alarmService=new Intent(arg0,LocationCheckService.class);
		arg0.startService(alarmService);
	}

}
