package com.nuark.mobile.trashapps.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.nuark.mobile.trashapps.MainActivity;

import java.util.ArrayList;

import xyz.nuark.trashbox.AppsPage;
import xyz.nuark.trashbox.Globals;
import xyz.nuark.trashbox.models.App;

public class AppListLoader extends AsyncTaskLoader<ArrayList<App>> {

	private final String TAG = getClass().getSimpleName();
	private String tag = "";
	boolean wt = false;

	public AppListLoader(Context context) {
		super(context);
	}

	@Override
	public ArrayList<App> loadInBackground() {
		Log.d(TAG, "loadInBackground");
		if (MainActivity.instance.worker == null){
			MainActivity.instance.worker = new AppsPage();
		}
		if (tag.length() > 0 && !wt){
			MainActivity.instance.worker = new AppsPage("https://trashbox.ru" + Globals.Tagger.getTag(tag));
			wt = true;
			tag = "";
		}
		ArrayList<App> aList = MainActivity.instance.worker.getNextAppsPage();
		Log.d(TAG, "loadInBackground: aList.len = " + aList.size());
		return aList;
	}

	@Override
	public void forceLoad() {
		Log.d(TAG, "forceLoad");
		super.forceLoad();
	}

	@Override
	protected void onStartLoading() {
		super.onStartLoading();
		Log.d(TAG, "onStartLoading");
		forceLoad();
		MainActivity.instance.lv.startLoading();
	}

	@Override
	protected void onStopLoading() {
		super.onStopLoading();
		Log.d(TAG, "onStopLoading");
		MainActivity.instance.lv.stopLoading();
	}

	@Override
	public void deliverResult(ArrayList<App> data) {
		Log.d(TAG, "deliverResult");
		super.deliverResult(data);
	}

	public void loadTag(String tag){
		this.tag = tag;
	}
}