package com.isport.sportpool.data;

public class DataElementLeagueTable
{
	public String id ="";
	public String name = "";
	public String place = "";
	public String tPlay = "";
	public String tWon = "";
	public String tDraws = "";
	public String tLost = "";
	public String tScore = "";
	public String tConcede = "";
	public String hPlay = "";
	public String hWon = "";
	public String hDraws = "";
	public String hLost = "";
	public String hScore = "";
	public String hConcede = "";
	public String aPlay = "";
	public String aWon = "";
	public String aDraws = "";
	public String aLost = "";
	public String aScore = "";
	public String aConcede = "";
	public String tDiff = "";
	public String tPoint = "";
	
	public DataElementLeagueTable(String id,String sTeamName1, String sPlace, String sTablePlay,
			String sTableWon, String sTableDraws, String sTableLost, String sTableScore
			, String sTableConcede,String sHomePlay, String sHomeWon, String sHomeDraws
			, String sHomeLost, String sHomeScore,String sHomeConcede, String sAwayPlay
			, String sAwayWon, String sAwayDraws, String sAwayLost,String sAwayScore
			, String sAwayConcede, String sTableDiff, String sTablePoint)
	{
		this.id = id;
		name = sTeamName1;
		place = sPlace;
		tPlay = sTablePlay;
		tWon = sTableWon;
		tDraws = sTableDraws;
		tLost = sTableLost;
		tScore = sTableScore;
		tConcede = sTableConcede;
		hPlay = sHomePlay;
		hWon = sHomeWon;
		hDraws = sHomeDraws;
		hLost = sHomeLost;
		hScore = sHomeScore;
		hConcede = sHomeConcede;
		aPlay = sAwayPlay;
		aWon = sAwayWon;
		aDraws = sAwayDraws;
		aLost = sAwayLost;
		aScore = sAwayScore;
		aConcede = sAwayConcede;
		tDiff = sTableDiff;
		tPoint = sTablePoint;
	}
}
