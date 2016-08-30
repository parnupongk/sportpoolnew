package com.isport.sportpool;

import java.io.InputStream;
import java.util.ArrayList;

import com.isport.sportpool.data.DataElementGroupListMenu;
import com.isport.sportpool.data.DataElementListMenuLeft;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.service.StartUp;
import com.isport.sportpool.xml.XMLParserListMenu;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.widget.ImageView;

import org.xml.sax.SAXException;

public class SportPool_Logo extends Activity implements Runnable,ReceiveDataListener
{
    /** Called when the activity is first created. */
	private ImageView logo = null;
	private Handler thread = null;
	private boolean resumeHasRun = false;
	public static ArrayList<DataElementGroupListMenu> vGroupListMenu1 ;
	public static ArrayList<Object> vGroup ;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_logo);
        
        //sethImage190(new HashMap<String, Bitmap>());
        DataSetting.IMEI = StartUp.getImei(this);
        DataSetting.IMSI = StartUp.getImsi(this);
        StartUp.getModel(this);
        StartUp.getSetting(this);
        
        logo = (ImageView) findViewById(R.id.img_logo);
    	logo.setBackgroundResource(R.drawable.landingfina);
        

    	if( !isOnline() )
    	{
    		AlterInternetError();
    	}

    	//actionBar.hide();
    	
    }
    
    
    private void AlterInternetError()
    {
    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    	builder.setMessage("Please Connect to the internet")
    	       .setCancelable(false)
    	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
    	           public void onClick(DialogInterface dialog, int id) {
    	                finish();
    	           }
    	       });
    	AlertDialog alert = builder.create();
    	alert.show();
    }
    
    @Override
    protected void onStop() {
    	super.onStop();
    	resumeHasRun = false;
    	finish();
    	
    }
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	finish();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	
    	//EventDataBinding();
    	if(!resumeHasRun)
    	{
    		menuDataBinding();
    		if(DataSetting.checkNotify)
        	{
        		this.startService(new Intent(this, SportPool_Notify.class));
        	}
    		resumeHasRun = true;
    	}
    	
    }
	public void menuDataBinding()
	{
		try
		{
			final String url = DataURL.listMenu + "&sportType=00001" + "&lang="+DataSetting.Languge
					+ "&imei="+DataSetting.IMEI + "&model="+DataSetting.MODEL
					+ "&imsi="+DataSetting.IMSI + "&type="+DataSetting.TYPE;
			

			AsycTaskLoadData load = new AsycTaskLoadData(this , this,null,"listmenu");
			load.execute(url);
			
		
			/*new Thread(new Runnable() {
				
				@Override
				public void run() {
					vGroup  = CMFeedXmlUtil.feed(url, "SportApp");
					
					String s  = vGroup.toString();
				}
			}).start();*/
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Logo Error:", ex.getMessage());
		}
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if( strOutput != null )
			{
				if(loadName == "listmenu")
				{
					thread = new Handler();
					thread.postDelayed(this, 000L);

				}
				/*else if(loadName == "event")
				{
					XMLParserEvent xmlList = new XMLParserEvent();
					Xml.parse(inputStream, Xml.Encoding.UTF_8, xmlList);

					SportArena_BaseClass.vdataEvent = xmlList.vData;
					//menuDataBinding();


				}*/
			}
			else
			{
				AlterInternetError();
			}

		}
		catch(Exception ex)
		{
			Log.d("SportPool Logo Error:", ex.getMessage());
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			XMLParserListMenu xmlListMenu = new XMLParserListMenu();
			Xml.parse(strOutput, xmlListMenu);
			//xmlListMenu.get(inputStream);

			SportPool_BaseClass.ISACTIVE = xmlListMenu.isActive;
			SportPool_BaseClass.ISAdView = xmlListMenu.isAdView;
			SportPool_BaseClass.ACTIVE_HEADER = xmlListMenu.active_header;
			SportPool_BaseClass.ACTIVE_DETAIL = xmlListMenu.active_detail;
			SportPool_BaseClass.ACTIVE_FOOTER = xmlListMenu.active_footer;
			SportPool_BaseClass.ACTIVE_FOOTER1 = xmlListMenu.active_footer1;
			SportPool_BaseClass.ACTIVE_OTPWAIT = xmlListMenu.active_otpwait;
			SportPool_BaseClass.SERVERVERSION = xmlListMenu.serverversion;
			SportPool_BaseClass.vGroupListMenu = new ArrayList<DataElementGroupListMenu>();
			SportPool_BaseClass.vGroupListMenu = xmlListMenu.vGroupListMenu;
			SportPool_BaseClass.vGroupLeftMenu = xmlListMenu.vListMenuLeft;
			SportPool_BaseClass.vGroupLeftMenu.add(0, new DataElementListMenuLeft("","","header","")); // banner
			SportPool_BaseClass.URLBANNER = xmlListMenu.url_banner;
			SportPool_BaseClass.URLICON = xmlListMenu.url_icon;
			SportPool_BaseClass.MSISDN = xmlListMenu.msisdn;
		}
		catch(Exception ex)
		{
			Log.d("sportpool", ex.getMessage());
		}
	}

	
	public boolean isOnline() {
	    ConnectivityManager cm =
	        (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	    return cm.getActiveNetworkInfo() != null && 
	       cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}


	@Override
	public void run() {
		if(SportPool_BaseClass.ISACTIVE.equals("N"))
		{
			Intent intent = new Intent(this, SportPool_Active.class);
			startActivity(intent);
			finish();

		}
		else
		{
			Intent intent = new Intent(this, SportPool_Program.class);
			startActivity(intent);
			finish();
		}
	}





}