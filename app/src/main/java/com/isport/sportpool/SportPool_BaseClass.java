package com.isport.sportpool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.isport.sportpool.data.DataElementAnalyseDetail;
import com.isport.sportpool.data.DataElementAnalyseHeadToHead;
import com.isport.sportpool.data.DataElementAnalyseHeadToHeadDetail;
import com.isport.sportpool.data.DataElementGroupListMenu;
import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataElementGroupScore;
import com.isport.sportpool.data.DataElementLeagueStatic;
import com.isport.sportpool.data.DataElementLeagueTable;
import com.isport.sportpool.data.DataElementListMenuLeft;
import com.isport.sportpool.data.DataElementSMSFree;
import com.isport.sportpool.data.DataElementSMSPremium;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.list.MenuAdapter;
import com.isport.sportpool.service.ImageUtil;
import com.isport.sportpool.service.StartUp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.AdapterView.OnItemClickListener;


public class SportPool_BaseClass extends ActionBarActivity implements OnClickListener {

    // Variable
    public static String SERVERVERSION = "1.1";
    public static String ISACTIVE = "N";
    public static String ISAdView = "true";
    public static String ACTIVE_HEADER = "";
    public static String ACTIVE_DETAIL = "";
    public static String ACTIVE_FOOTER = "";
    public static String ACTIVE_FOOTER1 = "";
    public static String ACTIVE_OTPWAIT = "";
    public static String CURRENT_PAGE = "";
    public static String CURRENT_PAGE_TH = "";
    public static String URLBANNER = "";
    public static String URLICON = "";
    public static String MSISDN = "";
    public static Typeface superTypeface;
    // Layout
    public static HashMap<String, Bitmap> hImage190 = null;
    protected static RelativeLayout layoutCenter = null;
    protected DrawerLayout drawerLayout;
    protected ActionBarDrawerToggle actionBarDrawerToggle;
    // Control
    protected ListView listView = null;
    protected RelativeLayout layoutFooter = null;
    protected LinearLayout main_layout_webview = null;
    protected AdView adView = null;
    protected AdView main_adView_isp = null;
    protected ActionBar actionBar;
    private InterstitialAd interstitialAd;
    private Tracker mTracker;

    // DataElement
    protected static ImageUtil imgUtil = null;
    public static ArrayList<DataElementListMenuLeft> vGroupLeftMenu = null;
    public static ArrayList<DataElementGroupListMenu> vGroupListMenu = null;
    public static Vector<DataElementLeagueTable> vGroupLeagueTable = null;
    public static ArrayList<DataElementGroupProgram> vGroupProgram = null;
    public static ArrayList<DataElementGroupProgram> vGroupProgrambyTeam = null;
    public static ArrayList<DataElementGroupScore> vGroupScorebyTeam = null;

