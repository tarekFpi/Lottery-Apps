package com.Rbp.world;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.Rbp.world.Adapter.DailyTAskAdapter;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.PasswordChangeResponse;
import com.Rbp.world.Model.DailyTaskHistory_model;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class DailyTaskActivity extends AppCompatActivity {
    TextView timer_text, timer_score;
    WebView webfind;
    String url;
    Context context;
    AppSessionManager sessionManager;
    String userId_login, mainUrl, security;

    LinearLayout linearadsCounter, go_back_daily;

    TextView tab_text;
    CountDownTimer Count;

    RecyclerView recyclerView;

    Toolbar toolbar_teamDailyTask;

    private List<DailyTaskHistory_model> dailyTaskHistory_modelList;
    DailyTAskAdapter dailyTAskAdapter;

    protected boolean hasFinished = false;

    String finddate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_task);

        date();
        context = getApplicationContext();

        sessionManager = new AppSessionManager(this);
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        timer_text = findViewById(R.id.timer_text);
        timer_score = findViewById(R.id.timer_score);

        webfind = findViewById(R.id.webfind);

        toolbar_teamDailyTask = findViewById(R.id.toolbar_teamDailyTask);
        go_back_daily = findViewById(R.id.go_back_daily);
        tab_text = findViewById(R.id.tab_text);
       // avialable_ads = findViewById(R.id.avialable_ads);


        linearadsCounter = findViewById(R.id.linearadsCounter);
        recyclerView = findViewById(R.id.recycle);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        dailyTaskHistory_modelList = new ArrayList<>();
        dailyTAskAdapter = new DailyTAskAdapter(context, dailyTaskHistory_modelList);

        webfind.setWebViewClient(new MyBrowser());

        Intent intent = getIntent();
        String weburl = intent.getStringExtra("webUrl");
       // int ads = intent.getIntExtra("count_add",0);

     //   avialable_ads.setText("Avialable \nAds : "+ads);

        if (weburl != null) {
            url = weburl;
            webfind.getSettings().setLoadsImagesAutomatically(true);
            webfind.getSettings().setJavaScriptEnabled(true);
            webfind.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webfind.loadUrl(url);

            firstTimer();
        } else {
            linearadsCounter.setVisibility(View.GONE);
            webfind.setVisibility(View.GONE);
           // avialable_ads.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
            toolbar_teamDailyTask.setVisibility(View.VISIBLE);
            DailyTaskHistory();
        }

        tab_text.setText("Daily Task History");
        go_back_daily.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    private void firstTimer() {
        long duration = TimeUnit.SECONDS.toMillis(4);

        Count = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(millisUntilFinished)));

                timer_text.setText("Time Remaning : ");
                timer_score.setText(sDuration);
                hasFinished = true;

            }

            @Override
            public void onFinish() {

                hasFinished = true;
                DailyTaskCounter();
            }
        }.start();
    }

    private void anotherTimerStart() {
        long duration = TimeUnit.SECONDS.toMillis(2);
        Count = new CountDownTimer(duration, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                String sDuration = String.format(Locale.ENGLISH, "%02d : %02d", TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                        TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MICROSECONDS.toMinutes(millisUntilFinished)));


            }

            @Override
            public void onFinish() {

                hasFinished = true;
                DailyTaskUrl();
            }
        }.start();
    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private void DailyTaskUrl() {

        String personalUri = mainUrl + "api/app_dailytask";



        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {

                    JSONArray jsonArray = response.getJSONArray("tbl_web_link");
                    JSONObject method_list = jsonArray.getJSONObject(0);

                    String wbburl = method_list.getString("link");

                    url = wbburl;

                } catch (JSONException e) {

                    e.printStackTrace();
                }
                try {
                    String err = response.getString("error");

                    if (err.equals("available")) {
                     //   int count_add = response.getInt("count_add");
                        if (url != null) {
                            //avialable_ads.setVisibility(View.VISIBLE);
                            webfind.loadUrl(url);
                            //avialable_ads.setText("Avialable \nAds : "+count_add);
                            Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                            firstTimer();
                        }
                    }
                    if (err.equals("done")) {
                        linearadsCounter.setVisibility(View.GONE);
                        webfind.setVisibility(View.GONE);
                      //  avialable_ads.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);
                        toolbar_teamDailyTask.setVisibility(View.VISIBLE);

                        int limit = response.getInt("income_limit");


                        for (int i = 0; i < limit; i++) {
                            String date = finddate;
                            int income_Amount = 50;
                            String income_Type = "Add View Income";
                            DailyTaskHistory_model dailyTaskHistoryModel = new DailyTaskHistory_model(income_Amount, income_Type, date);
                            dailyTaskHistory_modelList.add(dailyTaskHistoryModel);
                        }
                        dailyTAskAdapter = new DailyTAskAdapter(context, dailyTaskHistory_modelList);
                        recyclerView.setAdapter(dailyTAskAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // progressDialog.dismiss();
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }


    private void DailyTaskHistory() {

        String personalUri = mainUrl + "api/app_dailytask";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    progressDialog.dismiss();
                    JSONArray jsonArray = response.getJSONArray("tbl_web_link");
                    JSONObject method_list = jsonArray.getJSONObject(0);

                    String wbburl = method_list.getString("link");
                    url = wbburl;


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }
                try {
                    String err = response.getString("error");
                    if (err.equals("available")) {
                        if (url != null) {

                            webfind.loadUrl(url);
                            Toast.makeText(context, url, Toast.LENGTH_SHORT).show();
                            firstTimer();
                        }
                    }
                    if (err.equals("done")) {
                        linearadsCounter.setVisibility(View.GONE);
                        webfind.setVisibility(View.GONE);
                     //   avialable_ads.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        toolbar_teamDailyTask.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();

                        int limit = response.getInt("income_limit");

                    
                        for (int i = 0; i < limit; i++) {
                            String date = finddate;
                            int income_Amount = 50;
                            String income_Type = "Add View Income";
                            DailyTaskHistory_model dailyTaskHistoryModel = new DailyTaskHistory_model(income_Amount, income_Type, date);
                            dailyTaskHistory_modelList.add(dailyTaskHistoryModel);
                        }
                        dailyTAskAdapter = new DailyTAskAdapter(context, dailyTaskHistory_modelList);
                        recyclerView.setAdapter(dailyTAskAdapter);

//                        try {
//                            JSONArray jsonArray = response.getJSONArray("tbl_user_get");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject transfer_withdraw_history = jsonArray.getJSONObject(i);
//
//                                int income_Amount = transfer_withdraw_history.getInt("income_Amount");
//                                String date = transfer_withdraw_history.getString("date");
//                                String income_Type = transfer_withdraw_history.getString("income_Type");
//
//                                DailyTaskHistory_model dailyTaskHistoryModel = new DailyTaskHistory_model(income_Amount, income_Type, date);
//                                dailyTaskHistory_modelList.add(dailyTaskHistoryModel);
//                            }
//                            dailyTAskAdapter=new DailyTAskAdapter(context,dailyTaskHistory_modelList);
//                            recyclerView.setAdapter(dailyTAskAdapter);
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
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
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void DailyTaskCounter() {

        String personalUri = mainUrl + "api/app_daily_task_sub";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals(" ADS Visit Income Added")) {
                    progressDialog.dismiss();
                  //  avialable_ads.setVisibility(View.GONE);

                    timer_text.setText("Work Time Completed!");
                    timer_score.setText("You Scored 0 Out of : 1");
                    anotherTimerStart();
                }
                if (jsonArray.getError().equals("done")) {

                    linearadsCounter.setVisibility(View.GONE);
                    webfind.setVisibility(View.GONE);
                   // avialable_ads.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    toolbar_teamDailyTask.setVisibility(View.VISIBLE);

                    progressDialog.dismiss();

                    int limit = 0;
                    try {
                        limit = response.getInt("income_limit");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    for (int i = 0; i < limit; i++) {
                        String date = finddate;
                        int income_Amount = 50;
                        String income_Type = "Add View Income";
                        DailyTaskHistory_model dailyTaskHistoryModel = new DailyTaskHistory_model(income_Amount, income_Type, date);
                        dailyTaskHistory_modelList.add(dailyTaskHistoryModel);
                    }
                    dailyTAskAdapter = new DailyTAskAdapter(context, dailyTaskHistory_modelList);
                    recyclerView.setAdapter(dailyTAskAdapter);

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
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

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {
        Intent go_Back = new Intent(DailyTaskActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        if (hasFinished == true) {
            Count.cancel();
        }
        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }

    public void date() {



        finddate = new SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(Calendar.getInstance().getTime());


    }

}