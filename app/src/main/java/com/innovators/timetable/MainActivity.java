package com.innovators.timetable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Adapter adapter;
    List<Model> notesList;
    Database database;
    ImageView Reminder, Achievements;
    ConstraintLayout constraintLayout;

    private String selectedFilter = null;

    TextView date, time;
    private ImageButton add;
    private Object OnMenuItemClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_TimeTable);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        Reminder = findViewById(R.id.Reminder_Notes);
        Achievements = findViewById(R.id.Achievements);

        recyclerView = findViewById(R.id.recycler);
        notesList = new ArrayList<>();

        database = new Database(this);
        fetchAllNotesFromDatabase();

        constraintLayout = findViewById(R.id.layout_main);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new Adapter(this, MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);

        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);

        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        add = (ImageButton) findViewById(R.id.add);

        Achievements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoToAchievements();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNotesCreate();
            }
        });


        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        date.setText(currentDate);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(10);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Calendar calendar1 = Calendar.getInstance();
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss aa");
                                String currentTime = simpleDateFormat.format(calendar1.getTime());
                                time.setText(currentTime);
                            }
                        });
                    }
                } catch (Exception e) {
                    time.setText(R.string.app_name);
                }
            }
        };
        thread.start();
    }

    void fetchAllNotesFromDatabase() {
        Cursor cursor = database.readAllData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No Data to Show", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                notesList.add(new Model(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5)));
            }
        }

    }

    public void openNotesCreate() {
        Intent intent = new Intent(this, notesCreate.class);
        startActivity(intent);
    }


    ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getAdapterPosition();
            Model item = adapter.getList().get(position);

            adapter.removeItem(position);

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
                                Database db = new Database(MainActivity.this);
                                db.deleteSingleItem(item.getId());
                            }
                        }
                    });
            snackbar.setActionTextColor(Color.YELLOW);
            snackbar.show();

        }
    };
    public void createSome1(String v1 , String v2, String v3, String v4, String v5) {
        if (!TextUtils.isEmpty(v1)&& !TextUtils.isEmpty(v3) && !TextUtils.isEmpty(v4) && !TextUtils.isEmpty(v5)) {
            Database db = new Database(MainActivity.this);
            db.addNotes(v1, v2, v3, v4, v5);


        } else {
            Toast.makeText(MainActivity.this, "All fields Required", Toast.LENGTH_SHORT).show();
        }
    }


    public void filterList(String status) {
        selectedFilter = status;
        ArrayList<Model> filterDays = new ArrayList<>();

        for (Model model : notesList) {
            if (model.getDay().toLowerCase().contains(status.toLowerCase())) {
                filterDays.add(model);
            }

        }
        Adapter adapter = new Adapter(getApplicationContext(), MainActivity.this, filterDays);
        recyclerView.setAdapter(adapter);
    }

    public void GoToAchievements(){
        Intent intent = new Intent(this, com.innovators.timetable.Achievements.class);
        startActivity(intent);
    }



    public void FilterAll(View view){
        Adapter adapter = new Adapter(getApplicationContext(), MainActivity.this, notesList);
        recyclerView.setAdapter(adapter);
    }

    public void FilterMonday(View view) {
        filterList("monday");
    }

    public void FilterTuesday(View view) {
        filterList("tuesday");
    }

    public void FilterWednesday(View view) {
        filterList("wednesday");
    }

    public void FilterThursday(View view) {
        filterList("thursday");
    }

    public void FilterFriday(View view) {
        filterList("friday");
    }

    public void FilterSaturday(View view) {
        filterList("saturday");
    }

    public void FilterSunday(View view) {
        filterList("sunday");
    }

}