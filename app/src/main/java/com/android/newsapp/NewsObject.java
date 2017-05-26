package com.android.newsapp;

import android.graphics.Bitmap;

/**
 * Created by ART_F on 2017-05-25.
 */

public class NewsObject {
    final String sectionName;
    final String headLine;
    final String published;
    final String webUrl;
    final Bitmap thumbNail;

    NewsObject(String sectionName, String headLine, String published, String webUrl, Bitmap thumbNail){
        this.sectionName = sectionName;
        this.headLine = headLine;
        this.published = published;
        this.webUrl = webUrl;
        this.thumbNail = thumbNail;
    }
}
