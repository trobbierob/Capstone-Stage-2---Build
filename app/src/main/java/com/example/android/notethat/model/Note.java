package com.example.android.notethat.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import android.support.annotation.NonNull;

@Entity(tableName = "note_table")
public class Note implements Parcelable {

    public Note() {
    }

    public Note(@NonNull String mNoteText) {
        this.mNoteText = mNoteText;
    }

    private int id;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "noteText")
    public String mNoteText;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getmNoteText() {
        return mNoteText;
    }

    public void setmNoteText(String mNoteText) {
        this.mNoteText = mNoteText;
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", mNoteText='" + mNoteText + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(mNoteText);
    }

    protected Note(Parcel in) {
        id = in.readInt();
        mNoteText = in.readString();
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };
}
