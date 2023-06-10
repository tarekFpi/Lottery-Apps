package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.Rbp.world.Adapter.TransferWithdrawAdpater;
import com.Rbp.world.Model.Transfer_Withdraw_model;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.PasswordChangeResponse;
import com.Rbp.world.JsonResponse.Payment_method_Response;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class WithdrawMethodFragment extends Fragment {

    AppSessionManager sessionManager;

    String mainUrl, id, security, name;

    Context context;
    LinearLayout withdraw_method_panel;

    ArrayList<Payment_method_Response> list = new ArrayList<>();

    Spinner spinner;

    int idn;
    String method_name_f;
    EditText withdraw_method_details;
    Button update_withdraw_method;
    String details,message;


    CardView withdrawmethod_buttom_layout;
    TextView withdraw_method_name,withdraw_method_details_pin;

    public WithdrawMethodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_method, container, false);
        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        name = user.get(AppSessionManager.KEY_USER_ID);

        spinner = view.findViewById(R.id.spnr_WithdrawForm_PaymentMtd);

        withdraw_method_details = view.findViewById(R.id.withdraw_method_details);
        update_withdraw_method = view.findViewById(R.id.update_withdraw_method);

        withdraw_method_name=view.findViewById(R.id.withdraw_method_name);
        withdraw_method_details_pin=view.findViewById(R.id.withdraw_method_details_pin);
        withdrawmethod_buttom_layout=view.findViewById(R.id.withdrawmethod_buttom_layout);

        withdraw_method_panel=view.findViewById(R.id.withdraw_method_panel);

        SpinnerList();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSelectedUser(view);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        update_withdraw_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                details = withdraw_method_details.getText().toString();

                if (idn != 0 && !TextUtils.isEmpty(details)) {
                    UpdateMethod();
                } else if (TextUtils.isEmpty(details)) {
                    withdraw_method_details.setError("Enter you Method Details");
                } else {
                    Toast.makeText(context, "Select Withdraw Method", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void UpdateMethod() {

        String withdraw_update_url = mainUrl + "api/app_withdraw_method_sub";

        //JSONObject withdraw_data = new JSONObject();


        final ProgressDialog progressDialog = new ProgressDialog(getActivity());

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    JSONObject withdraw_data = new JSONObject();
    try {

            withdraw_data.put("security_error", security);
            withdraw_data.put("id", id);
            withdraw_data.put("selected_n", method_name_f);
            withdraw_data.put("method_details", details);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, withdraw_update_url, withdraw_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("Successfully Payment Method Update")){
                    progressDialog.dismiss();


                    withdrawmethod_buttom_layout.setVisibility(View.VISIBLE);
                    withdraw_method_name.setText("Method Name : \n"+method_name_f);
                    withdraw_method_details_pin.setText("Method Details : \n"+details);
                    withdraw_method_panel.setVisibility(View.VISIBLE);
                    Toast.makeText(context, "Successfully Payment Method Update", Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    Toast.makeText(context, jsonArray.getError().toString(), Toast.LENGTH_SHORT).show();
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


    private void SpinnerList() {

        String withdrawmethodUrl = mainUrl + "api/app_withdraw_method";
        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

   JSONObject withdrawmethod_data = new JSONObject();
        try {
            withdrawmethod_data.put("security_error", security);
            withdrawmethod_data.put("id", id);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, withdrawmethodUrl, withdrawmethod_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("withdraw_method_name");

                    Payment_method_Response payment_method_response1 = new Payment_method_Response(0, "--Select--");
                    list.add(payment_method_response1);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        progressdialog.dismiss();
                        JSONObject method_list = jsonArray.getJSONObject(i);

                        idn = method_list.getInt("id");
                        String method_name = method_list.getString("method_name");

                        Payment_method_Response payment_method_response = new Payment_method_Response(idn, method_name);
                        list.add(payment_method_response);
                    }
                    ArrayAdapter<Payment_method_Response> adapter = new ArrayAdapter<Payment_method_Response>(getActivity(), android.R.layout.simple_spinner_dropdown_item, list);
                    spinner.setAdapter(adapter);

                } catch (JSONException e) {
                    progressdialog.dismiss();
                    e.printStackTrace();
                }
                try {
                    String error = response.getString("error");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (response.getString("error").equals("done")){

                        JSONArray jsonArray = response.getJSONArray("withdraw_method_row");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject method_list = jsonArray.getJSONObject(i);

                            String method_name = method_list.getString("bank_Name");
                            String account_No = method_list.getString("account_No");
                            withdrawmethod_buttom_layout.setVisibility(View.VISIBLE);

                            if (!method_name.equals("null") && !account_No.equals("null")){
                                withdraw_method_name.setText("Method Name : \n"+method_name);
                                withdraw_method_details_pin.setText("Method Details : \n"+account_No);
                                withdraw_method_panel.setVisibility(View.VISIBLE);
                            }else {
                                withdraw_method_panel.setVisibility(View.GONE);
                            }



                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                progressdialog.dismiss();
//                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();

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


    public void getSelectedUser(View v) {
        Payment_method_Response user = (Payment_method_Response) spinner.getSelectedItem();
        displayUserData(user);
    }

    private void displayUserData(Payment_method_Response user) {
        idn = user.getId();
        method_name_f=user.getMethodName();

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