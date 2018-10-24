package com.example.android.notethat.db;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.notethat.model.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao mNoteDoa;
    private LiveData<List<Note>> mAllNotes;

    NoteRepository(Application application) {
        NoteDatabase db = NoteDatabase.getDatabase(application);
        mNoteDoa = db.noteDao();
        mAllNotes = mNoteDoa.getAllNotes();
    }

    LiveData<List<Note>> getmAllNotes(){
        return mAllNotes;
    }

    public void insert (Note note){
        new insertAsyncTask(mNoteDoa).execute(note);
    }

    private static class insertAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao mAsyncTaskDao;

        insertAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(final Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

}
