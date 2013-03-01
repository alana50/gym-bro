package enos.meditz.gym.bro;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class ActivityExerciseList extends FragmentActivity {
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_one_or_add);
    }
	
	@Override
	public void onResume() {
		super.onResume();
		final ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(new ArrayAdapter<Exercise>(this, R.layout.simple_list_item, DBHelper.getExercises(this)));
		listView.setOnItemClickListener(new OnItemClickListener() {
		    public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        // Do something in response to the click
		    	Intent intent = new Intent(ActivityExerciseList.this, ActivityExercise.class);
		    	intent.putExtra(ExtraHelper.EXTRA_EXERCISE, ((Exercise) listView.getItemAtPosition(position)).id);
		    	startActivity(intent);
		    }
		}); 
	}
	
	public void onClickAdd(View view) {
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
}
