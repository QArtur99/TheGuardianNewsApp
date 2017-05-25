package com.android.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.content.AsyncTaskLoader;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ART_F on 2017-05-25.
 */

public class NewsLoader extends AsyncTaskLoader<List<NewsObject>> {

    private List<NewsObject> newsList = new ArrayList<>();
    private String section;

    public NewsLoader(Context context, String section) {
        super(context);
        this.section = section;
    }

    @Override
    public List<NewsObject> loadInBackground() {

        try {
            getBooksList();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return newsList;
    }

    private void getBooksList() throws IOException, JSONException {
        String jsonString = TheGuardianComAPI.getBooksString(section);
        JSONObject jsonObject = new JSONObject(jsonString);
        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONArray("results");
        int newsAmount = jsonArray.length();
        for(int i = 0; newsAmount > i; i++ ){
            JSONObject jsonData =  jsonArray.getJSONObject(i);

            String thumbNailUrl = (String) jsonData.getJSONObject("fields").get("thumbnail");
            URL url = new URL(thumbNailUrl);
            Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());

            String sectionName = jsonData.getString("sectionName");
            String headLine = jsonData.getJSONObject("fields").getString("headline");
            String date = jsonData.getString("webPublicationDate");
            long sec = new DateTime(date).getMillis();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd  HH:mm");
            String published = String.valueOf(dateFormat.format(sec));
            String webUrl = jsonData.getString("webUrl");

            newsList.add(new NewsObject(sectionName, headLine, published, webUrl, bmp));
        }
    }
}
