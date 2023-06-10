package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.Rbp.world.Adapter.LotterySingelResultAdapter;
import com.Rbp.world.Adapter.MylotteryAdapter;
import com.Rbp.world.DashboardActivity;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.LotterySingleModel;
import com.Rbp.world.Model.MylotteryModel;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;


public class LotteryResultFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private RecyclerView recyclerView_result;

 //   private List<LotterySingleModel> lotterySingleModelList=new ArrayList<>();

    // private LotterySingelResultAdapter lotterySingelResultAdapter;


    private List<MylotteryModel> mylotteryModels;

    private MylotteryAdapter adapter;

    private Context context;

    private String mainUrl = MainValues.URL;

    private String message,id,status,
            lotary_name,date,code,userId_login;

    private double point;

    private String winner_amount,prize_number;

    private ProgressDialog progressdialog;

    private SharedPreferences preferences_userId;

    private  int winnings_count=0;

    private TextView textView_winner;

    private SwipeRefreshLayout swipeRefreshLayout;

    private CardView cardView_total;

    AppSessionManager sessionManager;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LotteryResultFragment() {
        // Required empty public constructor
    }


    public static LotteryResultFragment newInstance(String param1, String param2) {
        LotteryResultFragment fragment = new LotteryResultFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_lottery_result, container, false);

        context = getActivity().getApplicationContext();

        mylotteryModels=new ArrayList<>();

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_RefreshLayout_singleResult);

        textView_winner=(TextView)view.findViewById(R.id.text_totalAnoutCart);

        recyclerView_result=(RecyclerView)view.findViewById(R.id.recycerview_resultSingle);
        recyclerView_result.setHasFixedSize(true);
        recyclerView_result.setLayoutManager(new LinearLayoutManager(getActivity()));

        preferences_userId =context.getSharedPreferences("preferences_userId", Context.MODE_PRIVATE);

        userId_login = preferences_userId.getString("user_id","User id Not Found..");

        progressdialog = new ProgressDialog(view.getContext());

        cardView_total=(CardView)view.findViewById(R.id.MywinningsLottery_Total);

        ShowData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        winnings_count=0;

                        ShowData();

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });

        return view;
    }

    private void ShowData() {

     ///   progressdialog.setContentView(R.layout.custom_progrss_dialog);
    //    progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        progressdialog.setTitle("Please Wait..");
        progressdialog.show();

        String url = mainUrl + "api/my_lottary";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject jsonObMain=new JSONObject(response);
                    message=jsonObMain.getString("message");

                    if(message.contains("Data is available!")){

                        JSONArray jsonArray = jsonObMain.getJSONArray("lotery");

                        mylotteryModels.clear();

                        for (int i = 0; i <jsonArray.length() ;i++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(i);

                            id= jsonObject.getString("id");
                            code= jsonObject.getString("code");
                            lotary_name= jsonObject.getString("lotary_name");
                            point= jsonObject.getDouble("point");
                            status= jsonObject.getString("winn_status");
                            winner_amount= jsonObject.getString("winner_amount");

                            if(jsonObject.getString("prize_number").contains("null")){

                                prize_number="0";

                            }else{

                                prize_number= jsonObject.getString("prize_number");
                            }

                            if(!jsonObject.getString("winn_status").contains("0")){

                                winnings_count++;
                            }

                            date= jsonObject.getString("date");


                            MylotteryModel lottery_model=
                                     new MylotteryModel(id,code,point,date,lotary_name,winner_amount,prize_number,"",status);

                            mylotteryModels.add(lottery_model);
                        }
                        adapter= new MylotteryAdapter(context,mylotteryModels);
                        recyclerView_result.setAdapter(adapter);
                        adapter.notifyDataSetChanged();

                        textView_winner.setText("My winnings:"+winnings_count);

                        progressdialog.dismiss();

                    }else if(message.contains("Data is Not available!")){

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        cardView_total.setVisibility(View.GONE);
                        progressdialog.dismiss();
                    }else{

                        progressdialog.dismiss();

                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressdialog.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                netWorkError(error);
                progressdialog.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> hashMap = new HashMap<String, String>();
                hashMap.put("security_error","tec71");
                hashMap.put("id",userId_login);

                return hashMap;
            }
        };

        RequestQueue requestQueue= Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

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