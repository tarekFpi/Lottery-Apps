package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.Transction_history_model;
import com.Rbp.world.R;

import java.util.List;

public class TransctionHistoryAdapter extends RecyclerView.Adapter<TransctionHistoryAdapter.TransctionViewHolder> {

    private Context context;
    private List<Transction_history_model> dataList;

    public TransctionHistoryAdapter(Context context, List<Transction_history_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public TransctionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.transction_log_history_list, parent, false);
        return new TransctionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransctionViewHolder holder, int position) {

        Transction_history_model transctionHistoryModel=dataList.get(position);

        int serial_no=position+1;

        holder.serial.setText(""+serial_no);
        holder.transferId.setText("Transfer ID : \n"+transctionHistoryModel.getUsername());
        holder.balance.setText("Balance : \n"+transctionHistoryModel.getAfterAmount()+" BDT");
        holder.method.setText("Method : \n"+transctionHistoryModel.getMethod());
        holder.type.setText("Type : \n"+transctionHistoryModel.getTransctionType());
        holder.date.setText("Date : \n"+transctionHistoryModel.getDate());


        holder.amount.setText("Amount : \n"+transctionHistoryModel.getAmount()+" BDT");
        holder.note.setText("Charge :  "+transctionHistoryModel.getNote() +" BDT");

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class TransctionViewHolder extends RecyclerView.ViewHolder{

        Button serial;
        TextView transferId,amount,balance,method,type,date,note;

        public TransctionViewHolder(@NonNull View itemView) {
            super(itemView);

            serial=itemView.findViewById(R.id.tv_transction_history_SL);
            transferId=itemView.findViewById(R.id.tv_transction_history_id);
            balance=itemView.findViewById(R.id.tv_transction_history_balance);
            amount=itemView.findViewById(R.id.tv_transction_history_Amount);
            method=itemView.findViewById(R.id.tv_transction_history_Method);
            type=itemView.findViewById(R.id.tv_transction_history_type);
            date=itemView.findViewById(R.id.tv_transction_history_Date);
            note=itemView.findViewById(R.id.tv_transction_history_note);
        }
    }

}
