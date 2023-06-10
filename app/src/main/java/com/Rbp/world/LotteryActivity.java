package com.Rbp.world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.Rbp.world.Adapter.LotteryAdapter;
import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.Lottery_model;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LotteryActivity extends AppCompatActivity {

    private List<Lottery_model> lottery_models =new ArrayList<>();

    private LotteryAdapter lotteryAdapter;

    private RecyclerView recyclerView;

    private int count_lottery;

    private TextView textView_totalCount,textView_details;


    private LinearLayout linearLayout_cartShow;

    private LinearLayout back;

    private String mainUrl = MainValues.URL;

    private String security,message,id,status,code,point,date,userId_login;

    AppSessionManager sessionManager;

    private ProgressDialog progressDialog;

    private  static   String uid,Lotary_name;

    private SharedPreferences preferences;

    private Toolbar toolbar;

    private SwipeRefreshLayout swipeRefreshLayout;

    private int count_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lottery);

        toolbar=(Toolbar)findViewById(R.id.toolbar_lotterySelect);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_RefreshLayout_lotterySelect);

        preferences = getSharedPreferences("Lottery_id", Context.MODE_PRIVATE);
        Lotary_name= preferences.getString("Lotary_name","Lotary_name not Found");

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Please Wite..");

        textView_details=(TextView)findViewById(R.id.textLot_Details);
        textView_details.setText("Lottery Name: "+Lotary_name);

        sessionManager = new AppSessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);

        textView_totalCount = (TextView)findViewById(R.id.text_totalCountCart);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerview_lotteryShow);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));


        linearLayout_cartShow=(LinearLayout)findViewById(R.id.linear_Home_addTotalCunt);

        linearLayout_cartShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(LotteryActivity.this,Add_toCartShowActivity.class);
                startActivity(intent);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //ShowLottery();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {


                        ShowLottery();

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
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

    @Override
    protected void onResume() {

       ShowLottery();

        super.onResume();
    }

    void goBack() {

      /*  Intent go_Back = new Intent(LotteryActivity.this, LotteryNameActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
         R.anim.slide_out_right);*/
        finish();

    }

    private void ShowLottery(){

        String url = mainUrl + "api/lotary_tickets";

        security = Structure.SURL;

        uid= preferences.getString("id","");

         if(uid!=null){
             progressDialog.show();

             StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {

                     try {

                         JSONObject jsonObMain=new JSONObject(response);
                         message=jsonObMain.getString("message");

                         if(message.contains("Data is available!")) {

                             JSONArray jsonArray = jsonObMain.getJSONArray("lotery");

                             lottery_models.clear();

                             for (int i = 0; i <jsonArray.length() ;i++) {

                                 JSONObject jsonObject = jsonArray.getJSONObject(i);

                                 id= jsonObject.getString("id");
                                 status= jsonObject.getString("status");
                                 code= jsonObject.getString("code");
                                 point=   jsonObject.getString("point");
                                 date= jsonObject.getString("date");

                                 Lottery_model lottery_model= new Lottery_model(id,code,status,point,date);

                                 lottery_models.add(lottery_model);
                             }
                             lotteryAdapter= new LotteryAdapter(lottery_models,getApplicationContext());
                             recyclerView.setAdapter(lotteryAdapter);
                             lotteryAdapter.notifyDataSetChanged();

                             textView_details.setVisibility(View.VISIBLE);
                             progressDialog.dismiss();

                             lotteryAdapter.setOnItemClick(new LotteryAdapter.onItemClickLisiner() {
                                 @Override
                                 public void OnClick_Lisiner(int position) {

                                     Lottery_model item_positon=lottery_models.get(position);

                                     AddToCart(item_positon.getId());
                                 }
                             });

                         }else {

                             progressDialog.dismiss();

                            // textView_details.setVisibility(View.GONE);
                             Toast.makeText(LotteryActivity.this, message, Toast.LENGTH_SHORT).show();
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
                     hashMap.put("security_error",security);
                     hashMap.put("lottary_id",uid);
                     return hashMap;
                 }
             };

             RequestQueue requestQueue= Volley.newRequestQueue(this);
             requestQueue.add(stringRequest);
         }

    }



    private void AddToCart(String cartId){

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        String url = mainUrl + "api/add_to_cart";

        security = Structure.SURL;

      StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {

              try {

                  JSONObject jsonObject=new JSONObject(response);
                  message=jsonObject.getString("message");

                  if(message.contains("Lotary Added To Cart Successfully!")) {

                 Toast.makeText(LotteryActivity.this, message, Toast.LENGTH_SHORT).show();

                      progressDialog.dismiss();

                      Lottery_Count();

                      Notification_Add(message);

                  }else{

                      progressDialog.dismiss();
                      Toast.makeText(LotteryActivity.this, message, Toast.LENGTH_SHORT).show();
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
              hashMap.put("security_error",security);
              hashMap.put("l_id",cartId);
              hashMap.put("u_id",userId_login);

              return hashMap;
          }
      };

        RequestQueue requestQueue= Volley.newRequestQueue(LotteryActivity.this);
        requestQueue.add(stringRequest);
    }


    private void Notification_Add(String message){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(getApplicationContext(),"Channel")
                .setContentTitle("Lottery")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(message)
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1,notification.build());

    }

    private void Lottery_Count() {

        String url = mainUrl + "api/count_cart_item";

        security = Structure.SURL;

        StringRequest stringRequest1 =new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);
                    message=jsonObject.getString("message");

                    if(message.contains("Data is available!")) {

                      count_lottery = jsonObject.getInt("CountCartProduct");

                     textView_totalCount.setText(""+count_lottery);

                    }else if(message.equalsIgnoreCase("Data is Not available!")){
                        progressDialog.dismiss();
                        Toast.makeText(LotteryActivity.this, "Cart is Empty..!", Toast.LENGTH_SHORT).show();
                    }else{

                     //   System.out.println("status:"+message);
                        progressDialog.dismiss();
                        Toast.makeText(LotteryActivity.this, message, Toast.LENGTH_SHORT).show();
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
                hashMap.put("security_error",security);
                hashMap.put("u_id",userId_login);

                return hashMap;
            }
        };

        RequestQueue requestQueue1= Volley.newRequestQueue(LotteryActivity.this);
        requestQueue1.add(stringRequest1);
    }

    private void netWorkError(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(LotteryActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(LotteryActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(LotteryActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(LotteryActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(LotteryActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(LotteryActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }

    public void ShowCart(View view) {
        Intent intent=new Intent(LotteryActivity.this,Add_toCartShowActivity.class);
        startActivity(intent);

    }
}