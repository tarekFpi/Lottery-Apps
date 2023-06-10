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

import com.Rbp.world.Model.LotteryGiftModel;
import com.Rbp.world.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public  class LotteryGiftAdapter extends RecyclerView.Adapter<LotteryGiftAdapter.MyViewHolder> {

    private List<LotteryGiftModel>modelList;

    private Context context;

    private static int listposition=-1;

    private  static onItemClickLisiner clickLisiner;

    public LotteryGiftAdapter(List<LotteryGiftModel> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from(context);

       View view= layoutInflater.inflate(R.layout.lottery_gifty_layout,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myviwHolder, int position) {

        LotteryGiftModel itemPositon=modelList.get(position);


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
            animation.setDuration(1000);
            viewAnimition.startAnimation(animation);
            listposition = position;
        }
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public  class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView textView_lotteryName,
                textView_status,textView_text_lotDate;

        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_lotteryName=(TextView)itemView.findViewById(R.id.text_GiftlotName);
            textView_status=(TextView)itemView.findViewById(R.id.textGiftLot_status);
            textView_text_lotDate=(TextView)itemView.findViewById(R.id.text_GiftlotDate);

            imageView=(ImageView)itemView.findViewById(R.id.imageGiftLottery);

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

