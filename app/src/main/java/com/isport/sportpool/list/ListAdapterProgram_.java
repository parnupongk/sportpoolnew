package com.isport.sportpool.list;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_BaseClass;
import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.ImageUtil;
import com.isport.sportpool.service.ReceiveDataListener;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import org.xml.sax.SAXException;


public class ListAdapterProgram_ extends BaseAdapter implements ReceiveDataListener,OnClickListener
{
	private Context context = null;
	private ArrayList<DataElementGroupProgram> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;
	private String matchTeamLike = "";
	private int maxCount = 0;
	
	public ListAdapterProgram_(Context context, ArrayList<DataElementGroupProgram> vData, ImageUtil imgUtil
			,HashMap<String, Bitmap> hImage,String matchTeamLike,int maxCount)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
		this.hImage = hImage;
		this.matchTeamLike = matchTeamLike;
		this.maxCount = maxCount;
	}

	@Override
	public int getCount() {
		return vData.size() > maxCount ? maxCount : vData.size() ;
	}

	@Override
	public Object getItem(int position) {
		return vData.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_program_match , null);
		}
		
		TextView txtLeague = (TextView)convertView.findViewById(R.id.subprogram_txt_league);
		final RatingBar barMatch = (RatingBar)convertView.findViewById(R.id.subprogram_ratbar_match);
		final TextView txtTeam1 = (TextView)convertView.findViewById(R.id.sub_program_team1);
		final TextView txtVs = (TextView)convertView.findViewById(R.id.sub_program_channel);
		final TextView txtTeam2 = (TextView)convertView.findViewById(R.id.sub_program_team2);
		final TextView txtTime = (TextView)convertView.findViewById(R.id.sub_program_time);
		ImageView imgFb = (ImageView)convertView.findViewById(R.id.sub_program_fb);
		//TextView txtVote1 = (TextView)convertView.findViewById(R.id.subprogram_txt_vote1);
		//TextView txtVote2 = (TextView)convertView.findViewById(R.id.subprogram_txt_vote2);
		TextView txtTrend = (TextView)convertView.findViewById(R.id.subprogram_txt_trend);
		ImageView imgLikeTeam1 = (ImageView)convertView.findViewById(R.id.sub_program_liketeam1);
		ImageView imgLikeTeam2 = (ImageView)convertView.findViewById(R.id.sub_program_liketeam2);
		imgLikeTeam1.setImageBitmap(null);
		imgLikeTeam2.setImageBitmap(null);
		
		
		txtLeague.setText(vData.get(position).leagueData.contestGroupName + "  " +  vData.get(position).GroupItemCollection.get(0).matchDate +" "+vData.get(position).GroupItemCollection.get(0).matchTime);
		txtLeague.setTypeface(SportPool_BaseClass.superTypeface);
		barMatch.setRating(vData.get(position).GroupItemCollection.get(0).starLike);
		
		txtTeam1.setText(vData.get(position).GroupItemCollection.get(0).name1);
		txtTeam1.setTypeface(SportPool_BaseClass.superTypeface);
		txtTeam1.setWidth((int)(170*imgUtil.scaleSize()));
		txtTeam1.setSingleLine();
		
		txtVs.setText("VS");
		txtVs.setTypeface(SportPool_BaseClass.superTypeface);
		
		txtTeam2.setText(vData.get(position).GroupItemCollection.get(0).name2);
		txtTeam2.setTypeface(SportPool_BaseClass.superTypeface);
		txtTeam2.setWidth((int)(170*imgUtil.scaleSize()));
		txtTeam2.setSingleLine();
		
		txtTrend.setText(vData.get(position).GroupItemCollection.get(0).trend);
		txtTrend.setTypeface(SportPool_BaseClass.superTypeface);
		
		
		imgFb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				com.isport.sportpool.service.Share.shareFB(context, DataSetting.URLPLAYSOTRE, txtTime.getText() + " " + txtTeam1.getText() + " " + txtVs.getText() + " " + txtTeam2.getText(), "", DataSetting.URLLOGO);
				/*Intent intent = new Intent(context, Social_facebook.class);
				intent.putExtra("title", "SportPool Football Program");
				intent.putExtra("detail", txtTime.getText() + " " + txtTeam1.getText() + " " + txtVs.getText() + " " + txtTeam2.getText());
				intent.putExtra("urlImage", "http://market.android.com/details?id=com.isport.sportpool");
				intent.putExtra("urlLink", "http://market.android.com/details?id=com.isport.sportpool");
				context.startActivity(intent);*/
			}
		});

		/*
		txtVote1.setText(String.valueOf(vData.get(position).GroupItemCollection.get(0).precentTeam1 )+ "%");
		txtVote1.setTypeface(SportPool_BaseClass.superTypeface);
		
		txtVote2.setText(String.valueOf(vData.get(position).GroupItemCollection.get(0).precentTeam2) + "%");
		txtVote2.setTypeface(SportPool_BaseClass.superTypeface);
		
		
		imgLikeTeam1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				DataSetting.MatchTeamLike += vData.get(position).GroupItemCollection.get(0).matchId + vData.get(position).GroupItemCollection.get(0).teamCode1 + ",";
				GetURL(vData.get(position).GroupItemCollection.get(0).matchId,"1","0","0");
				
			}
		});
		
		imgLikeTeam2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				DataSetting.MatchTeamLike += vData.get(position).GroupItemCollection.get(0).matchId + vData.get(position).GroupItemCollection.get(0).teamCode1 + ",";
				GetURL(vData.get(position).GroupItemCollection.get(0).matchId,"0","1","0");
				
			}
		});*/
		
		barMatch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                     float touchPositionX = event.getX();
                     float width = barMatch.getWidth();
                     float starsf = (touchPositionX / width) * 5.0f;
                     int stars = (int)starsf + 1;
                     barMatch.setRating(stars);

                     GetURL(vData.get(position).GroupItemCollection.get(0).matchId,"0","0", String.valueOf(stars) );
                     //Toast.makeText(context, String.valueOf("test"), Toast.LENGTH_SHORT).show();                   
                     v.setPressed(false);
                }
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    v.setPressed(true);
                }

                if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    v.setPressed(false);
                }




                return true;
            }});
		
		return convertView;
	}

	
	private void GetURL(String matchId,String team1,String team2,String star)
	{
		try
		{
			//progress = ProgressDialog.show(this, null, "Loading...", true, true);
			
			String url = DataURL.MatchLike;
			url += "&matchid=" + matchId ;
			url += "&team1=" + team1;
			url += "&team2=" + team2;
			url += "&star=" + star;
			url += "&lang="+DataSetting.Languge;
			url += "&imei="+DataSetting.IMEI;
			url += "&model="+DataSetting.MODEL;
			url += "&imsi="+DataSetting.IMSI;
			url += "&type="+DataSetting.TYPE;
			
			AsycTaskLoadData load = new AsycTaskLoadData(context, this, null, "matchlike");
			load.execute(url);
			
			
		}
		catch(Exception ex)
		{
			Log.d("SportPool Error : " , ex.getMessage());
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		if(strOutput != null)
		{
			Toast.makeText(context, "Vote Success" , Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {

	}
}
