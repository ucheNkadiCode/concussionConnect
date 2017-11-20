package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.github.concussionconnect.R;

public class AwaitConfirmationActivity extends Activity implements View.OnClickListener {
    private TextView textLoginHere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_await_confirmation);
        textLoginHere = (TextView) findViewById(R.id.textLoginHere);
        textLoginHere.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == textLoginHere) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
