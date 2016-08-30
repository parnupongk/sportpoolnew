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

import com.isport.sportpool.data.DataElementGroupListMenu;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementListMenu;
import com.isport.sportpool.data.DataElementListMenuLeft;


public class XMLParserListMenu extends DefaultHandler
{
	private String iconURL_48x48 = "";
	private String iconURL_16x11 = "";
	private String countryName = "";
	private String countryId = "";
	
	private String iconFileName16x11 = "";
	private String iconFileName48x48 = "";
	private String contestGroupName = "";
	private String contestGroupId = "";
	
	public String isActive = "";
	public String isAdView = "";
	public String active_header = "";
	public String active_detail = "";
	public String active_footer = "";
	public String active_footer1 = "";
	public String active_otpwait = "";
	public String url_banner = "";
	public String url_icon = "";
	public String msisdn = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String serverversion = "";
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";

	public ArrayList<DataElementListMenu> vListMenuAllCountry = null;
	public DataElementGroupListMenu vListMenu=null;

	public ArrayList<DataElementGroupListMenu> vGroupListMenu=null;
	public ArrayList<DataElementListMenuLeft> vListMenuLeft = null;
	public ArrayList<DataElementGroupListMenu> vGroupOtherSportMenu=null;
	
	private String lmenu_name_en="";
	private String lmenu_name ="";
	private String lmenu_type ="";
	private String lmenu_url ="";
	private String RootXML = "";
	
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
			header = atts.getValue("header");
			date = atts.getValue("date");
			serverversion = atts.getValue("version");
			isAdView = atts.getValue("adview");
			url_banner = atts.getValue("urlbanner");
			url_icon = atts.getValue("urlicon");
			msisdn = atts.getValue("msisdn");
			vGroupListMenu = new ArrayList<DataElementGroupListMenu>();
			vListMenuLeft = new ArrayList<DataElementListMenuLeft>();
			vGroupOtherSportMenu = new ArrayList<DataElementGroupListMenu>();
			vListMenuAllCountry = new ArrayList<DataElementListMenu>();
			
			RootXML = localName;
		}
		else if( localName.equals("left_menu") )
		{
			RootXML = localName;
		}
		else if(localName.equals("menu") && RootXML.equals("left_menu"))
		{
			lmenu_name = atts.getValue("name");
			lmenu_type = atts.getValue("type");
			lmenu_url = atts.getValue("url");
			lmenu_name_en = atts.getValue("name_en");
			AddLeftMenu();
		}
		else if(localName.equals("Active"))
		{
			isActive = atts.getValue("isactive");
			active_header = atts.getValue("header");
			active_detail = atts.getValue("detail");
			active_footer = atts.getValue("footer");
			active_footer1 = atts.getValue("footer1");
			active_otpwait = atts.getValue("otp_status");
			RootXML = localName;
		}
		else if(localName.equals("Country"))
		{
			iconURL_48x48 = atts.getValue("iconURL_48x48");
			iconURL_16x11 = atts.getValue("iconURL_16x11");
			//sportType = atts.getValue("sportType");
			countryName = atts.getValue("countryName");
			countryId = atts.getValue("countryId");
			//id = atts.getValue("id");
			iconFileName16x11 = iconURL_16x11+atts.getValue("iconFileName16x11");
			iconFileName48x48 = iconURL_48x48+atts.getValue("iconFileName48x48");
			RootXML = localName;
			if(!countryId.equals("all")) NewCountry();

		}
		else if(localName.equals("League") && RootXML.equals("Country"))
		{
			if(atts.getValue("iconFileName16x11") != null)
			{
				iconFileName16x11 = iconURL_16x11+atts.getValue("iconFileName16x11");
				iconFileName48x48 = iconURL_48x48+atts.getValue("iconFileName48x48");
			}
			contestGroupName = atts.getValue("contestGroupName");
			contestGroupId = atts.getValue("contestGroupId");
			if(!countryId.equals("all")) addLeagueByCountry();
			else 
				{
					vListMenuAllCountry.add(new DataElementListMenu(atts.getValue("countryId"), atts.getValue("countryName"), "", iconFileName16x11, iconFileName48x48));
				}
			
			
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
			
		} else if (localName.equals("Country")) {
			iconURL_48x48 = "";
			iconURL_16x11 = "";
			if(!countryId.equals("all"))AddCountry();
		}else if (localName.equals("League")) {
			
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
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private void addLeagueByCountry()
	{
		DataElementLeague data = new DataElementLeague(countryName,iconFileName48x48,contestGroupId, contestGroupName, countryName );
		
		vListMenu.GroupLeagueCollection.add(data);
		
		contestGroupName = "";
		contestGroupId = "";
	}
	
	private void AddCountry()
	{
		if( vListMenu != null ) vGroupListMenu.add(vListMenu);
	}
	
	private void NewCountry()
	{
		DataElementListMenu data = new DataElementListMenu(countryId, countryName, "", iconFileName16x11, iconFileName48x48);
		vListMenu = new DataElementGroupListMenu(data);
	
		//sportType = "";
		//id = "";
	}
	
	/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	
	private void AddLeftMenu()
	{
		vListMenuLeft.add(new DataElementListMenuLeft(lmenu_url, lmenu_name, lmenu_type,lmenu_name_en));
	
	}

}