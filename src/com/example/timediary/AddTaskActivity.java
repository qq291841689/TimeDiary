package com.example.timediary;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskActivity extends Activity implements OnClickListener {

	String TAG="TimeDiary";
	EditText newTask_editText;
	EditText distance_editText;
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.add_task_layout);
	        Button saveButton=(Button)findViewById(R.id.save_addtask_button);
	        Button cancelButton=(Button)findViewById(R.id.cancel_addtask_button);
	        saveButton.setOnClickListener(this);
	        cancelButton.setOnClickListener(this);
	    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.save_addtask_button:
			newTask_editText=(EditText)findViewById(R.id.addtask_editText);
			distance_editText=(EditText)findViewById(R.id.distance_editText);
			String editTextTaskName=newTask_editText.getText().toString();
			LocationManagerAccess mLocationManagerAccess=new LocationManagerAccess();
			Location currentLocation=mLocationManagerAccess.getCurrentLocation();
			App.mDBTools.addNewTask(editTextTaskName,currentLocation.getLatitude(),currentLocation.getLongitude(),Integer.parseInt(distance_editText.getText().toString()));
			finish();
			break;
		case R.id.cancel_addtask_button:
			finish();
			break;
		}
	}
}
