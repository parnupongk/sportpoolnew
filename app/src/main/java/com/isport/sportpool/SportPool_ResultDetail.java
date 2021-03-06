package com.isport.sportpool;

import java.io.InputStream;
import java.util.Vector;

import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataElementScore;
import com.isport.sportpool.data.DataElementScoreDetailGame;
import com.isport.sportpool.data.DataElementScoreDetailScore;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterResultDetail;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ImageUtil;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.xml.XMLParserScoreDetail;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Xml;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_ResultDetail extends Activity implements ReceiveDataListener, Runnable, OnClickListener
{
	private RelativeLayout layout = null;
	private Handler handler = null;
	private ProgressDialog progress = null;
	
	private DataElementLeague dataLeague = null;
	private DataElementScore data = null;
	private ImageView imgLeagueName = null;
	private TextView textLeagueName = null;
	private XMLParserScoreDetail xmlPar = null;
	//private TableLayout layoutResult = null;
	private TextView team1 = null;
	private TextView score = null;
	private TextView time = null;
	private TextView team2 = null;
	private ImageView viewClose = null;
	
	private String URL = "";
	
	private AsycTaskLoadData load = null;
	
	private Vector<DataElementScoreDetailGame> vGame = null;
	private DataElementScoreDetailScore dataScore = null;
	private ListAdapterResultDetail adapterGame = null;
	private ListView listGame = null;
	private ImageUtil imgUtil = null;
	private boolean resumeHasRun = false;
	//private Vector<String[]> listPlayerTeam1 =  null;
	//private Vector<String[]> listPlayerTeam2 =  null;
	
	//private String DETAIL_GAME = "detailMatch";
	//private String DETAIL_PLAYER = "detailPlayer";
/*	
	private int[][] arrImage = {{R.drawable.bt_submenu_ringside_b, R.drawable.bt_submenu_ringside},
			{R.drawable.bt_submenu_player_b, R.drawable.bt_submenu_player}};
*/	
	private int index = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sub_score_detail);

		handler = new Handler();
		imgUtil = new ImageUtil(this);
