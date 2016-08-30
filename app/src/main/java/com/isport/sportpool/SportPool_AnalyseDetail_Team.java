package com.isport.sportpool;


import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterStatTeam;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserAnalyseDetail;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_AnalyseDetail_Team extends Fragment implements Runnable,ReceiveDataListener {

	// Data
	private Handler handler = null;
	private String contestGroupId = null;
	private String teamId1 = null;
	private String teamId2 = null;
	private String matchId = null;
	private String teamName1 = null;
	private String teamName2 = null;
	private boolean resumeHasRun = false;
	// control
	private ProgressDialog progress = null;
	private RelativeLayout layout = null;

	private LinearLayout headerTeam1 = null;
	private LinearLayout headerTeam2 = null;
	private ListView listTeam1 = null;
	private ListView listTeam2 = null;
	//private ListView listHistory = null;

	private TextView txtTeam1Stat = null;
	private TextView txtTeam2Stat = null;
	
	private TabHost tabMenu = null;
	private LocalActivityManager localManager = null;
	XMLParserAnalyseDetail xmlAnalyse=null;
	
    public static SportPool_AnalyseDetail_Team newInstance(String contestGroupId, String teamId1,String teamId2,
    		String matchId,String teamName1 ,String teamName2) {
    	SportPool_AnalyseDetail_Team fragment = new SportPool_AnalyseDetail_Team();
        Bundle args = new Bundle();

        
        fragment.contestGroupId = contestGroupId;
        fragment.teamId1 =teamId1;
        fragment.teamId2 =teamId2;
        fragment.matchId =matchId;
        fragment.teamName1 = teamName1;
        fragment.teamName2 = teamName2;
        
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.sportpool_analysedetail_team, container, false);
        headerTeam1 = (LinearLayout)rootView.findViewById(R.id.analysedetail_header_team1);
        headerTeam2 = (LinearLayout)rootView.findViewById(R.id.analysedetail_header_team2);
        listTeam1 = (ListView)rootView.findViewById(R.id.analysedetail_list_team1);
        listTeam2 = (ListView)rootView.findViewById(R.id.analysedetail_list_team2);
        //listHistory = (ListView)findViewById(R.id.analysedetail_list_history);

        txtTeam1Stat = (TextView)rootView.findViewById(R.id.analysedetail_txtstat_team1);
        txtTeam2Stat = (TextView)rootView.findViewById(R.id.analysedetail_txtstat_team2);
        txtTeam1Stat.setTypeface(SportPool_BaseClass.superTypeface);
        txtTeam2Stat.setTypeface(SportPool_BaseClass.superTypeface);
        
        txtTeam1Stat.setText(teamName1);
        txtTeam2Stat.setText(teamName2);

        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
	        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		    params.setMargins(0, -90, 0, 0);
		    rootView.setLayoutParams(params);
        }*/
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.sportpool_analysedetail_team);
		
		

        if(getIntent().getExtras() != null)
		{
        	contestGroupId = "";//(String) getIntent().getExtras().getString("contestgroupid");
			teamId1 =(String) getIntent().getExtras().getString("teamid1");
			teamId2 =(String) getIntent().getExtras().getString("teamid2");
			matchId =(String) getIntent().getExtras().getString("matchid");
			teamName1 = (String) getIntent().getExtras().getString("teamname1");
			teamName2 = (String) getIntent().getExtras().getString("teamname2");
		}*/
        

	}

	private LinearLayout SetHeader(LinearLayout header)
	{
		header.setBackgroundResource(R.drawable.bg_list_head);
		TextView txtDate_ = new TextView(getActivity());
		TextView txtTo_ = new TextView(getActivity());
		TextView txtMatch_ = new TextView(getActivity());
		TextView txt1_ = new TextView(getActivity());
		TextView txtX_ = new TextView(getActivity());
		TextView txt2_ = new TextView(getActivity());
		TextView txtFt_ = new TextView(getActivity());
		TextView txtRo_ = new TextView(getActivity());
		
		txtDate_.setText("Date");
		//txtTo.setText("To");
		txtMatch_.setText("Match");
		//txt1_.setText("1");
		//txtX_.setText("X");
		txt2_.setText("2");
		txtFt_.setText("Ft");
		txtRo_.setText("Ro");
		

		txtDate_.setWidth((int)(100*SportPool_BaseClass.imgUtil.scaleSize()));
		//txtTo.setWidth((int)(125*imgUtil.scaleSize()));
		txtMatch_.setWidth((int)(200*SportPool_BaseClass.imgUtil.scaleSize()));
		//txt1_.setWidth((int)(50*imgUtil.scaleSize()));
		//txtX_.setWidth((int)(50*imgUtil.scaleSize()));
		txt2_.setWidth((int)(50*SportPool_BaseClass.imgUtil.scaleSize()));
		txtFt_.setWidth((int)(50*SportPool_BaseClass.imgUtil.scaleSize()));
		txtRo_.setWidth((int)(50*SportPool_BaseClass.imgUtil.scaleSize()));
		
		txtDate_.setGravity(Gravity.CENTER);
		//txtTo.setGravity(Gravity.CENTER);
		txtMatch_.setGravity(Gravity.CENTER);
		//txt1_.setGravity(Gravity.CENTER);
		//txtX_.setGravity(Gravity.CENTER);
		txt2_.setGravity(Gravity.CENTER);
		txtFt_.setGravity(Gravity.CENTER);
		txtRo_.setGravity(Gravity.CENTER);
		
		header.addView(txtDate_);
		header.addView(txtMatch_);
		//header.addView(txt1_);
		//header.addView(txtX_);
		header.addView(txt2_);
		header.addView(txtFt_);
		header.addView(txtRo_);
		
		return header;
	}
	
	@Override
	public void onStop() 
	{
		super.onStop();
		resumeHasRun = false;
	};
	@Override
	public void onResume() 
	{
		super.onResume();
		
		if(!resumeHasRun)
		{
			if( SportPool_BaseClass.vDataStaticTeam1 != null && SportPool_BaseClass.vDataStaticTeam2 != null )
			{
			
		        headerTeam1 = SetHeader(headerTeam1);
		        headerTeam2 = SetHeader(headerTeam2);
				
				final ListAdapterStatTeam adapterTeam1 = new ListAdapterStatTeam(getActivity(), SportPool_BaseClass.vDataStaticTeam1, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());
				listTeam1.setAdapter(adapterTeam1);
				
				//adapterTeam1.
				
				final ListAdapterStatTeam adapterTeam2 = new ListAdapterStatTeam(getActivity(), SportPool_BaseClass.vDataStaticTeam2, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());
				listTeam2.setAdapter(adapterTeam2);
						
				if( progress != null )progress.dismiss();
						
				/*handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						
						
						
					}
				});*/
				
				
				
			}
			//else GetURL();
			
			resumeHasRun = true;
		}
	};
	@Override
	public void run() {


		if( progress != null )progress.dismiss();
		
		
	}
	private void GetURL()
	{
		try
		{
			//progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.footballanalysedetail;
			url += "&contestgroupid=" + contestGroupId;
			url += "&teamcode1=" + teamId1;
			url += "&teamcode2=" + teamId2;
			url += "&matchid=" + matchId;
			url += "&sportType=00001" ;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+ DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(getActivity(), this, null, "analyse");
			load.execute(url);
			
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "analyse")
			{

				SportPool_BaseClass.vDataStaticTeam1 = xmlAnalyse.vDataEleLeagueStaticTeam1;
				SportPool_BaseClass.vDataStaticTeam2 = xmlAnalyse.vDataEleLeagueStaticTeam2;
				SportPool_BaseClass.vDataLeagueTable = xmlAnalyse.vDataEleLeagueTable;

				final String message = xmlAnalyse.message;

				if( message.equals("") )
				{

					final ListAdapterStatTeam adapterTeam1 = new ListAdapterStatTeam(getActivity(), SportPool_BaseClass.vDataStaticTeam1, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());
					final ListAdapterStatTeam adapterTeam2 = new ListAdapterStatTeam(getActivity(), SportPool_BaseClass.vDataStaticTeam2, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());

					handler = new Handler();
					handler.post(new Runnable() {
						@Override
						public void run() {
							//program_message.setVisibility(View.INVISIBLE);
							//program_list_data.setVisibility(View.VISIBLE);
							//program_list_data.setAdapter(adaterResult);

							listTeam1.setAdapter(adapterTeam1);
							listTeam2.setAdapter(adapterTeam2);
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
		xmlAnalyse = new XMLParserAnalyseDetail();
		Xml.parse(strOutput, xmlAnalyse);
	}
}
