package com.example.easyhealthy.ui.food;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.FoodNutritionAdapter;
import com.example.easyhealthy.model.NutritionData;
import com.example.easyhealthy.ui.nutrition.AddNutritionDataActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DetailedFoodActivity extends AppCompatActivity {

    TextView tvHeading;
    TextView tvDonViDo;

    Button btnThemDuLieu;
    Button btnNgay;
    Button btnTuan;
    Button btnThang;
    Button btnNam;
    BarChart barChart;
    TextView tvHeading2;
    TextView tvTrungBinh;

    TextView tvHeading1;

    RecyclerView rcvChiTietDinhDuong;

    ImageView imgGioiThieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_food);

        addControls();
        addEvents();

        btnNgay.callOnClick();
    }

    private void addControls() {
        tvHeading = (TextView) findViewById(R.id.tv_detailedFood_heading);
        tvDonViDo = (TextView) findViewById(R.id.tv_donViDo);



        btnNgay = (Button) findViewById(R.id.btn_ngay);
        btnTuan = (Button) findViewById(R.id.btn_tuan);
        btnThang = (Button) findViewById(R.id.btn_thang);
        btnNam = (Button) findViewById(R.id.btn_nam);
        btnThemDuLieu = (Button) findViewById(R.id.btn_themDuLieu);
        barChart = findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
//        entries.add(new BarEntry(1, 2));
//        entries.add(new BarEntry(6, 3));
//        entries.add(new BarEntry(10, 4));
//        entries.add(new BarEntry(14, 5));
//        entries.add(new BarEntry(18, 7));

        BarDataSet dataSet = new BarDataSet(entries, "Canxi");
        dataSet.setColors(ColorTemplate.rgb("71EA66"));
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(24);
        barChart.animateXY(2000, 2000);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getDescription().setText("Lưu lượng canxi trong ngày");


        tvHeading2 = (TextView) findViewById(R.id.tv_detailedFood_heading2);
        tvTrungBinh = (TextView) findViewById(R.id.tv_detailedFood_trungBinh);
        tvHeading1 = (TextView) findViewById(R.id.tv_detailedFood_heading);
        imgGioiThieu = findViewById(R.id.img_gioiThieu);
        Intent intent = getIntent();
        tvHeading1.setText(intent.getStringExtra("title"));
        List<Pair<String, Integer>> nutritionList = null;

        // hard code
        if (tvHeading1.getText().toString().equals("Bánh mì")) {
            tvDonViDo.setText("ổ");
            imgGioiThieu.setImageResource(R.drawable.img_banhmi);
            nutritionList = Arrays.asList(new Pair<String, Integer>("Canxi", 20), new Pair<String, Integer>("Vitamin A", 30));
        }
        else if (tvHeading1.getText().toString().equals("Phở")) {
            tvDonViDo.setText("tô");
            imgGioiThieu.setImageResource(R.drawable.img_pho);
            nutritionList = Arrays.asList(new Pair<String, Integer>("Canxi", 50), new Pair<String, Integer>("Vitamin A", 80), new Pair<String, Integer>("Chất đạm", 100), new Pair<String, Integer>("Chất béo", 80));

        }
        else if (tvHeading1.getText().toString().equals("Cơm sườn")) {
            tvDonViDo.setText("bát");
            imgGioiThieu.setImageResource(R.drawable.img_comsuon);
            nutritionList = Arrays.asList(new Pair<String, Integer>("Chất xơ", 20), new Pair<String, Integer>("Vitamin A", 30), new Pair<String, Integer>("Chất đạm", 80));


        }
        rcvChiTietDinhDuong = (RecyclerView) findViewById(R.id.rcv_chiTietDinhDuongRcv);
        FoodNutritionAdapter foodNutritionAdapter = new FoodNutritionAdapter(nutritionList);
        rcvChiTietDinhDuong.setAdapter(foodNutritionAdapter);
        rcvChiTietDinhDuong.setLayoutManager(new LinearLayoutManager(this));
    }

    private void addEvents() {
        Toolbar toolbar = findViewById(R.id.tb_chiTietDinhDuong);
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
        btnThemDuLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFoodActivity.class);
                intent.putExtra("title", tvHeading1.getText().toString());
                startActivity(intent);
            }
        });
        btnNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableSelected = getResources().getDrawable(R.drawable.background_rounded_selected_button, getTheme());
                btnNgay.setBackground(drawableSelected);
                Drawable drawableNotSelected = getResources().getDrawable(R.drawable.background_rounded_transparent, getTheme());
                btnTuan.setBackground(drawableNotSelected);
                btnThang.setBackground(drawableNotSelected);
                btnNam.setBackground(drawableNotSelected);
                updateUI("ngay");
                updateDataForChart("ngay");
            }
        });

        btnTuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableSelected = getResources().getDrawable(R.drawable.background_rounded_selected_button, getTheme());
                btnTuan.setBackground(drawableSelected);
                Drawable drawableNotSelected = getResources().getDrawable(R.drawable.background_rounded_transparent, getTheme());
                btnNgay.setBackground(drawableNotSelected);
                btnThang.setBackground(drawableNotSelected);
                btnNam.setBackground(drawableNotSelected);
                updateUI("tuan");
                updateDataForChart("tuan");
            }
        });

        btnThang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableSelected = getResources().getDrawable(R.drawable.background_rounded_selected_button, getTheme());
                btnThang.setBackground(drawableSelected);
                Drawable drawableNotSelected = getResources().getDrawable(R.drawable.background_rounded_transparent, getTheme());
                btnNgay.setBackground(drawableNotSelected);
                btnTuan.setBackground(drawableNotSelected);
                btnNam.setBackground(drawableNotSelected);
                updateUI("thang");
                updateDataForChart("thang");
            }
        });

        btnNam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawableSelected = getResources().getDrawable(R.drawable.background_rounded_selected_button, getTheme());
                btnNam.setBackground(drawableSelected);
                Drawable drawableNotSelected = getResources().getDrawable(R.drawable.background_rounded_transparent, getTheme());
                btnNgay.setBackground(drawableNotSelected);
                btnThang.setBackground(drawableNotSelected);
                btnTuan.setBackground(drawableNotSelected);
                updateUI("nam");
                updateDataForChart("nam");
            }
        });
    }
    private void updateDataForChart(String type) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        String collectionPath = tvHeading1.getText().toString();
        if (collectionPath.isEmpty()) {
            return;
        }
        CollectionReference collectionReference = firestore.collection(collectionPath);
        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, tvHeading1.getText().toString());
        BarData barData = new BarData(dataSet);
        XAxis xAxis = barChart.getXAxis();
        switch (type) {
            case "ngay":
                // Get the current time
                Calendar calendar = Calendar.getInstance();
                long currentTime = calendar.getTimeInMillis();

                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                long startOfDayTimestamp = calendar.getTimeInMillis();
                collectionReference
                        .whereGreaterThanOrEqualTo("date", new Date(startOfDayTimestamp))
                        .whereLessThanOrEqualTo("date", new Date(currentTime))
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            // Handle the retrieved documents
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                NutritionData nutritionData = new NutritionData(documentSnapshot);
                                entries.add(new BarEntry(Integer.parseInt(nutritionData.getTime().substring(0, 2)), nutritionData.getQuantity()));
                            }
                            updateChart(entries, xAxis, "Số lượng "+ tvHeading1.getText().toString() + " trong ngày", "ngay");
                        })
                        .addOnFailureListener(e -> Log.e("1", "Error fetching tuan data: " + e.getMessage()));
                break;

            case "tuan":
                calendar = Calendar.getInstance();
                currentTime = calendar.getTimeInMillis();
                calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());
                long startOfWeekTimestamp = calendar.getTimeInMillis();

                collectionReference
                        .whereGreaterThanOrEqualTo("date", new Date(startOfWeekTimestamp))
                        .whereLessThanOrEqualTo("date", new Date(currentTime))
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            // Handle the retrieved documents
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                NutritionData nutritionData = new NutritionData(documentSnapshot);
                                entries.add(new BarEntry(nutritionData.getDate().getDay(), nutritionData.getQuantity()));
                            }
                            updateChart(entries, xAxis, "Số lượng "+ tvHeading1.getText().toString() + " trong tuần", "tuan");
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "Error fetching data: " + e.getMessage()));
                break;
            case "thang":

                Calendar calendarThang = Calendar.getInstance();
                long currentTimeThang = calendarThang.getTimeInMillis();

                calendarThang.set(Calendar.DAY_OF_MONTH, 1);
                long startOfMonthTimestamp = calendarThang.getTimeInMillis();

                collectionReference
                        .whereGreaterThanOrEqualTo("date", new Date(startOfMonthTimestamp))
                        .whereLessThanOrEqualTo("date", new Date(currentTimeThang))
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            // Handle the retrieved documents
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                NutritionData nutritionData = new NutritionData(documentSnapshot);
                                entries.add(new BarEntry(nutritionData.getDate().getDate(), nutritionData.getQuantity()));
                            }
                            updateChart(entries, xAxis, "Số lượng "+ tvHeading1.getText().toString() + " trong tháng", "thang");
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "Error fetching thang data: " + e.getMessage()));
                break;

            case "nam":
                // Get the current time
                Calendar calendarNam = Calendar.getInstance();
                long currentTimeNam = calendarNam.getTimeInMillis();

                calendarNam.set(Calendar.MONTH, Calendar.JANUARY);
                calendarNam.set(Calendar.DAY_OF_MONTH, 1);
                long startOfYearTimestamp = calendarNam.getTimeInMillis();

                collectionReference
                        .whereGreaterThanOrEqualTo("date", new Date(startOfYearTimestamp))
                        .whereLessThanOrEqualTo("date", new Date(currentTimeNam))
                        .get()
                        .addOnSuccessListener(queryDocumentSnapshots -> {
                            // Handle the retrieved documents
                            for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                                NutritionData nutritionData = new NutritionData(documentSnapshot);
                                entries.add(new BarEntry(nutritionData.getDate().getMonth() + 1, nutritionData.getQuantity()));
                            }
                            updateChart(entries, xAxis, "Số lượng "+ tvHeading1.getText().toString() + " trong năm", "nam");
                        })
                        .addOnFailureListener(e -> Log.e(TAG, "Error fetching nam data: " + e.getMessage()));
                break;
        }
    }
    private void updateChart(ArrayList<BarEntry> entries, XAxis xAxis, String description, String type) {
        BarDataSet dataSet = new BarDataSet(entries, tvHeading1.getText().toString());
        dataSet.setColors(ColorTemplate.rgb("71EA66"));
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
        barChart.setFitBars(true);

        xAxis.setAxisMinimum(0);
        switch (type) {
            case "ngay":
                xAxis.setAxisMaximum(24);
                break;
            case "tuan":
                xAxis.setAxisMaximum(7);
                break;
            case "thang":
                xAxis.setAxisMaximum(31);
                break;
            case "nam":
                xAxis.setAxisMaximum(12);
                break;
        }
        barChart.animateXY(2000, 2000);
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getDescription().setText(description);

        updateTrungBinh(entries, type);
    }

    private void updateTrungBinh(ArrayList<BarEntry> entries, String type) {
        int sum = 0;
        for (int i = 0; i < entries.size(); i++) {
            sum += entries.get(i).getY();
        }
        switch (type) {
            case "ngay":
                tvTrungBinh.setText(String.valueOf(sum));
                break;
            case "tuan":
                tvTrungBinh.setText(String.valueOf(sum / 7));
                break;
            case "thang":
                tvTrungBinh.setText(String.valueOf(sum / 30));
                break;
            case "nam":
                tvTrungBinh.setText(String.valueOf(sum / 12));
                break;
        }

    }
    private void updateUI(String type) {
        if (type == "tuan" || type == "thang") {
            tvHeading2.setText("Trung bình mỗi ngày");
        }
        else if (type == "nam") {
            tvHeading2.setText("Trung bình mỗi tháng");
        }
        else {
            tvHeading2.setText("Tổng");
        }
    }
}