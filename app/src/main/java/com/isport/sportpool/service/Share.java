package com.isport.sportpool.service;

import com.isport.sportpool.R;
import com.isport.sportpool.SportPool_facebook;

import android.content.Context;
import android.content.Intent;

public class Share
{
	public static boolean sendSMS(Context context, String message)
	{
		try{
			Intent sendIntent = new Intent(Intent.ACTION_VIEW);
			sendIntent.putExtra("sms_body", message); 
			sendIntent.setType("vnd.android-dir/mms-sms");
			context.startActivity(Intent.createChooser(sendIntent,"Select SMS application."));
			
//			SMS sms = new SMS(context);
//			sms.sendSMS(phoneNumber, message);
			return true;
		}catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("Share method sendSMS", e);
			return false;
		}
	}
	
	public static void sendEmail(Context context, String [] email, String subject, String text)
	{
		try
		{
			String script= "\n \nเกาะติดผลฟุตบอลทั่วโลก \n" +
					"   wap.isport.co.th \n" +
					"หรือดาวน์โหลด Application นี้ได้ที่ \n" +
					"   https://market.android.com/details?id=bs.android.SportArena&feature=search_result";
			
			Intent emailIntent = new Intent(Intent.ACTION_SEND);
		    emailIntent.setType("plain/text");
		    emailIntent.putExtra(Intent.EXTRA_EMAIL, email);
		    emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
		    emailIntent.putExtra(Intent.EXTRA_TEXT, text+script);
		    context.startActivity(Intent.createChooser(emailIntent, "Select email application."));
			
//			Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);  
////			String aEmailList[] = { "user@fakehost.com","user2@fakehost.com" };  
////			String aEmailCCList[] = { "user3@fakehost.com","user4@fakehost.com"};  
////			String aEmailBCCList[] = { "user5@fakehost.com" };
//			emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, email);  
////			emailIntent.putExtra(android.content.Intent.EXTRA_CC, aEmailCCList);  
////			emailIntent.putExtra(android.content.Intent.EXTRA_BCC, aEmailBCCList);  
//			emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
//			emailIntent.setType("plain/text");  
//			emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
//			context.startActivity(emailIntent);
			
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException("Share method sendEmail", e);
		}
	}
	
	public static void shareFB(Context context, String link, String title, String description, String imgURL)
	{
//		link = "http://wap.isport.co.th/isportws/facebookshare.aspx?id=112548";
//		imgURL = "http://203.149.30.76/isportimage/images/325x200/110819F0S55482.jpg";
//		description = "เนย์มาร์ กองหน้าทีมชาติบราซิลของ ซานโตส ซูฮกความฮอตของ บาร์เซโลน่า ยอมรับ ไม่มีทีมไหนในโลกนี้ที่จะมาเปรียบเทียบกับ \"เจ้าบุญทุ่ม\" ได้อีกแล้ว เพราะนอกจากระบบการเล่นจะสุดยอด ยังมีนักเตะเทพประทานอย่าง ลิโอเนล เมสซี่ ร่วมทีมอยู่ด้วย";
//		title = "เนย์มาร์รับไม่มีทีมไหนเทียบบาร์ซ่าได้ ";
		
		description = "เกาะติดผลฟุตบอลทั่วโลก   wap.isport.co.th หรือดาวน์โหลด Application ได้ที่  Android Market";
//		description += "";market.android.com/details?id=bs.android.SportArena&feature=search_result";
		
		Intent intent = new Intent(context, SportPool_facebook.class);
		intent.putExtra("app_id", "320504274771237" );
		//in tent.putExtra("app_id", "457628054372449"); test
		intent.putExtra("shareURL", link);
		intent.putExtra("shareTitle", title);
		intent.putExtra("shareDetail", description);
		intent.putExtra("shareImage",imgURL);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
}
