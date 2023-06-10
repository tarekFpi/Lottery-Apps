package com.Rbp.world;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.Rbp.world.Adapter.Image_sliderAdapter;
import com.Rbp.world.Adapter.ProfileInfoAdapter;
import com.Rbp.world.Fragment.ProfileFragment;
import com.Rbp.world.Model.ProfileInfoModel;
import com.Rbp.world.Model.Slider_Model;
import com.airbnb.lottie.LottieAnimationView;
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
import com.anupkumarpanwar.scratchview.ScratchView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.JsonResponse.Home_page_Response;
import com.Rbp.world.JsonResponse.PasswordChangeResponse;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.Rbp.world.Team.TeamActivity;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;

public class DashboardActivity extends AppCompatActivity {

    private static final int MY_SOCKET_TIMEOUT_MS = 20000;
    private CardView des_income_history, des_lottery, transctionlog;

    TextView day_date;

    private Image_sliderAdapter image_sliderAdapter;

    private List<Slider_Model>slider_modelList=new ArrayList<>();

    TextView des_user_ID, mobile_recharge_text,withdraw_balance_text;

    String finddate;

    LinearLayout des_profile, team, des_transfer_reserve_balance, des_sending_report, des_password, des_processBalance, des_business_center,
            des_placement_list, des_sponsor_list, des_transaction_pin, des_transfer_balance, des_withdraw_balance,
            des_withdraw_method, des_daily_task, des_imageuplode, des_income_c_history,
            des_Mobile_recharge, des_bgColor,des_bgColor2,gift_linerlayout,tab_ofBalance,des_rakuten;

    String webUrl = null,message,message_card_balance11,message_card_balance22,scratch_card_balance="",scratch_card2_balance="",scratch_balance1="",scratch_balance2="",
    message_scratch_card1,message_scratch_card2;


    TextView balanceDataShow;
    CircleImageView des_profile_pic, des_image_uplode_pic,imageView_user;
    AppSessionManager sessionManager;

    CardView shadowVisible,cardView_scratch_card,cardView_scratch_card2;

    ImageView logout;

    String imageUrl;

    String data = null;
    String data2 = null;
    String data3 = null;
    String data4 = null;
    String data5 = null;
    String data6 = null;
    String data7 = null;
    String data8 = null;
    String data9 = null;
    String data10 = null;
    String data11 = null;
    String data12 = null;
    String data13 = null;
    String id1, id2, id3, id4, id5, id6, id7, id8, id9, id10, id11, id12, id13;
    String name, UID, ref_uid,team_designation;
    String team_A, team_B, team_C;
    Context context;
    String userId_login, mainUrl, security,userName;

    private TextView des_mainbalance, des_user_instant_income, des_daily_income, des_total_income, des_total_Withdraw
            ,des_shopping_balance,des_team_icome_balance,des_reward_icome_balance,des_royal_income_balance,des_bonus_income_balance;//Balance show

    private HorizontalScrollView horizonScrollmoneyInfo;

    private LinearLayout linearLayout_tabBalance,linearLayout_tabBalance2,linear_Winnings_all,linear_winnings_person;

    private ImageView imageView_tabStart,imageView_tabEnd;

    private TextView textView_taptotal_Balance,textView_tapSecond_total,textView_userName,textView_userGmail;

    private int tab_status =0;

    private Toolbar toolbar;

    private DrawerLayout drawerLayout;

    private NavigationView navigationView;

    private ActionBarDrawerToggle toggleButton;

    private LottieAnimationView lottieAnimationView;

    //ic_process_balance
    private View view_navigation;

    private ScratchView scratchView,scratchView2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        context = getApplicationContext();
        date();

        scratchView = findViewById(R.id.scratch_view);

        scratchView2 = findViewById(R.id.scratch_view2);


        sessionManager = new AppSessionManager(this);
         if (sessionManager.checkLogin())
            finish();

