package com.example.timediary;

import android.app.Application;

public class CommonData {
	static Application mApplication=null;
	
	public CommonData(){
		
	}
	
	void setApplicationObject(Application app){
		mApplication=app;
	}
	
	static Application getApplicationObject(){
		return mApplication;
	}
}
