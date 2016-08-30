package com.isport.sportpool.data;

public class DataElementScoreDetailScore
{
	public String shareURL = "";
	public String likeURL = "";
	public String teamName2 = "";
	public String teamName1 = "";
	public String teamCode2 = "";
	public String teamCode1 = "";
	public String tmName = "";
	public String scoreAway = "";
	public String scoreHome = "";
	public String scoreType = "";
	public String contestURLImages = "";
	public String contestName = "";
	public String contestGroupId = "";
	
	public DataElementScoreDetailScore(String shareURL, String likeURL, String teamName2,
			String teamName1, String teamCode2, String teamCode1, String tmName, String scoreAway,
			String scoreHome, String scoreType, String contestURLImages, String contestName, String contestGroupId)
	{
		this.shareURL = shareURL;
		this.likeURL = likeURL;
		this.teamCode1 = teamCode1;
		this.teamCode2 = teamCode2;
		this.teamName1 = teamName1;
		this.teamName2 = teamName2;
		this.tmName = tmName;
		this.scoreAway = scoreAway;
		this.scoreHome = scoreHome;
		if(scoreType.toUpperCase().equals("FINISHED"))
		{
			scoreType = "FT";
		}
		this.scoreType = scoreType;
		this.contestURLImages = contestURLImages;
		this.contestName = contestName;
		this.contestGroupId = contestGroupId;
	}
}
