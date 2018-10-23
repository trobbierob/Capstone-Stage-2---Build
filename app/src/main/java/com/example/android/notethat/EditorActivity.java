package com.example.android.notethat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

public class EditorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        EditText noteContent = findViewById(R.id.note_content_et);

        

    }


    @Override
    public void onBackPressed() {
        /*Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
        super.onBackPressed();
    }
}
