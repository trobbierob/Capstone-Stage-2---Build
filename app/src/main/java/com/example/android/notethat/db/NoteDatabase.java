package com.example.android.notethat.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;

import com.example.android.notethat.model.Note;

@Database(entities = {Note.class}, version = 1)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    static NoteDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoteDatabase.class) {
                if(INSTANCE == null){
                    //Create Note database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, "note_database")
                            .addCallback(RoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback RoomDatabaseCallback = new RoomDatabase.Callback(){
      @Override
      public void onOpen (SupportSQLiteDatabase db){
          super.onOpen(db);
          new PopulateDbAsync(INSTANCE).execute();
      }
    };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void>{

        private final NoteDao mDao;

        PopulateDbAsync(NoteDatabase db) {
            mDao = db.noteDao();
        }

        @Override
        protected Void doInBackground(final Void... voids) {
            mDao.deleteAll();
            Note note = new Note("What's the word?");
            mDao.insert(note);
            note = new Note("Big Bird?");
            mDao.insert(note);
            return null;
        }
    }
}
