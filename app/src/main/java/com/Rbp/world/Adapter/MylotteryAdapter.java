package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.MylotteryModel;
import com.Rbp.world.R;
import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public  class MylotteryAdapter extends RecyclerView.Adapter<MylotteryAdapter.MyviewHolder> {

    private Context context;
    private List<MylotteryModel>mylotteryModels;

    private static int listposition=-1;

    public MylotteryAdapter(Context context, List<MylotteryModel> mylotteryModels) {
        this.context = context;
        this.mylotteryModels = mylotteryModels;
    }

    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

       View view=  layoutInflater.inflate(R.layout.mylottery_layout,parent,false);

        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

        MylotteryModel itemPosition=mylotteryModels.get(position);

        myviewHolder.textView_code.setText("Code: "+itemPosition.getCode());
        myviewHolder.textView_date.setText("Date:\n"+itemPosition.getDate());
        myviewHolder.textView_prize_number.setText("Prize Number: "+itemPosition.getPrize_number());
        myviewHolder.textView_winner_amount.setText("Winner Amount :"+itemPosition.getWinner_amount());
        myviewHolder.textView_lotteryName.setText("Lottery Name: "+itemPosition.getLotary_name());
        myviewHolder.textView_point.setText("Point:"+itemPosition.getPoint());
        //winning
        if(itemPosition.getWinn_status().trim().contains("0")){

            myviewHolder.textView_winn_status.setText("Status:Losing");
            myviewHolder.animationView.setVisibility(View.GONE);
        }else {

            myviewHolder.textView_winn_status.setText("Status:Winning");
            myviewHolder.animationView.setVisibility(View.VISIBLE);
        }

        setAnimiton(myviewHolder.itemView,position);
    }

    void setAnimiton(View viewAnimition,int position) {

        if (position > listposition) {
            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
            animation.setDuration(1000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }
    @Override
    public int getItemCount() {
        return mylotteryModels.size();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{

        private TextView textView_lotteryName,
                textView_code,textView_point,textView_date,
                textView_winner_amount,textView_prize_number,
                textView_winn_status;

        private LottieAnimationView animationView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            textView_lotteryName=(TextView)itemView.findViewById(R.id.mylotName);

            textView_code=(TextView)itemView.findViewById(R.id.text_MyNumber);

            textView_point=(TextView)itemView.findViewById(R.id.text_Mypoint);

            textView_date=(TextView)itemView.findViewById(R.id.text_Mydate);

            textView_winner_amount=(TextView)itemView.findViewById(R.id.textMywinner_amount);

            textView_prize_number=(TextView)itemView.findViewById(R.id.textMyprize_number);

            textView_winn_status=(TextView)itemView.findViewById(R.id.text_myStatus);

            animationView=(LottieAnimationView)itemView.findViewById(R.id.aminmation_single);
        }
    }
}
