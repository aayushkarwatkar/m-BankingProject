package info.androidhive.slidingmenu;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class LoginScreen extends Activity {
	
	EditText userName,passwd;
	String string_role, string_role_default;
	Intent intent;
	String loggedin ="0", custs ="0";
	String content = "hello";
	String domain = "thedivineprincess.tk";
	private ProgressDialog progress;
	
	public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String name = "nameKey"; //dummy initialization
    public static final String pass = "passwordKey"; ////dummy initialization
    public static final String type = "0"; ////dummy initialization
    
    SharedPreferences sharedpreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		 //checking whether shared preferences are set or not
        sharedpreferences=getSharedPreferences(MyPREFERENCES,
                Context.MODE_PRIVATE);
        if (sharedpreferences.contains(name))
        {
            if(sharedpreferences.contains(pass)){
            	
            	String sharedType = sharedpreferences.getString(type, "0");
            	
            	if(sharedType.contains("0")){
            		Intent i = new Intent(this,MainActivity.class);
                    startActivity(i);
            	}
            	else{
            		Intent i = new Intent(this,MainActivity2.class);
                    startActivity(i);
            	}                
            }
        }
		
         setContentView(R.layout.activity_login_screen);
		
		
		//***Spinner declaration ***//
        
        final Spinner spinner = (Spinner) findViewById(R.id.role);
    	// Create an ArrayAdapter using the string array and a default spinner layout
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.role, android.R.layout.simple_spinner_item);
    	// Specify the layout to use when the list of choices appears
    	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	// Apply the adapter to the spinner
    	spinner.setAdapter(adapter);
    	
    	//on spinner item selected
    	spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    	    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
    	    	string_role = spinner.getSelectedItem().toString();
    	    }
    	    public void onNothingSelected(AdapterView<?> parent) {
    	    	string_role_default="NA";
    	    }
    	});
    	  	
    	
    	
    	//Calling the screen as per button click
    	
    	ImageButton button = (ImageButton) findViewById(R.id.moveForward);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
            	
            	//Extracting user id fields
        		
        		userName = (EditText) findViewById(R.id.userId);
        		
        		passwd = (EditText) findViewById(R.id.passwd);
        		
        		if((!userName.getText().toString().matches("")) && (!passwd.getText().toString().matches("")))
        		{
        			
        			//Web Login
    			    class sendFile extends AsyncTask<Void, Void , String> {
    			    
    			    @Override
    			    protected void onPreExecute() {
    		         
    		        super.onPreExecute();
    		        //Progress Bar
    		        progress = ProgressDialog.show(LoginScreen.this, "Wait",
    		        "Registering...");
    			    }
            	
    			    @Override
    			    protected String doInBackground(Void... params) {
    			    
    			    try{
    			    HttpClient httpClient = new DefaultHttpClient();  
    			    Log.d("TAG", "Before making a post request");                 
    			    HttpGet httpget = new HttpGet("http://"+domain+"/check.php?uname="+
    			    							  userName.getText().toString()+
    			    							  "&passwd="+passwd.getText().toString());
    			    HttpResponse response = httpClient.execute(httpget);
    			    HttpEntity EntityGet = response.getEntity();
    			    StatusLine statusLine = response.getStatusLine();
    			    int statusCode = statusLine.getStatusCode();
    			    if (EntityGet != null) {
    				content = EntityUtils.toString(EntityGet);
    				}
    				Toast.makeText(getApplicationContext(), statusCode ,Toast.LENGTH_LONG).show();
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
    				Log.d("Login", content);  
    				if(content.contains("User"))
    			    {
    			    validated(v);	
    			    }
    			    else{Toast.makeText(getApplicationContext(), "Wrong username and password" ,Toast.LENGTH_LONG).show();}
    				}
    			    }	
    			    sendFile task = new sendFile();
    			    task.execute();
    			    
        			
        		}
        		if(userName.getText().toString().matches(""))
        		{Toast.makeText(getApplicationContext(), "Please check Userid", Toast.LENGTH_SHORT).show();}
        		if(passwd.getText().toString().matches(""))
        		{Toast.makeText(getApplicationContext(), "Please check Password", Toast.LENGTH_SHORT).show();}
        		
            }
        });
        
	}
	
	
	
	
		//Called if successful login
		public void validated(View v)
		{
			if(string_role.contains("Customers"))
			{
			
			Toast.makeText(getApplicationContext(), "Welcome" , Toast.LENGTH_SHORT).show();
    		
    		login(v);
    		
			}
			
			if(string_role.contains("Bank"))
			{
			
			Toast.makeText(getApplicationContext(), "We are glad to see you back" , Toast.LENGTH_SHORT).show();
    		loginBank(v);
			}
		}
    	
		
		
		
		
      //Putting values into the prefernces, once after validation is done
        public void login(View view){
            Log.d("Tag", "Inside function");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String username = userName.getText().toString();
            String password = passwd.getText().toString().toUpperCase();
            
            editor.putString(name, username);
            editor.putString(pass, password);
            editor.putString(type, "0");
            
            editor.commit();
            Intent i = new Intent(this,MainActivity.class);
            startActivity(i);
        }
        
        
        
      //Putting values into the prefernces, once after validation is done
        public void loginBank(View view){
            Log.d("Tag", "Inside function");
            SharedPreferences.Editor editor = sharedpreferences.edit();
            String username = userName.getText().toString();
            String password = passwd.getText().toString().toUpperCase();

            editor.putString(name, username);
            editor.putString(pass, password);
            editor.putString(type, "1");
            
            editor.commit();
            Intent i = new Intent(this,MainActivity2.class);
            startActivity(i);
        }

        
        
        
        //For activity resume functionality
        @Override
        protected void onResume() {
            sharedpreferences=getSharedPreferences(MyPREFERENCES,
                    Context.MODE_PRIVATE);
            if (sharedpreferences.contains(name))
            {
                if(sharedpreferences.contains(pass)){
                	
                	String sharedType = sharedpreferences.getString(type, "0");
                	
                	if(sharedType.contains("0")){
                		Intent i = new Intent(this,MainActivity.class);
                        startActivity(i);
                	}
                	else{
                		Intent i = new Intent(this,MainActivity2.class);
                        startActivity(i);
                	}                
                }
            }
    		            super.onResume();
        }
                  
           
	
}
