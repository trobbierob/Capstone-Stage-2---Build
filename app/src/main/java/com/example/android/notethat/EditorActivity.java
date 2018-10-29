package com.example.android.notethat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class EditorActivity extends AppCompatActivity {

    private EditText mNoteContent;

    public static final String EXTRA_REPLY = "stuff";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mNoteContent = findViewById(R.id.note_content_et);
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
            } else {
                String noteContent = mNoteContent.getText().toString();
                intent.putExtra(EXTRA_REPLY, noteContent);
                setResult(RESULT_OK, intent);
                Toasty.success(this, getString(R.string.saved_string),
                        Toast.LENGTH_LONG, true).show();
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
