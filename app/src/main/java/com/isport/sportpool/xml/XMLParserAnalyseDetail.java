package com.isport.sportpool.xml;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.isport.sportpool.data.DataElementAnalyseDetail;
import com.isport.sportpool.data.DataElementAnalyseHeadToHead;
import com.isport.sportpool.data.DataElementAnalyseHeadToHeadDetail;
import com.isport.sportpool.data.DataElementLeagueStatic;
import com.isport.sportpool.data.DataElementLeagueTable;

public class XMLParserAnalyseDetail extends DefaultHandler
{
	private boolean bSport = false;
	private boolean bAnalyseDetail = false;
	private boolean bAnalyseHeadtohead = false;
	private boolean bHeadToHeadTeam = false;
	private boolean bLeagueTable = false;
	private boolean bLeagueTeam = false;
	private boolean bLeagueStatic = false;
	private boolean bLeagueStaticTeam = false;
	private boolean bStatus = false;
	private boolean bMessage = false;
	
	///////////////////////// analyse_detail //////////////////////////
	private String sAnalyse = "";
	private String sHandicap = "";
	private String sResult = "";
	private String sPoolAVG = "";
	///////////////////////// andlyse headtohead detail ///////////////////
	private String sTeam_id1 = "";
	private String sTeam_id2 = "";
	private String sTeam_name1 = "";
	private String sTeam_name2 = "";
	private String sResult_team1 = "";
	private String sResult_team2 = "";
	///////////////////////// analyse_headtohead //////////////////////////
	private String sTotalGa = "";
	private String sTotalGs = "";
	private String sTotalLoss = "";
	private String sTotalDraw = "";
	private String sTotalWin = "";
	private String sTotalMatch = "";
	///////////////////////// LeagueTable ////////////////////////////////
	private String sPlace = "";
	private String sTablePlay = "";
	private String sTableWon = "";
	private String sTableDraws = "";
	private String sTableLost = "";
	private String sTableScore = "";
	private String sTableConcede = "";
	private String sHomePlay = "";
	private String sHomeWon = "";
	private String sHomeDraws = "";
	private String sHomeLost = "";
	private String sHomeScore = "";
	private String sHomeConcede = "";
	private String sAwayPlay = "";
	private String sAwayWon = "";
	private String sAwayDraws = "";
	private String sAwayLost = "";
	private String sAwayScore = "";
	private String sAwayConcede = "";
	private String sTableDiff = "";
	private String sTablePoint = "";
	
	///////////////////////// LeagueStatic ////////////////////////////
	private String sStaticTeam = "";
	private String sResultTeam1 = "";
	private String sResultTeam2 = "";
	private String stmName = "";
	private String sdateTxt = "";
	
	//////////////////////// use all ///////////////////////////////////
	private String sTeamName2 = "";
	private String sTeamName1 = "";
	
	//////////////////////// status ////////////////////////////////
	public String status = "";
	public String message = "";
	
	///////////////////////// data element ////////////////////////////
	public DataElementAnalyseDetail dataElementAnalyseDetail = null;
	public Vector<DataElementAnalyseHeadToHead> vDataEleAnalyseHeadToHead = null;
	public Vector<DataElementAnalyseHeadToHeadDetail> vDataEleHeadDetail = null;
	public Vector<DataElementLeagueTable> vDataEleLeagueTable = null;
	public Vector<DataElementLeagueStatic> vDataEleLeagueStaticTeam1 = null;
	public Vector<DataElementLeagueStatic> vDataEleLeagueStaticTeam2 = null;
	
	private String team1 = "";
	private String team2 = "";
	private int teamCount1=0;
	private int teamCount2=0;
	
	@Override
	public void startDocument() throws SAXException
	{}
	
