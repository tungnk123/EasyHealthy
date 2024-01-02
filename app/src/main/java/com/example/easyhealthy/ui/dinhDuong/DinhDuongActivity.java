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
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HistoryListAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.model.NutritionData;
import com.example.easyhealthy.ui.food.AddNewFoodActivity;
import com.example.easyhealthy.ui.food.DetailedFoodActivity;
import com.example.easyhealthy.ui.nutrition.AddNewNutritionActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

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

    public ListWithNoImageAdapter foodAdapter;
    public HistoryListAdapter historyListAdapter;

    Button btnAddNewNutrition;
    Button btnAddFood;
    List<String> dataset;

    List<String> curFood;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    NutritionData[] dataSet;

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

            if (result != null && result.getResultCode() == 102) {
                String tenThucAn = result.getData().getStringExtra("TEN_THUC_AN");
                String moTa = result.getData().getStringExtra("MO_TA");
                String tenDonVi = result.getData().getStringExtra("TEN_DON_VI");

                curFood.add(tenThucAn);
                foodAdapter.notifyDataSetChanged();


            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinh_duong);
        addControls();
        addEvents();
        updateDataInRcv();

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
        dataSet = new NutritionData[]{
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
        historyListAdapter.setOnItemClickListener(new HistoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NutritionData item) {
                Intent intent = new Intent(getApplicationContext(), DetailedNutritionActivity.class);
                intent.putExtra("title", item.getType());
                startActivity(intent);
            }
        });
        rcvHistory.setAdapter(historyListAdapter);
        rcvHistory.setLayoutManager(new LinearLayoutManager(this));

        rcvHistoryFood = (RecyclerView) findViewById(R.id.rcv_baiBao);
        rcvCurrentFood = (RecyclerView) findViewById(R.id.rcv_curFood);
        curFood = new ArrayList<>();
        curFood.add("Bánh mì");
        curFood.add("Phở");
        curFood.add("Cơm sườn");
        foodAdapter = new ListWithNoImageAdapter(curFood);
        foodAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), DetailedFoodActivity.class);
                intent.putExtra("title", item);
                startActivity(intent);
            }
        });
        rcvCurrentFood.setAdapter(foodAdapter);
        rcvCurrentFood.setLayoutManager(new LinearLayoutManager(this));

        btnAddNewNutrition = (Button) findViewById(R.id.btn_addNewSinhHieu);
        btnAddFood = findViewById(R.id.btn_addThucAn);

    }

    private void addEvents() {
        btnAddNewNutrition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(getApplicationContext(), AddNewNutritionActivity.class));
            }
        });

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(getApplicationContext(), AddNewFoodActivity.class));
            }
        });

        Toolbar toolbar = findViewById(R.id.tb_dinhDuong);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    public void updateDataInRcv() {
        firestore.collection("Chất đạm")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[0] = new NutritionData(document);
                        historyListAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Chất xơ")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[1] = new NutritionData(document);
                        historyListAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Chất béo")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[2] = new NutritionData(document);
                        historyListAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });

    }



}