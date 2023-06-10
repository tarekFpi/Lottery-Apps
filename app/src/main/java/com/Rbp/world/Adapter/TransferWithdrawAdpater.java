package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.Transfer_Withdraw_model;
import com.Rbp.world.R;

import java.util.List;

public class TransferWithdrawAdpater extends RecyclerView.Adapter<TransferWithdrawAdpater.MyViewHolder> {
    private Context context;
    private List<Transfer_Withdraw_model> dataList;

    private static int listposition=-1;

    public TransferWithdrawAdpater(Context context, List<Transfer_Withdraw_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transfer_n_withdraw_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Transfer_Withdraw_model transferWithdrawModel = dataList.get(position);

        int serial_no = position + 1;

        holder.serial.setText(""+serial_no);
        
        holder.user_id.setText("Transaction ID : \n"+transferWithdrawModel.getUsername());
        holder.date.setText("Date : \n"+transferWithdrawModel.getDate());
        holder.amount.setText("Amount : \n"+transferWithdrawModel.getAmount());
        holder.method.setText("Method : \n"+transferWithdrawModel.getMethod());
        holder.balance.setText("Balance : \n"+transferWithdrawModel.getAfterAmount());

        holder.status.setText("Status : \n"+transferWithdrawModel.getStatus());

        setAnimiton(holder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {

        if (position > listposition) {
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(2000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView user_id, date, amount, method, balance,status;

        Button serial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            serial = itemView.findViewById(R.id.tv_t_W_list_SL);
            user_id = itemView.findViewById(R.id.tv_t_W_list_userID);
            date = itemView.findViewById(R.id.tv_t_W_list_date);
            amount = itemView.findViewById(R.id.tv_t_W_list_amount);
            method = itemView.findViewById(R.id.tv_t_W_list_method);
            balance = itemView.findViewById(R.id.tv_t_W_list_balance);
            status = itemView.findViewById(R.id.tv_t_W_list_status);

        }
    }
}
