package com.isport.sportpool;

import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.list.ListAdapterMenuLeagueDetail;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class SportPool_MenuLeague extends Activity  implements OnChildClickListener{
	
	private RelativeLayout layout = null;
	private RelativeLayout layoutCenter = null;
	private ExpandableListView list_menuleague = null;
	private TextView txtLeague = null;
	private boolean resumeHasRun = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	
    	//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
    	
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.sub_menuleague);

        list_menuleague = (ExpandableListView)findViewById(R.id.sub_menuleague_list_data);
        txtLeague = (TextView)findViewById(R.id.menuleague_txt);
        txtLeague.setText("กรุณาเลือกลีกที่ต้องการ");
        list_menuleague.setOnChildClickListener(this);
        //layoutCenter.addView(layout);
        
        //setupActionBar();
        //customizeStatusBar();
        //setSlideMenu();
        
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
    
    @Override 
    protected void onStop() {
    	super.onStop();
    	resumeHasRun = false;
		finish();
    };
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	try {
    		
    		super.onResume();
    		if(!resumeHasRun)
    		{
	    		if(SportPool_BaseClass.vGroupListMenu == null)
	    		{
	    			Intent intent = new Intent(this, SportPool_Logo.class);
	    			startActivity(intent);
	    			this.finish();
	    		}
	    		else
	    		{
	    			ListAdapterMenuLeagueDetail adapterMenuLeague = new ListAdapterMenuLeagueDetail(this, list_menuleague, SportPool_BaseClass.vGroupListMenu, SportPool_BaseClass.imgUtil, "", SportPool_BaseClass.gethImage190());
	    			list_menuleague.setAdapter(adapterMenuLeague);
	    		}
	    		resumeHasRun = true;
    		}
    		//init();

    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }



	@Override
	public boolean onChildClick(ExpandableListView arg0, View arg1, int groupPosition,
			int childPosition, long arg4) {
		
		DataSetting.contentGroupId = SportPool_BaseClass.vGroupListMenu.get(groupPosition).GroupLeagueCollection.get(childPosition).contestGroupId;
		this.finish();
		return false;
	}

    
}
