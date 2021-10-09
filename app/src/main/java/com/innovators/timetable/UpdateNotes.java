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

public class UpdateNotes extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextInputEditText subTitle, meetLink;
    Button Update;
    String id, text;
    int positionToShowToSpinner;
    Button start, end;
    int hour, minute;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_notes);

        start = findViewById(R.id.startTime);
        end = findViewById(R.id.endTime);
        spinner = findViewById(R.id.days);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        subTitle = findViewById(R.id.subTitle);
        meetLink = findViewById(R.id.meetLink);
        Update = findViewById(R.id.update);

        Intent i = getIntent();
        subTitle.setText(i.getStringExtra("title"));
        meetLink.setText(i.getStringExtra("link"));
        id = i.getStringExtra("id");

        String val = i.getStringExtra("days");
        int position = adapter.getPosition(i.getStringExtra("days"));
        spinner.setSelection(position);

        start.setText(i.getStringExtra("stime"));
        end.setText(i.getStringExtra("etime"));
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(subTitle.getText().toString()) && !TextUtils.isEmpty(spinner.getSelectedItem().toString()) && !TextUtils.isEmpty(start.getText().toString()) && !TextUtils.isEmpty(end.getText().toString())){

                    Database db = new Database(UpdateNotes.this);
                    db.updateNotes(subTitle.getText().toString(),meetLink.getText().toString(),spinner.getSelectedItem().toString(),start.getText().toString(),end.getText().toString(),id );
                    Intent i = new Intent(UpdateNotes.this,MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();


                }
                else{
                    Toast.makeText(UpdateNotes.this,"All fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });



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

//    private int getIndex(Spinner spinner, String s) {
//        for (int i = 0; i < spinner.getCount(); i++) {
//            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(s)) {
//                return i;
//            }
//        }
//        return 0;
//    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long l) {
        text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}