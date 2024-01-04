package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivityStartUpBinding;

public class StartUpActivity extends AppCompatActivity {
    private ActivityStartUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityStartUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //set up button sign up and log in
        binding.buttonSignup.setOnClickListener(v -> {
            Intent intent = new Intent(StartUpActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
        binding.buttonLogin.setOnClickListener(v -> {
            Intent intent = new Intent(StartUpActivity.this, LogInActivity.class);
            startActivity(intent);
        });

    }
}
