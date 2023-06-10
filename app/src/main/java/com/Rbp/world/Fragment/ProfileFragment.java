package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.Rbp.world.Adapter.ProfileInfoAdapter;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.Model.ProfileInfoModel;
import com.Rbp.world.ProfileInfoEditActivity;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {


    RecyclerView recyclerView;
    private List<ProfileInfoModel> profileInfoModelList;
    ProfileInfoAdapter profileInfoAdapter;
    Context context;

    ImageView editProfile,profileImg;

    String name, username, email, mobileNo, address, sex, votarId, nomineName, nomineReleation,
            nomineDateOfBirth, fatherName, motherName, religion, dateOfBirth,education,occuption,joinDate,package_type,star_founder;

    AppSessionManager sessionManager;
    String mainUrl, id, security;

    String imageUrl;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        context = getActivity().getApplicationContext();

        sessionManager = new AppSessionManager(getActivity());
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);


        editProfile = view.findViewById(R.id.editProfile);

        profileImg=view.findViewById(R.id.profileImg);

        recyclerView = view.findViewById(R.id.profileRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        profileInfoModelList = new ArrayList<>();

        profileInfoAdapter = new ProfileInfoAdapter(view.getContext(), profileInfoModelList);

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, ProfileInfoEditActivity.class);

                intent.putExtra("imageUrl", imageUrl);

                startActivity(intent);
            }
        });

        loadProfileInfo();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadProfileInfo();
    }

    private void loadProfileInfo() {
        String profileInfoUrl = mainUrl + "api/app_profile_wiew";

        JSONObject reg_data = new JSONObject();

        final ProgressDialog progressdialog = new ProgressDialog(getActivity());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", id);



        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, profileInfoUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("profile_wiew");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject profileInfodata = jsonArray.getJSONObject(i);

                        name = profileInfodata.getString("name");
                        username = profileInfodata.getString("username");
                        email = profileInfodata.getString("email");
                        mobileNo = profileInfodata.getString("mobile_No");
                        address = profileInfodata.getString("address");
                        sex = profileInfodata.getString("sex");
                        votarId = profileInfodata.getString("votar_Id");
                        nomineName = profileInfodata.getString("nomine_Name");
                        nomineReleation = profileInfodata.getString("nomine_Releation");
                        nomineDateOfBirth = profileInfodata.getString("nomine_Date_Of_Birth");
                        fatherName = profileInfodata.getString("father_Name");
                        motherName = profileInfodata.getString("mother_Name");
                        religion = profileInfodata.getString("religion");
                        dateOfBirth = profileInfodata.getString("date_Of_Birth");
                        education = profileInfodata.getString("edu_qualifications");
                        occuption = profileInfodata.getString("occuption");

                        String picture=profileInfodata.getString("picture_Url");

                        joinDate = profileInfodata.getString("join_Date");

                        package_type= String.valueOf(profileInfodata.getInt("package_name"));

                        String pakage=package_type.equals("3700") ? "Icon Package" : package_type.equals("5800") ?"Founders Package" : package_type.equals("11000") ?"Millionaire package" :package_type.equals("22000") ?"Galaxy Package" : "Not update";

                        star_founder= String.valueOf(profileInfodata.getInt("star_founder")).equals("1") ?"Star founder" : "User";
                       imageUrl=mainUrl+picture;
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_user_male)
                                .into(profileImg);

                        ProfileInfoModel profileInfoModel = new ProfileInfoModel(name, username, email, mobileNo, address, sex, votarId, nomineName, nomineReleation, nomineDateOfBirth, fatherName, motherName, religion, dateOfBirth);

                        profileInfoModel.setEducationalQualification(education);
                        profileInfoModel.setOccuption(occuption);
                        profileInfoModel.setPackage_type(pakage);
                        profileInfoModel.setJoinDate(joinDate);
                        profileInfoModel.setDesignation(star_founder);
                        profileInfoModelList.clear();
                        profileInfoModelList.add(profileInfoModel);


                    }
                    progressdialog.dismiss();
                    profileInfoAdapter = new ProfileInfoAdapter(context, profileInfoModelList);

                    recyclerView.setAdapter(profileInfoAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressdialog.dismiss();
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