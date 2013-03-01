package enos.meditz.gym.bro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ActivitySelectWorkout extends Activity {
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_one_or_add);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new ArrayAdapter<Workout>(this, R.layout.simple_list_item, DBHelper.getWorkoutsLight(this)));
	}
	
	public void onClickAdd(View view) {
		 startActivity((new Intent(this, ActivityEditWorkout.class)).putExtra(ExtraHelper.EXTRA_WORKOUT, DBHelper.createWorkout(this)));
	}
	
	
}
