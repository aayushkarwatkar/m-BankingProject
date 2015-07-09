package info.androidhive.slidingmenu;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class PeerPaymentFragment extends Fragment {
	
	public PeerPaymentFragment(){}
	
	String userName;
	public static final String MyPREFERENCES = "MyPrefs" ;
	Intent intent;
	public static final String name = "nameKey"; //dummy initialization
	SharedPreferences sharedpreferences;
	String content = "hello";
	private ProgressDialog progress;
	String domain = "thedivineprincess.tk";
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.peer_payment, container, false);
		
		sharedpreferences=this.getActivity().getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(name, ""); 
        
		final EditText takeAccount = (EditText)rootView.findViewById(R.id.TakeAccount);
		final EditText takeBank = (EditText)rootView.findViewById(R.id.TakeBank);
		final EditText takePeerName = (EditText)rootView.findViewById(R.id.TakePeerName);
		final EditText takePin = (EditText)rootView.findViewById(R.id.TakePin);
		final EditText takeAmount = (EditText)rootView.findViewById(R.id.TakeAmount);
		final EditText takeDate = (EditText)rootView.findViewById(R.id.TakeDate);
		
 
        
        
        ImageButton button = (ImageButton)rootView.findViewById(R.id.pay);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	
            	if(!(takeAmount.getText().toString().matches("")) && !(takeDate.getText().toString().matches("")) && !(takePin.getText().toString().matches("")) && !(takeAccount.getText().toString().matches("")) && !(takeBank.getText().toString().matches("")) && !(takePeerName.getText().toString().matches("")) )
            		{
    			    
            		class sendFile extends AsyncTask<Void, Void , String> {
        			    
    			    @Override
    			    protected void onPreExecute() {
    		         
    		        super.onPreExecute();
    		        //Progress Bar
    		        progress = ProgressDialog.show(getActivity(), "Wait",
    		        "In progress...");
    			    }
            	
    			    @Override
    			    protected String doInBackground(Void... params) {
    			    
    			    try{
    			    HttpClient httpClient = new DefaultHttpClient();  
    			    Log.d("TAG", "Before making a post request");                 
    			    HttpGet httpget = new HttpGet("http://"+domain+"/peerpay.php?" +
    			    							  "uname="+userName+
    			    							  "&date_paid="+takeDate.getText().toString()+
    			    							  "&amount="+takeAmount.getText().toString()+
    			    							  "&peer_account_number="+takeAccount.getText().toString()+
    			    							  "&peer_bank="+takeBank.getText().toString()+
    			    							  "&peer_name="+takePeerName.getText().toString()+
    			    							  "&pin="+takePin.getText().toString());
    			    							  
    			    HttpResponse response = httpClient.execute(httpget);
    			    HttpEntity EntityGet = response.getEntity();
    			    StatusLine statusLine = response.getStatusLine();
    			    int statusCode = statusLine.getStatusCode();
    			    if (EntityGet != null) {
    				content = EntityUtils.toString(EntityGet);
    				}
    				Toast.makeText(getActivity().getApplicationContext(), statusCode ,Toast.LENGTH_LONG).show();
    				}
    				catch (Exception e) 
    				{
    				e.printStackTrace();
    				}
    			    return content;
    			    }

    			    protected void onPostExecute(String content) 
    				{
    			    progress.dismiss();
    				Log.d("Peer", content);
    				if(content.contains("done"))
    				{transaction();}
    				else{Toast.makeText(getActivity().getApplicationContext(), "Transaction Failed", Toast.LENGTH_SHORT).show();}
    				
    				}
    			    }	
    			    sendFile task = new sendFile();
    			    task.execute();
            		
            		}
            		else
            		{Toast.makeText(getActivity().getApplicationContext(), "Enter all fields", Toast.LENGTH_SHORT).show();}
            		
            }
        });
    	 
        
        return rootView;
    }
	public void transaction(){
		
		Toast.makeText(getActivity(), "Successfull Payment",Toast.LENGTH_LONG).show();
		intent = new Intent(getActivity(), MainActivity.class);
		startActivity(intent);
		
	}
}

