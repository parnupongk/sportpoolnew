package com.isport.sportpool.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;


public class ImageUtil
{
	Context context;
	private float density = 0,screen_width = 0,screen_height = 0;
	private float scaleSize = 0;
	

	public ImageUtil(Context _context){
		
		context = _context;
		init();
	}
	
	private void init(){
		
		DisplayMetrics dismas = context.getResources().getDisplayMetrics();
	    density = dismas.density;
	    screen_width = dismas.widthPixels;
	    screen_height = dismas.heightPixels;
	    scaleSize = screen_width/480;

	}
	
	public Bitmap scaleResource(int res_id){
		
		Bitmap b = BitmapFactory.decodeResource(context.getResources(),res_id);
		b = Bitmap.createScaledBitmap(b,(int)(b.getWidth()*scaleSize),(int)(b.getHeight()*scaleSize), true);
		return b;
	}
	public Bitmap scaleResource(int res_id,int new_width,int new_height){
		
		Bitmap b = BitmapFactory.decodeResource(context.getResources(),res_id);
//		Bitmap bb = Bitmap.createScaledBitmap(b,new_width,new_height, true);
//		Bitmap bbb = Bitmap.createScaledBitmap(bb,(int)(bb.getWidth()*scaleSize),(int)(bb.getHeight()*scaleSize), true);
		Bitmap bbb = Bitmap.createScaledBitmap(b,(int)(b.getWidth()*scaleSize),(int)(b.getHeight()*scaleSize), true);
		
		return bbb;
	}
	public Bitmap scaleBitmap(Bitmap bitmap){
		
		Bitmap b = bitmap;
		if(b != null)
		{
			b = Bitmap.createScaledBitmap(b,(int)(b.getWidth()*scaleSize),(int)(b.getHeight()*scaleSize), true);
		}
		return b;
	}
	public Bitmap scaleBitmap(Bitmap bitmap,int new_width,int new_height){
		
		Bitmap b = bitmap;
//		Bitmap bb = Bitmap.createScaledBitmap(b,new_width,new_height, true);
//		Bitmap bbb = Bitmap.createScaledBitmap(bb,(int)(bb.getWidth()*scaleSize),(int)(bb.getHeight()*scaleSize), true);
		Bitmap bbb = Bitmap.createScaledBitmap(b,(int)(b.getWidth()*scaleSize),(int)(b.getHeight()*scaleSize), true);
		return bbb;
	}
	public Bitmap cropBitmap(Bitmap src,int s_width,int s_heigth){
		int width = src.getWidth();
		int height = src.getHeight();
		int newWidth = s_width;
		int newHeight = s_heigth;
		
		Bitmap img = null;
		if(width >= height)
		{ 
			float scale = (float)newHeight/(float)src.getHeight();
			img = Bitmap.createScaledBitmap(src,(int) (width*scale),(int)(height*scale), true);
			img = Bitmap.createBitmap(img, (img.getWidth()/2)-(newWidth/2), 0, newWidth, newHeight);
		} 
		else {
			float scale = (float)newWidth/(float)src.getWidth();
			img = Bitmap.createScaledBitmap(src,(int) (width*scale),(int)(height*scale), true);
			img = Bitmap.createBitmap(img,0,(img.getHeight()/2)-(newHeight/2), newWidth, newHeight);
		}
		return img;
	}
	
	public float scaleSize(){
		return scaleSize;
	}
	
	public float screen_width(){
		return screen_width;
	}
	
	public float screen_height(){
		return screen_height;
	}
}
