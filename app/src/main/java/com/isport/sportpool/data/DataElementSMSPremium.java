package com.isport.sportpool.data;

import java.io.Serializable;

import android.graphics.Bitmap;

public class DataElementSMSPremium implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String img640 = "";
	public String img320 = "";
	public String img75 = "";
	public String title = "";
	public String header = "";
	public String message = "";
	public String phone = "";
	public String url = "";
	
	public Bitmap image = null;
	public boolean checkLoad = false;
	
	public DataElementSMSPremium(String img640, String img320, String img75, 
			String title, String header,String message,String phone,String url)
	{
		this.img640 = img640;
		this.img320 = img320;
		this.img75 = img75;
		this.title = title;
		this.header = header;
		this.message = message;
		this.phone = phone;
		this.url = url;
	}
}
