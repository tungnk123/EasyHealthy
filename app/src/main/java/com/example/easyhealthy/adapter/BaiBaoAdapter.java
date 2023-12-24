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

public class BaiBaoAdapter extends RecyclerView.Adapter<BaiBaoAdapter.ListItemHolder> {
    private final DuyetItem[] localDataSet;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public BaiBaoAdapter(DuyetItem[] dataSet) {
        localDataSet = dataSet;
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bai_bao_item, parent, false);

        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.textView.setText(localDataSet[position].getTitle());
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
        private final TextView textView;
        private final ImageView imageView;
        public ListItemHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            imageView = (ImageView) view.findViewById(R.id.img_startIcon);
            textView = (TextView) view.findViewById(R.id.tv_itemTitle);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(DuyetItem item);
    }

}
