package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoDevice;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.AuthenticationDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ChallengeContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.ForgotPasswordContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.continuations.MultiFactorAuthenticationContinuation;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.AuthenticationHandler;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.ForgotPasswordHandler;
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
    private TextView textForgotPassword;
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
        textForgotPassword = (TextView) findViewById(R.id.textForgotPassword);
        buttonLogin.setOnClickListener(this);
        textRegisterHere.setOnClickListener(this);
        textForgotPassword.setOnClickListener(this);
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
        if (v == textForgotPassword) {
            //Not working yet
            //forgotPassword();
        }
    }


    private void forgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter your email");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String emailKey = input.getText().toString();
                sendCode(emailKey);
//                Toast.makeText(getApplicationContext(), "The email is " + emailKey, Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
    private void sendCode(String emailKey) {
        CognitoUser user = AWSHelper.getPool().getUser(emailKey);
        ForgotPasswordHandler handler = new ForgotPasswordHandler() {
            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "New Password Created", Toast.LENGTH_SHORT).show();
                // Forgot password process completed successfully, new password has been successfully set

            }

            @Override
            public void getResetCode(ForgotPasswordContinuation continuation) {
                // A code will be sent, use the "continuation" object to continue with the forgot password process

                // This will indicate where the code was sent
                CognitoUserCodeDeliveryDetails codeSentHere = continuation.getParameters();

                // Code to get the code from the user - user dialogs etc.

                // If the program control has to exit this method, take the "continuation" object.
                // "continuation" is the only possible way to continue with the process



                // When the code is available

                // Set the new password
                continuation.setPassword("Wrestling1");

                // Set the code to verify
                continuation.setVerificationCode("12345");

                // Let the forgot password process proceed
                continuation.continueTask();

            }

            public void onFailure(Exception e) {
                // Forgot password processing failed, probe the exception for cause
                Log.wtf("failTag", e.getMessage());
            }
        };

        user.forgotPassword(handler);
    }
    /*
    * Authenticate user's credentials and either display error message that user does not exist
    * or startActivity and allow user to enter app
    */
    private void userLogin() {

        email = editTextEmail.getText().toString().trim().toLowerCase();
        password = editTextPassword.getText().toString().trim();;
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
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

        // Create an empty user instance of this specific pool
        CognitoUser user = AWSHelper.getPool().getUser();
        //Get Session fills user object with all its details
        user.getSessionInBackground(authenticationHandler);
    }
}
