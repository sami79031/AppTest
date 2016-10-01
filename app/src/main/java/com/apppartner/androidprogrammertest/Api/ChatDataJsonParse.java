package com.apppartner.androidprogrammertest.Api;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.models.ChatData;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Samurai on 9/30/16.
 */

public class ChatDataJsonParse {
    private static final String LOG_TAG = "ChatDataJsonParse";
    public ParsedDataProtocol delegate;
    private Context context;
    private ArrayList<ChatData> chatDataArrayList;

    public ChatDataJsonParse(Context context) {
        this.context = context;
        chatDataArrayList = new ArrayList<>();
    }


    public void parseData(){
        try
        {
            String chatFileData = loadChatFile();
            //System.out.println(chatFileData);
            JSONObject jsonData = new JSONObject(chatFileData);
            JSONArray jsonArray = jsonData.getJSONArray("data");

            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ChatData chatData = new ChatData(jsonObject);
                chatDataArrayList.add(chatData);
            }


        }
        catch (Exception e)
        {
            Log.w(LOG_TAG, e);
        }

        delegate.parsedData(chatDataArrayList);
    }


    private String loadChatFile() throws IOException
    {
        InputStream inputStream = context.getResources().openRawResource(R.raw.chat_data);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        String receiveString;
        StringBuilder stringBuilder = new StringBuilder();

        while ((receiveString = bufferedReader.readLine()) != null )
        {
            stringBuilder.append(receiveString);
            stringBuilder.append("\n");
        }

        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();


        return stringBuilder.toString();
    }
}
