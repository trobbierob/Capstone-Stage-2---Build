package com.example.android.notethat;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.android.notethat.db.NoteViewModel;
import com.example.android.notethat.model.Note;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    private static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;
    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        final Intent intent = new Intent(this, EditorActivity.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel.getmAllWords().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(intent, NEW_WORD_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_WORD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(EditorActivity.EXTRA_REPLY));
            mNoteViewModel.insert(note);
        } else if (resultCode == RESULT_CANCELED){
            Toasty.info(this,
                    getString(R.string.not_saved_string),
                    Toast.LENGTH_LONG, true).show();
        } else {
            Toasty.error(this, getString(R.string.deleted_string),
                    Toast.LENGTH_LONG, true).show();
        }
    }
}
