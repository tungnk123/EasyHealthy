package com.example.easyhealthy.ui.sinh_hieu;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.adapter.SinhHieuAdapter;
import com.example.easyhealthy.databinding.ActivitySinhHieuBinding;
import com.example.easyhealthy.model.HoatDongData;
import com.example.easyhealthy.ui.nutrition.AddNewNutritionActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SinhHieuActivity extends AppCompatActivity {


    RecyclerView rcvAllSinhHieu;
    RecyclerView rcvSinhHieuHistory;

    ListWithNoImageAdapter listWithNoImageAdapter;
    SinhHieuAdapter sinhHieuAdapter;

    List<HoatDongData> hoatDongHistoryList;

    List<String> dataSet;

    Button btnThemSinhHieu;

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result != null && result.getResultCode() == 101) {
                String tenSinhHieu = result.getData().getStringExtra("TEN_SINH_HIEU");
                String moTa = result.getData().getStringExtra("MO_TA");
                String tenDonVi = result.getData().getStringExtra("TEN_DON_VI");
//                Toast.makeText(getApplicationContext(), "Add " + tenChatDinhDuong, Toast.LENGTH_LONG).show();
                List<String> modifiableDataset = new ArrayList<>(dataSet);
                modifiableDataset.add(tenSinhHieu);
                dataSet = modifiableDataset;
                listWithNoImageAdapter = new ListWithNoImageAdapter(dataSet);
                listWithNoImageAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(String item) {
                        Intent intent = new Intent(getApplicationContext(), ChiTietSinhHieuActivity.class);
                        intent.putExtra("title", item);
                        startActivity(intent);
                    }
                });
                listWithNoImageAdapter.notifyDataSetChanged();
                rcvAllSinhHieu.setAdapter(listWithNoImageAdapter);
                rcvAllSinhHieu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                Toast.makeText(getApplicationContext(), "Add " + tenSinhHieu, Toast.LENGTH_LONG).show();
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sinh_hieu);
        addControls();
        addEvents();

    }

    void addControls() {
        rcvAllSinhHieu = (RecyclerView) findViewById(R.id.rcv_allSinhHieu);
        rcvSinhHieuHistory = (RecyclerView) findViewById(R.id.rcv_sinhHieuHistory);

        dataSet = Arrays.asList("Đường huyết","Oxi trong máu", "Thân nhiệt", "Nhịp tim", "Tần số hô hấp");
        listWithNoImageAdapter = new ListWithNoImageAdapter(dataSet);
        listWithNoImageAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSinhHieuActivity.class);
                intent.putExtra("title", item);
                addMota(intent, item);
                addDonViDo(intent, item);
                startActivity(intent);
            }
        });

        rcvAllSinhHieu.setAdapter(listWithNoImageAdapter);
        rcvAllSinhHieu.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        hoatDongHistoryList = new ArrayList<>();
        hoatDongHistoryList.add(new HoatDongData("Đường huyết", R.drawable.ic_glucose, 200, "mmol/L"));
        hoatDongHistoryList.add(new HoatDongData("Oxi trong máu", R.drawable.ic_oxy, 4, "%"));
        hoatDongHistoryList.add(new HoatDongData("Thân nhiệt", R.drawable.ic_temperature, 30, "độ C"));
        sinhHieuAdapter = new SinhHieuAdapter(hoatDongHistoryList);
        sinhHieuAdapter.setOnItemClickListener(new SinhHieuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HoatDongData item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietSinhHieuActivity.class);
                intent.putExtra("title", item.getTitle());
                addMota(intent, item.getTitle());
                addDonViDo(intent, item.getTitle());
                startActivity(intent);
            }
        });
        rcvSinhHieuHistory.setAdapter(sinhHieuAdapter);
        rcvSinhHieuHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        btnThemSinhHieu = (Button) findViewById(R.id.btn_addNewSinhHieu);


    }

    private void addMota(Intent intent, String item) {
        switch (item) {
            case "Đường huyết":
                intent.putExtra("MO_TA", "Đo lường nồng độ đường huyết để kiểm tra mức độ đường trong máu, cung cấp thông tin quan trọng về quản lý đáng kể cho người dùng đang theo dõi tiêu chí sức khỏe của họ.");
                break;
            case "Oxi trong máu":
                intent.putExtra("MO_TA", "Đo lường sự bão hòa oxi trong máu, giúp theo dõi cung cấp oxi cho cơ thể và đánh giá tình trạng hô hấp.");
                break;
            case "Thân nhiệt":
                intent.putExtra("MO_TA", "Đo lường nhiệt độ cơ thể để theo dõi sự biến động, cung cấp thông tin quan trọng về tình trạng sức khỏe và các biểu hiện của bệnh.");
                break;
            case "Nhịp tim":
                intent.putExtra("MO_TA", "Đánh giá nhịp tim, cung cấp thông tin quan trọng về tình trạng tim mạch và hoạt động vận động.");
                break;
            case "Tần số hô hấp":
                intent.putExtra("MO_TA", "Đo lường số lần hô hấp trong một khoảng thời gian, giúp theo dõi chức năng hô hấp và đánh giá sự thoải mái của đường hô hấp.");
                break;
        }
    }

    private void addDonViDo(Intent intent, String item) {
        switch (item) {
            case "Đường huyết":
                intent.putExtra("DON_VI_DO", "mmol/L");
                break;
            case "Oxi trong máu":
                intent.putExtra("DON_VI_DO", "%");
                break;
            case "Thân nhiệt":
                intent.putExtra("DON_VI_DO", "độ C");
                break;
            case "Nhịp tim":
                intent.putExtra("DON_VI_DO", "nhịp");
                break;
            case "Tần số hô hấp":
                intent.putExtra("DON_VI_DO", "lần");
                break;
        }
    }

    void addEvents() {
        btnThemSinhHieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch(new Intent(getApplicationContext(), AddNewSinhHieuActivity.class));
            }
        });
    }
}