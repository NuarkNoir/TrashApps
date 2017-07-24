package com.nuark.mobile.trashapps.loaders;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuark.mobile.trashapps.AppAdapter;
import com.nuark.mobile.trashapps.MainActivity;
import com.nuark.mobile.trashapps.utils.Globals;
import com.nuark.trashbox.api.AppsLoader;
import com.nuark.trashbox.models.App;
import com.nuark.trashbox.utils.GetLatestPage;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AppListLoader extends AsyncTask<Object, Void, Object> {

	String offset = "0";
	ListView lv;
	Activity act;
	TextView pagesShw;
	LinearLayout mainContent;
	LinearLayout loadingNotification;
	ArrayList<App> apps = new ArrayList<>();
	private String url = Globals.getCurrentUrl();

	public AppListLoader(ListView lv, Activity act, LinearLayout loadingNotification, LinearLayout mainContent, TextView pagesShw)
	{
		this.lv = lv;
		this.act = act;
		this.loadingNotification = loadingNotification;
		this.mainContent = mainContent;
		this.pagesShw = pagesShw;
	}

	@Override
	protected void onPreExecute() {
		loadingNotification.setVisibility(View.VISIBLE);
		mainContent.setVisibility(View.GONE);
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Object... p1)
	{
		try
		{
			MainActivity.setLastpage(new GetLatestPage().Get(url));
			apps = new AppsLoader(url + "page_topics/" + MainActivity.getCurrentpage() + "/", MainActivity.sortingType).Load();
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
		MainActivity.p_comparer();
		String tmp = MainActivity.getCurrentpage() + "/" + MainActivity.getLastpage();
		pagesShw.setText(tmp);
		lv.setAdapter(new AppAdapter(act, apps));
		loadingNotification.setVisibility(View.GONE);
		mainContent.setVisibility(View.VISIBLE);
		if (result.toString().contains("error")){
			Toasty.error(act, result.toString().replace("error", "Ошибка!"), Toast.LENGTH_LONG, true).show();
		} else if (result.toString().contains("warning")){
			Toasty.error(act, result.toString().replace("warning", "Предупреждение!"), Toast.LENGTH_LONG, true).show();
		}
		super.onPostExecute(result);
	}
}
