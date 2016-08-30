package com.isport.sportpool;

import java.io.InputStream;
import com.isport.sportpool.list.ListAdapterScore;
import com.isport.sportpool.service.ReceiveDataListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

import org.xml.sax.SAXException;

public class SportPool_SettingTeam_Score extends Fragment implements ReceiveDataListener {

	
	// Data
		private boolean resumeHasRun = false;
		// control
		private ListView listMatch = null;
		
    public static SportPool_SettingTeam_Score newInstance(String teamId) {
    	SportPool_SettingTeam_Score fragment = new SportPool_SettingTeam_Score();
        Bundle args = new Bundle();
        //fragment.teamId = teamId;
        fragment.setArguments(args);
        return fragment;
    }
		
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_score, container, false);
		listMatch = (ListView)rootView.findViewById(R.id.score_list_match);

		listMatch.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				Intent intent =  new Intent( getActivity(), SportPool_ResultDetail.class); //new Intent(this, SportPool_ResultDetail.class);
				//intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
				intent.putExtra("data", SportPool_BaseClass.vGroupScorebyTeam.get(position));
				intent.putExtra("header", SportPool_BaseClass.vGroupScorebyTeam.get(position).leagueData.contestGroupName);
				startActivity(intent);
				
			}
		});
		
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
		/*setContentView(R.layout.sportpool_score);
		
		if(getIntent().getExtras() != null)
		{
			teamId = (String) getIntent().getExtras().getString("teamid");
		}
        */

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
			if( SportPool_BaseClass.vGroupScorebyTeam.size() > 0 )
			{
				ListAdapterScore adaterResult = new ListAdapterScore(getActivity(),SportPool_BaseClass.vGroupScorebyTeam
						,SportPool_BaseClass.imgUtil,SportPool_BaseClass.gethImage190());
				listMatch.setAdapter(adaterResult);
			}
			else
			{
				//GetURL();
			}
			resumeHasRun = true;
		}
	}
	



	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
