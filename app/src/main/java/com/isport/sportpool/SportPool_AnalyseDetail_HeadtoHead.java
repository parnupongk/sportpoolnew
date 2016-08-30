package com.isport.sportpool;

import com.isport.sportpool.list.ListAdapterHeadtoHead;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import org.xml.sax.SAXException;

public class SportPool_AnalyseDetail_HeadtoHead extends Fragment implements ReceiveDataListener,OnClickListener {

	// Data

	private Handler handler = null;
	private String contestGroupId = null;
	private String teamId1 = null;
	private String teamId2 = null;
	private String matchId = null;
	private String teamName1 = null;
	private String teamName2 = null;
	private boolean resumeHasRun = false;
	private XMLParserAnalyseDetail xmlAnalyse=null;
	// control
	private ProgressDialog progress = null;
	private RelativeLayout layout = null;

	private TextView txtHeaderHTOH = null;
	private TextView txtHeaderHadicap = null;
	private TextView txtHadicap = null;
	private TextView txtHeaderDetail = null;
	private TextView txtDetail = null;
	private TextView txtHeaderResult = null;
	private TextView txtResult = null;
	private ListView listStatic = null;
	//private ListView listTeam1 = null;
	//private ListView listTeam2 = null;
	//private ListView listHistory = null;
	private ImageView imgTeam1 = null;
	private ImageView imgTeam2 = null;
	//private TextView txtTeam1Stat = null;
	//private TextView txtTeam2Stat = null;
	private LinearLayout layoutHead = null;
	
	private TabHost tabMenu = null;
	private LocalActivityManager localManager = null;
	
    public static SportPool_AnalyseDetail_HeadtoHead newInstance(String contestGroupId, String teamId1,String teamId2,
    		String matchId,String teamName1 ,String teamName2) {
    	SportPool_AnalyseDetail_HeadtoHead fragment = new SportPool_AnalyseDetail_HeadtoHead();
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
		View rootView = inflater.inflate(R.layout.sportpool_analysedetail_headtohead, container, false);
		
		layoutHead = (LinearLayout)rootView.findViewById(R.id.analysedetail_layout_analyse);
        txtHeaderHTOH = (TextView)rootView.findViewById(R.id.analysedetail_txt_header_htoh);
        txtHeaderHadicap = (TextView)rootView.findViewById(R.id.analysedetail_txt_headerhandicap);
        txtHadicap = (TextView)rootView.findViewById(R.id.analysedetail_txt_handicap);
        txtHeaderDetail = (TextView)rootView.findViewById(R.id.analysedetail_txt_headerdetail);
        txtDetail = (TextView)rootView.findViewById(R.id.analysedetail_txt_detail);
        txtHeaderResult = (TextView)rootView.findViewById(R.id.analysedetail_txt_headerresult);
        txtResult = (TextView)rootView.findViewById(R.id.analysedetail_txt_result);
        listStatic = (ListView)rootView.findViewById(R.id.analysedetail_list_static);
        imgTeam1 = (ImageView)rootView.findViewById(R.id.analysedetail_img_staticteam1);
        imgTeam2 = (ImageView)rootView.findViewById(R.id.analysedetail_img_staticteam2);

        
        
        txtHadicap.setTypeface(SportPool_BaseClass.superTypeface);
		txtHeaderDetail.setTypeface(SportPool_BaseClass.superTypeface);
		txtDetail.setTypeface(SportPool_BaseClass.superTypeface);
		txtHeaderResult.setTypeface(SportPool_BaseClass.superTypeface);
		txtResult.setTypeface(SportPool_BaseClass.superTypeface);
        
        //listStatic.setDivider(null);
        //listStatic.setDividerHeight(0);
        //txtTeam1Stat.setText(teamName1);
        //txtTeam2Stat.setText(teamName2);
        	//this.set(this);
        layoutHead.setOnClickListener(this);
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
		/*setContentView(R.layout.sportpool_analysedetail_headtohead);
		
        

        if(getIntent().getExtras() != null)
		{
        	contestGroupId = (String) getIntent().getExtras().getString("contestgroupid");
			teamId1 =(String) getIntent().getExtras().getString("teamid1");
			teamId2 =(String) getIntent().getExtras().getString("teamid2");
			matchId =(String) getIntent().getExtras().getString("matchid");
			teamName1 = (String) getIntent().getExtras().getString("teamname1");
			teamName2 = (String) getIntent().getExtras().getString("teamname2");
		}*/
        
        
        
	}

