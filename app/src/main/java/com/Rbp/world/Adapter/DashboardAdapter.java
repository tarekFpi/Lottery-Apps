package com.Rbp.world.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.Rbp.world.Helpers.OnDashboardClickListener;
import com.Rbp.world.Model.DashboardItemModel;
import com.Rbp.world.R;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.DashboardViewHolder> implements Filterable {

    private Filter contactFilter = new Filter() {
        /* class com.lisbon.spc_world.adapter.DashboardAdapter.C34422 */

        /* access modifiers changed from: protected */
        public FilterResults performFiltering(CharSequence charSequence) {
            ArrayList arrayList = new ArrayList();
            if (charSequence == null || charSequence.length() == 0) {
                arrayList.addAll(DashboardAdapter.this.dashListFull);
            } else {
                String trim = charSequence.toString().toLowerCase().trim();
                Iterator it = DashboardAdapter.this.dashListFull.iterator();
                while (it.hasNext()) {
                    DashboardItemModel dashboardItemModel = (DashboardItemModel) it.next();
                    if (dashboardItemModel.getTitle().toLowerCase().contains(trim)) {
                        arrayList.add(dashboardItemModel);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = arrayList;
            return filterResults;
        }

        /* access modifiers changed from: protected */
        public void publishResults(CharSequence charSequence, FilterResults filterResults) {
            DashboardAdapter.this.dashboardItemList.clear();
            DashboardAdapter.this.dashboardItemList.addAll((List) filterResults.values);
            DashboardAdapter.this.notifyDataSetChanged();
        }
    };
    private Context context;
    private ArrayList<DashboardItemModel> dashListFull;
    private List<DashboardItemModel> dashboardItemList;
    private OnDashboardClickListener onDashboardClickListener;

    public DashboardAdapter(Context context2, List<DashboardItemModel> list, OnDashboardClickListener onDashboardClickListener2) {
        this.context = context2;
        this.dashboardItemList = list;
        this.onDashboardClickListener = onDashboardClickListener2;
        this.dashListFull = new ArrayList<>(list);
    }

    @Override // androidx.recyclerview.widget.RecyclerView.AbstractC0928Adapter
    public DashboardViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new DashboardViewHolder(LayoutInflater.from(this.context).inflate(R.layout.layout_dash_items, viewGroup, false));
    }

    public void onBindViewHolder(DashboardViewHolder dashboardViewHolder, int i) {
        final DashboardItemModel dashboardItemModel = this.dashboardItemList.get(i);
        dashboardViewHolder.dashItemImage.setImageResource(dashboardItemModel.getDashImage());
        dashboardViewHolder.dashItemTitle.setText(dashboardItemModel.getTitle());
        dashboardViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            /* class com.lisbon.spc_world.adapter.DashboardAdapter.View$OnClickListenerC34411 */

            public void onClick(View view) {
                DashboardAdapter.this.onDashboardClickListener.onDashBoardItem(dashboardItemModel.getId());
            }
        });

    }

    @Override // androidx.recyclerview.widget.RecyclerView.AbstractC0928Adapter
    public int getItemCount() {
        return this.dashboardItemList.size();
    }

    public class DashboardViewHolder extends RecyclerView.ViewHolder {
        private ImageView dashItemImage;
        private TextView dashItemTitle;

        public DashboardViewHolder(View view) {
            super(view);
            this.dashItemImage = (ImageView) view.findViewById(R.id.dashItemImage);
            this.dashItemTitle = (TextView) view.findViewById(R.id.dashItemName);
        }
    }

    public Filter getFilter() {
        return this.contactFilter;
    }
}
