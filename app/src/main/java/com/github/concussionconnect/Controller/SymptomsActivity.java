package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.github.concussionconnect.Model.SymptomAdapter;
import com.github.concussionconnect.Model.SymptomModel;
import com.github.concussionconnect.R;

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
        bundle = getIntent().getExtras();
    }
    @Override
    public void onClick(View v) {
        if (v == nextButton) {
            ArrayList<SymptomModel> symptoms = new ArrayList<>();
            for (SymptomModel x : symptomAdapter.getSympList()) {
                symptoms.add(x);
            }

            bundle.putSerializable("symptoms", symptoms);
            startActivity(new Intent(this, WordLearnActivity.class).putExtras(bundle));
        }
    }
}
