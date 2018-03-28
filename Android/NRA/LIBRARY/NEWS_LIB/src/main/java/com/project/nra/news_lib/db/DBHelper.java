package com.project.nra.news_lib.db;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.project.nra.news_lib.model.News;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp.luong on 3/8/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "news.db";
    private static final String TABLE_NEWS = "news";

    private static final String COLUMN_NEWS_ID = "id";
    private static final String COLUMN_NEWS_SOURCE = "source";
    private static final String COLUMN_NEWS_AUTHOR = "author";
    private static final String COLUMN_NEWS_TITLE = "title";
    private static final String COLUMN_NEWS_DESC = "description";
    private static final String COLUMN_NEWS_URL = "url";
    private static final String COLUMN_NEWS_URLTOIMAGE = "url_to_image";
    private static final String COLUMN_NEWS_PUBLISHEDAT = "published_at";
    private static final String COLUMN_NEWS_IMAGE = "news_image";

    private static final int INDEX_COLUMN_NEWS_ID = 0;
    private static final int INDEX_COLUMN_NEWS_SOURCE = 1;
    private static final int INDEX_COLUMN_NEWS_AUTHOR = 2;
    private static final int INDEX_COLUMN_NEWS_TITLE = 3;
    private static final int INDEX_COLUMN_NEWS_DESC = 4;
    private static final int INDEX_COLUMN_NEWS_URL = 5;
    private static final int INDEX_COLUMN_NEWS_URLTOIMAGE = 6;
    private static final int INDEX_COLUMN_NEWS_PUBLISHEDAT = 7;
    private static final int INDEX_COLUMN_NEWS_IMAGE = 8;

    public static DBHelper dbHelper;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelper == null) {
            dbHelper = new DBHelper(context);
        }
        return dbHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate");
        String CREATE_NEWS_TABLE = "CREATE TABLE " + TABLE_NEWS + "("
                + COLUMN_NEWS_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_NEWS_SOURCE + " TEXT,"
                + COLUMN_NEWS_AUTHOR + " TEXT,"
                + COLUMN_NEWS_TITLE + " TEXT,"
                + COLUMN_NEWS_DESC + " TEXT,"
                + COLUMN_NEWS_URL + " TEXT,"
                + COLUMN_NEWS_URLTOIMAGE + " TEXT,"
                + COLUMN_NEWS_PUBLISHEDAT + " TEXT,"
                + COLUMN_NEWS_IMAGE + " BLOB" + ");";
        db.execSQL(CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NEWS);

        // Create tables again
        onCreate(db);
    }

    public void addNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_ID, news.getId());
        values.put(COLUMN_NEWS_SOURCE, news.getSource());
        values.put(COLUMN_NEWS_AUTHOR, news.getAuthor());
        values.put(COLUMN_NEWS_TITLE, news.getTitle());
        values.put(COLUMN_NEWS_DESC, news.getDescription());
        values.put(COLUMN_NEWS_URL, news.getUrl());
        values.put(COLUMN_NEWS_URLTOIMAGE, news.getUrlToImage());
        values.put(COLUMN_NEWS_PUBLISHEDAT, news.getPublishedAt());
        values.put(COLUMN_NEWS_IMAGE, news.getImage());

        db.insert(TABLE_NEWS, null, values);
        db.close();
    }

    public boolean isNewsExisted(String url) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWS, new String[] { COLUMN_NEWS_ID,
                        COLUMN_NEWS_SOURCE, COLUMN_NEWS_AUTHOR, COLUMN_NEWS_TITLE, COLUMN_NEWS_DESC, COLUMN_NEWS_URL,
                        COLUMN_NEWS_URLTOIMAGE, COLUMN_NEWS_PUBLISHEDAT, COLUMN_NEWS_IMAGE}, COLUMN_NEWS_URL + "=?",
                new String[] {url}, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            return true;
        }
        return false;
    }

    public News getNews(int newsId) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NEWS, new String[] { COLUMN_NEWS_ID,
                        COLUMN_NEWS_SOURCE, COLUMN_NEWS_AUTHOR, COLUMN_NEWS_TITLE, COLUMN_NEWS_DESC, COLUMN_NEWS_URL,
                        COLUMN_NEWS_URLTOIMAGE, COLUMN_NEWS_PUBLISHEDAT, COLUMN_NEWS_IMAGE}, COLUMN_NEWS_ID + "=?",
                new String[] { String.valueOf(newsId) }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            News news = new News(Integer.parseInt(cursor.getString(INDEX_COLUMN_NEWS_ID)),
                    cursor.getString(INDEX_COLUMN_NEWS_SOURCE), cursor.getString(INDEX_COLUMN_NEWS_AUTHOR), cursor.getString(INDEX_COLUMN_NEWS_TITLE), cursor.getString(INDEX_COLUMN_NEWS_DESC),
                    cursor.getString(INDEX_COLUMN_NEWS_URL), cursor.getString(INDEX_COLUMN_NEWS_URLTOIMAGE), cursor.getString(INDEX_COLUMN_NEWS_PUBLISHEDAT), cursor.getBlob(INDEX_COLUMN_NEWS_IMAGE));
            return news;
        }
        return null;
    }

    public List<News> getAllNews() {
        List<News> newsList = new ArrayList<News>();
        String selectQuery = "SELECT  * FROM " + TABLE_NEWS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(Integer.parseInt(cursor.getString(INDEX_COLUMN_NEWS_ID)));
                news.setSource(cursor.getString(INDEX_COLUMN_NEWS_SOURCE));
                news.setAuthor(cursor.getString(INDEX_COLUMN_NEWS_AUTHOR));
                news.setTitle(cursor.getString(INDEX_COLUMN_NEWS_TITLE));
                news.setDescription(cursor.getString(INDEX_COLUMN_NEWS_DESC));
                news.setUrl(cursor.getString(INDEX_COLUMN_NEWS_URL));
                news.setUrlToImage(cursor.getString(INDEX_COLUMN_NEWS_URLTOIMAGE));
                news.setPublishedAt(cursor.getString(INDEX_COLUMN_NEWS_PUBLISHEDAT));
                news.setImage(cursor.getBlob(INDEX_COLUMN_NEWS_IMAGE));

                newsList.add(news);
            } while (cursor.moveToNext());
        }

        return newsList;
    }

    public ArrayList<String> getNewsTitleBySource(String source) {
        ArrayList<String> newsTitleList = new ArrayList<String>();
        List<News> newsList = getNewsBySource(source);
        if (newsList.size() > 0) {
            for (News news: newsList) {
                newsTitleList.add(news.getTitle());
            }
        }
        return newsTitleList;
    }

    public ArrayList<String> getNewsSources() {
        Log.d(TAG, "getNewsSources... news count = " + getNewsCount());
        ArrayList<String> newsSourceList = new ArrayList<String>();
        SQLiteDatabase db = this.getReadableDatabase();

        String countQuery = "SELECT DISTINCT source FROM " + TABLE_NEWS + ";";
        Cursor cursor = db.rawQuery(countQuery, null);

        if (cursor.moveToFirst()) {
            do {
                newsSourceList.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        Log.d(TAG, "newsSourceList = " + newsSourceList.size());
        return newsSourceList;
    }

    public List<News> getNewsBySource(String source) {
        List<News> newsList = new ArrayList<News>();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(TABLE_NEWS, new String[] { COLUMN_NEWS_ID,
                        COLUMN_NEWS_SOURCE, COLUMN_NEWS_AUTHOR, COLUMN_NEWS_TITLE, COLUMN_NEWS_DESC, COLUMN_NEWS_URL,
                        COLUMN_NEWS_URLTOIMAGE, COLUMN_NEWS_PUBLISHEDAT, COLUMN_NEWS_IMAGE}, COLUMN_NEWS_SOURCE + "=?",
                new String[] {source}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(Integer.parseInt(cursor.getString(INDEX_COLUMN_NEWS_ID)));
                news.setSource(cursor.getString(INDEX_COLUMN_NEWS_SOURCE));
                news.setAuthor(cursor.getString(INDEX_COLUMN_NEWS_AUTHOR));
                news.setTitle(cursor.getString(INDEX_COLUMN_NEWS_TITLE));
                news.setDescription(cursor.getString(INDEX_COLUMN_NEWS_DESC));
                news.setUrl(cursor.getString(INDEX_COLUMN_NEWS_URL));
                news.setUrlToImage(cursor.getString(INDEX_COLUMN_NEWS_URLTOIMAGE));
                news.setPublishedAt(cursor.getString(INDEX_COLUMN_NEWS_PUBLISHEDAT));
                news.setImage(cursor.getBlob(INDEX_COLUMN_NEWS_IMAGE));
                newsList.add(news);
            } while (cursor.moveToNext());
        }

        return newsList;
    }

    public void removeNews(News news) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NEWS, COLUMN_NEWS_ID + " = ?",
                new String[] { String.valueOf(news.getId()) });
        db.close();
    }

    public int updateNews (News news) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NEWS_ID, news.getId());
        values.put(COLUMN_NEWS_SOURCE, news.getSource());
        values.put(COLUMN_NEWS_AUTHOR, news.getAuthor());
        values.put(COLUMN_NEWS_TITLE, news.getTitle());
        values.put(COLUMN_NEWS_DESC, news.getDescription());
        values.put(COLUMN_NEWS_URL, news.getUrl());
        values.put(COLUMN_NEWS_URLTOIMAGE, news.getUrlToImage());
        values.put(COLUMN_NEWS_PUBLISHEDAT, news.getPublishedAt());
        values.put(COLUMN_NEWS_IMAGE, news.getImage());

        return db.update(TABLE_NEWS, values, COLUMN_NEWS_ID + " = ?",
                new String[] { String.valueOf(news.getId()) });
    }

    public int getNewsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NEWS + ";";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();

        return count;
    }

    public long getDBSize() {
        SQLiteDatabase db = this.getReadableDatabase();
        return new File(db.getPath()).length();
    }
}
