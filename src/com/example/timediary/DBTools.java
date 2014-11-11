package com.example.timediary;

import java.sql.Date;
import java.util.HashMap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBTools extends SQLiteOpenHelper{

	static int idCounter=0;
	public String TAG="TimeDiary";
	public DBTools(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		Log.d(TAG,"DBTools constructor called");
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String query="CREATE TABLE tasks(taskid NUMBER, taskname TEXT,latitude REAL,longitude REAL,distance NUMBER)"; //category TEXT, 
		db.execSQL(query);		
		query="CREATE TABLE timediaryentries(taskid NUMBER,started_at DATETIME,ended_at DATETIME)"; //category TEXT,
		db.execSQL(query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		String query="DROP TABLE IF EXISTS tasks";
		db.execSQL(query);
		query="DROP TABLE IF EXISTS timediaryentries";
		db.execSQL(query);
		onCreate(db);		
	}
	
	public void addNewTask(String taskName,double latitude,double longitude,int distance){
		SQLiteDatabase database=this.getWritableDatabase();
		ContentValues mContentValues=new ContentValues();
		mContentValues.put("taskid", idCounter++);
		mContentValues.put("taskname", taskName);
		mContentValues.put("latitude", latitude);
		mContentValues.put("longitude", longitude);
		mContentValues.put("distance", distance);		
		long ret=database.insert("tasks", null, mContentValues);
	}

	public Cursor getAllTasks(){
		String selectQuery="SELECT taskid _id,taskname FROM tasks";
		SQLiteDatabase database=this.getReadableDatabase();
		Cursor mCursor=database.rawQuery(selectQuery,null);
		return mCursor;
	}
	
	public Cursor getAllTasksLocation(){
		String selectQuery="SELECT taskid _id,taskname,latitude,longitude,distance FROM tasks";
		SQLiteDatabase database=this.getReadableDatabase();
		Cursor mCursor=database.rawQuery(selectQuery,null);
		return mCursor;		
	}
	
	public void makeNewTimeDiaryEntry(int id,Date started_at,Date ended_at){
		SQLiteDatabase database=this.getWritableDatabase();
		ContentValues mContentValues=new ContentValues();
		mContentValues.put("taskid", id);
		mContentValues.put("started_at", started_at.toString());
		mContentValues.put("ended_at",ended_at.toString());
		long ret=database.insert("timediaryentries", null, mContentValues);
	}
	
	public Cursor getAllEntriesForTaskId(int id){
		String selectQuery="SELECT taskid _id,started_at,ended_at FROM timediaryentries WHERE id='"+id+"'";
		SQLiteDatabase database=this.getReadableDatabase();
		Cursor mCursor=database.rawQuery(selectQuery,null);
		return mCursor;
	}
}
