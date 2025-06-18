package com.example.deliveryrouteplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Route;
import com.example.deliveryrouteplanner.R;


import java.util.ArrayList;
import java.util.List;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.RouteViewHolder> {
    private final Context context;
    private List<Route> mRoutes = new ArrayList<>();
    private final LayoutInflater mInflater;

    class RouteViewHolder extends RecyclerView.ViewHolder {
        private final TextView routeItemView;
        private RouteViewHolder (View itemView) {
            super(itemView);
            routeItemView = itemView.findViewById(R.id.textViewRouteListItem);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view){
                    int position = getAdapterPosition();
                    final Route current = mRoutes.get(position);
                    Intent intent = new Intent(context, RouteDetails.class);
                    intent.putExtra("id", current.getRouteID());
                    intent.putExtra("startLocation", current.getStartLocation());
                    intent.putExtra("endLocation", current.getEndLocation());
                    intent.putExtra("date", current.getDate());
                    intent.putExtra("totalDistance", current.getTotalDistance());
                    context.startActivity(intent);
                }
            });
        }

    }

    @NonNull
    @Override
    public RouteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.route_list_item, parent, false);
        return new RouteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RouteViewHolder holder, int position) {
        if (!mRoutes.isEmpty()) {
            Route current = mRoutes.get(position);
            holder.routeItemView.setText(current.getStartLocation() + " to " + current.getEndLocation());
        }
    }

    @Override
    public int getItemCount() {
        if (mRoutes != null) {
            return mRoutes.size();
        }
        else {
            return 0;
        }
    }


    public void setRoutes (List<Route> routes) {
        mRoutes = routes;
        notifyDataSetChanged();
    }

    public RouteAdapter(Context context) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
    }

}
