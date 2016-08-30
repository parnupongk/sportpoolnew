package com.isport.sportpool.service;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.InputStream;


public class AsycTaskLoadData extends AsyncTask<String, Void, String> {
    private InputStream input = null;
    private ProgressDialog pd = null;
    private Context context = null;
    private ReceiveDataListener receive = null;
    private String url = "";
    private String LoadName = "";

    public AsycTaskLoadData(Context context, ReceiveDataListener receive, ProgressDialog pd, String loadName) {
        this.pd = pd;
        this.context = context;
        this.receive = receive;
        this.LoadName = loadName;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (pd != null) {
            pd = ProgressDialog.show(context, null, "Loading...", true, true);
        }
    }

    @Override
    protected String doInBackground(String... urls) {

        String rtn = null;
        try {
            url = urls[0];

            OkHttpClient okHttpClient = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(url).build();


            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                rtn = response.body().string();
            } else {
                rtn = "Not Success - code : " + response.code();
            }

            receive.onReceiveGetDataXML(LoadName, url, rtn);


        } catch (Exception e) {
//			PrintLog.printException("AsycTaskLoadData method doInBackground", e);
            e.printStackTrace();
        }

        return rtn;
    }

    @Override
    protected void onProgressUpdate(Void... item) {
        /*if(pd != null){
            pd = ProgressDialog.show(context, null, "Loading..." + item.toString(), true, true);
        }*/
    }

    @Override
    protected void onPostExecute(String result) {
        if (pd != null) {
            pd.dismiss();
        }

        if (result != null) {
            if (receive != null)
                receive.onReceiveDataStream(LoadName, url, result);
        } else {
            receive.onReceiveDataStream(LoadName, url, null);
        }


    }
}
