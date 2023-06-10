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

import com.Rbp.world.Fragment.DailyIncomeHistoryFragment;
import com.Rbp.world.Fragment.FundTransferReportFragment;
import com.Rbp.world.Fragment.IncomeHistoryFragment;
import com.Rbp.world.Fragment.InstantIncomeHistoryFragment;
import com.Rbp.world.Fragment.PasswordChangeFragment;
import com.Rbp.world.Fragment.ProcessBalanceFragment;
import com.Rbp.world.Fragment.TeamHistoryFragment;
import com.Rbp.world.Fragment.TransactionPinFragment;
import com.Rbp.world.Fragment.TransctionHistoryFragment;
import com.Rbp.world.Fragment.TransferBalanceFragment;
import com.Rbp.world.Fragment.WithdrawBalanceFragment;
import com.Rbp.world.Fragment.WithdrawMethodFragment;

public class MemberIncomeLoadActivity extends AppCompatActivity {

    TextView report_load_tab_bar;

    TextView tab_text;
    LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_report_load);

        report_load_tab_bar = findViewById(R.id.report_load_tab_bar);

        back = findViewById(R.id.go_back_mrla);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra("FRGID");

        if (stringExtra.equals("30")) {

            InstantIncomeHistoryFragment instantIncomeHistoryFragment = new InstantIncomeHistoryFragment();
            report_load_tab_bar.setText("Instant Income History");
            replaceFragment(instantIncomeHistoryFragment);

        }if (stringExtra.equals("31")) {

            DailyIncomeHistoryFragment dailyIncomeHistoryFragment = new DailyIncomeHistoryFragment();
            report_load_tab_bar.setText("Daily Income History");
            replaceFragment(dailyIncomeHistoryFragment);

        }if (stringExtra.equals("32")) {

            TeamHistoryFragment teamHistoryFragment = new TeamHistoryFragment();
            report_load_tab_bar.setText("Team Income History");
            replaceFragment(teamHistoryFragment);

        }


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fl_MemberReportLoad, fragment);
        beginTransaction.addToBackStack(fragment.toString());
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        beginTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {
        Intent go_Back = new Intent(MemberIncomeLoadActivity.this, MainActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        go_Back.putExtra("dashItem", "income");
        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }
}