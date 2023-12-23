package com.example.easyhealthy.adapter;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;

import java.util.List;

public class FoodNutritionAdapter extends RecyclerView.Adapter<FoodNutritionAdapter.FoodNutritionViewHolder> {

    List<Pair<String, Integer>> nutritionList;

    public FoodNutritionAdapter(List<Pair<String, Integer>> list) {
        nutritionList = list;
    }
    @NonNull
    @Override
    public FoodNutritionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.nutrition_food_item, parent, false);

        return new FoodNutritionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodNutritionViewHolder holder, int position) {
        holder.tvTitleNutrition.setText(nutritionList.get(position).first);
        holder.tvLuuLuong.setText(nutritionList.get(position).second + " mg");
    }

    @Override
    public int getItemCount() {
        return nutritionList.size();
    }

    class FoodNutritionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitleNutrition;
        TextView tvLuuLuong;
        public FoodNutritionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleNutrition = (TextView) itemView.findViewById(R.id.tv_nutritionFoodItem_title);
            tvLuuLuong = (TextView) itemView.findViewById(R.id.tv_nutritionFoodItem_value);
        }
    }
}
