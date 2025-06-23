package com.example.deliveryrouteplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RouteSelectionAdapter extends RecyclerView.Adapter<RouteSelectionAdapter.RouteViewHolder> {

    final List<Route> routeList = new ArrayList<>();
    final Set<Integer> selectedRouteIds = new HashSet<>();
    private final LayoutInflater mInflater;

    public RouteSelectionAdapter (Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public static class RouteViewHolder extends RecyclerView.ViewHolder {
        CheckBox routeCheckBox;
        TextView routeInfo;

        public RouteViewHolder(@NonNull View itemView) {
            super(itemView);
            routeCheckBox = itemView.findViewById(R.id.routeCheckBox);
            routeInfo = itemView.findViewById(R.id.TextViewRouteInfo);
        }
    }

    @NonNull
    @Override
    public RouteSelectionAdapter.RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.route_selection_list_item, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteSelectionAdapter.RouteViewHolder holder, int position) {
            Route currentRoute = routeList.get(position);
            String info = "Route #" + currentRoute.getRouteID() +
                    "\nDate: " + new SimpleDateFormat("MM/dd/yyyy", Locale.US).format(currentRoute.getDate()) +
                    "\nFrom: " + currentRoute.getStartLocation() +
                    " To: " + currentRoute.getEndLocation();

            holder.routeInfo.setText(info);
            holder.routeCheckBox.setOnCheckedChangeListener(null);
            holder.routeCheckBox.setChecked(selectedRouteIds.contains(currentRoute.getRouteID()));
            holder.routeCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    selectedRouteIds.add(currentRoute.getRouteID());
                }
                else {
                    selectedRouteIds.remove(currentRoute.getRouteID());
                }
            });

    }

    @Override
    public int getItemCount() {
        return routeList.size();
    }

    public void setRoutes(List<Route> routes) {
        this.routeList.clear();
        this.routeList.addAll(routes);
        notifyDataSetChanged();
    }

    public Set<Integer> getSelectedRouteIds() {
        return selectedRouteIds;
    }

    public List<Route> getRouteList(){
        return new ArrayList<>(routeList);
    }
}
