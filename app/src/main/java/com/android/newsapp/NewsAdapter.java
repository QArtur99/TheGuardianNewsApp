package com.android.newsapp;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ART_F on 2017-05-25.
 */

public class NewsAdapter extends ArrayAdapter<NewsObject> {

    private List<NewsObject> newsList = new ArrayList<>();

    public NewsAdapter(Activity context, List<NewsObject> newsList) {
        super(context, 0, newsList);
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        NewsObject newsObject = getItem(position);

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.sectionName.setText(newsObject.sectionName);
        holder.imageView.setImageBitmap(newsObject.thumbNail);
        holder.title.setText(newsObject.headLine);
        holder.publishedDate.setText(newsObject.published);

        return convertView;
    }

    public void setBooks(List<NewsObject> data) {
        newsList.addAll(data);
        notifyDataSetChanged();
    }

    static class ViewHolder {
        @BindView(R.id.sectionName) TextView sectionName;
        @BindView(R.id.image) ImageView imageView;
        @BindView(R.id.title) TextView title;
        @BindView(R.id.publishedDate) TextView publishedDate;


        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
