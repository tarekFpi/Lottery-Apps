package com.Rbp.world;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.Rbp.world.Adapter.LotteryAdapter;
import com.Rbp.world.Adapter.LotteryNameAdapter;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.LotteryNameModel;
import com.Rbp.world.Model.Lottery_model;
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

public class LotteryNameActivity extends AppCompatActivity {

    private List<LotteryNameModel> lotteryNameModelList=new ArrayList<>();
    private LotteryNameAdapter lotteryNameAdapter;

    private RecyclerView recyclerView_lotteryName;

    private String mainUrl = MainValues.URL;

    private String security,message,id,status,
            lotary_name,details,date,Image;

    private double terget_amount,total_bid,total_bid_amount;

    private ProgressDialog progressDialog;

    private LinearLayout back;

    private SharedPreferences preferences;

    private SwipeRefreshLayout swipeRefreshLayout;

    //swipe_RefreshLayout_lotName

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery_name);

        preferences = getSharedPreferences("Lottery_id", Context.MODE_PRIVATE);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_RefreshLayout_lotName);

        recyclerView_lotteryName=(RecyclerView)findViewById(R.id.recyclerview_lotteryName);
        recyclerView_lotteryName.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_lotteryName.setHasFixedSize(true);

         progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        ShowData();

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


        back = findViewById(R.id.go_back_home);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {

        Intent go_Back = new Intent(LotteryNameActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

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

                        lotteryNameModelList.clear();
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


                            LotteryNameModel lottery_model=
                                    new LotteryNameModel(id,lotary_name,details,Image,status,terget_amount,total_bid,total_bid_amount,date);

                            lotteryNameModelList.add(lottery_model);
                        }
                        lotteryNameAdapter= new LotteryNameAdapter(getApplicationContext(),lotteryNameModelList);
                        recyclerView_lotteryName.setAdapter(lotteryNameAdapter);
                        lotteryNameAdapter.notifyDataSetChanged();
                        progressDialog.dismiss();

                        lotteryNameAdapter.setOnItemClick(new LotteryNameAdapter.onItemClickLisiner() {
                            @Override
                            public void OnClick_Lisiner(int position) {

                                LotteryNameModel item=lotteryNameModelList.get(position);

                                Intent intent=new Intent(LotteryNameActivity.this,LotteryActivity.class);
                                preferences.edit().putString("id",item.getId()).commit();
                                preferences.edit().putString("Lotary_name",item.getLotary_name()).commit();
                                startActivity(intent);

                            }
                        });
                    }else{

                        progressDialog.dismiss();
                        Toast.makeText(LotteryNameActivity.this, message, Toast.LENGTH_SHORT).show();

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
            Toast.makeText(LotteryNameActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(LotteryNameActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(LotteryNameActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(LotteryNameActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(LotteryNameActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(LotteryNameActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }
}