package com.Rbp.world.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.Rbp.world.Adapter.LotteryNameAdapter;
import com.Rbp.world.Adapter.MylotteryAdapter;
import com.Rbp.world.Helpers.MainValues;
import com.Rbp.world.LotteryActivity;
import com.Rbp.world.LotteryNameActivity;
import com.Rbp.world.Model.LotteryNameModel;
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

public class MyLottery_Fragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<MylotteryModel> mylotteryModels=new ArrayList<>();
    private MylotteryAdapter mylotteryAdapter;

    private RecyclerView recyclerView;

    private Context context;

    private String mainUrl = MainValues.URL;

    private String message,id,status,
            lotary_name,date,code,userId_login;

    private double point;

    private int winner_amount,prize_number;

    private ProgressDialog progressdialog;

    private SharedPreferences preferences_userId;

    AppSessionManager sessionManager;

    public MyLottery_Fragment() {
     // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static MyLottery_Fragment newInstance(String param1, String param2) {
        MyLottery_Fragment fragment = new MyLottery_Fragment();
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
        View view= inflater.inflate(R.layout.fragment_my_lottery_, container, false);
        context = getActivity().getApplicationContext();

        preferences_userId =context.getSharedPreferences("preferences_userId", Context.MODE_PRIVATE);

        userId_login = preferences_userId.getString("user_id","User id Not Found..");

     //   Toast.makeText(context, "userId_login;p"+userId_login, Toast.LENGTH_SHORT).show();

      recyclerView=(RecyclerView)view.findViewById(R.id.recyclerview_myLottery);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        progressdialog = new ProgressDialog(view.getContext());

        progressdialog.show();
        progressdialog.setContentView(R.layout.custom_progrss_dialog);
        progressdialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

       //ShowData();

        return view;
    }


}