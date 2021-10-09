package com.innovators.timetable;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Note_holder extends AppCompatActivity {
    TextView link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_holder);

        link = findViewById(R.id.link);

        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String link2 = link.getText().toString();
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link2));
                startActivity(intent);
            }
        });
    }
}
