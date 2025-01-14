package dev.mobprog.techhub;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.Array;
import java.util.ArrayList;

import dev.mobprog.techhub.models.Article;
import dev.mobprog.techhub.models.Source;
import dev.mobprog.techhub.models.User;

public class HelperClass extends SQLiteOpenHelper {
    private static final String DATABASE = "DATABASE";
    private static final int VERSION = 4;
    public HelperClass(@Nullable Context context) {
        super(context, DATABASE, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table msBookmark(id INTEGER PRIMARY KEY AUTOINCREMENT, userEmail TEXT, sourceId TEXT, sourceName TEXT, author TEXT, title TEXT, description TEXT, url TEXT, urlToImage TEXT, publishedAt TEXT, content TEXT)");
        db.execSQL("create table msUser(id INTEGER PRIMARY KEY AUTOINCREMENT, email TEXT, password TEXT)");
    }
    public boolean register(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("email", user.getEmail());
        values.put("password", user.getPassword());

        long result = db.insert("msUser", null, values);
        db.close();

        return result != -1;
    }
    public boolean login(String email, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + "msUser" + " WHERE " +
                "email" + " = ? AND " + "password" + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{email, password});

        boolean exits = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exits;
    }
    public boolean isEmailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + "msUser" + " WHERE " + "email" + "=?";
        Cursor cursor = db.rawQuery(query, new String[]{email});

        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();

        return exists; // Return true if email exists
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists msBookmark");
        db.execSQL("drop table if exists msUser");
        onCreate(db);
    }
    public boolean insertBookmark(String sourceId, String sourceName, String author, String title, String description, String url, String urlToImage, String publishedAt, String content){
        User user = Session.getInstance().getUser();
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM MsBookmark WHERE url = ?", new String[]{url});
        if(cursor.moveToFirst()){
            cursor.close();
            db.close();
            return false;
        }
        ContentValues values = new ContentValues();
        values.put("userEmail", user.getEmail());
        values.put("sourceId", sourceId);
        values.put("sourceName", sourceName);
        values.put("author", author);
        values.put("title", title);
        values.put("description", description);
        values.put("url", url);
        values.put("urlToImage", urlToImage);
        values.put("publishedAt", publishedAt);
        values.put("content", content);
        db.insert("msBookmark", null, values);
        db.close();
        return true;

    }
//    public void insertUser
    public void deleteBookmark(String url) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM msBookmark WHERE url = " +"'"+url+"'");
        db.close();
    }
    @SuppressLint("Range")
    public ArrayList<Article> getArticles (String userEmail){
        SQLiteDatabase db = this.getWritableDatabase();
        @SuppressLint("Recycle") Cursor cursor = db.rawQuery("SELECT * FROM MsBookmark WHERE userEmail = ?",new String[]{userEmail});
        Article article;
        Source source;
        ArrayList<Article> articleList = new ArrayList<>();
        while (cursor.moveToNext()){
            source = new Source();
            article = new Article();
            article.setAuthor(cursor.getString(cursor.getColumnIndex("author")));
            article.setContent(cursor.getString(cursor.getColumnIndex("content")));
            article.setDescription(cursor.getString(cursor.getColumnIndex("description")));
            source.setId(cursor.getString(cursor.getColumnIndex("sourceId")));
            source.setName(cursor.getString(cursor.getColumnIndex("sourceName")));
            article.setSource(source);
            article.setUrl(cursor.getString(cursor.getColumnIndex("url")));
            article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
            article.setPublishedAt(cursor.getString(cursor.getColumnIndex("publishedAt")));
            article.setUrlToImage(cursor.getString(cursor.getColumnIndex("urlToImage")));
            articleList.add(article);
        }
        return articleList;
    }

}
