package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.concussionconnect.Model.ConnectToDB;
import com.github.concussionconnect.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener {
    private TextView welcomeText;
    private Button testButton;
    private Button settingsButton;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = new Bundle();
        DBConnector connector = new DBConnector();
        connector.execute(null, null, null);


    }

    @Override
    public void onClick(View v) {
        if (v == testButton) {
            startActivity(new Intent(this, TestSetupActivity.class).putExtras(bundle));
        }
        if (v == settingsButton) {
            startActivity(new Intent(this, SettingsActivity.class));
        }
    }

    private class DBConnector extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            HashMap<String, Object> map = new HashMap<>();
            ArrayList<String> playerList = new ArrayList<>();
            try {
                JSONObject array = ConnectToDB.sendGetRequest("getRoster", map);
                JSONArray players = array.getJSONArray("Items");
                for (int i = 0; i < players.length(); i++) {
                    String player = (String) players.getJSONObject(i).get("nameAndDOB");
                    playerList.add(player);
                }
                bundle.putStringArrayList("roster", playerList);
                return "EXECUTED";

            } catch (Exception e) {
                Log.wtf("MyTag", e.getMessage());
                return "FAILURE";
            }
        }

        protected void onProgressUpdate(Integer... progress) {
//            setProgressPercent(progress[0]);
        }

        protected void onPostExecute(String result) {
            welcomeText = (TextView) findViewById(R.id.welcomeText);
            testButton = (Button) findViewById(R.id.testButton);
            settingsButton = (Button) findViewById(R.id.settingsButton);
            testButton.setOnClickListener(MainActivity.this);
            settingsButton.setOnClickListener(MainActivity.this);
        }
    }
}
