package com.example.android.notethat;

import android.appwidget.AppWidgetManager;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.example.android.notethat.db.NoteViewModel;
import com.example.android.notethat.model.Note;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import java.util.List;

import es.dmoral.toasty.Toasty;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int NEW_NOTE_ACTIVITY_REQUEST_CODE = 1;
    private NoteViewModel mNoteViewModel;
    private InterstitialAd mInterstitial;
    private AdRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        // Initialize MobileAds
        MobileAds.initialize(this, "ca-app-pub-3170570918400197~4823456895");

        // Initialize Interstitial Ad
        mInterstitial = new InterstitialAd(this);
        mInterstitial.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        request = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mInterstitial.loadAd(request);

        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        final Intent intent = new Intent(this, EditorActivity.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final NoteAdapter adapter = new NoteAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNoteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.setNotes(notes);
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mInterstitial.isLoaded()) {
                    mInterstitial.show();
                } else {
                    startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
                }

            }
        });

        mInterstitial.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load Ad & start EditorActivity
                mInterstitial.loadAd(request);
                startActivityForResult(intent, NEW_NOTE_ACTIVITY_REQUEST_CODE);
            }

        });
    }

    public void forceCrash(View view) {
        throw new RuntimeException("This is a crash");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuItemSelected = item.getItemId();
        if (menuItemSelected == R.id.action_bar_delete_all) {
            mNoteViewModel.deleteAll();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_NOTE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Note note = new Note(data.getStringExtra(EditorActivity.EXTRA_REPLY));
            changeWidgetText(note.getmNoteText());
            mNoteViewModel.insert(note);
        } else if (resultCode == RESULT_CANCELED){
            Toasty.info(this, getString(R.string.not_saved_string),
                    Toast.LENGTH_SHORT, true).show();
        } else {
            Note note = new Note(data.getStringExtra(EditorActivity.EXTRA_REPLY));
            mNoteViewModel.delete(note);
            Toasty.error(this, getString(R.string.deleted_string),
                    Toast.LENGTH_SHORT, true).show();
        }
    }

    private void changeWidgetText(String noteContent) {
        RemoteViews view = new RemoteViews(getPackageName(), R.layout.note_widget_provider);
        view.setTextViewText(R.id.appwidget_last_note, noteContent);
        ComponentName theWidget = new ComponentName(this, NoteWidgetProvider.class);
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        manager.updateAppWidget(theWidget, view);
    }
}
