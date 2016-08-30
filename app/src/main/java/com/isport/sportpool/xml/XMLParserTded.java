package com.isport.sportpool.xml;

import java.util.Vector;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import com.isport.sportpool.data.DataElementTded;

public class XMLParserTded extends DefaultHandler
{
	private String tdedName = "";
	private String tdedValue = "";
	
	private boolean bMessage = false;
	private boolean bStatus = false;
	
	public String message = "";
	public String status = "";
	public String header = "";
	public String date = "";


	
	public Vector<DataElementTded> vData = null;
	
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
			vData = new Vector<DataElementTded>();
			date = atts.getValue("date");
			header = atts.getValue("header");
		} else if(localName.equals("detail")) {
			tdedName = atts.getValue("tded_name");
			tdedValue = atts.getValue("tded");

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
			
		} else if (localName.equals("detail")) {
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
		vData.add(new DataElementTded(tdedName,tdedValue));
		
		tdedName = "";
		tdedValue = "";
	}
}