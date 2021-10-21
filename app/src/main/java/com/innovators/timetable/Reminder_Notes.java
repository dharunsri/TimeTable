package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Reminder_Notes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_notes);

        getSupportActionBar().hide();
    }
}