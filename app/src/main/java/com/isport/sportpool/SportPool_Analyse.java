package com.isport.sportpool;

import java.io.InputStream;
import java.util.ArrayList;

import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterProgram;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserMatchProgram;

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
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_Analyse extends SportPool_BaseClass implements ReceiveDataListener,OnChildClickListener {

	// Data
	private ArrayList<DataElementGroupProgram> mGroupData = null;
	private Handler handler = null;
	private boolean resumeHasRun = false;
	private XMLParserMatchProgram xmlProgram = null;
	// control
	private ProgressDialog progress = null;
	ExpandableListView listView = null;
	RelativeLayout layout = null;
	TextView txtMessage = null;
	
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sportpool_main);
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_analyse, layout);
        
        listView = (ExpandableListView)layout.findViewById(R.id.score_list_analyse);
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        txtMessage = (TextView)layout.findViewById(R.id.analyse_message);
        txtMessage.setTypeface(superTypeface);
        listView.setOnChildClickListener(this);
        
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
		
		CURRENT_PAGE_TH = getResources().getString(R.string.page_analyse);
		CURRENT_PAGE = getResources().getString(R.string.page_analyse_en);
		
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
			else if (!resumeHasRun) 
				{
					if(vGroupLeftMenu == null )
					{
						Intent intent = new Intent(this, SportPool_Logo.class);
						startActivity(intent);
						finish();
					}
					else GetURL();
					
					resumeHasRun = true;
				}
		}
		
		init();
	};
	
	private void GetURL()
	{
		try
		{
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.programanalyse;
			url += "&sportType=00001" ;
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "analyse");
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
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		//mGroupData
		Intent intent = new Intent(this, SportPool_AnalyseDetail.class);
		intent.putExtra("contestgroupid", mGroupData.get(groupPosition).leagueData.contestGroupId);
		intent.putExtra("teamid1", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode1);
		intent.putExtra("teamid2", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode2);
		intent.putExtra("matchid", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).matchId);
		intent.putExtra("teamname1", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name1);
		intent.putExtra("teamname2", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name2);
		startActivity(intent);
		return false;
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "analyse")
			{

				mGroupData = xmlProgram.groupProgram;

				final String message = xmlProgram.message;

				if( message.equals("") )
				{

					final ListAdapterProgram adaterResult = new ListAdapterProgram(this,listView,mGroupData
							,imgUtil,message,gethImage190());

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
			xmlProgram = new XMLParserMatchProgram();
			Xml.parse(strOutput, xmlProgram);
		}
		catch(Exception ex)
		{

		}
	}
}
