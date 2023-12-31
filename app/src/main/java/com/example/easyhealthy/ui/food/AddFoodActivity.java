package com.example.easyhealthy.ui.food;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easyhealthy.R;
import com.example.easyhealthy.model.NutritionData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddFoodActivity extends AppCompatActivity {

    TextView tvNgayDatePikcer;
    TextView tvGioTimePicker;
    TextView tvHeading;
    Button btnAdd;
    EditText edtSoLuong;

    TextView tvAddFoodData;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);
        addControls();
        addEvents();
    }

    private void addControls() {
        tvNgayDatePikcer = findViewById(R.id.tv_addFoodData_ngayTimePicker);
        tvGioTimePicker = findViewById(R.id.tv_addFoodData_gioTimePicker);

        // Set current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", hour, minute);
        tvGioTimePicker.setText(currentTime);

        // Set current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        tvNgayDatePikcer.setText(formattedDate);

        btnAdd = (Button) findViewById(R.id.btn_addSinhHieu);
        edtSoLuong = (EditText) findViewById(R.id.edt_soLuong);
        tvHeading = (TextView) findViewById(R.id.tv_detailedFood_heading);

        Intent intent = getIntent();
        tvHeading.setText(intent.getStringExtra("title"));
        tvAddFoodData = (TextView) findViewById(R.id.tv_donViTinh);
        if (tvHeading.getText().toString().equals("Bánh mì")) {
            tvAddFoodData.setText("ổ");
        }
        else if (tvHeading.getText().toString().equals("Phở")) {
            tvAddFoodData.setText("tô");
        }
        else if (tvHeading.getText().toString().equals("Cơm sườn")) {
            tvAddFoodData.setText("bát");
        }
    }

    private void addEvents() {
        Toolbar toolbar = findViewById(R.id.tb_chiTietDinhDuong);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("");
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        tvNgayDatePikcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tvGioTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = tvHeading.getText().toString();
                String date = tvNgayDatePikcer.getText().toString();
                String time = tvGioTimePicker.getText().toString();
                int quantity = Integer.parseInt(edtSoLuong.getText().toString());

                // Parse the date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date parsedDate = null;

                try {
                    parsedDate = dateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // Format the date to create the Firestore document ID


                NutritionData nutritionData = new NutritionData(type, parsedDate, time, quantity);

                firestore.collection(type)
                        .add(nutritionData)
                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(getApplicationContext(), "Save canxi successfully", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(), "Save canxi failed", Toast.LENGTH_LONG).show();
                            }
                        });

                startActivity(new Intent(getApplicationContext(), DetailedFoodActivity.class));
            }
        });
    }
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(selectedYear, monthOfYear, dayOfMonth);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String formattedDate = dateFormat.format(selectedDate.getTime());
                tvNgayDatePikcer.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                tvGioTimePicker.setText(selectedTime);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

}