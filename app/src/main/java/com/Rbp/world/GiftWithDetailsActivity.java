package com.Rbp.world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class GiftWithDetailsActivity extends AppCompatActivity {

    private SharedPreferences preferences;

    private TextView textView_details,textView_giftCount;

    private String details;

    private LinearLayout linearLayout_gift;

    private int count_lottery;

    private String mainUrl = MainValues.URL;

    private String userId_login,message;

    private AppSessionManager sessionManager;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_with_details);
        toolbar=(Toolbar)findViewById(R.id.toolbar_giftDetailsFormLoad);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        preferences = getSharedPreferences("Lottery_id", Context.MODE_PRIVATE);

        details= preferences.getString("details","details_not_found");

        textView_details=(TextView)findViewById(R.id.textLot_GiftDetails);

        textView_giftCount=(TextView)findViewById(R.id.text_GifttotalCountCart);

        linearLayout_gift=(LinearLayout)findViewById(R.id.linear_gift_addTotalCunt);

        textView_details.setText("Details:"+details);

        sessionManager = new AppSessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);


        linearLayout_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(GiftWithDetailsActivity.this,Add_toCartShowActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Lottery_Count();
    }

    private void Lottery_Count() {

        String url = mainUrl + "api/count_cart_item";

        StringRequest stringRequest1 =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);
                    message=jsonObject.getString("message");

                    if(message.contains("Data is available!")) {

                        count_lottery = jsonObject.getInt("CountCartProduct");

                        textView_giftCount.setText(""+count_lottery);

                    }else if(message.equalsIgnoreCase("Data is Not available!")){

                        Toast.makeText(GiftWithDetailsActivity.this, "Cart is Empty..!", Toast.LENGTH_SHORT).show();
                    }else{

                   Toast.makeText(GiftWithDetailsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);

            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("security_error","tec71");
                hashMap.put("u_id",userId_login);

                return hashMap;
            }
        };

        RequestQueue requestQueue1= Volley.newRequestQueue(GiftWithDetailsActivity.this);
        requestQueue1.add(stringRequest1);
    }

    private void netWorkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(GiftWithDetailsActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(GiftWithDetailsActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(GiftWithDetailsActivity .this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(GiftWithDetailsActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(GiftWithDetailsActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(GiftWithDetailsActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }
}