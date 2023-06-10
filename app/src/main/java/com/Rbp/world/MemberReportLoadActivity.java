package com.Rbp.world;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.Rbp.world.Fragment.AllResultFragment;
import com.Rbp.world.Fragment.DailyIncomeHistoryFragment;
import com.Rbp.world.Fragment.FundTransferReportFragment;
import com.Rbp.world.Fragment.IncomeHistoryFragment;
import com.Rbp.world.Fragment.InstantIncomeHistoryFragment;
import com.Rbp.world.Fragment.LotteryResultFragment;
import com.Rbp.world.Fragment.PasswordChangeFragment;
import com.Rbp.world.Fragment.ProcessBalanceFragment;
import com.Rbp.world.Fragment.TeamHistoryFragment;
import com.Rbp.world.Fragment.TransactionPinFragment;
import com.Rbp.world.Fragment.TransctionHistoryFragment;
import com.Rbp.world.Fragment.TransferBalanceFragment;
import com.Rbp.world.Fragment.WithdrawBalanceFragment;
import com.Rbp.world.Fragment.WithdrawMethodFragment;

public class MemberReportLoadActivity extends AppCompatActivity {

    TextView report_load_tab_bar;

    TextView tab_text;
    LinearLayout back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_report_load);

        report_load_tab_bar = findViewById(R.id.report_load_tab_bar);

        back = findViewById(R.id.go_back_mrla);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });

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

        if (stringExtra.equals("10")) {
            FundTransferReportFragment fundTransferReportFragment = new FundTransferReportFragment();
            report_load_tab_bar.setText("Sending Report");
            replaceFragment(fundTransferReportFragment);
        }
        if (stringExtra.equals("02")) {
            IncomeHistoryFragment incomeHistoryFragment = new IncomeHistoryFragment();
            report_load_tab_bar.setText("Income History");
            replaceFragment(incomeHistoryFragment);
        }

        if (stringExtra.equals("03")) {

            TransctionHistoryFragment transctionHistoryFragment = new TransctionHistoryFragment();
            report_load_tab_bar.setText("Transction Log");
            replaceFragment(transctionHistoryFragment);

        }
        if (stringExtra.equals("04")) {

            PasswordChangeFragment passwordChangeFragment = new PasswordChangeFragment();
            report_load_tab_bar.setText("Password Change");
            replaceFragment(passwordChangeFragment);

        } if (stringExtra.equals("05")) {

           LotteryResultFragment lotteryResultFragment = new LotteryResultFragment();
            report_load_tab_bar.setText("Person Result");
            replaceFragment(lotteryResultFragment);

          }

        if (stringExtra.equals("08")) {

             AllResultFragment allResultFragment = new AllResultFragment();
            report_load_tab_bar.setText("All Result");
            replaceFragment(allResultFragment);

        }

        if (stringExtra.equals("06")) {


            ProcessBalanceFragment processBalanceFragment = new ProcessBalanceFragment();
            report_load_tab_bar.setText("Process Balance");
            replaceFragment(processBalanceFragment);

        }if (stringExtra.equals("14")) {

            TransactionPinFragment transactionPinFragment = new TransactionPinFragment();
            report_load_tab_bar.setText("Transaction Pin");
            replaceFragment(transactionPinFragment);

        }if (stringExtra.equals("15")) {

            TransferBalanceFragment transferBalanceFragment = new TransferBalanceFragment();
            report_load_tab_bar.setText("Transfer Balance");
            replaceFragment(transferBalanceFragment);

        }if (stringExtra.equals("16")) {

            WithdrawBalanceFragment withdrawBalanceFragment = new WithdrawBalanceFragment();
            report_load_tab_bar.setText("Withdraw Balance");
            replaceFragment(withdrawBalanceFragment);

        }if (stringExtra.equals("17")) {

            WithdrawMethodFragment withdrawMethodFragment = new WithdrawMethodFragment();
            report_load_tab_bar.setText("Withdraw Method");
            replaceFragment(withdrawMethodFragment);

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
        Intent go_Back = new Intent(MemberReportLoadActivity.this, DashboardActivity.class);
        go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        startActivity(go_Back);
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
        finish();

    }
}