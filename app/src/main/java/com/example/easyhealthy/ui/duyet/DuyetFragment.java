package com.example.easyhealthy.ui.duyet;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.MainActivity;
import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.ListAdapter;
import com.example.easyhealthy.databinding.FragmentDuyetBinding;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.dinhDuong.DinhDuongActivity;

import java.util.Objects;

public class DuyetFragment extends Fragment {

    private FragmentDuyetBinding binding;
    public RecyclerView rcvListItem;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DuyetViewModel duyetViewModel = new ViewModelProvider(this).get(DuyetViewModel.class);

        binding = FragmentDuyetBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        rcvListItem = binding.rcvDanhMucSucKhoe;
        DuyetItem[] dataSet = {
                new DuyetItem("Dinh dưỡng", R.drawable.ic_nitrition),
                new DuyetItem("Nước", R.drawable.ic_water),
                new DuyetItem("Sinh hiệu", R.drawable.ic_sinhhieu),
                new DuyetItem("Ngủ", R.drawable.ic_bed),
                new DuyetItem("Triệu chứng", R.drawable.ic_trieuchung),
                new DuyetItem("Hoạt động", R.drawable.ic_fire),
                new DuyetItem("Số đo cơ thể", R.drawable.ic_body),

        };
        ListAdapter listAdapter = new ListAdapter(dataSet);
        listAdapter.setOnItemClickListener(new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Toast.makeText(requireContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                if (Objects.equals(item.getTitle(), "Dinh dưỡng")) {
                    Intent intent = new Intent(getContext(), DinhDuongActivity.class);
                    startActivity(intent);
                }
            }
        });
        rcvListItem.setAdapter(listAdapter);
        rcvListItem.setLayoutManager(new LinearLayoutManager(requireContext()));

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
