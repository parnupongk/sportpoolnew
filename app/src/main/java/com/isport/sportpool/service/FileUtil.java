package com.isport.sportpool.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import android.content.Context;

public class FileUtil {
	@SuppressWarnings("deprecation")
	public void writeFileToInternalStorage(Context context,String fileName) {
		  String eol = System.getProperty("line.separator");
		  BufferedWriter writer = null;
		  try {
		    writer = 
		      new BufferedWriter(new OutputStreamWriter(context.openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE)));
		    writer.write("This is a test1." + eol);
		    writer.write("This is a test2." + eol);
		  } catch (Exception e) {
		      e.printStackTrace();
		  } finally {
		    if (writer != null) {
		    try {
		      writer.close();
		    } catch (IOException e) {
		      e.printStackTrace();
		    }
		    }
		  }
		}
	
	@SuppressWarnings("finally")
	public String readFileFromInternalStorage(Context context,String fileName) {
		StringBuffer buffer = new StringBuffer();
		  String eol = System.getProperty("line.separator");
		  BufferedReader input = null;
		  try {
		    input = new BufferedReader(new InputStreamReader(context.openFileInput(fileName)));
		    String line;
		    
		    while ((line = input.readLine()) != null) {
		    	buffer.append(line + eol);
		    }
		  } 
		  catch (Exception e) 
		  {
		     e.printStackTrace();
		  } 
		  finally 
		  {
			  return buffer.toString();
			  /*if (input != null) 
			  {
			    try 
			    {
			    	input.close();
			    	
			    }
			    catch (IOException e) 
			    {
			      e.printStackTrace();
			    }
			  }*/
		  }
	} 
	
}
