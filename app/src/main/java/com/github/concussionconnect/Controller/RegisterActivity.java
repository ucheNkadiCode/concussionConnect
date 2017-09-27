package com.github.concussionconnect.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.concussionconnect.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPassword;
    private Spinner spinnerUserType;
    private TextView textLoginHere;
    private Button buttonRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextFirstName = (EditText) findViewById(R.id.editTextFirstName);
        editTextLastName = (EditText) findViewById(R.id.editTextLastName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        textLoginHere = (TextView) findViewById(R.id.textLoginHere);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);

        buttonRegister.setOnClickListener(this);
        textLoginHere.setOnClickListener(this);
    }

    public void userRegister() {
        //For now, when they click register, it'll take you into the test as a trainer

    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            userRegister();
        }

        if (v == textLoginHere) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
