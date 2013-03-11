package enos.meditz.gym.bro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivityExerciseList extends FragmentActivity {
	private Workout workout;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_list);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		workout = DBHelper.getWorkout(this, getIntent().getLongExtra(ExtraHelper.EXTRA_WORKOUT, -1));
		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new ArrayAdapter<Exercise>(this, R.layout.simple_list_item, DBHelper.getExercises(this, workout)));
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        // Do something in response to the click
		    	Intent intent = new Intent(ActivityExerciseList.this, ActivityExercise.class);
		    	intent.putExtra(ExtraHelper.EXTRA_EXERCISE, ((Exercise) listView.getItemAtPosition(position)).id);
		    	startActivity(intent);
		    }
		}); 
	}
	
	public void onClickEdit(View view) {
		 startActivity((new Intent(this, ActivityEditWorkout.class)).putExtra(ExtraHelper.EXTRA_WORKOUT, workout.id));
	}
}
