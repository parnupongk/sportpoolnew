package com.isport.sportpool.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.support.annotation.AttrRes;

public class DataElementGroupProgram implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	
	public DataElementLeague leagueData = null;
	
	public List<DataElementProgram> GroupItemCollection ;
	
	public DataElementGroupProgram(DataElementLeague leagueData)
	{
		this.leagueData  = leagueData;
		this.GroupItemCollection = new ArrayList<DataElementProgram>();
	}
}
