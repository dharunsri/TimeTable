package com.innovators.timetable;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ac_Adapter extends RecyclerView.Adapter<ac_Adapter.MyViewHolder> implements Filterable {

    Context context;
    Activity activity;
    List<ac_Model> notesList;
    List<ac_Model> newList;


    public ac_Adapter(Context context, Activity activity, List<ac_Model> notesList)   {
        this.context = context;
        this.activity = activity;
        this.notesList = notesList;
        newList = new ArrayList<>(notesList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ac_note_holder,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.eventType.setText(notesList.get(position).getEventType());
        holder.collegeName.setText(notesList.get(position).getCollegeName());
        holder.eventDate.setText(notesList.get(position).getEventDate());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateAchievements.class);
                intent.putExtra("eventType",notesList.get(position).getEventType());
                intent.putExtra("collegeName",notesList.get(position).getCollegeName());
                intent.putExtra("eventName",notesList.get(position).getEventName());
                intent.putExtra("eventDate",notesList.get(position).getEventDate());
                intent.putExtra("id",notesList.get(position).getId());

                activity.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return notesList.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }

    private Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<ac_Model> filteredList = new ArrayList<>();

            if(constraint == null || constraint.length() == 0){
                filteredList.addAll(newList);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(ac_Model item : newList){
                    if(item.getEventType().toLowerCase().contains(filterPattern)){
                        filteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            notesList.clear();
            notesList.addAll((List)filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView eventType, collegeName, eventDate;
        RelativeLayout layout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            eventType = itemView.findViewById(R.id.EventType);
            collegeName = itemView.findViewById(R.id.CollegeName);
            eventDate = itemView.findViewById(R.id.EventDate);
            layout = itemView.findViewById(R.id.ac_note_layout);

        }
    }

    public List<ac_Model>getList(){
        return notesList;
    }

    public void removeItem(int position){
        notesList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(ac_Model item, int position){
        notesList.add(position,item);
        notifyItemInserted(position);
    }
}
