package com.isport.sportpool.xml;

import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.isport.sportpool.data.DataElementGroupProgram;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementProgram;

public class XMLParserMatchProgram extends DefaultHandler
{
	private String sTmName = "";
	private String sContestURLImages = "";
	private String sContestGroupId = "";
	private String sContestGroupName = "";
	private String sTmSystem = "";
	
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
	
	public String status = "";
	public String message = "";
	public String textHeader = "";
	public String textDate = "";
	
	public ArrayList<DataElementGroupProgram> groupProgram = null;
	public DataElementGroupProgram program = null;
	
	public int matchCount = 0;

    public void get(InputStream inputStream) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser mSaxParser = factory.newSAXParser();
            XMLReader mXmlReader = mSaxParser.getXMLReader();
            mXmlReader.setContentHandler(this);
            mXmlReader.parse(new InputSource(inputStream));
        } catch(Exception e) {
            // Exceptions can be handled for different types
            // But, this is about XML Parsing not about Exception Handling
            Log.e("Sportpool", "Exception: " + e.getMessage());
        }
    }
	
	
    public void startDocument() throws SAXException {
        System.out.println("start document   : ");
    }

    public void endDocument() throws SAXException {
        System.out.println("end document     : ");
    }
    
	@Override
	public void startElement(String namespaceURI, String localName,String qName, Attributes atts)  
	{
		try
		{
			if (localName.equals("SportApp"))
			{
	
				groupProgram = new ArrayList<DataElementGroupProgram>();
				
				textHeader = atts.getValue("header");
				textDate = atts.getValue("date");
			}
			else if(localName.equals("League"))
			{
				sTmName = atts.getValue("tmName");
				sContestURLImages = atts.getValue("contestURLImages");
				sContestGroupId = atts.getValue("contestGroupId");
				sContestGroupName = atts.getValue("contestGroupName");
				sTmSystem = atts.getValue("tmSystem");
				
				//addLeague();
				
				program = new DataElementGroupProgram(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
				
				//hData.put(sContestGroupId, new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
				//vData.add(new DataElementMatchProgram(sContestGroupId, "", "", "", "", "", "", "", "", "", "", -1));
				
				if( program != null ) groupProgram.add(program);
				
			}
			else if(localName.equals("Match"))
			{
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
				this.analyse = atts.getValue("analyse");
				this.trend = atts.getValue("trend");
				this.precentTeam1 = Float.parseFloat( atts.getValue("precentteam1"));
				this.precentTeam2 = Float.parseFloat(atts.getValue("precentteam2"));
				this.starLike = Float.parseFloat(atts.getValue("starlike"));
				
				//addList();
				
				program.GroupItemCollection.add(new DataElementProgram(sContestGroupId, sMschId, sMatchId, sTeamCode1, sTeamCode2
						, sName1, sName2, sLiveChannel, sMatchDate, sMatchTime, sIsDetail, matchCount%2,this.analyse,trend,precentTeam1,precentTeam2,starLike));
				
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
		catch(Exception ex)
		{
			Log.d("Sportpool", "startElement Exception: " + ex.getMessage());
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		try
		{
			/*if (localName.equals("SportApp")) {

			} else if (localName.equals("Match")) {

				
				
			} else if (localName.equals("League")) {
				sTmName = "";
				sContestURLImages = "";
				sContestGroupId = "";
				sContestGroupName = "";
				sTmSystem = "";
			} else */
			if(localName.equals("status")) {
				bStatus = false;
			} else if(localName.equals("message")) {
				bMessage = false;
			}
		}
		catch(Exception ex)
		{
			Log.d("Sportpool", "endElement Exception: " + ex.getMessage());
		}
		
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		try
		{
			if (bStatus) {
				status += new String(ch, start, length);
			} else if (bMessage) {
				message += new String(ch, start, length);
			}
		}
		catch(Exception ex)
		{
			Log.d("Sportpool", "characters Exception: " + ex.getMessage());
		}

	}
	
	private void addLeague()
	{
		
		try
		{
			program = new DataElementGroupProgram(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
			
			//hData.put(sContestGroupId, new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
			//vData.add(new DataElementMatchProgram(sContestGroupId, "", "", "", "", "", "", "", "", "", "", -1));
			
			if( program != null ) groupProgram.add(program);
		}
		catch(Exception ex)
		{
			Log.d("Sportpool", "addLeague Exception: " + ex.getMessage());
		}
		
	}
	
	private void addList()
	{
		matchCount++;
		
		// add league
		//program = new DataElementGroupProgram(new DataElementLeague(sTmName, sContestURLImages, sContestGroupId, sContestGroupName, sTmSystem));
		
		// add match
		program.GroupItemCollection.add(new DataElementProgram(sContestGroupId, sMschId, sMatchId, sTeamCode1, sTeamCode2
				, sName1, sName2, sLiveChannel, sMatchDate, sMatchTime, sIsDetail, matchCount%2,this.analyse,trend,precentTeam1,precentTeam2,starLike));
		
		// add to group
		//groupProgram.add(program);
		

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
		precentTeam1 = 0;
		precentTeam2 = 0;
		starLike = 0;
	}
}
