package com.isport.sportpool;


import com.isport.sportpool.R.id;
import com.isport.sportpool.data.DataElementLeagueTable;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserLeagueTable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.util.Vector;

public class SportPool_LeagueTable extends SportPool_BaseClass implements ReceiveDataListener, OnClickListener
{
	private Context context = null;
	private Activity activity = null;
	private XMLParserLeagueTable xmlPar = null;
	private ProgressDialog progress = null;
	private Handler handler = null;
	private TextView table_league_date = null;
	private RelativeLayout layout = null;
	private RelativeLayout tableleague_id = null;
	private LinearLayout layoutHeader = null;
	private ImageView imgHeader = null;
	private ImageView buttonHeader = null;
	private LinearLayout layoutLeague = null;
	private ImageView imgLeague = null;
	private TextView textLeague = null;

	//private String isByTeam = null;
	private String teamId = null; 
	private String contentGroupId = null;
	private TableLayout table = null;
	private TextView star = null;
	private boolean resumeHasRun = false;	
	private String URL = "";
	
	private Vector<DataElementLeagueTable> vData = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		if(getIntent().getExtras() != null)
		{
			teamId = (String) getIntent().getExtras().getString("teamid");
			DataSetting.contentGroupId = (String) getIntent().getExtras().getString("contentGroupId");
		}
		
		if( teamId == null || teamId == "" )
		{
			setContentView(R.layout.sportpool_main);
			layoutCenter = (RelativeLayout)findViewById(id.main_layout_center);
			
			layout = new RelativeLayout(this);

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        inflater.inflate(R.layout.sportpool_leaguetable, layout);
	        
	        layoutHeader = (LinearLayout) layout.findViewById(id.table_layout_header);
	        table_league_date = (TextView)layout.findViewById(id.table_league_date);
	        //buttonHeader = (ImageView) layout.findViewById(R.id.table_header_button);
	        tableleague_id = (RelativeLayout)layout.findViewById(id.tableleague_id);
	        layoutLeague = (LinearLayout) layout.findViewById(id.table_layout_league);
	        imgLeague = (ImageView) layout.findViewById(id.table_league_img);
	        textLeague = (TextView) layout.findViewById(id.table_league_text);
	        table = (TableLayout) layout.findViewById(id.table_layout_table);
	        star = (TextView) layout.findViewById(id.table_star_text);
	       
	        
	        layoutCenter.addView(layout);
		}
		else
		{
			setContentView(R.layout.sportpool_leaguetable);
	        
	        layoutHeader = (LinearLayout) findViewById(id.table_layout_header);
	        table_league_date = (TextView)findViewById(id.table_league_date);
	        //buttonHeader = (ImageView) findViewById(R.id.table_header_button);
	        tableleague_id = (RelativeLayout)findViewById(id.tableleague_id);
	        layoutLeague = (LinearLayout) findViewById(id.table_layout_league);
	        imgLeague = (ImageView) findViewById(id.table_league_img);
	        textLeague = (TextView) findViewById(id.table_league_text);
	        table = (TableLayout) findViewById(id.table_layout_table);
	        star = (TextView) findViewById(id.table_star_text);
		}
			
        imgLeague.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
        star.setTextColor(Color.WHITE);
        //table_league_date.setText("");
        //table_league_date.setOnClickListener(this);
        //buttonHeader.setOnClickListener(this);
        tableleague_id.setOnClickListener(this);
        
