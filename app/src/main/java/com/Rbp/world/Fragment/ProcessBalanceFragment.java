package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class ProcessBalanceFragment extends Fragment {

    TextView processBalanceText;
    Button processBalanceBtn;

    Context context;

    AppSessionManager sessionManager;
    String mainUrl, id, security;

    public ProcessBalanceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_process_balance, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        processBalanceText = view.findViewById(R.id.processBalanceText);
        processBalanceBtn = view.findViewById(R.id.processBalanceBtn);

        processBalanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateData();
            }
        });




        return view;
    }

    private void updateData() {

        String profileInfoUrl = mainUrl + "api/app_process_blance_submit";


        JSONObject reg_data = new JSONObject();

        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        try {
            reg_data.put("security_error", security);
            reg_data.put("id", id);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, profileInfoUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("BALANCE PROCESS DONE")) {
                    progressDialog.dismiss();

                    processBalanceText.setText("BALANCE PROCESS DONE");
                    processBalanceText.setTextColor(Color.parseColor("#009432"));

                    Toast.makeText(context, "BALANCE PROCESS DONE", Toast.LENGTH_SHORT).show();

                }
               else if (jsonArray.getError().equals("Data Not Found")) {
                    progressDialog.dismiss();
                    processBalanceText.setText("Data Not Found");
                    Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
                    processBalanceText.setTextColor(Color.parseColor("#EA2027"));
                }
                else {
                    progressDialog.dismiss();
//                    processBalanceText.setText("Data Not Found");
//                    Toast.makeText(context, "Data Not Found", Toast.LENGTH_SHORT).show();
//                    processBalanceText.setTextColor(Color.parseColor("#EA2027"));
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
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