package com.Rbp.world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Toast;

import com.Rbp.world.Adapter.LotteryGiftAdapter;
import com.Rbp.world.Adapter.LotteryNameAdapter;
import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.LotteryGiftModel;
import com.Rbp.world.Model.LotteryNameModel;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryGiftActivity extends AppCompatActivity {

 private RecyclerView recyclerView_lottery;

 private LotteryGiftAdapter adapter;

  private List<LotteryGiftModel> modelList=new ArrayList<>();



    private String mainUrl = MainValues.URL;

    private String security,message,id,status,
            lotary_name,details,date,Image;

    private double terget_amount,total_bid,total_bid_amount;

    private ProgressDialog progressDialog;

    private SharedPreferences preferences;

    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_gift);
        toolbar=(Toolbar)findViewById(R.id.toolbar_giftFormLoad);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_RefreshLayout);

        preferences = getSharedPreferences("Lottery_id", Context.MODE_PRIVATE);

        recyclerView_lottery=(RecyclerView)findViewById(R.id.recyclerView_lottGift);
        recyclerView_lottery.setHasFixedSize(true);
        recyclerView_lottery.setLayoutManager(new LinearLayoutManager(this));

        progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                      ShowData();

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        ShowData();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    private  void ShowData(){

        String url = mainUrl + "api/lotary_name_stra";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObMain=new JSONObject(response);
                    message=jsonObMain.getString("message");

                    if(message.equalsIgnoreCase("Data is available!")){
                        JSONArray jsonArray = jsonObMain.getJSONArray("lotery");

                        modelList.clear();
                        for (int i = 0; i <jsonArray.length() ;i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            id= jsonObject.getString("id");
                            status= jsonObject.getString("status");
                            lotary_name= jsonObject.getString("lotary_name");
                            details= jsonObject.getString("details");
                            Image="https://1xluck24.com/login/public/"+jsonObject.getString("logo");

                            total_bid_amount= jsonObject.getDouble("total_bid_amount");
                            terget_amount= jsonObject.getDouble("terget_amount");
                            total_bid= jsonObject.getDouble("total_bid");
                            date= jsonObject.getString("create_date");


                            LotteryGiftModel lottery_model=
                               new LotteryGiftModel(id,lotary_name,details,Image,status,terget_amount,total_bid,total_bid_amount,date);

                            modelList.add(lottery_model);
                        }
                        adapter= new LotteryGiftAdapter(modelList,getApplicationContext());
                        recyclerView_lottery.setAdapter(adapter);
                       adapter.notifyDataSetChanged();

                        progressDialog.dismiss();

                        adapter.setOnItemClick(new LotteryGiftAdapter.onItemClickLisiner() {
                            @Override
                            public void OnClick_Lisiner(int position) {

                               LotteryGiftModel item=modelList.get(position);

                                Intent intent=new Intent(LotteryGiftActivity.this,GiftWithDetailsActivity.class);
                                preferences.edit().putString("id",item.getId()).commit();
                                preferences.edit().putString("details",item.getDetails()).commit();
                                startActivity(intent);
                            }
                        });
                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(LotteryGiftActivity.this, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);
                progressDialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("security_error","tec71");

                return hashMap;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void netWorkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(LotteryGiftActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(LotteryGiftActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(LotteryGiftActivity .this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(LotteryGiftActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(LotteryGiftActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(LotteryGiftActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }
}