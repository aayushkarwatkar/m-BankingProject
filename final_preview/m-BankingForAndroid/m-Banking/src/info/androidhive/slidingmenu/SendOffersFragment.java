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
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;

public class SendOffersFragment extends Fragment {

	String content = "hello";
	private ProgressDialog progress;
	String domain = "thedivineprincess.tk";

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {
  
         View rootView = inflater.inflate(R.layout.send_offer_now, container, false);
         Button btn;
         final EditText text = (EditText)rootView.findViewById(R.id.offer_text);
         
         btn = (Button) rootView.findViewById(R.id.button_send_offer);
         btn.setOnClickListener(new OnClickListener() {

             @Override
             public void onClick(View arg0) {

            	 if(!text.getText().toString().matches("")){
            		 
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
  			    
  			    String send = text.getText().toString();
			   
			    send = send.replace(" ", "%20");
			    
  			    HttpGet httpget = new HttpGet("http://"+domain+"/offers.php?offers="+send);
  			    							  
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
  				Log.d("Sending alert", content);
  				if(content.contains("done"))
  					{Toast.makeText(getActivity(), "Sent an offer",Toast.LENGTH_SHORT).show();
  					Intent intent = new Intent(getActivity(), MainActivity2.class);
  	  			    startActivity(intent);}
  				}
  			    }	
  			    sendFile task = new sendFile();
  			    task.execute();	 
  			    
  			    
            	 }
            	 else{Toast.makeText(getActivity(), "Enter data -__- ",Toast.LENGTH_SHORT).show();}

             }
         });
         
         return rootView;
     }

 }
