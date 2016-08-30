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

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

import org.xml.sax.SAXException;

public class ListAdapterProgram extends BaseExpandableListAdapter implements OnClickListener,ReceiveDataListener
{
	private ArrayList<DataElementGroupProgram> vData=null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private HashMap<String, Bitmap> hImage = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;
	private ImageView btnFB =null;
	private ImageView btnTW = null;
	
	public ListAdapterProgram(Context context ,ExpandableListView listView
			, ArrayList<DataElementGroupProgram> mGroupData
			, ImageUtil imgUtil, String message
			, HashMap<String, Bitmap> hImage)
	{
		this.vData = mGroupData;
		this.listView = listView;
		this.context = context; 
		this.imgUtil = imgUtil;
		this.hImage = hImage;
		this.message = message;
		groupStatus = new int[mGroupData.size()];
		setListEvent();
	}
	
	private void setListEvent() {
		

		listView.setOnGroupExpandListener(new OnGroupExpandListener() {

					@Override
					public void onGroupExpand(int arg0) {
						groupStatus[arg0] = 1;
					}
				});

		listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {

					@Override
					public void onGroupCollapse(int arg0) {
						groupStatus[arg0] = 0;
					}
				});
	}

	@Override
	public Object getChild(int arg0, int arg1) {
		return vData.get(arg0).GroupItemCollection.get(arg1);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_program_match,null);
		}
		
		//RelativeLayout voteLayout = (RelativeLayout)convertView.findViewById(R.id.subprogram_layout_detail1);
		//TextView txtVote1 = (TextView)convertView.findViewById(R.id.subprogram_txt_vote1);
		//TextView txtVote2 = (TextView)convertView.findViewById(R.id.subprogram_txt_vote2);
		//voteLayout.setVisibility(View.INVISIBLE);
		
		
		final TextView txtTeam1 = (TextView)convertView.findViewById(R.id.sub_program_team1);
		final TextView txtVs = (TextView)convertView.findViewById(R.id.sub_program_channel);
		final TextView txtTeam2 = (TextView)convertView.findViewById(R.id.sub_program_team2);
		final TextView txtTime = (TextView)convertView.findViewById(R.id.sub_program_time);
		ImageView imgFb = (ImageView)convertView.findViewById(R.id.sub_program_fb);
		TextView txtTrend = (TextView)convertView.findViewById(R.id.subprogram_txt_trend);
		ImageView imgLikeTeam1 = (ImageView)convertView.findViewById(R.id.sub_program_liketeam1);
		ImageView imgLikeTeam2 = (ImageView)convertView.findViewById(R.id.sub_program_liketeam2);
		imgLikeTeam1.setImageBitmap(null);
		imgLikeTeam2.setImageBitmap(null);
		
		
		txtTeam1.setText(vData.get(groupPosition).GroupItemCollection.get(childPosition).name1);
		txtTeam1.setTypeface(SportPool_BaseClass.superTypeface);
		txtTeam1.setWidth((int)(170*imgUtil.scaleSize()));
		txtTeam1.setSingleLine();

		if( !vData.get(groupPosition).GroupItemCollection.get(childPosition).liveChannel.equals("") )
		{
			txtVs.setText(vData.get(groupPosition).GroupItemCollection.get(childPosition).liveChannel);
		}
		else
		{
			txtVs.setText("VS");
		}
		txtVs.setTypeface(SportPool_BaseClass.superTypeface);

		txtTime.setText(vData.get(groupPosition).GroupItemCollection.get(childPosition).matchTime);
		txtTime.setTypeface(SportPool_BaseClass.superTypeface);
		
		txtTeam2.setText(vData.get(groupPosition).GroupItemCollection.get(childPosition).name2);
		txtTeam2.setTypeface(SportPool_BaseClass.superTypeface);
		txtTeam2.setWidth((int)(170*imgUtil.scaleSize()));
		txtTeam2.setSingleLine();
		
		String trend = (vData.get(groupPosition).GroupItemCollection.get(childPosition).trend.equals(""))? vData.get(groupPosition).GroupItemCollection.get(childPosition).trend : "ผลที่คาด : " + vData.get(groupPosition).GroupItemCollection.get(childPosition).trend;
		txtTrend.setText(trend);
		txtTrend.setTypeface(SportPool_BaseClass.superTypeface);
		
		imgFb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				com.isport.sportpool.service.Share.shareFB(context, DataSetting.URLPLAYSOTRE, txtTime.getText() + " " + txtTeam1.getText() + " " + txtVs.getText() + " " + txtTeam2.getText(), "", DataSetting.URLLOGO);
				
				/*Intent intent = new Intent(context, Social_facebook.class);
				intent.putExtra("title", "SportPool Football Program");
				intent.putExtra("detail", txtTime.getText() + " " + txtTeam1.getText() + " " + txtVs.getText() + " " + txtTeam2.getText());
				intent.putExtra("urlImage", "");
				intent.putExtra("urlLink", "http://market.android.com/details?id=com.isport.sportpool");
				context.startActivity(intent);*/
			}
		});
		
		/*txtVote1.setText(String.valueOf(vData.get(groupPosition).GroupItemCollection.get(childPosition).precentTeam1 )+ "%");
		txtVote1.setTypeface(SportPool_BaseClass.superTypeface);
		
		txtVote2.setText(String.valueOf(vData.get(groupPosition).GroupItemCollection.get(childPosition).precentTeam2) + "%");
		txtVote2.setTypeface(SportPool_BaseClass.superTypeface);
		
		
		imgLikeTeam1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				DataSetting.MatchTeamLike += vData.get(groupPosition).GroupItemCollection.get(childPosition).matchId + vData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode1 + ",";
				GetURL(vData.get(groupPosition).GroupItemCollection.get(childPosition).matchId,"1","0","0");
				
			}
		});
		
		imgLikeTeam2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {

				DataSetting.MatchTeamLike += vData.get(groupPosition).GroupItemCollection.get(childPosition).matchId + vData.get(groupPosition).GroupItemCollection.get(childPosition).teamCode1 + ",";
				GetURL(vData.get(groupPosition).GroupItemCollection.get(childPosition).matchId,"0","1","0");
				
			}
		});
		*/
		
		
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
	public int getChildrenCount(int groupPosition) {
		return vData.get(groupPosition).GroupItemCollection.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return vData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return vData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(final int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_program_league,null);
		}
			
		TextView txtLeague = (TextView)convertView.findViewById(R.id.subprogram_txt_league);
		final RatingBar barMatch = (RatingBar)convertView.findViewById(R.id.subprogram_ratbar_match);
		
		txtLeague.setText(vData.get(groupPosition).leagueData.contestGroupName + "  " +  vData.get(groupPosition).GroupItemCollection.get(0).matchDate +" "+vData.get(groupPosition).GroupItemCollection.get(0).matchTime);
		txtLeague.setTypeface(SportPool_BaseClass.superTypeface);
		barMatch.setRating(vData.get(groupPosition).GroupItemCollection.get(0).starLike);
		
		barMatch.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                     float touchPositionX = event.getX();
                     float width = barMatch.getWidth();
                     float starsf = (touchPositionX / width) * 5.0f;
                     int stars = (int)starsf + 1;
                     barMatch.setRating(stars);

                     GetURL(vData.get(groupPosition).GroupItemCollection.get(0).matchId,"0","0", String.valueOf(stars) );
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
		ExpandableListView eLV = (ExpandableListView) parent;
		eLV.expandGroup(groupPosition);
		
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}
	private void SetAnimationView(View convertView)
	{
		
		Animation animation = null;
		DisplayMetrics metrics = new DisplayMetrics();
		//animation = new TranslateAnimation(metrics.widthPixels/2, 0, 0, 0);
		animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
		   
		   animation.setDuration(750);
		   convertView.startAnimation(animation);
		   animation = null;
	}


	@Override
	public void onClick(View v) {

		
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
