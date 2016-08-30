package com.isport.sportpool;

import java.io.InputStream;
import java.util.Vector;

import com.isport.sportpool.data.DataElementPlayer;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserPlayer;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_Player extends Fragment implements ReceiveDataListener, OnClickListener
{
	
	private ProgressDialog progress = null;
	private Handler handler = null;
	private String teamId = null; 
	private String teamName = null;
	private Vector<DataElementPlayer> vData = null;
	private boolean resumeHasRun = false;
	private XMLParserPlayer xmlPar = null;
	
	// Control
	private ImageView imgTeam = null;
	private TextView txtTeam = null;
	private TableLayout table = null;
	
	
	TableRow row = null;
	TextView shirt = null;
	TextView name = null;
	TextView countryName = null;
	TextView position = null;
	TextView dateBirth = null;
	TextView hight = null;
	TextView wight = null;
	
	public static SportPool_Player newInstance(String teamId,String teamName) {
		SportPool_Player fragment = new SportPool_Player();
        Bundle args = new Bundle();
        fragment.teamId = teamId;
        fragment.teamName = teamName;
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_player, container, false);
		
		imgTeam = (ImageView)rootView.findViewById(R.id.player_img_team);
		txtTeam = (TextView)rootView.findViewById(R.id.player_txt_team);
		table = (TableLayout)rootView.findViewById(R.id.player_layout_table);
        
		txtTeam.setText(teamName);
		imgTeam.setImageResource(R.drawable.ic_favourite_press);
		
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
	        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		    params.setMargins(0, -90, 0, 0);
		    rootView.setLayoutParams(params);
        }
        
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		/*setContentView(R.layout.sportpool_player);
		
		if(getIntent().getExtras() != null)
		{
			teamId = (String) getIntent().getExtras().getString("teamid");
			teamName = (String) getIntent().getExtras().getString("teamname");
		}
		
		handler = new Handler();*/


	}
	
	private void setURL()
	{
		//progress = ProgressDialog.show(getActivity() , null, "Loading...", true, true);
		
		//contestGroupId=&lang=&code=210002a&type=bb
		String URL = DataURL.playerbyteam;
			URL += "&teamid="+teamId;
			URL += "&sportType=00001";
			URL += "&lang="+ DataSetting.Languge;
			URL += "&imei="+DataSetting.IMEI;
			URL += "&model="+DataSetting.MODEL;
			URL += "&imsi="+DataSetting.IMSI;
			URL += "&type="+DataSetting.TYPE;
        
        AsycTaskLoadData load = new AsycTaskLoadData(getActivity(), this,null,"player");
        load.execute(URL);
	}
	
	
	@Override
	public void onResume()
	{
		super.onResume();
		try
		{
			if(!resumeHasRun)
			{
				/*if(vGroupLeftMenu == null )
				{
					Intent intent = new Intent(this, SportPool_Logo.class);
					startActivity(intent);
					finish();
				}
				else
				{
					//progress = ProgressDialog.show(this, null, "Loading...", true, true);
					//GetDataListMenuLeague menu = new GetDataListMenuLeague(this, vGroupListMenu, imgUtil, hImage190, page_lt_league_gallery, DataSetting.contentGroupId,false);
			        //menu.SetAdapterMenu();
			        
					setURL();
				}*/
				setURL();
				resumeHasRun = true;
			}
		}
		catch(Exception ex)
		{
			Log.d("SportPool", "SportPool "+ex.getMessage());
			
		}
	}
    
	@Override
	public void onStop() {
    	super.onStop();
    	resumeHasRun = false;
    }
	
	private void AddDataLeagueTable(DataElementPlayer data,TableRow row,TextView shirt,TextView name,TextView countryName
			,TextView position,TextView dateBirth,TextView hight,TextView wight)
	{
		name.setSingleLine();
		name.setEllipsize(TruncateAt.END);
		
		shirt.setText(data.shirtNumber);
		name.setText(data.name);
		countryName.setText(data.countryName);
		position.setText(data.position);
		dateBirth.setText(data.dateBirth);
		hight.setText(data.hight);
		wight.setText(data.wight);
		
		
		AddRowToLeagueTable(row,shirt,name,countryName,position,dateBirth,hight,wight);
	}
	private void AddHeaderLeagueTable(TableRow row,TextView shirt,TextView name,TextView countryName
			,TextView position,TextView dateBirth,TextView hight,TextView wight)
	{
		shirt.setText("No.");
		name.setText("Name");
		countryName.setText("Country");
		position.setText("Position");
		dateBirth.setText("BD");
		hight.setText("Hignt");
		wight.setText("Wight");
		
		shirt.setTextColor(Color.WHITE);
		
		row.setBackgroundColor(Color.rgb(44, 44, 44));
		AddRowToLeagueTable(row, shirt, name, countryName, position, dateBirth, hight, wight);
	}
	private void AddRowToLeagueTable(TableRow row,TextView shirt,TextView name,TextView countryName
			,TextView position,TextView dateBirth,TextView hight,TextView wight)
	{
		
		row.addView(shirt);
		row.addView(name);
		row.addView(position);
		row.addView(countryName);
		//row.addView(dateBirth);
		//row.addView(hight);
		//row.addView(wight);
		
		table.addView(row);
	}

	private void NewsPlayerRowControl()
	{
		shirt = new TextView(getActivity());
		name = new TextView(getActivity());
		countryName = new TextView(getActivity());
		position = new TextView(getActivity());
		dateBirth = new TextView(getActivity());
		hight = new TextView(getActivity());
		wight = new TextView(getActivity());
		
		shirt.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
		name.setWidth((int)(180*SportPool_BaseClass.imgUtil.scaleSize()));
		position.setWidth((int)(100*SportPool_BaseClass.imgUtil.scaleSize()));
		countryName.setWidth((int)(100*SportPool_BaseClass.imgUtil.scaleSize()));
		dateBirth.setWidth((int)(100*SportPool_BaseClass.imgUtil.scaleSize()));
		hight.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
		wight.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));

		shirt.setTextColor(Color.BLACK);
		name.setTextColor(Color.BLACK);
		countryName.setTextColor(Color.BLACK);
		position.setTextColor(Color.BLACK);
		dateBirth.setTextColor(Color.BLACK);
		hight.setTextColor(Color.BLACK);
		wight.setTextColor(Color.BLACK);
		
		shirt.setGravity(Gravity.CENTER);
		name.setGravity(Gravity.CENTER);
		countryName.setGravity(Gravity.CENTER);
		position.setGravity(Gravity.CENTER);
		dateBirth.setGravity(Gravity.CENTER);
		hight.setGravity(Gravity.CENTER);
		wight.setGravity(Gravity.CENTER);
		
		shirt.setTypeface(SportPool_BaseClass.superTypeface);
		name.setTypeface(SportPool_BaseClass.superTypeface);
		countryName.setTypeface(SportPool_BaseClass.superTypeface);
		position.setTypeface(SportPool_BaseClass.superTypeface);
		dateBirth.setTypeface(SportPool_BaseClass.superTypeface);
		hight.setTypeface(SportPool_BaseClass.superTypeface);
		wight.setTypeface(SportPool_BaseClass.superTypeface);
	}
	
	@Override
	public void onClick(View v) 	{


		
	}



	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {


			if(xmlPar.status.equals("success") && xmlPar.message.trim().equals("") )
			{
				vData = xmlPar.vData;

				NewsPlayerRowControl();
				table.removeAllViews();

				for(int i = 0; i < vData.size(); i++)
				{
					row = new TableRow(getActivity());
					TableLayout.LayoutParams tableRowParams=
							new TableLayout.LayoutParams
									(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

					tableRowParams.setMargins(10, 2, 10, 2);
					row.setLayoutParams(tableRowParams );
					if((i%2) == 0)
					{
						row.setBackgroundResource(R.drawable.bg_tab_leaguetable1);
					}
					else
					{
						row.setBackgroundResource(R.drawable.bg_tab_leaguetable2);
					}


					DataElementPlayer data = vData.elementAt(i);

					if( i==0)
					{
						// Header
						AddHeaderLeagueTable(row,shirt,name,countryName,position,dateBirth,hight,wight);
						row.setBackgroundResource(R.drawable.tab_leaguetable);

						// Content
						row = new TableRow(getActivity());
						tableRowParams=
								new TableLayout.LayoutParams
										(TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

						tableRowParams.setMargins(10, 2, 10, 2);
						row.setLayoutParams(tableRowParams );
						if((i%2) == 0)
						{
							row.setBackgroundResource(R.drawable.bg_tab_leaguetable1);
						}
						else
						{
							row.setBackgroundResource(R.drawable.bg_tab_leaguetable2);
						}

						NewsPlayerRowControl();

						AddDataLeagueTable(data,row,shirt,name,countryName,position,dateBirth,hight,wight);
					}
					else
					{

						NewsPlayerRowControl();
						AddDataLeagueTable(data,row,shirt,name,countryName,position,dateBirth,hight,wight);

					}

				}

				//progress.dismiss();
				//AsycTaskLoadImage loadImage = new AsycTaskLoadImage(imgLeague, null, null, null);
				//loadImage.execute(xmlPar.contestURLImages);
			}
			else
			{
				txtTeam.setText(xmlPar.message);
				table.removeAllViews();
			}

		} catch (Exception e) {

			Log.d("SportPool", "SportPool " + e.getMessage());
			//progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlPar = new XMLParserPlayer();
			Xml.parse(strOutput, xmlPar);
		}
		catch(Exception ex)
		{

		}
	}
}
