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
import com.Rbp.world.Adapter.TransctionHistoryAdapter;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.Model.Transction_history_model;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TransctionHistoryFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Transction_history_model> transctionHistoryModelList;
    TransctionHistoryAdapter transctionHistoryAdapter;
    private RecyclerView.LayoutManager layoutManager;

    Context context;

    AppSessionManager sessionManager;
    String mainUrl, id, security;

    public TransctionHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transction_history, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        recyclerView = view.findViewById(R.id.rv_transctionHistory_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        transctionHistoryModelList = new ArrayList<>();
        transctionHistoryAdapter = new TransctionHistoryAdapter(view.getContext(), transctionHistoryModelList);


        loadTransctionHistory();
        return view;
    }

    private void loadTransctionHistory() {

        String transctionurl = mainUrl + "api/app_transction_history";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);




        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, transctionurl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("transction_history");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject transctionlist = jsonArray.getJSONObject(i);

                        if (transctionlist.getString("transction_Type").equals("Transfer Balance") && transctionlist.getString("to_User_Id").equals(id)) {

                            String username = transctionlist.getString("user_Id_d");
                            int amount = transctionlist.getInt("amount");
                            String date = transctionlist.getString("date");
                            String note = String.valueOf(transctionlist.getInt("charge"));
                            String type = "Receive Balance";
                            String method = transctionlist.getString("method");

                            String receive_balance = transctionlist.getString("rsv_after_amount");

                            int balance = Integer.parseInt(receive_balance);


                            if (!note.equals("null")) {

                                Transction_history_model transction_history_model = new Transction_history_model("", username, date, amount, note, type, method, balance);

                                transctionHistoryModelList.add(transction_history_model);
                            }else {
                                note=" ";
                                Transction_history_model transction_history_model = new Transction_history_model("", username, date, amount, note, type, method, balance);

                                transctionHistoryModelList.add(transction_history_model);
                            }



                        } else {
                            String username = transctionlist.getString("username");
                            int amount = transctionlist.getInt("amount");
                            String date = transctionlist.getString("date");
                            String note = transctionlist.getString("charge");
                            String type = transctionlist.getString("transction_Type");
                            String method = transctionlist.getString("method");
                            int balance = transctionlist.getInt("after_amount");


                            if (!note.equals("null")) {

                                Transction_history_model transction_history_model = new Transction_history_model("", username, date, amount, note, type, method, balance);

                                transctionHistoryModelList.add(transction_history_model);
                            }else {
                                note=" ";
                                Transction_history_model transction_history_model = new Transction_history_model("", username, date, amount, note, type, method, balance);

                                transctionHistoryModelList.add(transction_history_model);
                            }


                        }


                    }
                    progressdialog.dismiss();
                    transctionHistoryAdapter = new TransctionHistoryAdapter(context, transctionHistoryModelList);
                    recyclerView.setAdapter(transctionHistoryAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressdialog.dismiss();
                    Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
                progressdialog.dismiss();
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