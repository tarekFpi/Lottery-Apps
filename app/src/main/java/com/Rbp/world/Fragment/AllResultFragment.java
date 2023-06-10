package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Rbp.world.Adapter.AllResult_LotteryAdapter;
import com.Rbp.world.Adapter.MylotteryAdapter;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.Model.AllResult_Lotterymodel;
import com.Rbp.world.Model.LotterySingleModel;
import com.Rbp.world.Model.MylotteryModel;
import com.Rbp.world.R;
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
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import io.supercharge.shimmerlayout.ShimmerLayout;


public class AllResultFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AllResultFragment() {

    }

    private RecyclerView recyclerView_result;

    private List<AllResult_Lotterymodel> allResult_lotterymodels=new ArrayList<>();

    private AllResult_LotteryAdapter allResult_lotteryAdapter;

    private Context context;

    private String mainUrl = MainValues.URL;

    private String message,id,status,
            lotary_name,date,code;

    private double point;

    private String winner_amount,prize_number;

    private ProgressDialog progressdialog;

    private SwipeRefreshLayout swipeRefreshLayout;


    public static AllResultFragment newInstance(String param1, String param2) {
        AllResultFragment fragment = new AllResultFragment();
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
        View view= inflater.inflate(R.layout.fragment_all_result, container, false);

         context = getActivity().getApplicationContext();

         recyclerView_result=(RecyclerView)view.findViewById(R.id.recycl_mult_userLottery);
          recyclerView_result.setHasFixedSize(true);
          recyclerView_result.setLayoutManager(new LinearLayoutManager(getContext()));

        progressdialog = new ProgressDialog(view.getContext());

        swipeRefreshLayout=(SwipeRefreshLayout)view.findViewById(R.id.swipe_RefreshLayout_allResult);

        loadData();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadData();

                        swipeRefreshLayout.setRefreshing(false);

                    }
                }, 2000);
            }
        });


        return view;
    }

    private void loadData(){

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);


        String url = mainUrl + "api/all_lottary";

        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(context, "response:"+response, Toast.LENGTH_SHORT).show();
                try {

                    JSONObject jsonObMain=new JSONObject(response);
                    message=jsonObMain.getString("message");

                    if(message.contains("Data is available!")){

                        JSONArray jsonArray = jsonObMain.getJSONArray("lotery");

                        allResult_lotterymodels.clear();

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


                            date= jsonObject.getString("date");

                            AllResult_Lotterymodel lottery_model=
                                    new AllResult_Lotterymodel(id,code,point,date,lotary_name,winner_amount,prize_number,"",status);

                            allResult_lotterymodels.add(lottery_model);
                        }
                        allResult_lotteryAdapter= new AllResult_LotteryAdapter(allResult_lotterymodels,context);
                        recyclerView_result.setAdapter(allResult_lotteryAdapter);
                        allResult_lotteryAdapter.notifyDataSetChanged();
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