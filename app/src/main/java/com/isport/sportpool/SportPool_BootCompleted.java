package com.isport.sportpool;


import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.service.StartUp;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SportPool_BootCompleted extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		// TODO Auto-generated method stub
		if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
		{			
			DataSetting.IMSI = StartUp.getImsi(context);
			DataSetting.IMEI = StartUp.getImei(context);
			StartUp.getModel(context);
			StartUp.getSetting(context);
			
			if(DataSetting.IMSI != null && DataSetting.IMSI.length() > 1)
			{
					if(DataSetting.checkNotify)
					{
						context.startService(new Intent(context, SportPool_Notify.class));
					}
			}
		}
	}
}
