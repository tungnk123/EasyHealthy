package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.easyhealthy.MainActivity;
import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivityLogInBinding;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LogInActivity extends AppCompatActivity {
    private ActivityLogInBinding binding;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnLogin.setOnClickListener(v -> {
            // Xử lý đăng nhập
            DocumentReference userRef = db.collection("users").document(binding.etEmail.getText().toString());
            userRef.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        // Tài khoản tồn tại, kiểm tra mật khẩu
                        String storedPassword = document.getString("password"); // Lấy mật khẩu đã lưu
                        // Xử lý xác minh mật khẩu ở đây (so sánh storedPassword với password)
                        if (storedPassword.equals(binding.etPassword.getText().toString())) {
                            // Mật khẩu đúng, đăng nhập thành công
                            Toast.makeText(LogInActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

                            // set email vao shared preferences
                             SharedPreferences.Editor editor = getSharedPreferences("data", MODE_PRIVATE).edit();
                             editor.putString("email", binding.etEmail.getText().toString());
                             editor.apply();

                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();
                        } else {
                            // Mật khẩu sai, đăng nhập thất bại
                            Toast.makeText(LogInActivity.this, "Mật khẩu không đúng", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LogInActivity.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LogInActivity.this, "Lỗi kết nối, vui lòng thử lại sau", Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.tvSignup.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }
}