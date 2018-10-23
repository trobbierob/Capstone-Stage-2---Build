package com.example.android.notethat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class EditorActivity extends AppCompatActivity {

    EditText noteContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        noteContent = findViewById(R.id.note_content_et);
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
            Toasty.success(this, noteContent.getText(), Toast.LENGTH_LONG, true).show();
        } else if(menuItemSelected == R.id.action_bar_delete){
            Toasty.error(this, getString(R.string.deleted_string), Toast.LENGTH_LONG, true).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
