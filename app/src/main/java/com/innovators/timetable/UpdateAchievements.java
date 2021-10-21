package com.innovators.timetable;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class UpdateAchievements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatePickerDialog datePickerDialog;
    EditText eventDate,CollegeName,EventName;
    Button updateAchievements;
    Spinner eventType;
    String id;
    ac_Adapter adapter;

    String[] events = { "- Select the event -","Symposium", "Workshop",
            "Paper Presentation", "Hackathon",
            "Webinar", "Internship" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_achievements);

        getSupportActionBar().hide();

        initDatePicker();

        eventType = findViewById(R.id.Events);
        eventDate = findViewById(R.id.event_date);
        CollegeName = findViewById(R.id.college_name);
        EventName = findViewById(R.id.event_name);
        updateAchievements = findViewById(R.id.update_achievements);

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, events);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventType.setAdapter(adapter);
        eventType.setOnItemSelectedListener(this);


        Intent i = getIntent();
        eventDate.setText(i.getStringExtra("eventDate"));
        CollegeName.setText(i.getStringExtra("collegeName"));
        EventName.setText(i.getStringExtra("eventName"));
        id = i.getStringExtra("id");
        String val = i.getStringExtra("eventType");
        int position = adapter.getPosition(i.getStringExtra("eventType"));
        eventType.setSelection(position);

        updateAchievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(EventName.getText().toString()) && !TextUtils.isEmpty(eventType.getSelectedItem().toString()) && !TextUtils.isEmpty(CollegeName.getText().toString()) && !TextUtils.isEmpty(eventDate.getText().toString())){
                    ac_Database db = new ac_Database(UpdateAchievements.this);
                    db.updateNotes(eventType.getSelectedItem().toString(),CollegeName.getText().toString(),EventName.getText().toString(),eventDate.getText().toString(),id );
                    Intent i = new Intent(UpdateAchievements.this,Achievements.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    finish();

                }
                else{
                    Toast.makeText(UpdateAchievements.this,"All fields Required", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                eventDate.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }


    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}