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

import com.Rbp.world.Model.Placement_list_model;
import com.Rbp.world.R;

import java.util.List;

public class PlacementListAdpater extends RecyclerView.Adapter<PlacementListAdpater.PlacementListViewHolder> {

    private Context context;
    private List<Placement_list_model> dataList;

    private static int listposition=-1;

    public PlacementListAdpater(Context context, List<Placement_list_model> dataList) {
        this.context = context;
        this.dataList = dataList;
    }


    @Override
    public PlacementListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.placement_n_sponsor_list_item, parent, false);
        return new PlacementListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlacementListViewHolder holder, int position) {

        Placement_list_model placement_list_model = dataList.get(position);
        int serial_no=position+1;

        holder.serial.setText(""+serial_no);

        holder.userID.setText("User ID: \n"+placement_list_model.getUsername());
        holder.date.setText("Joining Date: \n"+placement_list_model.getJoinDate());
        holder.sponsor.setText("Sponsor ID: \n"+placement_list_model.getSponsorUserN());
        holder.designation.setText("Designation: \nMember");


        setAnimiton(holder.itemView,position);
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

        return dataList.size();
    }

    public class PlacementListViewHolder extends RecyclerView.ViewHolder {

        Button serial;
        TextView userID, date, sponsor, designation, teamA, teamB, teamC;

        public PlacementListViewHolder(@NonNull View itemView) {
            super(itemView);

            serial = itemView.findViewById(R.id.tv_placemetn_list_SL);
            userID = itemView.findViewById(R.id.tv_placemetn_list_userID);
            date = itemView.findViewById(R.id.tv_placemetn_list_date);
            sponsor = itemView.findViewById(R.id.tv_placemetn_list_sponsor);
            designation = itemView.findViewById(R.id.tv_placemetn_list_designation);

        }
    }
}
