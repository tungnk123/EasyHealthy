package com.example.easyhealthy.ui.hoso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easyhealthy.databinding.FragmentHosoBinding;

public class HoSoFragment extends Fragment {

    private FragmentHosoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHosoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // hien thi dialog chinh sua ten khi click vao nut chinh sua
        binding.ibtnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hien thi dialog nhap ten moi
        hienThiDialogDoiTen();
            }
        });
        return root;
    }

    private void hienThiDialogDoiTen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Nhập tên mới");

        // Tạo một EditText trong dialog để người dùng nhập tên mới
        final EditText input = new EditText(requireContext());
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Thiết lập button cho dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String tenMoi = input.getText().toString();
                // Xử lý tên mới ở đây (ví dụ: cập nhật tên trên giao diện)
                capNhatTenMoi(tenMoi);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Hiển thị dialog
        builder.show();
    }

    // Phương thức để cập nhật tên mới (ví dụ)
    private void capNhatTenMoi(String tenMoi) {
        TextView tvTen = binding.tvTen;
        tvTen.setText(tenMoi);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}