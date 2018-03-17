package vn.uits.bllutooth_android;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Copyright © 2017 BAP CO., LTD
 * Created by PHUQUY on 3/17/18.
 */

public class NetworkActivity extends AppCompatActivity {

    private static final String TAG = NetworkActivity.class.getSimpleName();
    private URL url;
    private HttpURLConnection mConnection;
    private OutputStreamWriter outputStream;

    private EditText mEdtAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);

        mEdtAddress = findViewById(R.id.mEdtAddress);
    }

    public void onSearch(View view) {
        new DownloadData().execute();
    }

    private class DownloadData extends AsyncTask<Context, String, String> {

        private String mResutl;

        @Override
        protected String doInBackground(Context... contexts) {
            try {
                url = new URL("https://maps.google.com/maps/api/geocode/json?address=" + mEdtAddress.getText().toString().trim());

                mConnection = (HttpURLConnection) url.openConnection();
                mConnection.setDoOutput(true);
                mConnection.setRequestMethod("GET");

                outputStream = new OutputStreamWriter(mConnection.getOutputStream());
                outputStream.flush();
                outputStream.close();

                StringBuilder sb = new StringBuilder();
                InputStreamReader inputStreamReader = new InputStreamReader(mConnection.getInputStream());
                BufferedReader reader = new BufferedReader(inputStreamReader);

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                    mResutl = sb.toString();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return mResutl;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mEdtAddress.setText("");
            try {
                JSONObject object = new JSONObject(s);
                // root
                JSONArray root = object.getJSONArray("results");
                Log.d(TAG, "onPostExecute: " + root.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
