package com.isport.sportpool.service;

import java.net.URLEncoder;
import java.sql.Date;

import com.isport.sportpool.data.DataSetting;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.text.method.DateTimeKeyListener;

public class StartUp
{
	public static String getImsi(Context context)
	{
		try {
			String imsi = "";
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			imsi = telephonyManager.getSubscriberId();
			return imsi.equals("") || imsi == null ? "imsi" : imsi ; 
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("StartUp method getImsi", e);
			return "imsi";
		}
			
	}
	
	public static String getImei(Context context)
    {
		try
		{
			TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);	
	    	String id = telephonyManager.getDeviceId();
	    	return id.equals("") || id == null ? "imei" : id ; 

		}catch (Exception e) {
			// TODO: handle exception]
			//PrintLog.printException("StartUp method getImei", e);
			return "imei";
		}
    }
	
	public static void getModel(Context context)
	{
		try{
			DataSetting.MODEL = URLEncoder.encode(Build.MODEL);
			//PrintLog.print("StartUp method getModel", DataSetting.MODEL);
		} catch (Exception e) {
			//PrintLog.printException("StartUp method getModel", e);
		}
	}
	
	//////////////////////////////////////// Setting /////////////////////////////////////////
	
//	private final static String PREFS_NAME = "BS_SIAM_SPORT"; 
	
	public static void getSetting(Context context)
	{
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
//		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		DataSetting.teamId = settings.getString("teamId", "");
		DataSetting.teamName = settings.getString("teamName", "");
		DataSetting.teamContentGroupId = settings.getString("teamContentGroupId", "");
		DataSetting.MatchTeamLike = settings.getString("MatchTeamLike","");
		//DataSetting.setLeague = settings.getString("selectLeague", "");
		//DataSetting.checkNotify = settings.getBoolean("selectNotify", false);
		//DataSetting.checkFirst = settings.getBoolean("checkFirst", true);
		//DataSetting.checktimerefresh = settings.getString("selectRefreshTime", "0");
		if( DataSetting.MatchTeamLike.length() > 10000 )
		{
			DataSetting.MatchTeamLike = "";
		}
	}
	
	public static void setSetting(Context context, String teamId,String teamName,String MatchTeamLike,String teamContentGroupId)
	{
		// We need an Editor object to make preference changes.
		// All objects are from android.context.Context
//		SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
		SharedPreferences.Editor editor = settings.edit();
		//editor.putString("selectLanguage", language);
		//editor.putString("selectLeague", setLeague);
		//editor.putBoolean("selectNotify", setNotify);
		//editor.putBoolean("checkFirst", checkFirst);
		//editor.putString("selectRefreshTime", setRefreshTim);
		editor.putString("teamId", teamId);
		editor.putString("teamName", teamName);
		editor.putString("MatchTeamLike", MatchTeamLike);
		editor.putString("teamContentGroupId", teamContentGroupId);
		//DataSetting.Languge = language;
		//DataSetting.checkNotify = setNotify;
		//DataSetting.setLeague = setLeague;
		//DataSetting.checkFirst = checkFirst;
		DataSetting.teamId = teamId;
		DataSetting.teamName = teamName;
		DataSetting.MatchTeamLike = MatchTeamLike;
		DataSetting.teamContentGroupId = teamContentGroupId;
		// Commit the edits!
		editor.commit();
	}
}
