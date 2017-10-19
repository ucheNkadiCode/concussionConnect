package com.github.concussionconnect.Model;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.concussionconnect.R;

import java.util.ArrayList;

/**
 * Created by unkadi on 10/12/17.
 */

public class SymptomAdapter extends ArrayAdapter<SymptomModel>{
    ChecklistModel[] modelItems = null;
    Context mContext;
    ArrayList<SymptomModel> sympList;
    public SymptomAdapter(ArrayList<SymptomModel> data, Context context) {
        super(context, R.layout.symptom_slider, data);
        this.sympList = data;
        mContext = context;
    }
    private static class ViewHolder {
        TextView word;
        SeekBar valueSlider;
        EditText sympValueText;
    }
    public ArrayList<SymptomModel> getSympList() {
        return sympList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        SymptomModel symptomModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final SymptomAdapter.ViewHolder viewHolder; // view lookup cache stored in tag
        View v = convertView;
        //        final View result;

        if (convertView == null) {
            viewHolder = new SymptomAdapter.ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.symptom_slider, parent, false);

            viewHolder.word = (TextView) convertView.findViewById(R.id.sympWord);
            viewHolder.valueSlider = (SeekBar) convertView.findViewById(R.id.valueSlider);
            viewHolder.sympValueText = (EditText) convertView.findViewById(R.id.sympValueText);
            viewHolder.sympValueText.setText("0");
            viewHolder.valueSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                    int getPosition = (Integer) seekBar.getTag();  // Here we get the position that we have set for the Seekbar using setTag.
                    sympList.get(getPosition).setValue(progress); // Set the value of Actual symptom object. Adding 1 makes scale from 1 to 6
                    viewHolder.sympValueText.setText(String.valueOf(progress));
                    viewHolder.valueSlider.setProgress(progress); // As well as actual value of seekBar
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    //Nothing
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    //Nothing
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.sympWord, viewHolder.word);
            convertView.setTag(R.id.valueSlider, viewHolder.valueSlider);
        } else {
            viewHolder = (SymptomAdapter.ViewHolder) convertView.getTag();

            //result = convertView;
        }

        //using placeholder string because it is bad practice to concatenate strings inside of setText

        viewHolder.valueSlider.setTag(position); // This line is important.

        viewHolder.word.setText(sympList.get(position).getSympName());
        viewHolder.valueSlider.setProgress(sympList.get(position).getValue());
        // Return the completed view to render on screen
        return convertView;
    }
}
