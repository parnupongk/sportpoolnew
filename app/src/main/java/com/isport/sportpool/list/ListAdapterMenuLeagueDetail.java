package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;

import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_BaseClass;
import com.isport.sportpool.data.DataElementGroupListMenu;
import com.isport.sportpool.data.DataElementListMenu;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ImageUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class ListAdapterMenuLeagueDetail extends BaseExpandableListAdapter
{
	private ArrayList<DataElementGroupListMenu> mGroupData=null;
	private Context context = null;
	private ImageUtil imgUtil = null;
	private HashMap<String, Bitmap> hImage = null;
	private String message = "";
	private ExpandableListView listView = null;
	private int[] groupStatus;
	
	public ListAdapterMenuLeagueDetail(Context context ,ExpandableListView listView
			, ArrayList<DataElementGroupListMenu> mGroupData
			, ImageUtil imgUtil, String message
			, HashMap<String, Bitmap> hImage)
	{
		this.mGroupData = mGroupData;
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
		return mGroupData.get(arg0).GroupLeagueCollection.get(arg1);
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
			convertView = inflater.inflate(R.layout.sub_menuleague_group_league,null);
		}
		
		ImageView imgView = (ImageView)convertView.findViewById(R.id.sub_menuleague_img_league);
		TextView sub_menuleague_league = (TextView)convertView.findViewById(R.id.sub_menuleague_league);
		sub_menuleague_league.setText(mGroupData.get(groupPosition).GroupLeagueCollection.get(childPosition).contestGroupName);
		sub_menuleague_league.setTypeface(SportPool_BaseClass.superTypeface);
		
		convertView.setBackgroundResource(R.drawable.menu_bg);
		imgView.setImageResource(R.drawable.star);
		if((childPosition%2) == 0)
		{
			//convertView.setBackgroundResource(R.drawable.bg_tab_othersport1);
		}
		else
		{
			//convertView.setBackgroundResource(R.drawable.bg_tab_othersport2);
		}
		//SetAnimationView(convertView);
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return mGroupData.get(groupPosition).GroupLeagueCollection.size();
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
			convertView = inflater.inflate(R.layout.sub_menuleague_group_country,null);
		}
			
			ImageView imgCountry = (ImageView)convertView.findViewById(R.id.sub_menuleague_img_country);
			TextView sub_menuleague_country = (TextView)convertView.findViewById(R.id.sub_menuleague_txt_name);

			DataElementListMenu data = mGroupData.get(groupPosition).countryData;
			sub_menuleague_country.setText(data.name);
			sub_menuleague_country.setTypeface(SportPool_BaseClass.superTypeface);
			
			if( data.icon48x48.length() > 0 && hImage.get(data.icon48x48) == null)
			{
				if(!data.checkLoad  && hImage!= null && hImage.get(data.icon48x48) == null)
				{
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.icon48x48);
				}
			}

			if(hImage!= null && hImage.get(data.icon48x48) != null)
			{
				imgCountry.setImageBitmap(hImage.get(data.icon48x48));
			}
			else
			{
				imgCountry.setImageBitmap(null);
			}
			
			if (groupStatus[groupPosition] == 0) {
				convertView.setBackgroundResource(R.drawable.menu_bg);
			} else {
				convertView.setBackgroundResource(R.drawable.menu_bg_active);
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

	public static void main(String[] args) {
		
	}
	
}
