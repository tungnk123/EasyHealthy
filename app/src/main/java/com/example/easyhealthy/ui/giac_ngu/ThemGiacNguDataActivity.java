package com.example.easyhealthy.ui.giac_ngu;

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
import com.example.easyhealthy.ui.luong_nuoc.AddLuongNuocActivity;
import com.example.easyhealthy.ui.nutrition.DetailedNutritionActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ThemGiacNguDataActivity extends AppCompatActivity {

    TextView tvNgayNguDatePikcer;
    TextView tvNgayThucDatePikcer;
    TextView tvGioNguTimePicker;
    TextView tvGioThucTimePicker;
    TextView tvHeading;
    Button btnAdd;
    EditText edtLuuLuong;

    FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_giac_ngu_data);
        try {
            addControls();
            addEvents();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addControls() {
        tvNgayNguDatePikcer = findViewById(R.id.tv_addFoodData_ngayNguTimePicker);
        tvNgayThucDatePikcer = findViewById(R.id.tv_addFoodData_ngayThucTimePicker);
        tvGioNguTimePicker = findViewById(R.id.tv_addFoodData_gioNguTimePicker);
        tvGioThucTimePicker = findViewById(R.id.tv_addFoodData_gioThucTimePicker);

        // Set current time
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String currentTime = String.format("%02d:%02d", hour, minute);
        tvGioNguTimePicker.setText(currentTime);
        tvGioThucTimePicker.setText(currentTime);

        // Set current date
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(currentDate);
        tvNgayNguDatePikcer.setText(formattedDate);
        tvNgayThucDatePikcer.setText(formattedDate);

        btnAdd = (Button) findViewById(R.id.btn_addSinhHieu);
        edtLuuLuong = (EditText) findViewById(R.id.edt_soLuong);
        tvHeading = (TextView) findViewById(R.id.tv_detailedFood_heading);

        Intent intent = getIntent();
        tvHeading.setText(intent.getStringExtra("title"));
    }

    private void addEvents() {
        tvNgayNguDatePikcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });

        tvNgayThucDatePikcer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateThucPickerDialog();
            }
        });

        tvGioNguTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePickerDialog();
            }
        });

        tvGioThucTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeGioThucPickerDialog();
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = tvHeading.getText().toString();
                String dateNgu = tvNgayNguDatePikcer.getText().toString();
                String dateThuc = tvNgayThucDatePikcer.getText().toString();
                String timeNgu = tvGioNguTimePicker.getText().toString();
                String timeThuc = tvGioThucTimePicker.getText().toString();


                // Parse the date string into a Date object
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                Date parsedDate = null;

                try {
                    parsedDate = dateFormat.parse(dateNgu);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
                Calendar calTimeNgu = Calendar.getInstance();
                Calendar calTimeThuc = Calendar.getInstance();
                try {
                    calTimeNgu.setTime(timeFormat.parse(timeNgu));
                    Date date1 = dateFormat.parse(dateNgu);
                    calTimeNgu.set(date1.getYear(), date1.getMonth(), date1.getDay());
                    calTimeThuc.setTime(timeFormat.parse(timeThuc));
                    Date date2 = dateFormat.parse(dateThuc);
                    calTimeThuc.set(date2.getYear(), date2.getMonth(), date2.getDay());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                long timeNguInMillis = calTimeNgu.getTimeInMillis();
                long timeThucInMillis = calTimeThuc.getTimeInMillis();


                long sleepDurationInMillis = timeThucInMillis - timeNguInMillis;

                // Convert sleep duration from milliseconds to minutes
                int sleepDurationInMinutes = (int) (sleepDurationInMillis / (3600 * 1000));
                if (type.isEmpty() ) {
                    return;
                }

                NutritionData nutritionData = new NutritionData(type, parsedDate, timeNgu , sleepDurationInMinutes);

                try {
                    firestore.collection(type)
                            .add(nutritionData)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Toast.makeText(getApplicationContext(), "Save successfully", Toast.LENGTH_LONG).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Save canxi failed", Toast.LENGTH_LONG).show();
                                }
                            });
                    Intent intent = new Intent(ThemGiacNguDataActivity.this, GiacNguActivity.class);
                    intent.putExtra("title", tvHeading.getText().toString());
                    startActivity(intent);
                }
                catch (Exception e) {
                    e.printStackTrace();
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
                tvNgayNguDatePikcer.setText(formattedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showDateThucPickerDialog() {
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
                tvNgayThucDatePikcer.setText(formattedDate);
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
                tvGioNguTimePicker.setText(selectedTime);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }

    private void showTimeGioThucPickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                String selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute);
                tvGioThucTimePicker.setText(selectedTime);
            }
        }, hour, minute, true);

        timePickerDialog.show();
    }
}
