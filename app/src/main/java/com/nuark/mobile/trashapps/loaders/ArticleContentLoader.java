package com.nuark.mobile.trashapps.loaders;

import android.app.Application;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Nuark with love on 09.05.2017.
 * Protected by QPL-1.0
 */

public class ArticleContentLoader extends AsyncTask<Object, Void, Object> {

    private String url;
    private TextView articleText;
    private CarouselView carouselView;
    private ArrayList<String> imagesList = new ArrayList<>();
    private Application context;

    public ArticleContentLoader(String url, TextView articleText, CarouselView carouselView, Application context) {
        this.url = url;
        this.articleText = articleText;
        this.carouselView = carouselView;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            Document d = Jsoup.connect(url).get();
            for (Element img : d.select("div.div_image_screenshot a")) {
                imagesList.add(img.attr("href"));
                System.out.println(img.attr("href"));
            }
            return d.select(".div_text_content").html();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

    @Override
    protected void onPostExecute(Object o) {
        articleText.setText(Html.fromHtml(o.toString().replaceAll("(postload).*", "")));
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(imagesList.size());
        super.onPostExecute(o);
    }

    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Ion.with(context).load(imagesList.get(position)).progressBar(new ProgressBar(context)).intoImageView(imageView);
        }
    };
}
