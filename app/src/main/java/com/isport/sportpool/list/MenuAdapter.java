package com.isport.sportpool.list;

import java.util.ArrayList;
import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_Analyse;
import com.isport.sportpool.SportPool_LeagueTable;
import com.isport.sportpool.SportPool_Livescore;
import com.isport.sportpool.SportPool_Program;
import com.isport.sportpool.SportPool_Result;
import com.isport.sportpool.SportPool_SMSService;
import com.isport.sportpool.SportPool_SettingTeam;
import com.isport.sportpool.data.DataElementListMenuLeft;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MenuAdapter extends BaseAdapter {	
	private Context context;
	private ArrayList<DataElementListMenuLeft> items;
	private LayoutInflater inflater;	
	private String current_page = "";

	public MenuAdapter(Context context, ArrayList<DataElementListMenuLeft> items, Activity act,String current_page) {
		this.context = context;
		this.items = items;
		this.inflater = LayoutInflater.from(context);	
		this.current_page = current_page;
	}
			
	public class ViewHolder {
		//RelativeLayout layoutTitle ;
		TextView title ;
		LinearLayout menuLayout ;
		ImageView img ;
		ImageView imgHeader ;
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder;
		
		if( convertView == null)
		{
			convertView = inflater.inflate(R.layout.sportpool_item_menu, null);
			holder = new ViewHolder();
			//holder.layoutTitle = (RelativeLayout)convertView.findViewById(R.id.menu_layout_title);
			holder.title = (TextView) convertView.findViewById(R.id.menu_title);
			holder.menuLayout = (LinearLayout)convertView.findViewById(R.id.left_menu_layout);
			holder.img = (ImageView)convertView.findViewById(R.id.left_menu_img);
			holder.imgHeader = (ImageView)convertView.findViewById(R.id.menu_image_header);
			
			convertView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}
		//int t = this.getCount();
		String nameEn = getItem(position).name_en;
		if( position == 0 )
		{
			holder.imgHeader.setVisibility(View.VISIBLE);
			//holder.layoutTitle.setVisibility(View.GONE);
		}
		else
		{
			holder.imgHeader.setVisibility(View.GONE);
			//holder.layoutTitle.setVisibility(View.VISIBLE);

			if( getItem(position).type.equals("header") )
			{
				holder.img.setImageResource(R.drawable.ic_chooseteamball);
				holder.menuLayout.setBackgroundResource(R.drawable.bg_header_side_menu);
			}
			else
			{
				
				if( current_page.equals(nameEn) )
				{
					holder.menuLayout.setBackgroundResource(R.drawable.menu_bg_active);
					if(current_page.equals("first"))
					{
						holder.img.setImageResource(R.drawable.ic_chooseteamball);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_analyse_en)))
					{
						holder.img.setImageResource(R.drawable.ic_guru_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_favteam_en)))
					{
						holder.img.setImageResource(R.drawable.ic_favourite_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_leaguetable_en)))
					{
						holder.img.setImageResource(R.drawable.ic_table_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_program_en)))
					{
						holder.img.setImageResource(R.drawable.ic_program_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_livescore_en)))
					{
						holder.img.setImageResource(R.drawable.ic_live_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_result_en)))
					{
						holder.img.setImageResource(R.drawable.ic_live_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_smsservice_en)))
					{
						
						holder.img.setImageResource(R.drawable.ic_sms_press);
					}
					else if(current_page.equals(context.getResources().getString(R.string.page_tded_en)))
					{
						holder.img.setImageResource(R.drawable.ic_chooseteam_press);
					}
				}
				else
				{
	
					holder.menuLayout.setBackgroundResource(R.drawable.menu_bg);
					if(nameEn != null &&  nameEn.equals("first"))
					{
						holder.img.setImageResource(R.drawable.ic_chooseteamball);
					}
					else if( nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_analyse_en)))
					{
						holder.img.setImageResource(R.drawable.ic_guru);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_favteam_en)))
					{
						holder.img.setImageResource(R.drawable.ic_favourite);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_leaguetable_en)))
					{
						holder.img.setImageResource(R.drawable.ic_table);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_program_en)))
					{
						holder.img.setImageResource(R.drawable.ic_program);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_livescore_en)))
					{
						holder.img.setImageResource(R.drawable.ic_live);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_result_en)))
					{
						holder.img.setImageResource(R.drawable.ic_live);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_smsservice_en)))
					{
						
						holder.img.setImageResource(R.drawable.ic_sms);
					}
					else if(nameEn != null && nameEn.equals(context.getResources().getString(R.string.page_tded_en)))
					{
						holder.img.setImageResource(R.drawable.ic_chooseteam);
					}
				}
			}
		
		}
		
		holder.title.setText(getItem(position).name);			
		return convertView;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public DataElementListMenuLeft getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
