package com.Rbp.world;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;

import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.Rbp.world.Helpers.CheckInternetConnection;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.PTeamResponse;
import com.Rbp.world.JsonResponse.RegisterResponse;
import com.Rbp.world.JsonResponse.SponsorUserResponse;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class RegistrationActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS =20000;
    private Context context;

    private static final String TAG = "MyTAg";
    Spinner placement_user_type,package_type_spinner, country_side_spinner;

    int package_value;

    CheckInternetConnection internetConnection;

    EditText fullname, userId, mobile, sponsor_user_id, placement_user_id, register_pass, register_c_pass,et_register_UserEmail;
    EditText team_name,editText_nid;
    String str_team_name, message;

    String str_name, str_userId, str_mobile, str_joiningCode, str_sponsorUserId, str_placementUserId,
            str_placementUserType, s_email;

    Button register_btn;

    String country = null;

    int itemposition;

    AppSessionManager sessionManager;

    TextView check_userId_exist, tv_sponsor_user_id, tv_placement_user_id, tv_placement_user_team;

    TextView tab_text;
    LinearLayout back;

    CardView reg_from_Card;

    public static String uriImage="";


    private String s_check_userId_exist, s_sponsor_user_id, s_placement_user_id, s_placement_user_team;

    private String userId_login, security;

    private String mainUrl = MainValues.URL;

   private FloatingActionButton floatingActionButton;

    private ImageView imageViewScanner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        internetConnection = new CheckInternetConnection();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.choiceColor));
        }


        floatingActionButton=(FloatingActionButton) findViewById(R.id.floating_scanner);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent intent=new Intent(RegistrationActivity.this,ScannerViewActivity.class);
              startActivity(intent);

            }
        });

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        context = getApplicationContext();

        sessionManager = new AppSessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetails();
     //  userId_login = user.get(AppSessionManager.KEY_SL_ID);
       // mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        //security=user.get(AppSessionManager.KEY_SECURITY);


        FindView();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        placement_user_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_position = String.valueOf(position);
                itemposition = Integer.parseInt(item_position);
                if (!placement_user_id.getText().toString().equals("")) {

                    if (itemposition !=0){
                        checkPlacementTeam(itemposition);
                    }else {
                        tv_placement_user_team.setVisibility(View.GONE);
                        Toast.makeText(context, "Please,Selected your team", Toast.LENGTH_SHORT).show();
                    }

                } else {


                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/* package_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item_position = String.valueOf(position);
                itemposition = Integer.parseInt(item_position);

                if (!userId.getText().toString().equals("")) {
                         if (itemposition !=0){
                   if (itemposition == 1){
                       package_value=4500;
                   } if (itemposition == 2){
                       package_value=7000;
                   } if (itemposition == 3){
                       package_value=13000;
                   } if (itemposition == 4){
                       package_value=25000;
                   }
                }else {
                    package_value=0;
                    Toast.makeText(context, "Please,Selected your Package", Toast.LENGTH_SHORT).show();
                }
                }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
*/

        country_side_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                country = parent.getItemAtPosition(position).toString();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Registerhere();
            }
        });

        userId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(result)) {
                    userId.setText(result);
                    userId.setSelection(result.length());
                    // alert the user
                }
                if (editable.length() > 0 && !editable.toString().equals("0")) {
                    String user_id = userId.getText().toString();

                    checkExistingUser(editable.toString());


                } else {
                    check_userId_exist.setText("");
                }


            }
        });

        sponsor_user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                String result = editable.toString().replaceAll(" ", "");


                if (!editable.toString().equals(result)) {
                    sponsor_user_id.setText(result);
                    sponsor_user_id.setSelection(result.length());
                    // alert the user
                }

                if (editable.length() > 0 && !editable.toString().equals("0")) {
                    String sponsorId = sponsor_user_id.getText().toString();

                    checkSponsorId(sponsorId);



                } else {
                    tv_sponsor_user_id.setVisibility(View.GONE);
                }

            }
        });

        placement_user_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(result)) {
                    placement_user_id.setText(result);
                    placement_user_id.setSelection(result.length());
                    // alert the user
                }

                if (editable.length() > 0 && !editable.toString().equals("0")) {
                    String placementId = sponsor_user_id.getText().toString();
                    checkPlacementID(placementId);


                } else {
                    tv_placement_user_id.setVisibility(View.GONE);
                    tv_placement_user_team.setVisibility(View.GONE);
                    register_btn.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
                    register_btn.setClickable(false);
                    register_btn.setEnabled(false);
                }

            }
        });

        loadContent();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(!uriImage.equals("")){

        Picasso.get().load(uriImage).into(imageViewScanner);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadContent();
    }

    private void FindView() {

      imageViewScanner=(ImageView)findViewById(R.id.imageView_Scanner);

        tab_text = findViewById(R.id.tab_text);
        back = findViewById(R.id.go_back_reg);

        tab_text.setText("Register");

        fullname = findViewById(R.id.fullname);
        userId = findViewById(R.id.userId);
        mobile = findViewById(R.id.mobile);
   //     joining_code = findViewById(R.id.joining_code);
        sponsor_user_id = findViewById(R.id.sponsor_user_id);
        placement_user_id = findViewById(R.id.placement_user_id);

        team_name = findViewById(R.id.team_name);

        editText_nid=(EditText)findViewById(R.id.edit_NID);

        reg_from_Card = findViewById(R.id.reg_from_Card);

        placement_user_type = findViewById(R.id.placement_side_spinner);
        package_type_spinner = findViewById(R.id.package_name_side_spinner);

        register_btn = findViewById(R.id.reg_register_btn);
        register_btn.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        register_btn.setClickable(false);


        check_userId_exist = findViewById(R.id.check_userId);
      //  tv_joining_code = findViewById(R.id.tv_joining_code);
        tv_sponsor_user_id = findViewById(R.id.tv_sponsor_user_id);
        tv_placement_user_id = findViewById(R.id.tv_placement_user_id);
        tv_placement_user_team = findViewById(R.id.tv_placement_user_team);


        register_pass = findViewById(R.id.register_pass);
        register_c_pass = findViewById(R.id.register_c_pass);

        et_register_UserEmail = findViewById(R.id.et_register_UserEmail);

        country_side_spinner = findViewById(R.id.country_side_spinner);

    }

    private void loadContent() {
        if (internetConnection.isInternetAvailable(this)) {
            reg_from_Card.setVisibility(View.VISIBLE);
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {


                }
            }, 1500);
            return;

        }

        reg_from_Card.setVisibility(View.GONE);

        Snackbar action = Snackbar.make(getWindow().getDecorView().getRootView(), "No internet connection!", 12000).setAction("RETRY", new View.OnClickListener() {
            /* class com.lisbon.rst_world.activity.SplashScreen.AnonymousClass4 */

            public void onClick(View view) {
                RegistrationActivity.this.loadContent();
            }
        });
        action.setActionTextColor(SupportMenu.CATEGORY_MASK);
        ((TextView) action.getView().findViewById(R.id.snackbar_text)).setTextColor(InputDeviceCompat.SOURCE_ANY);
        action.show();

    }


    private void checkPlacementTeam(int itemposition) {

        String url5 = mainUrl + "api/apps_error_selected_n_chack";


        str_name = fullname.getText().toString().trim();
        str_userId = userId.getText().toString().replace(" ", "");
        str_mobile = mobile.getText().toString().trim();
     //   str_joiningCode = joining_code.getText().toString().trim();
        str_sponsorUserId = sponsor_user_id.getText().toString().trim();
        str_placementUserId = placement_user_id.getText().toString().trim();

        str_placementUserType = String.valueOf(itemposition);

        JSONObject reg_data = new JSONObject();

        try {

            reg_data.put("placement_u_id", str_placementUserId);
            reg_data.put("selected_n", str_placementUserType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url5, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                PTeamResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PTeamResponse.class);

                if (jsonArray.getError().equalsIgnoreCase("Available")) {


                    s_placement_user_team = String.valueOf(true);


                    String ab = jsonArray.getError();

                    String full = ab;

                    tv_placement_user_team.setVisibility(View.VISIBLE);
                    tv_placement_user_team.setText(full);
                    tv_placement_user_team.setTextColor(Color.parseColor("#009432"));

                   } else if (jsonArray.getError().equalsIgnoreCase("Not Available")) {
                    //process your steps if the value is success

                    s_placement_user_team = String.valueOf(false);

                    String ab = jsonArray.getError();

                    String full = ab;

                    tv_placement_user_team.setVisibility(View.VISIBLE);
                    tv_placement_user_team.setText(full);
                    tv_placement_user_team.setTextColor(Color.parseColor("#EA2027"));


                    // Toast.makeText(RegistrationActivity.this, full, Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(RegistrationActivity.this, jsonArray.getError(), Toast.LENGTH_SHORT).show();
                }

                if (!TextUtils.isEmpty(s_check_userId_exist)  && !TextUtils.isEmpty(s_sponsor_user_id)) {

                    registerButtonAvailable(s_check_userId_exist, s_sponsor_user_id);
                } else {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
                Toast.makeText(RegistrationActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }


    //check sponsor id
    private void checkPlacementID(String placementId) {
        String url4 = mainUrl + "api/apps_placement_u_id_chack";


        str_name = fullname.getText().toString().trim();
        str_userId = userId.getText().toString().replace(" ", "");
        str_mobile = mobile.getText().toString().trim();
    //    str_joiningCode = joining_code.getText().toString().trim();
        str_sponsorUserId = sponsor_user_id.getText().toString().trim();
        str_placementUserId = placement_user_id.getText().toString().trim();

        JSONObject reg_data = new JSONObject();

        try {

            reg_data.put("placement_u_id", str_placementUserId);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url4, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                SponsorUserResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), SponsorUserResponse.class);

                if (jsonArray.getError().equalsIgnoreCase("A - Available")) {
                    //process your steps if the value is success

                    s_placement_user_id = String.valueOf(true);
                    String name = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + name;

                    tv_placement_user_id.setVisibility(View.VISIBLE);
                    tv_placement_user_id.setText(full);
                    tv_placement_user_id.setTextColor(Color.parseColor("#009432"));


                    placement_user_type.setEnabled(true);

                    Toast.makeText(RegistrationActivity.this, full, Toast.LENGTH_SHORT).show();

                } else if (jsonArray.getError().equalsIgnoreCase("B - Available")) {
                    //process your steps if the value is success
                    s_placement_user_id = String.valueOf(true);
                    String name = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + name;

                    tv_placement_user_id.setVisibility(View.VISIBLE);
                    tv_placement_user_id.setText(full);
                    tv_placement_user_id.setTextColor(Color.parseColor("#009432"));

                    placement_user_type.setEnabled(true);
                    Toast.makeText(RegistrationActivity.this, full, Toast.LENGTH_SHORT).show();

                } else if (jsonArray.getError().equalsIgnoreCase("C - Available")) {
                    //process your steps if the value is success
                    s_placement_user_id = String.valueOf(true);

                    String name = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + name;

                    tv_placement_user_id.setVisibility(View.VISIBLE);
                    tv_placement_user_id.setText(full);
                    tv_placement_user_id.setTextColor(Color.parseColor("#009432"));

                    placement_user_type.setEnabled(true);
                    Toast.makeText(RegistrationActivity.this, full, Toast.LENGTH_SHORT).show();

                } else if (jsonArray.getError().equalsIgnoreCase("invalid Placement")) {
                    s_placement_user_id = String.valueOf(false);

                    String point = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + point;

                    tv_placement_user_id.setVisibility(View.VISIBLE);
                    tv_placement_user_id.setText(full);
                    tv_placement_user_id.setTextColor(Color.parseColor("#EA2027"));
                    tv_placement_user_team.setVisibility(View.GONE);
                    placement_user_type.setEnabled(false);

                    Toast.makeText(RegistrationActivity.this, jsonArray.getError(), Toast.LENGTH_SHORT).show();

                } else {

                }

                if (!TextUtils.isEmpty(s_check_userId_exist)  && !TextUtils.isEmpty(s_sponsor_user_id)) {

                    registerButtonAvailable(s_check_userId_exist, s_sponsor_user_id);
                } else {

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
              //  Toast.makeText(RegistrationActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }

    //check sponsor id
    private void checkSponsorId(String toString) {

        String url3 = mainUrl + "api/apps_sponser_u_id_chack";


        str_sponsorUserId = sponsor_user_id.getText().toString().trim();


        JSONObject reg_data = new JSONObject();

        try {

       reg_data.put("sponser_u_id", str_sponsorUserId);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url3, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                SponsorUserResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), SponsorUserResponse.class);

                if (jsonArray.getError().equalsIgnoreCase("Sponser ID Available")) {
                    //process your steps if the value is success
                    s_sponsor_user_id = String.valueOf(true);
                    String name = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + name;

                    tv_sponsor_user_id.setVisibility(View.VISIBLE);
                    tv_sponsor_user_id.setText(full);
                    tv_sponsor_user_id.setTextColor(Color.parseColor("#009432"));


                    //   Toast.makeText(RegistrationActivity.this, full, Toast.LENGTH_SHORT).show();

                } else if (jsonArray.getError().equalsIgnoreCase("invalid Sponser ID")) {

                    s_sponsor_user_id = String.valueOf(false);

                    String point = jsonArray.getName();
                    String ab = jsonArray.getError();

                    String full = ab + " ," + point;

                    tv_sponsor_user_id.setVisibility(View.VISIBLE);
                    tv_sponsor_user_id.setText(full);
                    tv_sponsor_user_id.setTextColor(Color.parseColor("#EA2027"));

                    // Toast.makeText(RegistrationActivity.this, jsonArray.getError(), Toast.LENGTH_SHORT).show();

                } else {

                }
                if (!TextUtils.isEmpty(s_check_userId_exist)  && !TextUtils.isEmpty(s_sponsor_user_id) ) {

                    registerButtonAvailable(s_check_userId_exist, s_sponsor_user_id);
                } else {

                }
                // registerButtonAvailable(s_check_userId_exist, s_joining_code, s_sponsor_user_id, s_placement_user_id, s_placement_user_team);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
               // Toast.makeText(RegistrationActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonObjectRequest);


    }



    //Check User Id
    private void checkExistingUser(String toString) {


        String url2 = mainUrl + "api/apps_username_chack";


        str_name = fullname.getText().toString().trim();
        str_userId = userId.getText().toString().replace(" ", "");



        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", "tec71");

            reg_data.put("username", str_userId);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url2, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                RegisterResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), RegisterResponse.class);

                if (jsonArray.getError().equalsIgnoreCase("Username Available")) {
                    //process your steps if the value is success

                    s_check_userId_exist = String.valueOf(true);

                    check_userId_exist.setText("OK");
                    check_userId_exist.setTextColor(Color.parseColor("#009432"));


//                        Intent intent = new Intent(RegistrationActivity.this, DashboardActivity.class);
//                        startActivity(intent);
                    // Toast.makeText(RegistrationActivity.this, s_check_userId_exist, Toast.LENGTH_LONG).show();


                } else if (jsonArray.getError().equalsIgnoreCase("username already exist")) {

                    s_check_userId_exist = String.valueOf(false);

                    check_userId_exist.setText("try another");
                    check_userId_exist.setTextColor(Color.parseColor("#EA2027"));

                } else {

                    check_userId_exist.setText("...");
                }
                if (!TextUtils.isEmpty(s_check_userId_exist)  && !TextUtils.isEmpty(s_sponsor_user_id)) {

                    registerButtonAvailable(s_check_userId_exist, s_sponsor_user_id);
                } else {

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



    private void Registerhere() {

        String url = mainUrl + "api/apps_registration";


        security = Structure.SURL;

        str_placementUserType = String.valueOf(itemposition);

        String pin = register_pass.getText().toString().trim();
        String cpin = register_c_pass.getText().toString().trim();

        String nid= editText_nid.getText().toString().trim();

        if (TextUtils.isEmpty(fullname.getText().toString())) {

       Toast.makeText(this, "Enter Full Name", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(editText_nid.getText().toString())) {

        Toast.makeText(this, "Enter NID Number Empty!!", Toast.LENGTH_SHORT).show();
            editText_nid.requestFocus();
            editText_nid.setError("Enter NID Number Empty!!");
        } else if (userId.getText().toString().equals("")) {
            Toast.makeText(this, "Enter UserId", Toast.LENGTH_SHORT).show();
        } else if (et_register_UserEmail.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Email Address", Toast.LENGTH_SHORT).show();
        } else if (mobile.getText().toString().equals("")) {
            Toast.makeText(this, "Enter Mobile", Toast.LENGTH_SHORT).show();
        }  else if (sponsor_user_id.getText().toString().equals("")) {
            Toast.makeText(this, "EnterSponsor User", Toast.LENGTH_SHORT).show();
        }/* else if (package_value==0) {
            Toast.makeText(this, "Enter Package Type", Toast.LENGTH_SHORT).show();
        }*/ else if (TextUtils.isEmpty(pin)) {
            Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(cpin)) {
            Toast.makeText(this, "Enter Confirm Password", Toast.LENGTH_SHORT).show();
        } else if (!pin.toString().equals(cpin)) {
            Toast.makeText(this, "Confirm password not Match to your new Password", Toast.LENGTH_SHORT).show();

        }else if (country.equals("Select your Country")) {
            Toast.makeText(this, "Select your Country", Toast.LENGTH_SHORT).show();
        } else {


            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progrss_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            str_name = fullname.getText().toString().trim();
             str_userId = userId.getText().toString();
             str_mobile = mobile.getText().toString().trim();

            str_sponsorUserId = sponsor_user_id.getText().toString().trim();
            str_placementUserId = placement_user_id.getText().toString().trim();



            str_team_name = team_name.getText().toString().trim();

            s_email = et_register_UserEmail.getText().toString().trim();



            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject=new JSONObject(response);

                        message=jsonObject.getString("error");

                        if(message.equalsIgnoreCase("Registration Successful ")){

                            progressDialog.dismiss();

                             fullname.setText("");
                             userId.setText("");
                             mobile.setText("");
                            team_name.setText("");
                            et_register_UserEmail.setText("");
                            register_pass.setText("");
                            register_c_pass.setText("");
                            editText_nid.setText("");

                            Notification_Add(message);

                           // String sl_user_id = jsonObject.getString("user_id");
                      Toast.makeText(RegistrationActivity.this,  message, Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(RegistrationActivity.this,  message, Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();
                    networkError(error);

                    // reg_data.put("package_name", String.valueOf(package_value));
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> reg_data = new HashMap<String, String>();
                    reg_data.put("security_error", security);
                    reg_data.put("user_id_u", str_userId);
                    reg_data.put("full_name", str_name);
                    reg_data.put("user_Name", str_userId);
                    reg_data.put("mobile_number", str_mobile);
                    reg_data.put("sponser_u_id", str_sponsorUserId);
                    reg_data.put("nid_card", nid);
                    reg_data.put("password", pin);
                    reg_data.put("password_conform", cpin);
                    reg_data.put("email", s_email);
                    reg_data.put("country", country);

                    return reg_data;
                }
            };

            RequestQueue requestQueue= Volley.newRequestQueue(RegistrationActivity.this);
            requestQueue.add(stringRequest);

        }


    }

    private void registerButtonAvailable(String s_check_userId_exist, String s_sponsor_user_id) {

        // !s_check_userId_exist.equals("true") || !s_joining_code.equals("true") || !s_sponsor_user_id.equals("true") || !s_placement_user_id.equals("true") || !s_placement_user_team.equals("true")
        if (s_check_userId_exist.equals("true")  && s_sponsor_user_id.equals("true") ) {

            register_btn.setClickable(true);
            register_btn.setEnabled(true);
            register_btn.getBackground().setColorFilter(null);

        } else {
            register_btn.getBackground().setColorFilter(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
            register_btn.setClickable(false);
            register_btn.setEnabled(false);

        }

    }

    void goBack() {
        Intent go_Back = new Intent(RegistrationActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }

    private void networkError(VolleyError error) {
        if (error instanceof NetworkError) {
            loadContent();
            Toast.makeText(RegistrationActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(RegistrationActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(RegistrationActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(RegistrationActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(RegistrationActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(RegistrationActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }

    private void Notification_Add(String message){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(getApplicationContext(),"Channel")
                .setContentTitle("Registration")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(message)
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1,notification.build());

    }

}