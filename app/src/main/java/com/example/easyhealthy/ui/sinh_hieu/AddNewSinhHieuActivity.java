package com.example.easyhealthy.ui.sinh_hieu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.easyhealthy.R;
import com.example.easyhealthy.ui.dinhDuong.DinhDuongActivity;

public class AddNewSinhHieuActivity extends AppCompatActivity {

    EditText edtTenHinhHieu;
    EditText edtMoTa;
    EditText edtTenDonviDo;

    Button btnThem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_sinh_hieu);

        addControls();
        addEvents();
    }

    private void addControls() {
        edtTenHinhHieu = (EditText) findViewById(R.id.edt_tenSinhHieu);
        edtMoTa = (EditText) findViewById(R.id.edt_moTa);
        edtTenDonviDo = (EditText) findViewById(R.id.edt_tenDonViDo);
        btnThem = (Button) findViewById(R.id.btn_addSinhHieu);
    }

    private void addEvents() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentBack = new Intent(getApplicationContext(), SinhHieuActivity.class);
                intentBack.putExtra("TEN_SINH_HIEU", edtTenHinhHieu.getText().toString());
                intentBack.putExtra("MO_TA", edtMoTa.getText().toString());
                intentBack.putExtra("TEN_DON_VI", edtTenDonviDo.getText().toString());
                setResult(101, intentBack);
                finish();
            }
        });
    }


}