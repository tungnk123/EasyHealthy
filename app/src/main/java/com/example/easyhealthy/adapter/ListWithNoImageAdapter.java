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

public class ListWithNoImageAdapter extends RecyclerView.Adapter<ListWithNoImageAdapter.ListItemHolder> {
    private final String[] localDataSet;
    private OnItemClickListener mListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }



    public ListWithNoImageAdapter(String[] dataSet) {
        localDataSet = dataSet;
    }
    @NonNull
    @Override
    public ListItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_noimage, parent, false);

        return new ListItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListItemHolder holder, int position) {
        holder.textView.setText(localDataSet[position]);
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
        public ListItemHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View
            textView = (TextView) view.findViewById(R.id.tv_itemTitle);
        }

        public TextView getTextView() {
            return textView;
        }
    }
    public interface OnItemClickListener {
        void onItemClick(String item);
    }


}
