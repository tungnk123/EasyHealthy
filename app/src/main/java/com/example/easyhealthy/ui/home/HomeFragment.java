package com.example.easyhealthy.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
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
import com.example.easyhealthy.model.NutritionData;
import com.example.easyhealthy.ui.baibao.ChiTietBaiBaoActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment implements SensorEventListener {

    private FragmentHomeBinding binding;
    private SensorManager sensorManager;
    private double MagnitudePrevious = 0;
    private int stepCount = 0;

    BaiBaoAdapter baiBaoAdapter;

    DuyetItem[] baiBaoList;

    RecyclerView rcvDinhDuong;
    HistoryListAdapter adapter;
    NutritionData[] dataSet;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

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


         dataSet = new NutritionData[]{
                 new NutritionData("Chất đạm", Calendar.getInstance().getTime(), "20:01", 200),
                 new NutritionData("Chất xơ", Calendar.getInstance().getTime(), "20:01", 100),
                 new NutritionData("Chất béo", Calendar.getInstance().getTime(), "20:01", 300),

         };
        rcvDinhDuong = binding.rcvMucYeuThich;
        adapter = new HistoryListAdapter(dataSet);
        adapter.setOnItemClickListener(new HistoryListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(NutritionData item) {
                Intent intent = new Intent(getContext(), DetailedNutritionActivity.class);
                intent.putExtra("title", item.getType());
                startActivity(intent);
            }
        });
        rcvDinhDuong.setAdapter(adapter);
        rcvDinhDuong.setLayoutManager(new LinearLayoutManager(getContext()));

        updateDataInCardView();
        updateDataInRcv();
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
        updateChiSoBuocChan();
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

    public void updateChiSoBuocChan() {
        binding.tvCaloBuocChan.setText(String.valueOf((int) (stepCount * 0.05)));
        binding.tvKmBuocChan.setText(String.valueOf((int) (stepCount * 0.0006)));
        binding.tvPhutBuocChan.setText(String.valueOf((int) (stepCount * 0.01)));
    }

    public void updateDataInCardView() {
        firestore.collection("Cân nặng")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvCanNangValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvCanNangValue.setText("--");
                        }
                    }

                    // Sau khi lấy được dữ liệu mới nhất, bạn có thể cập nhật CardView ở đây
                })
                .addOnFailureListener(e -> {
                });

        firestore.collection("Huyết áp")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvHuyetApValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvHuyetApValue.setText("--");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Đường huyết")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvDuongHuyetValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvDuongHuyetValue.setText("--");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Nhịp tim")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvNhipTimValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvNhipTimValue.setText("--");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Thời lượng ngủ")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvSleepValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvSleepValue.setText("--");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Calories tiêu thụ")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        if (document.getDouble("quantity") > 0) {
                            binding.tvCaloValue.setText(String.valueOf(document.getDouble("quantity").intValue()));
                        }
                        else {
                            binding.tvCaloValue.setText("--");
                        }
                    }
                })
                .addOnFailureListener(e -> {
                });



    }

    public void updateDataInRcv() {
        firestore.collection("Chất đạm")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[0] = new NutritionData(document);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Chất xơ")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[1] = new NutritionData(document);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });
        firestore.collection("Chất béo")
                .orderBy("date", Query.Direction.DESCENDING)
                .limit(1)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        dataSet[2] = new NutritionData(document);
                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(e -> {
                });

    }
}
