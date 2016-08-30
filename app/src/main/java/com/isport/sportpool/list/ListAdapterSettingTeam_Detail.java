package com.isport.sportpool.list;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Vector;
import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementFavoriteTeam;
import com.isport.sportpool.data.DataElementFavoriteTeamComp;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementLeagueTable;
import com.isport.sportpool.service.ImageUtil;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


public class ListAdapterSettingTeam_Detail extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementLeagueTable> vData = null;
	private HashMap<String,String> leagueSetting = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;
	private ArrayList<DataElementFavoriteTeam> favTeam = null;
	private String contentGroupid = null;
	public ListAdapterSettingTeam_Detail(Context context, Vector<DataElementLeagueTable> vData, ImageUtil imgUtil
			,HashMap<String, Bitmap> hImage,ArrayList<DataElementFavoriteTeam> favTeam,String contentGroupId)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
		this.hImage = hImage;
		this.favTeam = favTeam;
		this.contentGroupid = contentGroupId;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return vData.get(position);
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
		
		//String g = vData.get(position).id;
		//boolean s = favTeam.equals(g);
		//Collections.sort(favTeam);
		//int ss = Collections.binarySearch(favTeam, );
		DataElementFavoriteTeam team = new DataElementFavoriteTeam(vData.get(position).name ,vData.get(position).id,contentGroupid );
		//int index = Collections.binarySearch(favTeam, team,new DataElementFavoriteTeamComp());

		boolean s = DataElementFavoriteTeam.SearchFavTeam(favTeam, team);
		if(s )
		{
			img.setImageResource(R.drawable.ic_chooseteam_press);
		}
		else
		{
			img.setImageBitmap(null);
		}
		
		txtTeam.setText(vData.get(position).name);
		
		
		return convertView;
	}


}