        setupActionBar();
		setTranslucentStatus(true);
        setSlideMenu();
        
	}
	
	private void setURL()
	{

		//progress = ProgressDialog.show(this, null, "Loading...", true, true);
		//contestGroupId=&lang=&code=210002a&type=bb
		URL = DataURL.table;
		if(DataSetting.contentGroupId != null)
		{
        	URL += "&contestgroupid="+ DataSetting.contentGroupId;
		}
		else
		{
			URL += "&contestgroupid=";
		}
			URL += "&sportType=00001";
			URL += "&lang="+DataSetting.Languge;
			URL += "&imei="+DataSetting.IMEI;
			URL += "&model="+DataSetting.MODEL;
			URL += "&imsi="+DataSetting.IMSI;
			URL += "&type="+DataSetting.TYPE;
        
        AsycTaskLoadData load = new AsycTaskLoadData(this, this,null,"leaguetable");
        load.execute(URL);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		
		CURRENT_PAGE_TH =  getResources().getString(R.string.page_leaguetable);
		CURRENT_PAGE = getResources().getString(R.string.page_leaguetable_en);
		try
		{
			if(!resumeHasRun)
			{
				if( ISACTIVE == null || ISACTIVE.equals("") )
				{
					Intent intent = new Intent(this, SportPool_Logo.class);
	    			startActivity(intent);
	    			finish();
				}
				else if(ISACTIVE.equals("N"))
				{
					Intent intent = new Intent(this, SportPool_Active.class);
	    			startActivity(intent);
	    			finish();

				}
				else if(vGroupLeftMenu == null )
				{
					Intent intent = new Intent(this, SportPool_Logo.class);
					startActivity(intent);
					finish();
				}
				else
				{
					progress = ProgressDialog.show(this, null, "Loading...", true, true);
					
					//GetDataListMenuLeague menu = new GetDataListMenuLeague(this, vGroupListMenu, imgUtil, hImage190, page_lt_league_gallery, DataSetting.contentGroupId,false);
			        //menu.SetAdapterMenu();
					init();
					setURL();
				}
				resumeHasRun = true;
			}
			
		}
		catch(Exception ex)
		{
			//PrintLog.printException(this, ex.getMessage(), ex);
		}
	}
    
	@Override
	protected void onPause() 
	{
		super.onPause();
		resumeHasRun = false;
	};
	
	@Override
    protected void onStop() {
    	// TODO Auto-generated method stub
    	super.onStop();
    	resumeHasRun = false;
    	DataSetting.contentGroupId = "";
		finish();
    }
	
	
	private void GenLeagueTable() throws Exception
	{
		try
		{
			TableRow row = null;
			TextView lp = null;
			TextView name = null;
			TextView p = null;
			TextView w = null;
			TextView d = null;
			TextView l = null;
			TextView f = null;
			TextView a = null;
			TextView pds = null;
			TextView gd = null;
			int place = 0;
			int placeTemp = 0;
			table.removeAllViews();
			
			for(int i = 0; i < vData.size(); i++)
			{
				row = new TableRow(this);
				TableLayout.LayoutParams tableRowParams=
						  new TableLayout.LayoutParams
						  (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

						int leftMargin=8;
						int topMargin=2;
						int rightMargin=8;
						int bottomMargin=2;

						tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
						row.setLayoutParams(tableRowParams );
				/*if((i%2) == 0)
				{
					
					row.setBackgroundResource(R.drawable.bg_list_league);

				}
				else
				{
					row.setBackgroundResource(R.drawable.bg_list_team);
				}*/
						if((i%2) == 0)
						{
							row.setBackgroundResource(R.drawable.bg_tab_leaguetable1);
						}
						else
						{
							row.setBackgroundResource(R.drawable.bg_tab_leaguetable2);
						}
				lp = new TextView(this);
				name = new TextView(this);
				p = new TextView(this);
				w = new TextView(this);
				d = new TextView(this);
				l = new TextView(this);
				f = new TextView(this);
				a = new TextView(this);
				pds = new TextView(this);
				gd = new TextView(this);
				
				lp.setWidth((int)(38*imgUtil.scaleSize()));
				name.setWidth((int)(115*imgUtil.scaleSize()));
				p.setWidth((int)(38*imgUtil.scaleSize()));
				w.setWidth((int)(38*imgUtil.scaleSize()));
				d.setWidth((int)(38*imgUtil.scaleSize()));
				l.setWidth((int)(38*imgUtil.scaleSize()));
				f.setWidth((int)(38*imgUtil.scaleSize()));
				a.setWidth((int)(38*imgUtil.scaleSize()));
				pds.setWidth((int)(38*imgUtil.scaleSize()));
				gd.setWidth((int)(38*imgUtil.scaleSize()));
				
				lp.setGravity(Gravity.CENTER);
				p.setGravity(Gravity.CENTER);
				w.setGravity(Gravity.CENTER);
				d.setGravity(Gravity.CENTER);
				l.setGravity(Gravity.CENTER);
				f.setGravity(Gravity.CENTER);
				a.setGravity(Gravity.CENTER);
				pds.setGravity(Gravity.CENTER);
				gd.setGravity(Gravity.CENTER);
				
				DataElementLeagueTable data = vData.elementAt(i);
				placeTemp=Integer.parseInt( data.place) ;
				if(place > placeTemp || i==0)
				{
					AddHeaderLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
					row.setBackgroundResource(R.drawable.tab_leaguetable);
					row = new TableRow(this);
					tableRowParams=
							  new TableLayout.LayoutParams
							  (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

							tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
							row.setLayoutParams(tableRowParams );
					
					
					lp = new TextView(this);
					name = new TextView(this);
					p = new TextView(this);
					w = new TextView(this);
					d = new TextView(this);
					l = new TextView(this);
					f = new TextView(this);
					a = new TextView(this);
					pds = new TextView(this);
					gd = new TextView(this);
					
					lp.setWidth((int)(38*imgUtil.scaleSize()));
					name.setWidth((int)(115*imgUtil.scaleSize()));
					p.setWidth((int)(38*imgUtil.scaleSize()));
					w.setWidth((int)(38*imgUtil.scaleSize()));
					d.setWidth((int)(38*imgUtil.scaleSize()));
					l.setWidth((int)(38*imgUtil.scaleSize()));
					f.setWidth((int)(38*imgUtil.scaleSize()));
					a.setWidth((int)(38*imgUtil.scaleSize()));
					pds.setWidth((int)(38*imgUtil.scaleSize()));
					gd.setWidth((int)(38*imgUtil.scaleSize()));
					
					lp.setGravity(Gravity.CENTER);
					p.setGravity(Gravity.CENTER);
					w.setGravity(Gravity.CENTER);
					d.setGravity(Gravity.CENTER);
					l.setGravity(Gravity.CENTER);
					f.setGravity(Gravity.CENTER);
					a.setGravity(Gravity.CENTER);
					pds.setGravity(Gravity.CENTER);
					gd.setGravity(Gravity.CENTER);
					
					AddDataLeagueTable(data,row,lp,name,p,w,d,l,f,a,pds,gd);
				}
				else
				{
					AddDataLeagueTable(data,row,lp,name,p,w,d,l,f,a,pds,gd);
					
				}
				
				place = Integer.parseInt( data.place) ;
				
				
			}
		}
		catch(Exception ex)
		{
			throw new Exception(ex.getMessage());
		}
	}
	
	private void AddDataLeagueTable(DataElementLeagueTable data,TableRow row,TextView lp,TextView name,TextView p
			,TextView w,TextView d,TextView l,TextView f,TextView a,TextView pds,TextView gd)
	{
		name.setSingleLine();
		name.setEllipsize(TruncateAt.END);
		
		lp.setText(data.place);
		name.setText(data.name);
		p.setText(data.tPlay);
		w.setText(data.tWon);
		d.setText(data.tDraws);
		l.setText(data.tLost);
		f.setText(data.tScore);
		a.setText(data.tConcede);
		pds.setText(data.tPoint);
		gd.setText(data.tDiff);
		
		lp.setTextColor(Color.BLACK);
		/*
		else if( data.place.equals("2") )
		{
			name.setTextColor(Color.CYAN);
		}
		else if( data.place.equals("3") )
		{
			name.setTextColor(Color.MAGENTA);
		}
		else*/ 
		name.setTextColor(Color.BLACK);
		p.setTextColor(Color.BLACK);
		w.setTextColor(Color.BLACK);
		d.setTextColor(Color.BLACK);
		l.setTextColor(Color.BLACK);
		f.setTextColor(Color.BLACK);
		a.setTextColor(Color.BLACK);
		pds.setTextColor(Color.BLACK);
		gd.setTextColor(Color.BLACK);
		
		if( data.id.equals(teamId) )
		{
			name.setTextColor(Color.GREEN);
		}
		
		AddRowToLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
	}
	private void AddHeaderLeagueTable(TableRow row,TextView lp,TextView name,TextView p
			,TextView w,TextView d,TextView l,TextView f,TextView a,TextView pds,TextView gd)
	{
		lp.setText("LP");
		name.setText("TEAM");
		p.setText("P");
		w.setText("W");
		d.setText("D");
		l.setText("L");
		f.setText("F");
		a.setText("A");
		pds.setText("PDs");
		gd.setText("GD");
		
		lp.setTextColor(Color.WHITE);
		name.setTextColor(Color.WHITE);
		p.setTextColor(Color.BLACK);
		w.setTextColor(Color.BLACK);
		d.setTextColor(Color.BLACK);
		l.setTextColor(Color.BLACK);
		f.setTextColor(Color.BLACK);
		a.setTextColor(Color.BLACK);
		pds.setTextColor(Color.BLACK);
		gd.setTextColor(Color.BLACK);
		
		pds.setTextSize(12);
		
		row.setBackgroundColor(Color.rgb(44, 44, 44));
		AddRowToLeagueTable(row,lp,name,p,w,d,l,f,a,pds,gd);
	}
	private void AddRowToLeagueTable(TableRow row,TextView lp,TextView name,TextView p
			,TextView w,TextView d,TextView l,TextView f,TextView a,TextView pds,TextView gd)
	{
		
		row.addView(lp);
		row.addView(name);
		row.addView(p);
		row.addView(w);
		row.addView(d);
		row.addView(l);
		row.addView(f);
		row.addView(a);
		row.addView(pds);
		row.addView(gd);
		
		table.addView(row);
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if(item.getItemId() == id.action_leagetable)
		{
			Intent intent = new Intent(this, SportPool_MenuLeague.class);
			startActivity(intent);
			return true;
		}
		else if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
		else return super.onOptionsItemSelected(item);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.leagetable, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {

			if(xmlPar.status.equals("success") && xmlPar.message.trim().equals("") )
			{
				vData = xmlPar.vData;

				final String leagueName = xmlPar.tmName;
				final String remark = xmlPar.remark;
				table_league_date.setText(xmlPar.date);


				handler = new Handler();
				handler.post(new Runnable() {
					@Override
					public void run() {

						try {

							textLeague.setText(leagueName);
							star.setText(remark);
							GenLeagueTable();

							if( progress != null )progress.dismiss();
						} catch (Exception e) {
							e.printStackTrace();
						}

					}
				});






				AsycTaskLoadImage loadImage = new AsycTaskLoadImage(imgLeague, null, null, null);
				loadImage.execute(xmlPar.contestURLImages);
			}
			else
			{
				textLeague.setText(xmlPar.message);
				star.setText("");
				table.removeAllViews();
			}
			progress.dismiss();
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException(this,"Table method onReceiveDataStream", e);
			Log.d("SportPool", "SportPool " + e.getMessage());
			progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlPar = new XMLParserLeagueTable();
			Xml.parse(strOutput, xmlPar);
		}
		catch(Exception ex)
		{

		}
	}
}