	@Override
	public void onStop() 
	{
		super.onStop();
		resumeHasRun = false;
	}
	@Override
	public void onResume() 
	{
		super.onResume();
		try
		{
			if (!resumeHasRun) {
			if( SportPool_BaseClass.dataAnalyseDetail != null && SportPool_BaseClass.vDataEleHeadDetail != null )
			{
				GenData();
			}
			//else 
				//GetURL();
				resumeHasRun = true;
			}
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , "SportPool " + ex.getMessage());
		}
	};

	
	private void GenData() throws Exception
	{
		try
		{
			txtHeaderHadicap.setText("อัตราต่อรอง");
			txtHadicap.setText(SportPool_BaseClass.dataAnalyseDetail.handicap);
			txtHeaderDetail.setText("ความน่าจะเป็นของเกม");
			txtDetail.setText(SportPool_BaseClass.dataAnalyseDetail.analyse);
			txtHeaderResult.setText("ผลการแข่งขันที่คาด");
			txtResult.setText(SportPool_BaseClass.dataAnalyseDetail.result);
			
				txtHeaderHTOH.setText("ผลการพบกันที่ผ่านมา");

			
				imgTeam1.setImageResource(R.drawable.team1);
				imgTeam2.setImageResource(R.drawable.team2);
				
				final ListAdapterHeadtoHead adapterHead = new ListAdapterHeadtoHead(getActivity(), SportPool_BaseClass.vDataEleHeadDetail, SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190());
				//final ListAdapterStatTeam adapterTeam1 = new ListAdapterStatTeam(this, vDataStaticTeam1, imgUtil, gethImage190());
				//final ListAdapterStatTeam adapterTeam2 = new ListAdapterStatTeam(this, vDataStaticTeam2, imgUtil, gethImage190());
				
				handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {
						//program_message.setVisibility(View.INVISIBLE);
						//program_list_data.setVisibility(View.VISIBLE);
						//program_list_data.setAdapter(adaterResult);
						listStatic.setAdapter(adapterHead);
						//listTeam1.setAdapter(adapterTeam1);
						//listTeam2.setAdapter(adapterTeam2);
						if( progress != null )progress.dismiss();
					}
				});
			
			
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}

	@Override
	public void onClick(View v) 	{


		
	}



	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try
		{
			if(loadName == "analyse")
			{



				SportPool_BaseClass.dataAnalyseDetail = xmlAnalyse.dataElementAnalyseDetail;
				SportPool_BaseClass.vDataHeadtoHead = xmlAnalyse.vDataEleAnalyseHeadToHead;
				SportPool_BaseClass.vDataEleHeadDetail = xmlAnalyse.vDataEleHeadDetail;
				SportPool_BaseClass.vDataStaticTeam1 = xmlAnalyse.vDataEleLeagueStaticTeam1;
				SportPool_BaseClass.vDataStaticTeam2 = xmlAnalyse.vDataEleLeagueStaticTeam2;
				SportPool_BaseClass.vDataLeagueTable = xmlAnalyse.vDataEleLeagueTable;

				final String message = xmlAnalyse.message;

				if( message.equals("") )
				{

					GenData();

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
			xmlAnalyse = new XMLParserAnalyseDetail();
			Xml.parse(strOutput, xmlAnalyse);
		}
		catch(Exception ex)
		{

		}
	}
}
