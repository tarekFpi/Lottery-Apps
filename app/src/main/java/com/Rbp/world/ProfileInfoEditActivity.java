package com.Rbp.world;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

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
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.Rbp.world.Adapter.ProfileInfoAdapter;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.ProfileEditResponse;
import com.Rbp.world.Model.ProfileInfoModel;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.Rbp.world.StoreSession.DateFragment;
import com.Rbp.world.StoreSession.YourDateFragment;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileInfoEditActivity extends AppCompatActivity {
    private static final int SELECT_PICTURE = 111;
    private static final int PICK_CAMERA_REQUEST = 2 ;
    ProfileInfoAdapter profileInfoAdapter;
    private List<ProfileInfoModel> profileInfoModelList;

    EditText et_Profile_Name, et_profile_userId, et_Profile_FatherName, et_Profile_MotherName,
            et_Profile_PhoneNumber, et_profile_address, et_profile_email, et_profile_sex,
            et_Profile_NID, et_profile_nomine_Name, et_profile_nomine_Releation, et_profile_religion,
            et_profile_education,et_profile_occuption;

    static EditText et_profile_nomine_Date_Of_Birth;
    static EditText et_profile_date_Of_Birth;

    String name, username, email, mobileNo, address, sex, votarId, nomineName, nomineReleation,
            nomineDateOfBirth, fatherName, motherName, religion, dateOfBirth,education,occuption;;

    Button btn_UpdateInfo, profile_uplodeImage, nominee_birth_btn, your_birth_btn;

    Bitmap bitmap;

    AppSessionManager sessionManager;

    String mainUrl, id, security;

    LinearLayout back;
    ImageView editProfile_image;

    ConstraintLayout choseImage;

    Context context;

    TextView tab_text, et_profile_email_checker;
    ImageView et_email_valid;
    String emailPattern;

    String encodeImageString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_profile_info_edit);

        tab_text = findViewById(R.id.tab_text_edit_profile);
        back = findViewById(R.id.go_back_editProfile);

        sessionManager = new AppSessionManager(this);
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        tab_text.setText("Update Profile");

        context = getApplicationContext();

        profileInfoModelList = new ArrayList<>();
        profileInfoAdapter = new ProfileInfoAdapter(context, profileInfoModelList);


        findView();
        loadData();


        final String path = getIntent().getStringExtra("imageUrl");
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.ic_profile)
                .into(editProfile_image);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go_Back = new Intent(ProfileInfoEditActivity.this, MemberFromLoadActivityActivity.class);
                go_Back.putExtra("Techno", "01");
                startActivity(go_Back);
            }
        });

        btn_UpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                updateInformation();

            }
        });

        choseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeImage();

            }
        });

        profile_uplodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bitmap resized = Bitmap.createScaledBitmap(bitmap, 400, 400, true);
