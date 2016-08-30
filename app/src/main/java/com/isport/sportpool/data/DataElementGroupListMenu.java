package com.isport.sportpool.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataElementGroupListMenu implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DataElementListMenu countryData = null;
	
	public List<DataElementLeague> GroupLeagueCollection ;
	
	public DataElementGroupListMenu(DataElementListMenu countryData)
	{
		this.countryData  = countryData;
		GroupLeagueCollection = new ArrayList<DataElementLeague>();
	}
}
