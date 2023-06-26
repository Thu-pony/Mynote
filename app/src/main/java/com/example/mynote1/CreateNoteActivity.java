package com.example.mynote1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {
    private MysqlopenHelper mysqlopenHelper;
    private EditText titleEditText,subtitleEditText, contentEditText;
    private TextView textDateTime;
    private ImageView saveImageView;
    private Note alreadyAvailableNote;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        ininView();
        mysqlopenHelper = new MysqlopenHelper(this);
        //设置当前要查看笔记
        if(getIntent().getBooleanExtra("isViewOrUpdate",false)){
            alreadyAvailableNote = (Note)getIntent().getSerializableExtra("note");
            setViewOrUpdateNote();
        }
        saveImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNote();
            }
        });
    }

    private void setViewOrUpdateNote() {
        contentEditText.setText(alreadyAvailableNote.getNoteText());
        titleEditText.setText(alreadyAvailableNote.getTitle());
        subtitleEditText.setText(alreadyAvailableNote.getSubtitle());
        textDateTime.setText(alreadyAvailableNote.getDateTime());
    }

    private void saveNote() {
        if(titleEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note title can't be empty!",Toast.LENGTH_SHORT).show();
            return;
        }else if(subtitleEditText.getText().toString().trim().isEmpty()
                && contentEditText.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note can't be empty!",Toast.LENGTH_SHORT).show();
            return;
        }

        final Note note = new Note();
        note.setTitle(titleEditText.getText().toString());
        note.setSubtitle(subtitleEditText.getText().toString());
        note.setNoteText(contentEditText.getText().toString());
        //note.setDateTime(textDateTime.getText().toString());
        long res = mysqlopenHelper.addNote(note);
        if (res == -1)Toast.makeText(this,"Note insert failed!",Toast.LENGTH_SHORT).show();
        else Toast.makeText(this,"Note insert successfully",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        setResult(RESULT_OK,intent);
        //隐藏软键盘
        InputMethodManager imm = (InputMethodManager) CreateNoteActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(CreateNoteActivity.this.getWindow().getDecorView().getWindowToken(),0);
        finish();
    }

    private void ininView() {
        titleEditText = findViewById(R.id.inputNoteTitle);
        subtitleEditText = findViewById(R.id.inputNoteSubtitle);
        contentEditText = findViewById(R.id.inputNoteText);
        saveImageView = findViewById(R.id.imageSave);
        textDateTime = findViewById(R.id.textDateTime);
        textDateTime.setText(
                new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a", Locale.getDefault()).format(new Date())
        );

    }
}