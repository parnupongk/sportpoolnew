package com.isport.sportpool.xml;

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.isport.sportpool.data.DataElementScoreDetailGame;
import com.isport.sportpool.data.DataElementScoreDetailScore;

public class XMLParserScoreDetail extends DefaultHandler
{
	private String shareURL = "";
	private String likeURL = "";
	private String teamName2 = "";
	private String teamName1 = "";
	private String teamCode2 = "";
	private String teamCode1 = "";
	private String tmName = "";
	private String scoreAway = "";
	private String scoreHome = "";
	private String scoreType = "";
	private String contestURLImages = "";
	private String contestName = "";
	private String contestGroupId = "";
	
	private String minute = "";
	private String evenType = "";
	private String teamId = "";
	private String teamName = "";
	private String playerName = "";
	
	private String id = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	
	public DataElementScoreDetailScore dataLiveScore = null;
	public Vector<DataElementScoreDetailGame> vGame = null;
	public Vector<String []> vNameTeam1 = null;
	public Vector<String []> vNameTeam2 = null;
	
	private String [] nameTeam = null;
	
	@Override
	public void startDocument() throws SAXException
	{}
	
	@Override
	public void endDocument() throws SAXException
	{}
	
	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts) throws SAXException 
	{
		if (localName.equals("SportApp")) {
			vGame = new Vector<DataElementScoreDetailGame>();
			vNameTeam1 = new Vector<String[]>();
			vNameTeam2 = new Vector<String[]>();
		} else if(localName.equals("Score")) {
			shareURL = atts.getValue("shareURL");
			likeURL = atts.getValue("likeURL");
			teamName2 = atts.getValue("teamName2");
			teamName1 = atts.getValue("teamName1");
			teamCode2 = atts.getValue("teamCode2");
			teamCode1 = atts.getValue("teamCode1");
			tmName = atts.getValue("tmName");
			scoreAway = atts.getValue("scoreAway");
			scoreHome = atts.getValue("scoreHome");
			scoreType = atts.getValue("scoreType");
			contestURLImages = atts.getValue("contestURLImages");
			contestName = atts.getValue("contestName");
			contestGroupId = atts.getValue("contestGroupId");
		} else if(localName.equals("ScoreDetail")) {
			minute = atts.getValue("minute");
			evenType = atts.getValue("evenType");
			teamId = atts.getValue("teamId");
			teamName = atts.getValue("teamName");
			playerName = atts.getValue("playerName");
		} else if(localName.equals("MatchPlayer")) {
			
		} else if(localName.equals("team")) {
			id = atts.getValue("teamId");
		} else if(localName.equals("player")) {
			nameTeam = new String [2];
			nameTeam[0] = atts.getValue("position");
			nameTeam[1] = atts.getValue("name");
		} else if(localName.equals("status")) {
			bStatus = true;
		} else if(localName.equals("message")) {
			bMessage = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			
		} else if (localName.equals("Score")) {
			addScoreResult();
		} else if(localName.equals("ScoreDetail")) {
			addScoreDetail();
		} else if (localName.equals("MatchPlayer")) {
			
		} else if (localName.equals("team")) {
			id = "";
		} else if(localName.equals("player")) {
			addPlayer();
		}  else if (localName.equals("status")) {
			bStatus = false;
		} else if (localName.equals("message")) {
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
	
	private void addScoreResult()
	{
		dataLiveScore = new DataElementScoreDetailScore(shareURL, likeURL, teamName2, teamName1, teamCode2, teamCode1, tmName, scoreAway, scoreHome, scoreType, contestURLImages, contestName, contestGroupId);
	}
	
	private void addScoreDetail()
	{
		vGame.add(new DataElementScoreDetailGame(minute, evenType, teamId, teamName, playerName));
		
		minute = "";
		evenType = "";
		teamId = "";
		teamName = "";
		playerName = "";
	}
	
	private void addPlayer()
	{
		//PrintLog.print("XMLParserLiveScoreDetail method addPlayer", teamCode1+" : "+teamCode2+" : "+id);
		//PrintLog.print("XMLParserLiveScoreDetail method addPlayer", "name:"+nameTeam[1]+" , position:"+nameTeam[0]);
		
		if(teamCode1.equals(id))
		{
			vNameTeam1.add(nameTeam);
		}
		else
		{
			vNameTeam2.add(nameTeam);
		}
	}
}
