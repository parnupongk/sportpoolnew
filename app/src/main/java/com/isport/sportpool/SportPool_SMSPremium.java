package com.isport.sportpool;

import java.io.InputStream;

import com.isport.sportpool.data.DataElementSMSPremium;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterSMSPremium;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserSMSService;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ListView;

import org.xml.sax.SAXException;

public class SportPool_SMSPremium extends Fragment implements ReceiveDataListener,OnItemClickListener,OnClickListener {

	// Data
	private Handler handler = null;
	private boolean resumeHasRun = false;
	// control
	private ProgressDialog progress = null;
	private ListView listPremium = null;
	private ImageView imgView = null;
	private XMLParserSMSService xmlSMS=null;

    public static SportPool_SMSPremium newInstance(String param1, String param2) {
    	SportPool_SMSPremium fragment = new SportPool_SMSPremium();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.sportpool_smspremium);
		
        
        //listPremium = (ListView)findViewById(R.id.smspremium_list);
        //imgView = (ImageView)findViewById(R.id.smspremium_img_banner);
        //listPremium.setOnItemClickListener(this);
        //imgView.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_smspremium, container, false);
		listPremium = (ListView)rootView.findViewById(R.id.smspremium_list);
        imgView = (ImageView)rootView.findViewById(R.id.smspremium_img_banner);
        listPremium.setOnItemClickListener(this);
        imgView.setOnClickListener(this);
		
        GetURL();
		/*if(!resumeHasRun)
		{
			
			resumeHasRun = true;
		}*/
        /*
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
	        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		    params.setMargins(0, -90, 0, 0);
		    rootView.setLayoutParams(params);
        }
        */
        
		return rootView;
	}
	
	
	private void GetURL()
	{
		try
		{
			progress = ProgressDialog.show(getActivity(), null, "Loading...", true, true);
			
			String url = DataURL.SMSService;

			url += "&sportType=00001" ;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+ DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(getActivity(), this, null, "smsservice");
			load.execute(url);
			
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}


	@Override 
	public void onClick(View v) 
	{

		
		if(v == imgView && SportPool_BaseClass.vDataSMSPremium.size() > 0 )
		{
			final DataElementSMSPremium data = SportPool_BaseClass.vDataSMSPremium.elementAt(0);
			Intent i = new Intent(Intent.ACTION_VIEW);  
        	i.setData(Uri.parse(data.url));  
        	startActivity(i); 
		}
	};
	
	@Override
	public void onItemClick(AdapterView<?> arg0, View adap, int position, long arg3) {
		
		final DataElementSMSPremium data = SportPool_BaseClass.vDataSMSPremium.elementAt(position);
		final CharSequence[] items = {"Call "+data.phone, "Go To "+data.url};

		//imgView.setImage data.img320
		
		imgView.setImageBitmap(SportPool_BaseClass.hImage190.get(data.img320));
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Privilege Menu");
		builder.setItems(items, new DialogInterface.OnClickListener()
		{
		    public void onClick(DialogInterface dialog, int item)
		    {
		        if(item == 0)
		        {
		        	Intent intent = new Intent(Intent.ACTION_CALL);
		        	intent.setData(Uri.parse("tel:"+data.phone));
		            startActivity(intent);
		        }
		        else if(item == 1)
		        {
		        	Intent i = new Intent(Intent.ACTION_VIEW);  
		        	i.setData(Uri.parse(data.url));  
		        	startActivity(i); 
		        }
		    }
		});
		AlertDialog alert = builder.create();
		alert.show();
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "smsservice" && xmlSMS != null)
			{

				SportPool_BaseClass.vDataSMSPremium = xmlSMS.vDataSMSPremium;
				SportPool_BaseClass.vDataSMSFree = xmlSMS.vDataSMSFree;

				//final String message = xmlAnalyse.message;

				if( SportPool_BaseClass.vDataSMSPremium.size() > 0 )
				{
					//String img = imgUtil.scaleSize() > 1.2 ? vDataSMSPremium.get(0).img640 : vDataSMSPremium.get(0).img320 ;
					String img = SportPool_BaseClass.vDataSMSPremium.get(0).img320;

					if( SportPool_BaseClass.hImage190.get(img) == null )
					{
						AsycTaskLoadImage load = new AsycTaskLoadImage(imgView, null, null, this);
						load.execute(img);
					}
					else
					{
						imgView.setImageBitmap(SportPool_BaseClass.hImage190.get(img));
						//progress.setVisibility(View.INVISIBLE);
					}

					final ListAdapterSMSPremium listSMSPremium  = new ListAdapterSMSPremium(getActivity(), SportPool_BaseClass.vDataSMSPremium, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {

							listPremium.setAdapter(listSMSPremium);
							if( progress != null )progress.dismiss();
						}
					});

				}
				else
				{
					//program_message.setVisibility(View.VISIBLE);
					//program_list_data.setVisibility(View.INVISIBLE);
					//program_message.setText(message);
					if( progress != null )progress.dismiss();
				}

			}

		}
		catch(Exception ex)
		{
			if( progress != null )progress.dismiss();
			Log.d("SportPool Error : " , "SportPool " + ex.getMessage());
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlSMS = new XMLParserSMSService();
			Xml.parse(strOutput, xmlSMS);
		}
		catch(Exception ex)
		{
			//throw new Exception(ex.getMessage());
		}
	}
}
