package com.isport.sportpool;


import java.util.ArrayList;

import twitter4j.api.FavoritesResources;

import com.isport.sportpool.data.DataElementFavoriteTeam;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.list.ListAdapterMenuLeagueDetail;
import com.isport.sportpool.list.ListAdapterSettingTeam;
import com.isport.sportpool.service.StartUp;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SportPool_SettingTeam extends SportPool_BaseClass  implements OnClickListener,OnChildClickListener,OnItemClickListener {
	
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	private ExpandableListView list_menuleague = null;
	private ListView listTeam = null;
	private Handler handler = null;
	private TextView txtChoice = null;
	
	private ListAdapterMenuLeagueDetail adapterMenuLeague = null;
	private ArrayList<DataElementFavoriteTeam> favTeam = null;
	
	private boolean resumeHasRun = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.sportpool_main);
        
        StartUp.getSetting(this);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        
        layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_setting_team, layout);

        listTeam = (ListView)layout.findViewById(R.id.settingteam_list_team);
        list_menuleague = (ExpandableListView)layout.findViewById(R.id.settingteam_expand_view);
        txtChoice = (TextView)layout.findViewById(R.id.settingteam_txt_choice);
        
        txtChoice.setTypeface(superTypeface);
        txtChoice.setText("เลือกทีมโปรด");
        
        listTeam.setOnItemClickListener(this);
        registerForContextMenu(listTeam);
        
        list_menuleague.setDivider(null);
        list_menuleague.setChildDivider(null);
        
 
        list_menuleague.setOnChildClickListener(this);
        list_menuleague.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                case MotionEvent.ACTION_DOWN:
                    // Disallow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    break;

                case MotionEvent.ACTION_UP:
                    // Allow ScrollView to intercept touch events.
                    v.getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }

                // Handle ListView touch events.
                v.onTouchEvent(event);
                return true;
            }
        });
        
        /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
	        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		    params.setMargins(0, -110, 0, 0);
		    layoutCenter.setLayoutParams(params);
        }*/
        
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

    
	private void GetSetting()
	{
		try
		{
			//teamSetting = new HashMap<String, String>();
			favTeam = new ArrayList<DataElementFavoriteTeam>();
			if (DataSetting.teamId != "" && DataSetting.teamName != "")
			{
				String[] teamIds = DataSetting.teamId.split(",");
				String[] teamNames = DataSetting.teamName.split(",");
				String[] contentGroupid = DataSetting.teamContentGroupId.split(",");
				for(int i=0;i<teamIds.length;i++)
				{
					if( !teamIds[i].equals("") &&  !teamNames[i].equals(""))
					{
						//teamSetting.put(teamIds[i], teamNames[i]);
						favTeam.add(new DataElementFavoriteTeam(teamNames[i],teamIds[i], (contentGroupid.length > 0 ? contentGroupid[i] : "" )));
					}
				}
			}
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}
	
    @Override
    protected void onResume() {
    	try {
    		
    		super.onResume();
    		
    		CURRENT_PAGE_TH = getResources().getString(R.string.page_favteam);
			CURRENT_PAGE = getResources().getString(R.string.page_favteam_en);
			
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

    		
		    		if(vGroupListMenu == null)
		    		{
		    			Intent intent = new Intent(this, SportPool_Logo.class);
		    			startActivity(intent);
		    			this.finish();
		    		}
		    		else
		    		{
		    			init();
		    			GetSetting();
		    			// My Team
		    			ListAdapterSettingTeam adapTeam = new ListAdapterSettingTeam(this,favTeam);
		    			listTeam.setAdapter(adapTeam);
		    			
		    			// Team Choice
		    			adapterMenuLeague = new ListAdapterMenuLeagueDetail(this, list_menuleague, vGroupListMenu, imgUtil, "", gethImage190());
		    			handler = new Handler();
						handler.post(new Runnable() {
							@Override
							public void run() {
								//program_message.setVisibility(View.INVISIBLE);
								//program_list_data.setVisibility(View.VISIBLE);
								//program_list_data.setAdapter(adaterResult);
								list_menuleague.setAdapter(adapterMenuLeague);
							}
						});
		    		}
			}
    		
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }


    @Override
   protected void onStop() 
    {
    	super.onStop();
    	resumeHasRun = false;
    }

	@Override
	public boolean onChildClick(ExpandableListView arg0, View v, int groupPosition,
			int childPosition, long arg4) {

		DataElementLeague data = vGroupListMenu.get(groupPosition).GroupLeagueCollection.get(childPosition);
		Intent intent = new Intent(this, SportPool_SettingTeam_Detail.class);
		intent.putExtra("data",data);
		startActivity(intent);
		
		return false;
	}

	
	@Override
	public void onClick(View v) {
		/*if(v == btnDone)
		{
			league = adapterMenuLeague.vLeague;
			setLeague();
			String timeRefresh = (String) (setting_time_text.getText().equals("")? "0" : setting_time_text.getText()) ; 
			StartUp.setSetting(this, DataSetting.Languge, setLeague, timeRefresh ,DataSetting.checkNotify, false);
			Toast.makeText(this, "setting success.", Toast.LENGTH_SHORT).show();
			if(DataSetting.checkNotify)
			{
				startService(new Intent(this, SportArena_Notify.class));
			}
			finish();
		}*/
		
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		
		arg1.setBackgroundResource(R.drawable.bg_list_league);
		
		String teamId = favTeam.get(arg2).teamCode;//(new ArrayList<String>(teamSetting.keySet())).get(arg2);
		String teamName = favTeam.get(arg2).teamName;//(new ArrayList<String>(teamSetting.values())).get(arg2);
		String contentGroupId = favTeam.get(arg2).contestGroupId;
		Intent intent = new Intent(this, SportPool_SettingTeam_Favorite.class);
		intent.putExtra("teamid",teamId);
		intent.putExtra("teamname",teamName);
		intent.putExtra("contentGroupId",contentGroupId);
		startActivity(intent);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	    ContextMenuInfo menuInfo) {
	  if (v==listTeam) {
	    AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
	    menu.setHeaderTitle(  favTeam.get(info.position).teamName  );
	    //String[] menuItems = getResources().getStringArray(R.menu.menu);
	    //for (int i = 0; i<menuItems.length; i++) {
	      menu.add(Menu.NONE, info.position, 1, getResources().getString(R.string.menu_delete));
	    //}
	  }
	}
	

	@Override
	public boolean onContextItemSelected(MenuItem item) {

		favTeam.remove(item.getItemId());
		SaveSetting();
		ListAdapterSettingTeam adapTeam = new ListAdapterSettingTeam(this,favTeam);
		listTeam.setAdapter(adapTeam);
		
		return true;
	}

	private void SaveSetting()
	{
		try
		{
			String teamId="", teamName="" ,contentGroupId="";
			for( DataElementFavoriteTeam fav : favTeam )
			{
				teamId += "," + fav.teamCode;
				teamName += "," + fav.teamName;
				contentGroupId += "," + fav.contestGroupId;
			}
			StartUp.setSetting(this, teamId, teamName,DataSetting.MatchTeamLike,contentGroupId);

		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}
}
