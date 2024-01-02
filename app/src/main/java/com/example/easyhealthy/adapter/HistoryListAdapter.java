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
import com.example.easyhealthy.model.NutritionData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HistoryListAdapter extends RecyclerView.Adapter<HistoryListAdapter.ListItemHolder> {
    private final NutritionData[] localDataSet;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public HistoryListAdapter(NutritionData[] dataSet) {
        localDataSet = dataSet;
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_list_item, parent, false);

        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.tvTitle.setText(localDataSet[position].getType());
        holder.tvNumber.setText(String.valueOf(localDataSet[position].getQuantity()));

        Date date = localDataSet[position].getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        String formattedDate = dateFormat.format(date);
        // Check if date is today or not
        if (formattedDate.equals(dateFormat.format(new Date()))) {
            holder.tvTime.setText(localDataSet[position].getTime());
        }
        else {
            holder.tvTime.setText(formattedDate);
        }
        holder.itemView.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onItemClick(localDataSet[position]);
            }
        });
    }

    @Override
    public int getItemCount() {
        return localDataSet.length;
    }

    public static class ListItemHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;
        private final TextView tvNumber;

        private final TextView tvTime;
        public ListItemHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            tvTitle = (TextView) view.findViewById(R.id.tv_itemTitle);
            tvNumber = view.findViewById(R.id.tv_itemNumber);
            tvTime = view.findViewById(R.id.tv_itemTime);

        }

    }
    public interface OnItemClickListener {
        void onItemClick(NutritionData item);
    }

}
