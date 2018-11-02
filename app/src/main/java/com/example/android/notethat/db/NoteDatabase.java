package com.example.android.notethat.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.android.notethat.R;
import com.example.android.notethat.model.Note;

@Database(entities = {Note.class}, version = 2)
public abstract class NoteDatabase extends RoomDatabase {

    public abstract NoteDao noteDao();

    private static volatile NoteDatabase INSTANCE;

    public static NoteDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoteDatabase.class) {
                if(INSTANCE == null){
                    //Create Note database
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteDatabase.class, context.getString(R.string.note_database_string))
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
      }
    };
}
