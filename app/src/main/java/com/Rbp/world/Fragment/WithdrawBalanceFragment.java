package com.Rbp.world.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
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
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.PasswordChangeResponse;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.Rbp.world.Transfer_Wthdraw_HistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class WithdrawBalanceFragment extends Fragment {

    AppSessionManager sessionManager;

    String mainUrl, id, security, name;

    Context context;

    EditText withdraw_balance_amount, withdraw_balance_pin;
    Button withdraw_balance_btn;

    TextView tv_withdraw_history_userID, tv_withdraw_history_date, tv_withdraw_history_amount, tv_withdraw_history_method, tv_withdraw_history_balance,tv_withdraw_history_status;

    String amount, pin;

    TextView more_withdrawHistory,withdraw_amount;

    private LottieAnimationView lottieAnimationView;


    LinearLayout withdrawbalance_subHistory;

    public WithdrawBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_balance, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());

       // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        name = user.get(AppSessionManager.KEY_USER_ID);



        withdraw_balance_amount = view.findViewById(R.id.withdraw_balance_amount);
        withdraw_balance_pin = view.findViewById(R.id.withdraw_balance_pin);
        withdraw_balance_btn = view.findViewById(R.id.withdraw_balance_btn);

        // tv_withdraw_history_userID,tv_withdraw_history_date,tv_withdraw_history_amount,tv_withdraw_history_method,tv_withdraw_history_balance;
        tv_withdraw_history_userID = view.findViewById(R.id.tv_withdraw_history_userID);
        tv_withdraw_history_date = view.findViewById(R.id.tv_withdraw_history_date);
        tv_withdraw_history_amount = view.findViewById(R.id.tv_withdraw_history_amount);
        tv_withdraw_history_method = view.findViewById(R.id.tv_withdraw_history_method);
        tv_withdraw_history_balance = view.findViewById(R.id.tv_withdraw_history_balance);
        tv_withdraw_history_status = view.findViewById(R.id.tv_withdraw_history_status);

        withdrawbalance_subHistory=view.findViewById(R.id.withdrawbalance_subHistory);
        withdraw_amount=view.findViewById(R.id.withdraw_amount);

        lottieAnimationView=view.findViewById(R.id.aminmation_notfount);


        more_withdrawHistory = view.findViewById(R.id.more_withdrawHistory);

        withdraw_balance_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                amount = withdraw_balance_amount.getText().toString().trim();
                pin = withdraw_balance_pin.getText().toString().trim();

                if (!TextUtils.isEmpty(amount) && !TextUtils.isEmpty(pin)) {
                    WithDrawBalance();
                } else if (TextUtils.isEmpty(amount)) {

                    withdraw_balance_amount.setError("Enter Amount");
                    return;

                } else if (TextUtils.isEmpty(pin)) {
                    withdraw_balance_pin.setError("Enter Transction Pin");
                    return;
                }


            }
        });
        more_withdrawHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(context, Transfer_Wthdraw_HistoryActivity.class);
                intentProfile.putExtra("TansferWithdraw", "02");

                startActivity(intentProfile);
            }
        });
        WithdrawHistorySub();
        balanceShow();

        return view;
    }

    private void WithdrawHistorySub() {
        String transferbalanceUrl = mainUrl + "api/app_withdraw_balance_history";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        JSONObject transfer_data = new JSONObject();

        try {
            transfer_data.put("security_error", security);
            transfer_data.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, transferbalanceUrl, transfer_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("error").equals("done")){
                        progressdialog.dismiss();

                        JSONArray jsonArray = response.getJSONArray("withdraw_list");
                        JSONObject method_list = jsonArray.getJSONObject(0);

                        String username = method_list.getString("username");
                        String after_amount = method_list.getString("after_amount");
                        String date = method_list.getString("date");
                        String amount = method_list.getString("amount");
                        String method = method_list.getString("method");

                        String status= String.valueOf(method_list.getInt("status")).equals("0") ?"Complete" : "Pending";

                        tv_withdraw_history_userID.setText("Transaction ID : \n"+username);
                        tv_withdraw_history_balance.setText("Balance : \n"+after_amount);

                        tv_withdraw_history_date.setText("Date : \n"+date);
                        tv_withdraw_history_amount.setText("Amount : \n"+amount);
                        tv_withdraw_history_method.setText("Method : \n"+method);
                        tv_withdraw_history_status.setText("Status : \n"+status);

                        withdrawbalance_subHistory.setVisibility(View.VISIBLE);

                    }
                    if (response.getString("error").equals("Data Not Found")){
                        progressdialog.dismiss();
                        withdrawbalance_subHistory.setVisibility(View.GONE);

                        lottieAnimationView.setVisibility(View.VISIBLE);

                        Toast.makeText(context, "Withdraw Balance History Not Found", Toast.LENGTH_LONG).show();

                    }

                } catch (JSONException e) {
                    progressdialog.dismiss();
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }

    private void balanceShow() {

        String withdrawhistoryUrl = mainUrl + "api/app_balance_sh";
        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject withdrawhistory_data = new JSONObject();
        try {
            withdrawhistory_data.put("security_error", security);
            withdrawhistory_data.put("id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, withdrawhistoryUrl, withdrawhistory_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {

                    String amount = response.getString("balance");

                    withdraw_amount.setText("Amount : "+amount+ " BDT");


                } catch (JSONException e) {
                    e.printStackTrace();

                }

                progressdialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void WithDrawBalance() {

        String withdrawbalanceUrl = mainUrl + "api/app_withdraw_balance_sub";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject withdraw_data = new JSONObject();
        try {
            withdraw_data.put("security_error", security);
            withdraw_data.put("id", id);
            withdraw_data.put("username_log", name);
            withdraw_data.put("withdraw_amount", amount);
            withdraw_data.put("Transction_Password", pin);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, withdrawbalanceUrl, withdraw_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("Successfully Transfer Amount")) {
                    progressdialog.dismiss();

                     withdraw_balance_amount.setText("");
                     withdraw_balance_pin.setText("");

                     String message ="Successfully Transfer Amount";

                    Notification_Add(message);

                    Toast.makeText(context, "Successfully Transfer Amount", Toast.LENGTH_SHORT).show();
                } else {

                    progressdialog.dismiss();

                    String error = jsonArray.getError();
                    withdraw_balance_amount.setError(error);
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
                }
                progressdialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressdialog.dismiss();
                networkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void Notification_Add(String message){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)context.getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(context.getApplicationContext(),"Channel")
                .setContentTitle("Withdraw Balance")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(message)
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context.getApplicationContext());
        notificationManagerCompat.notify(1,notification.build());

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