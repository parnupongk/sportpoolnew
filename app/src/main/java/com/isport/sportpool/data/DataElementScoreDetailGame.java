package com.isport.sportpool.data;

public class DataElementScoreDetailGame
{
	public String minute = "";
	public String evenType = "";
	public String teamId = "";
	public String teamName = "";
	public String playerName = "";
	
	public DataElementScoreDetailGame(String minute, String evenType, String teamId , String teamName, String playerName)
	{
		this.minute = minute;
		this.evenType = evenType;
		this.teamId = teamId;
		this.teamName = teamName;
		this.playerName = playerName;
	}
}
