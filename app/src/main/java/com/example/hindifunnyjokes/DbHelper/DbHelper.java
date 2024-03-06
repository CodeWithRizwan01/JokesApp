package com.example.hindifunnyjokes.DbHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.hindifunnyjokes.ModelClasses.Jokes;

import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FavouriteJokes";
    public static final int DATABASE_VERSION = 1;

    private static DbHelper instance;
    public static DbHelper getInstance(Context context){
        if (instance == null){
            instance = new DbHelper(context);
        }
        return instance;
    }

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Jokes.CREATE_JOKES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL(Jokes.DELETE_JOKES_TABLE);
            db.execSQL(Jokes.CREATE_JOKES_TABLE);
        }
    }

    public boolean insertFavouriteJoke(String title, String joke){
        SQLiteDatabase database = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Jokes.KEY_TITLE, title);
        values.put(Jokes.KEY_JOKE, joke);

        long effectRows = -1;
        try {
            effectRows = database.insert(Jokes.JOKES_TABLE, null, values);
        } catch (Exception ex){
            return false;
        }

        return effectRows == 1;
    }

    public boolean deleteFavouriteJoke(String title){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = -1;
        try {
            effectRows = database.delete(Jokes.JOKES_TABLE, Jokes.KEY_TITLE + "=?", new String[]{title});
        } catch (Exception ex){
            return false;
        }

        return effectRows == 1;
    }



    public boolean deleteAllRows(){
        SQLiteDatabase database = getWritableDatabase();

        long effectRows = -1;
        try {
            effectRows = database.delete(Jokes.JOKES_TABLE, null, null);
        } catch (Exception ex){
            return false;
        }

        return effectRows == 1;
    }

    public List<Jokes> getAllFavouriteJokes (){
        SQLiteDatabase database = getReadableDatabase();

        Cursor cursor = database.rawQuery(Jokes.SELECT_JOKES_TABLE, null);

        List<Jokes> jokesList = new ArrayList<>(cursor.getCount());
        if (cursor.moveToFirst()){
            do {
                Jokes jokes = new Jokes();
                jokes.setId(cursor.getInt(0));
                jokes.setTitle(cursor.getString(1));
                jokes.setJoke(cursor.getString(2));
                jokesList.add(jokes);
            } while (cursor.moveToNext());
        }

        return jokesList;
    }

    public boolean isJokeExist(String title){
        SQLiteDatabase database = getReadableDatabase();
        Cursor cursor = null;
        try {
            String query = "SELECT * FROM " + Jokes.JOKES_TABLE + " WHERE " + Jokes.KEY_TITLE + " = ?";
            cursor = database.rawQuery(query, new String[]{title});
            return cursor.getCount() > 0;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

}
