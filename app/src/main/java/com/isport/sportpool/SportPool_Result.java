package com.isport.sportpool;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

import com.isport.sportpool.data.DataElementGroupScoreResult;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterResult;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserScoreResult;

import android.app.DatePickerDialog.OnDateSetListener;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;


public class SportPool_Result extends SportPool_BaseClass implements ReceiveDataListener,OnDateSetListener,OnChildClickListener,OnClickListener {
	
	private Handler handler = null;
	private RelativeLayout layout = null;
	private ExpandableListView result_list_data = null;
	private ProgressDialog progress = null;
	private boolean resumeHasRun = false;
	private boolean resumeHasDate = false;
	private ArrayList<DataElementGroupScoreResult> mGroupData = null;
	private TextView txtMessage = null;
	private TextView txtDate = null;
	private XMLParserScoreResult xmlResult =null;
	
	private Date date = null;
	private String day = "1";
	private String month = "1";
	private String year = "1";
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportpool_main);
       
        DataSetting.contentGroupId = "";
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_result, layout);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        txtMessage = (TextView)layout.findViewById(R.id.result_txt_null);
        txtDate = (TextView)layout.findViewById(R.id.result_txt_date);
        txtDate.setTypeface(superTypeface);
        txtMessage.setTypeface(superTypeface);
        
        result_list_data =(ExpandableListView)layout.findViewById(R.id.result_list_match);
        result_list_data.setOnChildClickListener(this);
        //result_list_data.setDivider(null);
        //result_list_data.setDividerHeight(0);        
        
        date = new Date(System.currentTimeMillis());
        getDate();
        
        layoutCenter.addView(layout);
        
        setupActionBar();
		setTranslucentStatus(true);
        setSlideMenu();
    }

    @Override
    protected void onPause() 
    {
    	super.onPause();

    };
    @Override
    protected void onStop() {
    	super.onStop();
    	resumeHasRun = false;
    	DataSetting.contentGroupId = "";
		finish();
    }

    @Override
    protected void onResume() {
    	try {
				super.onResume();
				CURRENT_PAGE_TH = getResources().getString(R.string.page_result);
				CURRENT_PAGE = getResources().getString(R.string.page_result_en);
				
				if (!resumeHasRun) {
					
					if(vGroupLeftMenu == null )
					{
						Intent intent = new Intent(this, SportPool_Logo.class);
						startActivity(intent);
						finish();
					}
					else if( ISACTIVE == null || ISACTIVE.equals("") )
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
					else if( !SERVERVERSION.equals(DataSetting.VERSION) )
					{
						AlertDialog.Builder builder = new AlertDialog.Builder(this);
			        	builder.setMessage("Please Update Version")
			        	       .setCancelable(false)
			        	       .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			        	           public void onClick(DialogInterface dialog, int id) {
			        	        	   Intent i = new Intent(Intent.ACTION_VIEW);  
			   			        		i.setData(Uri.parse(DataURL.playstore));  
			   			        		startActivity(i); 
			        	                finish();
			        	           }
			        	       });
			        	AlertDialog alert = builder.create();
			        	alert.show();
					}
					else
					{
				        resumeHasRun = true;				       
				        init();
				        DataBinding();
				        //return;
					}
			    }
				
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			//PrintLog.printException(this, "", e);
		}
    }

    private void DataBinding() throws Exception
    {
    	try
    	{
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.ScoreResult;
			url += "&date="+ year + month + day;
			url += "&sportType=00001";
			url += "&contestgroupid="+DataSetting.contentGroupId;
			url += "&countryid=";
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+ DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"Result");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
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
			if(!resumeHasDate)
			{
				date = new Date(year-1900, monthOfYear, dayOfMonth);
		
				getDate();
				DataBinding();
				resumeHasDate = true;
			}
		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			//PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public boolean onChildClick(ExpandableListView arg0, View v, int intGroup,
			int intChild, long arg4) {
		
		v.setBackgroundResource(R.drawable.bg_list_league);
		Intent intent = new Intent(this, SportPool_ResultDetail.class);
		//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		intent.putExtra("data", mGroupData.get(intGroup).GroupItemCollection.get(intChild));
		intent.putExtra("header", mGroupData.get(intGroup).leagueData);
		startActivity(intent);
		
		return false;
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

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "Result")
			{

				mGroupData = xmlResult.mGroupData;

				final String message = xmlResult.message;
				txtDate.setText(xmlResult.textDate);
				if(message.trim().equals(""))
				{

					final ListAdapterResult adaterResult = new ListAdapterResult(this,result_list_data,mGroupData,imgUtil,"",gethImage190());

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							txtMessage.setVisibility(View.GONE);
							result_list_data.setVisibility(View.VISIBLE);
							resumeHasDate = false;
							result_list_data.setAdapter(adaterResult);
							if( progress != null )progress.dismiss();


						}
					});
				}
				else
				{
					txtMessage.setVisibility(View.VISIBLE);
					result_list_data.setVisibility(View.GONE);
					txtMessage.setText(message);
					if( progress != null )progress.dismiss();
				}

			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			//PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlResult = new XMLParserScoreResult();
			Xml.parse(strOutput, xmlResult);
		}
		catch(Exception ex)
		{

		}
	}
}


