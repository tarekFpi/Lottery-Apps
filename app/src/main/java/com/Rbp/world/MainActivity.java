package com.Rbp.world;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.Rbp.world.Adapter.DashboardAdapter;
import com.Rbp.world.Helpers.OnDashboardClickListener;
import com.Rbp.world.Model.DashboardItemModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnDashboardClickListener {

    private RecyclerView dashBoardRecycler;
    private DashboardAdapter dashboardAdapter;
    private List<DashboardItemModel> dashboardItemList = new ArrayList();
    private ImageView viewImg;
    private String dashItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar8);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        dashBoardRecycler = findViewById(R.id.dashBoardRecycler);

        dashItem = getIntent().getStringExtra("dashItem");
        getDashboardItemList();
        dashboardAdapter = new DashboardAdapter(this, dashboardItemList, this);
        dashBoardRecycler.setLayoutManager(new GridLayoutManager(this, 1));


        dashBoardRecycler.setHasFixedSize(true);
        dashBoardRecycler.setAdapter(this.dashboardAdapter);
    }

    private void getDashboardItemList() {
        if (this.dashItem.equals("income")) {

            getSupportActionBar().setTitle("Income History");
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.part_one, "Instant Income History", 30));
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.part_one, "Daily Income History", 31));
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.part_one, "Team Income History", 32));


        }
        if (this.dashItem.equals("rakuten")) {

            getSupportActionBar().setTitle("Rakuten");
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.ic_rakuten, "Rakuten", 33));
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.ic_rakuten, "Rakuten Shopping", 34));
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.ic_rakuten, "Rakuten France", 35));
            this.dashboardItemList.add(new DashboardItemModel(R.drawable.ic_rakuten, "Rakuten Sport", 36));


        }
    }

    @Override
    public void onDashBoardItem(int i) {
        if (i == 30) {
            Intent intent5 = new Intent(MainActivity.this, MemberIncomeLoadActivity.class);
            intent5.putExtra("FRGID", "30");
            startActivity(intent5);

        }
        else if(i == 31){

            Intent intent5 = new Intent(MainActivity.this, MemberIncomeLoadActivity.class);
            intent5.putExtra("FRGID", "31");
            startActivity(intent5);
        }
        else if(i == 32){
            Intent intent5 = new Intent(MainActivity.this, MemberIncomeLoadActivity.class);
            intent5.putExtra("FRGID", "32");
            startActivity(intent5);

        }
        else if(i == 33){
            String url = "https://www.rakuten.com/";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(browserIntent);

        }else if(i == 34){
            String url = "https://www.rakuten.com/stores/all/index.htm";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(browserIntent);

        }else if(i == 35){
            String url = "https://global.fr.shopping.rakuten.com/rakuten-france/";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(browserIntent);

        }else if(i == 36){
            String url = "https://sports.rakuten.com/";
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
           startActivity(browserIntent);

        }

    }
    @Override // androidx.appcompat.app.AppCompatActivity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}