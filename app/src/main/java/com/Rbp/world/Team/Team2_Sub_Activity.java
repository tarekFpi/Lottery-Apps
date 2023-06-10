package com.Rbp.world.Team;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.snackbar.Snackbar;
import com.Rbp.world.DashboardActivity;
import com.Rbp.world.Helpers.MySingleTon;
import com.Rbp.world.R;
import com.Rbp.world.StoreSession.AppSessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class Team2_Sub_Activity extends AppCompatActivity {
    private int nodeCount = 1;
    private Node currentNode;


    TextView tab_text;
    LinearLayout back;
    Context context;
    GraphView graphView;

    final Graph graph = new Graph();
    String name, UID, ref_uid,team_designation;
    String team_A, team_B, team_C;

    String data2 = null;
    String data = null;
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
    String data_s_1, data_s_2, data_s_3, data_s_4, data_s_5, data_s_6, data_s_7, data_s_8, data_s_9, data_s_10, data_s_11, data_s_12, data_s_13;
    AppSessionManager sessionManager;
    String userId_login, mainUrl, security;

    EditText serachUser;
    Button searchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team2__sub_);

        SharedPreferences sp = getSharedPreferences("MyData2", MODE_PRIVATE);
        name = sp.getString("name", null);
        UID = sp.getString("UID", null);
        team_A = sp.getString("team_A", null);
        team_B = sp.getString("team_B", null);
        team_C = sp.getString("team_C", null);
        ref_uid = sp.getString("ref_uid", null);


        Intent intent = getIntent();
        data = intent.getStringExtra("data_s_1");
        data2 = intent.getStringExtra("data_s_2");
        data3 = intent.getStringExtra("data_s_3");
        data4 = intent.getStringExtra("data_s_4");
        data5 = intent.getStringExtra("data_s_5");
        data6 = intent.getStringExtra("data_s_6");
        data7 = intent.getStringExtra("data_s_7");
        data8 = intent.getStringExtra("data_s_8");
        data9 = intent.getStringExtra("data_s_9");
        data10 = intent.getStringExtra("data_s_10");
        data11 = intent.getStringExtra("data_s_11");
        data12 = intent.getStringExtra("data_s_12");
        data13 = intent.getStringExtra("data_s_13");

        id1 = intent.getStringExtra("id");
        id2 = intent.getStringExtra("id2");
        id3 = intent.getStringExtra("id3");
        id4 = intent.getStringExtra("id4");
        id5 = intent.getStringExtra("id5");
        id6 = intent.getStringExtra("id6");
        id7 = intent.getStringExtra("id7");
        id8 = intent.getStringExtra("id8");
        id9 = intent.getStringExtra("id9");
        id10 = intent.getStringExtra("id10");
        id11 = intent.getStringExtra("id11");
        id12 = intent.getStringExtra("id12");
        id13 = intent.getStringExtra("id13");

        data_s_1 = sp.getString("data", null);
        data_s_2 = sp.getString("data2", null);
        data_s_3 = sp.getString("data3", null);
        data_s_4 = sp.getString("data4", null);
        data_s_5 = sp.getString("data5", null);
        data_s_6 = sp.getString("data6", null);
        data_s_7 = sp.getString("data7", null);
        data_s_8 = sp.getString("data8", null);
        data_s_9 = sp.getString("data9", null);
        data_s_10 = sp.getString("data10", null);
        data_s_11 = sp.getString("data11", null);
        data_s_12 = sp.getString("data12", null);
        data_s_13 = sp.getString("data13", null);

