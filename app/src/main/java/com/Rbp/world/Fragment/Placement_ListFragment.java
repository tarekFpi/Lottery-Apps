package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
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
import com.Rbp.world.Adapter.PlacementListAdpater;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.Model.Placement_list_model;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Placement_ListFragment extends Fragment {


    Context context;

    AppSessionManager sessionManager;
    String mainUrl, id, security;


    RecyclerView recyclerView;
    private List<Placement_list_model> placementListModelList;
    PlacementListAdpater placementListAdpater;

    public Placement_ListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_placement__list, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(context);
        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        recyclerView = view.findViewById(R.id.rv_placement_List);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        placementListModelList = new ArrayList<>();
        placementListAdpater = new PlacementListAdpater(context, placementListModelList);

        loadPlacementList();

        return view;
    }

    private void loadPlacementList() {

        String placementUrl = mainUrl + "api/app_placement_list";

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


//        str_name = fullname.getText().toString().trim();
//        str_userId = userId.getText().toString().replace(" ", "");
//        str_mobile = mobile.getText().toString().trim();
//        str_joiningCode = joining_code.getText().toString().trim();
//        str_sponsorUserId = sponsor_user_id.getText().toString().trim();
//        str_placementUserId = placement_user_id.getText().toString().trim();
//
//        str_placementUserType = String.valueOf(itemposition);

        JSONObject place_data = new JSONObject();

        try {
            place_data.put("security_error", security);
            place_data.put("id", id);
//            reg_data.put("username", str_userId);
//            reg_data.put("mobile_number", str_mobile);
//            reg_data.put("Joining_code", str_joiningCode);
//            reg_data.put("sponser_u_id", str_sponsorUserId);
//            reg_data.put("placement_u_id", str_placementUserId);
//            reg_data.put("selected_n", str_placementUserType);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, placementUrl, place_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray jsonArray = response.getJSONArray("user_center_sel");


//                    Toast.makeText(context, jsonArray.toString(), Toast.LENGTH_SHORT).show();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject placementList = jsonArray.getJSONObject(i);

                        String username = placementList.getString("username");
                        String join_Date = placementList.getString("join_Date");

                        String placement_User = placementList.optString("placement_User_n");

                        String sponsor_User = placementList.getString("sponsor_User_n");
                        int carry1 = placementList.getInt("carry1");
                        int carry2 = placementList.getInt("carry2");
                        int carry3 = placementList.getInt("carry3");


                        Placement_list_model placement_list_model = new Placement_list_model(username,join_Date,sponsor_User,carry1,carry2,carry3);
                        placementListModelList.add(placement_list_model);

                    }
                    placementListAdpater = new PlacementListAdpater(context, placementListModelList);
                    recyclerView.setAdapter(placementListAdpater);
                    progressdialog.dismiss();

                } catch (JSONException e) {
                    progressdialog.dismiss();
                    Toast.makeText(context, "No Data found", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                networkError(error);
                progressdialog.dismiss();

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