package com.example.deliveryrouteplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.deliveryrouteplanner.Entities.Stop;
import com.example.deliveryrouteplanner.R;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class StopAdapter extends RecyclerView.Adapter<StopAdapter.StopViewHolder> {
    private final Context context;
    private List<Stop> mStops = new ArrayList<>();
    private final LayoutInflater mInflater;

    class StopViewHolder extends RecyclerView.ViewHolder {
        private final TextView stopItemView;
        private StopViewHolder (View itemView) {
            super(itemView);
            stopItemView = itemView.findViewById(R.id.textViewStopListItem);
            itemView.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick (View view) {
                    int position = getAdapterPosition();
                    final Stop current = mStops.get(position);
                    Intent intent = new Intent(context, StopDetails.class);
                    intent.putExtra("stop", current);
                    context.startActivity(intent);

                }
            });
        }
    }

    @NonNull
    @Override
    public StopAdapter.StopViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.stop_list_item, parent, false);
        return new StopViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StopAdapter.StopViewHolder holder, int position) {
        if(!mStops.isEmpty()) {
            Stop current = mStops.get(position);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.US);
            String etaFormatted = timeFormat.format(current.getEstArrival());
            holder.stopItemView.setText("Stop: " + current.getAddress() + " ETA: " + etaFormatted);
        }
    }

    @Override
    public int getItemCount() {
        if (mStops != null) {
            return mStops.size();
        }
        else {
            return 0;
        }
    }

    public StopAdapter (Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
    }

    public void setStops (List<Stop> stops) {
        mStops = stops;
        notifyDataSetChanged();
    }
}
