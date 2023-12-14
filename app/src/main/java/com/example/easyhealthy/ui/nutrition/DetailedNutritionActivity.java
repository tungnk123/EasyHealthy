package com.example.easyhealthy.ui.nutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DetailedNutritionActivity extends AppCompatActivity {

    Button btnThemDuLieu;
    Button btnNgay;
    Button btnTuan;
    Button btnThang;
    Button btnNam;
    BarChart barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_nutrition);
        addControls();
        addEvents();
    }



    private void addControls() {
        btnNgay = (Button) findViewById(R.id.btn_ngay);
        btnTuan = (Button) findViewById(R.id.btn_tuan);
        btnThang = (Button) findViewById(R.id.btn_thang);
        btnNam = (Button) findViewById(R.id.btn_nam);
        btnThemDuLieu = (Button) findViewById(R.id.btn_themDuLieu);
        barChart = findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(1, 2));
        entries.add(new BarEntry(6, 3));
        entries.add(new BarEntry(10, 4));
        entries.add(new BarEntry(14, 5));
        entries.add(new BarEntry(18, 7));

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
    }

    private void addEvents() {
        btnThemDuLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNutritionDataActivity.class));
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
                Toast.makeText(getApplicationContext(), "button ngay click", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "button tuan click", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "button thang click", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "button nam click", Toast.LENGTH_LONG).show();
            }
        });
    }
}