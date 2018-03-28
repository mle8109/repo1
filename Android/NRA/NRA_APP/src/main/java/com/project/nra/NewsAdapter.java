package com.project.nra;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.nra.news_lib.model.News;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by hp.luong on 3/24/2017.
 */

public class NewsAdapter extends ArrayAdapter<News> {
    private ArrayList<News> newsList;

    public NewsAdapter(Context context, ArrayList<News> news) {
        super(context, 0, news);
        this.newsList = news;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News news = newsList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_listview, parent, false);
        }
        if (news != null) {
            TextView txtNews = (TextView) convertView.findViewById(R.id.txtNews);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.iconImage);
            TextView txtScore = (TextView) convertView.findViewById(R.id.txtScore);
            if (txtNews != null) {
                txtNews.setText(news.getTitle());
//                new DownloadImageTask(imageView).execute(news.getUrlToImage());
                Bitmap bmpImage = BitmapFactory.decodeByteArray(news.getImage(), 0, news.getImage().length);
                imageView.setImageBitmap(bmpImage);
                txtScore.setText("1.0");
            }

        }
        return convertView;
    }
}

class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.d("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }
}
