package com.isport.sportpool.service;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class AsycTaskLoadImage extends AsyncTask<String, Void, Bitmap>
{
	private ImageView iv = null;
	private ProgressBar pb = null;
	private String url = "";
	private HashMap<String, Bitmap> hImage = null;
	private ReceiveDataListener receive = null;
	private BaseAdapter list = null;
	private BaseExpandableListAdapter listexpand = null;
	public AsycTaskLoadImage(ImageView iv,ProgressBar pb, HashMap<String, Bitmap> hImage, ReceiveDataListener receive, BaseAdapter list)
	{
		this.iv = iv;
		this.pb = pb;
		this.hImage = hImage;
		this.receive = receive;
		this.list = list;
	}
	public AsycTaskLoadImage(ImageView iv,ProgressBar pb, HashMap<String, Bitmap> hImage, ReceiveDataListener receive, BaseExpandableListAdapter listexpand)
	{
		this.iv = iv;
		this.pb = pb;
		this.hImage = hImage;
		this.receive = receive;
		this.listexpand = listexpand;
	}
	public AsycTaskLoadImage(ImageView iv,ProgressBar pb, HashMap<String, Bitmap> hImage, ReceiveDataListener receive)
	{
		this.iv = iv;
		this.pb = pb;
		this.hImage = hImage;
		this.receive = receive;

	}
	
	@Override
	protected void onPreExecute (){
		super.onPreExecute();
		if(pb!=null){
			pb.setVisibility(View.VISIBLE);
		}
	}
	
	@Override
	protected Bitmap doInBackground(String... urls) {
		// TODO Auto-generated method stub
		try {
			url = urls[0];
			//PrintLog.print("AsycTaskLoadImage method doInBackground", "url image = "+url);
			URL url = new URL(urls[0]);
			Bitmap b = null;
			b = BitmapFactory.decodeStream((InputStream) url.getContent());
			
//			if(b == null)
//			{
//				PrintLog.print("AsycTaskLoadImage method doInBackground", "url == null : "+url);
//			}
//			else
//			{
//				PrintLog.print("AsycTaskLoadImage method doInBackground", "url != null : "+url);
//			}
			
			return b;
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("AsycTaskLoadImage method doInBackground", e);
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Void... item)
	{
	}
	
	@Override
	protected void onPostExecute(Bitmap result)
	{
		if(result != null)
		{
//			receive.onReceiveImage(url, result);
			if(pb != null)
			{
				pb.setVisibility(View.INVISIBLE);
			}
			
			if(iv != null)
			{
				iv.setImageBitmap(result);
				iv.postInvalidate();
			}
			
			if(list != null)
			{
				list.notifyDataSetChanged();
			}
			
			if( listexpand != null )
			{
				listexpand.notifyDataSetChanged();
			}
			
			if(hImage != null)
			{
				//PrintLog.print("AsycTaskLoadImage method doInBackground", "put image = "+url);
				hImage.put(this.url, result);
			}
		}
		else
		{
			if(iv != null)
			{
//				iv.setImageResource(R.drawable.img_big);
//				iv.postInvalidate();
			}
			
			if(pb != null)
			{
				pb.setVisibility(View.INVISIBLE);
			}
		}
	}
}
