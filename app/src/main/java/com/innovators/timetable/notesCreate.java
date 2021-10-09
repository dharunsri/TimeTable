package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;

public class notesCreate extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextInputEditText subTitle, meetLink;
    Button save, cancel;

    //int positionOfSelectedDataFromSpinner;

    Button start, end;
    int hour, minute;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_create);

        start = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
        spinner = findViewById(R.id.days);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        subTitle = findViewById(R.id.subTitle);
        meetLink = findViewById(R.id.meetLink);
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToMainActivity();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1,v2,v3,v4,v5;

                v1=subTitle.getText().toString();
                v2=meetLink.getText().toString();
                v3=spinner.getSelectedItem().toString();
                v4=start.getText().toString();
                v5=end.getText().toString();
                createSome(v1,v2,v3,v4,v5);


            }
        });


    }
    public void createSome(String v1 , String v2, String v3, String v4, String v5) {
        if (!TextUtils.isEmpty(v1)&& !TextUtils.isEmpty(v3) && !TextUtils.isEmpty(v4) && !TextUtils.isEmpty(v5)) {
            Database db = new Database(notesCreate.this);
            db.addNotes(v1, v2, v3, v4, v5);

            Intent intent = new Intent(notesCreate.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(notesCreate.this, "All fields Required", Toast.LENGTH_SHORT).show();
        }
    }


    public void popTimePicker1(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                start.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour,minute, true);

        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }

    public void popTimePicker2(View view) {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                hour = selectedHour;
                minute = selectedMinute;
                end.setText(String.format(Locale.getDefault(),"%02d:%02d",hour,minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this, onTimeSetListener, hour,minute, true);

        timePickerDialog.setTitle("Set Time");
        timePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        String text = parent.getItemAtPosition(position).toString();


    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void backToMainActivity(){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}