package com.droid;

import java.util.ArrayList;





import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;


public class Media extends Activity{

	DBAdapter db;
	private TextView txt;
	ArrayList<String> list;
	long s,l;
	public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.media);
    	

    	db = new DBAdapter(this);
    	
    	Start st=new Start();
    	String val=st.val;
    	
   	    db.open();
   	list = new ArrayList<String>();
   	this.txt = (TextView) this.findViewById(R.id.txt);
   	 StringBuilder sb = new StringBuilder();
     sb.append("Application name         No           Usage\n\n");
     
     
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
   	     	if(c.getString(1).equals("3")&&(Long.parseLong(c.getString(3))/1000)>l/1000){
   	     	list.add(c.getString(2)+"hey.."+c.getString(3));
   	     	}
   	     
   	     	
   	  	
   	     } while (c.moveToNext());
   		}
   		
   		
   		for (String name : list) {
            sb.append(name + "\n");
         }
   	 
   		
        this.txt.setText(sb.toString());
    	
    	
    	
	}
}
