package com.example.easyhealthy.ui.logIn_signUp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.easyhealthy.MainActivity;
import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivityCompleteProfileBinding;
import com.example.easyhealthy.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CompleteProfileActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
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
            // get user
            Intent intent1 = getIntent();
            User userModel = (User) intent1.getSerializableExtra("user");

            // them cac thuoc tinh cua user
            userModel.setHeight(Integer.parseInt(binding.etHeight.getText().toString()));
            userModel.setWeight(Integer.parseInt(binding.etWeight.getText().toString()));
            userModel.setGender(genderSpinner.getSelectedItem().toString());
            userModel.setDateOfBirth(binding.etDoB.getText().toString());
            userModel.setAge(calculateAge(binding.etDoB.getText().toString()));
            userModel.setBmi((double) userModel.getWeight() / (userModel.getHeight() * userModel.getHeight()) * 10000);

            // tao user tren firestore
            // tao document voi id la email
            // set tat ca cac thuoc tinh cua user vao document
            Map<String, Object> user = new HashMap<>();
            user.put("name", userModel.getName());
            user.put("email", userModel.getEmail());
            user.put("password", userModel.getPassword());
            user.put("height", userModel.getHeight());
            user.put("weight",userModel.getWeight());
            user.put("bmi",userModel.getBmi());
            user.put("gender",userModel.getGender());
            user.put("age",userModel.getAge());
            user.put("dateOfBirth",userModel.getDateOfBirth());

            db.collection("users").document(userModel.getEmail())
                    .set(user)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CompleteProfileActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

                            // set shared preferences
                            // luu email vao sharedPreferences
                            // de lay thong tin user bang email
                            getSharedPreferences("data", MODE_PRIVATE)
                                    .edit()
                                    .putString("email", userModel.getEmail())
                                    .apply();

                            Intent intent2 = new Intent(CompleteProfileActivity.this, MainActivity.class);
                            intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent2);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CompleteProfileActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                        }
                    });

        });
    }
    // tinh tuoi
    private int calculateAge(String dateOfBirth) {
        int age = 0;
        // tinh tuoi
        // lay ngay thang nam sinh
        String[] dateOfBirthArray = dateOfBirth.split("/");
        int dateOfBirthDay = Integer.parseInt(dateOfBirthArray[0]);
        int dateOfBirthMonth = Integer.parseInt(dateOfBirthArray[1]);
        int dateOfBirthYear = Integer.parseInt(dateOfBirthArray[2]);

        // lay ngay thang nam hien tai
        Calendar calendar = Calendar.getInstance();
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        int currentMonth = calendar.get(Calendar.MONTH) + 1;
        int currentYear = calendar.get(Calendar.YEAR);

        // tinh tuoi
        age = currentYear - dateOfBirthYear;
        if (currentMonth < dateOfBirthMonth) {
            age--;
        } else if (currentMonth == dateOfBirthMonth) {
            if (currentDay < dateOfBirthDay) {
                age--;
            }
        }
        return age;
    }
}