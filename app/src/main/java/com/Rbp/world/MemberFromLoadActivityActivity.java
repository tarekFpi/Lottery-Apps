package com.Rbp.world;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.Rbp.world.Fragment.AgentWithdrawFromFragment;
import com.Rbp.world.Fragment.Business_Center_Fragment;
import com.Rbp.world.Fragment.MobileRecharge_Fragment;
import com.Rbp.world.Fragment.MyLottery_Fragment;
import com.Rbp.world.Fragment.Placement_ListFragment;
import com.Rbp.world.Fragment.ProfileFragment;
import com.Rbp.world.Fragment.Sponsor_ListFragment;
import com.Rbp.world.Fragment.TransferReserveBalanceFragment;
import com.Rbp.world.Fragment.TransferShopBalanceFragment;
import com.Rbp.world.Fragment.WithdrawShopBalanceFragment;

public class MemberFromLoadActivityActivity extends AppCompatActivity {

    TextView tab_text;
    LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_from_load_activity);

        tab_text = findViewById(R.id.tab_text);
        back = findViewById(R.id.go_back_mfla);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goBack();
            }
        });


        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("Techno");

        if (stringExtra.equals("05")) {
            AgentWithdrawFromFragment agentWithdrawFormFragment = new AgentWithdrawFromFragment();
            tab_text.setText("Cashout");
            replaceFragment(agentWithdrawFormFragment);
        }
        if (stringExtra.equals("01")) {
            ProfileFragment profileFragment = new ProfileFragment();
            tab_text.setText("Profile");
            replaceFragment(profileFragment);
        }
        if (stringExtra.equals("11")) {
            Business_Center_Fragment business_center_fragment = new Business_Center_Fragment();
            tab_text.setText("Business Center");
            replaceFragment(business_center_fragment);
        }
        if (stringExtra.equals("12")) {
            Placement_ListFragment placement_listFragment = new Placement_ListFragment();
            tab_text.setText("Placement List");
            replaceFragment(placement_listFragment);

        }if (stringExtra.equals("13")) {
            Sponsor_ListFragment sponsor_listFragment = new Sponsor_ListFragment();
            tab_text.setText("Sponsor List");
            replaceFragment(sponsor_listFragment);
        }if (stringExtra.equals("18")) {
            TransferReserveBalanceFragment transferReserveBalanceFragment = new TransferReserveBalanceFragment();
            tab_text.setText("Transfer Reserve Balance");
            replaceFragment(transferReserveBalanceFragment);
        }



//        if (stringExtra.equals("19")) {
//            WithdrawShopBalanceFragment withdrawShopBalanceFragment = new WithdrawShopBalanceFragment();
//            tab_text.setText("Withdraw Shop Balance");
//            replaceFragment(withdrawShopBalanceFragment);
//        }

        if (stringExtra.equals("20")) {
            MobileRecharge_Fragment mobileRecharge_fragment = new MobileRecharge_Fragment();
            tab_text.setText("Mobile Recharge");
            replaceFragment(mobileRecharge_fragment);
        }

    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fl_FromLoadContainer, fragment);
        beginTransaction.addToBackStack(fragment.toString());
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        beginTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {
        Intent go_Back = new Intent(MemberFromLoadActivityActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }
}