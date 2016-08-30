package com.isport.sportpool;

import java.io.IOException;
import java.io.InputStream;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.service.StartUp;
import com.isport.sportpool.xml.XMLParserNotify;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;

import org.xml.sax.SAXException;


public class SportPool_Notify extends Service implements Runnable, ReceiveDataListener
{
	private String URL = "";
	private String TYPE = "android";
//	private boolean checkThread = false;
	private Handler thread = null;
	
//	private Context context = null;
	private int SIMPLE_NOTFICATION_ID;
	
	private Notification notification = null;
	private CharSequence contentTitle = "SportPool";
    private NotificationManager mNotificationManager = null;
    
    private XMLParserNotify xmlPar =null;
	
//	public LiveScoreNotify(Context context, ReceiveDataListener receive, String TYPE)
//	public LiveScoreNotify(Context context)
//	{
//		this.TYPE = TYPE;
//		this.context = context;
//		this.receive = receive;
//		this.context = context;
//		mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//		thread = new Handler();
//	}
	
	@Override
	public IBinder onBind(Intent intent) {
		
		return null;
	}
	
	@Override
	public void onCreate()
	{
		mNotificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
		thread = new Handler();
		thread.post(this);
	}
	@Override
	public void onTaskRemoved(Intent rootIntent) {
		Intent restartService = new Intent(getApplicationContext(), this.getClass());
	    restartService.setPackage(getPackageName());
	    PendingIntent restartServicePI = PendingIntent.getService(
	        getApplicationContext(), 1, restartService,
	        PendingIntent.FLAG_ONE_SHOT);
	    
	    AlarmManager alarmService = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
	    alarmService.set(
	    AlarmManager.ELAPSED_REALTIME,
	    SystemClock.elapsedRealtime() + 1000,
	    restartServicePI);

	    super.onTaskRemoved(rootIntent);
	}
	
	private void setURLNotify()
	{
		//countryId=&minute=&lang=th&code=210002a&type=BB
		if( DataSetting.setLeague == null )
			{
				StartUp.getSetting(this);
				DataSetting.IMEI = StartUp.getImei(this);
		        DataSetting.IMSI = StartUp.getImsi(this);
		        StartUp.getModel(this);
			}
		
		URL = DataURL.notification;
		URL += "&contentgroupid=" + DataSetting.setLeague;
        URL += "&min=";
        URL += "&lang="+DataSetting.Languge;
        URL += "&imei="+DataSetting.IMEI;
        URL += "&model="+DataSetting.MODEL;
        URL += "&imsi="+DataSetting.IMSI;
        URL += "&type="+DataSetting.TYPE;
	}

	@Override
	public void run() {
		
		try {
			
			
			setURLNotify();
			
			//Log.i("sportpool", "sportpool url " + URL);
			
			AsycTaskLoadData load = new AsycTaskLoadData(this,this,null,"notification");
	        load.execute(URL);
	        
	        if(DataSetting.checkNotify)
	        	thread.postDelayed(this, 120000L);
	        
		} catch (Exception e) {
			
			Log.e("sportpool", "sportpool error " + e.getMessage());
			//PrintLog.printException("LiveScoreNotify method run", e);
		}
	}
	
	private void notifyScore(String title,String detail , String footer,String urlBig,String urlSmall,String phone,String url)
	{
		Bitmap imgSmall = null;
		Bitmap imgBig = null;
		try {
			
			imgBig = BitmapFactory.decodeStream(
	                (InputStream) new java.net.URL(urlBig).getContent());
			
	} catch (IOException e) {
		Log.e("sportpool", "sportpool error " + e.getMessage());
	}
		

		Intent viewIntent = new Intent(this, SportPool_Program.class);
		//viewIntent.putExtra(EXTRA_EVENT_ID, eventId);
		PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent pd = null;
		int icon = 0;

		//Log.i("sportpool", "sportpool phone " + phone);
		
		if( !phone.equals("") )
		{
			Uri uri = Uri.parse("tel:" + phone);
			Intent intent = new Intent(Intent.ACTION_DIAL);
			intent.setData(uri);
			pd = PendingIntent.getActivity(this, 0,intent, PendingIntent.FLAG_UPDATE_CURRENT);
			icon = R.drawable.ic_phone;
		}
		else 
		{
			Intent i = new Intent(Intent.ACTION_VIEW);  
        	i.setData(Uri.parse(url));  
        	pd = PendingIntent.getActivity(this, 0,i, PendingIntent.FLAG_UPDATE_CURRENT);
        	icon = R.drawable.ic_browser;
		}
		
	    
		
		Notification notif = new Notification.Builder(this)
		 .setContentText(detail)
		 .setContentTitle(title)
	     .setSmallIcon(R.drawable.ic_launcher)
	     .setLargeIcon(imgBig)
	     .setContentIntent(viewPendingIntent)
	     .addAction(R.drawable.star, " ", viewPendingIntent)
	     .addAction(icon, " ", pd)
	     .setStyle(new Notification.BigPictureStyle().bigPicture(imgBig).setBigContentTitle(detail))
	     .build();
		
		//Log.i("sportpool", "sportpool title " + title);
		
		mNotificationManager.notify(0, notif);
		
		

	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {


			if( !xmlPar.title.trim().equals(""))
			{
				notifyScore(xmlPar.title,xmlPar.detail,xmlPar.footer,xmlPar.urlImgBig,xmlPar.urlImgSmall,xmlPar.actionCall,xmlPar.actionURL);
			}
		} catch (Exception e) {

			//PrintLog.printException("LiveScoreNotify method onReceiveDataStream", e);
			Log.e("sportpool", "------ sportpool Start "+e.getMessage()+" -----" + url);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlPar = new XMLParserNotify();
			Xml.parse(strOutput, xmlPar);
		}
		catch(Exception ex)
		{

		}
	}
}