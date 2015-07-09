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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AlertsFragment extends Fragment {
	
	public AlertsFragment(){}
	
	String content = "Press Button First";
	String domain = "thedivineprincess.tk";	
	TextView text;
	private ProgressDialog progress;
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.alerts_fragement, container, false);
        
        text = (TextView)rootView.findViewById(R.id.alert_box);
        
        //alert checking api
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
	    HttpGet httpget = new HttpGet("http://"+domain+"/alert.php");
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
		Log.d("alert", content); 
		//Toast.makeText(getActivity(), content ,Toast.LENGTH_LONG).show();
		String trimmedContent = StringUtils.substringBefore(content, "<");
		text.setText(trimmedContent);
		}
	    }
        
	    
	    sendFile task = new sendFile();
	    task.execute();
	    
        
         
        return rootView;
    }
}