//
//        data = sp.getString("data", null);
//        data2 = sp.getString("data2", null);
//        data3 = sp.getString("data3", null);
//        data4 = sp.getString("data4", null);
//        data5 = sp.getString("data5", null);
//        data6 = sp.getString("data6", null);
//        data7 = sp.getString("data7", null);
//        data8 = sp.getString("data8", null);
//        data9 = sp.getString("data9", null);
//        data10 = sp.getString("data10", null);
//        data11 = sp.getString("data11", null);
//        data12 = sp.getString("data12", null);
//        data13 = sp.getString("data13", null);
//
        serachUser = findViewById(R.id.teamUserSearch);
        searchBtn = findViewById(R.id.teamUserSearchBtn);

        context = getApplicationContext();

        sessionManager = new AppSessionManager(context);
        HashMap<String, String> user = sessionManager.getUserDetails();

        userId_login = user.get(AppSessionManager.KEY_SL_ID);
        mainUrl = user.get(AppSessionManager.KEY_BASEURL);
        security = user.get(AppSessionManager.KEY_SECURITY);

        back = findViewById(R.id.go_back_team2);

        tab_text = findViewById(R.id.team_tab_bar_text2);

        graphView = findViewById(R.id.graph2);

        tab_text.setText("Team");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String findUser = serachUser.getText().toString().trim();
                if (!TextUtils.isEmpty(findUser)) {

                    loadContentbySearch(findUser);
                }
            }
        });

        GrapgTree();

        Adpater();

    }

    private void GrapgTree() {
        final Node node1 = new Node(getNodeText(data));
        final Node node2 = new Node(getNodeText(data2));
        final Node node3 = new Node(getNodeText(data3));
        final Node node4 = new Node(getNodeText(data4));
        final Node node5 = new Node(getNodeText(data5));
        final Node node6 = new Node(getNodeText(data6));
        final Node node7 = new Node(getNodeText(data7));
        final Node node8 = new Node(getNodeText(data8));
        final Node node9 = new Node(getNodeText(data9));
        final Node node10 = new Node(getNodeText(data10));
        final Node node11 = new Node(getNodeText(data11));
        final Node node12 = new Node(getNodeText(data12));
        final Node node13 = new Node(getNodeText(data13));

        graph.addEdge(node1, node2);
        graph.addEdge(node1, node3);
        graph.addEdge(node1, node4);

        graph.addEdge(node2, node5);
        graph.addEdge(node2, node6);
        graph.addEdge(node2, node7);

        graph.addEdge(node3, node8);
        graph.addEdge(node3, node9);
        graph.addEdge(node3, node10);

        graph.addEdge(node4, node11);
        graph.addEdge(node4, node12);
        graph.addEdge(node4, node13);


    }

    private void loadContent(String userid) {

        String treeUrl = mainUrl + "api/app_user_tree_chack";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", userid);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, treeUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Intent teamIntent = new Intent(Team2_Sub_Activity.this, TeamActivity.class);

                try {

                    JSONArray jsonArray = response.getJSONArray("user_tree_main");
                    JSONArray jsonArray2 = response.getJSONArray("user_tree_1_1");
                    JSONArray jsonArray3 = response.getJSONArray("user_tree_1_2");
                    JSONArray jsonArray4 = response.getJSONArray("user_tree_1_3");

                    JSONArray jsonArray5 = response.getJSONArray("user_tree_2_1");

                    JSONArray jsonArray6 = response.getJSONArray("user_tree_2_2");

                    JSONArray jsonArray7 = response.getJSONArray("user_tree_2_3");

                    JSONArray jsonArray8 = response.getJSONArray("user_tree_3_1");
                    JSONArray jsonArray9 = response.getJSONArray("user_tree_3_2");

                    JSONArray jsonArray10 = response.getJSONArray("user_tree_3_3");
                    JSONArray jsonArray11 = response.getJSONArray("user_tree_4_1");

                    JSONArray jsonArray12 = response.getJSONArray("user_tree_4_2");
                    JSONArray jsonArray13 = response.getJSONArray("user_tree_4_3");


                    if (response.getJSONArray("user_tree_main").equals(jsonArray)) {
                        String reff = ref_uid;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject treeData = jsonArray.getJSONObject(i);

                            id1 = treeData.getString("id");
                            // team_designation = treeData.getString("designation");
                            name = treeData.getString("name");
                            UID = treeData.getString("username");
                            team_A = treeData.getString("carry1");
                            team_B = treeData.getString("carry2");
                            team_C = treeData.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_main_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2 = jsonArraref.getJSONObject(b);

                                reff = treeData2.getString("username");


                            }


                            data_s_1 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_1", data_s_1);
                            teamIntent.putExtra("id", id1);

                        }


                    }

                    if (response.getJSONArray("user_tree_1_1").equals(jsonArray2)) {
                        String reff = ref_uid;
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject treeData2 = jsonArray2.getJSONObject(i);

                            id2 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            //  team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff = treeData2r.getString("username");



                            }


                            data_s_2 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_2", data_s_2);

                            teamIntent.putExtra("id2", id2);

                        }

                    }

                    if (response.getJSONArray("user_tree_1_2").equals(jsonArray3)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray3.length(); i++) {
                            JSONObject treeData2 = jsonArray3.getJSONObject(i);

                            id3 = treeData2.getString("id");
                            // team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_3 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_3", data_s_3);
                            teamIntent.putExtra("id3", id3);


                        }

                    }

                    if (response.getJSONArray("user_tree_1_3").equals(jsonArray4)) {
                        String reff3 = null;
                        for (int i = 0; i < jsonArray4.length(); i++) {
                            JSONObject treeData2 = jsonArray4.getJSONObject(i);
                            id4 = treeData2.getString("id");
                            //  team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_4 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data_s_4", data_s_4);
                            teamIntent.putExtra("id4", id4);

                        }

                    }

                    if (response.getJSONArray("user_tree_2_1").equals(jsonArray5)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray5.length(); i++) {
                            JSONObject treeData2 = jsonArray5.getJSONObject(i);
                            id5 = treeData2.getString("id");
                            //    team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_5 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_5", data_s_5);

                            teamIntent.putExtra("id5", id5);


                        }

                    }


                    if (response.getJSONArray("user_tree_2_2").equals(jsonArray6)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray6.length(); i++) {
                            JSONObject treeData2 = jsonArray6.getJSONObject(i);
                            id6 = treeData2.getString("id");
                            // team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_6 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_6", data_s_6);
                            teamIntent.putExtra("id6", id6);


                        }

                    }

                    if (response.getJSONArray("user_tree_2_3").equals(jsonArray7)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray7.length(); i++) {
                            JSONObject treeData2 = jsonArray7.getJSONObject(i);
                            id7 = treeData2.getString("id");
                            //    team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");





                            }


                            data_s_7 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_7", data_s_7);
                            teamIntent.putExtra("id7", id7);

                        }

                    }

                    if (response.getJSONArray("user_tree_3_1").equals(jsonArray8)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray8.length(); i++) {
                            JSONObject treeData2 = jsonArray8.getJSONObject(i);
                            id8 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            // team_designation = treeData2.getString("designation");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_8 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_8", data_s_8);
                            teamIntent.putExtra("id8", id8);


                        }

                    }

                    if (response.getJSONArray("user_tree_3_2").equals(jsonArray9)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray9.length(); i++) {
                            JSONObject treeData2 = jsonArray9.getJSONObject(i);
                            id9 = treeData2.getString("id");
                            //    team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");



                            }


                            data_s_9 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_9", data_s_9);
                            teamIntent.putExtra("id9", id9);

                        }

                    }

                    if (response.getJSONArray("user_tree_3_3").equals(jsonArray10)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray10.length(); i++) {
                            JSONObject treeData2 = jsonArray10.getJSONObject(i);

                            id10 = treeData2.getString("id");
                            //  team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_3_r_n");

                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_10 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_10", data_s_10);
                            teamIntent.putExtra("id10", id10);

                        }


                    }

                    if (response.getJSONArray("user_tree_4_1").equals(jsonArray11)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray11.length(); i++) {
                            JSONObject treeData2 = jsonArray11.getJSONObject(i);
                            id11 = treeData2.getString("id");
                            //team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");

                            }


                            data_s_11 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_11", data_s_11);
                            teamIntent.putExtra("id11", id11);


                        }

                    }

                    if (response.getJSONArray("user_tree_4_2").equals(jsonArray12)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray12.length(); i++) {
                            JSONObject treeData2 = jsonArray12.getJSONObject(i);
                            id12 = treeData2.getString("id");
                            //  team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");





                            }


                            data_s_12 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_12", data_s_12);
                            teamIntent.putExtra("id12", id12);


                        }

                    }

                    if (response.getJSONArray("user_tree_4_3").equals(jsonArray13)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray13.length(); i++) {
                            JSONObject treeData2 = jsonArray13.getJSONObject(i);
                            id13 = treeData2.getString("id");
                            // team_designation = treeData2.getString("designation");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_13 = "Name : " + name +
                                    "\nID : " + UID +
                                    "\nReference ID : " + reff3 +

                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_13", data_s_13);
                            teamIntent.putExtra("id13", id13);

                        }

                    }
                    startActivity(teamIntent);
                    finish();

                    progressDialog.dismiss();
                }


                catch (JSONException e) {
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
        MySingleTon.getInstance(context).addToRequestQueue(jsonObjectRequest);


    }


    private void loadContentbySearch(String finduser) {

        String treeUrl = mainUrl + "api/app_user_tree_search";

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.show();
        progressDialog.setContentView(R.layout.custom_progrss_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        JSONObject reg_data = new JSONObject();

        try {
            reg_data.put("security_error", security);
            reg_data.put("id", userId_login);
            reg_data.put("user_tree_search", finduser);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, treeUrl, reg_data, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Intent teamIntent = new Intent(Team2_Sub_Activity.this, TeamActivity.class);

                try {

                    JSONArray jsonArray = response.getJSONArray("user_tree_main");
                    JSONArray jsonArray2 = response.getJSONArray("user_tree_1_1");
                    JSONArray jsonArray3 = response.getJSONArray("user_tree_1_2");
                    JSONArray jsonArray4 = response.getJSONArray("user_tree_1_3");

                    JSONArray jsonArray5 = response.getJSONArray("user_tree_2_1");

                    JSONArray jsonArray6 = response.getJSONArray("user_tree_2_2");

                    JSONArray jsonArray7 = response.getJSONArray("user_tree_2_3");

                    JSONArray jsonArray8 = response.getJSONArray("user_tree_3_1");
                    JSONArray jsonArray9 = response.getJSONArray("user_tree_3_2");

                    JSONArray jsonArray10 = response.getJSONArray("user_tree_3_3");
                    JSONArray jsonArray11 = response.getJSONArray("user_tree_4_1");

                    JSONArray jsonArray12 = response.getJSONArray("user_tree_4_2");
                    JSONArray jsonArray13 = response.getJSONArray("user_tree_4_3");


                    if (response.getJSONArray("user_tree_main").equals(jsonArray)) {
                        String reff = ref_uid;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject treeData = jsonArray.getJSONObject(i);

                            id1 = treeData.getString("id");

                            name = treeData.getString("name");
                            UID = treeData.getString("username");
                            team_A = treeData.getString("carry1");
                            team_B = treeData.getString("carry2");
                            team_C = treeData.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_main_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2 = jsonArray.getJSONObject(b);

                                reff = treeData2.getString("username");


                            }


                            data_s_1 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_1", data_s_1);
                            teamIntent.putExtra("id", id1);

                        }


                    }

                    if (response.getJSONArray("user_tree_1_1").equals(jsonArray2)) {
                        String reff = ref_uid;
                        for (int i = 0; i < jsonArray2.length(); i++) {
                            JSONObject treeData2 = jsonArray2.getJSONObject(i);

                            id2 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff = treeData2r.getString("username");



                            }


                            data_s_2 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_2", data_s_2);

                            teamIntent.putExtra("id2", id2);

                        }

                    }

                    if (response.getJSONArray("user_tree_1_2").equals(jsonArray3)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray3.length(); i++) {
                            JSONObject treeData2 = jsonArray3.getJSONObject(i);

                            id3 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_3 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_3", data_s_3);
                            teamIntent.putExtra("id3", id3);


                        }

                    }

                    if (response.getJSONArray("user_tree_1_3").equals(jsonArray4)) {
                        String reff3 = null;
                        for (int i = 0; i < jsonArray4.length(); i++) {
                            JSONObject treeData2 = jsonArray4.getJSONObject(i);
                            id4 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_1_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_4 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";


                            teamIntent.putExtra("data_s_4", data_s_4);
                            teamIntent.putExtra("id4", id4);

                        }

                    }

                    if (response.getJSONArray("user_tree_2_1").equals(jsonArray5)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray5.length(); i++) {
                            JSONObject treeData2 = jsonArray5.getJSONObject(i);
                            id5 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_5 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_5", data_s_5);

                            teamIntent.putExtra("id5", id5);


                        }

                    }


                    if (response.getJSONArray("user_tree_2_2").equals(jsonArray6)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray6.length(); i++) {
                            JSONObject treeData2 = jsonArray6.getJSONObject(i);
                            id6 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_6 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_6", data_s_6);
                            teamIntent.putExtra("id6", id6);


                        }

                    }

                    if (response.getJSONArray("user_tree_2_3").equals(jsonArray7)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray7.length(); i++) {
                            JSONObject treeData2 = jsonArray7.getJSONObject(i);
                            id7 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_2_3_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");





                            }


                            data_s_7 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_7", data_s_7);
                            teamIntent.putExtra("id7", id7);

                        }

                    }

                    if (response.getJSONArray("user_tree_3_1").equals(jsonArray8)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray8.length(); i++) {
                            JSONObject treeData2 = jsonArray8.getJSONObject(i);
                            id8 = treeData2.getString("id");
                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_1_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_8 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_8", data_s_8);
                            teamIntent.putExtra("id8", id8);


                        }

                    }

                    if (response.getJSONArray("user_tree_3_2").equals(jsonArray9)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray9.length(); i++) {
                            JSONObject treeData2 = jsonArray9.getJSONObject(i);
                            id9 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");



                            }


                            data_s_9 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_9", data_s_9);
                            teamIntent.putExtra("id9", id9);

                        }

                    }

                    if (response.getJSONArray("user_tree_3_3").equals(jsonArray10)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray10.length(); i++) {
                            JSONObject treeData2 = jsonArray10.getJSONObject(i);

                            id10 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_3_r_n");

                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");


                            }


                            data_s_10 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_10", data_s_10);
                            teamIntent.putExtra("id10", id10);

                        }


                    }

                    if (response.getJSONArray("user_tree_4_1").equals(jsonArray11)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray11.length(); i++) {
                            JSONObject treeData2 = jsonArray11.getJSONObject(i);
                            id11 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");

                            }


                            data_s_11 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_11", data_s_11);
                            teamIntent.putExtra("id11", id11);


                        }

                    }

                    if (response.getJSONArray("user_tree_4_2").equals(jsonArray12)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray12.length(); i++) {
                            JSONObject treeData2 = jsonArray12.getJSONObject(i);
                            id12 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");





                            }


                            data_s_12 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";




                            teamIntent.putExtra("data_s_12", data_s_12);
                            teamIntent.putExtra("id12", id12);


                        }

                    }

                    if (response.getJSONArray("user_tree_4_3").equals(jsonArray13)) {
                        String reff3 = ref_uid;
                        for (int i = 0; i < jsonArray13.length(); i++) {
                            JSONObject treeData2 = jsonArray13.getJSONObject(i);
                            id13 = treeData2.getString("id");

                            name = treeData2.getString("name");
                            UID = treeData2.getString("username");
                            team_A = treeData2.getString("carry1");
                            team_B = treeData2.getString("carry2");
                            team_C = treeData2.getString("carry3");

                            JSONArray jsonArraref = response.getJSONArray("user_tree_3_2_r_n");
                            for (int b = 0; b < jsonArraref.length(); b++) {
                                JSONObject treeData2r = jsonArraref.getJSONObject(b);

                                reff3 = treeData2r.getString("username");




                            }


                            data_s_13 = "Name : " + name +
                                    "\nBID : " + UID +
                                    "\nReference BID : " + reff3 +
                                    "\nDesignation : Member" +
                                    "\nTeam-A Carry : " + team_A +
                                    "\nTeam-B Carry : " + team_B +
                                    "\nTeam-C Carry : " + team_C +
                                    "\n";



                            teamIntent.putExtra("data_s_13", data_s_13);
                            teamIntent.putExtra("id13", id13);

                        }

                    }
                    startActivity(teamIntent);
                    finish();

                    progressDialog.dismiss();
                }


                catch (JSONException e) {
                    progressDialog.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "No Data Found.", Snackbar.LENGTH_LONG).show();

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

    private void Adpater() {
        // you can set the graph via the constructor or use the adapter.setGraph(Graph) method
        GraphAdapter adapter = new GraphAdapter<GraphView.ViewHolder>(graph) {

            @Override
            public int getCount() {
                return graph.getNodeCount();
            }

            @Override
            public Object getItem(int position) {
                return graph.getNodeAtPosition(position);
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @NonNull
            @Override
            public GraphView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            public void onBindViewHolder(GraphView.ViewHolder viewHolder, Object data, int position) {

                String s = data.toString().replaceAll("[()]", "");

                String show = s.substring(9);

                //   ((SimpleViewHolder) viewHolder).textView.setText(show);

                String[] info = show.split("[, @]+", 5);

                ((SimpleViewHolder) viewHolder).textView.setText(show);

                //((SimpleViewHolder) viewHolder).team_team_A.setText();

//                String userInput = info[2];
//
//
//                ((SimpleViewHolder) viewHolder).ref_uid.setText("Reference BID : " + info2[0]);
//                ((SimpleViewHolder) viewHolder).team_team_A.setText("Team-A Carry : " + info2[0]);
//                ((SimpleViewHolder) viewHolder).team_team_B.setText("Team-B Carry : " + 0);
//                ((SimpleViewHolder) viewHolder).team_team_C.setText("Team-C Carry : " + "0");


            }
        };

        graphView.setAdapter(adapter);
        graphView.setOnItemClickListener((parent, view, position, id) -> {
            currentNode = (Node) adapter.getItem(position);

            if (position == 0) {
//                Snackbar.make(graphView, "Clicked on " + id1, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();

            }
            if (position == 1) {
//                Snackbar.make(graphView, "Clicked on " + id2, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id2.equals("null")) {
                    loadContent(id2);
                }

            }
            if (position == 2) {
//                Snackbar.make(graphView, "Clicked on " + id3, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id3.equals("null")) {
                    loadContent(id3);
                }

            }
            if (position == 3) {
//                Snackbar.make(graphView, "Clicked on " + id4, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id4.equals("null")) {
                    loadContent(id4);
                }

            }
            if (position == 4) {
//                Snackbar.make(graphView, "Clicked on " + id5, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id5.equals("null")) {
                    loadContent(id5);
                }

            }
            if (position == 5) {
//                Snackbar.make(graphView, "Clicked on " + id6, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id6.equals("null")) {
                    loadContent(id6);
                }

            }
            if (position == 6) {
//                Snackbar.make(graphView, "Clicked on " + id7, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id7.equals("null")) {
                    loadContent(id7);
                }

            }
            if (position == 7) {
//                Snackbar.make(graphView, "Clicked on " + id8, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id8.equals("null")) {
                    loadContent(id8);
                }

            }
            if (position == 8) {
//                Snackbar.make(graphView, "Clicked on " + id9, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id9.equals("null")) {
                    loadContent(id9);
                }

            }
            if (position == 9) {
//                Snackbar.make(graphView, "Clicked on " + id10, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id10.equals("null")) {
                    loadContent(id10);
                }

            }
            if (position == 10) {
//                Snackbar.make(graphView, "Clicked on " + id11, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id11.equals("null")) {
                    loadContent(id11);
                }

            }
            if (position == 11) {
//                Snackbar.make(graphView, "Clicked on " + id12, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();

                if (!id12.equals("null")) {
                    loadContent(id12);
                }

            }
            if (position == 12) {
//                Snackbar.make(graphView, "Clicked on " + id13, Snackbar.LENGTH_SHORT).show();
                Toast.makeText(context, "Clicked on \n" + currentNode.getData().toString(), Toast.LENGTH_SHORT).show();
                if (!id13.equals("null")) {
                    loadContent(id13);
                }

            }

        });
        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));
    }

    protected String getNodeText(String infoData) {

        return infoData + "" + nodeCount++;
    }

    @Override
    public void onBackPressed() {

        goBack();
    }


    private void anotherActivity(Class<TeamActivity> teamActivityClass) {
        Intent teamIntent = new Intent(Team2_Sub_Activity.this, teamActivityClass);
        teamIntent.putExtra("data_s_1", data_s_1);
        teamIntent.putExtra("data_s_2", data_s_2);
        teamIntent.putExtra("data_s_3", data_s_3);
        teamIntent.putExtra("data_s_4", data_s_4);
        teamIntent.putExtra("data_s_5", data_s_5);
        teamIntent.putExtra("data_s_6", data_s_6);
        teamIntent.putExtra("data_s_7", data_s_7);
        teamIntent.putExtra("data_s_8", data_s_8);
        teamIntent.putExtra("data_s_9", data_s_9);
        teamIntent.putExtra("data_s_10", data_s_10);
        teamIntent.putExtra("data_s_11", data_s_11);
        teamIntent.putExtra("data_s_12", data_s_12);
        teamIntent.putExtra("data_s_13", data_s_13);

        teamIntent.putExtra("id", id1);
        teamIntent.putExtra("id2", id2);
        teamIntent.putExtra("id3", id3);
        teamIntent.putExtra("id4", id4);
        teamIntent.putExtra("id5", id5);
        teamIntent.putExtra("id6", id6);
        teamIntent.putExtra("id7", id7);
        teamIntent.putExtra("id8", id8);
        teamIntent.putExtra("id9", id9);
        teamIntent.putExtra("id10", id10);
        teamIntent.putExtra("id11", id11);
        teamIntent.putExtra("id12", id12);
        teamIntent.putExtra("id13", id13);

        startActivity(teamIntent);
    }

    void goBack() {
        Intent go_Back = new Intent(Team2_Sub_Activity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);

        finish();

    }
}