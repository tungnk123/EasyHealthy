package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.easyhealthy.MainActivity;
import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivityCompleteProfileBinding;

public class CompleteProfileActivity extends AppCompatActivity {
    private ActivityCompleteProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCompleteProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String[] gendersWithHint = {"Chọn giới tính", "Nam", "Nữ", "Khác"};
        Spinner genderSpinner = binding.spnGender;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, gendersWithHint);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        genderSpinner.setAdapter(adapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    // Người dùng đã chọn gợi ý, xử lý theo yêu cầu của bạn (ví dụ: thông báo hoặc hành động khác)
                } else {
                    String selectedGender = gendersWithHint[position];
                    // Xử lý khi người dùng chọn giới tính khác
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Xử lý khi không có lựa chọn nào được chọn
            }
        });

        binding.btnFinish.setOnClickListener(v -> {
            Intent intent = new Intent(CompleteProfileActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
    }
}