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

public class NguyCoAdapter extends RecyclerView.Adapter<NguyCoAdapter.ListItemHolder> {
    private final DuyetItem[] localDataSet;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public NguyCoAdapter(DuyetItem[] dataSet) {
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
        holder.tvTitle.setText(localDataSet[position].getTitle());
        holder.imageView.setImageResource(localDataSet[position].getIcon());
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
        void onItemClick(DuyetItem item);
    }

}
