package com.example.android.notethat.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.android.notethat.model.Note;

import java.util.List;

public interface NoteDao {

    @Insert
    void insert(Note note);

    @Insert
    void insertAll(Note... notes);

    @Query("DELETE FROM note_table")
    void deleteAll();

    @Query("SELECT * FROM note_table ORDER BY noteText")
    LiveData<List<Note>> getAllNotes();

}
