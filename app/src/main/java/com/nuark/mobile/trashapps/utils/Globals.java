package com.nuark.mobile.trashapps.utils;

/**
 * Created by Nuark with love on 19.07.2017.
 * Protected by QPL-1.0
 */

public class Globals {
    private static String currentUrl = com.nuark.trashbox.Globals.Statics.getProgsUrl();

    public static String getCurrentUrl() {
        return currentUrl;
    }

    public static void setCurrentUrl(String url) {
        currentUrl = url;
    }
}
