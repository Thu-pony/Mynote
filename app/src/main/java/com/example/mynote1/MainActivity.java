package com.example.mynote1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NotesListeners {
    private static final int REQUEST_CODE_ADD_NOTE = 1;
    private static final String TAG = "Mynote";
    //used to update note
    private static final int REQUEST_CODE_UPDATE_NOTE = 2;
    private static final int REQUEST_CODE_SHOW_NOTES = 3;
    private static final int REQUEST_CODE_SELECT_IMAGE = 4;
    private static final int REQUEST_CODE_STORAGE_PERMISSION = 5;
    private int clickedNotePosition = -1;
    private ImageView imageAddMain;
    private RecyclerView noteRecyclerView;
    private ArrayList<Note> noteList = new ArrayList<Note>();
    private MysqlopenHelper mysqlopenHelper;
    private NotesAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //实例化recyclerView
        noteRecyclerView = findViewById(R.id.notesRecyclerView);
        noteRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        mysqlopenHelper = new MysqlopenHelper(this);
        noteList = (ArrayList<Note>) mysqlopenHelper.getAllNote();
        noteAdapter = new NotesAdapter(noteList,this);
        noteRecyclerView.setAdapter(noteAdapter);
        imageAddMain = (ImageView) findViewById(R.id.imageAddNoteMain);
        imageAddMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(getApplicationContext(), CreateNoteActivity.class),REQUEST_CODE_ADD_NOTE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            noteList = (ArrayList<Note>) mysqlopenHelper.getAllNote();
            noteAdapter = new NotesAdapter(noteList,this);
            noteRecyclerView.setAdapter(noteAdapter);
        }
    }

    @Override
    public void onNoteClicked(Note note, int position) {
        //当前被选中的序号
        clickedNotePosition = position;
        Log.d(TAG,"被选中的序号" + clickedNotePosition);
        Intent intent = new Intent(getApplicationContext(),CreateNoteActivity.class);
        intent.putExtra("isViewOrUpdate",true);
        intent.putExtra("note",note);
        Log.d(TAG,"被选中的内容" + note);
        startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
    }
}