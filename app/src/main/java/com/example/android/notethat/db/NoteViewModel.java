package com.example.android.notethat.db;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.notethat.model.Note;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository mRepository;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getmAllNotes();
    }

    public LiveData<List<Note>> getAllNotes() {
        return mAllNotes;
    }

    public void insert(Note note) {
        mRepository.insert(note);
    }

    public void delete(Note note) {
        mRepository.delete(note);
    }

    public void deleteAll() {
        mRepository.deleteAll();
    }
}
