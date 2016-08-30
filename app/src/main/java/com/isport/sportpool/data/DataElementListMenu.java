package com.isport.sportpool.data;

import java.io.Serializable;

public class DataElementListMenu implements Serializable 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String countryId = "";
	public String countryName = "";
	public String name = "";
	public String contestGroupId = "";
	public String icon16x11 = "";
	public String icon48x48 = "";
	public boolean checkLoad = false;
	
//	public boolean chooseCountry = false;
	
	public DataElementListMenu(String countryId, String name, String contestGroupId, String icon16x11, String icon48x48)
	{
		this.countryId = countryId;
		this.name = name;
		this.contestGroupId = contestGroupId;
		this.icon16x11 = icon16x11;
		this.icon48x48 = icon48x48;
	}
	public DataElementListMenu(String countryId, String countryName,String name, String contestGroupId, String icon16x11, String icon48x48)
	{
		this.countryId = countryId;
		this.countryName = countryName;
		this.name = name;
		this.contestGroupId = contestGroupId;
		this.icon16x11 = icon16x11;
		this.icon48x48 = icon48x48;
	}
}
