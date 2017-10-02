package com.github.concussionconnect.Model;

/**
 * Created by unkadi on 10/2/17.
 */
import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.concussionconnect.R;

import java.util.ArrayList;

public class ChecklistAdapter extends ArrayAdapter<ChecklistModel> {
    ChecklistModel[] modelItems = null;
    Context mContext;
    ArrayList<ChecklistModel> wordList;
    public ChecklistAdapter(ArrayList<ChecklistModel> data, Context context) {
        super(context, R.layout.checklist_row, data);
        this.wordList = data;
        mContext = context;
    }
    private static class ViewHolder {
        TextView word;
        CheckBox checkBox;
    }
    public ArrayList<ChecklistModel> getWordList() {
        return wordList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        // Get the data item for this position
        ChecklistModel checklistModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        final ViewHolder viewHolder; // view lookup cache stored in tag
        View v = convertView;
        //        final View result;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.checklist_row, parent, false);

            viewHolder.word = (TextView) convertView.findViewById(R.id.textView1);
            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.checkBox1);
            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                    wordList.get(getPosition).setChecked(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
                    viewHolder.checkBox.setChecked(isChecked);
                }
            });
            convertView.setTag(viewHolder);
            convertView.setTag(R.id.textView1, viewHolder.word);
            convertView.setTag(R.id.checkBox1, viewHolder.checkBox);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();

            //result = convertView;
        }

        //using placeholder string because it is bad practice to concatenate strings inside of setText

        viewHolder.checkBox.setTag(position); // This line is important.

        viewHolder.word.setText(wordList.get(position).getWord());
        viewHolder.checkBox.setChecked(wordList.get(position).isChecked());
        // Return the completed view to render on screen
        return convertView;
    }
}
