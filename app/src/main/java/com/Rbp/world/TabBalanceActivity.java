package com.Rbp.world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.Home_page_Response;
import com.Rbp.world.StoreSession.AppSessionManager;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class TabBalanceActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 20000;

    TextView des_mainbalance, des_user_instant_income, des_daily_income, des_total_income, des_total_Withdraw
            ,des_shopping_balance,des_team_icome_balance,des_reward_icome_balance,des_royal_income_balance,des_bonus_income_balance,
            text_des_package,des_pendingbanalg;//

    String userId_login, mainUrl, security;

    AppSessionManager sessionManager;

    String imageUrl;

    CircleImageView des_profile_pic, des_image_uplode_pic;

    CardView shadowVisible;

    HorizontalScrollView horizonScrollmoneyInfo;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_balance);
        toolbar=(Toolbar)findViewById(R.id.toolbar_balance);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        des_mainbalance = findViewById(R.id.des_mainbalance);
      //  des_daily_income = findViewById(R.id.des_daily_income);
        des_total_income = findViewById(R.id.des_total_income);
        des_total_Withdraw = findViewById(R.id.des_total_Withdraw);
       // des_shopping_balance = findViewById(R.id.des_shopping_balance);

      //  des_royal_income_balance = findViewById(R.id.des_royal_income_balance);
        des_bonus_income_balance = findViewById(R.id.des_bonus_income_balance);

       // des_user_instant_income = findViewById(R.id.des_user_instant_income);

     //   des_profile_pic = findViewById(R.id.des_profile_pic);

        //des_team_icome_balance = findViewById(R.id.des_team_icome_balance);
        //des_reward_icome_balance = findViewById(R.id.des_reward_icome_balance);

      //  des_pendingbanalg=(TextView)findViewById(R.id.des_pendingbalance);

        sessionManager = new AppSessionManager(this);
        if (sessionManager.checkLogin())
            finish();

        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        PersonalData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void PersonalData() {

        String personalUri = mainUrl + "api/app_homepages_data";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressDialog.setCancelable(false);

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

                Home_page_Response jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), Home_page_Response.class);

                progressDialog.dismiss();

                try {
                    String currency = response.getString("currency");

                    imageUrl = mainUrl + response.getString("main_picture_Url");

                    //all_pending_r
                    des_mainbalance.setText("" +response.getString("main_balance")+ " " + currency);
                    //  shadowVisible.setVisibility(View.GONE);
                    ///      horizonScrollmoneyInfo.setVisibility(View.VISIBLE);

                  //  des_user_instant_income.setText("" +response.getString("user_instant_income") + " " + currency);
                 //   des_daily_income.setText("" + response.getString("daily_income")+ " " + currency);
                    des_total_income.setText("" + response.getString("total_income") + " " + currency);
                    des_total_Withdraw.setText("" +response.getString("total_withdraw") + " " + currency);
                  //  des_team_icome_balance.setText("" +response.getString("user_team_db") + " " + currency);

                    //  des_team_icome_balance.setText("" +response.getString("user_team_db") + " " + currency);
                    //des_reward_icome_balance.setText("" +response.getString("reward_income") + " " + currency);
                    // des_total_Withdraw.setText("" +response.getString("total_withdraw") + " " + currency);

                  //  des_shopping_balance.setText("" +response.getString("main_shop_balance")+" " + currency);
                   // des_royal_income_balance.setText("" +response.getString("royal_income")+" " + currency);
                    des_bonus_income_balance.setText("" +response.getString("bonus_income")+" " + currency);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void netWorkError(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(getApplicationContext(), "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(getApplicationContext(), "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(getApplicationContext(), "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(getApplicationContext(), "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(getApplicationContext(), "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }

}