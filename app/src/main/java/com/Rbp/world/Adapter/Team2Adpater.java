package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.Rbp.world.Model.TeamModel;
import com.Rbp.world.R;
import com.Rbp.world.Team.SimpleViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.GraphView;

public class Team2Adpater extends GraphAdapter<GraphView.ViewHolder> {

    Graph graph;
    Context context;
    List<TeamModel> modelList;

    List<Graph> graphList;


    public Team2Adpater(@NotNull Graph graph, Context context, List<TeamModel> modelList, List<Graph> graphList) {
        super(graph);
        this.context = context;
        this.modelList = modelList;
        this.graphList = graphList;
    }

    @Override
    public void onBindViewHolder(@NotNull GraphView.ViewHolder viewHolder, @NotNull Object data, int position) {

        TeamModel teamModel = modelList.get(position);

        List<String> matchList = new ArrayList<String>();
        Pattern regex = Pattern.compile("\\((.*?)\\)");
        Matcher regexMatcher = regex.matcher(data.toString());

        while (regexMatcher.find()) {//Finds Matching Pattern in String
            matchList.add(regexMatcher.group(1));//Fetching Group from String
        }

        for (String str : matchList) {

            String show = str.substring(5);
            ((SimpleViewHolder) viewHolder).textView.setText(teamModel.getName());
            //  ((SimpleViewHolder) viewHolder).name.setText(show);

        }

    }

    @NonNull
    @NotNull
    @Override
    public GraphView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int i) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_card, parent, false);
        return new SimpleViewHolder(view) ;
    }

    @Override
    public int getCount()
    {
        return graphList.size();
    }

    @Override
    public Object getItem(int position) {
        return graph.getNodeAtPosition(position);
    }

    @Override
    public boolean isEmpty() {
        return false;
    }


}
