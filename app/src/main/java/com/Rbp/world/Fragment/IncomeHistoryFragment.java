package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.Rbp.world.Adapter.ListIncomeAdapter;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.Model.Income_list_model;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class IncomeHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Income_list_model> income_list_modelList;
    ListIncomeAdapter listIncomeAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;

    AppSessionManager sessionManager;

    String userId_login, mainUrl,security;


    public IncomeHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_income_history, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetails();

        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security=user.get(AppSessionManager.KEY_SECURITY);


        recyclerView = view.findViewById(R.id.rv_income_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        income_list_modelList = new ArrayList<>();
        listIncomeAdapter = new ListIncomeAdapter(view.getContext(), income_list_modelList);

        loadPaymentMethodOptions();

        return view;
    }

    private void loadPaymentMethodOptions() {
        String income_history_url = mainUrl + "api/app_income_history";

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);



        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", userId_login);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, income_history_url, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("income_list");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject income_list = jsonArray.getJSONObject(i);


                        String username = income_list.getString("username");
                        String income_type = income_list.getString("income_type");
                        int amount = income_list.getInt("income_Amount");
                        String date = income_list.getString("date");

                        Income_list_model incomeListModel = new Income_list_model("", username, date, amount, income_type);
                        income_list_modelList.add(incomeListModel);
                    }
                    progressDialog.dismiss();
                    listIncomeAdapter = new ListIncomeAdapter(context, income_list_modelList);
                    recyclerView.setAdapter(listIncomeAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                networkError(error);
                Log.d("TAG", "onErrorResponse: " + error.getMessage());
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