        // get user data from session
        HashMap<String, String> user = sessionManager.getUserDetails();
        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        toolbar=(Toolbar)findViewById(R.id.toolbar_home_page);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.navigation_id);

        toggleButton=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        toggleButton.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));

        drawerLayout.addDrawerListener(toggleButton);
        toggleButton.syncState();


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()) {

                    case R.id.side_home:
                    drawerLayout.closeDrawer(GravityCompat.START);
                    break;

                    case R.id.Side_logOut:

                        sessionManager.logoutUser();
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.side_changePassword:

                        Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                        intent5.putExtra("FRGID", "04");
                        startActivity(intent5);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.side_profile:

                        Intent intentProfile = new Intent(DashboardActivity.this, MemberFromLoadActivityActivity.class);
                        intentProfile.putExtra("Techno", "01");

                        startActivity(intentProfile);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.cart_lotteryShow:

                        Intent intentCart = new Intent(DashboardActivity.this,Add_toCartShowActivity.class);
                        intentCart.putExtra("Techno", "01");

                        startActivity(intentCart);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.myWinnings:

                        Intent result = new Intent(DashboardActivity.this,MemberReportLoadActivity.class);
                        result.putExtra("FRGID", "05");
                        startActivity(result);

                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;


                    case R.id.gift_lottery:

                        Intent result_gift = new Intent(DashboardActivity.this,LotteryGiftActivity.class);
                        startActivity(result_gift);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
                return false;
            }
        });

        view_navigation =navigationView.inflateHeaderView(R.layout.header_menu);


        SliderView sliderView = findViewById(R.id.image_slider_homepage);
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM); //set indicator animation by using IndicatorAnimationType. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(4); //set scroll delay in seconds :
        sliderView.startAutoCycle();


        slider_modelList.add(new Slider_Model( R.drawable.lottery11));
        slider_modelList.add(new Slider_Model( R.drawable.lot1));
        slider_modelList.add(new Slider_Model( R.drawable.lot2));
        slider_modelList.add(new Slider_Model( R.drawable.lot3));
        slider_modelList.add(new Slider_Model( R.drawable.lot4));
        slider_modelList.add(new Slider_Model( R.drawable.lot5));

        image_sliderAdapter =new Image_sliderAdapter(getApplicationContext(),slider_modelList);
        sliderView.setSliderAdapter(image_sliderAdapter);


        imageView_user =view_navigation.findViewById(R.id.imageView_navUser);
