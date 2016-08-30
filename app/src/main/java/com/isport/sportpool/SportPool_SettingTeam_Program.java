package com.isport.sportpool;

import java.io.InputStream;
import java.util.ArrayList;

import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.list.ListAdapterProgram;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserMatchProgramScoreByTeam;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout.LayoutParams;

import org.xml.sax.SAXException;

public class SportPool_SettingTeam_Program extends Fragment implements ReceiveDataListener {

	
	// Data
		private ArrayList<DataElementGroupProgram> mGroupData = null;
		private String teamId = null;
		private XMLParserMatchProgramScoreByTeam xmlProgram = null;
		// control
		private ExpandableListView listProgram = null;

		
    public static SportPool_SettingTeam_Program newInstance(String teamId) {
    	SportPool_SettingTeam_Program fragment = new SportPool_SettingTeam_Program();
        Bundle args = new Bundle();
        fragment.teamId = teamId;
        fragment.setArguments(args);
        return fragment;
    }
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_program_byteam, container, false);
		listProgram = (ExpandableListView)rootView.findViewById(R.id.programbyteam_list_program);
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
		/*setContentView(R.layout.sportpool_program_byteam);
		
		if(getIntent().getExtras() != null)
		{
			teamId = (String) getIntent().getExtras().getString("teamid");
		}
		
        listProgram = (ExpandableListView)findViewById(R.id.programbyteam_list_program);*/
	}
	
	@Override
	public void onPause() 
	{
		super.onPause();
		//StartUp.setSetting(this, DataSetting.teamId, DataSetting.teamName, DataSetting.MatchTeamLike);
	}
	@Override
	public void onStop() 
	{
		super.onStop();
		//resumeHasRun = false;
	}
	@Override
	public void onResume() 
	{
		super.onResume();
		if(SportPool_BaseClass.vGroupProgrambyTeam != null)
		{
			//GetURL();
			final ListAdapterProgram adaterResult = new ListAdapterProgram(getActivity(),listProgram,SportPool_BaseClass.vGroupProgrambyTeam
					,SportPool_BaseClass.imgUtil,"",SportPool_BaseClass.gethImage190());
			listProgram.setAdapter(adaterResult);
			//resumeHasRun = true;
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
