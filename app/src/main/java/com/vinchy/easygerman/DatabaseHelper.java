package com.vinchy.easygerman;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public final class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Words.db";
    public static final int DATABASE_VERSION = 1;

    public static class WordEntry implements BaseColumns {
        private static final String PRIMARY_KEY = "ID";
        private static final String WORDS_TABLE = "WORDS_TABLE";
        private static final String COLUMN_WORD = "WORD";
        private static final String COLUMN_ARTICLE = "ARTICLE";
        private static final String COLUMN_TRANSLATION = "TRANSLATION";
        private static final String COLUMN_REPETITIONS = "REPETITIONS";
        private static final String COLUMN_CATEGORY_ID = "CATEGORY_ID";
        private static final String COLUMN_WORD_TYPE = "WORD_TYPE";
    }

    public static class CategoryEntry implements BaseColumns {
        private static final String PRIMARY_KEY = "ID";
        private static final String CATEGORIES_TABLE = "CATEGORIES_TABLE";
        private static final String COLUMN_NAME = "NAME";
    }

    public class Word {

        public String word;
        public String article;
        public String translation;
        public int repetitions;
        public String type;
        public int categoryID;

        public Word(String word, String article, String translation, String type, int categoryID) {
            this.word = word;
            this.article = article;
            this.translation = translation;
            this.repetitions = 0;
            this.type = type;
            this.categoryID = categoryID;
        }
    }

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String createCategoriesTable = "CREATE TABLE " + CategoryEntry.CATEGORIES_TABLE +
                " (" + CategoryEntry.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                CategoryEntry.COLUMN_NAME + " TEXT);";


        final String createWordsTable = "CREATE TABLE " + WordEntry.WORDS_TABLE +
                " (" + WordEntry.PRIMARY_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WordEntry.COLUMN_WORD + " TEXT, " +
                WordEntry.COLUMN_ARTICLE + " TEXT, " +
                WordEntry.COLUMN_TRANSLATION + " TEXT," +
                WordEntry.COLUMN_REPETITIONS + " INT," +
                WordEntry.COLUMN_WORD_TYPE + " TEXT, " +
                WordEntry.COLUMN_CATEGORY_ID + " FOREIGN_KEY REFERENCES " + CategoryEntry.CATEGORIES_TABLE + "(" + CategoryEntry.PRIMARY_KEY + "));"; // Not sure whether the key should be type int

        sqLiteDatabase.execSQL(createCategoriesTable);
        sqLiteDatabase.execSQL(createWordsTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public boolean addWord(String word, String article, String translation, String type, int categoryID) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(WordEntry.COLUMN_WORD, word);
        values.put(WordEntry.COLUMN_ARTICLE, article);
        values.put(WordEntry.COLUMN_TRANSLATION, translation);
        values.put(WordEntry.COLUMN_WORD_TYPE, type);
        values.put(WordEntry.COLUMN_CATEGORY_ID, categoryID);

        long ins = db.insert(WordEntry.WORDS_TABLE, null, values);
        if (ins == -1) // Return false if failed to insert values
            return false;
        return true;
    }

    public boolean addCategory(String name) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CategoryEntry.COLUMN_NAME, name);

        long ins = db.insert(CategoryEntry.CATEGORIES_TABLE, null, values);
        if (ins == -1) // Return false if failed to insert values
            return false;
        return true;
    }

    public int getCategoryID(String name) {
        SQLiteDatabase db = this.getReadableDatabase();

        final String[] projection = { CategoryEntry.PRIMARY_KEY };
        final String selection = CategoryEntry.COLUMN_NAME + " = ?";
        final String[] selectionArgs = { name };

        Cursor cursor = db.query(CategoryEntry.CATEGORIES_TABLE, projection, selection, selectionArgs, null, null, null);
        if (!cursor.moveToFirst()) // Return -1 if the category hasn't been found
            return -1;
        return cursor.getInt(0);
    }

    public Word getRandomWordFromCategory(int categoryID) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Projection (which columns will be returned to the cursor)
        String[] projection = {
                WordEntry.COLUMN_WORD,
                WordEntry.COLUMN_ARTICLE,
                WordEntry.COLUMN_TRANSLATION,
                WordEntry.COLUMN_WORD_TYPE
        };

        // Filter WHERE COLUMN_CATEGORY = 'category'
        String selection = WordEntry.COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = { Integer.toString(categoryID) };

        // Sort words in descending order
        String sortOrder = WordEntry.COLUMN_WORD + " DESC";

        // Query
        Cursor cursor = db.query(WordEntry.WORDS_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        // If no words are matched (category is empty) return an empty string
        if (!cursor.moveToFirst()) {
            return null;
        }

        // Otherwise get a random word from the list
        Random random = new Random();
        cursor.moveToPosition(random.nextInt(cursor.getCount()));

        Word word = new Word(cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_WORD)),
                cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_ARTICLE)),
                cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_TRANSLATION)),
                cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_ARTICLE)),
                cursor.getInt(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_CATEGORY_ID)));
        return word;
    }

    public ArrayList<String> getCategories() {
        SQLiteDatabase db = this.getReadableDatabase();

        // Query for all the categories
        final String query = "SELECT " + CategoryEntry.COLUMN_NAME + " FROM " + CategoryEntry.CATEGORIES_TABLE + ";";
        Cursor cursor = db.rawQuery(query, null);

        // Add them to the list and return
        ArrayList<String> categories = new ArrayList<>();
        while (cursor.moveToNext())
            categories.add(cursor.getString(cursor.getColumnIndexOrThrow(CategoryEntry.COLUMN_NAME)));
        return categories;
    }

    public ArrayList<Bundle> getWordsFromCategory(String categoryName) {
        SQLiteDatabase db = this.getReadableDatabase();

        // Projection (which columns will be returned to the cursor)
        String[] projection = {
                WordEntry.COLUMN_WORD,
                WordEntry.COLUMN_ARTICLE,
                WordEntry.COLUMN_TRANSLATION,
                WordEntry.COLUMN_WORD_TYPE
        };

        String categoryID = Integer.toString(getCategoryID(categoryName));

        String selection = WordEntry.COLUMN_CATEGORY_ID + " = ?";
        String[] selectionArgs = { categoryID };
        String sortOrder = WordEntry.COLUMN_WORD + " DESC";

        Cursor cursor = db.query(WordEntry.WORDS_TABLE,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder);

        ArrayList<Bundle> words = new ArrayList<>();
        while (cursor.moveToNext()) {
            Bundle newWord = new Bundle();
            newWord.putString("word", cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_WORD)));
            newWord.putString("article", cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_ARTICLE)));
            newWord.putString("translation", cursor.getString(cursor.getColumnIndexOrThrow(WordEntry.COLUMN_TRANSLATION)));

            words.add(newWord);
        }

        return words;
    }


    // TODO: Implement those methods
    /*
    public String getTranslationFromWord(String word) {
        return "";
    }

    public String getWordFromTranslation(String translation) {
        return "";
    }

    public boolean deleteWord() {
        return false;
    }

    public boolean deleteCategory() {
        return false;
    }
    */
}
