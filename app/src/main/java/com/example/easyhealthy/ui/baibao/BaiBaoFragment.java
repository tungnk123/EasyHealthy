package com.example.easyhealthy.ui.baibao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.BaiBaoAdapter;
import com.example.easyhealthy.adapter.NguyCoAdapter;
import com.example.easyhealthy.databinding.FragmentBaibaoBinding;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.model.HoatDongData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class BaiBaoFragment extends Fragment {

    private FragmentBaibaoBinding binding;
    RecyclerView rcvNguyCo;
    RecyclerView rcvBaiBao;

    RecyclerView rcvBaiKiemTra;

    NguyCoAdapter nguyCoAdapter;
    BaiBaoAdapter baiBaoAdapter;

    BaiBaoAdapter baiKiemTraAdapter;
    List<HoatDongData> duyetItems;

    DuyetItem[] baiBaoList;
    DuyetItem[] baiKiemTraList;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BaiBaoViewModel dashboardViewModel =
                new ViewModelProvider(this).get(BaiBaoViewModel.class);

        binding = FragmentBaibaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        addControls();
        addEvents();
        return root;
    }

    private void addControls() {
        rcvNguyCo = binding.rcvNguyCo;
        rcvBaiBao = binding.rcvBaiBao;
        rcvBaiKiemTra = binding.rcvBaiKiemTra;
        duyetItems = new ArrayList<>();
        duyetItems.add(new HoatDongData("Đường huyết cao", R.drawable.ic_glucose, 200, "mmol/L"));
        duyetItems.add(new HoatDongData("Thiếu nước", R.drawable.ic_water, 1, "L"));
        baiBaoList = new DuyetItem[] {
                new DuyetItem("Những lưu ý khi bị nhiễm mỡ trong máu", R.drawable.ic_gan_nhiem_mo),
                new DuyetItem("Một số bất lợi khi sử dụng thuốc hạ mỡ máu", R.drawable.ic_thuoc_ha_mo_mau),
                new DuyetItem("Nấm hương tốt cho người mỡ máu", R.drawable.ic_nam_huong),
        };




        nguyCoAdapter = new NguyCoAdapter(duyetItems);
        rcvNguyCo.setAdapter(nguyCoAdapter);
        rcvNguyCo.setLayoutManager(new LinearLayoutManager(getContext()));

        baiBaoAdapter = new BaiBaoAdapter(baiBaoList);

        baiBaoAdapter.setOnItemClickListener(new BaiBaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Intent intent = new Intent(getContext(), ChiTietBaiBaoActivity.class);
                intent.putExtra("title", item.getTitle());
                startActivity(intent);
            }
        });
        rcvBaiBao.setAdapter(baiBaoAdapter);
        rcvBaiBao.setLayoutManager(new LinearLayoutManager(getContext()));


        checkBMI();

        baiKiemTraList = new DuyetItem[] {
                new DuyetItem("Bài kiểm tra stress", R.drawable.img_stress),
                new DuyetItem("Bài kiểm tra rối loạn ăn uống", R.drawable.img_roiloananuong),
                new DuyetItem("Bài kiểm tra độ nghiện", R.drawable.img_nghien),
        };
        baiKiemTraAdapter = new BaiBaoAdapter(baiKiemTraList);
        baiKiemTraAdapter.setOnItemClickListener(new BaiBaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Intent intent = new Intent(getContext(), ChiTietBaiBaoActivity.class);
                intent.putExtra("title", item.getTitle());
                startActivity(intent);
            }
        });
        rcvBaiKiemTra.setLayoutManager(new LinearLayoutManager(getContext()));
        rcvBaiKiemTra.setAdapter(baiKiemTraAdapter);

    }

    private void addEvents() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    void checkBMI() {
        AtomicInteger canNang = new AtomicInteger();
        AtomicInteger chieuCao = new AtomicInteger();
        firestore.collection("Chiều cao")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        HoatDongData data = new  HoatDongData(document);
                        canNang.set(data.getNumber());
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Cân nặng")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        HoatDongData data = new  HoatDongData(document);
                        chieuCao.set(data.getNumber());
                    }
                })
                .addOnFailureListener(e -> {
                });

        double bmi = calculateBMI(canNang.get(), chieuCao.get());
        if (bmi < 18.5) {
            String warn = "Thiếu cân";
            duyetItems.add(new HoatDongData(warn, R.drawable.ic_bmi, (int) bmi, "kg/m2"));
            nguyCoAdapter.notifyDataSetChanged();
        } else if (bmi >= 18.5 && bmi < 24.9) {

        } else if (bmi >= 25 && bmi < 29.9) {
            String warn = "Thừa cân";
            duyetItems.add(new HoatDongData(warn, R.drawable.ic_bmi, (int) bmi, "kg/m2"));
            nguyCoAdapter.notifyDataSetChanged();
        } else {
            String warn = "Béo phì";
            duyetItems.add(new HoatDongData(warn, R.drawable.ic_bmi, 35, "kg/m2"));
            nguyCoAdapter.notifyDataSetChanged();
        }
    }

    private double calculateBMI(int weight, int height) {
        double heightInMeter = height / 100.0;
        return weight / (heightInMeter * heightInMeter);

    }

}