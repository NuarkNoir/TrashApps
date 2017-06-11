package com.nuark.mobile.trashapps.utils;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Nuark with love on 07.05.2017.
 * Protected by QPL-1.0
 */

public class ArrayUtil
{
    public static ArrayList<String> convert(JSONArray jArr)
    {
        ArrayList<String> list = new ArrayList<>();
        try {
            for (int i=0, l=jArr.length(); i<l; i++){
                list.add(jArr.getString(i));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static JSONArray convert(Collection<Object> list)
    {
        return new JSONArray(list);
    }
}