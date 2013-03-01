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
	private static Exercise exercise;
	private static ArrayList<Set> today = new ArrayList<Set>();
	private static ArrayList<Set> past = new ArrayList<Set>();
	
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
		((ListView) findViewById(R.id.listView2)).setAdapter(new SetAdapter(this,
				R.layout.row_set, past));
	}
	
	public void onClickAdd(View view) {
		(new AddSetDialogFragment()).show(getFragmentManager(), "new_set");
	}
	
	public static class AddSetDialogFragment extends DialogFragment {
	    public Dialog onCreateDialog(Bundle savedInstanceState) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

	        // Inflate and set the layout for the dialog
	        // Pass null as the parent view because its going in the dialog layout
	        builder.setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_add_set, null))
	        	// Add action buttons
	               .setPositiveButton("Add", new DialogInterface.OnClickListener() {
	                   public void onClick(DialogInterface dialog, int id) {
	                	   DBHelper.addSet(getActivity(), exercise, Double.parseDouble(((EditText) getDialog().findViewById(R.id.weight)).getText().toString()),
	                			   Double.parseDouble(((EditText) getDialog().findViewById(R.id.reps)).getText().toString()));
	                	   ((ActivityExercise) getActivity()).onResume();
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
		private ArrayList<Set> sets;

		public SetAdapter(Context context, int textViewResourceId, ArrayList<Set> items) {
			super(context, textViewResourceId, items);
			sets = items;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row_set, null);
			}
			Set s = sets.get(position);
			if (s != null) {
				Calendar c = s.calendar;
				((TextView) v.findViewById(R.id.datetext)).setText(c.get(Calendar.MONTH) + "/" + c.get(Calendar.DAY_OF_MONTH) + "/" + c.get(Calendar.YEAR));
				((TextView) v.findViewById(R.id.weighttext)).setText("Weight: " + s.weight);
				((TextView) v.findViewById(R.id.reptext)).setText("Reps: " + s.reps);
			}
			return v;
		}
	}
}
