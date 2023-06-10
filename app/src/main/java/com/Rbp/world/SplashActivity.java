package com.Rbp.world;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.CheckInternetConnection;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 20000;

    Button button;

    AppSessionManager sessionManager;

    CheckInternetConnection internetConnection;

    private String versionNumber, versionCode;

    String version;

    int versionfromPn;

    String surl;

    String mainUrl;

    Context context;
    private Handler handler;
    FirebaseAnalytics firebaseAnalytics;

    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);


        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseFirestore = FirebaseFirestore.getInstance();
        handler = new Handler();

//        button = findViewById(R.id.button);

        sessionManager = new AppSessionManager(this);

        context = getApplicationContext();


        surl = Structure.SURL;
        mainUrl = MainValues.URL;

        sessionManager.storeBaseUrl(mainUrl);

        internetConnection = new CheckInternetConnection();

        initialPermission();

    }

    private void loadContent() {
        if (internetConnection.isInternetAvailable(this)) {
//            button.setVisibility(View.VISIBLE);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {

                    AccessDevice2();

                }
            }, 1500);
            return;

        }

//        button.setVisibility(View.GONE);

        Snackbar action = Snackbar.make(getWindow().getDecorView().getRootView(), "No internet connection!", 12000).setAction("RETRY", new View.OnClickListener() {
            /* class com.lisbon.rst_world.activity.SplashScreen.AnonymousClass4 */

            public void onClick(View view) {
                SplashActivity.this.loadContent();
            }
        });
        action.setActionTextColor(SupportMenu.CATEGORY_MASK);
        ((TextView) action.getView().findViewById(R.id.snackbar_text)).setTextColor(InputDeviceCompat.SOURCE_ANY);
        action.show();

    }

    public void goToDashboard() {
        Intent i = new Intent(this, DashboardActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }

    private void initialPermission() {

        Dexter.withActivity(SplashActivity.this)
                .withPermissions("android.permission.INTERNET",
                        "android.permission.ACCESS_WIFI_STATE",
                        "android.permission.ACCESS_NETWORK_STATE",
                        "android.permission.ACCESS_COARSE_LOCATION",
                        "android.permission.ACCESS_FINE_LOCATION",
                        "android.permission.WAKE_LOCK",
                        "android.permission.READ_EXTERNAL_STORAGE",
                        "android.permission.WRITE_EXTERNAL_STORAGE",
                        "android.permission.CAMERA",
                        "android.permission.CALL_PHONE")
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        loadContent();

                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }


//    public void run(View view) {
//
//        if (!this.sessionManager.isUserLoggedIn()) {
//            startActivity(new Intent(this, LoginActivity.class));
//
//        } else {
//            goToDashboard();
//
//
//        }
//    }
private void AccessDevice2() {
    try {
        PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
        versionNumber = pInfo.versionName;

        versionCode = String.valueOf(pInfo.versionCode);

    } catch (PackageManager.NameNotFoundException e) {
        e.printStackTrace();
    }

//    Runnable runnable = new Runnable() {
//        @Override
//        public void run() {
//            handler.post(new Runnable() { // This thread runs in the UI
//                @Override
//                public void run() {
//                    //
//
//
//                  // Update the UI
//                }
//            });
//        }
//    };
//    new Thread(runnable).start();
    DocumentReference documentReference = firebaseFirestore.collection("versionControl").document("Basedata");

    documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        @Override
        public void onSuccess(DocumentSnapshot documentSnapshot) {
            version =documentSnapshot.get("version").toString();
            if (version.equals(versionNumber) ){

                if (SplashActivity.this.sessionManager.isUserLoggedIn()) {

                    goToDashboard();

                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }
            }
            if (!version.equals(versionNumber)){
                Dialog dialog = new Dialog(SplashActivity.this);
                dialog.setContentView(R.layout.version_miss_popup);
                dialog.show();
                dialog.setCancelable(false);

                dialog.findViewById(R.id.version_popup_button).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = context.getApplicationInfo().packageName;


                        try {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + id)));
                        } catch (ActivityNotFoundException unused) {
                            startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + id)));
                        }

                    }
                });
            }

        }
    });
}


    private void AccessDevice() {

        String url = mainUrl + "api/apps_version";
        JSONObject access_code = new JSONObject();

        try {
            access_code.put("security_error", surl);

            access_code.put("apps_version", versionNumber);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, access_code, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    if (response.getString("error").equals("access done")) {

                        if (SplashActivity.this.sessionManager.isUserLoggedIn()) {

                            goToDashboard();

                        } else {
                            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);

                        }

                    } else {
                        Dialog dialog = new Dialog(SplashActivity.this);
                        dialog.setContentView(R.layout.version_miss_popup);
                        dialog.show();
                        dialog.setCancelable(false);

                        dialog.findViewById(R.id.version_popup_button).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                String id = context.getApplicationInfo().packageName;
                                Toast.makeText(context, id, Toast.LENGTH_SHORT).show();

                                try {
                                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + id)));
                                } catch (ActivityNotFoundException unused) {
                                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + id)));
                                }

                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    private void networkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(context, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(context, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(context, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(context, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }


}