//                editProfile_image.setImageBitmap(resized);

                UplodeImage();


            }
        });

        nominee_birth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new DateFragment();
                dateFragment.show(getSupportFragmentManager(), "Date Picker");

            }
        });

        your_birth_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dateFragment = new YourDateFragment();
                dateFragment.show(getSupportFragmentManager(), "Date Picker2");

            }
        });

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        et_profile_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = editable.toString().replaceAll(" ", "");


                if (!editable.toString().equals(result)) {
                    et_profile_email.setText(result);
                    et_profile_email.setSelection(result.length());
                }
                if (editable.length() > 3) {
                    email = et_profile_email.getText().toString();

                    if (email.matches(emailPattern)) {
                        //  Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
                        // or
                        checkEmailAbailable(email);

                    } else {
                        //  Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
                        //or
                        et_email_valid.setVisibility(View.GONE);
                        et_profile_email_checker.setVisibility(View.GONE);
                    }


                } else {
                    et_profile_email_checker.setVisibility(View.GONE);
                    et_email_valid.setVisibility(View.GONE);
                }

            }
        });


    }

    private void checkEmailAbailable(String email) {
        String url3 = mainUrl + "api/email_chack";

        String emailfind = et_profile_email.getText().toString();

        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("id", id);
            reg_data.put("email", emailfind);


        } catch (JSONException e) {
            e.printStackTrace();
        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url3, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                ProfileEditResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), ProfileEditResponse.class);

                if (jsonArray.getError().equalsIgnoreCase("Email Available")) {

                    et_profile_email_checker.setVisibility(View.VISIBLE);
                    et_profile_email_checker.setText("Email Available");
                    et_profile_email_checker.setTextColor(Color.parseColor("#009432"));
                    et_email_valid.setVisibility(View.VISIBLE);

                } else if (jsonArray.getError().equalsIgnoreCase("Old Email Available")) {

                    et_profile_email_checker.setVisibility(View.VISIBLE);
                    et_profile_email_checker.setText("Old Email Available");
                    et_profile_email_checker.setTextColor(Color.parseColor("#009432"));
                    et_email_valid.setVisibility(View.VISIBLE);
                } else if (jsonArray.getError().equalsIgnoreCase("Email Not Available")) {

                    et_profile_email_checker.setVisibility(View.VISIBLE);
                    et_profile_email_checker.setText("Email Not Available");
                    et_profile_email_checker.setTextColor(Color.parseColor("#EA2027"));
                    et_email_valid.setVisibility(View.GONE);
                } else {


                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ProfileInfoEditActivity.this, error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }


    public static void NomineeDateSet(int year, int month, int day) {


        et_profile_nomine_Date_Of_Birth.setText(month + "/" + day + "/" + year);

    }

    public static void UserDateSet(int year, int month, int day) {

        et_profile_date_Of_Birth.setText(month + "/" + day + "/" + year);

    }

    private void UplodeImage() {


        String pictureurl = mainUrl + "api/app_image_update";





        StringRequest request = new StringRequest(Request.Method.POST, pictureurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                Toast.makeText(getApplicationContext(), "Profile Picture Uplode Successfully", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<String, String>();
                map.put("security_error", security);
                map.put("id", id);
                map.put("picture_Url", encodeImageString);
                return map;
            }
        };

        MySingleTon.getInstance(context).addToRequestQueue(request);

    }


    private void changeImage() {
        final LinearLayout choseLoaction_Gallery, choseLoaction_Camera;
        final AlertDialog create = new AlertDialog.Builder(this).create();
        View itemView = getLayoutInflater().inflate(R.layout.dialog_layout_for_profile_image_source, null);

        choseLoaction_Gallery = itemView.findViewById(R.id.alrt_ChoseLoaction_Gallery);
        choseLoaction_Camera = itemView.findViewById(R.id.alrt_ChoseLoaction_Camera);

        choseLoaction_Gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGalleryImage();
                create.dismiss();
            }
        });
        choseLoaction_Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCameraImage();
                create.dismiss();
            }
        });

        create.setCancelable(true);
        create.setView(itemView);
        create.show();


    }
    private void getCameraImage() {
        Dexter.withActivity(ProfileInfoEditActivity.this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), PICK_CAMERA_REQUEST);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void getGalleryImage() {

        Dexter.withActivity(ProfileInfoEditActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), 1);
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 103) {
            if (grantResults[0] == 0) {
                startActivityForResult(new Intent("android.media.action.IMAGE_CAPTURE"), 709);
            } else {
                Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode != 21) {
        } else {

            if (grantResults.length <= 0 || grantResults[0] != 0) {
                Toast.makeText(this, "You have to grant permission to upload image..", Toast.LENGTH_SHORT).show();
            } else {
                uploadImage();
            }
        }
    }


    private void uploadImage() {
        Log.e("permission accepted", "permission accepted");
        Intent intent = new Intent("android.intent.action.PICK");
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_PICTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            Uri filepath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filepath);

                bitmap = BitmapFactory.decodeStream(inputStream);

                Bitmap reducebitmap = Bitmap.createScaledBitmap(bitmap, 300, 300, true);

                profile_uplodeImage.setVisibility(View.VISIBLE);

                editProfile_image.setImageBitmap(reducebitmap);
                encodeBitmapImage(reducebitmap);
            } catch (Exception ex) {

            }
        }else if (requestCode == PICK_CAMERA_REQUEST) {
            Log.e("requestCode", "709");
            if (data != null) {
                Bitmap bitmap2 = (Bitmap) data.getExtras().get("data");
                bitmap = bitmap2;

                Bitmap reducebitmap = Bitmap.createScaledBitmap(bitmap2, 300, 300, true);

                editProfile_image.setImageBitmap(reducebitmap);
                profile_uplodeImage.setVisibility(View.VISIBLE);
                encodeBitmapImage(reducebitmap);
            }
        }

    }


    private void updateInformation() {

        if (!et_profile_email_checker.getText().toString().equals("Email Not Available")) {
            if (email.matches(emailPattern)) {
                upData();
            } else {
                Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            //or
            et_email_valid.setVisibility(View.GONE);
        }


    }

    private void encodeBitmapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();

        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private void upData() {

        String profileInfoUrl = mainUrl + "api/app_profile_update";

        JSONObject reg_data = new JSONObject();

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        name = et_Profile_Name.getText().toString();
        username = et_profile_userId.getText().toString();
        email = et_profile_email.getText().toString();
        mobileNo = et_Profile_PhoneNumber.getText().toString();
        address = et_profile_address.getText().toString();
        sex = et_profile_sex.getText().toString();
        votarId = et_Profile_NID.getText().toString();
        nomineName = et_profile_nomine_Name.getText().toString();
        nomineReleation = et_profile_nomine_Releation.getText().toString();
        nomineDateOfBirth = et_profile_nomine_Date_Of_Birth.getText().toString();
        fatherName = et_Profile_FatherName.getText().toString();
        motherName = et_Profile_MotherName.getText().toString();
        religion = et_profile_religion.getText().toString();
        dateOfBirth = et_profile_date_Of_Birth.getText().toString();
        education=et_profile_education.getText().toString();
        occuption=et_profile_occuption.getText().toString();


        try {
            reg_data.put("security_error", "tec71");
            reg_data.put("id", id);
            reg_data.put("name", name);
            reg_data.put("email", email);
            reg_data.put("username", username);
            reg_data.put("father_Name", fatherName);
            reg_data.put("mother_Name", motherName);
            reg_data.put("mobile_No", mobileNo);
            reg_data.put("address", address);
            reg_data.put("sex", sex);
            reg_data.put("votar_Id", votarId);
            reg_data.put("nomine_Name", nomineName);
            reg_data.put("nomine_Releation", nomineReleation);
            reg_data.put("nomine_Date_Of_Birth", nomineDateOfBirth);
            reg_data.put("religion", religion);
            reg_data.put("date_Of_Birth", dateOfBirth);
            reg_data.put("edu_qualifications", education);
            reg_data.put("occuption", occuption);


        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, profileInfoUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                ProfileEditResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), ProfileEditResponse.class);

                if (jsonArray.getError().equals("successfully update")) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileInfoEditActivity.this, "Your information update Successfully.", Toast.LENGTH_SHORT).show();


                    ProfileInfoModel profileInfoModel = new ProfileInfoModel(name, username, email, mobileNo, address, sex, votarId, nomineName, nomineReleation, nomineDateOfBirth, fatherName, motherName, religion, dateOfBirth);
                    profileInfoModelList.clear();

                    profileInfoModelList.add(profileInfoModel);

                    profileInfoAdapter.updateData(profileInfoModelList);
                    profileInfoAdapter.notifyDataSetChanged();


                } else if (jsonArray.getError().equals("error update")) {
                    progressDialog.dismiss();
                    Toast.makeText(ProfileInfoEditActivity.this, "Your information error update", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    private void netWorkError(VolleyError error) {
        if (error instanceof NetworkError) {
            Toast.makeText(ProfileInfoEditActivity.this, "No internet connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(ProfileInfoEditActivity.this, "Our server is busy please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(ProfileInfoEditActivity.this, "AuthFailure Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(ProfileInfoEditActivity.this, "Parse Error please try again later", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NoConnectionError) {
            Toast.makeText(ProfileInfoEditActivity.this, "No connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof TimeoutError) {
            Toast.makeText(ProfileInfoEditActivity.this, "Server time out please try again later", Toast.LENGTH_SHORT).show();
        }
        error.printStackTrace();

    }

    private void loadData() {

        String profileInfoUrl = mainUrl + "api/app_profile_wiew";

        JSONObject reg_data = new JSONObject();

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", id);
//            reg_data.put("username", str_userId);
//            reg_data.put("mobile_number", str_mobile);
//            reg_data.put("Joining_code", str_joiningCode);
//            reg_data.put("sponser_u_id", str_sponsorUserId);
//            reg_data.put("placement_u_id", str_placementUserId);
//            reg_data.put("selected_n", str_placementUserType);


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


                        et_Profile_Name.setText(name);
                        et_profile_userId.setText(username);
                        et_Profile_FatherName.setText(fatherName);
                        et_Profile_MotherName.setText(motherName);
                        et_Profile_PhoneNumber.setText(mobileNo);
                        et_profile_email.setText(email);
                        et_profile_address.setText(address);
                        et_profile_sex.setText(sex);
                        et_Profile_NID.setText(votarId);
                        et_profile_nomine_Name.setText(nomineName);
                        et_profile_nomine_Releation.setText(nomineReleation);
                        et_profile_nomine_Date_Of_Birth.setText(nomineDateOfBirth);
                        et_profile_religion.setText(religion);

                        et_profile_date_Of_Birth.setText(dateOfBirth);

                        et_profile_education.setText(education);
                        et_profile_occuption.setText(occuption);


                        progressDialog.dismiss();
                    }


                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
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

    private void findView() {


        et_Profile_Name = findViewById(R.id.et_Profile_Name);
        et_profile_userId = findViewById(R.id.et_profile_userId);
        et_Profile_FatherName = findViewById(R.id.et_Profile_FatherName);
        et_Profile_MotherName = findViewById(R.id.et_Profile_MotherName);
        et_Profile_PhoneNumber = findViewById(R.id.et_Profile_PhoneNumber);
        et_profile_address = findViewById(R.id.et_profile_address);
        et_profile_email = findViewById(R.id.et_profile_email);

        et_profile_sex = findViewById(R.id.et_profile_sex);
        et_Profile_NID = findViewById(R.id.et_Profile_NID);
        et_profile_nomine_Name = findViewById(R.id.et_profile_nomine_Name);
        et_profile_nomine_Releation = findViewById(R.id.et_profile_nomine_Releation);

        et_profile_nomine_Date_Of_Birth = findViewById(R.id.et_profile_nomine_Date_Of_Birth);
        et_profile_religion = findViewById(R.id.et_profile_religion);
        et_profile_date_Of_Birth = findViewById(R.id.et_profile_date_Of_Birth);

        btn_UpdateInfo = findViewById(R.id.btn_profileSettings_UpdateInfo);

        choseImage = findViewById(R.id.choseImage);

        editProfile_image = findViewById(R.id.editProfile_image);
        profile_uplodeImage = findViewById(R.id.profile_uplodeImage);
        et_profile_email_checker = findViewById(R.id.et_profile_email_checker);
        et_email_valid = findViewById(R.id.et_email_valid);

        nominee_birth_btn = findViewById(R.id.nominee_birth_btn);
        your_birth_btn = findViewById(R.id.your_birth_btn);
        et_profile_education = findViewById(R.id.et_profile_education);
        et_profile_occuption = findViewById(R.id.et_profile_occuption);


    }


    public static String getRealPathFromUri(Context context, Uri uri) {
        String result = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj, null, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(proj[0]);
                result = cursor.getString(column_index);
            }
            cursor.close();
        }
        if (result == null) {
            result = "Not found";
        }
        return result;
    }


}