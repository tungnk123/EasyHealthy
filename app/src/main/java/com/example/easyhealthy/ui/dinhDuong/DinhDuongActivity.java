package com.example.easyhealthy.ui.dinhDuong;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HistoryListAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.model.NutritionData;
import com.example.easyhealthy.ui.food.DetailedFoodActivity;
import com.example.easyhealthy.ui.nutrition.AddNewNutritionActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class DinhDuongActivity extends AppCompatActivity {

    public RecyclerView rcvNoResult;
    public RecyclerView rcvHistory;

    public RecyclerView rcvHistoryFood;
    public RecyclerView rcvCurrentFood;
    public ListWithNoImageAdapter adapter;
    public HistoryListAdapter historyListAdapter;

    Button btnAddNewNutrition;
    List<String> dataset;

    List<String> curFood;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == 101) {
                String tenChatDinhDuong = result.getData().getStringExtra("TEN_CHAT_DINH_DUONG");
                String moTa = result.getData().getStringExtra("MO_TA");
                String tenDonVi = result.getData().getStringExtra("TEN_DON_VI");
//                Toast.makeText(getApplicationContext(), "Add " + tenChatDinhDuong, Toast.LENGTH_LONG).show();
                List<String> modifiableDataset = new ArrayList<>(dataset);
                modifiableDataset.add(tenChatDinhDuong);
                dataset = modifiableDataset;
                adapter = new ListWithNoImageAdapter(dataset);
                adapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String item) {
                        Intent intent = new Intent(getApplicationContext(), DetailedNutritionActivity.class);
                        intent.putExtra("title", item);
                        startActivity(intent);
                    }
                });
                adapter.notifyDataSetChanged();
                rcvNoResult.setAdapter(adapter);
                rcvNoResult.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "Add " + tenChatDinhDuong, Toast.LENGTH_LONG).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_duong);
        addControls();
        addEvents();


    }


    private void addControls() {
        dataset = Arrays.asList(
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
                "Vitamin A"
                );
        NutritionData[] dataSet = {
                new NutritionData("Chất đạm", Calendar.getInstance().getTime(), "20:01", 200),
                new NutritionData("Chất xơ", Calendar.getInstance().getTime(), "20:01", 100),
                new NutritionData("Chất béo", Calendar.getInstance().getTime(), "20:01", 300),


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

        rcvHistoryFood = (RecyclerView) findViewById(R.id.rcv_baiBao);
        rcvCurrentFood = (RecyclerView) findViewById(R.id.rcv_curFood);
        curFood = Arrays.asList(
            "Bánh mì",
                "Phở",
                "Cơm sườn"
        );
        adapter = new ListWithNoImageAdapter(curFood);
        adapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), DetailedFoodActivity.class);
                intent.putExtra("title", item);
                startActivity(intent);
            }
        });
        rcvCurrentFood.setAdapter(adapter);
        rcvCurrentFood.setLayoutManager(new LinearLayoutManager(this));

        btnAddNewNutrition = (Button) findViewById(R.id.btn_addNewSinhHieu);

    }

    private void addEvents() {
        btnAddNewNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(getApplicationContext(), AddNewNutritionActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

}