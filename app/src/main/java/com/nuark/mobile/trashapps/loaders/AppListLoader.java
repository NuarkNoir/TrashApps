package com.nuark.mobile.trashapps.loaders;

import android.app.Activity;
import android.os.AsyncTask;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nuark.mobile.trashapps.AppAdapter;
import com.nuark.mobile.trashapps.MainActivity;
import com.nuark.mobile.trashapps.models.LApplication;
import com.nuark.mobile.trashapps.utils.Globals;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import es.dmoral.toasty.Toasty;

public class AppListLoader extends AsyncTask<Object, Void, Object> {

	String offset = "0";
	Document doc;
	ListView lv;
	Activity act;
	TextView pagesShw;
	LinearLayout mainContent;
	LinearLayout loadingNotification;
	ArrayList<LApplication> apps = new ArrayList<>();
	private String url = Globals.getCurrentUrl();

	public AppListLoader(ListView lv, Activity act, LinearLayout loadingNotification, LinearLayout mainContent, TextView pagesShw)
	{
		this.lv = lv;
		this.act = act;
		this.loadingNotification = loadingNotification;
		this.mainContent = mainContent;
		this.pagesShw = pagesShw;
	}

	public AppListLoader(ListView lv, Activity act, LinearLayout loadingNotification, LinearLayout mainContent, TextView pagesShw, String offset)
	{
		this.lv = lv;
		this.act = act;
		this.loadingNotification = loadingNotification;
		this.mainContent = mainContent;
		this.pagesShw = pagesShw;
		this.offset = offset;
		url = url + "page_topics/" + offset + "/";
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
			String regex = "(https:)(.*?)(.png)";
			Pattern pattern = Pattern.compile(regex);
			doc = Jsoup.connect(url).get();
			MainActivity.setLastpage(doc.body().select(".span_navigator_pages .span_item_active").first().text());
			Elements topics = doc.select("div.div_content_cat_topics div.div_topic_cat_content");
			for (Element topic : topics){
				String title = topic.select("span.div_topic_tcapt_content").first().text();
				String androver = topic.select("span.div_topic_cat_tag_os_android").first().text();
				String toplink = topic.select("div.div_topic_cat_content a.a_topic_content").first().attr("href");
				String icolink = topic.select("div.div_topic_content_icon").first().attr("style");
				Matcher matcher = pattern.matcher(icolink);
				matcher.find();
				icolink = matcher.group(0);
				ArrayList<String> tags = new ArrayList<>();
				for (Element el : topic.select("div.div_topic_cat_tags a")){
					tags.add(el.text());
				}
				apps.add(new LApplication(title, androver, toplink, icolink, tags));
			}
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
