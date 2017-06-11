package com.nuark.mobile.trashapps;


import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.nuark.mobile.trashapps.loaders.ArticleContentLoader;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;

public class ApplicationActivity extends Activity {

    String title, link, androidVersion, icolink;
    TextView articleView, titleView, androver;
    ImageView appIcon;
    CarouselView carouselView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        title = getIntent().getExtras().getString("TITLE");
        link = getIntent().getExtras().getString("LINK");
        androidVersion = getIntent().getExtras().getString("ANDROVER");
        icolink = getIntent().getExtras().getString("ICON");
        getActionBar().setTitle(title);

        articleView = (TextView) findViewById(R.id.articleView);
        titleView = (TextView) findViewById(R.id.title);
        androver = (TextView) findViewById(R.id.androver);
        appIcon = (ImageView) findViewById(R.id.appIcon);
        carouselView = (CarouselView) findViewById(R.id.carouselView);

        titleView.setText(title);
        androver.setText(androidVersion);

        new ArticleContentLoader(link, articleView, carouselView, getApplication()).execute();
        Ion.with(getApplicationContext()).load(icolink).intoImageView(appIcon);
    }
}
