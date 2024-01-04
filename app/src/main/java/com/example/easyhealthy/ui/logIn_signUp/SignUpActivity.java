package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivitySignUpBinding;
import com.example.easyhealthy.model.User;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    binding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnSignup.setOnClickListener(v -> {
        User user = new User();
        user.setName(binding.etFullName.getText().toString());
        user.setEmail(binding.etEmail.getText().toString());
        user.setPassword(binding.etPassword.getText().toString());

        Intent intent = new Intent(SignUpActivity.this, CompleteProfileActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    });

    binding.tvLogin.setOnClickListener(v -> {
        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(intent);
    });
    }
}