    //Analyse Data
    public static DataElementAnalyseDetail dataAnalyseDetail = null;
    public static Vector<DataElementAnalyseHeadToHead> vDataHeadtoHead = null;
    public static Vector<DataElementAnalyseHeadToHeadDetail> vDataEleHeadDetail = null;
    public static Vector<DataElementLeagueStatic> vDataStaticTeam1 = null;
    public static Vector<DataElementLeagueStatic> vDataStaticTeam2 = null;
    public static Vector<DataElementLeagueTable> vDataLeagueTable = null;
    public static Vector<DataElementSMSPremium> vDataSMSPremium = null;
    public static Vector<DataElementSMSFree> vDataSMSFree = null;

    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportpool_main);
        imgUtil = new ImageUtil(this);
        superTypeface = Typeface.createFromAsset(getAssets(), "fonts/supermarket.ttf");
        if (hImage190 == null) hImage190 = new HashMap<String, Bitmap>();
        DataSetting.IMEI = StartUp.getImei(this);
        DataSetting.IMSI = StartUp.getImsi(this);
        StartUp.getModel(this);
        StartUp.getSetting(this);

    }

    protected void setupActionBar() {
        // basic setup actionbar
        listView = (ListView) findViewById(R.id.left_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();//getActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    protected void setSlideMenu() {

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        drawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

    }


    @TargetApi(19)
    protected void setTranslucentStatus(boolean on) {
       /* Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }*/
    }

    protected void setListView() {

        try {
            //listView.setFadingEdgeLength(0);
            if (vGroupLeftMenu != null) {
                MenuAdapter menuAdapter = new MenuAdapter(this, vGroupLeftMenu, this, CURRENT_PAGE);
                listView.setAdapter(menuAdapter);
                listView.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> arg0, View v, int arg2,
                                            long arg3) {

                        if (!vGroupLeftMenu.get(arg2).type.equals("header")) {
                            v.setBackgroundResource(R.drawable.menu_bg_active);
                            setContent(arg2, vGroupLeftMenu.get(arg2).name, vGroupLeftMenu.get(arg2).name_en);
                        }
                    }
                });
            }
        } catch (Exception ex) {
            Log.e("Sportpool",ex.getMessage());
        }

    }


    protected void setContent(int position, String menuName, String menuNameEN) {

        Log.d("Sportpool Dubg", menuNameEN);
        if (menuName.equals("หน้าแรก") || menuNameEN.toLowerCase().equals("first")) {
            Intent intent = new Intent(this, SportPool_Analyse.class);
            this.startActivity(intent);
            //this.startActivityForResult(intent, 1);
            finish();
        } else if (menuNameEN.toLowerCase().equals("analyse")) {
            Intent intent = new Intent(this, SportPool_Analyse.class);
            this.startActivity(intent);
            //this.startActivityForResult(intent, 1);
            finish();
        } else if (menuName.equals("ทีมโปรด") || menuNameEN.toLowerCase().equals("team")) {
            Intent intent = new Intent(this, SportPool_SettingTeam.class);
            this.startActivity(intent);
            //this.startActivityForResult(intent, 1);
            finish();
        } else if (menuName.equals("ตารางคะแนน") || menuNameEN.toLowerCase().equals("leaguetable")) {
            Intent intent = new Intent(this, SportPool_LeagueTable.class);
            startActivity(intent);
            finish();
        } else if (menuName.equals("โปรแกรม") || menuNameEN.toLowerCase().equals("program")) {
            Intent intent = new Intent(this, SportPool_Program.class);
            startActivity(intent);
            finish();
        } else if (menuName.equals("ผลสด") || menuNameEN.toLowerCase().equals("livescore")) {
            Intent intent = new Intent(this, SportPool_Livescore.class);
            startActivity(intent);
            finish();
        } else if (menuName.equals("สรุปผล") || menuNameEN.toLowerCase().equals("result")) {
            Intent intent = new Intent(this, SportPool_Result.class);
            startActivity(intent);
            finish();
        } else if (menuNameEN.toLowerCase().equals("servicesms")) {
            Intent intent = new Intent(this, SportPool_SMSService.class);
            startActivity(intent);
            finish();
        } else if (menuNameEN.toLowerCase().equals("tded")) {
            Intent intent = new Intent(this, SportPool_Tded.class);
            startActivity(intent);
            finish();
        }

    }


    protected void init() {
        try {

            adView = (AdView) this.findViewById(R.id.main_google_adView);
            main_adView_isp = (AdView) findViewById(R.id.main_adView_isp);
            main_layout_webview = (LinearLayout) findViewById(R.id.main_layout_webview);
            layoutFooter = (RelativeLayout)findViewById(R.id.main_layout_footer);


            CURRENT_PAGE_TH = CURRENT_PAGE_TH.equals("") ? getString(R.string.page_program) : CURRENT_PAGE_TH;
            CURRENT_PAGE = CURRENT_PAGE.equals("") ? getString(R.string.page_program_en) : CURRENT_PAGE;
            String pageName = DataSetting.Languge.equals("th") ? CURRENT_PAGE_TH : CURRENT_PAGE;
            actionBar.setTitle(pageName);


            if (ISAdView != null && ISAdView.equals("true")) {

                AdRequest adRequest = new AdRequest.Builder()
                        .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                        .addTestDevice(DataSetting.IMEI)
                        .build();

                Random rad = new Random();
                int ads = rad.nextInt(3);
                //setAdsIsport();
                if (ads == 1) {
                    main_layout_webview.setVisibility(View.GONE);
                    main_adView_isp.setVisibility(View.GONE);

                    adView.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }
                    });

                    adView.setVisibility(View.VISIBLE);
                    adView.loadAd(adRequest);
                } else if (ads == 2) {
                    main_layout_webview.setVisibility(View.GONE);
                    adView.setVisibility(View.GONE);

                    main_adView_isp.setAdListener(new AdListener() {
                        @Override
                        public void onAdLoaded() {
                            super.onAdLoaded();
                        }
                    });
                    main_adView_isp.setVisibility(View.VISIBLE);
                    main_adView_isp.loadAd(adRequest);
                } else {
                    setAdsIsport();
                }

            }
            else
            {
                setAdsIsport();
            }

            setListView();
            SetInterstitialAd();

            // google analytics
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker(getResources().getString(R.string.GoogleAnalyticsID));
            // All subsequent hits will be send with screen name = "main screen"
            mTracker.setScreenName(CURRENT_PAGE);
            mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        } catch (Exception ex) {

            Log.e("Sportpool",ex.getMessage());
        }

    }

    private void setAdsIsport()
    {
        try
        {
            main_adView_isp.setVisibility(View.GONE);
            adView.setVisibility(View.GONE);
            main_layout_webview.setVisibility(View.VISIBLE);
            main_layout_webview.removeAllViews();
            WebView wView = new WebView(this);
            wView.setWebViewClient(new MyWebViewClient());
            wView.getSettings().setJavaScriptEnabled(true);
            wView.loadUrl(URLBANNER);
            main_layout_webview.addView(wView);
        }
        catch(Exception ex)
        {
            Log.e("Sportpool",ex.getMessage());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if( adView != null )adView.pause();
        if(  main_adView_isp != null)main_adView_isp.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static HashMap<String, Bitmap> gethImage190() {
        return hImage190;
    }

    public static void sethImage190(HashMap<String, Bitmap> hImage190) {
        SportPool_BaseClass.hImage190 = hImage190;
    }

    public class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        /*if(v == menuHeader)
		{
			Intent intent = new Intent(this, SportPool_Program.class);
			startActivity(intent);
			finish();
		}*/
    }

    @Override
    public void onBackPressed() {
        if( !CURRENT_PAGE.equals(getString(R.string.page_favteam_detail_en))
                && !CURRENT_PAGE.equals(getString(R.string.page_analyse_detail_en))
                && !CURRENT_PAGE.equals(getString(R.string.page_News_en))
                && !CURRENT_PAGE.equals(getResources().getString(R.string.page_active_en))) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            GetInterstitialAd();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
        else
        {
            finish();
        }
    }

    public void GetInterstitialAd() {
        if (interstitialAd != null &&  interstitialAd.isLoaded()) {
            interstitialAd.show();
        }
    }

    public void SetInterstitialAd()
    {
        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId(getResources().getString(R.string.admobFullId));

        AdRequest  adRequest =new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int errorCode) {
                //super.onAdFailedToLoad(errorCode);
                Log.e("Sportpool", String.valueOf(errorCode));
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                Log.e("Sportpool", "onAdLoaded");
                //GetInterstitialAd();
            }
        });

        interstitialAd.loadAd(adRequest);
    }

}



