package com.isport.sportpool.list;

import java.util.HashMap;
import java.util.Vector;
import com.isport.sportpool.R;
import com.isport.sportpool.data.DataElementSMSPremium;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ImageUtil;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.ProgressBar;
import android.widget.TextView;

public class ListAdapterSMSPremium extends BaseAdapter
{
	private Context context = null;
	private Vector<DataElementSMSPremium> vData = null;
	private HashMap<String, Bitmap> hImage = null;
	private ImageUtil imgUtil = null;

	
	public ListAdapterSMSPremium(Context context, Vector<DataElementSMSPremium> vData, ImageUtil imgUtil,HashMap<String, Bitmap> hImage)
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
			convertView = inflater.inflate(R.layout.sub_smspremium , null);
		}
		
		TextView title = (TextView) convertView.findViewById(R.id.sub_smspremium_text_title);
		TextView header = (TextView) convertView.findViewById(R.id.sub_smspremium_text_header);
		ImageView image = (ImageView) convertView.findViewById(R.id.sub_smspremium_image);
		ProgressBar progress = (ProgressBar) convertView.findViewById(R.id.sub_smspremium_progress);
		

		image.setPadding(0,(int)(10*imgUtil.scaleSize()), (int)(5*imgUtil.scaleSize()), 0);
		image.setLayoutParams(new FrameLayout.LayoutParams((int)(imgUtil.screen_width()/2), (int)(imgUtil.screen_width()/4), Gravity.TOP));
		progress.setLayoutParams(new FrameLayout.LayoutParams((int)(30*imgUtil.scaleSize()), (int)(30*imgUtil.scaleSize()), Gravity.CENTER));
		
		
		
		
		if( vData != null && vData.elementAt(position) != null )
		{
			DataElementSMSPremium data = vData.get(position);
			SpannableString content = new SpannableString(data.header);
		    content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
	
			title.setText(content);
			header.setText(data.title);
			//title.setTextColor(Color.rgb(45, 45, 45));
			//detail.setTextColor(Color.rgb(45, 45, 45));
			title.setMaxLines(1);
			header.setMaxLines(2);
			
			//String img = imgUtil.scaleSize() > 1.2 ? data.img320 : data.img75 ;
			
			String img = data.img320;
			if(!data.checkLoad && img.length() > 0 )
			{
				if(hImage!= null && hImage.get(img) == null)
				{
					data.checkLoad = true;
					AsycTaskLoadImage loadImage = new AsycTaskLoadImage(null, null, hImage, null, this);
					loadImage.execute(img);
				}
			}
	
			
			if(hImage!= null && hImage.get(img) != null)
			{
				if(progress.getVisibility() == View.VISIBLE)
					progress.setVisibility(View.INVISIBLE);
				image.setImageBitmap(hImage.get(img));
			}
			else
			{
				image.setImageBitmap(null);
				progress.setVisibility(View.VISIBLE);
			}
		}
			

		
		return convertView;
	}
}
