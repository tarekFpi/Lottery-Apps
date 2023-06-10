package com.Rbp.world;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.Rbp.world.Adapter.LotteryAdapter;
import com.Rbp.world.Adapter.LotteryCartShowAdapter;
import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.LotteryCartShow;
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

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class Add_toCartShowActivity extends AppCompatActivity {

    private RecyclerView recyclerView_addCart;

    private List<LotteryCartShow> lotteryCartShowList = new ArrayList<>();

    private LotteryCartShowAdapter lotteryCartShowAdapter;

    private Button button_addToCart;

    private LinearLayout back;

    private String mainUrl = MainValues.URL;

    private String security,message,message_total,id,code,status,userId_login,point,date;

    AppSessionManager sessionManager;

    private CardView cardView_CheckOutlottery;

    private  ProgressDialog progressDialog;

    private TextView textView_total;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart_show);

        toolbar=(Toolbar)findViewById(R.id.toolbar_cartmemberFormLoad);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


       textView_total=(TextView)findViewById(R.id.text_totalAnoutCart);

        sessionManager = new AppSessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);

        cardView_CheckOutlottery=(CardView)findViewById(R.id.cartLottery_Total);


        recyclerView_addCart=(RecyclerView) findViewById(R.id.recyclerview_CartlotteryShow);
        recyclerView_addCart.setHasFixedSize(true);
        recyclerView_addCart.setLayoutManager(new GridLayoutManager(this, 2));


        button_addToCart=(Button)findViewById(R.id.CheckOut_btn);

        button_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog.show();
                String url = mainUrl + "api/lotary_order_conform";

                security = Structure.SURL;

                StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject jsonObMain=new JSONObject(response);

                            message=jsonObMain.getString("error");

                            if(message.contains("Successfully Buy Lottery")) {

                                progressDialog.dismiss();


                                MaterialDialog mDialog = new MaterialDialog.Builder(Add_toCartShowActivity.this)
                                        .setTitle("Congratulations Lottery Buy successfully")
                                        .setAnimation(R.raw.successful)
                                       // .setMessage("Dear  Candidate Congratulations you are shorlisted   for scratch card")
                                        .setCancelable(false)
                                        .setPositiveButton("Ok", new AbstractDialog.OnClickListener() {
                                            @Override
                                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();

                                                Intent intent=new Intent(Add_toCartShowActivity.this,DashboardActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                                            @Override
                                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                                dialogInterface.dismiss();
                                                Add_toCartShowActivity.this.finish();

                                                Intent intent=new Intent(Add_toCartShowActivity.this,DashboardActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        })
                                        .build();
                                       mDialog.show();

                                Notification_Add(message);
                                AddToCartShow();
                                progressDialog.dismiss();

                            } else{

                                progressDialog.dismiss();
                                Toast.makeText(Add_toCartShowActivity.this, message, Toast.LENGTH_SHORT).show();
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
                        hashMap.put("id",userId_login);

                        return hashMap;
                    }
                };

                RequestQueue requestQueue= Volley.newRequestQueue(Add_toCartShowActivity.this);
                requestQueue.add(stringRequest);

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
    protected void onResume() {

        AddToCartShow();

        super.onResume();
    }

    private void Notification_Add(String message){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(getApplicationContext(),"Channel")
                .setContentTitle("Congratulations")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(message)
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1,notification.build());

    }




    private void   AddToCartShow(){

         progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        String url = mainUrl + "api/show_cart_item";

        security = Structure.SURL;

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    lotteryCartShowList.clear();
                    JSONObject jsonObMain=new JSONObject(response);

                    message=jsonObMain.getString("message");

                    if(message.contains("Data is available!")) {

                        button_addToCart.setEnabled(false);

                     JSONArray jsonArray = jsonObMain.getJSONArray("CardList");

                        for (int i = 0; i <jsonArray.length() ;i++) {

                         JSONObject jsonObject = jsonArray.getJSONObject(i);

                            id= jsonObject.getString("id");
                            code= jsonObject.getString("code");
                            point=   jsonObject.getString("point");
                            date= jsonObject.getString("date");

           LotteryCartShow  lotteryCartShow=new LotteryCartShow(id,date,code,point);
          lotteryCartShowList.add(lotteryCartShow);
                      }
                        lotteryCartShowAdapter=new LotteryCartShowAdapter(lotteryCartShowList,getApplicationContext());
                        recyclerView_addCart.setAdapter(lotteryCartShowAdapter);
                        progressDialog.dismiss();
                        button_addToCart.setEnabled(true);

                        TotalAmount();

                      } else if(message.equalsIgnoreCase("Data is Not available!")){

                        cardView_CheckOutlottery.setVisibility(View.GONE);
                        progressDialog.dismiss();
                     } else{

                        progressDialog.dismiss();
                        Toast.makeText(Add_toCartShowActivity.this, message, Toast.LENGTH_SHORT).show();
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

        RequestQueue requestQueue= Volley.newRequestQueue(Add_toCartShowActivity.this);
        requestQueue.add(stringRequest);

    }

    public void TotalAmount(){

        String url = mainUrl + "api/add_card_balance";

        security = Structure.SURL;

        StringRequest stringRequest_am=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObMain=new JSONObject(response);

                    message_total=jsonObMain.getString("message");

                    if(message_total.contains("Data is available!")) {

                    textView_total.setText("Total:"+jsonObMain.getString("CardList"));


                    } else if(message_total.equalsIgnoreCase("Data is Not available!")){

                        cardView_CheckOutlottery.setVisibility(View.GONE);

                    } else{
                        cardView_CheckOutlottery.setVisibility(View.GONE);
                        Toast.makeText(Add_toCartShowActivity.this, message, Toast.LENGTH_SHORT).show();
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

                Map<String, String> hashMap_amount = new HashMap<String, String>();
                hashMap_amount.put("security_error",security);
                hashMap_amount.put("id",userId_login);

                return hashMap_amount;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(Add_toCartShowActivity.this);
        requestQueue.add(stringRequest_am);
    }

    @Override
    protected void onStop() {
        this.finish();

        super.onStop();
    }



    private void netWorkError(VolleyError error) {

        if (error instanceof NetworkError) {
            Toast.makeText(Add_toCartShowActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(Add_toCartShowActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(Add_toCartShowActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(Add_toCartShowActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(Add_toCartShowActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(Add_toCartShowActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }
}