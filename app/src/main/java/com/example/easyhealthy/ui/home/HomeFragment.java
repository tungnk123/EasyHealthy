package com.example.easyhealthy.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easyhealthy.R;
import com.example.easyhealthy.adapter.BaiBaoAdapter;
import com.example.easyhealthy.adapter.HistoryListAdapter;
import com.example.easyhealthy.adapter.ListWithImageAdapter;
import com.example.easyhealthy.adapter.NguyCoAdapter;
import com.example.easyhealthy.databinding.FragmentHomeBinding;
import com.example.easyhealthy.model.DuyetItem;
import com.example.easyhealthy.ui.baibao.ChiTietBaiBaoActivity;

public class HomeFragment extends Fragment implements SensorEventListener {

    private FragmentHomeBinding binding;
    private SensorManager sensorManager;
    private double MagnitudePrevious = 0;
    private int stepCount = 0;

    BaiBaoAdapter baiBaoAdapter;

    DuyetItem[] baiBaoList;

    RecyclerView rcvDinhDuong;
    HistoryListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sensorManager = (SensorManager) requireContext().getSystemService(Context.SENSOR_SERVICE);


        baiBaoList = new DuyetItem[] {
                new DuyetItem("Những lưu ý khi bị nhiễm mỡ trong máu", R.drawable.ic_gan_nhiem_mo),
                new DuyetItem("Một số bất lợi khi sử dụng thuốc hạ mỡ máu", R.drawable.ic_thuoc_ha_mo_mau),
                new DuyetItem("Nấm hương tốt cho người mỡ máu", R.drawable.ic_nam_huong),
        };

        baiBaoAdapter = new BaiBaoAdapter(baiBaoList);
        baiBaoAdapter.setOnItemClickListener(new BaiBaoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DuyetItem item) {
                Intent intent = new Intent(getContext(), ChiTietBaiBaoActivity.class);
                startActivity(intent);
            }
        });
        binding.rcvBaiBao.setAdapter(baiBaoAdapter);
        binding.rcvBaiBao.setLayoutManager(new LinearLayoutManager(getContext()));


        DuyetItem[] dataSet = {
                new DuyetItem("Chất đạm", R.drawable.ic_nitrition),
                new DuyetItem("Chất xơ", R.drawable.ic_nitrition),
                new DuyetItem("Chất béo", R.drawable.ic_nitrition),


        };
        rcvDinhDuong = binding.rcvMucYeuThich;
        adapter = new HistoryListAdapter(dataSet);
        rcvDinhDuong.setAdapter(adapter);
        rcvDinhDuong.setLayoutManager(new LinearLayoutManager(getContext()));


        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        stepCount = sharedPreferences.getInt("stepCount", 0);
        binding.tvSoBuocChan.setText(stepCount + " bước");
        binding.pbSoBuocChan.setProgressWithAnimation(stepCount);

        Sensor stepSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (stepSensor == null) {
            Toast.makeText(getContext(), "No step counter sensor detected on this device", Toast.LENGTH_LONG).show();
        } else {
            MagnitudePrevious = 0.0;
            sensorManager.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        SharedPreferences sharedPreferences = requireActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("stepCount", stepCount);
        editor.apply();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent != null) {
            float x_acceleration = sensorEvent.values[0];
            float y_acceleration = sensorEvent.values[1];
            float z_acceleration = sensorEvent.values[2];

            double Magnitude = Math.sqrt(x_acceleration * x_acceleration + y_acceleration * y_acceleration + z_acceleration * z_acceleration);
            double MagnitudeDelta = Magnitude - MagnitudePrevious;
            MagnitudePrevious = Magnitude;
            double threshold = 15;
            if (MagnitudeDelta > threshold) {
                stepCount++;
                binding.tvSoBuocChan.setText(stepCount + " bước");
                binding.pbSoBuocChan.setProgressWithAnimation(stepCount);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Handle accuracy changes if needed
    }
}
