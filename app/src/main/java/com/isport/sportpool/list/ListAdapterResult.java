package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_BaseClass;
import com.isport.sportpool.data.DataElementGroupScoreResult;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ListAdapterResult extends BaseExpandableListAdapter
{
	private ArrayList<DataElementGroupScoreResult> mGroupData=null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private HashMap<String, Bitmap> hImage = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;
	public ListAdapterResult(Context context ,ExpandableListView listView
			, ArrayList<DataElementGroupScoreResult> mGroupData
			, ImageUtil imgUtil, String message
			, HashMap<String, Bitmap> hImage)
	{
		this.mGroupData = mGroupData;
		this.listView = listView;
		this.context = context; 
		this.imgUtil = imgUtil;
		this.hImage = hImage;
		this.message = message;
		this.groupStatus = new int[mGroupData.size()];
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
		return mGroupData.get(arg0).GroupItemCollection.get(arg1);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_score_group_match,null);
		}
		RelativeLayout list_result_layout = (RelativeLayout)convertView.findViewById(R.id.list_result_layout);
		list_result_layout.setPadding((int)(5*imgUtil.scaleSize()), (int)(10*imgUtil.scaleSize()), (int)(5*imgUtil.scaleSize()), (int)(10*imgUtil.scaleSize()));
		
		
		TextView list_result_team1 = (TextView)convertView.findViewById(R.id.list_result_team1);
		TextView list_result_status = (TextView)convertView.findViewById(R.id.list_result_status);
		TextView list_result_result_ft = (TextView)convertView.findViewById(R.id.list_result_result_ft);
		TextView list_result_team2 = (TextView)convertView.findViewById(R.id.list_result_team2);
		TextView list_result_result_ht = (TextView)convertView.findViewById(R.id.list_result_result_ht);
		ImageView imgDetail = (ImageView)convertView.findViewById(R.id.list_result_img_detail);
		
		list_result_status.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).minutes);
		list_result_team1.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamName1);
		list_result_team2.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).teamName2);
		list_result_result_ft.setText(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).scoreHome + "-" +
										mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).scoreAway);
		list_result_result_ht.setText("(" + mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).scoreHomeHT + "_" +
				mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).scoreAwayHT + ")");
		
		
		list_result_status.setWidth((int)(50*imgUtil.scaleSize()));
		list_result_team1.setWidth((int)(125*imgUtil.scaleSize()));
		list_result_team2.setWidth((int)(125*imgUtil.scaleSize()));
		list_result_result_ft.setWidth((int)(80*imgUtil.scaleSize()));
		list_result_result_ht.setWidth((int)(80*imgUtil.scaleSize()));
		
		list_result_status.setTypeface(SportPool_BaseClass.superTypeface);
		list_result_team1.setTypeface(SportPool_BaseClass.superTypeface);
		list_result_team2.setTypeface(SportPool_BaseClass.superTypeface);
		list_result_result_ft.setTypeface(SportPool_BaseClass.superTypeface);
		list_result_result_ht.setTypeface(SportPool_BaseClass.superTypeface);
		
		list_result_status.setGravity(Gravity.CENTER);
		list_result_team1.setGravity(Gravity.CENTER);
		list_result_team2.setGravity(Gravity.CENTER);
		list_result_result_ft.setGravity(Gravity.CENTER);
		list_result_result_ht.setGravity(Gravity.CENTER);
		
		if(mGroupData.get(groupPosition).GroupItemCollection.get(childPosition).isDetail  )
		{
			imgDetail.setImageResource(R.drawable.group_down);
		}
		else imgDetail.setImageBitmap(null);
		
		convertView.setBackgroundResource(R.drawable.bg_list_team);
		
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupData.get(groupPosition).GroupItemCollection.size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return mGroupData.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		return mGroupData.size();
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_score_group_league,null);
		}
			TextView sub_score_group_league_txt = (TextView)convertView.findViewById(R.id.sub_score_group_league_txt);
			ImageView imgHeader = (ImageView)convertView.findViewById(R.id.sub_score_group_league_img);
			ImageView imgGroup = (ImageView)convertView.findViewById(R.id.sub_score_group_league_imgExpand);
			
			DataElementLeague data = mGroupData.get(groupPosition).leagueData;
			sub_score_group_league_txt.setText(data.contestGroupName);
			sub_score_group_league_txt.setTypeface(SportPool_BaseClass.superTypeface);
			//imgHeader.setPadding((int)5, 5, (int)(5*imgUtil.scaleSize()), 0);
			//imgHeader.setLayoutParams(new FrameLayout.LayoutParams(16, 11, Gravity.CENTER_VERTICAL));
			if(!data.checkLoad && data.contestURLImages.length() > 0 )
			{
				if(hImage!= null && hImage.get(data.contestURLImages) == null)
				{
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.contestURLImages);
				}
			}

			
			if(hImage!= null && hImage.get(data.contestURLImages) != null)
			{
				imgHeader.setImageBitmap(hImage.get(data.contestURLImages));
			}
			else
			{
				imgHeader.setImageBitmap(null);
			}
			
			if (groupStatus[groupPosition] == 0) {
				imgGroup.setImageResource(R.drawable.group_down);
				convertView.setBackgroundResource(R.drawable.bg_list_team);
			} else {
				imgGroup.setImageResource(R.drawable.group_up);
				convertView.setBackgroundResource(R.drawable.bg_list_league);
			}

			
			
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

	
}
