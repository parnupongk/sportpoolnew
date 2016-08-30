package com.isport.sportpool;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import com.isport.sportpool.data.DataElementFavoriteTeam;
import com.isport.sportpool.data.DataElementFavoriteTeamComp;
import com.isport.sportpool.data.DataElementLeague;
import com.isport.sportpool.data.DataSetting;
import com.isport.sportpool.data.DataURL;
import com.isport.sportpool.list.ListAdapterSettingTeam_Detail;
import com.isport.sportpool.service.AsycTaskLoadData;
import com.isport.sportpool.service.AsycTaskLoadImage;
import com.isport.sportpool.service.ReceiveDataListener;
import com.isport.sportpool.service.StartUp;
import com.isport.sportpool.xml.XMLParserLeagueTable;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Xml;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.xml.sax.SAXException;

public class SportPool_SettingTeam_Detail extends Activity implements ReceiveDataListener, OnItemClickListener {


    // Data
    private DataElementLeague data = null;
    private Handler handler = null;
    private ArrayList<DataElementFavoriteTeam> favTeam = null;
    private XMLParserLeagueTable leagueTable = null;
    // control
    private ProgressDialog progress = null;
    private ListView listView = null;
    private TextView txtLeague = null;
    private ImageView imgLeague = null;
    private boolean resumeHasRun = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sportpool_setting_team_detail);

        if (getIntent().getExtras() != null) {
            data = (DataElementLeague) getIntent().getExtras().get("data");
        }

        favTeam = new ArrayList<DataElementFavoriteTeam>();

        listView = (ListView) findViewById(R.id.settingteam_list_view);
        txtLeague = (TextView) findViewById(R.id.settingteam_txt_league);
        imgLeague = (ImageView) findViewById(R.id.settingteam_img_league);

        if (SportPool_BaseClass.hImage190.get(data.contestURLImages) == null) {
            AsycTaskLoadImage load = new AsycTaskLoadImage(imgLeague, null, null, this);
            load.execute(data.contestURLImages);
        } else {
            imgLeague.setImageBitmap(SportPool_BaseClass.hImage190.get(data.contestURLImages));
            //progress.setVisibility(View.INVISIBLE);
        }


        listView.setOnItemClickListener(this);
        txtLeague.setText(data.tmName);
    }

    @Override
    protected void onStop() {
        super.onStop();
        resumeHasRun = false;
    }

    ;

    @Override
    protected void onResume() {
        super.onResume();

        if (!resumeHasRun) {
            GetSetting();
            GetURL();
            resumeHasRun = true;
        }

        //init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //StartUp.setSetting(context, teamId, teamName)
        SaveSetting();
    }

    private void SaveSetting() {
        try {
            String teamId = "", teamName = "", contentGroupId = "";
            for (DataElementFavoriteTeam fav : favTeam) {
                teamId += "," + fav.teamCode;
                teamName += "," + fav.teamName;
                contentGroupId += "," + fav.contestGroupId;
            }
            StartUp.setSetting(this, teamId, teamName, DataSetting.MatchTeamLike, contentGroupId);

        } catch (Exception ex) {
            Log.d("SportPool Error : ", ex.getMessage());
        }
    }

    private void GetSetting() {
        try {

            if (DataSetting.teamId != "" && DataSetting.teamName != "") {
                String[] teamIds = DataSetting.teamId.split(",");
                String[] teamNames = DataSetting.teamName.split(",");
                String[] contentGroupid = DataSetting.teamContentGroupId.split(",");
                for (int i = 0; i < teamIds.length; i++) {
                    if (!teamIds[i].equals("") && !teamNames[i].equals("")) {
                        //leagueSetting.put(teamIds[i], teamNames[i]);
                        favTeam.add(new DataElementFavoriteTeam(teamNames[i], teamIds[i], (contentGroupid.length > 0 ? contentGroupid[i] : "")));
                    }
                }
            }
        } catch (Exception ex) {
            Log.d("SportPool Error : ", ex.getMessage());
        }
    }

    private void GetURL() {
        try {
            String URL = DataURL.table;

            URL += "&contestgroupid=" + data.contestGroupId; // league Id

            URL += "&sportType=00001";
            URL += "&lang=" + DataSetting.Languge;
            URL += "&imei=" + DataSetting.IMEI;
            URL += "&model=" + DataSetting.MODEL;
            URL += "&imsi=" + DataSetting.IMSI;
            URL += "&type=" + DataSetting.TYPE;

            AsycTaskLoadData load = new AsycTaskLoadData(this, this, null, "leaguetable");
            load.execute(URL);


        } catch (Exception ex) {
            Log.d("SportPool Error : ", ex.getMessage());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.program, menu);
        return true;
    }


    @Override
    public void onItemClick(AdapterView<?> arg0, View view, int position, long arg3) {
        ImageView img = (ImageView) view.findViewById(R.id.sub_settingteam_img_select);

        //String s = vGroupLeagueTable.get(position).id;
        //boolean ss = favTeam.contains(vGroupLeagueTable.get(position).id);
        DataElementFavoriteTeam team = new DataElementFavoriteTeam(SportPool_BaseClass.vGroupLeagueTable.get(position).name, SportPool_BaseClass.vGroupLeagueTable.get(position).id, data.contestGroupId);
        int index = Collections.binarySearch(favTeam, team, new DataElementFavoriteTeamComp());
        if (index < 0) {
            favTeam.add(team);
            //leagueSetting.put(vGroupLeagueTable.get(position).id,vGroupLeagueTable.get(position).name);

            img.setImageResource(R.drawable.ic_chooseteam_press);
        } else {
            favTeam.remove(index);
            //leagueSetting.remove(vGroupLeagueTable.get(position).id);
            img.setImageBitmap(null);
        }
    }


    @Override
    public void onReceiveDataStream(String loadName, String url, String strOutput) {
        try {
            if (loadName == "leaguetable") {

                SportPool_BaseClass.vGroupLeagueTable = leagueTable.vData;

                String message = leagueTable.message;
                if (message.equals("")) {

                    final ListAdapterSettingTeam_Detail adater = new ListAdapterSettingTeam_Detail(this, SportPool_BaseClass.vGroupLeagueTable
                            , SportPool_BaseClass.imgUtil, SportPool_BaseClass.gethImage190(), favTeam, data.contestGroupId);

                    handler = new Handler();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            //program_message.setVisibility(View.INVISIBLE);
                            //program_list_data.setVisibility(View.VISIBLE);
                            //program_list_data.setAdapter(adaterResult);
                            listView.setAdapter(adater);
                            if (progress != null) progress.dismiss();
                        }
                    });

                } else {
                    //program_message.setVisibility(View.VISIBLE);
                    //program_list_data.setVisibility(View.INVISIBLE);
                    //program_message.setText(message);
                    if (progress != null) progress.dismiss();
                }

            }

        } catch (Exception ex) {
            if (progress != null) progress.dismiss();
            Log.d("SportPool Error : ", ex.getMessage());
            GetURL();
        }
    }

    @Override
    public void onReceiveGetDataXML(String loadName, String url, String strOutput) throws InterruptedException, SAXException {
        try {
            leagueTable = new XMLParserLeagueTable();
            Xml.parse(strOutput, leagueTable);
        } catch (Exception ex) {

        }
    }
}
