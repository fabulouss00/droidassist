package com.droid;




import java.util.List;


import android.app.ActivityManager;
import android.app.Service;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyAlarmService extends Service {
	
	
	DBAdapter db;
	
@Override
public void onCreate() {
 // TODO Auto-generated method stub
 Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG).show();
 
/// database access
 
 db = new DBAdapter(this);
 //db.open();  
 
 
 //db.close();
	
 

}

@Override
public IBinder onBind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG).show();
 return null;
}

@Override
public void onDestroy() {
 // TODO Auto-generated method stub
 super.onDestroy();
 Toast.makeText(this, "MyAlarmService.onDestroy()", Toast.LENGTH_LONG).show();
}

private BroadcastReceiver mReceiver = new BroadcastReceiver() {
	@Override
	public void onReceive(Context context, Intent intent){
		String action = intent.getAction();
		String cmd = intent.getStringExtra("command");
		Log.d("mIntentReceiver.onReceive---------- ", action + " / " + cmd);
		String artist = intent.getStringExtra("artist");
		String album = intent.getStringExtra("album");
		String track = intent.getStringExtra("track");
		Log.d("Music",artist+":"+album+":"+track+"Vimesh -----");
		db.open();
		Cursor c;
		c = db.getAllTitles();
	    int x=0;
		 if (c.moveToFirst())
		 {
		     do {          
		     		  	
		     	if(c.getString(2).equals(artist+" "+album+" "+track)){
		     		db.updateTitle(c.getShort(0),"3", artist+" "+album+" "+track,String.valueOf(System.currentTimeMillis()));
		     	    x=1;
		     	}
		     	
		     } while (c.moveToNext());
		 }
		 
		 if(x==0){
			 db.insertTitle("3", artist+" "+album+" "+track,String.valueOf(System.currentTimeMillis()));
		 }	
		
		 db.close();
	}
	};





@Override
public void onStart(Intent intent, int startId) {
 // TODO Auto-generated method stub
 super.onStart(intent, startId);
 Cursor c;
       
 Toast.makeText(this, "start is started", Toast.LENGTH_LONG).show();
 
 // media access
 
 IntentFilter iF = new IntentFilter();
	iF.addAction("com.android.music.metachanged");
	iF.addAction("com.android.music.playstatechanged");
	iF.addAction("com.android.music.playbackcomplete");
	iF.addAction("com.android.music.queuechanged");

	registerReceiver(mReceiver, iF);
 
 
 
 
 
 
 
 
 
 //////////////// database access....
 db.open();
 
 
 // installed applications
 PackageManager pm = this.getPackageManager();
 Intent intent2 = new Intent(Intent.ACTION_MAIN, null);
 intent2.addCategory(Intent.CATEGORY_LAUNCHER);

 List <ResolveInfo>list = pm.queryIntentActivities(intent2, PackageManager.PERMISSION_GRANTED);
 int x;
 for (ResolveInfo rInfo : list) {
	 c = db.getAllTitles();
     x=0;
	 if (c.moveToFirst())
	 {
	     do {          
	     		  	
	     	if(c.getString(2).equals(rInfo.activityInfo.applicationInfo.packageName.toString())){
	     	    x=1;
	     	}
	     	
	     } while (c.moveToNext());
	 }
	 
	 if(x==0){
		 db.insertTitle("2",rInfo.activityInfo.applicationInfo.packageName.toString(),"1");
	 }
	 
  
 }
 
 
//running processes........
 ActivityManager actvityManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
 List<RunningAppProcessInfo> procInfos = actvityManager.getRunningAppProcesses();
 int y;
 for(int i = 0; i < procInfos.size(); i++)
 {
         
	 c = db.getAllTitles();
     y=0;
	 if (c.moveToFirst())
	 {
	     do {          
	     		  	
	     	if(c.getString(2).equals(procInfos.get(i).processName)){
	     		String s=String.valueOf(System.currentTimeMillis());
	     	    db.updateTitle(Long.parseLong(c.getString(0)), c.getString(1),c.getString(2) ,s);
	     	}
	     	
	     } while (c.moveToNext());
	 }	 
	 
 }
 
 
 
 
 
 // display
 /*
 c = db.getAllTitles();
	if (c.moveToFirst())
 {
     do {          
     	Toast.makeText(this, 
                 "id: " + c.getString(0) + "\n" +
                 "NAME: " + c.getString(1) + "\n" +
                 "NO: " + c.getString(2) + "\n" +
                 "TIME:  " + c.getString(3),
                 Toast.LENGTH_LONG).show();
  	
     } while (c.moveToNext());
	}
 */
	// delete
	
	  /*  if(c.moveToFirst()){
    	do{   		
    		db.deleteTitle(Long.parseLong(c.getString(0)));
    		
    	}while (c.moveToNext());
    }*/
	    
	    
	   db.close(); 
	    
	    
	
 
}

public void assess(){
	Toast.makeText(this, "MyAlarmService.onStartassess()", Toast.LENGTH_LONG).show();
	//t.assess();
}

@Override
public boolean onUnbind(Intent intent) {
 // TODO Auto-generated method stub
 Toast.makeText(this, "MyAlarmService.onUnbind()", Toast.LENGTH_LONG).show();
 return super.onUnbind(intent);
}

}