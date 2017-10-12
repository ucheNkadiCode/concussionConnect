package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.SeekBar;

import com.github.concussionconnect.Model.ChecklistAdapter;
import com.github.concussionconnect.Model.ChecklistModel;
import com.github.concussionconnect.Model.SymptomAdapter;
import com.github.concussionconnect.Model.SymptomModel;
import com.github.concussionconnect.R;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SymptomsActivity extends Activity implements View.OnClickListener {
    private ArrayList<SymptomModel> sympList;
    private Button nextButton;
    private ListView listView;
    SymptomAdapter symptomAdapter;
    Bundle bundle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptoms);
        sympList = SymptomModel.getSymptomArray(getResources().getStringArray(R.array.symptom_list));
        nextButton =  (Button) findViewById(R.id.nextButton);
        nextButton.setOnClickListener(this);
        listView = (ListView) findViewById(R.id.listView1);
        symptomAdapter = new SymptomAdapter(sympList, this);
        listView.setAdapter(symptomAdapter);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//                SymptomModel symptomModel = ((SymptomAdapter) listView.getAdapter()).getSympList().get(position);
//                SeekBar valueSlider = (SeekBar) view.findViewById(R.id.valueSlider);
//                //valueSlider.setChecked(!box.isChecked());
//            }
//        });
        bundle = getIntent().getExtras();
    }
    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            ArrayList<String> symptoms = new ArrayList<>();
            for (SymptomModel x : symptomAdapter.getSympList()) {
//                if (x.isChecked()) {
//                    symptoms.add(x.getWord());
//                }
            }
            if (symptoms.size() == 0) {
                symptoms.add("none");
            }
            bundle.putStringArrayList("symptoms", symptoms);
            startActivity(new Intent(this, WordLearnActivity.class).putExtras(bundle));
        }
    }
}
