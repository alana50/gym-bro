package enos.meditz.gym.bro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActivitySelectWorkout extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_workout);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new ArrayAdapter<Workout>(this, R.layout.simple_list_item, DBHelper.getWorkoutsLight(this)));
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        // Do something in response to the click
		    	Intent intent = new Intent(ActivitySelectWorkout.this, ActivityExerciseList.class);
		    	intent.putExtra(ExtraHelper.EXTRA_WORKOUT, ((Workout) listView.getItemAtPosition(position)).id);
		    	startActivity(intent);
		    }
		}); 
	}
	
	public void onClickCreate(View view) {
		 startActivity((new Intent(this, ActivityEditWorkout.class)).putExtra(ExtraHelper.EXTRA_WORKOUT, DBHelper.createWorkout(this)));
	}
}
