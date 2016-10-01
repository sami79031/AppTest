package com.apppartner.androidprogrammertest.Api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Samurai on 9/30/16.
 */

public class LogInService extends AsyncTask<Void, Void, Void> {
    public LogInResponseProtocol delegate;
    private Map<String, String> params;
    private final String URL = "http://dev3.apppartner.com/AppPartnerDeveloperTest/scripts/login.php" ;
    private Context context;
    private ProgressDialog proDialog;
    private String jsonStr = "";
    long startTime;

    public LogInService(Context context, Map<String, String> params) {
        this.context = context;
        this.params = params;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Showing progress loading dialog
        proDialog = new ProgressDialog(context);
        proDialog.setMessage("Please wait...");
        proDialog.setCancelable(false);
        proDialog.show();
    }

    @Override
    protected Void doInBackground(Void... voids) {
        ServiceConnection serviceConnection = new ServiceConnection();
        try {
            startTime = System.currentTimeMillis();
            jsonStr = serviceConnection.makeWebServiceCall(URL, params);

        } catch (Exception e) {
            Log.d("locl",e.getLocalizedMessage());
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void requestresult) {
        super.onPostExecute(requestresult);
        // Dismiss the progress dialog
        if (proDialog.isShowing())
            proDialog.dismiss();

        long estimatedTime = System.currentTimeMillis() - startTime;
        JSONObject jObj = null;
        try {
            jObj = new JSONObject(jsonStr);
            jObj.put("time", estimatedTime);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        assert jObj != null;
        delegate.parsedData(jObj.toString());
    }
}
