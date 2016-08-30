package com.isport.sportpool;

import java.io.InputStream;
import java.util.Vector;

import com.isport.sportpool.data.DataElementTded;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterTded;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserTded;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_Tded extends SportPool_BaseClass implements ReceiveDataListener {

	// Data
	private Vector<DataElementTded> vData = null;
	private Handler handler = null;
	private boolean resumeHasRun = false;
	private XMLParserTded xmlTded = null;
	// control
	private ProgressDialog progress = null;
	ExpandableListView listView = null;
	RelativeLayout layout = null;
	TextView txtDate = null;
	TextView txtMessage = null;
	TextView txtHeader = null;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sportpool_main);
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_tded, layout);
        
        listView = (ExpandableListView)layout.findViewById(R.id.tded_list);
        txtDate = (TextView)layout.findViewById(R.id.tded_txt_date);
        txtMessage = (TextView)layout.findViewById(R.id.tded_message);
        txtHeader = (TextView)layout.findViewById(R.id.tded_txt_header);
        txtHeader.setTypeface(superTypeface);
        txtMessage.setTypeface(superTypeface);
        
        layoutCenter.addView(layout);
        
        setupActionBar();
		setTranslucentStatus(true);
        setSlideMenu();
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		else return super.onOptionsItemSelected(item);
	}
	
	@Override 
	protected void onPause() 
	{
		super.onPause();
		//StartUp.setSetting(this, DataSetting.teamId, DataSetting.teamName, DataSetting.MatchTeamLike);
	}
	@Override
	protected void onStop() 
	{
		super.onStop();
		resumeHasRun = false;
	}
	@Override
	protected void onResume() 
	{
		super.onResume();
		CURRENT_PAGE_TH = getResources().getString(R.string.page_tded);
		CURRENT_PAGE = getResources().getString(R.string.page_tded_en);
		if (!resumeHasRun) {
			if( ISACTIVE == null || ISACTIVE.equals("") )
			{
				Intent intent = new Intent(this, SportPool_Logo.class);
    			startActivity(intent);
    			finish();
			}
			else if(ISACTIVE.equals("N"))
			{
				Intent intent = new Intent(this, SportPool_Active.class);
    			startActivity(intent);
    			finish();

			}
			else if(vGroupLeftMenu == null )
			{
				Intent intent = new Intent(this, SportPool_Logo.class);
				startActivity(intent);
				finish();
			}
			else GetURL();
		}
		init();
	};
	
	private void GetURL()
	{
		try
		{
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.Tded;
			url += "&sportType=00001" ;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+ DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "tded");
			load.execute(url);
			
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//getMenuInflater().inflate(R.menu.program, menu);
		return true;
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "tded")
			{

				vData = xmlTded.vData;

				final String message = xmlTded.message;
				txtDate.setText(xmlTded.date);
				if( message.equals("") )
				{

					final ListAdapterTded adaterResult = new ListAdapterTded(this,listView,vData);

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {

							txtMessage.setVisibility(View.INVISIBLE);
							listView.setVisibility(View.VISIBLE);

							listView.setAdapter(adaterResult);
							if( progress != null )progress.dismiss();
						}
					});

				}
				else
				{
					txtMessage.setVisibility(View.VISIBLE);
					listView.setVisibility(View.INVISIBLE);
					txtMessage.setText(message);
					if( progress != null )progress.dismiss();
				}

			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlTded = new XMLParserTded();
			Xml.parse(strOutput, xmlTded);
		}
		catch(Exception ex)
		{

		}
	}
}
