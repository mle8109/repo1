package com.project.nra;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.project.nra.news_lib.db.DBHelper;
import com.project.nra.news_lib.model.News;

import java.util.ArrayList;

public class MainActivity extends Activity implements DownloadTask.AsyncResponse {
    private static final String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private ListView listView;
    private Spinner sourceSpinner;
    private Context mContext;
    private boolean isDownloaded = false;
    private DBHelper dbHelper;

    private NewsAdapter newsAdapter;
    private ArrayAdapter<String> spinnerArrayAdapter;

    private TextView txtAppTitle;
    DownloadNewsService mService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = getApplicationContext();
        dbHelper = DBHelper.getInstance(mContext);
        Log.d(TAG, "DB count = " + dbHelper.getNewsCount());
        Log.d(TAG, "DB size = " + dbHelper.getDBSize());
        Log.d(TAG, "isDownloaded = " + isDownloaded);

        txtAppTitle = (TextView) findViewById(R.id.txtAppTitle);
        txtAppTitle.setText("NRA app");
        sourceSpinner = (Spinner) findViewById(R.id.spinnerNewsSource);
        sourceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newsSource = parent.getItemAtPosition(position).toString().trim();
                loadNews(newsSource);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        listView = (ListView) findViewById(R.id.list);
        listView.setTextFilterEnabled(true);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent webIntent = new Intent(getApplicationContext(), WebViewActivity.class);
                News aNews = (News) parent.getAdapter().getItem(position);

                Toast.makeText(MainActivity.this, aNews.getDescription(), Toast.LENGTH_LONG).show();
//                webIntent.putExtra("url", aNews.getUrl());
//                startActivity(webIntent);
            }
        });


        if (!isDownloaded) {
            new AlertDialog.Builder(MainActivity.this)
                .setTitle("Download news")
                .setMessage("Do you want to download news?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        mProgressDialog = new ProgressDialog(MainActivity.this);
                        mProgressDialog.setMessage("Loading news...");
                        mProgressDialog.setIndeterminate(true);
                        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        mProgressDialog.setCancelable(true);
                        mProgressDialog.setCanceledOnTouchOutside(false);
                        DownloadTask downloadTask = new DownloadTask(getApplicationContext(), MainActivity.this, mProgressDialog);
                        downloadTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        isDownloaded = true;

                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d(TAG, "Number of news: " + dbHelper.getAllNews().size());
                        loadNews(dbHelper.getNewsSources().get(0));
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        } else {
            Log.d(TAG, "news: " + dbHelper.getNewsSources().get(0));
//            listView.setAdapter(new ArrayAdapter<News>(MainActivity.this, R.layout.activity_listview, dbHelper.getNewsBySource("techcrunch")));

//            newsAdapter = new NewsAdapter(mContext, (ArrayList) dbHelper.getNewsBySource("cnn"));
//            listView.setAdapter(newsAdapter);
            loadNews(dbHelper.getNewsSources().get(0));
        }
    }

    private void loadNews(String source) {
        Log.d(TAG, "loadNews from : " + source + ". contains: " + dbHelper.getNewsBySource(source).size() + " news");

        if (spinnerArrayAdapter == null) {
            spinnerArrayAdapter = new ArrayAdapter<String>(MainActivity.this, R.layout.activity_news_source, dbHelper.getNewsSources());
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sourceSpinner.setAdapter(spinnerArrayAdapter);
        }
        else {
            spinnerArrayAdapter.clear();
            spinnerArrayAdapter.addAll(dbHelper.getNewsSources());
            spinnerArrayAdapter.notifyDataSetChanged();
        }

        if (newsAdapter == null) {
            newsAdapter = new NewsAdapter(mContext, (ArrayList) dbHelper.getNewsBySource(source));
            listView.setAdapter(newsAdapter);
        }
        else {
            newsAdapter.clear();
            newsAdapter.addAll(dbHelper.getNewsBySource(source));
            newsAdapter.notifyDataSetChanged();
            listView.invalidate();
        }
    }

    @Override
    public void processFinish(ArrayList<News> news) {
        Log.d(TAG, "processFinish: " + news);
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (news.size() > 0) {
                    if (dbHelper.getNewsSources().size() > 0) {
                        loadNews(dbHelper.getNewsSources().get(0));
                    }
                    else {
                        loadNews("cnn");
                    }
                }
            }
        });
    }
}

