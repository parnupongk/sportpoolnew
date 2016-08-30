package com.isport.sportpool.xml;

import java.io.InputStream;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.isport.sportpool.data.DataElementSMSFree;
import com.isport.sportpool.data.DataElementSMSPremium;

public class XMLParserSMSService extends DefaultHandler
{
	//SMS Premium
	private String img640 = "";
	private String img320 = "";
	private String img75 = "";
	private String sTitle = "";
	private String sHeader = "";
	private String sMessage = "";
	private String sPhone = "";
	private String sUrl = "";
	
	// SMS Free
	private String newsId = "";
	private String newsHeader = "";
	private String newsTtile = "";
	private String newsDetail = "";
	private String newsImg190 = "";
	private String newsImg1000 = "";
	private String newsImg600 = "";
	private String newsImg400 = "";
	private String newsImg350 = "";
	private String newsImgDesc = "";
	private String newsUrlFB = "";
	
	public String status = "";
	public String message = "";
	public String textHeader = "";
	public String textDate = "";
	public String urlBanner = "";
	public boolean adView = false;
	
	public Vector<DataElementSMSPremium> vDataSMSPremium = null;
	public Vector<DataElementSMSFree> vDataSMSFree = null;

	
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
            Log.e("sportpool", "Exception: " + e.getMessage());
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
		if (localName.equals("SportApp"))
		{

			vDataSMSPremium = new Vector<DataElementSMSPremium>();
			vDataSMSFree = new Vector<DataElementSMSFree>();
			
			textHeader = atts.getValue("header");
			textDate = atts.getValue("date");
			urlBanner = atts.getValue("banner");
			adView = atts.getValue("adview") != null;
		}
		else if(localName.equals("Privilege"))
		{
			img640 = atts.getValue("image1");
			img320 = atts.getValue("image2");
			img75 = atts.getValue("image3");
			sTitle = atts.getValue("title");
			sHeader = atts.getValue("header");
			sMessage = atts.getValue("message");
			sPhone = atts.getValue("phone");
			sUrl = atts.getValue("url");
			
			//addLeague();
			
		}
		else if(localName.equals("News"))
		{
			newsId = atts.getValue("news_id");
			newsHeader = atts.getValue("news_header");
			newsTtile = atts.getValue("news_title");
			newsDetail = atts.getValue("news_detail");
			newsImg190 = atts.getValue("news_images_190");
			newsImg1000 = atts.getValue("news_images_1000");
			newsImg600 = atts.getValue("news_images_600");
			newsImg400 = atts.getValue("news_images_400");
			newsImg350 = atts.getValue("news_images_350");
			newsImgDesc = atts.getValue("news_images_description");
			newsUrlFB = atts.getValue("news_url_fb");

		}
		else if(localName.equals("status"))
		{
			//bStatus = true;
		}
		else if(localName.equals("message"))
		{
			//bMessage = true;
		}
		
	}
	
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
	{
		if (localName.equals("SportApp")) {

		} else if (localName.equals("Privilege")) {

			addSMSPremium();
			
		} else if (localName.equals("News")) {
			
			addSMSFree();
			
		} else if(localName.equals("status")) {
			//bStatus = false;
		} else if(localName.equals("message")) {
			//bMessage = false;
		}
	}
	
	@Override
	public void characters(char ch[], int start, int length)
	{
		/*if (bStatus) {
			status += new String(ch, start, length);
		} else if (bMessage) {
			message += new String(ch, start, length);
		}*/
	}
	
	private void addSMSPremium()
	{
		
		
		vDataSMSPremium.add(new DataElementSMSPremium(img640, img320, img75, sTitle, sHeader, sMessage, sPhone, sUrl));
	
		img640 = "";
		img320 = "";
		img75 = "";
		sTitle ="";
		sHeader ="";
		sMessage ="";
		sPhone ="";
		sUrl = "";
	}
	
	private void addSMSFree()
	{
		vDataSMSFree.add(new DataElementSMSFree(newsUrlFB, newsImgDesc, newsImg350, newsImg400
				, newsImg600, newsImg1000, newsImg190, newsDetail, newsTtile, newsHeader, newsId));
		
		newsId = "";
		newsHeader = "";
		newsTtile = "";
		newsDetail = "";
		newsImg190 = "";
		newsImg1000 = "";
		newsImg600 = "";
		newsImg400 = "";
		newsImg350 = "";
		newsImgDesc = "";
		newsUrlFB = "";
	}
}
