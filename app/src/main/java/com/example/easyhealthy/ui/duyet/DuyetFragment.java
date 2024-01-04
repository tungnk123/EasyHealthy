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

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.ListWithImageAdapter;
import com.example.easyhealthy.databinding.FragmentDuyetBinding;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.chi_so_co_the.ChiSoCoTheActivity;
import com.example.easyhealthy.ui.dinhDuong.DinhDuongActivity;
import com.example.easyhealthy.ui.giac_ngu.GiacNguActivity;
import com.example.easyhealthy.ui.hoatdong.HoatDongActivity;
import com.example.easyhealthy.ui.luong_nuoc.LuongNuocActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;
import com.example.easyhealthy.ui.sinh_hieu.SinhHieuActivity;

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
                new DuyetItem("Lượng nước", R.drawable.ic_water),
                new DuyetItem("Sinh hiệu", R.drawable.ic_sinhhieu),
                new DuyetItem("Ngủ", R.drawable.ic_bed),
                new DuyetItem("Hoạt động", R.drawable.ic_fire),
                new DuyetItem("Số đo cơ thể", R.drawable.ic_body),

        };
        ListWithImageAdapter listAdapter = new ListWithImageAdapter(dataSet);
        listAdapter.setOnItemClickListener(new ListWithImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Toast.makeText(requireContext(), "Clicked: " + item.getTitle(), Toast.LENGTH_SHORT).show();
                if (Objects.equals(item.getTitle(), "Dinh dưỡng")) {
                    Intent intent = new Intent(getContext(), DinhDuongActivity.class);
                    startActivity(intent);
                }
                else if (Objects.equals(item.getTitle(), "Hoạt động")) {
                    Intent intent = new Intent(getContext(), HoatDongActivity.class);
                    startActivity(intent);
                }
                else if (Objects.equals(item.getTitle(), "Sinh hiệu")) {
                    Intent intent = new Intent(getContext(), SinhHieuActivity.class);
                    startActivity(intent);
                }
                // TODO: Ae nao lam man hinh nao thi them o day
                else if (Objects.equals(item.getTitle(), "Số đo cơ thể")) {
                    Intent intent = new Intent(getContext(), ChiSoCoTheActivity.class);
                    startActivity(intent);
                }
                else if (Objects.equals(item.getTitle(), "Lượng nước")) {
                    Intent intent = new Intent(getContext(), LuongNuocActivity.class);
                    intent.putExtra("title", "Lượng nước");
                    startActivity(intent);
                }
                else if (Objects.equals(item.getTitle(), "Ngủ")) {
                    Intent intent = new Intent(getContext(), GiacNguActivity.class);
                    intent.putExtra("title", "Giấc ngủ");
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
