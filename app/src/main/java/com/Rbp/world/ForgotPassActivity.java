package com.Rbp.world;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.Rbp.world.Adapter.Structure;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.PasswordChangeResponse;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPassActivity extends AppCompatActivity {

    EditText forgot_userId;
    Button forgot_rest_pass;

    String mainUrl = MainValues.URL;

    String forgotUserId;
    String security;
    String messagetext;
    Context context;
    AppSessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_pass);

        context = getApplicationContext();

        forgot_userId = findViewById(R.id.forgot_userId);
        forgot_rest_pass = findViewById(R.id.forgot_rest_pass);


        forgot_rest_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submit();

            }
        });
    }

    private void submit() {

        String url = mainUrl + "api/app_forgot_password_sec";

        security = Structure.SURL;
        if (forgot_userId.getText().toString().equals("")) {

            forgot_userId.setError("Enter User Name");

        } else {

            final ProgressDialog progressDialog = new ProgressDialog(this);

            progressDialog.show();
            progressDialog.setContentView(R.layout.custom_progrss_dialog);
            progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

            forgotUserId = forgot_userId.getText().toString().trim();


            JSONObject postData = new JSONObject();
            try {
                postData.put("security_error", security);
                postData.put("forgot_username", forgotUserId);


            } catch (JSONException e) {
                e.printStackTrace();
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    PasswordChangeResponse jsonArray = null;
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.serializeNulls();
                    Gson gson = gsonBuilder.create();
                    jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);


                    try {
                        if (jsonArray.getError().equals("Password send your mobile phone !!")) {

                            progressDialog.dismiss();

                            messagetext=jsonArray.getError().toString();
                            goToLogin(messagetext);
                            Toast.makeText(context, jsonArray.getError(), Toast.LENGTH_LONG).show();

                        } else {

                            progressDialog.dismiss();

                            //displaying toast when error occurs
                            Toast.makeText(context, jsonArray.getError(), Toast.LENGTH_SHORT).show();

                        }
                    } catch (Exception e) {

                        Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    netWorkError(error);
                    Toast.makeText(context, error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            });
            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                    20000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);

        }
    }

    public void goToLogin(String message) {
        Intent i = new Intent(this, LoginActivity.class);
        i.putExtra("forgetpass",message );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);

        startActivity(i);
        finish();
    }

    private void netWorkError(VolleyError error) {
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