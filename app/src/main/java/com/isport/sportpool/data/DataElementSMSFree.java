package com.isport.sportpool.data;

import java.io.Serializable;

public class DataElementSMSFree implements Serializable
{
	public String urlFB = "";
	public String imgDescription = "";
	public String img350 = "";
	public String img400 = "";
	public String img600 = "";
	public String img1000 = "";
	public String img190 = "";
	public String detail = "";
	public String title = "";
	public String header = "";
	public String id = "";
//	public Bitmap 
	public boolean checkLoad = false;
	
	public DataElementSMSFree(String urlFB, String imgDescription , String img350, String img400, String img600,
			String img1000, String img190, String detail, String title, String header, String id)
	{
		this.urlFB = urlFB;
		this.imgDescription = imgDescription;
		this.img350 = img350;
		this.img400 = img400;
		this.img600 = img600;
		this.img1000 = img1000;
		this.img190 = img190;
		this.detail = detail;
		this.title = title;
		this.header = header;
		this.id = id;
	}
}
