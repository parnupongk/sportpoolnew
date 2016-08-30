package com.isport.sportpool;

import java.io.InputStream;

import com.isport.sportpool.data.DataElementSMSFree;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ReceiveDataListener;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuthAuthorization;
import twitter4j.auth.RequestToken;
import twitter4j.conf.*;

public class SportPool_NewsDetail extends SportPool_BaseClass implements ReceiveDataListener, OnClickListener
{
	private ConfigurationBuilder builder = null;
	private Twitter mTwitter;
	private DataElementSMSFree data = null;
	private RelativeLayout layout = null;
	private TextView textHeader = null;
	private RelativeLayout layoutData = null;
	private ImageView detailImage = null;
	private TextView textDataHeader = null;
	private TextView textTitle = null;
	private TextView textDetail = null;
	private ImageView btnFB  = null;
	private ImageView btnTwit = null;
	private ProgressBar progress = null;
	private String text = "";
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sportpool_main);

		if(getIntent().getExtras() != null)
		{
			data = (DataElementSMSFree) getIntent().getExtras().get("data");
			text = getIntent().getExtras().getString("header");
		}
		
		layout = new RelativeLayout(this);
		layout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		layout.setGravity(Gravity.CENTER_HORIZONTAL);
		
		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sub_news_detail, layout);
        
        layoutCenter = (RelativeLayout)findViewById(R.id.main_layout_center);
        textHeader = (TextView) layout.findViewById(R.id.detail_news_header_text);
        btnFB = (ImageView)layout.findViewById(R.id.news_share_fb);
        btnTwit = (ImageView)layout.findViewById(R.id.news_share_twit);
        layoutData = (RelativeLayout) layout.findViewById(R.id.detail_news_layout_data);
        detailImage = (ImageView) layout.findViewById(R.id.detail_news_data_image);
        progress = (ProgressBar) layout.findViewById(R.id.detail_news_data_progress);
        textDataHeader = (TextView) layout.findViewById(R.id.detail_news_data_header);
        textTitle = (TextView) layout.findViewById(R.id.detail_news_data_title);
        textDetail = (TextView) layout.findViewById(R.id.detail_news_data_detail);
        
        progress.setLayoutParams(new FrameLayout.LayoutParams((int)(100*imgUtil.scaleSize()), (int)(100*imgUtil.scaleSize()), Gravity.CENTER));
        detailImage.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT,Gravity.CENTER));
        
        btnFB.setOnClickListener(this);
        btnTwit.setOnClickListener(this);
        layoutData.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);

                
        if(data != null)
        {
        	SpannableString content = new SpannableString(data.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		    
        	textHeader.setText(text);
        	textDataHeader.setText(content);
        	textTitle.setText("     "+data.title);
        	textDetail.setText("    "+Html.fromHtml(data.detail));
        	
        	textHeader.setTextSize(19);
        	textDataHeader.setTextSize(19);
        	
        	textTitle.setTextColor(Color.BLACK);
        	textHeader.setTextColor(Color.BLACK);
        	textDataHeader.setTextColor(Color.BLACK);
        	textDetail.setTextColor(Color.BLACK);
        	
        	if( hImage190.get(data.img600) == null )
        	{
        		AsycTaskLoadImage load = new AsycTaskLoadImage(detailImage, progress, null, this);
        		load.execute(data.img600);
        	}
        	else
        	{
        		detailImage.setImageBitmap(hImage190.get(data.img600));
        		progress.setVisibility(View.INVISIBLE);
        	}
        	
        }
        else
        {
        	//PrintLog.printException(this, "", e)("data == null", "data == null");
        }
        
     
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
		finish();
	}

	@Override
	protected void onResume() 
	{
		super.onResume();
		CURRENT_PAGE = getString(R.string.page_News_en);
		CURRENT_PAGE_TH = getString(R.string.page_News);
		init();
	};


	@Override
	public void onClick(View view) {
		// TODO Auto-generated method stub
		if(view == detailImage && data != null)
		{
			/*Intent intent = new Intent(this, DetailNewsImage.class);
			intent.putExtra("data", data);
			intent.putExtra("image", image);
			startActivity(intent);*/
		}
		else if(data != null )
		{

			if(view == btnFB)
			{
				//Share.shareFB(this, data.urlFB, data.title, "", data.img350);
			}
			else if(view == btnTwit)
			{
				//btnTwit.setImageResource(R.drawable.btn_twit_act);
				//Share.sendEmail(this, new String[]{}, data.title, data.detail);
				//Intent intent = new Intent(this, StarSoccer_Twitter.class);
				//intent.putExtra("message", data.header);
				//this.startActivity(intent);
			}
		
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {

	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
