package com.isport.sportpool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONObject;
import org.xml.sax.SAXException;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.Button;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class SportPool_Active extends SportPool_BaseClass implements ReceiveDataListener,OnClickListener {
	
	private TextView txtHeader = null;
	private TextView txtDetail = null;
	private TextView txtFooter = null;
	private TextView txtFooter1 = null;
	//private EditText txtNumber = null;
	private Button btnSubmit = null;
	private RelativeLayout layout = null;
	private ProgressDialog progress = null;
	//private boolean isGenOTP = true;
	//private boolean isSubmitOTP = false;
	private String msisdn = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.sportpool_main);

        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_active, layout);

        txtHeader = (TextView)layout.findViewById(R.id.active_txt_header);
        txtDetail = (TextView)layout.findViewById(R.id.active_txt_detail);
        txtFooter = (TextView)layout.findViewById(R.id.active_txt_footer);
        txtFooter1 = (TextView)layout.findViewById(R.id.active_txt_footer1);
        btnSubmit = (Button)layout.findViewById(R.id.active_submit);
        //txtNumber = (EditText)layout.findViewById(R.id.active_txt_mobile);
        
        btnSubmit.setOnClickListener(this);
        
        txtHeader.setText(ACTIVE_HEADER);
		txtDetail.setText(ACTIVE_DETAIL);
		txtFooter.setText(ACTIVE_FOOTER);
		txtFooter1.setText(ACTIVE_FOOTER1);
		
		txtHeader.setTypeface(SportPool_BaseClass.superTypeface);
		txtDetail.setTypeface(SportPool_BaseClass.superTypeface);
		txtFooter.setTypeface(SportPool_BaseClass.superTypeface);
		txtFooter1.setTypeface(SportPool_BaseClass.superTypeface);
		
		layoutCenter.addView(layout);
		
		
		 //LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	    //params.setMargins(0, -50, 0, 0);
	    //layoutCenter.setLayoutParams(params);
		
        setupActionBar();
		setTranslucentStatus(true);
        setSlideMenu();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
   /* private void GetOTP() throws Exception
    {
    	try
    	{
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.GetOTP;
			//url += "&ano="+ txtNumber.getText();
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"genOTP");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }*/
    private void SubmitOTP() throws Exception
    {
    	try
    	{
    		
    		progress = ProgressDialog.show(this, null, "Loading...", true, true);
			String url = DataURL.SubmitOTP;
			url += "&otp="+ MSISDN;
			url += "&ano="+ msisdn;
			url += "&lang="+ DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"submitOTP");
	        load.execute(url);
	        
    	}
    	catch(Exception ex)
    	{
    		throw new Exception(ex.getMessage());
    	}
    }
    
    @Override
    protected void onStop() 
    {
    	super.onStop();
    	finish();
    }
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();
    		
    		CURRENT_PAGE  = getResources().getString(R.string.page_active_en);
    		CURRENT_PAGE_TH = getResources().getString(R.string.page_active);
    		if( MSISDN.equals("") )
    		{
    			btnSubmit.setVisibility(View.GONE);
    		}
    		if( ACTIVE_HEADER.equals("") )
    		{
    			Intent intent = new Intent(this, SportPool_Logo.class);
    			startActivity(intent);
    			finish();
    		}
    		init();
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

	private String readStream(InputStream iStream) throws IOException {
        //build a Stream Reader, it can read char by char
        InputStreamReader iStreamReader = new InputStreamReader(iStream);
        //build a buffered Reader, so that i can read whole line at once
        BufferedReader bReader = new BufferedReader(iStreamReader);
        String line = null;
        StringBuilder builder = new StringBuilder();
        while((line = bReader.readLine()) != null) {  //Read till end
            builder.append(line);
        }
        bReader.close();         //close all opened stuff
        iStreamReader.close();
        iStream.close();
        return builder.toString();
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try
		{
			//if( v == btnSubmit && isGenOTP)
			//{
				//btnSubmit.setBackgroundResource(R.drawable.btn_regis_down);
				//msisdn = txtNumber.getText().toString();
				//GetOTP();
			//}
			//else if(v == btnSubmit && isSubmitOTP)
			//{
				//btnSubmit.setBackgroundResource(R.drawable.btn_regis_down);
				SubmitOTP();
			//}
		}
		catch(Exception ex)
		{
			//PrintLog.printException(this, "Note", ex);
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
		if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		else return super.onOptionsItemSelected(item);
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

		try
		{
			if( strOutput != null )
			{
				JSONObject cc = new JSONObject(strOutput) ;
				JSONObject bb =cc.getJSONObject("SportApp");
				JSONObject c = bb.getJSONObject("Response");
				if(loadName == "submitOTP")
				{
					if( c.getString("isactive").equals("N") )
					{
						txtDetail.setText(c.getString("detail"));
						//txtNumber.setText("");
						progress.dismiss();
					}
					else if(c.getString("isactive").equals("Y"))
					{
						ISACTIVE = c.getString("isactive");
						Toast.makeText(this, c.getString("detail"), Toast.LENGTH_SHORT).show();
						Intent intent = new Intent(this, SportPool_Program.class);
						startActivity(intent);
						this.finish();
					} else if(c.getString("isactive").equals("E"))
					{
						// ตัด MT ไม่ได้ 7 วันติดต่อกัน
						txtDetail.setText(c.getString("detail"));
						btnSubmit.setVisibility(View.GONE);
						progress.dismiss();
					}

				}
				//btnSubmit.setBackgroundResource(R.drawable.btn_regis);
			}
		}
		catch(Exception ex)
		{
			progress.dismiss();
			//PrintLog.printException(this, "Note", ex);
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
