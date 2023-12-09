package com.example.easyhealthy.ui.nutrition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.easyhealthy.R;

public class DetailedNutritionActivity extends AppCompatActivity {

    Button btnThemDuLieu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_nutrition);
        addControls();
        addEvents();
    }



    private void addControls() {
        btnThemDuLieu = (Button) findViewById(R.id.btn_themDuLieu);
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