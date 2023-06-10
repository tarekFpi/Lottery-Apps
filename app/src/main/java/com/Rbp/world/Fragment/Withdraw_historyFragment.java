package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Rbp.world.LoginActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.Rbp.world.Adapter.TransferWithdrawAdpater;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.Model.Transfer_Withdraw_model;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Withdraw_historyFragment extends Fragment {

    Context context;

    AppSessionManager sessionManager;
    String mainUrl, id, security;
    RecyclerView recyclerView;

    private List<Transfer_Withdraw_model> transfer_withdraw_modelList;
    TransferWithdrawAdpater transferWithdrawAdpater;
    public Withdraw_historyFragment() {

    }

    private String message="";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_withdraw_history, container, false);


        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        recyclerView = view.findViewById(R.id.rv_T_W_history_List2);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        transfer_withdraw_modelList = new ArrayList<>();
        transferWithdrawAdpater = new TransferWithdrawAdpater(context, transfer_withdraw_modelList);

        loadPlacementList();

        return view;
    }
    private void loadPlacementList() {
        String placementUrl = mainUrl + "api/app_withdraw_balance_history";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        StringRequest stringRequest=new StringRequest(Request.Method.POST, placementUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObMain=new JSONObject(response);

                    message=jsonObMain.getString("error");

                    if(message.equalsIgnoreCase("done")){

                        JSONArray jsonArray = jsonObMain.getJSONArray("withdraw_list");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject transfer_withdraw_history = jsonArray.getJSONObject(i);

                            String username = transfer_withdraw_history.getString("username");
                            int after_amount = transfer_withdraw_history.getInt("after_amount");
                            String date = transfer_withdraw_history.getString("date");
                            int amount = transfer_withdraw_history.getInt("amount");
                            String method = transfer_withdraw_history.getString("method");

                            String status= String.valueOf(transfer_withdraw_history.getInt("status")).equals("0") ?"Complete" : "Pending";

                            Transfer_Withdraw_model transferWithdrawModel = new Transfer_Withdraw_model(username, after_amount, date, amount, method);
                            transferWithdrawModel.setStatus(status);
                            transfer_withdraw_modelList.add(transferWithdrawModel);
                        }
                        transferWithdrawAdpater=new TransferWithdrawAdpater(context,transfer_withdraw_modelList);
                        recyclerView.setAdapter(transferWithdrawAdpater);
                        progressdialog.dismiss();

                    }else{
                        progressdialog.dismiss();
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    progressdialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
                progressdialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("security_error", security);
                hashMap.put("id", id);
                return hashMap;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

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