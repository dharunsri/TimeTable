package com.innovators.timetable;

import static androidx.core.content.ContextCompat.startActivity;
import static androidx.recyclerview.widget.RecyclerView.*;
//import  com.innovators.timetable.notesCreate;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.ActionMenuItem;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> implements PopupMenu.OnMenuItemClickListener {
    Context context;
    Activity activity;
    List<Model> notesList;

    //int positionOfSelectedDataFromSpinner;

    public Adapter(Context context, Activity activity, List<Model> notesList) {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.note_holder, parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.title.setText(notesList.get(position).getTitle());
        holder.stime.setText(notesList.get(position).getStime());
        holder.etime.setText(notesList.get(position).getEtime());
        holder.link.setText(notesList.get(position).getLink());

        int position2 = holder.getAdapterPosition();

        holder.copy.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sub = notesList.get(position2).getTitle();
                String day = notesList.get(position2).getDay();
                String stime = notesList.get(position2).getStime();
                String etime = notesList.get(position2).getEtime();
                String link = notesList.get(position2).getLink();

                Intent intent1 = new Intent(context, MainActivity.class);
                Database db = new Database(context);
                Model item = getList().get(position);
                addItem(item, position);
                db.addNotes(sub, link, day, stime, etime);
                Intent intent3 = new Intent(context, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                activity.startActivity(intent3);
                activity.finish();

            }
        });



        holder.share.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String sub = notesList.get(position2).getTitle();
                String day = notesList.get(position2).getDay();
                String stime = notesList.get(position2).getStime();
                String etime = notesList.get(position2).getEtime();
                String link = notesList.get(position2).getLink();


                intent.putExtra(Intent.EXTRA_TEXT, "Subject: " + sub +"\n" +
                        "Day: " + day + "\n" +
                        "Time: " + stime +" - " + etime +
                        "\n" + "Link: " + link);
                activity.startActivity(Intent.createChooser(intent,"Share via"));
            }
        });

        holder.delete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(context, MainActivity.class);
                Model item = getList().get(position);
                removeItem(position);
                Database db = new Database(context);
                db.deleteSingleItem(item.getId());
            }
        });



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateNotes.class);

                intent.putExtra("title", notesList.get(position).getTitle());
                intent.putExtra("stime", notesList.get(position).getStime());
                intent.putExtra("etime", notesList.get(position).getEtime());
                intent.putExtra("days",notesList.get(position).getDay());
                intent.putExtra("link",notesList.get(position).getLink());
                intent.putExtra("id", notesList.get(position).getId());

                activity.startActivity(intent);
            }
        });
    }

    private void addItem(Model item, int position) {
        notesList.add(position, item);
        notifyItemInserted(position);
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {

        return false;
    }

    public static class MyViewHolder extends ViewHolder {
        TextView title,link;
        Spinner days;
        TextView stime, etime;
        RelativeLayout layout;
        ImageButton share,delete,copy;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            //share = itemView.findViewById(R.id.share);

            delete = itemView.findViewById(R.id.delete);

            share = itemView.findViewById(R.id.share);

            copy = itemView.findViewById(R.id.copy);

            title = itemView.findViewById(R.id.class_name);
            stime = itemView.findViewById(R.id.stime);
            etime = itemView.findViewById(R.id.etime);
            link = itemView.findViewById(R.id.link);
            days = itemView.findViewById(R.id.days);
            layout = itemView.findViewById(R.id.note_layout);
        }
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(context, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.options_menu);
        popup.show();
    }



    public List<Model> getList(){
        return notesList;
    }

    public void removeItem(int position){
        notesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Model item, int position){
        notesList.add(position,item);
        notifyItemInserted(position);
    }

}
