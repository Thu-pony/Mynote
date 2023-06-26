package com.example.mynote1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Liang
 * @date 2023/6/25 18:00
 * description
 */
public class MysqlopenHelper extends SQLiteOpenHelper {
    private static final String TAG = "Mynote";
    private static final String  DB_NAME = "MYNOTE.db";
    private static final int version = 2;
    private static final String TABLE_NOTES = "notes";
    //建表
    private static final String create_notes = "CREATE TABLE notes (\n" +
            "  ID INTEGER PRIMARY KEY AUTOINCREMENT ,\n" +
            "  title TEXT,\n" +
            "  subtitle TEXT,\n" +
            "  content TEXT, \n" +
            " timestamp DATETIME DEFAULT CURRENT_TIMESTAMP \n" +
            ")";

    public MysqlopenHelper(@Nullable Context context) {
        super(context, DB_NAME, null, version);
        Log.d(TAG,"helper 构造函数");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d(TAG,"helper create");
        sqLiteDatabase.execSQL(create_notes);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public List<Note> getAllNote() {
        List<Note> noteList = new ArrayList<Note>();
        // 执行查询语句
        String selectQuery = "SELECT  * FROM " + TABLE_NOTES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // 循环遍历所有的行并添加到List中
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(0));
                note.setTitle(cursor.getString(1));
                note.setSubtitle(cursor.getString(2));
                note.setNoteText(cursor.getString(3));
                note.setDateTime(cursor.getString(4));
                noteList.add(note);
            } while (cursor.moveToNext());
        }
        // 关闭数据库连接
        db.close();
        // 返回Note列表
        return noteList;
    }
    public long addNote(Note note) {
        String title = note.getTitle();
        String subtitle = note.getSubtitle();
        String content = note.getNoteText();
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("subtitle", subtitle);
        values.put("content", content);
        Log.d(TAG,note.toString());
        long res = db.insert(TABLE_NOTES, null, values);
        //db.close();
        Log.d(TAG,"插入时候成功，"  + res);
        return  res;
    }
}
