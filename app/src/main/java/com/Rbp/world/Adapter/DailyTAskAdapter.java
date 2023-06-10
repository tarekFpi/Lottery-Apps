package com.Rbp.world.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.DailyTaskHistory_model;
import com.Rbp.world.R;

import java.util.List;

public class DailyTAskAdapter extends RecyclerView.Adapter<DailyTAskAdapter.DailyTaskViewHolder> {
    private Context context;
    private List<DailyTaskHistory_model> dataList;

    public DailyTAskAdapter(Context context, List<DailyTaskHistory_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public DailyTaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.daily_task_item, parent, false);
        return new DailyTaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyTaskViewHolder holder, int position) {

        DailyTaskHistory_model dailyTaskHistoryModel = dataList.get(position);
        int serial_no = position + 1;

        holder.serial.setText("" + serial_no);

        holder.date.setText("Date: \n" + dailyTaskHistoryModel.getDate());
        holder.amount.setText("Amount : \n" + dailyTaskHistoryModel.getIncomeAmount());
        holder.incomeType.setText("Income Type : \n" + dailyTaskHistoryModel.getIncomeType());
        
        holder.web_link_1.setPaintFlags(holder.web_link_1.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        holder.web_link_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              final  String url = holder.web_link_1.getText().toString();


                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(browserIntent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class DailyTaskViewHolder extends RecyclerView.ViewHolder {

        Button serial;
        TextView amount, date, incomeType, web_link_1;

        public DailyTaskViewHolder(@NonNull View itemView) {
            super(itemView);

            serial = itemView.findViewById(R.id.tv_daily_task_sl);
            amount = itemView.findViewById(R.id.tv_daily_task_amount);
            date = itemView.findViewById(R.id.tv_daily_task_date);
            incomeType = itemView.findViewById(R.id.tv_daily_task_income_type);
            web_link_1 = itemView.findViewById(R.id.web_link_1);

        }
    }
}
