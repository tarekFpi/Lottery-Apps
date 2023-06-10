package com.Rbp.world;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class ImageUplodeActivity extends AppCompatActivity {

    private static final int REQUEST_PERMISSIONS = 100;
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int PICK_CAMERA_REQUEST = 2 ;
    private Bitmap bitmap;
    private String filePath;
    ImageView editProfile_image;
    Button profile_uplodeImage;
    ConstraintLayout choseImage;
    AppSessionManager sessionManager;

    String mainUrl, id, security;

    Context context;
    String encodeImageString;

    Uri picUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_uplode);

        editProfile_image = findViewById(R.id.editProfile_image);
        profile_uplodeImage = findViewById(R.id.profile_uplodeImage);

        sessionManager = new AppSessionManager(this);
        // get url
        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();

        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        id = user.get(AppSessionManager.KEY_SL_ID);
        security = user.get(AppSessionManager.KEY_SECURITY);
        context = getApplicationContext();

        choseImage = findViewById(R.id.choseImage);


        final String path = getIntent().getStringExtra("imageUrl");
        Picasso.get()
                .load(path)
                .placeholder(R.drawable.ic_profile)
                .into(editProfile_image);


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
        request.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
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
        Dexter.withActivity(ImageUplodeActivity.this)
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
        Dexter.withActivity(ImageUplodeActivity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(Intent.createChooser(intent, "Browse Image"), PICK_IMAGE_REQUEST);
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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
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
        } else if (requestCode == PICK_CAMERA_REQUEST) {
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

    private void encodeBitmapImage(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, byteArrayOutputStream);
        byte[] bytesofimage = byteArrayOutputStream.toByteArray();

        encodeImageString = android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {
        Intent go_Back = new Intent(ImageUplodeActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }
}