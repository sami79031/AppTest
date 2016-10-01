package com.apppartner.androidprogrammertest;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText username, password;
    private boolean isLoginSucess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        toolbarText.setText("Login");
        toolbarText.setTypeface(Font.setFont(this, Font.FontType.MachinatoExtraLight.toString()));

        username = (EditText) findViewById(R.id.username_id);
        password = (EditText) findViewById(R.id.password_id);


    }

    public Map<String, String> getUserLogins(String username, String password){
        Map<String, String> params = new TreeMap<>();
        params.put("username", username);
        params.put("password", password);

        return params;
    }

    public void onAuthenticateLogInBtnClicked(View v){
        String usr = username.getText().toString().trim();
        String psw = password.getText().toString().trim();

        if (usr.length() == 0 || psw.length() == 0)
        {
            showAlert("Warning", "Do not leave fields empty!");
            return;
        }

        LogInService logInService = new LogInService(this, getUserLogins(usr, psw));
        logInService.execute();
        logInService.delegate = this;
    }

    public void showAlert(String title, String message){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if (isLoginSucess)
                            backToMain();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .show();
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_login;
    }

    @Override
    public void onBackPressed()
    {
        backToMain();
    }

    private void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void parsedData(String output) {
        try {
            JSONObject jObj = new JSONObject(output);
            if (jObj.getString("code").equalsIgnoreCase("SUCCESS"))
                isLoginSucess = true;
            showAlert(jObj.getString("code"), jObj.getString("message").concat("\nTime: ")
                    .concat(jObj.getString("time").concat("mls")));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
