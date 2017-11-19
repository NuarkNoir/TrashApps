package com.nuark.mobile.trashapps.loaders;

import android.os.AsyncTask;
import android.view.View;
import android.widget.Toast;

import com.nuark.mobile.trashapps.MainActivity;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import xyz.nuark.trashbox.AppsPage;
import xyz.nuark.trashbox.models.App;
import xyz.nuark.trashbox.utils.Enumerators;

public class AppListLoader extends AsyncTask<Object, Void, Object> {

	private ArrayList<App> apps = new ArrayList<>();
	public Enumerators.Sort sort = Enumerators.Sort.Recomendation;
	public Enumerators.Type type = Enumerators.Type.Apps;
	public String link = "";

	public AppListLoader() {

	}

	public AppListLoader(Enumerators.Type type) {
		this.type = type;
	}

	public AppListLoader(Enumerators.Sort sort, Enumerators.Type type) {
		this.sort = sort;
		this.type = type;
	}

	public AppListLoader(String link) {
		this.link = link;
	}

	public AppListLoader(Enumerators.Sort sort, String link) {
		this.sort = sort;
		this.link = link;
	}

	public AppListLoader setSorting(Enumerators.Sort sort){
		if (link.length() > 0)
			return new AppListLoader(sort, this.type);
		else
			return new AppListLoader(sort, this.link);
	}

	@Override
	protected void onPreExecute() {
		MainActivity.instance.lv.startLoading();
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Object... p1)
	{
		System.out.println("Statring background apps list loading task...");
		System.out.println(sort);
		System.out.println(type);
		System.out.println(link);
		try
		{
			System.out.println("Checking...");
			AppsPage worker;
			if (MainActivity.instance.appsPage == null){
				System.out.println("Creating new worker...");
				if (link.length() > 0)
					worker = new AppsPage(link, sort);
				else
					worker = new AppsPage(type, sort);
				MainActivity.instance.appsPage = worker;
			} else {
				System.out.println("Using existing worker...");
				worker = MainActivity.instance.appsPage;
			}
			this.apps = worker.getNextAppsPage();
			return "success";
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "error\n" + e.getLocalizedMessage();
		}
	}

	@Override
	protected void onPostExecute(Object result)
	{
		super.onPostExecute(result);
		MainActivity instance = MainActivity.instance;
		instance.lv.addAll(apps);
		instance.mainContent.setVisibility(View.VISIBLE);
		if (result.toString().contains("error")){
			Toasty.error(instance, result.toString().replace("error", "Ошибка!"), Toast.LENGTH_LONG, true).show();
		} else if (result.toString().contains("warning")){
			Toasty.error(instance, result.toString().replace("warning", "Предупреждение!"), Toast.LENGTH_LONG, true).show();
		}
		instance.lv.stopLoading();
	}
}
