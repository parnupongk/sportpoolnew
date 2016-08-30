package com.isport.sportpool;

import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterProgram;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserMatchProgram;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
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
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_Program extends SportPool_BaseClass implements ReceiveDataListener,OnClickListener,OnDateSetListener,OnChildClickListener {

	
	// Data
	private ArrayList<DataElementGroupProgram> mGroupData = null;
	private Handler handler = null;
	private Date date = null;
	private String day = "1";
	private String month = "1";
	private String year = "1";
	private boolean resumeHasRun = false;
	private boolean resumeHasDate = false;

	// control
	private ProgressDialog progress = null;
	private RelativeLayout layout = null;
	private ExpandableListView pullToRefreshView = null;
	private TextView txtDate = null;
	private TextView txtMessage = null;
	private XMLParserMatchProgram xmlProgram;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sportpool_main);
		
		
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_program, layout);
        
        txtDate = (TextView)layout.findViewById(R.id.program_txt_date);
        txtMessage = (TextView)layout.findViewById(R.id.program_message);
        txtDate.setTypeface(superTypeface);
        txtMessage.setTypeface(superTypeface);
        
        
        pullToRefreshView = (ExpandableListView) layout.findViewById(R.id.pull_to_refresh);
        pullToRefreshView.setOnChildClickListener(this);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        date = new Date(System.currentTimeMillis());
        getDate();
        
        layoutCenter.addView(layout);

		setSlideMenu();
		setupActionBar();
		setTranslucentStatus(true);

	}
	
	@Override 
	protected void onPause() 
	{
		super.onPause();
		//StartUp.setSetting(this, DataSetting.teamId, DataSetting.teamName, DataSetting.MatchTeamLike);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		resumeHasRun = false;
		finish();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		
		CURRENT_PAGE_TH =  getResources().getString(R.string.page_program);
		CURRENT_PAGE = getResources().getString(R.string.page_program_en);
		
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
			else
			{
				if(vGroupLeftMenu == null )
				{
					Intent intent = new Intent(this, SportPool_Logo.class);
					startActivity(intent);
					finish();
				}
				else 
					{
						init();
						GetURL();
					}
				resumeHasRun = true;
			}
		}
		
		
	}
	
	private void GetURL()
	{
		try
		{
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.program;
			url += "&date="+ year + month + day;
			url += "&sportType=00001" ;
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "program");
			load.execute(url);
			
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
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
		if(item.getItemId() == R.id.action_date )
		{
			DatePickerDialog dp = new DatePickerDialog(this, this, Integer.parseInt(year), Integer.parseInt(month)-1, Integer.parseInt(day));
			dp.setTitle("เลือกวันที่ต้องการ");
			dp.show();
			return true;
		}
		else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		else return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.program, menu);
		return true;
	}

	private void getDate()
	{
		String [] d = date.toString().split("-");
		year = d[0];
		if(d[1].length() == 1)
		{
			month = "0"+d[1];
		}
		else
		{
			month = d[1];
		}
		
		if(d[2].length() == 1)
		{
			day = "0"+d[2];
		}
		else
		{
			day = d[2];
		}
	}


	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		try
		{
			if( !resumeHasDate )
			{
				date = new Date(year-1900, monthOfYear, dayOfMonth);
				getDate();
				GetURL();
				resumeHasDate = true;
			}
		}
		catch(Exception ex)
		{
			//if( progress != null )progress.dismiss();
		}
		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		//mGroupData
		if( !mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).analyse.equals("") )
		{
			//CURRENT_PAGE_TH = "วิเคราะห์-วิจารณ์";
			//CURRENT_PAGE = "Analyse";
			Intent intent = new Intent(this, SportPool_Analyse.class);
			/*intent.putExtra("contestgroupid", mGroupData.get(groupPosition).leagueData.contestGroupId);
			intent.putExtra("teamid1", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode1);
			intent.putExtra("teamid2", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode2);
			intent.putExtra("matchid", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).matchId);
			intent.putExtra("teamname1", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name1);
			intent.putExtra("teamname2", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).name2);*/
			startActivity(intent);
			finish();
		}
		return false;
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if( xmlProgram != null)
			{
				//

				//android.util.Xml.parse(inputStream, Xml.Encoding.UTF_8, xmlProgram);
				String message = xmlProgram.message;
				txtDate.setText(xmlProgram.textDate);
				if( message.equals("") )
				{

					mGroupData = xmlProgram.groupProgram;

					final ListAdapterProgram adaterResult = new ListAdapterProgram(getApplicationContext(),pullToRefreshView,mGroupData
							,imgUtil,message,gethImage190());

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {

							resumeHasDate = false;
							txtMessage.setVisibility(View.INVISIBLE);
							pullToRefreshView.setVisibility(View.VISIBLE);
							pullToRefreshView.setAdapter(adaterResult);
							if(progress != null &&  progress.isShowing() )progress.dismiss();
						}
					});
				}
				else
				{
					txtMessage.setVisibility(View.VISIBLE);
					pullToRefreshView.setVisibility(View.INVISIBLE);
					txtMessage.setText(message);
				}

			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			Log.d("SportPool Error : " , ex.getMessage());
			//GetURL();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlProgram = new XMLParserMatchProgram();
		Xml.parse(strOutput,xmlProgram);
	}
}
