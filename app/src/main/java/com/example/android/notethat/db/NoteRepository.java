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

    public void delete (Note note){
        new deleteAsyncTask(mNoteDoa).execute(note);
    }

    public void deleteAll (){
        new deleteAllAsyncTask(mNoteDoa).execute();
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

    private static class deleteAsyncTask extends AsyncTask<Note, Void, Void>{

        private NoteDao mAsyncTaskDao;

        deleteAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }

    private static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        private NoteDao mAsyncTaskDao;

        deleteAllAsyncTask(NoteDao dao) {
            mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }

}
