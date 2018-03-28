package com.project.nra;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.util.Log;
import android.widget.Toast;

import com.project.nra.news_lib.db.DBHelper;
import com.project.nra.news_lib.model.Constants;
import com.project.nra.news_lib.model.JsonUtils;
import com.project.nra.news_lib.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by hp.luong on 7/13/2016.
 */
public class DownloadTask extends AsyncTask<Void, Integer, ArrayList<News>> {
    private static final String TAG = DownloadTask.class.getSimpleName();

    public interface AsyncResponse {
        void processFinish(ArrayList<News> output);
    }

    public AsyncResponse mCallback = null;
    private PowerManager.WakeLock mWakeLock;
    private Context mContext;
    private ProgressDialog mProgressDialog;
    private String sourceNews = "";
    private DBHelper dbHelper;

    public DownloadTask(Context ctx, AsyncResponse callback, ProgressDialog dialog) {
        this.mContext = ctx;
        this.mCallback = callback;
        this.mProgressDialog = dialog;
        dbHelper = DBHelper.getInstance(ctx);
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute ");
        super.onPreExecute();
        // take CPU lock to prevent CPU from going off if the user
        // presses the power button during download
        PowerManager pm = (PowerManager) mContext.getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,
                getClass().getName());
        mWakeLock.acquire();
        mProgressDialog.show();
    }

    @Override
    protected ArrayList<News> doInBackground(Void... params) {
        Log.d(TAG, "doInBackground ");
        ArrayList<News> newsList =  downloadNews(Constants.SOURCE_QUERY);
//        Log.d(TAG, "doInBackground newsList size = " + newsList.size());
//        return getTitles(newsList, "cnn");
        return newsList;

        /*ArrayList<String> listNews = new ArrayList<String>();
        System.out.println("Testing...");
        String apiKey = "de2f02ca31e54052bf41b1ae7daceafa";
        String source = "techcrunch";
        String sourceQuery = "https://newsapi.org/v1/sources";
        String status = null;
        String query = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=3e22f2fcc1344975ae2b2e69379e2a6e";
        try {
            //DownloadSourceTask dsTask = new DownloadSourceTask(mContext, mHandler);
            //dsTask.execute(sourceQuery);

            String sourceResult = JsonUtils.getJsonResult(sourceQuery);
            //String sourceResult = "techcrunch";

            Log.d(TAG, "onProgressUpdate..sourceResult " + sourceResult);
            if (!sourceResult.equals("")) {
                List<String> sourceList = new ArrayList<String>();
                JSONObject sourceObject = new JSONObject(sourceResult.trim());
                JSONArray sourceArray = sourceObject.getJSONArray("sources");
                int sourceArrayLength = sourceArray.length();
                for (int i = 0; i < sourceArrayLength; i++) {
                    Log.d(TAG, "i= " + i);
                    publishProgress((int) (i * 100 / sourceArrayLength));
                    sourceList.add(sourceArray.getJSONObject(i).getString("id"));
                    System.out.println("Source : " + sourceArray.getJSONObject(i).getString("id"));
                    source = sourceArray.getJSONObject(i).getString("id");

                    query = "https://newsapi.org/v1/articles?source=" + source + "&apiKey=" + apiKey;

                    String result = JsonUtils.getJsonResult(query);
                    System.out.println("result = '" + result + "'");
                    try {
                        if (!result.equals("")) {
                            JSONObject jObject = new JSONObject(result.trim());

                            //listNews = new ArrayList<String>();
                            status = jObject.getString("status");
                            if (status.equals("ok")) {
                                JSONArray array = jObject.getJSONArray("articles");
                                for (int j = 0; j < array.length(); j++) {
                                    listNews.add(array.getJSONObject(j).getString("title"));
                                    System.out.println("\t News " + j + ": " + array.getJSONObject(j).getString("title"));
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Exception = " + e.toString());
                    }
                }
            }
            else {
                Log.d(TAG, "onProgressUpdate..sourceResult is NULL");
            }
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException = " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSONException = " + e.toString());
        }
        return listNews;*/
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
//        Log.d(TAG, "onProgressUpdate :" + sourceNews);
        super.onProgressUpdate(progress);
        // if we get here, length is known, now set indeterminate to false
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setMessage("Loading news from : " + sourceNews);
        mProgressDialog.setMax(100);
        mProgressDialog.setProgress(progress[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<News> list) {
        Log.d(TAG, "onPostExecute ");
        mWakeLock.release();
        mProgressDialog.dismiss();
        if (list != null) {
            Toast.makeText(mContext,"onPostExecute...size =" + list.size(), Toast.LENGTH_LONG).show();
            Log.d(TAG, "onPostExecute...size =" + list.size());
            mCallback.processFinish(list);
        }
        else {
            Toast.makeText(mContext,"onPostExecute...size is NULLL=", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onPostExecute...list is NULL");
        }
    }

    public ArrayList<String> getTitles(ArrayList<News> newsList, String source) {
        ArrayList<String> titleList = new ArrayList<String>();
        if (newsList.size() > 0) {
            for (News news: newsList) {
                if (news.getSource().equals(source)) {
                    Log.d(TAG, "loading from : " + source + " : "  + news.getTitle());
                    titleList.add(news.getTitle());
                }
            }
        }
        return titleList;
    }

    public ArrayList<News> downloadNews(String sourceQuery) {
        ArrayList<News> listNews = new ArrayList<News>();
        String source = "techcrunch";
        String status = null;
        String query = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=3e22f2fcc1344975ae2b2e69379e2a6e";

        String author;
        String title;
        String description;
        String url;
        String urlToImage;
        String publishedAt;
        byte[] newsImage;

        try {
            String sourceResult = JsonUtils.getJsonResult(sourceQuery);
            Log.d(TAG, "onProgressUpdate..sourceResult " + sourceResult);
            if (!sourceResult.equals("")) {
                List<String> sourceList = new ArrayList<String>();
                JSONObject sourceObject = new JSONObject(sourceResult.trim());
                JSONArray sourceArray = sourceObject.getJSONArray(Constants.SOURCES);
                int sourceArrayLength = sourceArray.length();
                for (int i = 0; i < sourceArrayLength; i++) {
                    publishProgress((int) (i * 100 / sourceArrayLength));
                    sourceList.add(sourceArray.getJSONObject(i).getString(Constants.SOURCE_ID));
//                    System.out.println("Source : " + sourceArray.getJSONObject(i).getString("id"));
                    sourceNews = sourceArray.getJSONObject(i).getString(Constants.SOURCE_ID);

                    query = Constants.PREFIX_SOURCE_QUERY + sourceNews + "&apiKey=" + Constants.API_KEY;

                    String result = JsonUtils.getJsonResult(query);
                    //Log.d(TAG, "result = '" + result + "'");
                    try {
                        if (!result.equals("")) {
                            JSONObject jObject = new JSONObject(result.trim());
                            status = jObject.getString(Constants.STATUS);
                            Log.d(TAG, "status = '" + status + "'");
                            if (status.equals(Constants.STATUS_OK)) {
                                JSONArray array = jObject.getJSONArray(Constants.ARTICLES);

                                for (int j = 0; j < array.length(); j++) {
                                    JSONObject obj = array.getJSONObject(j);
                                    Log.d(TAG, "obj = '" + obj + "'");
                                    if (obj != null) {
                                        author = obj.getString(Constants.AUTHOR);
                                        title = obj.getString(Constants.TITLE);
                                        description = obj.getString(Constants.DESCRIPTION);
                                        url = obj.getString(Constants.URL);
                                        Log.d(TAG, "add news: " + title);
                                        urlToImage = obj.getString(Constants.URL_IMAGE);
                                        publishedAt = obj.getString(Constants.PUBLISHED_AT);
                                        newsImage = downloadNewsImage(urlToImage);
                                        Log.d(TAG, "newsImage: " + newsImage);
                                        int count = dbHelper.getNewsCount();
                                        Log.d(TAG, "count = " + count);
                                        News aNews = new News(count + 1, sourceNews, author, title, description, url, urlToImage, publishedAt, newsImage);
                                        listNews.add(aNews);


                                        //update Database
                                        if (!dbHelper.isNewsExisted(url)) {
                                            Log.d(TAG, "Update to database: " + title);
                                            dbHelper.addNews(aNews);
                                        }
                                        else {
                                            Log.d(TAG, "News existed in DB: " + title);
                                        }
                                        //System.out.println("\t News " + j + ": " + array.getJSONObject(j).getString(Constants.TITLE));
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Exception = " + e.toString());
                    }
                }
            }
            else {
                Log.d(TAG, "onProgressUpdate..sourceResult is NULL");
            }
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException = " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSONException = " + e.toString());
        }
        return listNews;
    }

    private byte[] downloadNewsImage(String url) {
        Bitmap bmp = downloadImg(url);
        bmp = Bitmap.createScaledBitmap(bmp, 50, 50, true); //resize
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }

/*
    private byte[] downloadNewsImage(String url) {
        Bitmap img = null;
        ByteBuffer buffer = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            img = BitmapFactory.decodeStream(in);
            buffer = ByteBuffer.allocate(img.getByteCount());
            img.copyPixelsToBuffer(buffer);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            e.printStackTrace();
        }
        return buffer.array();
    }
*/

    private Bitmap downloadImg(String url) {
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }
}