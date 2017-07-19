package com.nuark.mobile.trashapps.utils;

/**
 * Created by Nuark with love on 19.07.2017.
 * Protected by QPL-1.0
 */

public class Globals {
    public static String TPU = "https://trashbox.ru/public/progs/tags/os_android/";
    public static String TGU = "https://trashbox.ru/public/games/tags/os_android/";
    private static String currentUrl = TPU;

    public static String getCurrentUrl() {
        return currentUrl;
    }

    public static void setCurrentUrl(Section s) {
        switch (s){
            case Games:
                currentUrl = TGU;
                break;
            case Programs:
                currentUrl =  TPU;
                break;
        }
    }

    public enum Section{
        Programs,
        Games
    }
}
