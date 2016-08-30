package com.isport.sportpool;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterSMSFree;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserSMSService;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import org.xml.sax.SAXException;

@SuppressLint("NewApi")
public class SportPool_SMSFree extends Fragment implements ReceiveDataListener,OnItemClickListener {

	// Data
	private Handler handler = null;
	private boolean resumeHasRun = false;
	// control
	private ProgressDialog progress = null;
	private ListView listFree = null;
	private LayoutInflater inflater;
	private XMLParserSMSService xmlSMS = null;
	
    public static SportPool_SMSFree newInstance(String param1, String param2) {
    	SportPool_SMSFree fragment = new SportPool_SMSFree();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    
    
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_smsfree, container, false);
		listFree = (ListView)rootView.findViewById(R.id.smsfree_list);
        listFree.setOnItemClickListener(this);
        
        //if(!resumeHasRun)
		//{
			if( SportPool_BaseClass.vDataSMSFree != null && SportPool_BaseClass.vDataSMSFree.size() > 0 )
			{
				
				
				final ListAdapterSMSFree adapterSMSFree = new ListAdapterSMSFree(getActivity(), SportPool_BaseClass.vDataSMSFree, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());
				
				listFree.setAdapter(adapterSMSFree);
			}
			else GetURL();
			
			resumeHasRun = true;
		//}
        
	        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
		        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
			    params.setMargins(0, -90, 0, 0);
			    rootView.setLayoutParams(params);
	        }
	        
        return rootView;
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.sportpool_smsfree);
		
        

        if(getIntent().getExtras() != null)
		{

		}
        
        listFree = (ListView)findViewById(R.id.smsfree_list);
        listFree.setOnItemClickListener(this);*/
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
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			if(SportPool_BaseClass.vDataSMSFree != null)
			{
				Intent intent = new Intent(getActivity(), SportPool_NewsDetail.class);
				intent.putExtra("data", SportPool_BaseClass.vDataSMSFree.elementAt(position));
				intent.putExtra("header", "");
				startActivity(intent);
			}

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


					final ListAdapterSMSFree adapterSMSFree = new ListAdapterSMSFree(getActivity(), SportPool_BaseClass.vDataSMSFree, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());


					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							//program_message.setVisibility(View.INVISIBLE);
							//program_list_data.setVisibility(View.VISIBLE);
							//program_list_data.setAdapter(adaterResult);

							listFree.setAdapter(adapterSMSFree);
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

		}
	}
}
