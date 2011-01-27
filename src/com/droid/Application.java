package com.droid;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Application extends Activity{

	private TextView output;
	DBAdapter db;
	private Button remove;
	List<String> listFr,listRa,listRaNames;
	
	long s,l;
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.application);
    	
    	Start st=new Start();
    	String val=st.val;
    	
    	 listFr = new ArrayList<String>();
    	 listRa = new ArrayList<String>();
    	 listRaNames= new ArrayList<String>();
    	 
    	 s=System.currentTimeMillis();
         if(val.equals("5")){
         	l=s-432000000;
         }
         else if(val.equals("10")){
         	l=s-864000000;
         }
         else if(val.equals("20")){
         	l=s-1728000000;
         }
         else {
         	int dys=Integer.parseInt(val);
         	l=s-(dys*24*60*60*1000);
         	
         	}
    	 
    	 
    	db = new DBAdapter(this);
    	 db.open();
    	
    	 Cursor c = db.getAllTitles();
    		if (c.moveToFirst())
    	 {
    	     do {          
    	     	/*Toast.makeText(this, 
    	                 "id: " + c.getString(0) + "\n" +
    	                 "NAME: " + c.getString(1) + "\n" +
    	                 "NO: " + c.getString(2) + "\n" +
    	                 "TIME:  " + c.getString(3),
    	                 Toast.LENGTH_LONG).show();*/
    	     	
    	     	
    	     	if(c.getString(1).equals("2")){
    	     		if((Long.parseLong(c.getString(3))/1000)>l/1000){
        	     		listFr.add(c.getString(2)+" "+c.getString(3));
        	     	}
    	     		else{
        	     		listRa.add(c.getString(2)+" "+c.getString(3));
        	     		listRaNames.add(c.getString(2));
        	     	}
    	     		     		
    	     	} 	
    	  	
    	     } while (c.moveToNext());
    		}
    	
    	
    	
    	 this.output = (TextView) this.findViewById(R.id.out_text);
    	
    	 StringBuilder sb = new StringBuilder();
         sb.append("Application name         No           Usage\n\n");
         sb.append("frequently used.....\n");
       
    	
    	for (String name : listFr) {
            sb.append(name + "\n");
         }
    	sb.append("\n");
    	
        sb.append("Rarely used.....\n");
       
    	
    	for (String name : listRa) {
            sb.append(name + "\n");
         }
         
        this.output.setText(sb.toString());
        
        db.close();
        
        /// buttton action
        
        remove = (Button)findViewById(R.id.remove);
        remove.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ removeAction(); }});
     
	}
	
	private void removeAction(){

       for (String name : listRaNames) {
           
        		String s="package:"+name;
        		Intent intent = new Intent(Intent.ACTION_DELETE);
                intent.setData(Uri.parse(s));
                startActivity(intent);
        	    
         }
       
       // update the database with the removed applications
       /*db.open();
       Cursor c;
       PackageManager pm = this.getPackageManager();
       Intent intent2 = new Intent(Intent.ACTION_MAIN, null);
       intent2.addCategory(Intent.CATEGORY_LAUNCHER);

       List <ResolveInfo>list = pm.queryIntentActivities(intent2, PackageManager.PERMISSION_GRANTED);
       int x;
       
      	 c = db.getAllTitles();
          
      	 if (c.moveToFirst())
      	 {
      	     do { 
      	    	 x=0;
      	    	for (ResolveInfo rInfo : list) {
      	    		if(c.getString(1).equals(rInfo.activityInfo.applicationInfo.packageName.toString())){
          	     	    x=1;
          	     	}
      	    	}
      	    	if(x==0){
      	      		 //db.insertTitle(rInfo.activityInfo.applicationInfo.packageName.toString(), "0", "0");
      	      		 db.deleteTitle(Long.parseLong(c.getString(0)));
      	      	 }
      	     	
      	     	
      	     } while (c.moveToNext());
      	 }
      	 
      	 
      	 db.close();*/
        
       
       
        
       
	}
	
	
}
