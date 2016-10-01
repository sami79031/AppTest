package com.apppartner.androidprogrammertest.Api;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Samurai on 6/22/16.
 */


public class ServiceConnection {
    private static final char PARAMETER_DELIMITER = '&';
    private static final char PARAMETER_EQUALS_CHAR = '=';
    public final static int GETRequest = 1;
    public final static int POSTRequest = 2;


    public ServiceConnection() {
    }

    public String makeWebServiceCall(String url, Map<String, String> params) throws Exception {
        return this.doHttpUrlConnectionAction(url, params);
    }
    private String doHttpUrlConnectionAction(String desiredUrl, Map<String, String> params)
            throws Exception {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        StringBuilder stringBuilder = null;

        try {
            // create connection
            URL urlToRequest = new URL(desiredUrl);
            urlConnection = (HttpURLConnection) urlToRequest.openConnection();
            urlConnection.setConnectTimeout(15 * 1000);
            urlConnection.setReadTimeout(15 * 1000);

            urlConnection.setDoOutput(true);
            urlConnection.setRequestMethod("POST");


            //send the POST out
            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            String query = createQueryStringForParameters(params);
            out.print(query);
            out.close();


            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            stringBuilder = new StringBuilder();

            // read the output from the server
            reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

        } catch (MalformedURLException | SocketTimeoutException e) {
            Log.d("URL", ""+e.getLocalizedMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return stringBuilder.toString();
    }

    public static String createQueryStringForParameters(Map<String, String> parameters) {
        StringBuilder parametersAsQueryString = new StringBuilder();
        if (parameters != null) {
            boolean firstParameter = true;

            for (String parameterName : parameters.keySet()) {
                if (!firstParameter) {
                    parametersAsQueryString.append(PARAMETER_DELIMITER);
                }

                parametersAsQueryString.append(parameterName)
                        .append(PARAMETER_EQUALS_CHAR)
                        .append(URLEncoder.encode(
                                parameters.get(parameterName)));

                firstParameter = false;
            }
        }
        return parametersAsQueryString.toString();
    }
}
