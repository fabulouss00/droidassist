package com.droid;

import java.util.ArrayList;
import java.util.List;



import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.provider.CallLog.Calls;
import android.provider.Contacts.People;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Contact extends Activity {
     
	
	
	
	private TextView output;
	private Button del;
	DBAdapter db;
	long s,l;
	private Button back;
	
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.contact);
    	
    	Start st=new Start();
    	String val=st.val;
    	
        this.output = (TextView) this.findViewById(R.id.out_text);
             
        // time calculation
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
        
        // create the databasse connection
        db = new DBAdapter(this);           
        db.open();        
        long id;
        
              
      // contact names
        
        /*ContentResolver cr = getContentResolver();
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,null, null, null, null);
        if (cur.getCount() > 0) {
 	    while (cur.moveToNext()) {
 		String name = cur.getString(
                        cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
 		
 		if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
 		    //Query phone here.  Covered next
 			//tv.setText(id);
 			
 			id = db.insertTitle(name,"0","0");
 			
 			
 			//Log.v("contcts",""+name);
 	        }
            }
 	}*/
        
        // log details
        
        Uri allCalls = Uri.parse("content://call_log/calls");
	      Cursor c = managedQuery(allCalls, null, null, null, null);
	      
	      if (c.moveToFirst()) {
	         do{            
	            
	            Log.v("Content Providers", 
	                  c.getString(c.getColumnIndex(Calls.DATE)) + ", " +
	                  c.getString(c.getColumnIndex(Calls.NUMBER)) + ", " +
	                   ","+ c.getString(c.getColumnIndex(Calls.CACHED_NAME))) ;
	            
	           // loginserting(c.getString(c.getColumnIndex(Calls.CACHED_NAME)),c.getString(c.getColumnIndex(Calls.NUMBER)),c.getString(c.getColumnIndex(Calls.DATE)), db);
	            loginserting(c.getString(c.getColumnIndex(Calls.CACHED_NAME)),"1",c.getString(c.getColumnIndex(Calls.DATE)), db);
	            
	         } while (c.moveToNext());
	      }
        
        
        
    // update
         /*c= db.getAllTitles();
        if(c.moveToFirst()){
        	do{
        		updating(c,db);
        		
        	}while (c.moveToNext());
        }*/
        
	      
	      
      //---get all titles---
        
        c = db.getAllTitles();
        StringBuilder sb = new StringBuilder();
        sb.append("Name         No           Usage\n");
        List<String> list = new ArrayList<String>();
        final List<String> dellist = new ArrayList<String>();
        final List<String> delRowList = new ArrayList<String>();
        String status;
        
       if (c.moveToFirst())
       {
           do {    
        	   if(c.getString(2).equals("1")){
        		 //DisplayTitle(c);
            	   if(Integer.parseInt(c.getString(3))>=5){
            		   status="Frequently";
            		   list.add(c.getString(1)+"  "+c.getString(2)+"  "+c.getString(3)+" "+status); 
            	   }
            	   else{
            		   status="Rarely";
                	   list.add(c.getString(1)+"  "+c.getString(2)+"  "+c.getString(3)+" "+status); 
                        
                       dellist.add(c.getString(1));
                       delRowList.add(c.getString(0));
            	   } 
        	   }
               
        		   
               
           } while (c.moveToNext());
       }
       
       for (String name : list) {
           sb.append(name + "\n");
        }
        
       this.output.setText(sb.toString());
       
         
        // button action.... delete confirmation and go back
        
        del = (Button)findViewById(R.id.del);
        del.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ deletionbutton(dellist,delRowList); }});
        
        back = (Button)findViewById(R.id.back);
        back.setOnClickListener(new Button.OnClickListener() { public void onClick (View v){ back(); }});
        
        
       // db.close();
              
	}
	
      /// end of oncreate method.............................
        
	
    // deletion button actions
	
	public void deletionbutton(List<String> dellist,List<String> delRowList){
		
        for(int i=0;i<dellist.size();i++){
        	String name=dellist.get(i);
        	String rowId=delRowList.get(i);
        	    	
        	// delete contacts...
        	
        	ContentResolver cr = getContentResolver();
        	Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
        	while (cur.moveToNext()) {
                try{
                    String lookupKey = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.LOOKUP_KEY));
                    Uri uri = Uri.withAppendedPath(ContactsContract.Contacts.CONTENT_LOOKUP_URI, lookupKey);
                    System.out.println("The uri is " + uri.toString());
                    //cr.delete(uri, null, null);
                    //getContentResolver().delete(People.CONTENT_URI, People.NAME+"=?", new String[] {"Thaththa"});    // this is also acceptable
                    cr.delete(People.CONTENT_URI, People.NAME+"=?", new String[] {name});
                    cr.delete(CallLog.Calls.CONTENT_URI,CallLog.Calls.CACHED_NAME+"=?" , new String[] {name});
                    
                }
                catch(Exception e)
                {
                    System.out.println(e.getStackTrace());
                }
            }
        	
        	
        	
        	// delete from database
        	//DbDeleting(rowId);  
        	// for delete only rare items from db
        	     	
            }
        
        // delete entire db
        DbAllDeleting();
        	

        
           // print rest of the data base   

   	 /*Cursor c = db.getAllTitles();
   	if (c.moveToFirst())
       {
           do {          
               DisplayTitle(c);
        	
           } while (c.moveToNext());
	}*/
   	
   	
   	
   	Intent testview = new Intent(Contact.this, Start.class);
    startActivity(testview);
   	

	}  // end of detetion button actions
	
	
	
	// go back to the starting window
	public void back(){
		DbAllDeleting();
		Intent testview = new Intent(Contact.this, Start.class);
        startActivity(testview);
	}
	
	     // database rare deletion
	
	public void DbDeleting(String rowId){
		Cursor c= db.getAllTitles();
        if(c.moveToFirst()){
        	do{
        		if(c.getString(0).equals(rowId)){
        			db.deleteTitle(Long.parseLong(rowId));
        		}
        		
        	}while (c.moveToNext());
        }
	}
	
	//Delete all titles from the database
	public void DbAllDeleting(){
		Cursor c= db.getAllTitles();
        if(c.moveToFirst()){
        	do{  
        		if(c.getString(2).equals("1")){
        			db.deleteTitle(Long.parseLong(c.getString(0)));
        		}       		
        		
        	}while (c.moveToNext());
        }
	}
	
	
        // log inserting
        
        public void loginserting(String name,String no,String date,DBAdapter db) {
        	Cursor c= db.getAllTitles();
        	int x=0;
        	int y=0;
            if(c.moveToFirst()){
            	do{
            		x=updating(c,name,no,date,db);
            		y=y+x;
            	}while (c.moveToNext());
            }
            
            if(y==0){
            	db.insertTitle(name, no, "1");
            }
    	}
        
                                                                                                                                                      
        
        // update titles
        
        public int updating(Cursor c,String name,String no,String date,DBAdapter db) {
        	
        	if(c.getString(1).equals(name)&& (Long.parseLong(date)/1000)>(l/1000)){  // check according to the time...
        		int x=Integer.parseInt(c.getString(3))+1;
        		String val=String.valueOf(x);
            	db.updateTitle(Long.parseLong(c.getString(0)), c.getString(1),no , val);
            	return 1;
        	}
      
        	else if(c.getString(1).equals(name)&& (Long.parseLong(date)/1000)<(l/1000)){  // check according to the time...
            	return 1;
        	}
        	else if(!c.getString(1).equals(name)&& (Long.parseLong(date)/1000)>(l/1000)){
        		return 0;
        	}
        	else{
        		return 1;
            	
        	}
        		
    	}
        
        
        // ---display all titles---
        
        public void DisplayTitle(Cursor c)
        {
        	
            Toast.makeText(this, 
                    "id: " + c.getString(0) + "\n" +
                    "NAME: " + c.getString(1) + "\n" +
                    "NO: " + c.getString(2) + "\n" +
                    "TIMES:  " + c.getString(3),
                    Toast.LENGTH_LONG).show();      
        }
    
	
	
	
}