	@Override
	public void endDocument() throws SAXException
	{}
	
	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException 
	{
		if (localName.equals("SportApp"))
		{
			bSport = true;
			vDataEleAnalyseHeadToHead = new Vector<DataElementAnalyseHeadToHead>();
			vDataEleLeagueTable = new Vector<DataElementLeagueTable>();
			vDataEleLeagueStaticTeam1 = new Vector<DataElementLeagueStatic>();
			vDataEleLeagueStaticTeam2 = new Vector<DataElementLeagueStatic>();
			vDataEleHeadDetail = new Vector<DataElementAnalyseHeadToHeadDetail>();
		}
		else if(localName.equals("analyse_detail"))
		{
			bAnalyseDetail = true;
			sResult = atts.getValue("result");
			sHandicap = atts.getValue("handicap");
			sAnalyse = atts.getValue("analyse");
			sPoolAVG = atts.getValue("poolavg");
		} else if(localName.equals("analyse_headtohead"))
		{
			bAnalyseHeadtohead = true;
		}
		else if(localName.equals("detail"))
		{
			sTeam_id1 = atts.getValue("team_id1");
			sTeam_id2 = atts.getValue("team_id2");
			sTeam_name1 = atts.getValue("team_name1");
			sTeam_name2 = atts.getValue("team_name2");
			sResult_team1 = atts.getValue("result_team1");
			sResult_team2 = atts.getValue("result_team2");
		}
		else if(localName.toLowerCase().equals("headtohead_team"))
		{
			bHeadToHeadTeam = true;
			sTotalGa = atts.getValue("total_ga");
			sTotalGs = atts.getValue("total_gs");
			sTotalLoss = atts.getValue("total_loss");
			sTotalDraw = atts.getValue("total_draw");
			sTotalWin = atts.getValue("total_win");
			sTotalMatch = atts.getValue("total_match");
			sTeamName1 = atts.getValue("teamName1");
			sTeamName2 = atts.getValue("teamName2");
		}
		else if(localName.equals("LeagueTable"))
		{
			bLeagueTable = true;
		}
		else if(localName.equals("leaguetable_team"))
		{
			bLeagueTeam = true;
			
			sTeamName1 = atts.getValue("teamName1");
			sPlace = atts.getValue("place");
			sTablePlay = atts.getValue("total_play");
			sTableWon = atts.getValue("total_won");
			sTableDraws = atts.getValue("total_draws");
			sTableLost = atts.getValue("total_lost");
			sTableScore = atts.getValue("total_score");
			sTableConcede = atts.getValue("total_concede");
			sHomePlay = atts.getValue("home_play");
			sHomeWon = atts.getValue("home_won");
			sHomeDraws = atts.getValue("home_draws");
			sHomeLost = atts.getValue("home_lost");
			sHomeScore = atts.getValue("home_score");
			sHomeConcede = atts.getValue("home_concede");
			sAwayPlay = atts.getValue("away_play");
			sAwayWon = atts.getValue("away_won");
			sAwayDraws = atts.getValue("away_draws");
			sAwayLost = atts.getValue("away_lost");
			sAwayScore = atts.getValue("away_score");
			sAwayConcede = atts.getValue("away_concede");
			sTableDiff = atts.getValue("total_diff");
			sTablePoint = atts.getValue("total_point");
		}
		else if(localName.equals("LeagueStatic"))
		{
			bLeagueStatic = true;
		}
		else if(localName.equals("leaguestatic_team"))
		{
			bLeagueStaticTeam = true;
			sTeamName1 = atts.getValue("teamName1");
			sTeamName2 = atts.getValue("teamName2");
			sStaticTeam = atts.getValue("static_team");
			sResultTeam2 = atts.getValue("result_team2");
			sResultTeam1 = atts.getValue("result_team1");
			stmName = atts.getValue("tm_name");
			sdateTxt = atts.getValue("date");
		}
		else if(localName.equals("status"))
		{
			bStatus = true;
		}
		else if(localName.equals("message"))
		{
			bMessage = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			bSport = false;
		} else if(localName.equals("analyse_detail")) {
			bAnalyseDetail = false;
			addAnalyseDetail();
		} else if(localName.equals("analyse_headtohead")) {
			bAnalyseHeadtohead = false;
		} else if(localName.equals("detail")) 
		{
			addHeadtoHeadDetail();
		}
		else if(localName.toLowerCase().equals("headtohead_team")) {
			bHeadToHeadTeam = false;
			addAnalyseHeadToHead();
		} else if(localName.equals("LeagueTable")) {
			bLeagueTable = false;
		} else if(localName.equals("leaguetable_team")) {
			bLeagueTeam = false;
			addLeagueTable();
		} else if(localName.equals("LeagueStatic")) {
			bLeagueStatic = false;
		} else if(localName.equals("leaguestatic_team")) {
			bLeagueStaticTeam = false;
			addLeagueStatic();
		} else if(localName.equals("status")) {
			bStatus = false;
		} else if(localName.equals("message")) {
			bMessage = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		}
	}
	