/*
        des_mainbalance = findViewById(R.id.des_mainbalance);
        des_user_instant_income = findViewById(R.id.des_user_instant_income);
        des_daily_income = findViewById(R.id.des_daily_income);
        des_total_income = findViewById(R.id.des_total_income);
        des_total_Withdraw = findViewById(R.id.des_total_Withdraw);
        des_shopping_balance = findViewById(R.id.des_shopping_balance);
*/

        cardView_scratch_card=(CardView)findViewById(R.id.cardView_scratch_card);

        cardView_scratch_card2=(CardView)findViewById(R.id.cardView_scratch_card2);

        linear_Winnings_all = findViewById(R.id.Winnings_all);
        linear_winnings_person = findViewById(R.id.des_winnings_person);

        linear_winnings_person.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent result = new Intent(DashboardActivity.this,MemberReportLoadActivity.class);
                result.putExtra("FRGID", "05");
                startActivity(result);
            }
        });

        linear_Winnings_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent_allresult = new Intent(DashboardActivity.this,MemberReportLoadActivity.class);
                intent_allresult.putExtra("FRGID", "08");
                startActivity(intent_allresult);
            }
        });

        des_transaction_pin = findViewById(R.id.des_transaction_pin);

        des_withdraw_balance = findViewById(R.id.des_withdraw_balance);
        des_withdraw_method = findViewById(R.id.des_withdraw_method);
       // des_royal_income_balance = findViewById(R.id.des_royal_income_balance);
        des_bonus_income_balance = findViewById(R.id.des_bonus_income_balance);


        des_income_history = findViewById(R.id.des_income_history);

        des_lottery = findViewById(R.id.des_lottery);

        des_bgColor = findViewById(R.id.linear_bgColors);

        des_bgColor2 = findViewById(R.id.linear_bgColors2);

        day_date = findViewById(R.id.day_date_text);

        balanceDataShow=findViewById(R.id.tapforInfo);
        shadowVisible=findViewById(R.id.shadowVisible);

        tab_ofBalance=(LinearLayout)findViewById(R.id.tab_ofBalance);

        tab_ofBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentBalance = new Intent(DashboardActivity.this,TabBalanceActivity.class);
                startActivity(intentBalance);
            }
        });

        gift_linerlayout=(LinearLayout)findViewById(R.id.gift_card);

        gift_linerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentgift = new Intent(DashboardActivity.this,LotteryGiftActivity.class);
                startActivity(intentgift);
            }
        });

        des_processBalance = findViewById(R.id.des_processBalance);
     //   des_daily_task = findViewById(R.id.des_daily_task);

        day_date.setText("Today: " + finddate);


        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        AlertDialog alertDialog = dialogBuilder.create();
        LayoutInflater inflater = this.getLayoutInflater();
        alertDialog.setView(inflater.inflate(R.layout.tab_lottery_alert, null));
        alertDialog.setTitle("Please Wite..");
        alertDialog.setCancelable(false);


        textView_taptotal_Balance=(TextView)findViewById(R.id.text_ScarsCartAmount);
        textView_tapSecond_total=(TextView)findViewById(R.id.text_ScarsCart2Amount);

     //   imageView_tabStart =(ImageView)findViewById(R.id.imageview_tapIcon);

        lottieAnimationView=(LottieAnimationView)findViewById(R.id.aminmation_id);

        linearLayout_tabBalance=(LinearLayout)findViewById(R.id.linerLayout_total_balance);

        linearLayout_tabBalance2=(LinearLayout)findViewById(R.id.linerLayout_total_balance2);

        scratchView.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {

              //  Show_Scratch_card();
                linearLayout_tabBalance.setVisibility(View.VISIBLE);

                Toast.makeText(context, "one", Toast.LENGTH_SHORT).show();

                MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                        .setTitle("Selected One Scratch card")
                        .setAnimation(R.raw.congratulations)
                        .setMessage("Dear  Candidate Congratulations you are shorlisted   for scratch card")
                        .setCancelable(false)
                        .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                            }
                        })
                        .build();
                mDialog.show();

                Notification_Add("Selected One Scratch card");

                des_bgColor.setBackgroundColor(getResources().getColor(R.color.purple_200));

            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {

            }
        });

        scratchView2.setRevealListener(new ScratchView.IRevealListener() {
            @Override
            public void onRevealed(ScratchView scratchView) {

                linearLayout_tabBalance2.setVisibility(View.VISIBLE);
                Toast.makeText(context, "Two", Toast.LENGTH_SHORT).show();

                des_bgColor2.setBackgroundColor(getResources().getColor(R.color.purple_200));

                MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                        .setTitle("Selected Two Scratch card")
                        .setAnimation(R.raw.congratulations)
                        .setMessage("Dear  Candidate Congratulations you are shorlisted   for scratch card")
                        .setCancelable(false)
                        .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                            }
                        })
                        .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                            @Override
                            public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                            }
                        })
                        .build();
                mDialog.show();

                Notification_Add("Selected Two Scratch card");
            }

            @Override
            public void onRevealPercentChangedListener(ScratchView scratchView, float percent) {

            }
        });

        des_placement_list = findViewById(R.id.des_placement_list);

        des_sponsor_list = findViewById(R.id.des_sponsor_list);

        des_lottery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DashboardActivity.this, LotteryNameActivity.class);
                startActivity(intent);
            }
        });


        withdraw_balance_text = findViewById(R.id.withdraw_balance_text);
       // des_team_icome_balance = findViewById(R.id.des_team_icome_balance);
       // des_reward_icome_balance = findViewById(R.id.des_reward_icome_balance);


        withdraw_balance_text.setSelected(true);

        des_income_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "02");
                startActivity(intent5);

            }
        });


        des_processBalance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "06");
                startActivity(intent5);
            }
        });


        des_placement_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(DashboardActivity.this, MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "12");
                startActivity(intent5);
            }
        });

        des_sponsor_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(DashboardActivity.this, MemberFromLoadActivityActivity.class);
                intent5.putExtra("Techno", "13");
                startActivity(intent5);
            }
        });

