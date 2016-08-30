package com.isport.sportpool.data;

import java.io.Serializable;

public class DataElementScore implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public String groupId = "";
	public String matchDate = "";
	public String mschId = "";
	public String matchId = "";
	public String scoreAwayHT = "";
	public String scoreHomeHT = "";
	public String scoreHome = "";
	public String scoreAway = "";
	public String curentPeriod = "";
	public String minutes = "";
	public String status = "";
	public String teamName2 = "";
	public String teamName1 = "";
	public String teamCode2 = "";
	public String teamCode1 = "";
	public int index = 0;
	public boolean isDetail = false;
	
	public DataElementScore(String sContestGroupId, String sMatchDate, String sMschId, String sMatchId, String sScoreAwayHT, String sScoreHomeHT,
			String sScoreAway, String sScoreHome, String sCurentPeriod, String sMinutes, String sStatus, String sTeamName2,
			String sTeamName1, String sTeamCode2, String sTeamCode1, boolean isDetail, int index)
	{
		groupId = sContestGroupId;
		matchDate = sMatchDate;
		mschId = sMschId;
		matchId = sMatchId;
		scoreAwayHT = sScoreAwayHT;
		scoreHomeHT = sScoreHomeHT;
		scoreAway = sScoreAway;
		scoreHome = sScoreHome;
		curentPeriod = sCurentPeriod;
		if(sMinutes.toUpperCase().equals("FINISHED"))
		{
			sMinutes = "FT";
		}
		minutes = sMinutes;
		if(sStatus.toUpperCase().equals("FINISHED"))
		{
			sStatus = "FT";
		}
		status = sStatus;
		teamName2 = sTeamName2;
		teamName1 = sTeamName1;
		teamCode2 = sTeamCode2;
		teamCode1 = sTeamCode1;
		this.index = index;
		this.isDetail = isDetail;
	}
}
