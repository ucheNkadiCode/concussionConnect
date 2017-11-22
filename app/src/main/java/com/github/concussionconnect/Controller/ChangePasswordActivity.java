package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.GenericHandler;
import com.github.concussionconnect.Model.AWSHelper;
import com.github.concussionconnect.R;

public class ChangePasswordActivity extends Activity implements View.OnClickListener {
    private Button buttonChangePassword;
    private EditText textOldPassword;
    private EditText textNewPassword1;
    private EditText textNewPassword2;
    CognitoUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        buttonChangePassword = (Button) findViewById(R.id.buttonChangePassword);
        buttonChangePassword.setOnClickListener(this);
        textOldPassword = (EditText) findViewById(R.id.textOldPassword);
        textNewPassword1 = (EditText) findViewById(R.id.textNewPassword1);
        textNewPassword2 = (EditText) findViewById(R.id.textNewPassword2);
    }
    public void changePassword() {
        String oldPassword = textOldPassword.getText().toString().trim();
        String newPassword1 = textNewPassword1.getText().toString().trim();
        String newPassword2 = textNewPassword2.getText().toString().trim();
        if (TextUtils.isEmpty(oldPassword) || TextUtils.isEmpty(newPassword1) || TextUtils.isEmpty(newPassword2)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }
        if (oldPassword.equals(newPassword1) || oldPassword.equals(newPassword2)) {
            Toast.makeText(this, "Old and new password cannot match", Toast.LENGTH_SHORT).show();
            return;
        }
        if (newPassword1.length() < 6 || newPassword2.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password length must be 6 or greater", Toast.LENGTH_LONG).show();
            return;
        }
        if (!newPassword1.equals(newPassword2)) {
            Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        CognitoUser user = AWSHelper.getPool().getCurrentUser();
        user.changePasswordInBackground(oldPassword, newPassword1, new GenericHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Password Change Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            }

            @Override
            public void onFailure(Exception exception) {
                Log.wtf("tag", exception.getMessage());
                if (exception.getMessage().contains("NotAuthorizedException")) {
                    Toast.makeText(getApplicationContext(), "Old passworrd is incorrect", Toast.LENGTH_SHORT).show();
                } else if (exception.getMessage().contains("LimitExceededException")) {
                    Toast.makeText(getApplicationContext(), "Incorrect password limit exceeded. Try again later", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == buttonChangePassword) {
            changePassword();
        }
    }
}