//        des_transaction_pin,des_transfer_balance,des_withdraw_balance,des_withdraw_method;

        des_transaction_pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "14");
                startActivity(intent5);
            }
        });

        des_withdraw_balance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "16");
                startActivity(intent5);
            }
        });

        des_withdraw_method.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent5 = new Intent(DashboardActivity.this, MemberReportLoadActivity.class);
                intent5.putExtra("FRGID", "17");
                startActivity(intent5);
            }
        });

         loadProfileInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();


            String ScratchUri = mainUrl +"api/scratch_card_lottary";

            StringRequest stringrequst_scratch_card =new StringRequest(Request.Method.POST, ScratchUri, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject=new JSONObject(response);

                        message_scratch_card1=jsonObject.getString("error").trim();

                        if(!message_scratch_card1.contains("0")){

                            cardView_scratch_card.setVisibility(View.VISIBLE);
                            //linearLayout_tabBalance.setVisibility(View.VISIBLE);

                           scratch_card_balance= jsonObject.getString("value");

                          //  Toast.makeText(context, "message_one:"+message_card_balance11, Toast.LENGTH_SHORT).show();

                            textView_taptotal_Balance.setText("One Scratch card:\t"+jsonObject.getString("value"));

                        }else{
                            cardView_scratch_card.setVisibility(View.GONE);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    netWorkError(error);
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("security_error",security);
                    hashMap.put("id", userId_login);

                    return hashMap;
                }
            };

            RequestQueue requestQueue1= Volley.newRequestQueue(DashboardActivity.this);
            requestQueue1.add(stringrequst_scratch_card);



          //show Scratch Second
            String ScratchUri_2 = mainUrl + "api/scratch_card";

            StringRequest stringrequst_scratch_card2 =new StringRequest(Request.Method.POST, ScratchUri_2, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {

                        JSONObject jsonObject=new JSONObject(response);

                        message_scratch_card2=jsonObject.getString("error").trim();

                        if(!message_scratch_card2.contains("0")){

                            cardView_scratch_card2.setVisibility(View.VISIBLE);

                            textView_tapSecond_total.setText("Second Scratch card:\t"+jsonObject.getString("value"));

                        }else {

                            cardView_scratch_card2.setVisibility(View.VISIBLE);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    netWorkError(error);
                }
            }){
                @Override
                protected Map<String, String> getParams() {

                    Map<String, String> hashMap = new HashMap<String, String>();
                    hashMap.put("security_error",security);
                    hashMap.put("id", userId_login);

                    return hashMap;
                }
            };

            RequestQueue requestQueue2= Volley.newRequestQueue(DashboardActivity.this);
           requestQueue2.add(stringrequst_scratch_card2);

    }

    ///user Image show
    private void loadProfileInfo() {
        String profileInfoUrl = mainUrl + "api/app_profile_wiew";

        JSONObject reg_data = new JSONObject();


        try {
            reg_data.put("security_error", security);
            reg_data.put("id", userId_login);



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

                       // name = profileInfodata.getString("name");
                        String  username = profileInfodata.getString("username");
                       String email = profileInfodata.getString("email");

                        String picture=profileInfodata.getString("picture_Url");

                        textView_userName=(TextView)view_navigation.findViewById(R.id.textNavUserName);
                        textView_userName.setText(""+username);

                        textView_userGmail =(TextView)view_navigation.findViewById(R.id.textNavUsergmail);

                        textView_userGmail.setText(""+email);


                        imageUrl=mainUrl+picture;
                        Picasso.get()
                                .load(imageUrl)
                                .placeholder(R.drawable.ic_user_male)
                                .into(imageView_user);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);
            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                20000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);

    }


    //show Scratch_card frist
   private void Show_Scratch_card(){

       String ScratchUri = mainUrl + "api/scratch_card";

       StringRequest stringrequst_scratch_card =new StringRequest(Request.Method.POST, ScratchUri, new Response.Listener<String>() {
           @Override
           public void onResponse(String response) {

               try {

          JSONObject jsonObject=new JSONObject(response);

        message_card_balance11=jsonObject.getString("error");

          if(!message_card_balance11.contains("0")){

       cardView_scratch_card.setVisibility(View.VISIBLE);
        linearLayout_tabBalance.setVisibility(View.VISIBLE);



       textView_taptotal_Balance.setText("One Scratch card:\t"+jsonObject.getString("value"));
       des_bgColor.setBackgroundColor(getResources().getColor(R.color.purple_200));


              MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                      .setTitle("Selected Scratch card")
                      .setAnimation(R.raw.congratulations)
                      .setMessage("Dear  Candidate Congratulations you are shorlisted   for scratch card")
                      .setCancelable(false)
                      .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                          @Override
                          public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                              dialogInterface.dismiss();

                          }
                      })
                      .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                          @Override
                          public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                              dialogInterface.dismiss();

                          }
                      })
                      .build();
              mDialog.show();

              Notification_Add("Selected Scratch card");
         } else {

          /*    MaterialDialog materialDialog = new MaterialDialog.Builder(DashboardActivity.this)
                      .setTitle("Sorry ??")
                      .setAnimation(R.raw.sorry)
                      .setMessage("Scratch card Not Found")
                      .setCancelable(false)
                      .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                          @Override
                          public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                              dialogInterface.dismiss();

                          }
                      })
                      .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                          @Override
                          public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                              dialogInterface.dismiss();

                          }
                      })
                      .build();

              materialDialog.show();*/

    Toast.makeText(context, "message_one:"+message_card_balance11, Toast.LENGTH_SHORT).show();

        textView_taptotal_Balance.setVisibility(View.GONE);
        }

     } catch (JSONException e) {
              e.printStackTrace();
         }

     }

       }, new Response.ErrorListener() {
           @Override
           public void onErrorResponse(VolleyError error) {
               netWorkError(error);
           }
       }){
           @Override
           protected Map<String, String> getParams() {

               Map<String, String> hashMap = new HashMap<String, String>();
               hashMap.put("security_error",security);
               hashMap.put("id", userId_login);

               return hashMap;
           }
       };

       RequestQueue requestQueue= Volley.newRequestQueue(DashboardActivity.this);
       requestQueue.add(stringrequst_scratch_card);

       Show_ScratchSecond_card();
   }

   //show Scratch Second
    private void Show_ScratchSecond_card(){


        String ScratchUri = mainUrl + "api/scratch_card_lottary";

        StringRequest stringrequst_scratch_card2 =new StringRequest(Request.Method.POST, ScratchUri, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObject=new JSONObject(response);

                    message_card_balance22=jsonObject.getString("error");

                    if(!message_card_balance22.contains("0")){

                        cardView_scratch_card2.setVisibility(View.VISIBLE);

                        linearLayout_tabBalance2.setVisibility(View.VISIBLE);

                      //  scratch_balance2=jsonObject.getString("value");

                        textView_tapSecond_total.setText("Second Scratch card:\t"+jsonObject.getString("value"));
                        des_bgColor2.setBackgroundColor(getResources().getColor(R.color.purple_200));

                        MaterialDialog mDialog = new MaterialDialog.Builder(DashboardActivity.this)
                                .setTitle("Selected Scratch card")
                                .setAnimation(R.raw.congratulations)
                                .setMessage("Dear  Candidate Congratulations you are shorlisted   for scratch card")
                                .setCancelable(false)
                                .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                    }
                                })
                                .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                    }
                                })
                                .build();
                        mDialog.show();

                        Notification_Add("Selected Scratch card");
                    }else {

                     /*   MaterialDialog materialDialog = new MaterialDialog.Builder(DashboardActivity.this)
                                .setTitle("Sorry ??")
                                .setAnimation(R.raw.sorry)
                                .setMessage("Scratch card Not Found")
                                .setCancelable(false)
                                .setPositiveButton("Got it", new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                    }
                                })
                                .setNegativeButton("Cancel", new AbstractDialog.OnClickListener() {
                                    @Override
                                    public void onClick(dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();

                                    }
                                })
                                .build();

                        materialDialog.show();*/

                        Toast.makeText(context, "messageSecond:"+message_card_balance22, Toast.LENGTH_SHORT).show();

                        cardView_scratch_card2.setVisibility(View.VISIBLE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);
            }
        }){
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("security_error",security);
                hashMap.put("id", userId_login);

                return hashMap;
            }
        };

        RequestQueue requestQueue2= Volley.newRequestQueue(DashboardActivity.this);
        requestQueue2.add(stringrequst_scratch_card2);

    }

    private void Notification_Add(String message){

        if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            NotificationChannel notificationChannel=new NotificationChannel("Channel","Channel", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager=(NotificationManager)getApplicationContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notification= new NotificationCompat.Builder(getApplicationContext(),"Channel")
                .setContentTitle("Congratulations")
                .setSmallIcon(R.drawable.ic_notifications)
                .setContentText(message)
                .setAutoCancel(true)
                //.setSound(Uri.)
                .setWhen(System.currentTimeMillis());

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(1,notification.build());

    }



    private void DailyTaskUrl() {

        String personalUri = mainUrl + "api/app_dailytask";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject personalData = new JSONObject();

        try {
            personalData.put("security_error", security);
            personalData.put("id", userId_login);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, personalUri, personalData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                PasswordChangeResponse jsonArray = null;
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.serializeNulls();
                Gson gson = gsonBuilder.create();
                jsonArray = gson.fromJson(response.toString(), PasswordChangeResponse.class);

                try {
                    progressDialog.dismiss();
                    JSONArray jsonArrayb = response.getJSONArray("tbl_web_link");
                    JSONObject method_list = jsonArrayb.getJSONObject(0);

                    webUrl = method_list.getString("link");

                } catch (JSONException e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                }

                try {
                    String err = response.getString("error");

                    if (err.equals("available")) {
                      //  int count_add = response.getInt("count_add");
                        if (webUrl != null) {
                            Intent intent5 = new Intent(DashboardActivity.this, DailyTaskActivity.class);
                            intent5.putExtra("webUrl", webUrl);
                           // intent5.putExtra("count_add", count_add);
                            startActivity(intent5);
//                            Toast.makeText(context, webUrl, Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (err.equals("done")) {
                        if (webUrl == null) {
                            Intent intent5 = new Intent(DashboardActivity.this, DailyTaskActivity.class);
                            intent5.putExtra("webUrl", webUrl);
                            startActivity(intent5);
//                            Toast.makeText(context, webUrl, Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
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
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);
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

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to Exit?")
                .setNegativeButton("No", null)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        finishAffinity();
                    }
                }).show();
    }

    public void date() {


    finddate = new SimpleDateFormat("EEEE, dd MMM", Locale.getDefault()).format(Calendar.getInstance().getTime());


    }
}