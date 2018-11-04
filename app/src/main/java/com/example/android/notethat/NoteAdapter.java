package com.example.android.notethat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.notethat.model.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private static final String TAG = NoteAdapter.class.getSimpleName();
    private static final String PASSING_NOTE_KEY = "passing_note_key";
    private final LayoutInflater mInflater;
    private Context mContext;
    private List<Note> mNotes;

    NoteAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    public List<Note> getmNotes() {
        return mNotes;
    }

    public void setmNotes(List<Note> mNotes) {
        this.mNotes = mNotes;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mInflater.inflate(R.layout.note_list_item, viewGroup, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int position) {
        final Note currentNote = mNotes.get(position);
        if (mNotes != null) {
            noteViewHolder.noteItemTextView.setText(currentNote.getmNoteText());
        }

        noteViewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EditorActivity.class);
                intent.putExtra(PASSING_NOTE_KEY, currentNote);
                mContext.startActivity(intent);
            }
        });
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

    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private final TextView noteItemTextView;
        public View mView;

        private NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            noteItemTextView = itemView.findViewById(R.id.note_tv);
            mView = itemView;
        }
    }
}
