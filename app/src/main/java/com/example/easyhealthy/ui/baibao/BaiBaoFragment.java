package com.example.easyhealthy.ui.baibao;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.easyhealthy.databinding.FragmentBaibaoBinding;
import com.example.easyhealthy.databinding.FragmentDuyetBinding;

public class BaiBaoFragment extends Fragment {

    private FragmentBaibaoBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BaiBaoViewModel dashboardViewModel =
                new ViewModelProvider(this).get(BaiBaoViewModel.class);

        binding = FragmentBaibaoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}