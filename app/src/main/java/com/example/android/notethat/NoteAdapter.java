package com.example.android.notethat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notethat.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private final TextView noteItemTextView;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemTextView = itemView.findViewById(R.id.note_tv);
        }
    }

    private final LayoutInflater mInflater;
    private List<Note> mNotes;

    NoteAdapter(Context context){
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.note_list_item, viewGroup, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int position) {
        if (mNotes != null) {
            Note currentNote = mNotes.get(position);
            noteViewHolder.noteItemTextView.setText(currentNote.getmNoteText());
        } else {
            noteViewHolder.noteItemTextView.setText("");
        }

    }

    void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNotes != null) {
            return mNotes.size();
        } else {
            return 0;
        }

    }
}
