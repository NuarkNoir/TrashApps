package com.nuark.mobile.trashapps.models;

import java.util.ArrayList;

/**
 * Created by Nuark with love on 07.05.2017.
 * Protected by QPL-1.0
 */

public class LApplication {

    private String title, androidVersion, topicLink, imageLink;
    private ArrayList<String> tagList;

    public LApplication(String title, String androidVersion, String topicLink, String imageLink, ArrayList<String> tagList) {
        this.title = title;
        this.androidVersion = androidVersion;
        this.topicLink = topicLink;
        this.imageLink = imageLink;
        this.tagList = tagList;
    }

    public String getTitle() {
        return title;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public String getTopicLink() {
        return topicLink;
    }

    public String getImageLink() {
        return imageLink;
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }
}
