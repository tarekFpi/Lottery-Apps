package com.Rbp.world.Team;

import android.view.View;
import android.widget.TextView;

import com.Rbp.world.R;

import de.blox.graphview.GraphView;

public class SimpleViewHolder extends GraphView.ViewHolder {
  public  TextView textView, name, UID, ref_uid;
  TextView team_team_A, team_team_B, team_team_C;

    public SimpleViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.team_text);
//        name = itemView.findViewById(R.id.team_user_name);
//        UID = itemView.findViewById(R.id.team_user_id);
//        ref_uid = itemView.findViewById(R.id.team_user_ref_id);
//        team_team_A = itemView.findViewById(R.id.team_team_A);
//        team_team_B = itemView.findViewById(R.id.team_team_B);
//        team_team_C = itemView.findViewById(R.id.team_team_C);

    }
}

