package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.Income_list_model;
import com.Rbp.world.R;

import java.util.List;

public class ListTeamIncomeAdapter extends RecyclerView.Adapter<ListTeamIncomeAdapter.MyViewHolder> {
    private Context context;
    private List<Income_list_model> dataList;

    public ListTeamIncomeAdapter(Context context, List<Income_list_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.team_list_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Income_list_model incomeListModel = dataList.get(position);

        int amountData = incomeListModel.getIncomeAmount();

        int serial_no = position + 1;

        holder.serial.setText("" + serial_no);
        holder.id.setText("ID : \n" + incomeListModel.getUsername());
        holder.amount.setText("Amount : \n" + amountData);
        holder.income_type.setText("Income Type : \n" + incomeListModel.getIncomeType());
        holder.date.setText("Date : \n" + incomeListModel.getDate());
        holder.genation.setText("Generation : \n" + incomeListModel.getGenaration());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id, amount, income_type, date,genation;
        Button serial;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            serial = itemView.findViewById(R.id.tv_income_list_SL);
            id = itemView.findViewById(R.id.tv_income_list_id);
            amount = itemView.findViewById(R.id.tv_income_list_Amount);
            income_type = itemView.findViewById(R.id.tv_income_list_income_type);
            date = itemView.findViewById(R.id.tv_income_list_Date);
            genation = itemView.findViewById(R.id.tv_income_list_generation);

        }
    }
}
