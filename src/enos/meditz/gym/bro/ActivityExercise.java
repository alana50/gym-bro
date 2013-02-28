package enos.meditz.gym.bro;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class ActivityExercise extends Activity {
	private Exercise exercise;
	private ArrayList<Set> today = new ArrayList<Set>();
	private ArrayList<Set> past = new ArrayList<Set>();
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		Intent intent = getIntent();
		exercise = DBHelper.getExercise(this, intent.getLongExtra(ExtraHelper.EXTRA_EXERCISE, -1));
		organizeSets();

		((ListView) findViewById(R.id.listView1)).setAdapter(new SetAdapter(this,
				R.layout.row_set, today));
	}
	
	public void onClickAdd(View view) {
		
	}
	
	public static class AddSetDialogFragment extends DialogFragment {
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_add_exercise, null))
	        	// Add action buttons
	               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   DBHelper.addExercise(getActivity(), ((EditText) getDialog().findViewById(R.id.new_exercise_name)).getText().toString());
	                	   ((ActivityExerciseList) getActivity()).onResume();
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
	
	private void organizeSets() {
		Calendar todaysDate = Calendar.getInstance();
		todaysDate.setTime(new Date());
		
		ArrayList<Set> sets = DBHelper.getSets(this, exercise);
		for(Set s : sets) {
			Calendar c = s.calendar;
			if(c.get(Calendar.DAY_OF_MONTH) != todaysDate.get(Calendar.DAY_OF_MONTH)
					|| c.get(Calendar.MONTH) != todaysDate.get(Calendar.MONTH)
					|| c.get(Calendar.YEAR) != todaysDate.get(Calendar.YEAR)) {
				past.add(s);
			} else {
				today.add(s);
			}
		}
	}
	
	private class SetAdapter extends ArrayAdapter<Set> {
		private ArrayList<Set> items;

		public SetAdapter(Context context, int textViewResourceId, ArrayList<Set> items) {
			super(context, textViewResourceId, items);
			this.items = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row_set, null);
			}
			Set s = items.get(position);
			if (s != null) {
				TextView tt = (TextView) v.findViewById(R.id.weighttext);
				TextView bt = (TextView) v.findViewById(R.id.reptext);
				if (tt != null) {
					tt.setText("Weight: " + s.weight);
				}
				if (bt != null) {
					bt.setText("Reps: " + s.reps);
				}
			}
			return v;
		}
	}
}
