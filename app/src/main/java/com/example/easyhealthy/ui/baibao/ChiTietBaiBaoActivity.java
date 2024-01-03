package com.example.easyhealthy.ui.baibao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.easyhealthy.R;

public class ChiTietBaiBaoActivity extends AppCompatActivity {

    TextView tvType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bai_bao);

        WebView webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        tvType = findViewById(R.id.tv_baiBao_type);
        Intent intent = getIntent();
        tvType.setText(intent.getStringExtra("title"));
        String type = tvType.getText().toString();
        switch (type) {
            case "Những lưu ý khi bị nhiễm mỡ trong máu":
                webView.loadUrl("https://tamanhhospital.vn/mau-nhiem-mo-co-nguy-hiem-khong/");
                break;
            case "Một số bất lợi khi sử dụng thuốc hạ mỡ máu":
                webView.loadUrl("https://www.vinmec.com/vi/thong-tin-duoc/su-dung-thuoc-toan/mot-so-tac-dung-khong-mong-muon-khi-dung-thuoc-giam-cholesterol-thuoc-giam-mo-mau/");
                break;
            case "Nấm hương tốt cho người mỡ máu":
                webView.loadUrl("https://thanhnien.vn/giam-mo-trong-mau-nho-nam-huong-185328663.htm");
                break;
            default:
                webView.loadUrl("https://tamanhhospital.vn/mau-nhiem-mo-co-nguy-hiem-khong/");
                break;

        }

        Toolbar toolbar = findViewById(R.id.tb_dinhDuong);
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
    }
}