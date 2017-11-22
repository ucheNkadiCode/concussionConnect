package com.github.concussionconnect.Model;

/**
 * Created by unkadi on 11/19/17.
 */

import android.content.Context;

import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUser;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserDetails;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserPool;
import com.amazonaws.mobileconnectors.cognitoidentityprovider.CognitoUserSession;
import com.amazonaws.regions.Regions;

public class AWSHelper {
    // App settings
    private static String username;
    private static String password;

    private static AWSHelper appHelper;
    private static CognitoUserPool userPool;
    private static CognitoUser user;



    // Change the next three lines of code to run this demo on your user pool

    /**
     * Add your pool id here
     */
    private static final String userPoolId = "us-east-1_cK66lvQsz";
    /**
     * Add you app id
     */

    private static final String clientId = "n88vuq15n6h35phbn0s2liqvf";
    /**
     * App secret associated with your app id - if the App id does not have an associated App secret,
     * set the App secret to null.
     * e.g. clientSecret = null;
     */
    private static final String clientSecret = "100k49uql4ohhse2c64po125ar8frg3djk6evrihsnq4947t9eac";

    /**
     * Set Your User Pools region.
     * e.g. if your user pools are in US East (N Virginia) then set cognitoRegion = Regions.US_EAST_1.
     */
    private static final Regions cognitoRegion = Regions.US_EAST_1;
    // User details from the service
    private static CognitoUserSession currSession;
    private static CognitoUserDetails userDetails;

    public static void init(Context context) {
        if (appHelper != null && userPool != null) {
            return;
        }

        if (appHelper == null) {
            appHelper = new AWSHelper();
        }

        if (userPool == null) {


            userPool = new CognitoUserPool(context, userPoolId, clientId, clientSecret, cognitoRegion);

        }

    }

    public static CognitoUserPool getPool() {
        return userPool;
    }
    public static void setCurrSession(CognitoUserSession session) {
        currSession = session;
    }

    public static  CognitoUserSession getCurrSession() {
        return currSession;
    }

    public static void setUserDetails(CognitoUserDetails details) {
        userDetails = details;
    }

    public static  CognitoUserDetails getUserDetails() {
        return userDetails;
    }

    public static CognitoUser getUser() {
        return user;
    }

    public static void setUser(CognitoUser newUser) {
        user = newUser;
    }
    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        AWSHelper.username = username;
    }

}
