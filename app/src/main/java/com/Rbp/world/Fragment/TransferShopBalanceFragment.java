package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
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

import androidx.fragment.app.Fragment;

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
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.Withdraw_shop_Response;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.Rbp.world.Transfer_Wthdraw_HistoryActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;


public class TransferShopBalanceFragment extends Fragment {

    AppSessionManager sessionManager;

    String mainUrl, id, security, name;

    Context context;
    EditText withdraw_shop_agent_id;

    EditText withdraw_shop_user_amount, withdraw_shop_user_pin, withdraw_shop_note;
    Button withdraw_shop_sub_btn;

    String username, amount, pin, note;

    TextView tv_withdraw_history_userID, tv_withdraw_history_date, tv_withdraw_history_amount, tv_withdraw_history_method, tv_withdraw_history_balance;

    TextView more_withdraw_shop_history;

    LinearLayout withdraw_shop_sub_history;

    ArrayList<Withdraw_shop_Response> list = new ArrayList<>();
    int idn;
    String agent_name;

    public TransferShopBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_shop_balance, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        name = user.get(AppSessionManager.KEY_USER_ID);

        withdraw_shop_agent_id = view.findViewById(R.id.withdraw_shop_agent_id);
        withdraw_shop_user_amount = view.findViewById(R.id.withdraw_shop_user_amount);
        withdraw_shop_user_pin = view.findViewById(R.id.withdraw_shop_user_pin);
        withdraw_shop_note = view.findViewById(R.id.withdraw_shop_note);

        withdraw_shop_sub_history = view.findViewById(R.id.withdraw_shop_sub_history);


        more_withdraw_shop_history = view.findViewById(R.id.more_withdraw_shop_history);

        more_withdraw_shop_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(context, Transfer_Wthdraw_HistoryActivity.class);
                intentProfile.putExtra("TansferWithdraw", "04");

                startActivity(intentProfile);
            }
        });


        withdraw_shop_sub_btn = view.findViewById(R.id.withdraw_shop_sub_btn);


        tv_withdraw_history_userID = view.findViewById(R.id.tv_withdraw_history_userID);
        tv_withdraw_history_date = view.findViewById(R.id.tv_withdraw_history_date);
        tv_withdraw_history_amount = view.findViewById(R.id.tv_withdraw_history_amount);
        tv_withdraw_history_method = view.findViewById(R.id.tv_withdraw_history_method);
        tv_withdraw_history_balance = view.findViewById(R.id.tv_withdraw_history_balance);


        historyList();



        withdraw_shop_sub_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username=withdraw_shop_agent_id.getText().toString().trim();
                amount = withdraw_shop_user_amount.getText().toString().trim();
                pin = withdraw_shop_user_pin.getText().toString().trim();
                note = withdraw_shop_note.getText().toString().trim();

                if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(pin) && !TextUtils.isEmpty(note)) {
                    UpdateMethod();
                } else if (TextUtils.isEmpty(username)) {
                    withdraw_shop_agent_id.setError("Enter User Name");
                    return;
                }else if (TextUtils.isEmpty(amount)) {
                    withdraw_shop_user_amount.setError("Enter Withdraw Amount");
                    return;
                } else if (TextUtils.isEmpty(pin)) {
                    withdraw_shop_user_pin.setError("Enter Transaction Pin");
                    return;
                } else if (TextUtils.isEmpty(note)) {
                    withdraw_shop_note.setError("TRANSACTION Note");
                    return;
                }

            }
        });


        return view;
    }





    private void historyList() {

        String withdrawhistoryUrl = mainUrl + "api/app_Shop_balance_transfer_his";
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

                    JSONArray jsonArray = response.getJSONArray("Shop_transfer_his");

                    JSONObject method_list = jsonArray.getJSONObject(0);

                    String username = method_list.getString("username");
                    String after_amount = method_list.getString("after_amount");
                    String date = method_list.getString("date");
                    String amount = method_list.getString("amount");
                    String method = method_list.getString("method");

                    tv_withdraw_history_userID.setText("Transaction ID : \n"+username);
                    tv_withdraw_history_balance.setText("Balance : \n"+after_amount);

                    tv_withdraw_history_date.setText("Date : \n"+date);
                    tv_withdraw_history_amount.setText("Amount : \n"+amount);
                    tv_withdraw_history_method.setText("Method : \n"+method);


                } catch (JSONException e) {
                    e.printStackTrace();
                    withdraw_shop_sub_history.setVisibility(View.GONE);
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

    private void UpdateMethod() {

        String withdraw_update_url = mainUrl + "api/app_Shop_balance_transfer_sub";


        JSONObject withdraw_data = new JSONObject();


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        try {
            withdraw_data.put("security_error", security);
            withdraw_data.put("id", id);
            withdraw_data.put("username", username);
            withdraw_data.put("login_username", name);
            withdraw_data.put("withdraw_amount", amount);
            withdraw_data.put("txt_Note", note);
            withdraw_data.put("Transction_Password", pin);


        } catch (JSONException e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, withdraw_update_url, withdraw_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    if (response.getString("error").equals("Successfully Transfer Amount")) {
                        progressDialog.dismiss();

                        Toast.makeText(context, "Successfully Transfer Amount", Toast.LENGTH_SHORT).show();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(context, response.getString("error").toString(), Toast.LENGTH_SHORT).show();
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