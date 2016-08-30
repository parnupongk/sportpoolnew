package com.isport.sportpool.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;

import android.graphics.Bitmap;


public class DataElementFavoriteTeam implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String teamName = "";
	public String teamCode = "";
	public String contestGroupId = "";
	
	
	public DataElementFavoriteTeam(String teamName, String teamCode, String contestGroupId)
	{
		this.teamName = teamName;
		this.teamCode = teamCode;
		this.contestGroupId = contestGroupId;
	}
	public String getteamCode() {
        return teamCode;
    }

	
	public static boolean SearchFavTeam(ArrayList<DataElementFavoriteTeam> arrFavTeam, DataElementFavoriteTeam favTeam)
	{
		boolean rtn = false;
		for( DataElementFavoriteTeam fav : arrFavTeam  )
		{
			if (fav.teamCode.equals(favTeam.teamCode))
			{
				rtn = true;
				break;
			}
		}
		return rtn;
	}
}
