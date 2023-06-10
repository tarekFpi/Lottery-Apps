package com.Rbp.world.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.Rbp.world.R;
import com.Rbp.world.SearchUserActivity;

public class AgentWithdrawFromFragment extends Fragment {

    TextView txt_WithdrawForm_Balance;
    EditText edit_text_ammount;
    Button btn_calculate;
    Spinner spnr_PaymentMtd;

    TextView sendTo;

    LinearLayout lnr_CalculationModule;

    TextView txt_RequestMoney, txt_RequestChargeName,
            txt_RequestCharge, txt_SystemChargeName,
            txt_SystemCharge, txt_NetWithdrawAble;
    Button btn_Confirm, btn_Cancel;


    public AgentWithdrawFromFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agent_withdraw_form, container, false);

        txt_WithdrawForm_Balance = view.findViewById(R.id.txt_WithdrawForm_Balance);
        edit_text_ammount = view.findViewById(R.id.et_WithdrawForm_Amount);
        sendTo = view.findViewById(R.id.tv_FundTransferOption_SendTo);
        btn_calculate = view.findViewById(R.id.btn_WithdrawForm_Calculate);
        spnr_PaymentMtd = view.findViewById(R.id.spnr_WithdrawForm_PaymentMtd);


        lnr_CalculationModule = view.findViewById(R.id.lnr_CalculationModule);
        txt_RequestMoney = view.findViewById(R.id.txt_WithdrawForm_RequestMoney);
        txt_RequestChargeName = view.findViewById(R.id.txt_WithdrawForm_RequestChargeName);
        txt_RequestCharge = view.findViewById(R.id.txt_WithdrawForm_RequestCharge);
        txt_SystemChargeName = view.findViewById(R.id.txt_WithdrawForm_SystemChargeName);
        txt_SystemCharge = view.findViewById(R.id.txt_WithdrawForm_SystemCharge);
        txt_NetWithdrawAble = view.findViewById(R.id.txt_WithdrawForm_NetWithdrawAble);
        btn_Confirm = view.findViewById(R.id.btn_WithdrawForm_Confirm);
        btn_Cancel = view.findViewById(R.id.btn_WithdrawForm_Cancel);


        btn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConvertAmmount();
            }
        });
        sendTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SearchUserActivity.class);
                intent.putExtra("FUNDTYPE","search" );

                startActivityForResult(intent, 420);
            }
        });


        return view;
    }
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 420 && i2 == -1) {

        }
    }
    private void ConvertAmmount() {

        String edit_Text_ammount = edit_text_ammount.getText().toString();
        FragmentActivity activity = getActivity();

        if (edit_Text_ammount.equals("")) {
            Toast.makeText(activity, "Please enter Amount", Toast.LENGTH_SHORT).show();
            return;
        } else if (edit_Text_ammount.length() <= 2) {

            Toast.makeText(activity, "Your amount should be 100 +", Toast.LENGTH_SHORT).show();
            return;
        } else if (spnr_PaymentMtd.getSelectedItemPosition() == 0) {

            Toast.makeText(activity, "Please select a payment option.", Toast.LENGTH_SHORT).show();
            return;
        } else {
            double doubleValue = Double.valueOf(edit_Text_ammount).doubleValue();
            lnr_CalculationModule.setVisibility(View.VISIBLE);
            calculateCharge(edit_Text_ammount, doubleValue);
            return;
        }

    }

    private void calculateCharge(String amount, double d) {
        txt_RequestMoney.setText(amount);
        double doubleValue = Double.valueOf("10.0").doubleValue();
        double d2 = (doubleValue * d) / 100.0d;
        double d3 = d - d2;
        txt_RequestChargeName.setText("Withdraw Charge (" + doubleValue + "%)");
        txt_RequestCharge.setText(d2 + "");
        double asDouble = 0.0;
        double d4 = d3 - ((asDouble * d3) / 100.0d);
        txt_SystemChargeName.setText("Payment System Charge (" + asDouble + ")");
        txt_SystemCharge.setText(d4 + "");

        txt_NetWithdrawAble.setText(d4 + "");


    }


}