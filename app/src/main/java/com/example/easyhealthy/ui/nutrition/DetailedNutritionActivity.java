package com.example.easyhealthy.ui.nutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.w3c.dom.Text;

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
    TextView tvHeading2;
    TextView tvTrungBinh;

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


        tvHeading2 = (TextView) findViewById(R.id.tv_detailedNutrition_heading2);
        tvTrungBinh = (TextView) findViewById(R.id.tv_detailedNutrition_trungBinh);
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
                Toast.makeText(getApplicationContext(), "button tuan click", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "button thang click", Toast.LENGTH_LONG).show();
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
                Toast.makeText(getApplicationContext(), "button nam click", Toast.LENGTH_LONG).show();
                updateUI("nam");
                updateDataForChart("nam");
            }
        });
    }

    private void updateDataForChart(String type) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        BarDataSet dataSet = new BarDataSet(entries, "Canxi");
        BarData barData = new BarData(dataSet);
        XAxis xAxis = barChart.getXAxis();
        switch (type) {
            case "ngay":

                entries.add(new BarEntry(1, 2));
                entries.add(new BarEntry(6, 3));
                entries.add(new BarEntry(10, 4));
                entries.add(new BarEntry(14, 5));
                entries.add(new BarEntry(18, 7));


                dataSet.setColors(ColorTemplate.rgb("71EA66"));

                barChart.setData(barData);
                barChart.setFitBars(true);
                xAxis = barChart.getXAxis();
                xAxis.setAxisMinimum(0);
                xAxis.setAxisMaximum(24);
                barChart.animateXY(2000, 2000);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setText("Lưu lượng canxi trong ngày");
                break;
            case "tuan":
                entries = new ArrayList<>();
                entries.add(new BarEntry(1, 2));
                entries.add(new BarEntry(2, 3));
                entries.add(new BarEntry(3, 4));
                entries.add(new BarEntry(6, 5));
                entries.add(new BarEntry(7, 7));

                dataSet = new BarDataSet(entries, "Canxi");
                dataSet.setColors(ColorTemplate.rgb("71EA66"));
                barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.setFitBars(true);

                xAxis.setAxisMinimum(0);
                xAxis.setAxisMaximum(7);
                barChart.animateXY(2000, 2000);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setText("Lưu lượng canxi trong tuần");
                break;
            case "thang":
                entries = new ArrayList<>();
                entries.add(new BarEntry(1, 2));
                entries.add(new BarEntry(8, 3));
                entries.add(new BarEntry(18, 4));
                entries.add(new BarEntry(25, 5));
                entries.add(new BarEntry(28, 7));

                dataSet = new BarDataSet(entries, "Canxi");
                dataSet.setColors(ColorTemplate.rgb("71EA66"));
                barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.setFitBars(true);

                xAxis.setAxisMinimum(0);
                xAxis.setAxisMaximum(31);
                barChart.animateXY(2000, 2000);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setText("Lưu lượng canxi trong tháng");
                break;
            case "nam":
                entries = new ArrayList<>();
                entries.add(new BarEntry(1, 2));
                entries.add(new BarEntry(3, 3));
                entries.add(new BarEntry(4, 4));
                entries.add(new BarEntry(6, 5));
                entries.add(new BarEntry(10, 7));

                dataSet = new BarDataSet(entries, "Canxi");
                dataSet.setColors(ColorTemplate.rgb("71EA66"));
                barData = new BarData(dataSet);
                barChart.setData(barData);
                barChart.setFitBars(true);

                xAxis.setAxisMinimum(0);
                xAxis.setAxisMaximum(12);
                barChart.animateXY(2000, 2000);
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getDescription().setText("Lưu lượng canxi trong năm");
                break;

        }
    }

    private void updateUI(String type) {
        if (type == "tuan" || type == "thang") {
            tvHeading2.setText("Trung bình mỗi ngày");
        }
        else if (type == "nam") {
            tvHeading2.setText("Trung bình");
        }
        else {
            tvHeading2.setText("Tổng");
        }
    }
}