package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.Api.ChatDataJsonParse;
import com.apppartner.androidprogrammertest.Api.ParsedDataProtocol;
import com.apppartner.androidprogrammertest.Utils.Font;
import com.apppartner.androidprogrammertest.adapters.ChatsArrayAdapter;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class ChatActivity extends BaseAppActivity implements ParsedDataProtocol
{
    private ChatsArrayAdapter chatsArrayAdapter;
    private RecyclerView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        listView = (RecyclerView) findViewById(R.id.listView);
        chatsArrayAdapter = new ChatsArrayAdapter(this);
        listView.setAdapter(chatsArrayAdapter);
        listView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        this.callChatDataApi();

        toolbarText.setText("Chat");
        toolbarText.setTypeface(Font.setFont(this, Font.FontType.MachinatoExtraLight.toString()));
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_chat;
    }

    private void callChatDataApi(){
        ChatDataJsonParse chatDataJsonParse = new ChatDataJsonParse(this);
        chatDataJsonParse.delegate = this;
        chatDataJsonParse.parseData();
    }



    @Override
    public void onBackPressed()
    {
        backToMain();
    }

    public void backToMain(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void parsedData(ArrayList<ChatData> output) {
        if (output != null) {
            chatsArrayAdapter.setChatDataArrayList(output);
            chatsArrayAdapter.notifyDataSetChanged();
        }
    }

}
