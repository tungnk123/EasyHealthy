package com.example.easyhealthy.ui.nutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyhealthy.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import java.util.ArrayList;

public class DetailedNutritionActivity extends AppCompatActivity {

    Button btnThemDuLieu;
    BarChart barChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_nutrition);
        addControls();
        addEvents();
    }



    private void addControls() {

        btnThemDuLieu = (Button) findViewById(R.id.btn_themDuLieu);
        barChart = findViewById(R.id.chart);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0, 45f));
        entries.add(new BarEntry(0, 65f));
        entries.add(new BarEntry(0, 75f));
        entries.add(new BarEntry(0, 85f));
        entries.add(new BarEntry(0, 105f));

        BarDataSet dataSet = new BarDataSet(entries, "Subjects");
        dataSet.setColor(R.color.purple_500);
        BarData barData = new BarData(dataSet);
        barChart.setData(barData);
    }

    private void addEvents() {
        btnThemDuLieu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddNutritionDataActivity.class));
            }
        });
    }
}