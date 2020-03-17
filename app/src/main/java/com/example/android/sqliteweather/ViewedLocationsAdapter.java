package com.example.android.sqliteweather;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.sqliteweather.data.room.ViewedLocation;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class ViewedLocationsAdapter
        extends RecyclerView.Adapter<ViewedLocationsAdapter.ViewedLocationViewHolder> {
    private List<ViewedLocation> mViewedLocations;
    private OnViewedLocationClickListener mViewedLocationClickListener;

    public interface OnViewedLocationClickListener {
        void onViewedLocationClick(ViewedLocation viewedLocation);
    }

    public ViewedLocationsAdapter(OnViewedLocationClickListener clickListener) {
        this.mViewedLocationClickListener = clickListener;
    }

    public void updateForecastItems(List<ViewedLocation> viewedLocations) {
        mViewedLocations = viewedLocations;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewedLocationsAdapter.ViewedLocationViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.location_in_drawer, parent, false);
        return new ViewedLocationsAdapter.ViewedLocationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewedLocationsAdapter.ViewedLocationViewHolder holder, int position) {
        holder.bind(mViewedLocations.get(position));
    }

    @Override
    public int getItemCount() {
        if (mViewedLocations != null) {
            return mViewedLocations.size();
        } else {
            return 0;
        }
    }

    class ViewedLocationViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mNameTV;

        public ViewedLocationViewHolder(@NonNull View itemView) {
            super(itemView);
            mNameTV = itemView.findViewById(R.id.tv_location_name);
            itemView.setOnClickListener(this);
        }

        public void bind(ViewedLocation viewedLocation) {
            mNameTV.setText(viewedLocation.name);
        }

        @Override
        public void onClick(View view) {
            mViewedLocationClickListener.onViewedLocationClick(
                    mViewedLocations.get(getAdapterPosition()));
        }
    }
}
