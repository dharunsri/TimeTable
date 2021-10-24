package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Update_Reminder_Notes extends AppCompatActivity {

    EditText title,description;
    TextView reminder_date;
    ImageButton reminder_bt;
    Button updateNotes;
    String id;
    String date_time = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reminder_notes);

        getSupportActionBar().hide();

        title = findViewById(R.id.r_title);
        description = findViewById(R.id.r_description);
        updateNotes = findViewById(R.id.update_reminder_bt);
        reminder_bt = findViewById(R.id.reminder);
        reminder_date = findViewById(R.id.reminder_date);

        Intent i =getIntent();
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
        id=i.getStringExtra("id");

        reminder_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker();

            }
        });

        updateNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString()))
                {

                    RM_Database db = new RM_Database(Update_Reminder_Notes.this);
                    db.updateNotes(title.getText().toString(),description.getText().toString(),id);

                    Intent i=new Intent(Update_Reminder_Notes.this,Reminder_Notes.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }
                else
                {
                    Toast.makeText(Update_Reminder_Notes.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void datePicker(){
        final Calendar cal = Calendar.getInstance();
        int Year = cal.get(Calendar.YEAR);
        int Month = cal.get(Calendar.MONTH);
        int Day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date_time = dayOfMonth + "-" + (month + 1) + "-" + year;
                timePicker();

            }
        }, Year,Month,Day);
        datePickerDialog.show();
    }

    private void timePicker(){
        final Calendar cal = Calendar.getInstance();
        int Hour = cal.get(Calendar.HOUR_OF_DAY);
        int Minute = cal.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                int Hour = hourOfDay;
                int Minute = minute;

                reminder_date.setText(date_time+" ("+hourOfDay+":"+minute+")");
            }
        }, Hour, Minute, false);
        timePickerDialog.show();
    }
}