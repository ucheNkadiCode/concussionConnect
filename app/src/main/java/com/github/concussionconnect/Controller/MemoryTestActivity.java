package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

public class MemoryTestActivity extends Activity implements View.OnClickListener {
    private ArrayList<ChecklistModel> wordList;
    private Button nextButton;
    private ListView listView;
    ChecklistAdapter checklistAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_test);
        nextButton =  (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ChecklistModel checklistModel = ((ChecklistAdapter) listView.getAdapter()).getWordList().get(position);
                CheckBox box = (CheckBox) view.findViewById(R.id.checkBox1);
                box.setChecked(!box.isChecked());
            }
        });
        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        int answer = Integer.valueOf(bundle.getString("listAnswer"));
        String[] selectedWords = null;
        if (answer == 0) {
            selectedWords = getResources().getStringArray(R.array.memory_word_list_1);
        } else if (answer == 1) {
            selectedWords = getResources().getStringArray(R.array.memory_word_list_2);
        } else if (answer == 2) {
            selectedWords = getResources().getStringArray(R.array.memory_word_list_3);
        } else if (answer == 3) {
            selectedWords = getResources().getStringArray(R.array.memory_word_list_4);
        }
        wordList = ChecklistModel.getChecklistArray(selectedWords);
        checklistAdapter = new ChecklistAdapter(wordList, this);
        listView.setAdapter(checklistAdapter);

    }

    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            //Toast.makeText(getApplicationContext(), "Thanks for submitting!", Toast.LENGTH_SHORT).show();
            int total = 0;
            String output = "";
            for (ChecklistModel x : checklistAdapter.getWordList()) {
                output += "Word: " + x.getWord() + ", " + x.isChecked() + "\n";
                if (x.isChecked()) {
                    total++;
                }
            }
            int numQuestions = getResources().getStringArray(R.array.memory_word_list_1).length;
            Toast.makeText(getApplicationContext(), "Player got " + total + " out of " + numQuestions + " right", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(this, EndActivity.class));
        }
    }
}
