package com.example.easyhealthy.ui.nutrition;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class AddNutritionDataActivity extends AppCompatActivity {

    TextView tvNgayDatePikcer;
    TextView tvGioTimePicker;
    TextView tvHeading;
    Button btnAdd;
    EditText edtLuuLuong;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_nutrition_data);
        try {
            addControls();
            addEvents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
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
        edtLuuLuong = (EditText) findViewById(R.id.edt_soLuong);
        tvHeading = (TextView) findViewById(R.id.tv_detailedFood_heading);

        Intent intent = getIntent();
        tvHeading.setText(intent.getStringExtra("title"));
    }

    private void addEvents() {
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
                try {
                    String type = tvHeading.getText().toString();
                    String date = tvNgayDatePikcer.getText().toString();
                    String time = tvGioTimePicker.getText().toString();
                    int quantity = Integer.parseInt(edtLuuLuong.getText().toString());

                    // Check if type is not empty or null
                    if (type.isEmpty()) {
                        throw new IllegalArgumentException("Invalid type");
                    }

                    // Parse the date string into a Date object
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    Date parsedDate = dateFormat.parse(date);

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
                    startActivity(new Intent(getApplicationContext(), DetailedNutritionActivity.class));
                } catch (NumberFormatException e) {
                    // Handle the case where parsing quantity fails
                    Toast.makeText(getApplicationContext(), "Invalid quantity format", Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    // Handle the case where parsing date fails
                    Toast.makeText(getApplicationContext(), "Invalid date format", Toast.LENGTH_LONG).show();
                } catch (IllegalArgumentException e) {
                    // Handle the case where type is empty
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    // Handle other unexpected exceptions
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "An unexpected error occurred", Toast.LENGTH_LONG).show();
                }
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
