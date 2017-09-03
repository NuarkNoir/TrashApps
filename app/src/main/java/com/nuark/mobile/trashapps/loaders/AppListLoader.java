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
import com.softw4re.views.InfiniteListView;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class AppListLoader extends AsyncTask<Object, Void, Object> {

	ArrayList<App> apps = new ArrayList<>();
	private String url = Globals.getCurrentUrl();

	public AppListLoader() {  }

	@Override
	protected void onPreExecute() {
		MainActivity.instance.lv.startLoading();
		super.onPreExecute();
	}

	@Override
	protected Object doInBackground(Object... p1)
	{
		try
		{
			MainActivity.setLastpage(new GetLatestPage().Get(url));
			apps = new AppsLoader(url + "page_topics/" + MainActivity.getCurrentpage() + "/", MainActivity.sortingType).Load();
			MainActivity.currentpage = String.valueOf(Integer.parseInt(MainActivity.getCurrentpage())-1);
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
		MainActivity.p_comparer();
		String tmp = MainActivity.getCurrentpage() + "/" + MainActivity.getLastpage();
		instance.pagesShw.setText(tmp);
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
