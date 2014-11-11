package com.example.timediary;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class ViewAllTasksActivity extends Activity {

	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mDrawerMenuOptionsArray;
	public String TAG="TimeDiary";
	LocationManagerAccess mLocationManagerAccess=null;
	private ListView mActivitiesList;
	
	public static PendingIntent mPendingIntent;
	public Intent mLocationListenerIntent;
	public static AlarmManager mAlarmManager;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.view_all_tasks_layout);
	        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
	        mDrawerList=(ListView)findViewById(R.id.left_drawer);
	        mDrawerMenuOptionsArray=getResources().getStringArray(R.array.nav_drawer_menu_options);
	        mDrawerList.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_list_item,mDrawerMenuOptionsArray));
	        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,GravityCompat.START);
	        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
	           
	        mLocationListenerIntent=new Intent(this,AlarmLocationListener.class);
	        mLocationListenerIntent.setAction("com.example.timediary.alarmlisteneraction");
	        mPendingIntent=PendingIntent.getBroadcast(this,111,mLocationListenerIntent,0);
	        mAlarmManager=(AlarmManager)getSystemService(ALARM_SERVICE);
	        
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTimeInMillis(System.currentTimeMillis());
	        mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 60*1000, mPendingIntent);
	        
	        getActionBar().setDisplayHomeAsUpEnabled(true);
	        getActionBar().setHomeButtonEnabled(true);
	     // ActionBarDrawerToggle ties together the the proper interactions
	        // between the sliding drawer and the action bar app icon
	        mDrawerToggle = new ActionBarDrawerToggle(
	                this,                  /* host Activity */
	                mDrawerLayout,         /* DrawerLayout object */
	                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
	                R.string.drawer_open,  /* "open drawer" description for accessibility */
	                R.string.drawer_close  /* "close drawer" description for accessibility */
	                ) {
	            public void onDrawerClosed(View view) {
	                //getActionBar().setTitle(mTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }

	            public void onDrawerOpened(View drawerView) {
	                //getActionBar().setTitle(mDrawerTitle);
	                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
	            }
	        };

	        CommonData cdata=new CommonData();
	        cdata.setApplicationObject(getApplication());
	    }
	 	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        MenuInflater inflater = getMenuInflater();
	        inflater.inflate(R.menu.main, menu);
	        return super.onCreateOptionsMenu(menu);
	    }

	    /* Called whenever we call invalidateOptionsMenu() */
	    @Override
	    public boolean onPrepareOptionsMenu(Menu menu) {
	        // If the nav drawer is open, hide action items related to the content view
	        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
	        return super.onPrepareOptionsMenu(menu);
	    }

	    @Override
	    public boolean onOptionsItemSelected(MenuItem item) {
	         // The action bar home/up action should open or close the drawer.
	         // ActionBarDrawerToggle will take care of this.
	        if (mDrawerToggle.onOptionsItemSelected(item)) {
	            return true;
	        }
	        // Handle action buttons
	        switch(item.getItemId()) {
	        	
	        default:
	            return super.onOptionsItemSelected(item);
	        }
	    }
	 
	  /* The click listner for ListView in the navigation drawer */
	    private class DrawerItemClickListener implements ListView.OnItemClickListener {
	        @Override
	        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        	Log.d(TAG,"onItemClick "+ position);
	        	
	        mDrawerLayout.closeDrawers();
	        Intent myIntent;
	        switch(position){	
	        case 0:
	        	myIntent=new Intent(ViewAllTasksActivity.this,AddTaskActivity.class);	        	
	        	startActivity(myIntent);
	        	break;
	        case 1:
	        	myIntent=new Intent(ViewAllTasksActivity.this,AddTimerActivity.class);	        	
	        	startActivity(myIntent);	        	
	        	break;
	        case 2: 
	        	myIntent=new Intent(ViewAllTasksActivity.this,ViewSummaryActivity.class);	        	
	        	startActivity(myIntent);	        	
	        	break;
	        default:
	        }		
	        }
	    }
	    @Override
	    protected void onResume(){
	    	super.onResume();
	    	Log.d(TAG,"onResume called");
	    	Cursor allTasksCursor=App.mDBTools.getAllTasks();
	    	Log.d(TAG,"count is "+allTasksCursor.getCount());
	    	if(allTasksCursor.getCount()>0){
	    		allTasksCursor.moveToFirst();
	    		do{
	    			Log.d(TAG,"Task Name is "+allTasksCursor.getString(0)+allTasksCursor.getString(1));
	    		}while(allTasksCursor.moveToNext());
	    	
	    		   String[] columns=new String[]{
	   	   	        	"taskname"	
	   	   	        };
	   	   	        
	   	   	        int[] to=new int[]{
	   	   	        	R.id.activitylist_listitem_text	
	   	   	        };
	   	   	        
	   	    	SimpleCursorAdapter activitiesListCursorAdapter=new SimpleCursorAdapter(this,R.layout.view_allactivities_listitem,allTasksCursor,columns,to,0);
	   	    	mActivitiesList=(ListView)findViewById(R.id.alltasks_listview);
	   	    	mActivitiesList.setAdapter(activitiesListCursorAdapter);
	   	    	mActivitiesList.setOnItemClickListener(new OnItemClickListener(){

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						
						
						Intent viewActivityIntent=new Intent(ViewAllTasksActivity.this,ViewTaskActivity.class);
						viewActivityIntent.putExtra("position", position);
						startActivity(viewActivityIntent);	
					}
	   	    		
	   	    	});
	    		
	    	}
	    	if(mLocationManagerAccess == null)
	    	mLocationManagerAccess=new LocationManagerAccess();	
	    }
	    
	    /**
	     * When using the ActionBarDrawerToggle, you must call it during
	     * onPostCreate() and onConfigurationChanged()...
	     */

	    @Override
	    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

	    @Override
	    public void onConfigurationChanged(Configuration newConfig) {
	        super.onConfigurationChanged(newConfig);
	        // Pass any configuration change to the drawer toggls
	        mDrawerToggle.onConfigurationChanged(newConfig);
	    }
}
