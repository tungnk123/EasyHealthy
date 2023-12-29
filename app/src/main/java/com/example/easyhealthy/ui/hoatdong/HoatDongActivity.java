package com.example.easyhealthy.ui.hoatdong;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.HoatDongAdapter;
import com.example.easyhealthy.adapter.ListWithNoImageAdapter;
import com.example.easyhealthy.model.HoatDongData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HoatDongActivity extends AppCompatActivity {


    RecyclerView rcvAllHoatDong;
    RecyclerView rcvHoatDongHistory;

    ListWithNoImageAdapter listWithNoImageAdapter;
    HoatDongAdapter hoatDongAdapter;

    List<HoatDongData> hoatDongHistoryList;

    List<String> dataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoat_dong);
        addControls();
        addEvents();
    }

    void addControls() {
        rcvAllHoatDong = (RecyclerView) findViewById(R.id.rcv_allSinhHieu);
        rcvHoatDongHistory = (RecyclerView) findViewById(R.id.rcv_sinhHieuHistory);

        dataSet = Arrays.asList("Quãng đường chạy bộ", "Calories tiêu thụ", "Số phút thể dục", "Khoảng cách bơi", "Số phút di chuyển", "Số phút đứng");
        listWithNoImageAdapter = new ListWithNoImageAdapter(dataSet);
        listWithNoImageAdapter.setOnItemClickListener(new ListWithNoImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietHoatDongActivity.class);
                intent.putExtra("title", item);
                addMota(intent, item);
                addDonViDo(intent, item);
                startActivity(intent);
            }
        });

        rcvAllHoatDong.setAdapter(listWithNoImageAdapter);
        rcvAllHoatDong.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        hoatDongHistoryList = new ArrayList<>();
        hoatDongHistoryList.add(new HoatDongData("Calories tiêu thụ", R.drawable.ic_fire, 200, "calo"));
        hoatDongHistoryList.add(new HoatDongData("Quãng đường chạy bộ", R.drawable.ic_fire, 4, "km"));
        hoatDongHistoryList.add(new HoatDongData("Số phút thể dục", R.drawable.ic_fire, 30, "phút"));
        hoatDongAdapter = new HoatDongAdapter(hoatDongHistoryList);
        hoatDongAdapter.setOnItemClickListener(new HoatDongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(HoatDongData item) {
                Intent intent = new Intent(getApplicationContext(), ChiTietHoatDongActivity.class);
                intent.putExtra("title", item.getTitle());
                addMota(intent, item.getTitle());
                addDonViDo(intent, item.getTitle());
                startActivity(intent);
            }
        });
        rcvHoatDongHistory.setAdapter(hoatDongAdapter);
        rcvHoatDongHistory.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }

    private void addMota(Intent intent, String item) {
        switch (item) {
            case "Quãng đường chạy bộ":
                intent.putExtra("MO_TA", "Ứng dụng tính quãng đường đi bộ giúp bạn theo dõi mọi bước chân trên hành trình của mình. Khám phá thiên nhiên, ngắm cảnh, và duy trì sức khỏe với giao diện đơn giản, dễ sử dụng, đồng thời cung cấp thông tin chính xác về khoảng cách đã đi.");
                break;
            case "Calories tiêu thụ":
                intent.putExtra("MO_TA", "Là đơn vị đo lường năng lượng trong thức ăn và đồ uống. Mỗi loại thức ăn có một lượng calo khác nhau, và calo là một chỉ số quan trọng để theo dõi lượng năng lượng tiêu thụ hàng ngày.");
                break;
            case "Số phút thể dục":
                intent.putExtra("MO_TA", "Ứng dụng đo khoảng cách đi bộ không chỉ giúp bạn theo dõi quãng đường, mà còn tính toán số phút thể dục dựa trên tốc độ và thời gian hoạt động. Điều này giúp bạn quản lý thời gian tập luyện hiệu quả và theo dõi tiến trình sức khỏe của mình.");
                break;
            case "Số phút đứng":
                intent.putExtra("MO_TA", "Ứng dụng đo khoảng cách đi bộ không chỉ giúp bạn theo dõi quãng đường mà còn ghi lại số phút đứng trong ngày. Điều này giúp bạn theo dõi thói quen đứng nhiều hơn, hỗ trợ sự thoải mái và chăm sóc sức khỏe toàn diện.");
                break;
            case "Số phút di chuyển":
                intent.putExtra("MO_TA", "Ứng dụng theo dõi số phút di chuyển cho phép bạn ghi lại thời gian tổng cộng mà bạn đã dành cho hoạt động đi bộ trong ngày. Giúp bạn theo dõi và quản lý chính xác thời gian thực hiện hoạt động vận động, từ đó đảm bảo chế độ luyện tập và duy trì một lối sống sức khỏe.");
                break;
            case "Khoảng cách bơi":
                intent.putExtra("MO_TA", "Ứng dụng theo dõi khoảng cách bơi cung cấp thông tin chính xác về quãng đường bạn đã vượt qua trong các buổi tập. Bạn có thể dễ dàng theo dõi và đánh giá tiến trình của mình, từ đó tối ưu hóa chế độ tập luyện và cải thiện sức khỏe toàn diện.");
                break;
        }
    }

    private void addDonViDo(Intent intent, String item) {
        switch (item) {
            case "Quãng đường chạy bộ":
                intent.putExtra("DON_VI_DO", "km");
                break;
            case "Calories tiêu thụ":
                intent.putExtra("DON_VI_DO", "calo");
                break;
            case "Số phút thể dục":
            case "Số phút đứng":
            case "Số phút di chuyển":
                intent.putExtra("DON_VI_DO", "phút");
                break;
            case "Khoảng cách bơi":
                intent.putExtra("DON_VI_DO", "m");
                break;
        }
    }

    void addEvents() {

    }
}