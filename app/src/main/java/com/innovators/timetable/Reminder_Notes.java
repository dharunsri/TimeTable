package com.innovators.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class Reminder_Notes extends AppCompatActivity {

    ImageButton add_bt;
    RecyclerView recyclerView;
    RN_Adapter adapter;
    List<RN_Model> notesList;
    RM_Database database;
    ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_notes);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.reminder_recycle);
        constraintLayout = findViewById(R.id.remainder_layout);

        add_bt = findViewById(R.id.add_reminders);

        add_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenCreateReminders();
            }
        });

        notesList = new ArrayList<>();
        database = new RM_Database(this);
        fetchAllNotesFromDatabase();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RN_Adapter(this, Reminder_Notes.this, notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }

    void fetchAllNotesFromDatabase() {
        Cursor cursor = database.readAllData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                notesList.add(new RN_Model(cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(0)));
            }
        }

    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            int position = viewHolder.getAdapterPosition();
            RN_Model item = adapter.getNotesList().get(position);

            adapter.removeItem(viewHolder.getAdapterPosition());

            Snackbar snackbar = Snackbar.make(constraintLayout, "Item Deleted", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            adapter.restoreItem(item, position);
                            recyclerView.scrollToPosition(position);
                        }
                    }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        @Override
                        public void onDismissed(Snackbar transientBottomBar, int event) {
                            super.onDismissed(transientBottomBar, event);

                            if (!(event == DISMISS_EVENT_ACTION)) {
                                RM_Database db = new RM_Database(Reminder_Notes.this);
                                db.deleteSingleItem(item.getId());
                            }


                        }
                    });

            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    };

    public void OpenCreateReminders(){
        Intent intent = new Intent(this, add_reminders.class);
        startActivity(intent);
    }
}