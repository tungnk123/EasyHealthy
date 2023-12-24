package com.example.easyhealthy.ui.baibao;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.BaiBaoAdapter;
import com.example.easyhealthy.adapter.ListWithImageAdapter;
import com.example.easyhealthy.adapter.NguyCoAdapter;
import com.example.easyhealthy.databinding.FragmentBaibaoBinding;
import com.example.easyhealthy.databinding.FragmentDuyetBinding;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.dinhDuong.DinhDuongActivity;

import java.util.Objects;

public class BaiBaoFragment extends Fragment {

    private FragmentBaibaoBinding binding;
    RecyclerView rcvNguyCo;
    RecyclerView rcvBaiBao;

    NguyCoAdapter nguyCoAdapter;
    BaiBaoAdapter baiBaoAdapter;
    DuyetItem[] duyetItems;

    DuyetItem[] baiBaoList;

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

        duyetItems = new DuyetItem[] {
                new DuyetItem("Máu đang nhiễm mỡ", R.drawable.ic_heart_rate),
                new DuyetItem("Lượng nước không đủ", R.drawable.ic_water),
        };

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
                startActivity(intent);
            }
        });
        rcvBaiBao.setAdapter(baiBaoAdapter);
        rcvBaiBao.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void addEvents() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}