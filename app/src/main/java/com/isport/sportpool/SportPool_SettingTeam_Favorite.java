package com.isport.sportpool;


import java.io.InputStream;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserMatchProgramScoreByTeam;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.util.Log;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

import org.xml.sax.SAXException;

import io.karim.MaterialTabs;

public class SportPool_SettingTeam_Favorite extends SportPool_BaseClass implements ReceiveDataListener, TabListener {


    // Data
    private String teamId = null;
    private String teamName = null;
    private String contentGroupId = null;
    private XMLParserMatchProgramScoreByTeam xmlProgram = null;
    // control
    private ProgressDialog progress = null;
    private RelativeLayout layout = null;
    private ViewPager mViewPager;
    SettingTeamPagerAdapter mSectionsPagerAdapter;
    private MaterialTabs tabs;
    private boolean resumeHasRun = false;
    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportpool_main);

        if (getIntent().getExtras() != null) {
            Bundle b = getIntent().getExtras();
            teamId = b.getString("teamid");
            teamName = b.getString("teamname");
            contentGroupId = b.getString("contentGroupId");
        }

        layout = new RelativeLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_programandscore_team, layout);

        layoutCenter = (RelativeLayout) findViewById(R.id.main_layout_center);

        mSectionsPagerAdapter = new SettingTeamPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) layout.findViewById(R.id.settingteam_pager);
        tabs = (MaterialTabs) layout.findViewById(R.id.material_tabs);

        layoutCenter.addView(layout);

        setupActionBar();
        setTranslucentStatus(true);
        //setSlideMenu();

    }
/*
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }*/
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		/*if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		else return super.onOptionsItemSelected(item);*/
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
	}


    @Override
    protected void onStop() {
        super.onStop();
        resumeHasRun = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        CURRENT_PAGE_TH = getResources().getString(R.string.page_favteam_detail);
        CURRENT_PAGE = getResources().getString(R.string.page_favteam_detail_en);
        if (!resumeHasRun) {
            GetURL();
            init();
            resumeHasRun=true;
        }
    }

    private void GetURL() {
        try {
            progress = ProgressDialog.show(this, null, "Loading...", true, true);

            String url = DataURL.programscorebyteam;
            url += "&teamid=" + teamId;
            url += "&lang=" + DataSetting.Languge;
            url += "&imei=" + DataSetting.IMEI;
            url += "&model=" + DataSetting.MODEL;
            url += "&imsi=" + DataSetting.IMSI;
            url += "&type=" + DataSetting.TYPE;

            AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "programscore");
            load.execute(url);


        } catch (Exception ex) {
            Log.d("SportPool Error : ", ex.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.program, menu);
        return true;
    }

    @Override
    public void onReceiveDataStream(String loadName, String url, String strOutput) {
        try {
            if (loadName == "programscore") {

                vGroupProgrambyTeam = xmlProgram.groupProgram;
                vGroupScorebyTeam = xmlProgram.groupScore;

                mViewPager.setAdapter(mSectionsPagerAdapter);
                tabs.setViewPager(mViewPager);
                if (progress != null) progress.dismiss();

            }

        } catch (Exception ex) {
            if (progress != null) progress.dismiss();
            Log.d("SportPool Error : ", ex.getMessage());
            //GetURL();
        }
    }

    @Override
    public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
        try {
            xmlProgram = new XMLParserMatchProgramScoreByTeam();
            Xml.parse(strOutput, xmlProgram);
        } catch (Exception ex) {

        }
    }

    public class SettingTeamPagerAdapter extends FragmentPagerAdapter {

        public SettingTeamPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SportPool_SettingTeam_Program.newInstance(teamId);
            } else if (position == 1) {
                return SportPool_SettingTeam_Score.newInstance(teamId);
            } else if (position == 2) {
                return SportPool_Player.newInstance(teamId, teamName);
            } else if (position == 3) {
                return SportPool_SettingTeam_Table.newInstance(teamId, contentGroupId);
            } else return null;
        }

        @Override
        public int getCount() {
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "โปรแกรม";
                case 1:
                    return "สรุปผล";
                case 2:
                    return "รายชื่อนักเตะ";
                case 3:
                    return "ตารางคะแนน";
            }
            return null;
        }
    }


    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }


    @Override
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        mViewPager.setCurrentItem(tab.getPosition());

    }


    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
        // TODO Auto-generated method stub

    }

}
