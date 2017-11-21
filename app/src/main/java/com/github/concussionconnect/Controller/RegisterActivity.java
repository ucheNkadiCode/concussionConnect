package com.github.concussionconnect.Controller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AnonymousAWSCredentials;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserAttributes;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserCodeDeliveryDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.handlers.SignUpHandler;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentityprovider.AmazonCognitoIdentityProviderClient;
import com.github.concussionconnect.Model.AWSHelper;
import com.github.concussionconnect.R;

import static java.lang.String.valueOf;

public class RegisterActivity extends Activity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPassword;
    private TextView textLoginHere;
    private Button buttonRegister;
    private String email;
    private String password;
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
        buttonRegister.setOnClickListener(this);
        textLoginHere.setOnClickListener(this);
        AWSHelper.init(this);
    }

    public void userRegister() {
        String givenName = editTextFirstName.getText().toString().trim();
        String familyName = editTextLastName.getText().toString().trim();
        email = editTextEmail.getText().toString().trim().toLowerCase();
        password = editTextPassword.getText().toString().trim();
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter your e-mail", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(givenName)) {
            Toast.makeText(this, "Please enter your first name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(familyName)) {
            Toast.makeText(this, "Please enter your last name", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_LONG).show();
            return;
        }
        if (password.length() < 6) {
            Toast.makeText(getApplicationContext(), "Password length must be 6 or greater", Toast.LENGTH_LONG).show();
            return;
        }
        CognitoUserPool userPool = AWSHelper.getPool();
        CognitoUserAttributes userAttributes = new CognitoUserAttributes();
        //primary key hashcode, Given Name, Last Name, Birthdate
        userAttributes.addAttribute("given_name", givenName);
        userAttributes.addAttribute("family_name", familyName);
        userAttributes.addAttribute("email", email);
        SignUpHandler signupCallback = new SignUpHandler() {
            @Override
            public void onSuccess(CognitoUser cognitoUser, boolean userConfirmed, CognitoUserCodeDeliveryDetails cognitoUserCodeDeliveryDetails) {
                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
                // Sign-up was successful
                // Check if this user (cognitoUser) needs to be confirmed
                AWSHelper.setUsername(email);
                AWSHelper.setPassword(password);
                AWSHelper.setUser(cognitoUser);
                if(!userConfirmed) {
                    // This user must be confirmed and a confirmation code was sent to the user
                    // cognitoUserCodeDeliveryDetails will indicate where the confirmation code was sent
                    // Get the confirmation code from user

                    startActivity(new Intent(getApplicationContext(), AwaitConfirmationActivity.class));
                }
                else {
                    // The user has already been confirmed
                    //This will never happen because the administrator will be confirming users
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            }

            @Override
            public void onFailure(Exception exception) {
                // Sign-up failed, check exception for the cause
                Log.wtf("myWTFTag", exception.getMessage());
                Toast.makeText(getApplicationContext(), "FAILURE", Toast.LENGTH_LONG).show();
            }
        };
        userPool.signUpInBackground(email, password, userAttributes, null, signupCallback);

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

//    String sha1Hash( String toHash )
//    {
//        String hash = null;
//        try
//        {
//            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
//            byte[] bytes = toHash.getBytes("UTF-8");
//            digest.update(bytes, 0, bytes.length);
//            bytes = digest.digest();
//
//            // This is ~55x faster than looping and String.formating()
//            hash = bytesToHex( bytes );
//        }
//        catch( NoSuchAlgorithmException e )
//        {
//            e.printStackTrace();
//        }
//        catch( UnsupportedEncodingException e )
//        {
//            e.printStackTrace();
//        }
//        return hash;
//    }
//
//    // http://stackoverflow.com/questions/9655181/convert-from-byte-array-to-hex-string-in-java
//    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
//    public static String bytesToHex( byte[] bytes )
//    {
//        char[] hexChars = new char[ bytes.length * 2 ];
//        for( int j = 0; j < bytes.length; j++ )
//        {
//            int v = bytes[ j ] & 0xFF;
//            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
//            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
//        }
//        return new String( hexChars );
//    }
}
