package com.isport.sportpool.data;

import java.io.Serializable;

import android.graphics.Bitmap;

public class DataElementLeague implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String tmName = "";
	public String contestURLImages = "";
	public String contestGroupId = "";
	public String contestGroupName = "";
	public String tmSystem = "";
	
	public Bitmap image = null;
	public boolean checkLoad = false;
	
	public DataElementLeague(String sTmName, String sContestURLImages, String sContestGroupId, 
			String sContestGroupName, String sTmSystem)
	{
		tmName = sTmName;
		contestURLImages = sContestURLImages;
		contestGroupId = sContestGroupId;
		contestGroupName = sContestGroupName;
		tmSystem = sTmSystem;
	}
}
