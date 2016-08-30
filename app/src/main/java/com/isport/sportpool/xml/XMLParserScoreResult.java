package com.isport.sportpool.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.isport.sportpool.data.DataElementGroupScoreResult;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementScore;

public class XMLParserScoreResult extends DefaultHandler
{
	private String sTmSystem = "";
	private String sTmName = "";
	private String sContestURLImages = "";
	private String sContestGroupId = "";
	private String sContestGroupName = "";
	
	private String sMatchDate = "";
	private String sMschId = "";
	private String sMatchId = "";
	private String sScoreAwayHT = "";
	private String sScoreHomeHT = "";
	private String sScoreAway = "";
	private String sScoreHome = "";
	private String sCurentPeriod = "";
	private String sMinutes = "";
	private String sStatus = "";
	private String sTeamName2 = "";
	private String sTeamName1 = "";
	private String sTeamCode2 = "";
	private String sTeamCode1 = "";
	
	private boolean isDetail = false;
	
	public ArrayList<DataElementGroupScoreResult> mGroupData = null;
	public DataElementGroupScoreResult mData = null;
	
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String textHeader = "";
	public String textDate = "";
	public String radioURL = "";
	private int count = 0;
	
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
			textDate = atts.getValue("date");
			textHeader = atts.getValue("header");
			radioURL = atts.getValue("url");
			
			mGroupData = new ArrayList<DataElementGroupScoreResult>();
			//hData = new Hashtable<String,DataElementScore>();
			//vData = new Vector<DataElementLeague>();
		} else if(localName.equals("League")) {
			sTmSystem = atts.getValue("tmSystem");
			sTmName = atts.getValue("tmName");
			sContestURLImages = atts.getValue("contestURLImages");
			sContestGroupId = atts.getValue("contestGroupId");
			sContestGroupName = atts.getValue("contestGroupName");
			
			addLeague();
		} else if(localName.equals("Score")) {
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
			sTeamName2 = atts.getValue("teamName2");
			sTeamName1 = atts.getValue("teamName1");
			sTeamCode2 = atts.getValue("teamCode2");
			sTeamCode1 = atts.getValue("teamCode1");
			
			if(atts.getValue("isDetail").equals("true"))
			{
				isDetail = true;
			}
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
			
		} else if (localName.equals("League")) {
			sTmName = "";
			sContestURLImages = "";
			sContestGroupId = "";
			sContestGroupName = "";
			sTmSystem = "";
		} else if (localName.equals("Score")) {
			addList();
		} else if (localName.equals("status")) {
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
	
	private void addLeague()
	{
		
		
		mData = new DataElementGroupScoreResult(new DataElementLeague( sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));

		if( mData != null ) mGroupData.add(mData);
		
		count = 0;
	}
	
	private void addList()
	{
		
		mData.GroupItemCollection.add(new DataElementScore(sContestGroupId, sMatchDate, sMschId, sMatchId, sScoreAwayHT
				, sScoreHomeHT, sScoreAway, sScoreHome, sCurentPeriod, sMinutes
				, sStatus, sTeamName2, sTeamName1, sTeamCode2, sTeamCode1, isDetail, (count%2)));
		
		count++;
		
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
		sTeamName2 = "";
		sTeamName1 = "";
		sTeamCode2 = "";
		sTeamCode1 = "";
		isDetail = false;
	}
}