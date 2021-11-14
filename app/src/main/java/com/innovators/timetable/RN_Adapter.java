package com.innovators.timetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RN_Adapter extends RecyclerView.Adapter<RN_Adapter.MyViewHolder> {
    Context context;
    Activity layout;
    List<RN_Model> notesList;

    public RN_Adapter(Context context, Activity layout, List<RN_Model> notesList) {
        this.context = context;
        this.layout = layout;
        this.notesList = notesList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rn_note_holder,parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder1, @SuppressLint("RecyclerView") int position) {

        holder1.title.setText(notesList.get(position).getTitle());
        holder1.description.setText(notesList.get(position).getDescription());
        holder1.date.setText(notesList.get(position).getDate());

        holder1.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Update_Reminder_Notes.class);

                intent.putExtra("title", notesList.get(position).getTitle());
                intent.putExtra("description", notesList.get(position).getDescription());
                intent.putExtra("date", notesList.get(position).getDate());
                intent.putExtra("id", notesList.get(position).getId());

                layout.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView title, description, date;
        RelativeLayout layout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.datetime);
            layout = itemView.findViewById(R.id.rn_note_layout);
        }
    }

    public List<RN_Model> getNotesList(){
        return notesList;
    }

    public void removeItem(int position){
        notesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(RN_Model item, int position){
        notesList.add(position, item);
        notifyItemInserted(position);
    }
}
