package com.example.bt_quatrinh_2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.NodeList;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.MyViewHolder> {
    List<note> noteList;
    Activity context;

    public NoteAdapter(List<note> noteList, Activity context) {
        this.noteList = noteList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_item_note, parent, false);
        return new NoteAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        note n = noteList.get(position);
        holder.name.setText(n.getName());
        holder.description.setText(n.getDescription());
        holder.date.setText(n.getDate());
        holder.layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, NoteDetailActivity.class);
               intent.putExtra("title_b", n.getName().toString());
               intent.putExtra("content_b", n.getDescription().toString());
               intent.putExtra("timer_b", n.getTimer().toString());
               context.startActivity(intent);
               FirebaseDatabase database = FirebaseDatabase.getInstance();
               DatabaseReference myRef = database.getReference("note");
               myRef = myRef.child(n.getId());
               myRef.removeValue();
           }
        });


    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        CardView layout;
        TextView name,description,date;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.note_layout);
            name = itemView.findViewById(R.id.name);
            description = itemView.findViewById(R.id.description);
            date = itemView.findViewById(R.id.date);
        }
    }
}
