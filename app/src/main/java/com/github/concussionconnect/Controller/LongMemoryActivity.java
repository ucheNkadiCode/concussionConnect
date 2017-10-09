package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.github.concussionconnect.Model.ChecklistAdapter;
import com.github.concussionconnect.Model.ChecklistModel;
import com.github.concussionconnect.R;

import java.util.ArrayList;

public class LongMemoryActivity extends Activity implements View.OnClickListener {
    private ArrayList<ChecklistModel> wordList;
    private Button nextButton;
    private ListView listView;
    ChecklistAdapter checklistAdapter;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_long_memory);
        nextButton =  (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                CheckBox box = (CheckBox) view.findViewById(R.id.checkBox1);
                box.setChecked(!box.isChecked());
            }
        });
        bundle = getIntent().getExtras();
        int id = bundle.getInt("listId");
        String[] selectedWords = getResources().getStringArray(id);
        wordList = ChecklistModel.getChecklistArray(selectedWords);
        checklistAdapter = new ChecklistAdapter(wordList, this);
        listView.setAdapter(checklistAdapter);
    }
    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            int total = 0;
            for (ChecklistModel x : checklistAdapter.getWordList()) {
                if (x.isChecked()) {
                    total++;
                }
            }
            bundle.putInt("longMemScore", total);
            startActivity(new Intent(this, EndActivity.class).putExtras(bundle));
        }
    }
}
