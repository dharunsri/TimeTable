package com.innovators.timetable;

import static android.provider.OpenableColumns.DISPLAY_NAME;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.channels.FileChannel;
import java.util.Calendar;


public class add_achievements extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner spinner;
    private DatePickerDialog datePickerDialog;
    EditText eventDate,CollegeName,EventName;
    TextView upload_certificate;
    Button upload_button, add_achievements;
    private static final int FILE_SELECT_CODE = 1;

    String[] events = { "- Select the event -","Symposium", "Workshop",
            "Paper Presentation", "Hackathon",
            "Webinar", "Internship" };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achievements);

        getSupportActionBar().hide();

        initDatePicker();

        add_achievements = findViewById(R.id.add_achievements);
        upload_certificate = findViewById(R.id.upload_certificate);
        upload_button = findViewById(R.id.upload_button);

        spinner = findViewById(R.id.Events);
        CollegeName = findViewById(R.id.college_name);
        EventName = findViewById(R.id.event_name);
        eventDate = findViewById(R.id.event_date);
        eventDate.setText(getTodaysDate());
        ArrayAdapter ad = new ArrayAdapter(this, android.R.layout.simple_spinner_item, events);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        spinner.setOnItemSelectedListener(this);

        add_achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1,v2,v3,v4;

                v1 = spinner.getSelectedItem().toString();
                v2 = CollegeName.getText().toString();
                v3 = EventName.getText().toString();
                v4 = eventDate.getText().toString();
                createSome(v1,v2,v3,v4);

            }
        });

//        upload_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //createDirectory("Example");

//            }
//        });



        upload_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
//                chooseFile.addCategory(Intent.CATEGORY_OPENABLE);
//                chooseFile.setType("*/*");
//                startActivityForResult(
//                        Intent.createChooser(chooseFile, "Choose a file"),
//                        FILE_SELECT_CODE
//                );

                showFileChooser();
            }
        });

    }

    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d("FilePicker", "File Uri: " + uri.toString());
                    // Get the path
                    String path = null;
                    try {
                        path = getPath(this, uri);
                        //File file = new File(path);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }
                    Log.d("FilePicker", "File Path: " + path);
                    // Get the file instance
                    // File file = new File(path);
                    // Initiate the upload
                    upload_certificate.setText("File Selected");
                    File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"TimeTable");
                    //File filepath = new File(path);
                    try {
                        copyDirectory(new File(path),destination);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
                break;

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    public void copyDirectory(File sourceLocation , File targetLocation)
            throws IOException {

        if (sourceLocation.isDirectory()) {
            if (!targetLocation.exists()) {
                targetLocation.mkdir();
            }

            String[] children = sourceLocation.list();
            for (int i=0; i<children.length; i++) {
                copyDirectory(new File(sourceLocation, children[i]),
                        new File(targetLocation, children[i]));
            }
        } else {

            InputStream in = new FileInputStream(sourceLocation);
            OutputStream out = new FileOutputStream(targetLocation);

            // Copy the bits from instream to outstream
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        }
    }

    //    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICKFILE_RESULT_CODE && resultCode == Activity.RESULT_OK){
//            Uri content_describer = data.getData();
//            //String src = Environment.getExternalStorageDirectory().getAbsolutePath()+content_describer;
//            String fileString = content_describer.toString();
//            File file = new File(fileString);
//            String path = file.getAbsolutePath();
//            Log.d("src is ", file.toString());
//            String displayName = file.getName();
//            String filename = displayName;
//            //upload_certificate.setText(src);
//            Log.d("FileName is ",filename);
//            File destination = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),"TimeTable");
//            upload_certificate.setText(filename);
//            Log.d("Destination is ", destination.toString());
//            //SetToFolder.setEnabled(true);
//
//            //Uri content_describer = data.getData();
//
//            upload_button.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    File file = destination;
//
//                    if (!file.exists()){
//
//                        file.mkdir();
//
//                        Toast.makeText(add_achievements.this,"Successful",Toast.LENGTH_SHORT).show();
//                    }else
//                    {
//
//                        Toast.makeText(add_achievements.this,"Folder Already Exists",Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
//        }
//    }
//

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

    public void createSome(String v1 , String v2, String v3, String v4) {
        if (!TextUtils.isEmpty(v1)&& !TextUtils.isEmpty(v3) && !TextUtils.isEmpty(v4)) {
            ac_Database db = new ac_Database(add_achievements.this);
            db.addNotes(v1, v2, v3, v4);

            Intent intent = new Intent(add_achievements.this, Achievements.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();

        } else {
            Toast.makeText(add_achievements.this, "All fields Required", Toast.LENGTH_SHORT).show();
        }
    }
}