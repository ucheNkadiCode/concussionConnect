package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.concussionconnect.Model.ChecklistAdapter;
import com.github.concussionconnect.Model.ChecklistModel;
import com.github.concussionconnect.R;

import java.util.ArrayList;

public class SymptomsActivity extends Activity implements View.OnClickListener {
    private ArrayList<ChecklistModel> sympList;
    private Button submitButton;
    private ListView listView;
    ChecklistAdapter checklistAdapter;
    private TextView resultView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        sympList = ChecklistModel.getChecklistArray(getResources().getStringArray(R.array.symptom_list));
        submitButton =  (Button) findViewById(R.id.submitButton);
        submitButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
        resultView = (TextView) findViewById(R.id.resultView);
        checklistAdapter = new ChecklistAdapter(sympList, this);
        listView.setAdapter(checklistAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ChecklistModel checklistModel = ((ChecklistAdapter) listView.getAdapter()).getWordList().get(position);
                CheckBox box = (CheckBox) view.findViewById(R.id.checkBox1);
                box.setChecked(!box.isChecked());
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == submitButton) {
            Toast.makeText(this, "AYYYEE", Toast.LENGTH_SHORT).show();
            String output = "";
            for (ChecklistModel x : checklistAdapter.getWordList()) {
                output += "Symptom: " + x.getWord() + ", " + x.isChecked() + "\n";
            }
            resultView.setText(output);
            startActivity(new Intent(this, MemoryTestActivity.class));
        }
    }
}
