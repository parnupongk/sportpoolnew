package com.isport.sportpool;

import java.util.Locale;
import android.annotation.SuppressLint;
import android.app.LocalActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TabHost;

import io.karim.MaterialTabs;


@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class SportPool_SMSService extends SportPool_BaseClass implements TabListener {

    // Data
    private Handler handler = null;


    // control
    private ProgressDialog progress = null;
    private RelativeLayout layout = null;
    private TabHost tabMenu = null;
    private LocalActivityManager localManager = null;
    private ViewPager mViewPager;
    SectionsPagerAdapter mSectionsPagerAdapter;
    private MaterialTabs tabs;

    @SuppressLint("NewApi")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.sportpool_main);

        layout = new RelativeLayout(this);
        layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sportpool_smsservice, layout);

        tabs = (MaterialTabs) layout.findViewById(R.id.material_tabs);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) layout.findViewById(R.id.smsservice_pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabs.setViewPager(mViewPager);


        setupActionBar();

        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        layoutCenter.addView(layout);


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

    @Override
    protected void onResume() {
        super.onResume();
        SportPool_BaseClass.CURRENT_PAGE_TH = getResources().getString(R.string.page_smsservice);
        SportPool_BaseClass.CURRENT_PAGE = getResources().getString(R.string.page_smsservice_en);
        init();
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onPause() {
        super.onPause();
        //localManager.dispatchPause(isFinishing());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.program, menu);
        return true;
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return SportPool_SMSPremium.newInstance("", "");
            } else
                return SportPool_SMSFree.newInstance("", "1");

            //else return SportPool_SMSPremium.newInstance("","");
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return "";
                case 1:
                    return "";
                    /*case 1:
                        return getString(R.string.title_section2).toUpperCase(l);
	                case 2:
	                    return getString(R.string.title_section3).toUpperCase(l);*/
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
