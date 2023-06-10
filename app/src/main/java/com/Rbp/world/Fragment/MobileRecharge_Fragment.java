package com.Rbp.world.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.Rbp.world.R;


public class MobileRecharge_Fragment extends Fragment implements View.OnClickListener {

    EditText ammount;
    TextView addmoney, addmoney2, addmoney3, addmoney4;

    public MobileRecharge_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mobile_recharge_, container, false);

        ammount = view.findViewById(R.id.ammount);

        addmoney = view.findViewById(R.id.addmoney);
        addmoney2 = view.findViewById(R.id.addmoney2);
        addmoney3 = view.findViewById(R.id.addmoney3);
        addmoney4 = view.findViewById(R.id.addmoney4);

        addmoney.setOnClickListener(this);
        addmoney2.setOnClickListener(this);
        addmoney3.setOnClickListener(this);
        addmoney4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        /*to get clicked view id**/
        switch (v.getId()) {
            case R.id.addmoney:
                ammount.setText("50");

                addmoney.setTextColor(Color.WHITE);
                addmoney.setBackgroundResource(R.drawable.rect1);

                addmoney2.setBackgroundResource(R.drawable.rect);
                addmoney3.setBackgroundResource(R.drawable.rect);
                addmoney4.setBackgroundResource(R.drawable.rect);

                addmoney2.setTextColor(Color.GRAY);
                addmoney4.setTextColor(Color.GRAY);
                addmoney3.setTextColor(Color.GRAY);

                break;
            case R.id.addmoney2:
                ammount.setText("100");
                addmoney2.setTextColor(Color.WHITE);

                addmoney2.setBackgroundResource(R.drawable.rect1);

                addmoney.setBackgroundResource(R.drawable.rect);
                addmoney3.setBackgroundResource(R.drawable.rect);
                addmoney4.setBackgroundResource(R.drawable.rect);

                addmoney.setTextColor(Color.GRAY);
                addmoney3.setTextColor(Color.GRAY);
                addmoney4.setTextColor(Color.GRAY);

                break;

            case R.id.addmoney3:
                ammount.setText("150");

                addmoney3.setTextColor(Color.WHITE);
                addmoney3.setBackgroundResource(R.drawable.rect1);

                addmoney.setBackgroundResource(R.drawable.rect);
                addmoney2.setBackgroundResource(R.drawable.rect);
                addmoney4.setBackgroundResource(R.drawable.rect);

                addmoney.setTextColor(Color.GRAY);
                addmoney2.setTextColor(Color.GRAY);
                addmoney4.setTextColor(Color.GRAY);

                break;

                case R.id.addmoney4:

                    ammount.setText("200");
                    addmoney4.setTextColor(Color.WHITE);
                    addmoney4.setBackgroundResource(R.drawable.rect1);

                    addmoney.setBackgroundResource(R.drawable.rect);
                    addmoney2.setBackgroundResource(R.drawable.rect);
                    addmoney3.setBackgroundResource(R.drawable.rect);

                    addmoney.setTextColor(Color.GRAY);
                    addmoney2.setTextColor(Color.GRAY);
                    addmoney3.setTextColor(Color.GRAY);


                break;
        }
    }
}