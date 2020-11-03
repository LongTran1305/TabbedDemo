package com.example.tabbeddemo.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.tabbeddemo.dto.BookDTO;

import java.util.ArrayList;
import java.util.List;

public class BookDB extends SQLiteOpenHelper {

    public static final String DB_NAME ="Book.db";
    public static final int DB_VERSION = 1;

    public BookDB(@Nullable Context context) {
        super(context,DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Book(id INTEGER PRIMARY KEY,title VARCHAR,price INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Book");
        onCreate(db);
    }
    public void create(BookDTO book){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id",book.getTxtID());
        values.put("title",book.getTxtTitle());
        values.put("price",book.getTxtPrice());
        db.insert("Book",null,values);
    }

    public List<BookDTO> getBooks(){
        List<BookDTO> result = new ArrayList<>();
        try {
            SQLiteDatabase db  = getReadableDatabase();
            Cursor c = db.query("Book",new String[]{"id","title","price"},
                    null,null,null,null,null);
            while(c.moveToNext()){
                int newId = c.getInt(c.getColumnIndex("id"));
                String title = c.getString(c.getColumnIndex("title"));
                int newPrice = c.getInt(c.getColumnIndex("price"));
                BookDTO b = new BookDTO(newId,title,newPrice);
                result.add(b);
            }
            c.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }
    public void update(BookDTO bookDTO){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("title",bookDTO.getTxtTitle());
        values.put("price",bookDTO.getTxtPrice());
        db.update("Book",values,"id=?",new String[]{String.valueOf(bookDTO.getTxtID())});
    }
    public void delete(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.delete("Book","id=?",new String[]{String.valueOf(id)});
    }
    public BookDTO getBook(int id) {
        BookDTO result = null;

        try {
            SQLiteDatabase db = getReadableDatabase();

            //Cach 1:
//            Cursor cursor = db.query("Book",new String[]{"id","title","price"},
//                    "id=?",new String[]{String.valueOf(id)}, null, null, null);

            //Cach 2:
            Cursor cursor = db.query("Book",null,
                    "id=?",new String[]{String.valueOf(id)}, null, null, null);


            if(cursor.moveToNext()) {
                int rid = cursor.getInt(cursor.getColumnIndex("id"));
                String rTitle = cursor.getString(cursor.getColumnIndex("title"));
                int rPrice = cursor.getInt(cursor.getColumnIndex("price"));
                result = new BookDTO(rid,rTitle,rPrice);

            }
            cursor.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }


    public List<BookDTO> findByTitle(String s){
        List<BookDTO> result = new ArrayList<>();
        try {
            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.query("Book",new String[]{"id","title","price"},
                    "title like ?",new String[]{"%"+s+"%"},null,null,null);
            while (cursor.moveToNext()){
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                int price = cursor.getInt(cursor.getColumnIndex("price"));
                BookDTO bookDto = new BookDTO(id,title,price);
                result.add(bookDto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return result;
    }
}
