package com.example.easyhealthy.ui.hoso;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easyhealthy.MainActivity;
import com.example.easyhealthy.databinding.FragmentHosoBinding;
import com.example.easyhealthy.ui.logIn_signUp.LogInActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.CollectionReference;

public class HoSoFragment extends Fragment {
    SharedPreferences sharedPreferences;

    private FragmentHosoBinding binding;
    FirebaseFirestore db;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHosoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sharedPreferences = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("email", "lkt@gmail.com");

        // lay thong tin user tu database set vao textview
        db = FirebaseFirestore.getInstance();
        db.collection("users")
                .whereEqualTo("email", email)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Lấy thông tin người dùng từ kết quả truy vấn
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);

                        // Lấy dữ liệu từ DocumentSnapshot
                        String userName = documentSnapshot.getString("name");
                        int height = documentSnapshot.getLong("height").intValue();
                        int weight = documentSnapshot.getLong("weight").intValue();
                        Double bmi = documentSnapshot.getDouble("bmi");
                        String gender = documentSnapshot.getString("gender");
                        int age = documentSnapshot.getLong("age").intValue();
                        String dateOfBirth = documentSnapshot.getString("dateOfBirth");

                        // Đặt dữ liệu vào các TextView
                        binding.tvTen.setText(userName);
                        binding.tvChieuCaoIndex.setText(String.valueOf(height));
                        binding.tvCanNangIndex.setText(String.valueOf(weight));
                        String bmiFormatted = String.format("%.1f", bmi);
                        binding.tvBmiIndex.setText(bmiFormatted);
                        binding.tvGioiTinhValue.setText(gender);
                        binding.tvTuoiValue.setText(String.valueOf(age));
                        binding.tvNgaySinhValue.setText(dateOfBirth);
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(requireContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                });

        // hien thi dialog chinh sua ten khi click vao nut chinh sua
        binding.ibtnChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // hien thi dialog nhap ten moi
                hienThiDialogDoiTen();
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

    // cập nhật tên mới
    private void capNhatTenMoi(String tenMoi) {
        TextView tvTen = binding.tvTen;
        tvTen.setText(tenMoi);

        DocumentReference userRef = db.collection("users").document(sharedPreferences.getString("email", ""));

        userRef
                .update("name", tenMoi)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "Cập nhật tên thành công", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), "Cập nhật tên thất bại", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}