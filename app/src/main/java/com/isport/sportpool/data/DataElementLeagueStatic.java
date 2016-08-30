package com.isport.sportpool.data;

import android.R.bool;

public class DataElementLeagueStatic
{
	public String name1 = "";
	public String name2 = "";
	public String staticTeam = "";
	public String resultTeam2 = "";
	public String resultTeam1 = "";
	public String tmName = "";
	public String dateTxt = "";
	public int index = 0;
	public boolean check = true;
	public DataElementLeagueStatic(String sTeamName1, String sTeamName2,
			String sStaticTeam, String sResultTeam2, String sResultTeam1,String tmName,String dateTxt,int index)
	{
		this.name1 = sTeamName1;
		this.name2 = sTeamName2;
		this.staticTeam = sStaticTeam;
		this.resultTeam1 = sResultTeam1;
		this.resultTeam2 = sResultTeam2;
		this.tmName = tmName;
		this.dateTxt = dateTxt;
		this.index = index;
		this.check = true;
	}
}
