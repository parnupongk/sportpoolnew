package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementAnalyseHeadToHead;
import com.isport.sportpool.data.DataElementAnalyseHeadToHeadDetail;
import com.isport.sportpool.data.DataElementLeagueStatic;
import com.isport.sportpool.service.ImageUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;


public class ListAdapterStatTeam extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementLeagueStatic> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterStatTeam(Context context, Vector<DataElementLeagueStatic> vData, ImageUtil imgUtil,HashMap<String, Bitmap> hImage)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
		this.hImage = hImage;
	}

	@Override
	public int getCount() {
		return vData.size();
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
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_analyse_team , null);
		}
		
		
		
		TextView txtDate = (TextView)convertView.findViewById(R.id.sub_analese_team_date);
		TextView txtTo = (TextView)convertView.findViewById(R.id.sub_analese_team_to);
		TextView txtMatch = (TextView)convertView.findViewById(R.id.sub_analese_team_match);
		//TextView txt1 = (TextView)convertView.findViewById(R.id.sub_analese_team_1);
		//TextView txtX = (TextView)convertView.findViewById(R.id.sub_analese_team_x);
		TextView txt2 = (TextView)convertView.findViewById(R.id.sub_analese_team_2);
		TextView txtFt = (TextView)convertView.findViewById(R.id.sub_analese_team_ft);
		TextView txtRo = (TextView)convertView.findViewById(R.id.sub_analese_team_ro);
		
		txtDate.setWidth((int)(100*imgUtil.scaleSize()));
		//txtTo.setWidth((int)(125*imgUtil.scaleSize()));
		txtMatch.setWidth((int)(200*imgUtil.scaleSize()));
		//txt1.setWidth((int)(50*imgUtil.scaleSize()));
		//txtX.setWidth((int)(50*imgUtil.scaleSize()));
		txt2.setWidth((int)(50*imgUtil.scaleSize()));
		txtFt.setWidth((int)(50*imgUtil.scaleSize()));
		txtRo.setWidth((int)(50*imgUtil.scaleSize()));
		
		txtDate.setGravity(Gravity.CENTER);
		//txtTo.setGravity(Gravity.CENTER);
		txtMatch.setGravity(Gravity.CENTER);
		//txt1.setGravity(Gravity.CENTER);
		//txtX.setGravity(Gravity.CENTER);
		txt2.setGravity(Gravity.CENTER);
		txtFt.setGravity(Gravity.CENTER);
		txtRo.setGravity(Gravity.CENTER);
		
		

		DataElementLeagueStatic data = vData.get(position);

			txtDate.setText(data.dateTxt);
			//txtTo.setText(data.tmName);
			txtMatch.setText(data.name1 + "-" + data.name2);
			//txt1.setText("");
			//txtX.setText("X");
			txt2.setText("2");
			txtFt.setText(data.resultTeam1 + "-" + data.resultTeam2);
			txtRo.setText("Ro");

		convertView.setBackgroundResource(R.drawable.bg_list_team);
		return convertView;
	}
}