	private void addAnalyseDetail()
	{
		dataElementAnalyseDetail = new DataElementAnalyseDetail(sAnalyse, sHandicap, sResult,sPoolAVG);

		sResult = "";
		sHandicap = "";
		sAnalyse = "";
		sPoolAVG = "";
	}
	private void addHeadtoHeadDetail()
	{
		vDataEleHeadDetail.add(new DataElementAnalyseHeadToHeadDetail(sTeam_id1, sTeam_id2, sTeam_name1, sTeam_name2, sResult_team1, sResult_team2));
		
		sTeam_id1 = "";
		sTeam_id2 = "";
		sTeam_name1 = "";
		sTeam_name2 = "";
		sResult_team1 = "";
		sResult_team2 = "";
		
	}
	private void addAnalyseHeadToHead()
	{
		if(sTeamName1 != null && sTeamName1.length() > 0)
		{

			vDataEleAnalyseHeadToHead.add(new DataElementAnalyseHeadToHead(sTotalGa, sTotalGs, sTotalLoss, sTotalDraw, sTotalWin, sTotalMatch, sTeamName1));
		}
		else
		{
			vDataEleAnalyseHeadToHead.add(new DataElementAnalyseHeadToHead(sTotalGa, sTotalGs, sTotalLoss, sTotalDraw, sTotalWin, sTotalMatch, sTeamName2));			
		}
		
		sTotalGa = "";
		sTotalGs = "";
		sTotalLoss = "";
		sTotalDraw = "";
		sTotalWin = "";
		sTotalMatch = "";
		sTeamName1 = "";
		sTeamName2 = "";
	}
	
	private void addLeagueTable()
	{
		vDataEleLeagueTable.add(new DataElementLeagueTable("",sTeamName1, sPlace, sTablePlay, sTableWon
				, sTableDraws, sTableLost, sTableScore, sTableConcede
				, sHomePlay, sHomeWon, sHomeDraws, sHomeLost
				, sHomeScore, sHomeConcede, sAwayPlay, sAwayWon
				, sAwayDraws, sAwayLost, sAwayScore, sAwayConcede
				, sTableDiff, sTablePoint));
		
		sTeamName1 = "";
		sPlace = "";
		sTablePlay = "";
		sTableWon = "";
		sTableDraws = "";
		sTableLost = "";
		sTableScore = "";
		sTableConcede = "";
		sHomePlay = "";
		sHomeWon = "";
		sHomeDraws = "";
		sHomeLost = "";
		sHomeScore = "";
		sHomeConcede = "";
		sAwayPlay = "";
		sAwayWon = "";
		sAwayDraws = "";
		sAwayLost = "";
		sAwayScore = "";
		sAwayConcede = "";
		sTableDiff = "";
		sTablePoint = "";
	}
	
	private void addLeagueStatic()
	{
		if(team1.length() < 1)
		{
			team1 = sStaticTeam;
		}
		
		if(team1.equals(sStaticTeam))
		{
			teamCount1++;
			//PrintLog.print("XMLParSportpoolAnalyse method addLeagueStatic", "team 1 add : team2 = "+team2);
			vDataEleLeagueStaticTeam1.add(new DataElementLeagueStatic(sTeamName1, sTeamName2, sStaticTeam, sResultTeam2, sResultTeam1,stmName,sdateTxt,teamCount1));
		}
		else
		{
			teamCount2++;
			vDataEleLeagueStaticTeam2.add(new DataElementLeagueStatic(sTeamName1, sTeamName2, sStaticTeam, sResultTeam2, sResultTeam1,stmName,sdateTxt,teamCount2));
			//PrintLog.print("XMLParSportpoolAnalyse method addLeagueStatic", "team 2 add : team2 = "+team2);
		}
		
		sTeamName1 = "";
		sTeamName2 = "";
		sStaticTeam = "";
		sResultTeam2 = "";
		sResultTeam1 = "";
		stmName = "";
		sdateTxt = "";
	}
}
