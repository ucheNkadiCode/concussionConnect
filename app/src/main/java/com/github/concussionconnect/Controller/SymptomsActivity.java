package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.github.concussionconnect.Model.ChecklistAdapter;
import com.github.concussionconnect.Model.ChecklistModel;
import com.github.concussionconnect.R;

import java.util.ArrayList;

public class SymptomsActivity extends Activity implements View.OnClickListener {
    private ArrayList<ChecklistModel> sympList;
    private Button nextButton;
    private ListView listView;
    ChecklistAdapter checklistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        sympList = ChecklistModel.getChecklistArray(getResources().getStringArray(R.array.symptom_list));
        nextButton =  (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
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
        if (v == nextButton) {
            String output = "";
            for (ChecklistModel x : checklistAdapter.getWordList()) {
                output += "Symptom: " + x.getWord() + ", " + x.isChecked() + "\n";
            }
            startActivity(new Intent(this, MemoryTestActivity.class).putExtras(getIntent().getExtras()));
        }
    }
}
