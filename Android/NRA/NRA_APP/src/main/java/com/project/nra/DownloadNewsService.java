package com.project.nra;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.project.nra.news_lib.model.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by hp.luong on 2/16/2017.
 */

public class DownloadNewsService extends Service {
    private final static String TAG = DownloadNewsService.class.getSimpleName();
    private PendingIntent pendingIntent;
    private final IBinder mBinder = new LocalBinder();

    public interface ResultCallback{
        public void updateNews(String news);
        public void update(ArrayList<String> news);
    }

    private ResultCallback callback;

    @Override
    public void onCreate() {
        super.onCreate();
       Log.d(TAG, "onCreated...service started");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand...service started");
        //DownloadTask dTask = new DownloadTask(this, this, new ProgressDialog(MainActivity.this));

//        test();
        return START_STICKY;
    }

    public class LocalBinder extends Binder {
        public DownloadNewsService getService() {
            return DownloadNewsService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: " + mBinder);
        return mBinder;
    }

    public void registerCallback(ResultCallback cb) {
        this.callback = cb;
        Log.d(TAG, "callback registered: " + callback);
    }

    public ArrayList<String> getListNews() {
        ArrayList<String> listNews = new ArrayList<String>();
        System.out.println("Testing...");
        String apiKey = "de2f02ca31e54052bf41b1ae7daceafa";
        String source = "techcrunch";
        String sourceQuery = "https://newsapi.org/v1/sources";
        String status = null;
        String query = "https://newsapi.org/v1/articles?source=techcrunch&sortBy=top&apiKey=3e22f2fcc1344975ae2b2e69379e2a6e";
        try {

//            DownloadSourceTask dsTask = new DownloadSourceTask(getApplicationContext(), mHandler);
//            dsTask.execute(sourceQuery);

            String sourceResult = JsonUtils.getJsonResult(sourceQuery);
            if (!sourceResult.equals("")) {
                List<String> sourceList = new ArrayList<String>();
                JSONObject sourceObject = new JSONObject(sourceResult.trim());
                JSONArray sourceArray = sourceObject.getJSONArray("sources");
                for (int i = 0; i < sourceArray.length(); i++) {
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
        } catch (MalformedURLException e) {
            System.out.println("MalformedURLException = " + e.toString());
        } catch (JSONException e) {
            System.out.println("JSONException = " + e.toString());
        }
        System.out.println("listNews size = '" + listNews.size() + "'");
        Log.d(TAG, "listNews size = '" + listNews.size() + "'");
        callback.update(listNews);
        return listNews;
    }
}
