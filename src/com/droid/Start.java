package com.droid;




import java.util.Calendar;

import javax.security.auth.PrivateCredentialPermission;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class Start extends Activity {
    /** Called when the activity is first created. */
	
	/*methods....
	 * oncreate - use to 
	 * disone - use to implement the actions to be done when clicked the button
	 * */
	// for alarm
	private PendingIntent pendingIntent;
	//
	private Button ok,start,cancel;
	private RadioButton rb1,rb2,rb3,rb4,rb5,rb6;
	private TextView edit;
	
	public static String val="";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main);
    	rb1 = (RadioButton)findViewById(R.id.one);
    	rb2 = (RadioButton)findViewById(R.id.two);
    	rb3 = (RadioButton)findViewById(R.id.three);
    	rb4 = (RadioButton)findViewById(R.id.four);
    	rb5 = (RadioButton)findViewById(R.id.five);
    	rb6 = (RadioButton)findViewById(R.id.six);
    	edit = (TextView)this.findViewById(R.id.edit);
    	
        ok = (Button)findViewById(R.id.ok);
        ok.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ disone(); }});
       
        start = (Button)findViewById(R.id.start);
        start.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ disstart(); }}); 
     
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ discancel(); }});
    }
    
    // action when start alarm
    private void disstart(){
        // alarm service
        
        Toast.makeText(Start.this, "Start Alarm", Toast.LENGTH_LONG).show();
        
 	   Intent myIntent = new Intent(Start.this, MyAlarmService.class);
 	   pendingIntent = PendingIntent.getService(Start.this, 0, myIntent, 0);
 	   
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 10);
        
       // alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10*1*1000, pendingIntent);
        
        
    }
    
    
    
    private void discancel(){
    	AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
    	   alarmManager.cancel(pendingIntent);

    	            // Tell the user about what we did.
    	            Toast.makeText(Start.this, "Cancel!", Toast.LENGTH_LONG).show();
    }
    
    // action when button is clicked
    private void disone()
    {
    	if((rb1.isChecked()==true)&&rb4.isChecked()==true){
    		val="5";
    		 Intent testview = new Intent(Start.this, Contact.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb1.isChecked()==true)&&rb5.isChecked()==true){
    		val="10";
    		 Intent testview = new Intent(Start.this, Contact.class); // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb1.isChecked()==true)&&rb6.isChecked()==true){
    		val="20";
    		 Intent testview = new Intent(Start.this, Contact.class); // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb1.isChecked()==true)&&rb4.isChecked()==false&&rb5.isChecked()==false&&rb6.isChecked()==false){
    		val=edit.getText().toString();
    		 Intent testview = new Intent(Start.this, Contact.class); // start second Intent(Window)
    	        startActivity(testview);
    	}
    	
    	if((rb2.isChecked()==true)&&rb4.isChecked()==true){
    		val="5";
    		 Intent testview = new Intent(Start.this, Application.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb2.isChecked()==true)&&rb5.isChecked()==true){
    		val="10";
    		 Intent testview = new Intent(Start.this, Application.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb2.isChecked()==true)&&rb6.isChecked()==true){
    		val="20";
    		 Intent testview = new Intent(Start.this, Application.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb2.isChecked()==true)&&rb4.isChecked()==false&&rb5.isChecked()==false&&rb6.isChecked()==false){
    		val=edit.getText().toString();
    		 Intent testview = new Intent(Start.this, Application.class); // start second Intent(Window)
    	        startActivity(testview);
    	}
    	
    	if((rb3.isChecked()==true)&&rb4.isChecked()==true){
    		val="5";
    		 Intent testview = new Intent(Start.this, Media.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb3.isChecked()==true)&&rb5.isChecked()==true){
    		val="10";
    		 Intent testview = new Intent(Start.this, Media.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb3.isChecked()==true)&&rb6.isChecked()==true){
    		val="20";
    		 Intent testview = new Intent(Start.this, Media.class);  // start second Intent(Window)
    	        startActivity(testview);
    	}
    	if((rb3.isChecked()==true)&&rb4.isChecked()==false&&rb5.isChecked()==false&&rb6.isChecked()==false){
    		val=edit.getText().toString();
    		 Intent testview = new Intent(Start.this, Media.class); // start second Intent(Window)
    	        startActivity(testview);
    	}
       
    }
}