package com.isport.sportpool.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import android.graphics.Bitmap;

public class DataElementGroupScoreResult implements Serializable
{
	public DataElementLeague leagueData = null;
	
	public List<DataElementScore> GroupItemCollection ;
	
	public DataElementGroupScoreResult(DataElementLeague leagueData)
	{
		this.leagueData  = leagueData;
		GroupItemCollection = new ArrayList<DataElementScore>();
	}
}
