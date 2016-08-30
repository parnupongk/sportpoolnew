package com.isport.sportpool.list;

import java.util.Vector;

import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementScoreDetailGame;
import com.isport.sportpool.data.DataElementScoreDetailScore;
import com.isport.sportpool.service.ImageUtil;

import android.content.Context;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ListAdapterResultDetail extends BaseAdapter
{
	private Context context = null;
	private DataElementScoreDetailScore dataResult = null;
	private Vector<DataElementScoreDetailGame> vGame = null;
	private ImageUtil imgUtil = null;
	
	public ListAdapterResultDetail(Context context, Vector<DataElementScoreDetailGame> vGame, DataElementScoreDetailScore dataResult, ImageUtil imgUtil)
	{
		this.context = context;
		this.dataResult = dataResult;
		this.vGame = vGame;
		this.imgUtil = imgUtil;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return vGame.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return vGame.elementAt(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView == null)
		{
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.sub_score_detail_game, null);
		}
		
		TextView time = (TextView) convertView.findViewById(R.id.sub_score_detail_game_time);
		ImageView event = (ImageView) convertView.findViewById(R.id.sub_score_detail_game_event);
		TextView name = (TextView) convertView.findViewById(R.id.sub_score_detail_game_namePlayer);
		LinearLayout layoutEvent = (LinearLayout) convertView.findViewById(R.id.sub_score_detail_game_layout_event);
		
		time.setWidth((int)(40*imgUtil.scaleSize()));
		
		if(vGame != null && vGame.size() > 0)
		{
			DataElementScoreDetailGame dataGame = vGame.elementAt(position);
			String playerName = dataGame.playerName;
			
			if(dataGame.evenType.toLowerCase().equals("2nd yellow"))
			{
				event.setImageResource(R.drawable.ic_2yellow);
			}
			else if(dataGame.evenType.toLowerCase().equals("red"))
			{
				event.setImageResource(R.drawable.ic_red);
			}
			else if(dataGame.evenType.toLowerCase().equals("yellow"))
			{
				event.setImageResource(R.drawable.ic_yellow);
			}
			else if(dataGame.evenType.toLowerCase().equals("penalty"))
			{
				event.setImageResource(R.drawable.ic_kickoff);
				playerName = "(pen.) "+dataGame.playerName;
			}
			else if(dataGame.evenType.toLowerCase().equals("own goal"))
			{
				event.setImageResource(R.drawable.ic_kickoff);
			}
			else if(dataGame.evenType.toLowerCase().equals("goal"))
			{
				event.setImageResource(R.drawable.ic_kickoff);
			}
			else if(dataGame.evenType.toLowerCase().equals("swap"))
			{
				event.setImageResource(R.drawable.ic_sub);
			}
						
			if(dataGame.teamId.equals(dataResult.teamCode1))
			{
				layoutEvent.setGravity(Gravity.LEFT);
			}
			else
			{
				layoutEvent.setGravity(Gravity.RIGHT);
			}
			
			if((position%2) == 0)
			{
				convertView.setBackgroundResource(R.drawable.bg_tab_leaguetable1);
			}
			else
			{
				convertView.setBackgroundResource(R.drawable.bg_tab_leaguetable2);
			}
			
			time.setText(dataGame.minute+"\"");
			name.setText(playerName);
			
			time.setGravity(Gravity.CENTER);
			//time.setTextColor(Color.WHITE);
			//name.setTextColor(Color.WHITE);
		}
		SetAnimationView(convertView);
		return convertView;
	}
	private void SetAnimationView(View convertView)
	{
		
		Animation animation = null;
		DisplayMetrics metrics = new DisplayMetrics();
		//animation = new TranslateAnimation(metrics.widthPixels/2, 0, 0, 0);
		animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
		   /*switch(mode){
		    case 1:
		     animation = new TranslateAnimation(metrics_.widthPixels/2, 0, 0, 0);
		    break;
		    
		    case 2:
		     animation = new TranslateAnimation(0, 0, metrics_.heightPixels, 0);
		    break;
		    
		    case 3:
		     animation = new ScaleAnimation((float)1.0, (float)1.0 ,(float)0, (float)1.0);
		    break;
		    
		    case 4:
		     //non animation
		     animation = new Animation() {};
		    break;
		   }*/
		   
		   animation.setDuration(750);
		   convertView.startAnimation(animation);
		   animation = null;
	}
}
