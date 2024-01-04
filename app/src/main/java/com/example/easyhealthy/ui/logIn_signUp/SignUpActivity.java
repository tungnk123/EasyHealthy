package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    binding = ActivitySignUpBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());

    binding.btnSignup.setOnClickListener(v -> {
        Intent intent = new Intent(SignUpActivity.this, CompleteProfileActivity.class);
        startActivity(intent);
    });

    binding.tvLogin.setOnClickListener(v -> {
        Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(intent);
    });
    }
}