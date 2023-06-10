package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.LotteryNameModel;
import com.Rbp.world.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public  class LotteryNameAdapter extends RecyclerView.Adapter<LotteryNameAdapter.MyviwHolder> {

  private Context context;

  private List<LotteryNameModel>lotteryNameModelList;

    private static int listposition=-1;

    private  static onItemClickLisiner clickLisiner;

    public LotteryNameAdapter(Context context, List<LotteryNameModel> lotteryNameModelList) {
        this.context = context;
        this.lotteryNameModelList = lotteryNameModelList;
    }

    @Override
    public MyviwHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

      View view= layoutInflater.inflate(R.layout.lottery_namelayout,parent,false);

        return new MyviwHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviwHolder myviwHolder, int position) {

        LotteryNameModel itemPositon=lotteryNameModelList.get(position);

        myviwHolder.textView_status.setText("Status:"+itemPositon.getStatus());
         myviwHolder.textView_text_lotDate.setText("Up:"+itemPositon.getUploadDate());
        myviwHolder.textView_lotteryName.setText("Name:"+itemPositon.getLotary_name());

        Picasso.get().load(itemPositon.getLogo()).into(myviwHolder.imageView);

        setAnimiton(myviwHolder.itemView,position);
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
        return lotteryNameModelList.size();
    }

    class  MyviwHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

      private TextView textView_lotteryName,
              textView_status,textView_text_lotDate;

      private ImageView imageView;

        public MyviwHolder(@NonNull View itemView) {
            super(itemView);

            textView_lotteryName=(TextView)itemView.findViewById(R.id.text_lotName);
            textView_status=(TextView)itemView.findViewById(R.id.textLot_status);
            textView_text_lotDate=(TextView)itemView.findViewById(R.id.text_lotDate);

            imageView=(ImageView)itemView.findViewById(R.id.imageLottery);

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