//		progress = ProgressDialog.show(this, null, "Loading...", true, true);
		
        
        //imgHeader.setImageResource(R.drawable.hd_sub_ball);
        //layoutHeader.setBackgroundResource(R.drawable.hd_sub);
        
        viewClose = (ImageView) findViewById(R.id.sub_score_detail_img_close);
        imgLeagueName = (ImageView) findViewById(R.id.sub_score_detail_img_leagueName);
        textLeagueName = (TextView) findViewById(R.id.sub_score_detail_text_leagueName);
        listGame = (ListView)findViewById(R.id.sub_score_detail_list_game);
       
        
        viewClose.setOnClickListener(this);
        
        //layoutLeagueName.setBackgroundResource(R.drawable.tab02);
        imgLeagueName.setPadding((int)(10*imgUtil.scaleSize()), 0, (int)(5*imgUtil.scaleSize()), 0);
        
        //layoutResult = (TableLayout) findViewById(R.id.sub_score_detail_layout_result_score);
        team1 = (TextView) findViewById(R.id.sub_score_detail_result_team1);
        team2 = (TextView) findViewById(R.id.sub_score_detail_result_team2);
        score = (TextView) findViewById(R.id.sub_score_detail_result_score);
        time = (TextView) findViewById(R.id.sub_score_detail_result_time);
        
        //team1.setWidth((int)(183*imgUtil.scaleSize()));
        //team2.setWidth((int)(183*imgUtil.scaleSize()));
        //score.setWidth((int)(94*imgUtil.scaleSize()));
        //time.setWidth((int)(94*imgUtil.scaleSize()));
        
        team1.setGravity(Gravity.CENTER);
        team2.setGravity(Gravity.CENTER);
        score.setGravity(Gravity.CENTER);
        //time.setGravity(Gravity.CENTER);
        
        //layoutResult.setBackgroundColor(Color.rgb(69, 69, 69));
        //score.setBackgroundColor(Color.BLACK);
        //time.setBackgroundColor(Color.BLACK);
        
 
        if(getIntent().getExtras() != null)
        {
        	Bundle b = getIntent().getExtras();
        	index = b.getInt("page");
        	
        	data = (DataElementScore) b.get("data");
        	dataLeague = (DataElementLeague) b.get("header");
        	
        	
            AsycTaskLoadImage loadFlag = new AsycTaskLoadImage(imgLeagueName, null, null, null);
            loadFlag.execute(dataLeague.contestURLImages);
            
            textLeagueName.setText(dataLeague.contestGroupName);
            textLeagueName.setTextColor(Color.WHITE);
            
            team1.setText(data.teamName1);
            team2.setText(data.teamName2);
            score.setText(data.scoreHome+"-"+data.scoreAway);
            time.setText(data.minutes);
        
	        //team1.setTextColor(Color.YELLOW);
	        //team2.setTextColor(Color.YELLOW);
	        //score.setTextColor(Color.WHITE);
	        //time.setTextColor(Color.WHITE);
        
        }
	}
	
	private void setURL()
	{
		URL = DataURL.liveScoreDetail;
        URL += "&matchId="+data.matchId;
        URL += "&mschId="+data.mschId;
        URL += "&lang="+ DataSetting.Languge;
        URL += "&imei="+DataSetting.IMEI;
        URL += "&model="+DataSetting.MODEL;
        URL += "&imsi="+DataSetting.IMSI;
        URL += "&type="+DataSetting.TYPE;
        
        load = new AsycTaskLoadData(this, this,null,"ScoreDetail");
		load.execute(URL);
	}

	@Override
	protected void onStop() 
	{
		super.onStop();
		resumeHasRun = false;
	};
	@Override
	public void onResume()
	{
		super.onResume();
		
		if(!resumeHasRun)
		{
			progress = ProgressDialog.show(this, null, "Loading...", true, true);
			setURL();
			resumeHasRun = true;
		}

	}


	@Override
	public synchronized void run() {
		try {
			//load = new AsycTaskLoadData(this, this);
            //load.execute(URL);
            
            //thread.postDelayed(this, 60000L);
		} catch (Exception e) {
			// TODO: handle exception
			//PrintLog.printException(this,"LiveScoreDetail method run", e);
		}
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		String message = "",score = "";
		if(v == viewClose)
		{
			this.finish();
		}
		else
		{
			if(dataScore.scoreType.toUpperCase().equals("FT") || dataScore.scoreType.toUpperCase().equals("HT"))
			{
				message = dataScore.scoreType;
			}
			else if(dataScore.scoreType.toUpperCase().equals("FINISHED"))
			{
				message = "FT";
			}
			else
			{
				message = "น. "+dataScore.scoreType;
			}
			
			
			//score = (dataScore.scoreHome.equals("") && dataScore.scoreAway.equals(""))? :;
			message += " "+dataScore.teamName1+" "+dataScore.scoreHome+"-"+dataScore.scoreAway+" "+dataScore.teamName2;
			
			/*if(v == shareFB)
			{
				//Share.shareFB(this, dataScore.shareURL, message, "", dataScore.shareURL);
				shareFB.setImageResource(R.drawable.btn_facebook_act);
				Intent intent = new Intent(this, SportArena_facebook.class);
				intent.putExtra("title", dataLeague.contestGroupName);
				intent.putExtra("detail", message);
				intent.putExtra("urlImage", DataSetting.URLLOGO);
				intent.putExtra("urlLink", DataSetting.URLPLAYSOTRE);
				this.startActivityForResult(intent, 1);
			}
			else if(v == shareTwit)
			{
				String game = "";
				DataElementScoreDetailGame data = null;
	
				for(int i = 0; i < vGame.size(); i++)
				{
					data = vGame.elementAt(i);
					game += data.minute+" "+data.evenType+" "+data.playerName+" "+data.teamName;
				}
				
				//Share.sendEmail(this, new String[]{}, message, message+game);
				shareTwit.setImageResource(R.drawable.btn_twitter_act);
				Intent intent = new Intent(this, SportArena_Twitter.class);
				intent.putExtra("message", message);
				this.startActivityForResult(intent, 1);
	
			}	*/
		}
	}


	@Override
	public void onReceiveDataStream(String loadName, String url, String strOutput) {
		final Context context = this;
		try {


			if(xmlPar.status.equals("success"))
			{
				vGame = xmlPar.vGame;
				dataScore = xmlPar.dataLiveScore;
				//listPlayerTeam1 = xmlPar.vNameTeam1;
				//listPlayerTeam2 = xmlPar.vNameTeam2;

				handler.post(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						adapterGame = new ListAdapterResultDetail(context, vGame, dataScore, imgUtil);
						listGame.setAdapter(adapterGame);

//						adapterPlayer = new ListAdapterLiveScoreDetailPlayerName(context, listPlayerTeam1, listPlayerTeam2, imgUtil);
//						listName.setAdapter(adapterPlayer);
					}
				});
			}

			progress.dismiss();
		} catch (Exception e) {
			//PrintLog.printException(this,"LiveScoreDetail Note", e);
			progress.dismiss();
		}
	}

	@Override
	public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
		try
		{
			xmlPar = new XMLParserScoreDetail();
			Xml.parse(strOutput, xmlPar);
		}
		catch(Exception ex)
		{

		}
	}
}
