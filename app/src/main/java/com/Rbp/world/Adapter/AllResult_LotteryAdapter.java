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
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.AllResult_Lotterymodel;
import com.Rbp.world.R;
import com.airbnb.lottie.LottieAnimationView;

import java.util.List;

public   class AllResult_LotteryAdapter extends RecyclerView.Adapter<AllResult_LotteryAdapter.MyviewHolder> {

 private List<AllResult_Lotterymodel>allResult_lotterymodels;

 private Context context;

    private static int listposition=-1;


   public AllResult_LotteryAdapter(List<AllResult_Lotterymodel> allResult_lotterymodels, Context context) {
      this.allResult_lotterymodels = allResult_lotterymodels;
      this.context = context;
   }

   @Override
   public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       LayoutInflater layoutInflater=LayoutInflater.from(context);

      View view= layoutInflater.inflate(R.layout.all_result_lottery,parent,false);

      return new MyviewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

       AllResult_Lotterymodel itemPosition=allResult_lotterymodels.get(position);

       myviewHolder.textView_code.setText("Code: "+itemPosition.getCode());
       myviewHolder.textView_date.setText("Date:\n"+itemPosition.getDate());
       myviewHolder.textView_prize_number.setText("Prize Number: "+itemPosition.getPrize_number());
       myviewHolder.textView_winner_amount.setText("Winner Amount :"+itemPosition.getWinner_amount());
       myviewHolder.textView_lotteryName.setText("Lottery Name: "+itemPosition.getLotary_name());
       myviewHolder.textView_point.setText("Point:"+itemPosition.getPoint());
     //  myviewHolder.textView_winn_status.setText("Status:"+itemPosition.getWinn_status());

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
            ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                    Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setDuration(1000);
            viewAnimition.startAnimation(animation);
            listposition = position;


        }
    }

   @Override
   public int getItemCount() {
      return allResult_lotterymodels.size();
   }

   class MyviewHolder extends RecyclerView.ViewHolder{

       private TextView textView_lotteryName,
               textView_code,textView_point,textView_date,
               textView_winner_amount,textView_prize_number,
               textView_winn_status;

       private LottieAnimationView animationView;

      public MyviewHolder(@NonNull View itemView) {
         super(itemView);

          textView_lotteryName=(TextView)itemView.findViewById(R.id.allLotlotName);

          textView_code=(TextView)itemView.findViewById(R.id.text_allLotNumber);

          textView_point=(TextView)itemView.findViewById(R.id.text_allLotpoint);

          textView_date=(TextView)itemView.findViewById(R.id.text_allLotdate);

          textView_winner_amount=(TextView)itemView.findViewById(R.id.text_allLotwinner_amount);

          textView_prize_number=(TextView)itemView.findViewById(R.id.text_allLot_prize_number);

          textView_winn_status=(TextView)itemView.findViewById(R.id.text_allLot_Status);

          animationView=(LottieAnimationView)itemView.findViewById(R.id.aminmation_lotAll);
      }
   }
}
