package com.example.timediary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ViewTaskActivity extends Activity {

	private String TAG="TimeDiary";
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.view_task_layout);
	        String[] spinnerItemArray=getResources().getStringArray(R.array.duration_view_task_spinner_array);	        
	        Spinner durationSpinner=(Spinner)findViewById(R.id.duration_view_task_spinner);
	        ArrayAdapter<String> durationSpinnerAdapter=new ArrayAdapter<String>(this,R.layout.duration_spinner_list_item,spinnerItemArray);
	        durationSpinnerAdapter.setDropDownViewResource(R.layout.duration_spinner_list_item);
	        durationSpinner.setAdapter(durationSpinnerAdapter);
	        
	        Intent receivedIntent=getIntent();
	        int position=receivedIntent.getIntExtra("position", 0);	        
	        Cursor allTasksCursorInternal=App.mDBTools.getAllTasks();
			allTasksCursorInternal.moveToPosition(position);
			String value=allTasksCursorInternal.getString(1);
			Log.d(TAG,"String value isssss "+value);
			
	        
	    }
}
