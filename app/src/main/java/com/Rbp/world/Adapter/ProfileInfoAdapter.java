package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Rbp.world.Model.ProfileInfoModel;
import com.Rbp.world.R;

import java.util.List;

public class ProfileInfoAdapter extends RecyclerView.Adapter<ProfileInfoAdapter.ProfileViewHolder> {
    private Context context;
    private List<ProfileInfoModel> dataList;

    public ProfileInfoAdapter(Context context, List<ProfileInfoModel> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.profile_info_list, parent, false);
        return new ProfileViewHolder(view);
    }

    public void updateData(List<ProfileInfoModel> dataItem) {
        dataList.clear();
        dataList.addAll(dataItem);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        dataList.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public void onBindViewHolder(@NonNull ProfileViewHolder holder, int position) {

        ProfileInfoModel profileInfoModel = dataList.get(position);

        holder.fullname.setText(profileInfoModel.getName());
        holder.userId.setText(profileInfoModel.getUsername());
        holder.fatherName.setText("" + profileInfoModel.getFatherName());
        holder.motherName.setText("" + profileInfoModel.getMotherName());
        holder.mobileNumber.setText("" + profileInfoModel.getMobileNo());

        holder.address.setText("" + profileInfoModel.getAddress());
        holder.email.setText("" + profileInfoModel.getEmail());
        holder.sex.setText("" + profileInfoModel.getSex());
        holder.nationalID.setText("" + profileInfoModel.getVotarId());

        holder.nomineeName.setText("" + profileInfoModel.getNomineName());
        holder.nomineeRelation.setText("" + profileInfoModel.getNomineReleation());
        holder.nominee_birthDate.setText("" + profileInfoModel.getNomineDateOfBirth());
        holder.religion.setText("" + profileInfoModel.getReligion());
        holder.birthDate.setText("" + profileInfoModel.getDateOfBirth());
        holder.profile_education.setText("" + profileInfoModel.getEducationalQualification());
        holder.profile_occuption.setText("" + profileInfoModel.getOccuption());

        holder.joindate.setText("" + profileInfoModel.getJoinDate());

        holder.packages.setText("" + profileInfoModel.getPackage_type());
        holder.designation.setText("" + profileInfoModel.getDesignation());

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        TextView fullname, userId, fatherName, motherName, mobileNumber, address,
                email, sex, nationalID, nomineeName, nomineeRelation, nominee_birthDate,
                religion, birthDate,profile_education,profile_occuption,joindate ,packages,designation;

        public ProfileViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.profile_fullname);
            userId = itemView.findViewById(R.id.profile_userId);
            fatherName = itemView.findViewById(R.id.profile_father_Name);
            motherName = itemView.findViewById(R.id.profile_mother_Name);
            mobileNumber = itemView.findViewById(R.id.profile_mobile_No);
            address = itemView.findViewById(R.id.profile_address);
            email = itemView.findViewById(R.id.profile_email);
            sex = itemView.findViewById(R.id.profile_sex);
            nationalID = itemView.findViewById(R.id.profile_votar_Id);
            nomineeName = itemView.findViewById(R.id.profile_nomine_Name);
            nomineeRelation = itemView.findViewById(R.id.profile_nomine_Releation);
            nominee_birthDate = itemView.findViewById(R.id.profile_nomine_Date_Of_Birth);
            religion = itemView.findViewById(R.id.profile_religion);
            birthDate = itemView.findViewById(R.id.profile_date_Of_Birth);
            profile_education = itemView.findViewById(R.id.profile_education);
            profile_occuption=itemView.findViewById(R.id.profile_occuption);

            joindate=itemView.findViewById(R.id.profile_date_Of_join);

            packages=itemView.findViewById(R.id.profile_package);

            designation =itemView.findViewById(R.id.profile_founder);

        }
    }
}
