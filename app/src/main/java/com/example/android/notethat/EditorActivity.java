package com.example.android.notethat;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.notethat.db.NoteViewModel;
import com.example.android.notethat.model.Note;

import es.dmoral.toasty.Toasty;

public class EditorActivity extends AppCompatActivity {

    private static final String TAG = EditorActivity.class.getSimpleName();
    private EditText mNoteContent;

    public static final String EXTRA_REPLY = "stuff";
    public static final String EXTRA_INT_REPLY = "stuff_with_numbers";
    private static final int RESULT_EDITED = 3;
    public static final String PASSING_NOTE_KEY = "passing_note_key";
    private int PASSING_NOTE_INT = 0;
    private boolean NOTE_KEY = false;

    private NoteViewModel mNoteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNoteContent = findViewById(R.id.note_content_et);

        Intent intent = getIntent();
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        /**
         * If the intent has data, we want to edit an existing note.
         * If the intent does not have data, we want to create a new note
         */
        if (intent.hasExtra(PASSING_NOTE_KEY)){
            NOTE_KEY = true;
            getSupportActionBar().setTitle(R.string.edit_note_string);
            Note note = intent.getParcelableExtra(PASSING_NOTE_KEY);
            PASSING_NOTE_INT = note.getId();
            mNoteContent.setText(note.getmNoteText());
        } else {
            getSupportActionBar().setTitle(R.string.new_note_string);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_bar_save) {
            Intent intent = new Intent();
            if (TextUtils.isEmpty(mNoteContent.getText())){
                setResult(RESULT_CANCELED, intent);
            } else if (NOTE_KEY){
                String noteContent = mNoteContent.getText().toString().trim();
                Note note = new Note(PASSING_NOTE_INT, noteContent);
                mNoteViewModel.update(note);
                Toasty.success(this, getString(R.string.saved_string),
                        Toast.LENGTH_SHORT, true).show();
            } else {
                String noteContent = mNoteContent.getText().toString().trim();
                intent.putExtra(EXTRA_REPLY, noteContent);
                setResult(RESULT_OK, intent);
                Toasty.success(this, getString(R.string.saved_string),
                        Toast.LENGTH_SHORT, true).show();
            }
            finish();
        } else if(menuItemSelected == R.id.action_bar_delete){
            Intent intent = new Intent();
            setResult(RESULT_FIRST_USER, intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
