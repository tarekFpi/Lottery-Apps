package com.Rbp.world.StoreSession;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.Rbp.world.LoginActivity;

import java.util.HashMap;

public class AppSessionManager {

    // Shared Preferences reference
    SharedPreferences pref;

    // Editor reference for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    public static final String KEY_BASEURL = "baseURL";
    // Sharedpref file name
    private static final String PREFER_NAME = "AndroidExamplePref";

    // All Shared Preferences Keys
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_SECURITY = "security";

    // Email address (make variable public to access from outside)
    public static final String KEY_USER_ID = "email";

    // password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "UserPass";

    // password (make variable public to access from outside)
    public static final String KEY_SL_ID = "User_s_uid";

    // Constructor
    public AppSessionManager(Context context) {

        this._context = context;
        pref = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    //Create login session
    public void createUserLoginSession(String security, String email, String password, String user_sl_id) {
        // Storing login value as TRUE
        editor.putBoolean(IS_USER_LOGIN, true);

        // Storing security in pref
        editor.putString(KEY_SECURITY, security);

        // Storing email in pref
        editor.putString(KEY_USER_ID, email);

        // Storing password in pref
        editor.putString(KEY_PASSWORD, password);

        // Storing USer_SL_ID in pref
        editor.putString(KEY_SL_ID, user_sl_id);

        // commit changes
        editor.commit();
    }

    public void storeBaseUrl(String str) {
        this.editor.putString(KEY_BASEURL, str);
        this.editor.commit();
    }

    public void storeSs(String str) {
        this.editor.putString(KEY_SECURITY, str);
        this.editor.commit();
    }

    /**
     * Check login method will check user login status
     * If false it will redirect user to login page
     * Else do anything
     */
    public boolean checkLogin() {
        // Check login status
        if (!this.isUserLoggedIn()) {

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);

            // Closing all the Activities from stack
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }


    /**
     * Get stored session data
     */
    public HashMap<String, String> getUserDetails() {

        //Use hashmap to store user credentials
        HashMap<String, String> user = new HashMap<String, String>();

        // user security
        user.put(KEY_SECURITY, pref.getString(KEY_SECURITY, null));

        // user user id
        user.put(KEY_USER_ID, pref.getString(KEY_USER_ID, null));

        user.put(KEY_PASSWORD, pref.getString(KEY_PASSWORD, null));

        // user sl_user id
        user.put(KEY_SL_ID, pref.getString(KEY_SL_ID, null));

        // user BASE URL
        user.put(KEY_BASEURL, pref.getString(KEY_BASEURL, null));
        // return user
        return user;
    }

    /**
     * Clear session details
     */
    public void logoutUser() {

        // Clearing all user data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Login Activity
        Intent i = new Intent(_context, LoginActivity.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }


    // Check for login
    public boolean isUserLoggedIn() {
        return this.pref.getBoolean(IS_USER_LOGIN, false);
    }
}