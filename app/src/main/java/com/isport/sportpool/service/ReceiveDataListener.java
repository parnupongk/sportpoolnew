package com.isport.sportpool.service;

import org.xml.sax.SAXException;

public interface ReceiveDataListener
{
	public void onReceiveDataStream(String loadName, String url, String strOutput) ;
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException;
}
