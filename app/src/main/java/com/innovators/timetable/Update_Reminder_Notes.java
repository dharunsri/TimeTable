package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Update_Reminder_Notes extends AppCompatActivity {

    EditText title,description;
    Button updateNotes;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_reminder_notes);

        getSupportActionBar().hide();

        title = findViewById(R.id.r_title);
        description = findViewById(R.id.r_description);
        updateNotes = findViewById(R.id.update_reminder_bt);

        Intent i =getIntent();
        title.setText(i.getStringExtra("title"));
        description.setText(i.getStringExtra("description"));
        id=i.getStringExtra("id");

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
}