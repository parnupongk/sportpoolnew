package com.isport.sportpool;

import java.io.InputStream;
import java.util.Vector;

import com.isport.sportpool.data.DataElementLeagueTable;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserLeagueTable;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.util.Xml;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_AnalyseDetail_LeagueTable extends Fragment implements ReceiveDataListener
{
	private Context context = null;
	private Activity activity = null;
	
	private ProgressDialog progress = null;
	private Handler handler = null;
	private TextView txtHDetail = null;
	private TextView txtDetail = null;
	private TextView txtHDetail1 = null;
	private TextView txtDetail1 = null;
	private LinearLayout layoutLeague = null;
	private ImageView imgLeague = null;
	private TextView textLeague = null;
	private boolean resumeHasRun = false;
	//private String isByTeam = null;
	private String teamId = null; 
	private String contentGroupId = null;
	private TableLayout table = null;
	private TextView star = null;
	
	private String URL = "";
	
	private Vector<DataElementLeagueTable> vData = null;
	XMLParserLeagueTable xmlPar=null;
	
    public static SportPool_AnalyseDetail_LeagueTable newInstance(String teamId) {
    	SportPool_AnalyseDetail_LeagueTable fragment = new SportPool_AnalyseDetail_LeagueTable();
        Bundle args = new Bundle();

        
        fragment.teamId = teamId;
        
        fragment.setArguments(args);
        return fragment;
    }
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.sportpool_analysedetail_poolavg, container, false);
		layoutLeague = (LinearLayout) rootView.findViewById(R.id.table_layout_pool_league);
        imgLeague = (ImageView) rootView.findViewById(R.id.table_league_pool_img);
        textLeague = (TextView) rootView.findViewById(R.id.table_league_pool_text);
        table = (TableLayout) rootView.findViewById(R.id.table_layout_pool_table);
        star = (TextView) rootView.findViewById(R.id.table_star_pool_text);
        txtHDetail = (TextView)rootView.findViewById(R.id.pool_txt_hdetail);
        txtDetail = (TextView)rootView.findViewById(R.id.pool_txt_detail);
        txtHDetail1 = (TextView)rootView.findViewById(R.id.pool_txt_hdetail1);
        txtDetail1 = (TextView)rootView.findViewById(R.id.pool_txt_detail1);
        textLeague.setText("ผลงานของแต่ละทีม");
        txtHDetail.setText("อัตราพูลเฉลี่ย");
        txtHDetail1.setText("อัตราพูลสูงสุด");
        
        String[] s = SportPool_BaseClass.dataAnalyseDetail.poolAVG.split("\\|");
        if( s.length > 4 )
        {
        	txtDetail.setText( s[0].trim() + " " + s[1].trim() + " " + s[2].trim() );
        	txtDetail1.setText(s[3].trim() + " " + s[4].trim() + " " + s[5].trim());
        }
	
	//handler = new Handler();

//	progress = ProgressDialog.show(this, null, "Loading...", true, true);
	
    imgLeague.setPadding((int)(10*SportPool_BaseClass.imgUtil.scaleSize()), 0, (int)(5*SportPool_BaseClass.imgUtil.scaleSize()), 0);
    
    star.setTextColor(Color.WHITE);
    /*if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
	    params.setMargins(0, -90, 0, 0);
	    rootView.setLayoutParams(params);
    }*/
		return rootView;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		
		/*if(getIntent().getExtras() != null)
		{
			teamId = (String) getIntent().getExtras().getString("teamid");
		}
		

			setContentView(R.layout.sportpool_analysedetail_poolavg);
	        
		*/
	        
        
	}
	/*
	private void setURL()
	{

		
		//contestGroupId=&lang=&code=210002a&type=bb
		URL = DataURL.table;
		if(DataSetting.contentGroupId != null)
		{
        	URL += "&contestgroupid="+DataSetting.contentGroupId;
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
	}*/

	@Override
	public void onResume()
	{
		super.onResume();
		try
		{
			if(!resumeHasRun)
			{
				if(SportPool_BaseClass.vGroupLeftMenu == null )
				{
					Intent intent = new Intent(getActivity(), SportPool_Logo.class);
					startActivity(intent);
					//finish();
				}
				else
				{
					//progress = ProgressDialog.show(this, null, "Loading...", true, true);
					
					//GetDataListMenuLeague menu = new GetDataListMenuLeague(this, vGroupListMenu, imgUtil, hImage190, page_lt_league_gallery, DataSetting.contentGroupId,false);
			        //menu.SetAdapterMenu();
			        
					GenLeagueTable();
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
	public void onStop() {
    	super.onStop();
    	
    	DataSetting.contentGroupId = "";
    	resumeHasRun = false;
    }
    
	
	private void GenLeagueTable()
	{
		vData = SportPool_BaseClass.vDataLeagueTable;
		
		//final String text = xmlPar.date;
		//final String leagueName = xmlPar.tmName;
		//final String remark = xmlPar.remark;
		//table_league_date.setText(xmlPar.date);

		//textLeague.setText(leagueName);
		//star.setText(remark);
		
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
			row = new TableRow(getActivity());
			TableLayout.LayoutParams tableRowParams=
					  new TableLayout.LayoutParams
					  (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

					int leftMargin=5;
					int topMargin=2;
					int rightMargin=5;
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
			lp = new TextView(getActivity());
			name = new TextView(getActivity());
			p = new TextView(getActivity());
			w = new TextView(getActivity());
			d = new TextView(getActivity());
			l = new TextView(getActivity());
			f = new TextView(getActivity());
			a = new TextView(getActivity());
			pds = new TextView(getActivity());
			gd = new TextView(getActivity());
			
			lp.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			name.setWidth((int)(115*SportPool_BaseClass.imgUtil.scaleSize()));
			p.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			w.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			d.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			l.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			f.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			a.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			pds.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			gd.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
			
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
				row = new TableRow(getActivity());
				tableRowParams=
						  new TableLayout.LayoutParams
						  (TableLayout.LayoutParams.FILL_PARENT,TableLayout.LayoutParams.WRAP_CONTENT);

						tableRowParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin);
						row.setLayoutParams(tableRowParams );
				
				
				lp = new TextView(getActivity());
				name = new TextView(getActivity());
				p = new TextView(getActivity());
				w = new TextView(getActivity());
				d = new TextView(getActivity());
				l = new TextView(getActivity());
				f = new TextView(getActivity());
				a = new TextView(getActivity());
				pds = new TextView(getActivity());
				gd = new TextView(getActivity());
				
				lp.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				name.setWidth((int)(115*SportPool_BaseClass.imgUtil.scaleSize()));
				p.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				w.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				d.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				l.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				f.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				a.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				pds.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				gd.setWidth((int)(38*SportPool_BaseClass.imgUtil.scaleSize()));
				
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
		
		

		
		//AsycTaskLoadImage loadImage = new AsycTaskLoadImage(imgLeague, null, null, null);
		//loadImage.execute(xmlPar.contestURLImages);
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
		/*if( data.place.equals("1") )
		{
			name.setTextColor(Color.GREEN);
		}
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
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		try {

			if(xmlPar.status.equals("success") && xmlPar.message.trim().equals("") )
			{

			}
			else
			{
				textLeague.setText(xmlPar.message);
				star.setText("");
				table.removeAllViews();
			}
			progress.dismiss();
		} catch (Exception e) {
			//PrintLog.printException(this,"Table method onReceiveDataStream", e);
			Log.d("SportPool", "SportPool " + e.getMessage());
			progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		xmlPar = new XMLParserLeagueTable();
		Xml.parse(strOutput, xmlPar);
	}
}
