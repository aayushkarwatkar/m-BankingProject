package info.androidhive.slidingmenu;

import org.apache.commons.lang3.StringUtils;
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
import android.widget.TextView;
import android.widget.Toast;

public class BalanceCheckFragment extends Fragment {
	
	public BalanceCheckFragment(){}
	
	public static final String MyPREFERENCES = "MyPrefs" ;
	String content = "Press Button First";
	String domain = "thedivineprincess.tk";	
	SharedPreferences sharedpreferences;
	public static final String name = "nameKey"; //dummy initialization
	String userName="";
	TextView text;
	private ProgressDialog progress;

	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        final View rootView = inflater.inflate(R.layout.balance_check, container, false);
        
        sharedpreferences=this.getActivity().getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        userName = sharedpreferences.getString(name, ""); 
        text = (TextView)rootView.findViewById(R.id.showAmount);
        
        ImageButton button = (ImageButton)rootView.findViewById(R.id.check);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
            	
            		//Balanace check api
    			    class sendFile extends AsyncTask<Void, Void , String> {
    			       
    			    @Override
        			protected void onPreExecute() {
    			    super.onPreExecute();
        		    //Progress Bar
        		    progress = ProgressDialog.show(getActivity() , "Wait",
        		    "Checking...");
        			}
    			    @Override
    			    protected String doInBackground(Void... params) {
    			    
    			    try{
    			    HttpClient httpClient = new DefaultHttpClient();  
    			    Log.d("TAG", "Before making a post request");                 
    			    HttpGet httpget = new HttpGet("http://"+domain+"/balance.php?uname="+
    			    							  userName);
    			    HttpResponse response = httpClient.execute(httpget);
    			    HttpEntity EntityGet = response.getEntity();
    			    StatusLine statusLine = response.getStatusLine();
    			    int statusCode = statusLine.getStatusCode();
    			    if (EntityGet != null) {
    				content = EntityUtils.toString(EntityGet);
    				}
    				Toast.makeText(getActivity(), statusCode ,Toast.LENGTH_LONG).show();
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
    				Log.d("balance", content); 
    				//Toast.makeText(getActivity(), content ,Toast.LENGTH_LONG).show();
    			    validated(v, content);
    				}
    			    }	
    			    sendFile task = new sendFile();
    			    task.execute();
            
            }
        });
    	 
         
        return rootView;
    }
	
	public void validated(View v, String content){
		String trimmedContent = StringUtils.substringBefore(content, "<");
		text.setText(trimmedContent);
	}
}
