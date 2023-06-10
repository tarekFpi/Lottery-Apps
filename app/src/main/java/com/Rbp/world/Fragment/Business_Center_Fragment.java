package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class Business_Center_Fragment extends Fragment {

    Context context;

    AppSessionManager sessionManager;
    String mainUrl, id, security;

    TextView business_userId, business_date, business_sponsorId, business_placementId, business_Acarry, business_Bcarry, business_Ccarry, business_AID,
            business_BID, business_CID, business_designation;

    public Business_Center_Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_business_center, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        business_userId = view.findViewById(R.id.business_userId);
        business_date = view.findViewById(R.id.business_date);
        business_sponsorId = view.findViewById(R.id.business_sponsorId);
        business_placementId = view.findViewById(R.id.business_placementId);

        business_Acarry = view.findViewById(R.id.business_Acarry);

        business_Bcarry = view.findViewById(R.id.business_Bcarry);
        business_Ccarry = view.findViewById(R.id.business_Ccarry);

        business_AID = view.findViewById(R.id.business_AID);
        business_BID = view.findViewById(R.id.business_BID);
        business_CID = view.findViewById(R.id.business_CID);
        business_designation = view.findViewById(R.id.business_designation);

        loadBusinessData();

        return view;
    }

    private void loadBusinessData() {

        String transctionurl = mainUrl + "api/app_business_center";

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
                    JSONObject data1 = response.getJSONObject("user_center_sel");


                    if (response.getJSONObject("user_center_sel").equals(data1)) {

                        String name = data1.optString("username");
                        String date = data1.optString("date");
                        int carry1 = data1.optInt("carry1");
                        int carry2 = data1.optInt("carry2");
                        int carry3 = data1.optInt("carry3");

                        business_userId.setText("User ID: \n" + name);
                        business_date.setText("Date: \n" + date);
                        business_Acarry.setText("Team A Carry: \n" + carry1);
                        business_Bcarry.setText("Team B Carry: \n" + carry2);
                        business_Ccarry.setText("Team C Carry: \n" + carry3);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    int sponsorId = response.getInt("sponsor_User_Id");
                    business_sponsorId.setText("Sponsor ID: \n" + sponsorId);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    int placementId = response.getInt("placement_User_Id");
                    business_placementId.setText("Placement Id: \n" + placementId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject left_Hand_user = response.getJSONObject("left_Hand_user");

                    if (response.getJSONObject("left_Hand_user").equals(left_Hand_user)) {

                        String left_username = left_Hand_user.optString("username");
                        business_AID.setText("A ID: \n" + left_username);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    JSONObject middle_Hand = response.getJSONObject("middle_Hand_user");
                    if (response.getJSONObject("middle_Hand_user").equals(middle_Hand)) {

                        String middle_username = middle_Hand.optString("username");
                        business_BID.setText("B ID: \n" + middle_username);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    JSONObject right_Hand = response.getJSONObject("right_Hand_user");

                    if (response.getJSONObject("right_Hand_user").equals(right_Hand)) {

                        String right_username = right_Hand.optString("username");
                        business_CID.setText("C ID: \n"+right_username);


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {

                    String designation = response.getString("Designation");
                    business_designation.setText("Designation: \n" + designation);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressdialog.dismiss();


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