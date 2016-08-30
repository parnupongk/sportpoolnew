package com.isport.sportpool.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataElementGroupScore;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementProgram;
import com.isport.sportpool.data.DataElementScore;

public class XMLParserMatchProgramScoreByTeam extends DefaultHandler
{
	private String sTmName = "";
	private String sContestURLImages = "";
	private String sContestGroupId = "";
	private String sContestGroupName = "";
	private String sTmSystem = "";
	
	private String sScoreAwayHT = "";
	private String sScoreHomeHT = "";
	private String sScoreAway = "";
	private String sScoreHome = "";
	private String sCurentPeriod = "";
	private String sMinutes = "";
	private String sStatus = "";
	private boolean isDetail = false;
	
	
	private String sIsDetail = "";
	private String sMatchTime = "";
	private String sMatchDate = "";
	private String sLiveChannel = "";
	private String sName1 = "";
	private String sName2 = "";
	private String sTeamCode1 = "";
	private String sTeamCode2 = "";
	private String sMschId = "";
	private String sMatchId = "";
	private String analyse = "";
	private String trend = "";
	private float precentTeam1 = 0;
	private float precentTeam2 = 0;
	private float starLike = 0;
	private boolean bMessage = false;
	private boolean bStatus = false;
	private boolean bMessageProg = false;
	private boolean bStatusProg = false;
	
	public String status = "";
	public String message = "";
	public String statusProg = "";
	public String messageProg = "";
	public String textHeader = "";
	public String textDate = "";
	
	public ArrayList<DataElementGroupProgram> groupProgram = null;
	public DataElementGroupProgram program = null;
	
	public ArrayList<DataElementGroupScore> groupScore = null;
	public DataElementGroupScore score = null;
	
	public int matchCount = 0;

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

			groupProgram = new ArrayList<DataElementGroupProgram>();
			groupScore = new ArrayList<DataElementGroupScore>();
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
		}
		else if(localName.equals("League_Program") || localName.equals("League"))
		{
			sTmName = atts.getValue("tmName");
			sContestURLImages = atts.getValue("contestURLImages");
			sContestGroupId = atts.getValue("contestGroupId");
			sContestGroupName = atts.getValue("contestGroupName");
			sTmSystem = atts.getValue("tmSystem");
			
			//addLeague();
			
		}else if(localName.equals("Score"))
		{
			// Score
			sMatchDate = atts.getValue("matchDate");
			sMschId = atts.getValue("mschId");
			sMatchId = atts.getValue("matchId");
			sScoreAwayHT = atts.getValue("score_away_ht");
			sScoreHomeHT = atts.getValue("score_home_ht");
			sScoreAway = atts.getValue("score_away");
			sScoreHome = atts.getValue("score_home");
			sCurentPeriod = atts.getValue("curent_period");
			sMinutes = atts.getValue("minutes");
			sStatus = atts.getValue("status");
			sName2 = atts.getValue("teamName2");
			sName1 = atts.getValue("teamName1");
			sTeamCode2 = atts.getValue("teamCode2");
			sTeamCode1 = atts.getValue("teamCode1");
			
			if(atts.getValue("isDetail").equals("true"))
			{
				isDetail = true;
			}
		}
		else if(localName.equals("Match_Program"))
		{
			// Program
			sMschId = atts.getValue("mschId");
			sMatchId = atts.getValue("matchId");
			sTeamCode1 = atts.getValue("teamCode1");
			sTeamCode2 = atts.getValue("teamCode2");
			sName1 = atts.getValue("teamName1");
			sName2 = atts.getValue("teamName2");
			sLiveChannel = atts.getValue("liveChannel");
			sMatchDate = atts.getValue("matchDate");
			sMatchTime = atts.getValue("matchTime");
			sIsDetail = atts.getValue("isDetail");
			analyse = atts.getValue("analyse");
			trend = atts.getValue("trend");
			this.precentTeam1 = Float.parseFloat( atts.getValue("precentteam1"));
			this.precentTeam2 = Float.parseFloat(atts.getValue("precentteam2"));
			this.starLike = Float.parseFloat(atts.getValue("starlike"));
		}
		else if(localName.equals("status"))
		{
			bStatus = true;
		}
		else if(localName.equals("message"))
		{
			bMessage = true;
		}
		else if(localName.equals("status_program"))
		{
			bStatusProg = true;
		}
		else if(localName.equals("message_program"))
		{
			bMessageProg = true;
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {

		}else if (localName.equals("Score")) {
			
			addScore();
		} else if (localName.equals("Match_Program")) {

			
			addProgram();
		} else if (localName.equals("League_Program")) {
			sTmName = "";
			sContestURLImages = "";
			sContestGroupId = "";
			sContestGroupName = "";
			sTmSystem = "";
		} else if(localName.equals("status")) {
			bStatus = false;
		} else if(localName.equals("message")) {
			bMessage = false;
		} else if(localName.equals("status_program")) {
			bStatusProg = false;
		} else if(localName.equals("message_program")) {
			bMessageProg = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		} else if (bStatusProg) {
			statusProg += new String(ch, start, length);
		} else if (bMessageProg) {
			messageProg += new String(ch, start, length);
		}
	}
	
	private void addScore()
	{
		
		score = new DataElementGroupScore(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
		
		score.GroupItemCollection.add(new DataElementScore(sContestGroupId,sMatchDate,sMschId,sMatchId,sScoreAwayHT,sScoreHomeHT
				,sScoreAway,sScoreHome,sCurentPeriod,sMinutes,sStatus,sName2,sName1,sTeamCode2,sTeamCode1,isDetail,0));
		
		groupScore.add(score);
		
		sMatchDate = "";
		sMschId = "";
		sMatchId = "";
		sScoreAwayHT = "";
		sScoreHomeHT = "";
		sScoreAway = "";
		sScoreHome = "";
		sCurentPeriod = "";
		sMinutes = "";
		sStatus = "";
		sName2 = "";
		sName1 = "";
		sTeamCode2 = "";
		sTeamCode1 = "";
		
	}
	
	private void addProgram()
	{
		matchCount++;
		
		// add league
		program = new DataElementGroupProgram(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
		
		// add match
		program.GroupItemCollection.add(new DataElementProgram(sContestGroupId, sMschId, sMatchId, sTeamCode1, sTeamCode2
				, sName1, sName2, sLiveChannel, sMatchDate, sMatchTime, sIsDetail, matchCount%2,this.analyse,trend,precentTeam1,precentTeam2,starLike));
		
		// add to group
		groupProgram.add(program);
		

		sMschId = "";
		sMatchId = "";
		sTeamCode1 = "";
		sTeamCode2 = "";
		sName1= "";
		sName2 = "";
		sLiveChannel = "";
		sMatchDate = "";
		sMatchTime = "";
		sIsDetail = "";
		analyse = "";
		trend = "";
	}
}
