package enos.meditz.gym.bro;

import java.util.ArrayList;
import java.util.List;

import enos.meditz.gym.bro.ActivityExerciseList.AddExerciseDialogFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.CheckBox;

public class ActivityEditWorkout extends Activity {
	private List<Exercise> selectedExercises = new ArrayList<Exercise>();
	private Workout workout;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		workout = DBHelper.getWorkout(this, getIntent().getLongExtra(ExtraHelper.EXTRA_WORKOUT, -1));
		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new CheckExerciseAdapter(this, R.layout.row_check, DBHelper.getExercises(this)));
	}
	
	private class CheckExerciseAdapter extends ArrayAdapter<Exercise> {
		private ArrayList<Exercise> exercises;

		public CheckExerciseAdapter(Context context, int textViewResourceId, ArrayList<Exercise> items) {
			super(context, textViewResourceId, items);
			exercises = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row_check, null);
			}
			Exercise e = exercises.get(position);
			CheckBox cb = (CheckBox) v.findViewById(R.id.check);
			cb.setTag(e);
			TextView tv = (TextView) v.findViewById(R.id.label);
			tv.setText(e.name);
			//if(DBHelper.contains(ActivityEditWorkout.this, workout, e)) {
				
			//}
			return v;
		}
	}
	
	public void onCheckBoxClick(View v) {
		CheckBox cb = (CheckBox) v;
		if(cb.isChecked()) {
			selectedExercises.add((Exercise) cb.getTag());
		} else {
			selectedExercises.remove(cb.getTag());
		}
	}
	
	public void onClickAddExercise(View v) {
		 (new AddExerciseDialogFragment()).show(getFragmentManager(), "new_exercise");
	}
	
	public static class AddExerciseDialogFragment extends DialogFragment {
	    
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_add_exercise, null))
	        	// Add action buttons
	               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   ActivityEditWorkout activity = (ActivityEditWorkout) getActivity();
	                	   DBHelper.addExercise(activity, ((EditText) getDialog().findViewById(R.id.new_exercise_name)).getText().toString());
	                	   activity.onResume();
	                   }
	               })
	               .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                       getDialog().cancel();
	                   }
	               });      
	        return builder.create();
	    }
	}
	
	public void onClickCreate(View v) {
		
	}
}
