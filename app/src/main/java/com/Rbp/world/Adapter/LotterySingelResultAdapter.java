package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.LotterySingleModel;
import com.Rbp.world.R;

import java.util.List;

public   class LotterySingelResultAdapter extends RecyclerView.Adapter<LotterySingelResultAdapter.MyviewHolder> {

 private List<LotterySingleModel>lotterySingleModelList;

 private Context context;

    public LotterySingelResultAdapter(List<LotterySingleModel> lotterySingleModelList, Context context) {
        this.lotterySingleModelList = lotterySingleModelList;
        this.context = context;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

        View view= layoutInflater.inflate(R.layout.single_lottery_layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

        LotterySingleModel item = lotterySingleModelList.get(position);

        myviewHolder.textView_userName.setText(""+item.getUserName());
        myviewHolder.textView_phone.setText(""+item.getPhoneNumber());
        myviewHolder.textView_lotteryNumber.setText(""+item.getLotteryNumber());
        myviewHolder.textView_date.setText(""+item.getUploadDate());
    }

    @Override
    public int getItemCount() {
        return lotterySingleModelList.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{

        private TextView textView_userName,textView_phone,
                textView_lotteryNumber,textView_date;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_userName=(TextView)itemView.findViewById(R.id.text_singleLotName);
            textView_phone=(TextView)itemView.findViewById(R.id.text_singleLotPhone);
            textView_lotteryNumber=(TextView)itemView.findViewById(R.id.text_singleLotNumber);
            textView_date=(TextView)itemView.findViewById(R.id.text_singleLotDate);
        }
    }
}
