package com.github.concussionconnect.Controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.concussionconnect.R;

import static java.lang.String.valueOf;

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
        textLoginHere = (TextView) findViewById(R.id.textLoginHere);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        spinnerUserType = (Spinner) findViewById(R.id.spinnerUserType);
        ArrayAdapter<CharSequence> userTypeAdapter = ArrayAdapter.createFromResource(this, R.array.user_type, android.R.layout.simple_spinner_item);
        userTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerUserType.setAdapter(userTypeAdapter);
        buttonRegister.setOnClickListener(this);
        textLoginHere.setOnClickListener(this);

    }

    public void userRegister() {
        //For now, when they click register, it'll take you into the test as a trainer
//        String email = editTextEmail.getText().toString().trim();
//        String password = editTextPassword.getText().toString().trim();
//        String firstName = editTextFirstName.getText().toString().trim();
//        String lastName = editTextLastName.getText().toString().trim();
//        String userType = valueOf(spinnerUserType.getSelectedItem());

//        if (TextUtils.isEmpty(email)) {
//            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(firstName)) {
//            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(lastName)) {
//            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(userType)) {
//            Toast.makeText(this, "Please select the user type", Toast.LENGTH_LONG).show();
//            return;
//        }
//
//        if (TextUtils.isEmpty(password)) {
//            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
//            return;
//        }
//        Toast.makeText(getApplicationContext(), "User would be registered", Toast.LENGTH_LONG).show();
        finish();
        startActivity(new Intent(this, SymptomsActivity.class));

    }
    @Override
    public void onClick(View v) {
        if (v == buttonRegister) {
            userRegister();

        }

        if (v == textLoginHere) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
