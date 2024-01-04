package com.example.easyhealthy.ui.hoso;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.example.easyhealthy.ui.logIn_signUp.LogInActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

public class HoSoFragment extends Fragment {

    private FragmentHosoBinding binding;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHosoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        final CollectionReference users = db.collection("users");

        // hien thi dialog chinh sua ten khi click vao nut chinh sua
        binding.ibtnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hien thi dialog nhap ten moi
                hienThiDialogDoiTen(users);
            }
        });

        binding.tvDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hien thi dialog xac nhan dang xuat
                hienThiDialogDangXuat();
            }
        });



        return root;
    }

    // Phương thức để hiển thị dialog xác nhận đăng xuất
    private void hienThiDialogDangXuat() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Xác nhận đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");

        // Thiết lập button cho dialog
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý đăng xuất ở đây (ví dụ: chuyển về màn hình đăng nhập)
                Intent intent = new Intent(requireActivity(), LogInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                requireActivity().finish();
            }
        });
        builder.setNegativeButton("Cancel", null);

        // Hiển thị dialog
        builder.show();
    }

    private void hienThiDialogDoiTen(CollectionReference users) {
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
                capNhatTenMoi(tenMoi, users);
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
    private void capNhatTenMoi(String tenMoi, CollectionReference users) {
        TextView tvTen = binding.tvTen;
        tvTen.setText(tenMoi);

        // Cập nhật tên mới lên database

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}