package com.isport.sportpool;

import java.io.InputStream;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserAnalyseDetail;

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
import android.widget.TextView;

import org.xml.sax.SAXException;

import io.karim.MaterialTabs;

public class SportPool_AnalyseDetail extends SportPool_BaseClass implements TabListener, ReceiveDataListener {

    // Data
    private String contestGroupId = null;
    private String teamId1 = null;
    private String teamId2 = null;
    private String matchId = null;
    private String teamName1 = null;
    private String teamName2 = null;
    private boolean resumeHasRun = false;
    private XMLParserAnalyseDetail xmlAnalyse = null;
    // control
    private ProgressDialog progress = null;
    private RelativeLayout layout = null;
    private TextView txtTeam1 = null;
    private TextView txtTeam2 = null;
    private TextView txtVS = null;

    private ViewPager mViewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private MaterialTabs tabs;

    @SuppressWarnings("deprecation")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportpool_main);

        layout = new RelativeLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_analysedetail, layout);


        if (getIntent().getExtras() != null) {
            contestGroupId = (String) getIntent().getExtras().getString("contestgroupid");
            teamId1 = (String) getIntent().getExtras().getString("teamid1");
            teamId2 = (String) getIntent().getExtras().getString("teamid2");
            matchId = (String) getIntent().getExtras().getString("matchid");
            teamName1 = (String) getIntent().getExtras().getString("teamname1");
            teamName2 = (String) getIntent().getExtras().getString("teamname2");
        }

        tabs = (MaterialTabs) layout.findViewById(R.id.awards_material_tabs);
        txtTeam1 = (TextView) layout.findViewById(R.id.analysedetail_txt_team1);
        txtVS = (TextView) layout.findViewById(R.id.analysedetail_txt_vs);
        txtTeam2 = (TextView) layout.findViewById(R.id.analysedetail_txt_team2);

        txtTeam1.setText(teamName1);
        txtVS.setText(" VS ");
        txtTeam2.setText(teamName2);

        txtTeam1.setTypeface(superTypeface);
        txtVS.setTypeface(superTypeface);
        txtTeam2.setTypeface(superTypeface);


        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) layout.findViewById(R.id.analysedetail_pager);

        setupActionBar();


        layoutCenter = (RelativeLayout) findViewById(R.id.main_layout_center);
        layoutCenter.addView(layout);


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
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    return true;
            }
            return true;
		}



    private void GetURL() {
        try {

            progress = ProgressDialog.show(this, null, "Loading...", true, true);

            String url = DataURL.footballanalysedetail;
            url += "&contestgroupid=" + contestGroupId;
            url += "&teamcode1=" + teamId1;
            url += "&teamcode2=" + teamId2;
            url += "&matchid=" + matchId;
            url += "&sportType=00001";
            url += "&lang=" + DataSetting.Languge;
            url += "&imei=" + DataSetting.IMEI;
            url += "&model=" + DataSetting.MODEL;
            url += "&imsi=" + DataSetting.IMSI;
            url += "&type=" + DataSetting.TYPE;

            AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "analyse");
            load.execute(url);


        } catch (Exception ex) {
            Log.d("SportPool Error : ", ex.getMessage());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        resumeHasRun = false;

    }



    @Override
    protected void onResume() {
        super.onResume();

        CURRENT_PAGE = getString(R.string.page_analyse_detail_en);
        CURRENT_PAGE_TH = getString(R.string.page_analyse_detail);

        if (!resumeHasRun) {
            GetURL();
            init();
            resumeHasRun = true;
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onReceiveDataStream(String loadName, String url, String strOutput) {
        if (loadName == "analyse") {

            SportPool_BaseClass.dataAnalyseDetail = xmlAnalyse.dataElementAnalyseDetail;
            SportPool_BaseClass.vDataHeadtoHead = xmlAnalyse.vDataEleAnalyseHeadToHead;
            SportPool_BaseClass.vDataEleHeadDetail = xmlAnalyse.vDataEleHeadDetail;
            SportPool_BaseClass.vDataStaticTeam1 = xmlAnalyse.vDataEleLeagueStaticTeam1;
            SportPool_BaseClass.vDataStaticTeam2 = xmlAnalyse.vDataEleLeagueStaticTeam2;
            SportPool_BaseClass.vDataLeagueTable = xmlAnalyse.vDataEleLeagueTable;

            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabs.setViewPager(mViewPager);
            progress.dismiss();

        }
    }

    @Override
    public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
        try {
            xmlAnalyse = new XMLParserAnalyseDetail();
            Xml.parse(strOutput, xmlAnalyse);
        } catch (Exception ex) {

        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SportPool_AnalyseDetail_HeadtoHead.newInstance(contestGroupId, teamId1, teamId2, matchId, teamName1, teamId2);
            } else if (position == 1) {
                return SportPool_AnalyseDetail_LeagueTable.newInstance(teamId1);
            } else if (position == 2) {
                return SportPool_AnalyseDetail_Team.newInstance(contestGroupId, teamId1, teamId2, matchId, teamName1, teamName2);
            } else return null;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "วิเคราะห์";
                case 1:
                    return "อัตราพลูเฉลี่ย";
                case 2:
                    return "ผลงาน 5 นัด";
            }
            return null;
        }
    }

    @Override
    public void onTabReselected(Tab arg0, FragmentTransaction arg1) {

    }

    @Override
    public void onTabSelected(Tab tab, FragmentTransaction arg1) {
        mViewPager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {

    }

}
