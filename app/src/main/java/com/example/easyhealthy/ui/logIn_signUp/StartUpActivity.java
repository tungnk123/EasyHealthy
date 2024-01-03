package com.example.easyhealthy.ui.logIn_signUp;

import androidx.appcompat.app.AppCompatActivity;

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
//        binding.btnSignUp.setOnClickListener(v -> {
//            setContentView(R.layout.activity_sign_up);
//        });
        binding.buttonLogin.setOnClickListener(v -> {
            setContentView(R.layout.activity_log_in);
        });

    }
}
