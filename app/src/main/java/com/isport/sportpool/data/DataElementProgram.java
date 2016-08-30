package com.isport.sportpool.data;

import java.io.Serializable;

public class DataElementProgram implements Serializable
{
	/**
	 * 
	 */
	public String mschId = "";
	public String matchId = "";
	public String teamCode1 = "";
	public String teamCode2 = "";
	public String name1 = "";
	public String name2 = "";
	public String liveChannel = "";
	public String matchDate = "";
	public String matchTime = "";
	public boolean isDetail = false;
	public String analyse = "";
	public String trend = "";
	public String groupId = "";
	public float precentTeam1 = 0;
	public float precentTeam2 = 0;
	public float starLike = 0;
	public int column = 0;
	
	public DataElementProgram(String sContestGroupId, String sMschId, String sMatchId, 
			String sTeamCode1, String sTeamCode2, String sName1
			, String sName2,String sLiveChannel, String sMatchDate
			, String sMatchTime, String sIsDetail, int column
			,String analyse,String Trend,float precentTeam1 
			,float precentTeam2 ,float starLike)
	{
		this.groupId = sContestGroupId;
		this.mschId = sMschId;
		this.matchId = sMatchId;
		this.teamCode1 = sTeamCode1;
		this.teamCode2 = sTeamCode2;
		this.name1 = sName1;
		this.name2 = sName2;
		this.liveChannel = sLiveChannel;
		this.matchDate = sMatchDate;
		this.matchTime = sMatchTime;
		this.analyse = analyse;
		this.column = column;
		this.trend = Trend;
		this.precentTeam1 = precentTeam1;
		this.precentTeam2 = precentTeam2;
		this.starLike = starLike;
		
		if(sIsDetail.equals("false"))
		{
			isDetail = false;
		}
		else
		{
			isDetail = true;
		}
		
	}
}
