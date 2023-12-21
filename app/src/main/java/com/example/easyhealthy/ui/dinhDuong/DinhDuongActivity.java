package com.example.easyhealthy.ui.dinhDuong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HistoryListAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;

public class DinhDuongActivity extends AppCompatActivity {

    public RecyclerView rcvNoResult;
    public RecyclerView rcvHistory;

    public RecyclerView rcvHistoryFood;
    public RecyclerView rcvCurrentFood;
    public ListWithNoImageAdapter adapter;
    public HistoryListAdapter historyListAdapter;
    String[] dataset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_duong);
        addControls();
        addEvents();


    }


    private void addControls() {
        dataset = new String[] {
                "Canxi",
                "Chất béo không bão hòa",
                "Chất đạm",
                "Chất xơ",
                "Cholesterol thực phẩm",
                "Clorua",
                "Crôm",
                "Đồng",
                "Đường thực phẩm",
                "Floate",
                "I-ốt",
                "Kali",
                "Kẽm",
                "Magie",
                "Mangan",
                "Tinh bột",
                "Sắt",
                "Selen",
                "Tổng Chất Béo",
                "Vitamin A",

        };
        DuyetItem[] dataSet = {
                new DuyetItem("Dinh dưỡng", R.drawable.ic_nitrition),
                new DuyetItem("Nước", R.drawable.ic_water),
                new DuyetItem("Sinh hiệu", R.drawable.ic_sinhhieu),
                new DuyetItem("Ngủ", R.drawable.ic_bed),
                new DuyetItem("Triệu chứng", R.drawable.ic_trieuchung),
                new DuyetItem("Hoạt động", R.drawable.ic_fire),
                new DuyetItem("Số đo cơ thể", R.drawable.ic_body),

        };

        rcvNoResult = (RecyclerView) findViewById(R.id.rcv_dinhDuong_noResultRcv);
        adapter = new ListWithNoImageAdapter(dataset);
        adapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), DetailedNutritionActivity.class);
                intent.putExtra("title", item);
                startActivity(intent);
            }
        });
        rcvNoResult.setAdapter(adapter);
        rcvNoResult.setLayoutManager(new LinearLayoutManager(this));

        rcvHistory = (RecyclerView) findViewById(R.id.rcv_dinhDuong_historyNutrition);
        historyListAdapter = new HistoryListAdapter(dataSet);
        rcvHistory.setAdapter(historyListAdapter);
        rcvHistory.setLayoutManager(new LinearLayoutManager(this));

        rcvHistoryFood = (RecyclerView) findViewById(R.id.rcv_dinhDuong_historyFood);
        rcvCurrentFood = (RecyclerView) findViewById(R.id.rcv_dinhDuong_currentFood);
        String[] curFood = new String[] {
            "Bánh mì",
                "Phở",
                "Cơm sườn"
        };
        adapter = new ListWithNoImageAdapter(curFood);
        rcvCurrentFood.setAdapter(adapter);
        rcvCurrentFood.setLayoutManager(new LinearLayoutManager(this));

    }

    private void addEvents() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }
}