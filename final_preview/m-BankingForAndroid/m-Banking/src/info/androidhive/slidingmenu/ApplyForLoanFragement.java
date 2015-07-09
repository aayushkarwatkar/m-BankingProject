package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class ApplyForLoanFragement extends Fragment {
	
	public ApplyForLoanFragement(){}
	String string_type, string_type_default;
	Intent intent;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		
 
        View rootView = inflater.inflate(R.layout.apply_loan, container, false);
        
        final EditText takeAmt = (EditText)rootView.findViewById(R.id.TakeAmount);
        //***Spinner declaration ***//
        
        final Spinner spinner = (Spinner)rootView.findViewById(R.id.role);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),R.array.type_loan, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinner.setAdapter(adapter);
    	
    	//on spinner item selected
    	spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	    	string_type = spinner.getSelectedItem().toString();
    	    }
    	    public void onNothingSelected(AdapterView<?> parent) {
    	    	string_type_default="NA";
    	    }
    	});
    	
    	ImageButton button = (ImageButton)rootView.findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if(!(takeAmt.getText().toString().matches("")))
            	{
            		Toast.makeText(getActivity(), "Sent to Bank for Verification",Toast.LENGTH_LONG).show();
            		intent = new Intent(getActivity(), MainActivity.class);
            		startActivity(intent);
            	}
            	else
            	{
            		Toast.makeText(getActivity().getApplicationContext(), "Amount Not Filled",Toast.LENGTH_LONG).show();
            	}
            }
        });
    	 
    	  	
        return rootView;
    }
}
