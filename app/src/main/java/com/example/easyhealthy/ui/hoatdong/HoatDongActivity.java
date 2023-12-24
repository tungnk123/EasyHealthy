package com.example.easyhealthy.ui.hoatdong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HoatDongAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.food.DetailedFoodActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;

import java.util.Arrays;
import java.util.List;

public class HoatDongActivity extends AppCompatActivity {


    RecyclerView rcvAllHoatDong;
    RecyclerView rcvHoatDongHistory;

    ListWithNoImageAdapter listWithNoImageAdapter;
    HoatDongAdapter hoatDongAdapter;

    DuyetItem[] hoatDongHistoryList;

    List<String> dataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoat_dong);
        addControls();
        addEvents();
    }

    void addControls() {
        rcvAllHoatDong = (RecyclerView) findViewById(R.id.rcv_allHoatDong);
        rcvHoatDongHistory = (RecyclerView) findViewById(R.id.rcv_hoatDongHistory);

        dataSet = Arrays.asList("Quãng đường chạy bộ", "Calories tiêu thụ", "Số phút thể dục", "Khoảng cách bơi", "Số phút di chuyển", "Số phút đứng");
        listWithNoImageAdapter = new ListWithNoImageAdapter(dataSet);
        listWithNoImageAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), DetailedNutritionActivity.class);
                intent.putExtra("title", item);
                startActivity(intent);
            }
        });

        rcvAllHoatDong.setAdapter(listWithNoImageAdapter);
        rcvAllHoatDong.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        hoatDongHistoryList = new DuyetItem[] {
                new DuyetItem("Calories tiêu thụ", R.drawable.ic_fire),
                new DuyetItem("Quãng đường chạy bộ", R.drawable.ic_fire),
                new DuyetItem("Số phút thể dục", R.drawable.ic_fire),
        };

        hoatDongAdapter = new HoatDongAdapter(hoatDongHistoryList);
        hoatDongAdapter.setOnItemClickListener(new HoatDongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Intent intent = new Intent(getApplicationContext(), DetailedNutritionActivity.class);
                intent.putExtra("title", item.getTitle());
                startActivity(intent);
            }
        });
        rcvHoatDongHistory.setAdapter(hoatDongAdapter);
        rcvHoatDongHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    void addEvents() {

    }
}