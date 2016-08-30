package com.isport.sportpool.list;

import java.util.HashMap;
import java.util.Vector;

import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementSMSFree;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ImageUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ListAdapterSMSFree extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementSMSFree> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterSMSFree(Context context, Vector<DataElementSMSFree> vData, ImageUtil imgUtil,HashMap<String, Bitmap> hImage)
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
		return vData.elementAt(position);
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
			convertView = inflater.inflate(R.layout.sub_smsfree , null);
		}

		LinearLayout list_main_news_layout = (LinearLayout)convertView.findViewById(R.id.sub_smsfree_layout);
		TextView title = (TextView) convertView.findViewById(R.id.sub_smsfree_text_title);
		TextView detail = (TextView) convertView.findViewById(R.id.sub_smsfree_text_detail);
		ImageView image = (ImageView) convertView.findViewById(R.id.sub_smsfree_image);
		ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.sub_smsfree_progress);
		
		//list_main_news_layout.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()), 0);
		//convertView.setPadding(0, (int)(10*imgUtil.scaleSize()), 0, (int)(10*imgUtil.scaleSize()));
		image.setPadding(5,5, 5, 5);
		image.setLayoutParams(new FrameLayout.LayoutParams((int)(imgUtil.screen_width()/2), (int)(imgUtil.screen_width()/4), Gravity.TOP));
		progress.setLayoutParams(new FrameLayout.LayoutParams((int)(30*imgUtil.scaleSize()), (int)(30*imgUtil.scaleSize()), Gravity.CENTER));
		
		//convertView.setBackgroundColor(Color.rgb(207, 207, 207));
		
		if(vData != null && vData.elementAt(position) != null)
		{
			DataElementSMSFree data = vData.elementAt(position);
			
			SpannableString content = new SpannableString(data.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);

			title.setText(content);
			detail.setText(data.title);
			//title.setTextColor(Color.rgb(45, 45, 45));
			//detail.setTextColor(Color.rgb(45, 45, 45));
			title.setMaxLines(1);
			detail.setMaxLines(2);
			if(hImage!= null && hImage.get(data.img350) == null)
			{
				if(!data.checkLoad && data.img350.length() > 0)
				{
					
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(data.img350);
				}
			}
			
			if(hImage!= null && hImage.get(data.img350)!= null)
			{
				if(progress.getVisibility() == View.VISIBLE)
					progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(hImage.get(data.img350));
			}
			else if(!(data.img350.length() > 0))
			{
				//image.setImageResource(R.drawable.img_small);
			}
			else
			{
//				image.setImageResource(R.drawable.img_small);
//				progress.setVisibility(View.INVISIBLE);
				
				image.setImageBitmap(null);
				progress.setVisibility(View.VISIBLE);
			}
		}
		return convertView;
	}
}
