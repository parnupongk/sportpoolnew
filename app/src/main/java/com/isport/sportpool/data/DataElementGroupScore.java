package com.isport.sportpool.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataElementGroupScore implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public DataElementLeague leagueData = null;
	
	public List<DataElementScore> GroupItemCollection ;
	
	public DataElementGroupScore(DataElementLeague leagueData)
	{
		this.leagueData  = leagueData;
		this.GroupItemCollection = new ArrayList<DataElementScore>();
	}
}
