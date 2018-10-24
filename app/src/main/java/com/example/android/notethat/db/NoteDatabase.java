package com.example.android.notethat.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

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
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
