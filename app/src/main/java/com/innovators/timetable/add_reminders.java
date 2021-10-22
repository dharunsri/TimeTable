package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class add_reminders extends AppCompatActivity {

    EditText title, description;
    Button addReminder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminders);

        getSupportActionBar().hide();

        title = findViewById(R.id.r_title);
        description = findViewById(R.id.r_description);
        addReminder = findViewById(R.id.add_reminder_bt);

        addReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){
                    RM_Database db = new RM_Database(add_reminders.this);
                    db.addNotes(title.getText().toString(), description.getText().toString());

                    Intent intent = new Intent(add_reminders.this,Reminder_Notes.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();

                } else {
                    Toast.makeText(add_reminders.this, "Both Fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}