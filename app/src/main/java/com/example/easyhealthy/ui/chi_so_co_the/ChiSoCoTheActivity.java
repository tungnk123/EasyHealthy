package com.example.easyhealthy.ui.chi_so_co_the;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HoatDongAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.HoatDongData;
import com.example.easyhealthy.ui.hoatdong.ChiTietHoatDongActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChiSoCoTheActivity extends AppCompatActivity {


    RecyclerView rcvAllChiSoCoThe;
    RecyclerView rcvChieuCaoCanNang;

    ListWithNoImageAdapter listWithNoImageAdapter;
    HoatDongAdapter hoatDongAdapter;

    List<HoatDongData> hoatDongHistoryList;

    List<String> dataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_so_co_the);
        addControls();
        addEvents();
    }

    void addControls() {
        rcvAllChiSoCoThe = (RecyclerView) findViewById(R.id.rcv_allChiSoCoThe);
        rcvChieuCaoCanNang = (RecyclerView) findViewById(R.id.rcv_chieuCaoCanNang);

        dataSet = Arrays.asList("Chỉ số khối cơ thể (BMI)", "Chu vi vòng eo", "Phần trăm chất béo cơ thể", "Khoảng cách bơi", "Thân nhiệt chuẩn", "Trọng lượng nạc cơ thể");
        listWithNoImageAdapter = new ListWithNoImageAdapter(dataSet);
        listWithNoImageAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietChiSoCoTheActivity.class);
                intent.putExtra("title", item);
                addMota(intent, item);
                addDonViDo(intent, item);
                startActivity(intent);
            }
        });

        rcvAllChiSoCoThe.setAdapter(listWithNoImageAdapter);
        rcvAllChiSoCoThe.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        hoatDongHistoryList = new ArrayList<>();
        hoatDongHistoryList.add(new HoatDongData("Chiều cao", R.drawable.ic_height, 170, "cm"));
        hoatDongHistoryList.add(new HoatDongData("Cân nặng", R.drawable.ic_height, 55, "Kg"));
        hoatDongAdapter = new HoatDongAdapter(hoatDongHistoryList);
        hoatDongAdapter.setOnItemClickListener(new HoatDongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HoatDongData item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietChiSoCoTheActivity.class);
                intent.putExtra("title", item.getTitle());
                addMotaChieuCaoCanNang(intent, item.getTitle());
                addDonViDoChieuCaoCanNang(intent, item.getTitle());
                startActivity(intent);
            }
        });
        rcvChieuCaoCanNang.setAdapter(hoatDongAdapter);
        rcvChieuCaoCanNang.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void addMota(Intent intent, String item) {
        switch (item) {
            case "Trọng lượng nạc cơ thể":
                intent.putExtra("MO_TA", "Ứng dụng tính quãng đường đi bộ giúp bạn theo dõi mọi bước chân trên hành trình của mình. Khám phá thiên nhiên, ngắm cảnh, và duy trì sức khỏe với giao diện đơn giản, dễ sử dụng, đồng thời cung cấp thông tin chính xác về khoảng cách đã đi.");
                break;
            case "Thân nhiệt chuẩn":
                intent.putExtra("MO_TA", "Là đơn vị đo lường năng lượng trong thức ăn và đồ uống. Mỗi loại thức ăn có một lượng calo khác nhau, và calo là một chỉ số quan trọng để theo dõi lượng năng lượng tiêu thụ hàng ngày.");
                break;
            case "Khoảng cách bơi":
                intent.putExtra("MO_TA", "Ứng dụng đo khoảng cách đi bộ không chỉ giúp bạn theo dõi quãng đường, mà còn tính toán số phút thể dục dựa trên tốc độ và thời gian hoạt động. Điều này giúp bạn quản lý thời gian tập luyện hiệu quả và theo dõi tiến trình sức khỏe của mình.");
                break;
            case "Phần trăm chất béo cơ thể":
                intent.putExtra("MO_TA", "Ứng dụng đo khoảng cách đi bộ không chỉ giúp bạn theo dõi quãng đường mà còn ghi lại số phút đứng trong ngày. Điều này giúp bạn theo dõi thói quen đứng nhiều hơn, hỗ trợ sự thoải mái và chăm sóc sức khỏe toàn diện.");
                break;
            case "Chu vi vòng eo":
                intent.putExtra("MO_TA", "Ứng dụng theo dõi số phút di chuyển cho phép bạn ghi lại thời gian tổng cộng mà bạn đã dành cho hoạt động đi bộ trong ngày. Giúp bạn theo dõi và quản lý chính xác thời gian thực hiện hoạt động vận động, từ đó đảm bảo chế độ luyện tập và duy trì một lối sống sức khỏe.");
                break;
            case "Chỉ số khối cơ thể (BMI)":
                intent.putExtra("MO_TA", "Ứng dụng theo dõi khoảng cách bơi cung cấp thông tin chính xác về quãng đường bạn đã vượt qua trong các buổi tập. Bạn có thể dễ dàng theo dõi và đánh giá tiến trình của mình, từ đó tối ưu hóa chế độ tập luyện và cải thiện sức khỏe toàn diện.");
                break;
        }
    }

    private void addDonViDo(Intent intent, String item) {
        switch (item) {
            case "Trọng lượng nạc cơ thể":
                intent.putExtra("DON_VI_DO", "kg");
                break;
            case "Thân nhiệt chuẩn":
                intent.putExtra("DON_VI_DO", "độ C");
                break;
            case "Khoảng cách bơi":
                intent.putExtra("DON_VI_DO", "m");
                break;
            case "Phần trăm chất béo cơ thể":
                intent.putExtra("DON_VI_DO", "%");
                break;
            case "Chu vi vòng eo":
                intent.putExtra("DON_VI_DO", "cm");
                break;
            case "Chỉ số khối cơ thể (BMI)":
                intent.putExtra("DON_VI_DO", "kg/m2");
                break;
        }
    }

    void addEvents() {

    }
    private void addMotaChieuCaoCanNang(Intent intent, String item) {
        switch (item) {
            case "Chiều cao":
                intent.putExtra("MO_TA", "Ứng dụng tính chiều cao của người dùng để lưu lại thông tin.");
                break;
            case "Cân nặng":
                intent.putExtra("MO_TA", "Ứng dụng tính cân nặng của người dùng để lưu lại thông tin.");
                break;

        }
    }
    private void addDonViDoChieuCaoCanNang(Intent intent, String item) {
        switch (item) {
            case "Chiều cao":
                intent.putExtra("DON_VI_DO", "cm");
                break;
            case "Cân nặng":
                intent.putExtra("DON_VI_DO", "kg");
                break;

        }
    }

}