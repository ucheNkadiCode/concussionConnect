package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.services.cognitoidentityprovider.model.NotAuthorizedException;
import com.amazonaws.services.cognitoidentityprovider.model.UserNotFoundException;
import com.github.concussionconnect.Model.AWSHelper;
import com.github.concussionconnect.R;

/**
 * Created by unkadi on 9/27/17.
 */

public class LoginActivity extends Activity implements View.OnClickListener {
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonLogin;
    private TextView textRegisterHere;
    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);
        buttonLogin = (Button) findViewById(R.id.buttonLogin);
        textRegisterHere = (TextView) findViewById(R.id.textRegisterHere);

        buttonLogin.setOnClickListener(this);
        textRegisterHere.setOnClickListener(this);
        AWSHelper.init(this);
    }
    @Override
    public void onClick(View v) {
        if (v == buttonLogin) {
            userLogin();
        }

        if (v == textRegisterHere) {
            startActivity(new Intent(this, RegisterActivity.class));
        }
    }

    private void userLogin() {
        //Authenticate user's credentials and either display error message that user does not exist
        //or startActivity and allow user to enter app.
        email = editTextEmail.getText().toString().trim().toLowerCase();
        password = editTextPassword.getText().toString().trim();;
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            // Password is empty
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();

            // Stop the function from executing further
            return;
        } else if (password.length() < 6) {
            Toast.makeText(this, "Password must be 6 characters or more", Toast.LENGTH_LONG).show();
            return;
        }

//        Callback handler for the sign-in process
        AuthenticationHandler authenticationHandler = new AuthenticationHandler() {

            @Override
            public void onSuccess(CognitoUserSession cognitoUserSession, CognitoDevice device) {
                // Sign-in was successful, cognitoUserSession will contain tokens for the user
                Toast.makeText(getApplicationContext(), "You logged in!", Toast.LENGTH_LONG).show();
                AWSHelper.setCurrSession(cognitoUserSession);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

            @Override
            public void getAuthenticationDetails(AuthenticationContinuation authenticationContinuation, String userId) {
                // The API needs user sign-in credentials to continue
                AWSHelper.setUsername(email);
                AWSHelper.setPassword(password);
                AuthenticationDetails authenticationDetails = new AuthenticationDetails(email, password, null);

                // Pass the user sign-in credentials to the continuation
                authenticationContinuation.setAuthenticationDetails(authenticationDetails);

                // Allow the sign-in to continue
                authenticationContinuation.continueTask();
            }

            @Override
            public void getMFACode(MultiFactorAuthenticationContinuation multiFactorAuthenticationContinuation) {
//                This app will not be using MFA right now...
//                Multi-factor authentication is required; get the verification code from user
//                multiFactorAuthenticationContinuation.setMfaCode(mfaVerificationCode);
//                Allow the sign-in process to continue
//                multiFactorAuthenticationContinuation.continueTask();
            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-in failed, check exception for the cause
                if (exception.getMessage().contains("UserNotFoundException")) {
                    Toast.makeText(getApplicationContext(), "Account was not found", Toast.LENGTH_SHORT).show();
                } else if (exception.getMessage().contains("NotAuthorizedException")) {
                    Toast.makeText(getApplicationContext(), "Incorrect Password", Toast.LENGTH_SHORT).show();
                } else if (exception.getMessage().contains("UserNotConfirmedException")) {
                    Toast.makeText(getApplicationContext(), "Your account is not yet confirmed", Toast.LENGTH_SHORT).show();
                } else {
                    Log.wtf("what", exception.getMessage());
                }

            }
            @Override
            public void authenticationChallenge(ChallengeContinuation challenge) {
                //Nothing
            }
        };

        // Sign in the user
        CognitoUser user = AWSHelper.getPool().getUser();
        //this creates user object with details
        user.getSessionInBackground(authenticationHandler);
        AWSHelper.setUser(user);
    }
}
