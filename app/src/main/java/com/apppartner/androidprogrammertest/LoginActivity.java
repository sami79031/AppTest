package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.apppartner.androidprogrammertest.Api.ChatDataJsonParse;
import com.apppartner.androidprogrammertest.Api.LogInResponseProtocol;
import com.apppartner.androidprogrammertest.Api.LogInService;
import com.apppartner.androidprogrammertest.Api.ServiceConnection;
import com.apppartner.androidprogrammertest.Utils.Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.TreeMap;

public class LoginActivity extends BaseAppActivity implements LogInResponseProtocol
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        toolbarText.setText("Login");
        toolbarText.setTypeface(Font.setFont(this, Font.FontType.MachinatoExtraLight.toString()));

        LogInService logInService = new LogInService(this, getUserLogins());
        logInService.execute();
        logInService.delegate = this;
    }

    public Map<String, String> getUserLogins(){
        Map<String, String> params = new TreeMap<>();
        params.put("username", "AppPartner");
        params.put("password", "qwerty");

        return params;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void parsedData(String output) {
        try {
            JSONObject jObj = new JSONObject(output);
            Log.d("CODE", jObj.getString("code"));
            Log.d("Message", jObj.getString("message"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
