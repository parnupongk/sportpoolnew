package com.isport.sportpool;


import com.isport.sportpool.data.DataElementGroupScoreResult;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterLiveScore;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserScoreResult;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
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

import java.util.ArrayList;

public class SportPool_Livescore extends SportPool_BaseClass implements ReceiveDataListener,OnChildClickListener {

	
		// Data
		private ArrayList<DataElementGroupScoreResult> mGroupData = null;
		private Handler handler = null;
		private boolean resumeHasRun = false;
		private XMLParserScoreResult xmlScore =null;
		
		// control
		private TextView txtMessage = null;
		private TextView txtDate = null;
		private ProgressDialog progress = null;
		private RelativeLayout layout = null;
		private ExpandableListView pullToRefreshView = null;
		
	@SuppressWarnings("deprecation")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sportpool_main);
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_livescore, layout);
        
        txtMessage = (TextView)layout.findViewById(R.id.page_ls_txt_null);
        txtDate  = (TextView)layout.findViewById(R.id.page_ls_txt_date);
        txtDate.setTypeface(superTypeface);
        pullToRefreshView = (ExpandableListView) layout.findViewById(R.id.page_ls_listview);
        pullToRefreshView.setOnChildClickListener(this);
        /*pullToRefreshView.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				new GetDataTask().execute();
			}
		});*/
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
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
	/*private class GetDataTask extends AsyncTask<Void, Void, String[]> {

	    @Override
	    protected void onPostExecute(String[] result) {
	    	GetURL();
	        pullToRefreshView.onRefreshComplete();
	        super.onPostExecute(result);
	    }

		@Override
		protected String[] doInBackground(Void... params) {			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			return null;
		}
	}*/
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

		CURRENT_PAGE_TH =  getResources().getString(R.string.page_livescore);
		CURRENT_PAGE = getResources().getString(R.string.page_livescore_en);
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
					resumeHasRun = true;
			 	}
			}
		}
		
	}
	
	private void GetURL()
	{
		try
		{
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.liveScore;
			url += "&contestgroupid=";
			url += "&sportType=00001" ;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+ DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "livescore");
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
		
		Intent intent = new Intent(this, SportPool_ResultDetail.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("data", mGroupData.get(groupPosition).GroupItemCollection.get(childPosition));
		intent.putExtra("header", mGroupData.get(groupPosition).leagueData);
		startActivity(intent);
		return false;
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "livescore")
			{

				mGroupData = xmlScore.mGroupData;

				final String message = xmlScore.message;

				if( message.equals("") )
				{

					final ListAdapterLiveScore adaterResult = new ListAdapterLiveScore(this,pullToRefreshView,mGroupData,imgUtil,message,gethImage190());
					txtDate.setText(xmlScore.textDate);
					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {

							txtMessage.setVisibility(View.GONE);
							pullToRefreshView.setVisibility(View.VISIBLE);

							pullToRefreshView.setAdapter(adaterResult);
							if( progress != null )progress.dismiss();
						}
					});

				}
				else
				{
					txtMessage.setVisibility(View.VISIBLE);
					pullToRefreshView.setVisibility(View.INVISIBLE);
					txtMessage.setText(message);
					if( progress != null )progress.dismiss();
				}

			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			Log.d("SportPool Error : " , ex.getMessage());
			GetURL();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlScore = new XMLParserScoreResult();

			Xml.parse(strOutput, xmlScore);
		}
		catch(Exception ex)
		{
		}
	}
}
