package com.isport.sportpool.data;

public class DataElementAnalyseHeadToHeadDetail
{
	public String team_id1 = "";
	public String team_id2 = "";
	public String team_name1 = "";
	public String team_name2 = "";
	public String result_team1 = "";
	public String result_team2 = "";
	public boolean check = false;
	
	public DataElementAnalyseHeadToHeadDetail(String team_id1,String team_id2,String team_name1,String team_name2
			,String result_team1,String result_team2)
	{
		this.team_id1 = team_id1;
		this.team_id2 = team_id2;
		this.team_name1 = team_name1;
		this.team_name2 = team_name2;
		this.result_team1 = result_team1;
		this.result_team2 = result_team2;
		
	}
}
