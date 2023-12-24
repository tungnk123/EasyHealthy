package com.example.easyhealthy.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.model.HoatDongData;
import com.example.easyhealthy.model.NutritionData;

import java.util.List;

public class HoatDongAdapter extends RecyclerView.Adapter<HoatDongAdapter.ListItemHolder> {
    private final List<HoatDongData> localDataSet;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public HoatDongAdapter(List<HoatDongData> dataSet) {
        localDataSet = dataSet;
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.nguy_co_item, parent, false);

        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.tvTitle.setText(localDataSet.get(position).getTitle());
        holder.imageView.setImageResource(localDataSet.get(position).getIcon());
        String numberData = localDataSet.get(position).getNumber() + " " + localDataSet.get(position).getDonViDo();
        holder.tvNumber.setText(numberData);
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(localDataSet.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.size();
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvNumber;
        private final ImageView imageView;
        public ListItemHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = (ImageView) view.findViewById(R.id.img_startIcon);
            tvTitle = (TextView) view.findViewById(R.id.tv_itemTitle);
            tvNumber = (TextView) view.findViewById(R.id.tv_itemNumber);
        }

    }
    public interface OnItemClickListener {
        void onItemClick(HoatDongData item);
    }

}
