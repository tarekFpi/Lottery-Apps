package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class TransactionPinFragment extends Fragment {
    AppSessionManager sessionManager;

    String mainUrl, id, security, name;

    Context context;

    EditText transaction_pin_current, transaction_pin_new, transaction_pin_new_confirm;
    Button transaction_pin_update_btn;


    String pin, newPin, confirmPin;

    public TransactionPinFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_transaction_pin, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        name = user.get(AppSessionManager.KEY_USER_ID);

        // EditText transaction_pin_current,transaction_pin_new,transaction_pin_new_confirm;
        transaction_pin_current = view.findViewById(R.id.transaction_pin_current);
        transaction_pin_new = view.findViewById(R.id.transaction_pin_new);
        transaction_pin_new_confirm = view.findViewById(R.id.transaction_pin_new_confirm);
        transaction_pin_update_btn = view.findViewById(R.id.transaction_pin_update_btn);

        transaction_pin_update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pin = transaction_pin_current.getText().toString().trim();
                newPin = transaction_pin_new.getText().toString().trim();
                confirmPin = transaction_pin_new_confirm.getText().toString().trim();

                if (!TextUtils.isEmpty(pin) && !TextUtils.isEmpty(newPin) && !TextUtils.isEmpty(confirmPin) && newPin.equals(confirmPin)) {
                    changePin();
                } else if (TextUtils.isEmpty(pin)) {
                    transaction_pin_current.setError("Enter Your Current Pin");
                    return;

                } else if (TextUtils.isEmpty(newPin)) {
                    transaction_pin_new.setError("Enter Your New Pin");
                    return;
                } else if (TextUtils.isEmpty(confirmPin)) {
                    transaction_pin_new_confirm.setError("Enter Your New  Pin again");
                    return;
                }else if (newPin!=confirmPin) {
                    transaction_pin_new_confirm.setError("Confirm pin not Match for your new Pin");
                    return;
                }
            }
        });


        return view;
    }

    private void changePin() {

        String transactionPinUrl = mainUrl + "api/app_transaction_pin";


        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject transaction_pin_data = new JSONObject();
        try {
            transaction_pin_data.put("security_error", security);
            transaction_pin_data.put("id", id);
            transaction_pin_data.put("current_password", pin);
            transaction_pin_data.put("new_password", newPin);
            transaction_pin_data.put("new_confirm_password", confirmPin);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, transactionPinUrl, transaction_pin_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                if (jsonArray.getError().equals("Pin change successfully")) {
                    progressdialog.dismiss();
                    Toast.makeText(context, "Pin change successfully", Toast.LENGTH_SHORT).show();

                }else {
                    progressdialog.dismiss();
                    String error=jsonArray.getError();
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