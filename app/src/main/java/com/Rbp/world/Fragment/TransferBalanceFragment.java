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


public class TransferBalanceFragment extends Fragment {
    AppSessionManager sessionManager;

    String mainUrl, id, security, name;

    Context context;

    EditText transfer_user_id, transfer_user_amount, transfer_user_pin, transfer_user_note;
    Button transfer_user_btn;

    String userid, amount, pin, note;

    TextView tv_transfer_history_userID,tv_transfer_history_date,tv_transfer_history_amount,tv_transfer_history_method,tv_transfer_history_balance;

    TextView more_transferHistory;

    LinearLayout transferbalance_sub_history;
    public TransferBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transfer_balance, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        name = user.get(AppSessionManager.KEY_USER_ID);

        transfer_user_id = view.findViewById(R.id.transfer_user_id);
        transfer_user_amount = view.findViewById(R.id.transfer_user_amount);
        transfer_user_pin = view.findViewById(R.id.transfer_user_pin);
        transfer_user_note = view.findViewById(R.id.transfer_user_note);
        transferbalance_sub_history=view.findViewById(R.id.transferbalance_sub_history);


        more_transferHistory = view.findViewById(R.id.more_transferHistory);



        transfer_user_btn = view.findViewById(R.id.transfer_user_btn);


        tv_transfer_history_userID = view.findViewById(R.id.tv_transfer_history_userID);
        tv_transfer_history_date = view.findViewById(R.id.tv_transfer_history_date);
        tv_transfer_history_amount = view.findViewById(R.id.tv_transfer_history_amount);
        tv_transfer_history_method = view.findViewById(R.id.tv_transfer_history_method);
        tv_transfer_history_balance = view.findViewById(R.id.tv_transfer_history_balance);


        transfer_user_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userid = transfer_user_id.getText().toString().trim();
                amount = transfer_user_amount.getText().toString().trim();
                pin = transfer_user_pin.getText().toString().trim();
                note = transfer_user_note.getText().toString().trim();

                if (!TextUtils.isEmpty(userid) && !TextUtils.isEmpty(amount) && !TextUtils.isEmpty(pin) && !TextUtils.isEmpty(note)) {
                    transferBalance();
                } else if (TextUtils.isEmpty(userid)) {

                    transfer_user_id.setError("Enter User Id");
                    return;

                } else if (TextUtils.isEmpty(amount)) {
                    transfer_user_amount.setError("Enter Withdraw Amount");
                    return;
                } else if (TextUtils.isEmpty(pin)) {
                    transfer_user_pin.setError("Enter Transaction Pin");
                    return;
                } else if (TextUtils.isEmpty(note)) {
                    transfer_user_note.setError("TRANSACTION Note");
                    return;
                }

            }
        });

        more_transferHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(context, Transfer_Wthdraw_HistoryActivity.class);
                intentProfile.putExtra("TansferWithdraw", "01");

                startActivity(intentProfile);
            }
        });
        transferHistorSub();

        return view;
    }

    private void transferHistorSub() {
        String transferbalanceUrl = mainUrl + "api/app_transfer_balance_history";

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

                        JSONArray jsonArray = response.getJSONArray("transfer_list");
                        JSONObject method_list = jsonArray.getJSONObject(0);

                        String username = method_list.getString("username");
                        String after_amount = method_list.getString("after_amount");
                        String date = method_list.getString("date");
                        String amount = method_list.getString("amount");
                        String method = method_list.getString("method");

                        tv_transfer_history_userID.setText("Transaction ID : \n"+username);
                        tv_transfer_history_balance.setText("Balance : \n"+after_amount);

                        tv_transfer_history_date.setText("Date : \n"+date);
                        tv_transfer_history_amount.setText("Amount : \n"+amount);
                        tv_transfer_history_method.setText("Method : \n"+method);
                        transferbalance_sub_history.setVisibility(View.VISIBLE);

                    }
                    if (response.getString("error").equals("Data Not Found")){
                        progressdialog.dismiss();
                        transferbalance_sub_history.setVisibility(View.GONE);
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

    private void transferBalance() {


        String transferbalanceUrl = mainUrl + "api/app_transfer_balance_sub";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        JSONObject transfer_data = new JSONObject();

        try {
            transfer_data.put("security_error", security);
            transfer_data.put("id", id);
            transfer_data.put("username_log", name);
            transfer_data.put("username", userid);
            transfer_data.put("withdraw_amount", amount);
            transfer_data.put("Transction_Password", pin);
            transfer_data.put("txt_Note", note);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, transferbalanceUrl, transfer_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("Successfully Transfer Amount")) {
                    progressdialog.dismiss();
                    Toast.makeText(context, "Successfully Transfer Amount", Toast.LENGTH_SHORT).show();

                } else {

                    progressdialog.dismiss();

                    String error = jsonArray.getError();
                    transfer_user_amount.setError(error);
                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
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