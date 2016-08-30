package com.isport.sportpool.list;

import java.util.ArrayList;
import java.util.HashMap;
import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementGroupScore;
import com.isport.sportpool.service.ImageUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;


public class ListAdapterScore extends BaseAdapter
{
	private Context context = null;
	private ArrayList<DataElementGroupScore> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterScore(Context context, ArrayList<DataElementGroupScore> vData, ImageUtil imgUtil,HashMap<String, Bitmap> hImage)
	{
		this.context = context;
		this.vData = vData;
		this.imgUtil = imgUtil;
		this.hImage = hImage;
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
			convertView = inflater.inflate(R.layout.sub_score_match , null);
		}
		
		TextView txtLeague = (TextView)convertView.findViewById(R.id.subscore_txt_league);
		RatingBar barMatch = (RatingBar)convertView.findViewById(R.id.subscore_ratbar_match);
		TextView txtTeam1 = (TextView)convertView.findViewById(R.id.sub_score_team1);
		TextView txtVs = (TextView)convertView.findViewById(R.id.sub_score_channel);
		TextView txtTeam2 = (TextView)convertView.findViewById(R.id.sub_score_team2);
		TextView txtVote1 = (TextView)convertView.findViewById(R.id.subscore_txt_vote1);
		TextView txtVote2 = (TextView)convertView.findViewById(R.id.subscore_txt_vote2);
		TextView txtScore = (TextView)convertView.findViewById(R.id.subscore_txt_score);
		
		txtVote1.setVisibility(View.GONE);
		txtVote2.setVisibility(View.GONE);
		
		txtLeague.setText(vData.get(position).leagueData.contestGroupName + " / " + vData.get(position).GroupItemCollection.get(0).matchDate) ;
		barMatch.setRating(3);
		txtTeam1.setText(vData.get(position).GroupItemCollection.get(0).teamName1);
		txtVs.setText("VS");
		txtTeam2.setText(vData.get(position).GroupItemCollection.get(0).teamName2);
		txtVote1.setText("80%");
		txtVote2.setText("30%");
		txtScore.setText(vData.get(position).GroupItemCollection.get(0).scoreHome + " : " + vData.get(position).GroupItemCollection.get(0).scoreAway);
		
		
		return convertView;
	}
}
