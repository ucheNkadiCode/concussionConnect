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
import com.github.concussionconnect.Model.SymptomModel;
import com.github.concussionconnect.R;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

public class EndActivity extends Activity implements View.OnClickListener {
    private Button cancelButton;
    private Button submitButton;
    private TextView displayResults;
    private HashMap<String, Object> map;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end);
        cancelButton = (Button) findViewById(R.id.cancelButton);
        submitButton = (Button) findViewById(R.id.submitButton);
        displayResults = (TextView) findViewById(R.id.displayResults);
        cancelButton.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        displayResults.setText("");
        bundle = getIntent().getExtras();
        map = new HashMap<>();
        map.put("PLAYERID", sha1Hash(bundle.getString("playerInfo")));
        displayResults.append("Name: " + bundle.getString("playerInfo") + "\n");
        displayResults.append("Encryption: " + sha1Hash(bundle.getString("playerInfo")) + "\n");
//        displayResults.append("List ID: " + bundle.getInt("listId") + "\n");
        ArrayList<SymptomModel> symptoms = (ArrayList<SymptomModel>) getIntent().getSerializableExtra("symptoms");
        int numSymptoms = 0;
        int severityTotal = 0;
        displayResults.append("\n");
        String symptomString = "";
        for (SymptomModel x : symptoms) {
            if (x.getValue() > 0) {
                symptomString = symptomString == "" ? x.getSympName() + ", Severity: " + x.getValue() : symptomString + ", " + x.getSympName() + ", Severity: " + x.getValue();
                numSymptoms++;
            }
            severityTotal += x.getValue();

            map.put(("SYMPTOM" + x.getSympName().toUpperCase()).replace(" ", "").replace("'", ""), x.getValue());
        }
        displayResults.append(symptomString);
        displayResults.append("\nTotal number of symptoms: " + numSymptoms + " of " + symptoms.size() + "\n");
        displayResults.append("Symptom severity score: " + severityTotal + " of " + symptoms.size() * 6);
        displayResults.append("\n\n");
        displayResults.append("Double Leg Errors: " + bundle.getInt("doubleLegErrors") +"\n");
        map.put("TRIAL1DOUBLELEGERRORS", bundle.getInt("doubleLegErrors"));
        displayResults.append("Short Term Memory Score: " + bundle.getInt("shortMemScore") +"\n");
        map.put("TRIAL2MEMORYSCORE", bundle.getInt("shortMemScore"));
        displayResults.append("Single Leg Errors: " + bundle.getInt("singleLegErrors") +"\n");
        map.put("TRIAL2SINGLELEGERRORS", bundle.getInt("singleLegErrors"));
        displayResults.append("Month Memory Score: " + bundle.getInt("monthMemScore") +"\n");
        map.put("TRIAL3MEMORYSCORE", bundle.getInt("monthMemScore"));
        displayResults.append("Tandem Leg Errors: " + bundle.getInt("tandemLegErrors") +"\n");
        map.put("TRIAL3TANDEMLEGERRORS", bundle.getInt("tandemLegErrors"));
        displayResults.append("Long Term Memory Score: " + bundle.getInt("longMemScore") +"\n");
        map.put("TRIAL4MEMORYSCORE", bundle.getInt("longMemScore"));
    }

    @Override
    public void onClick(View v) {
        if (v == cancelButton) {
            startActivity(new Intent(this, MainActivity.class));
        }
        if (v == submitButton) {
            submitButton.setEnabled(false);
            DBConnector connector = new DBConnector();
            connector.execute(null, null, null);
        }
    }
    //The way that players will be hashed. Taking in their name and birthday
    public String sha1Hash( String toHash )
    {
        String hash = null;
        try
        {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();
            hash = bytesToHex( bytes );
        }
        catch( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }
        catch( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return hash;
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex( byte[] bytes )
    {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }

    private class DBConnector extends AsyncTask<Void, Void, String> {
        protected String doInBackground(Void... params) {
            try {
                JSONObject array = ConnectToDB.sendPostRequest("postTestResults", map);
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
            if (result.equals("EXECUTED")) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }
    }
}
