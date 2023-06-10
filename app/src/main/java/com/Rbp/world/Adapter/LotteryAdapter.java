package com.Rbp.world.Adapter;


import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Rbp.world.Model.Lottery_model;
import com.Rbp.world.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class LotteryAdapter extends RecyclerView.Adapter<LotteryAdapter.MyviewHolder> {

   private List<Lottery_model>lottery_models;

   private Context context;

   private static int listposition=-1;

   private  static  onItemClickLisiner clickLisiner;

   public LotteryAdapter(List<Lottery_model> lottery_models, Context context) {
      this.lottery_models = lottery_models;
      this.context = context;
   }

   @Override
   public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

      LayoutInflater layoutInflater=LayoutInflater.from(context);

      View view=  layoutInflater.inflate(R.layout.lottery_layout,parent,false);

      return new MyviewHolder(view);
   }

   @Override
   public void onBindViewHolder(@NonNull MyviewHolder myviewHolder, int position) {

      Lottery_model item=lottery_models.get(position);

      myviewHolder.textView_one.setText(""+item.getNumber());

      myviewHolder.textView_point.setText("TK."+item.getPoint());

      myviewHolder.textView_expDate.setText("Up."+item.getDate());

     // SimpleDateFormat DateFor = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
     // DateFor.setTimeZone(TimeZone.getTimeZone("Asia/Dhaka"));
      setAnimiton(myviewHolder.itemView,position);

   }

   void setAnimiton(View viewAnimition,int position) {


      if (position > listposition) {
 /*        ScaleAnimation animation = new ScaleAnimation(0.0f, 0.1f, 0.0f, 0.1f, Animation.RELATIVE_TO_SELF, 0.5f,
                 Animation.RELATIVE_TO_SELF, 0.5f);
         animation.setDuration(1000);
         viewAnimition.startAnimation(animation);
         listposition = position;*/

         Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
         animation.setDuration(1000);
         viewAnimition.startAnimation(animation);
         listposition = position;
      }
   }


   @Override
   public int getItemCount() {
      return lottery_models.size();
   }

   class MyviewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private TextView textView_one,textView_expDate,textView_point;

      public MyviewHolder(@NonNull View itemView) {
         super(itemView);

         textView_one=(TextView)itemView.findViewById(R.id.text_lotteryOne);

         textView_expDate=(TextView)itemView.findViewById(R.id.text_lotteryExpart);

         textView_point=(TextView)itemView.findViewById(R.id.textLotterPoint);



         itemView.setOnClickListener(this);
      }

      @Override
      public void onClick(View v) {

         int position = getLayoutPosition();
         clickLisiner.OnClick_Lisiner(position);
      }
   }

   public interface  onItemClickLisiner{
      void OnClick_Lisiner(int position);
   }

   public  void setOnItemClick(onItemClickLisiner clickLisiner){
      this. clickLisiner=clickLisiner;
   }
}
