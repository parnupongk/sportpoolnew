package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;
import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_BaseClass;
import com.isport.sportpool.data.DataElementFavoriteTeam;

import android.content.Context;
import android.opengl.Visibility;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapterSettingTeam extends BaseAdapter
{
	private Context context = null;

	private ArrayList<DataElementFavoriteTeam> favTeam = null;


	
	public ListAdapterSettingTeam(Context context,ArrayList<DataElementFavoriteTeam> favTeam)
	{
		this.context = context;
		this.favTeam = favTeam;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return favTeam.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_setting_team_detail , null);
		}
		
		TextView txtTeam = (TextView)convertView.findViewById(R.id.sub_settingteam_txt_team);
		ImageView img = (ImageView)convertView.findViewById(R.id.sub_settingteam_img_select);
		img.setImageResource(R.drawable.ic_chooseteam_press);
		
		String g =  favTeam.get(position).teamName; //(new ArrayList<String>(leagueSetting.values())).get(position);
		/*
		if(leagueSetting!=null && leagueSetting.containsKey(g) )
		{
			chk.setChecked(true);
		}
		else
		{
			chk.setChecked(false);
		}
		chk.setTag(g);*/
		txtTeam.setTypeface(SportPool_BaseClass.superTypeface);
		txtTeam.setText(g);
		
		
		return convertView;
	}

	
}
