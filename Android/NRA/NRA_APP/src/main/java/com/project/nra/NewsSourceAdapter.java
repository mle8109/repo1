package com.project.nra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.nra.news_lib.model.News;

import java.util.ArrayList;

/**
 * Created by hp.luong on 3/24/2017.
 */

public class NewsSourceAdapter extends ArrayAdapter<News> {
    private ArrayList<News> newsList;


    public NewsSourceAdapter(Context context, ArrayList<News> news) {
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

            if (txtNews != null) {
                txtNews.setText(news.getTitle());
                }
        }
        return convertView;
    }
}
