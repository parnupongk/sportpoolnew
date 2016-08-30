package com.isport.sportpool.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLParserNotify extends DefaultHandler
{
	public String title = "";
	public String detail = "";
	public String footer = "";
	public String urlImgBig = "";
	public String urlImgSmall = "";
	public String actionCall  = "";
	public String actionURL = "";
	public String actionApp = "";
	
	public String notification = "";
	public String status = "";
	public String message = "";

	private boolean bNotification = false;
	private boolean bStatus = false;
	private boolean bMessage = false;
	
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
			title = atts.getValue("title");
			detail = atts.getValue("detail");
			footer = atts.getValue("footer");
			urlImgBig = atts.getValue("urlimgbig");
			urlImgSmall = atts.getValue("urlimgsmall");
			actionCall = atts.getValue("actioncall");
			actionURL = atts.getValue("actionurl");
			actionApp = atts.getValue("actionapp");
			
		} else if(localName.equals("notification")) {
			bNotification = true;
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
			
		} else if (localName.equals("notification")) {
			bNotification = false;
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
		} else if (bNotification) {
			notification += new String(ch, start, length);
		}
	}
}
