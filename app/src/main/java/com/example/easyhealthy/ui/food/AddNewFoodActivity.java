package com.example.easyhealthy.ui.food;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.example.easyhealthy.databinding.ActivityAddNewFoodBinding;
import com.example.easyhealthy.databinding.ActivitySinhHieuBinding;
import com.example.easyhealthy.ui.dinhDuong.DinhDuongActivity;

public class AddNewFoodActivity extends AppCompatActivity {

    EditText edtTenChatDinhDuong;
    EditText edtMoTa;
    EditText edtTenDonviDo;

    Button btnThem;

    ActivityAddNewFoodBinding binding;
    Uri imageUri;

    private static final int PICK_IMAGE_REQUEST = 111;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNewFoodBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        try {
            addControls();
            addEvents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls() {

    }

    private void addEvents() {
        Toolbar toolbar = findViewById(R.id.tb_chiTietDinhDuong);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        binding.btnAddImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mở màn hình chọn ảnh từ thư viện
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        binding.btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intentBack = new Intent(AddNewFoodActivity.this, DinhDuongActivity.class);
                    intentBack.putExtra("TEN_THUC_AN", binding.edtTenSinhHieu.getText().toString());
                    intentBack.putExtra("MO_TA", binding.edtMoTa.getText().toString());
                    intentBack.putExtra("TEN_DON_VI", binding.edtTenDonViDo.getText().toString());

                    // Kiểm tra xem có ảnh được chọn không trước khi thêm vào intent
                    if (imageUri != null) {
                        intentBack.putExtra("IMAGE_URI", imageUri.toString());
                    }

                    setResult(102, intentBack);
                    finish();
                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.btnAddImage.setImageURI(imageUri);
        }
    }

}