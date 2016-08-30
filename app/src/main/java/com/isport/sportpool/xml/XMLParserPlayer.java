package com.isport.sportpool.xml;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.isport.sportpool.data.DataElementPlayer;

public class XMLParserPlayer extends DefaultHandler
{
	private String id = "";
	private String name = "";
	private String lname = "";
	private String fname = "";
	private String countryName = "";
	private String position = "";
	private String shirtNumber = "";
	private String dateBirth = "";
	private String hight = "";
	private String wight = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";

	
	public Vector<DataElementPlayer> vData = null;
	
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
			vData = new Vector<DataElementPlayer>();
			date = atts.getValue("date");
			header = atts.getValue("header");
		} else if(localName.equals("player")) {
			id = atts.getValue("player_id");
			name = atts.getValue("player_name");
			lname = atts.getValue("player_lname");
			fname = atts.getValue("player_fname");
			countryName = atts.getValue("country_name");
			position = atts.getValue("position");
			shirtNumber = atts.getValue("shirt_number");
			dateBirth = atts.getValue("date_birth");
			hight = atts.getValue("height");
			wight = atts.getValue("weight");

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
			
		} else if (localName.equals("player")) {
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
		
	private void addList()
	{
		vData.add(new DataElementPlayer(id,name,lname,fname,countryName,position,shirtNumber,dateBirth,hight,wight));
		
		id = "";
		name = "";
		lname = "";
		fname = "";
		countryName = "";
		position = "";
		shirtNumber = "";
		dateBirth = "";
		hight = "";
		wight = "";
	}
}