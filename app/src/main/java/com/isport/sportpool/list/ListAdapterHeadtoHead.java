package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementAnalyseHeadToHead;
import com.isport.sportpool.data.DataElementAnalyseHeadToHeadDetail;
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


public class ListAdapterHeadtoHead extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementAnalyseHeadToHeadDetail> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterHeadtoHead(Context context, Vector<DataElementAnalyseHeadToHeadDetail> vData, ImageUtil imgUtil,HashMap<String, Bitmap> hImage)
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
			convertView = inflater.inflate(R.layout.sub_analyse_static , null);
		}
		
		
		LinearLayout listTeam1 = (LinearLayout)convertView.findViewById(R.id.subanalyse_layout_team1);
		LinearLayout listTeam2 = (LinearLayout)convertView.findViewById(R.id.subanalyse_layout_team2);
		TextView txtVs = (TextView)convertView.findViewById(R.id.subanalyse_txt_vs);
		listTeam1.removeAllViews();
		listTeam2.removeAllViews();
		DataElementAnalyseHeadToHeadDetail data = vData.get(position);
		//if( !data.check )
		//{
		//listTeam1.setLayoutParams(new LayoutParams((int)(80*imgUtil.scaleSize()),LayoutParams.WRAP_CONTENT));
		//listTeam2.setLayoutParams(new LayoutParams((int)(80*imgUtil.scaleSize()),LayoutParams.WRAP_CONTENT));
			for(int i=0;i<Integer.parseInt(data.result_team1);i++)
			{
				ImageView img  = new ImageView(context);
				img.setImageResource(R.drawable.ball);
				listTeam1.addView(img);
			}
			for(int i=0;i<Integer.parseInt(data.result_team2);i++)
			{
				ImageView img  = new ImageView(context);
				img.setImageResource(R.drawable.ball);
				listTeam2.addView(img);
			}
			
			//data.check = true;
		//}
		//convertView.setBackgroundResource(R.drawable.bg_list_team);
		
		txtVs.setText(data.result_team1 + " : " + data.result_team2);
		//listTeam1.addView( )
		
		return convertView;
	}
}
