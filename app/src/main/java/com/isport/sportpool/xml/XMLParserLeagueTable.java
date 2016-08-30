package com.isport.sportpool.xml;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.isport.sportpool.data.DataElementLeagueTable;

public class XMLParserLeagueTable extends DefaultHandler
{
	public String tmName = "";
	public String contestURLImages = "";
	
	private String id="";
	private String place = "";
	private String teamName1 = "";
	private String totalPlay = "";
	private String totalWon = "";
	private String totalDraws = "";
	private String totalLost = "";
	private String totalScore = "";
	private String totalConcede = "";
	private String homePlay = "";
	private String homeWon = "";
	private String homeDraws = "";
	private String homeLost = "";
	private String homeScore = "";
	private String homeConcede = "";
	private String awayPlay = "";
	private String awayWon = "";
	private String awayDraws = "";
	private String awayLost = "";
	private String awayScore = "";
	private String awayConcede = "";
	private String totalDiff = "";
	private String totalPoint = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	private boolean bRemark = false;
	
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";
	public String remark = "";
	
	public Vector<DataElementLeagueTable> vData = null;
	
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
			vData = new Vector<DataElementLeagueTable>();
			date = atts.getValue("date");
			header = atts.getValue("header");
		} else if(localName.equals("LeagueTable")) {
			tmName = atts.getValue("tmName");
			contestURLImages = atts.getValue("contestURLImages");
		} else if(localName.equals("leaguetable_team")) {
			id = atts.getValue("teamid");
			place = atts.getValue("place");
			teamName1 = atts.getValue("teamName1");
			totalPlay = atts.getValue("total_play");
			totalWon = atts.getValue("total_won");
			totalDraws = atts.getValue("total_draws");
			totalLost = atts.getValue("total_lost");
			totalScore = atts.getValue("total_score");
			totalConcede = atts.getValue("total_concede");
			homePlay = atts.getValue("home_play");
			homeWon = atts.getValue("home_won");
			homeDraws = atts.getValue("home_draws");
			homeLost = atts.getValue("home_lost");
			homeScore = atts.getValue("home_score");
			homeConcede = atts.getValue("home_concede");
			awayPlay = atts.getValue("away_play");
			awayWon = atts.getValue("away_won");
			awayDraws = atts.getValue("away_draws");
			awayLost = atts.getValue("away_lost");
			awayScore = atts.getValue("away_score");
			awayConcede = atts.getValue("away_concede");
			totalDiff = atts.getValue("total_diff");
			totalPoint = atts.getValue("total_point");
		} else if(localName.equals("status")) {
			bStatus = true;
		} else if(localName.equals("message")) {
			bMessage = true;
		} else if(localName.equals("remark")) {
			bRemark = true;
		}
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {
			
		} else if (localName.equals("LeagueTable")) {
			
		} else if (localName.equals("leaguetable_team")) {
			addList();
		} else if (localName.equals("status")) {
			bStatus = false;
		} else if (localName.equals("message")) {
			bMessage = false;
		} else if (localName.equals("remark")) {
			bRemark = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		} else if (bRemark) {
			remark += new String(ch, start, length);
		}
	}
		
	private void addList()
	{
		vData.add(new DataElementLeagueTable(id,teamName1, place, totalPlay, totalWon, totalDraws, totalLost, totalScore, totalConcede, homePlay, homeWon, homeDraws, homeLost, homeScore, homeConcede, awayPlay, awayWon, awayDraws, awayLost, awayScore, awayConcede, totalDiff, totalPoint));
		
		id="";
		place = "";
		teamName1 = "";
		totalPlay = "";
		totalWon = "";
		totalDraws = "";
		totalLost = "";
		totalScore = "";
		totalConcede = "";
		homePlay = "";
		homeWon = "";
		homeDraws = "";
		homeLost = "";
		homeScore = "";
		homeConcede = "";
		awayPlay = "";
		awayWon = "";
		awayLost = "";
		awayScore = "";
		awayConcede = "";
		totalDiff = "";
		totalPoint = "";
	}
}