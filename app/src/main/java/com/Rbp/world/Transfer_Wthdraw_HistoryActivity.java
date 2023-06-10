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

import com.Rbp.world.Fragment.TransferHistoryFragment;
import com.Rbp.world.Fragment.Transfer_reserve_historyFragment;
import com.Rbp.world.Fragment.Transfer_shop_historyFragment;
import com.Rbp.world.Fragment.Withdraw_historyFragment;
import com.Rbp.world.Fragment.Withdraw_shop_historyFragment;

public class Transfer_Wthdraw_HistoryActivity extends AppCompatActivity {

    TextView tab_text;
    LinearLayout back;
    String stringExtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transfer__wthdraw__history);

        tab_text = findViewById(R.id.tab_text);
        back = findViewById(R.id.go_back_t_w);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().addFlags(Integer.MIN_VALUE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Intent intent = getIntent();
        stringExtra = intent.getStringExtra("TansferWithdraw");

        if (stringExtra.equals("01")) {
            TransferHistoryFragment transferHistoryFragment = new TransferHistoryFragment();
            tab_text.setText("Transfer History");
            replaceFragment(transferHistoryFragment);

        }
        if (stringExtra.equals("02")) {
            Withdraw_historyFragment withdraw_historyFragment = new Withdraw_historyFragment();
            tab_text.setText("Withdraw History");
            replaceFragment(withdraw_historyFragment);
        }
        if (stringExtra.equals("03")) {
            Withdraw_shop_historyFragment withdraw_shop_historyFragment = new Withdraw_shop_historyFragment();
            tab_text.setText("Withdraw Shop History");
            replaceFragment(withdraw_shop_historyFragment);
        }  if (stringExtra.equals("04")) {
            Transfer_shop_historyFragment transfer_shop_historyFragment = new Transfer_shop_historyFragment();
            tab_text.setText("Transfer Shop History");
            replaceFragment(transfer_shop_historyFragment);
        }if (stringExtra.equals("05")) {
            Transfer_reserve_historyFragment transferReserveHistoryFragment = new Transfer_reserve_historyFragment();
            tab_text.setText("Transfer Reserve History");
            replaceFragment(transferReserveHistoryFragment);
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goBack();
            }
        });


    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        beginTransaction.replace(R.id.fl_TW_Container, fragment);
        beginTransaction.addToBackStack(fragment.toString());
        beginTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        beginTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        goBack();
    }

    void goBack() {

        if (stringExtra.equals("01")) {
            Intent go_Back = new Intent(Transfer_Wthdraw_HistoryActivity.this, MemberReportLoadActivity.class);
            go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            go_Back.putExtra("FRGID", "15");
            startActivity(go_Back);
            overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);
            finish();
        }
        if (stringExtra.equals("02")) {
            Intent go_Back = new Intent(Transfer_Wthdraw_HistoryActivity.this, MemberReportLoadActivity.class);
            go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

            go_Back.putExtra("FRGID", "16");
            startActivity(go_Back);
            overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);
            finish();
        }
        if (stringExtra.equals("03")) {
            Intent go_Back = new Intent(Transfer_Wthdraw_HistoryActivity.this, MemberFromLoadActivityActivity.class);
            go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            go_Back.putExtra("Techno", "19");
            startActivity(go_Back);
            overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);
            finish();
        }
//        if (stringExtra.equals("04")) {
//            Intent go_Back = new Intent(Transfer_Wthdraw_HistoryActivity.this, MemberFromLoadActivityActivity.class);
//            go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//            go_Back.putExtra("Techno", "18");
//            startActivity(go_Back);
//            overridePendingTransition(R.anim.slide_out_right,
//                    R.anim.slide_in_left);
//            finish();
//        }
        if (stringExtra.equals("05")) {
            Intent go_Back = new Intent(Transfer_Wthdraw_HistoryActivity.this, MemberFromLoadActivityActivity.class);
            go_Back.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            go_Back.putExtra("Techno", "18");
            startActivity(go_Back);
            overridePendingTransition(R.anim.slide_out_right,
                    R.anim.slide_in_left);
            finish();
        }





    }

}