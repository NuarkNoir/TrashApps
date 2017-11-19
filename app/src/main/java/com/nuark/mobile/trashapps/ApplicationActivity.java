package com.nuark.mobile.trashapps;


import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nuark.mobile.trashapps.loaders.ArticleContentLoader;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import xyz.nuark.trashbox.models.App;

public class ApplicationActivity extends Activity {

    String title, link, androidVersion, icolink;
    TextView articleView, titleView, androver;
    ImageView appIcon;
    Button dwnBtn;
    CarouselView carouselView;
    ArrayList<String> tagList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        title = getIntent().getExtras().getString("TITLE");
        link = getIntent().getExtras().getString("LINK");
        androidVersion = getIntent().getExtras().getString("ANDROVER");
        icolink = getIntent().getExtras().getString("ICON");
        tagList = getIntent().getExtras().getStringArrayList("TAGS");
        getActionBar().setTitle(title);

        articleView = findViewById(R.id.articleView);
        titleView = findViewById(R.id.title);
        androver = findViewById(R.id.androver);
        appIcon = findViewById(R.id.appIcon);
        carouselView = findViewById(R.id.carouselView);
        dwnBtn = findViewById(R.id.dwnBtn);

        titleView.setText(title);
        androver.setText(androidVersion);
        dwnBtn.setVisibility(View.GONE);

        App app = new App(title,  androidVersion, link, icolink, tagList);

        new ArticleContentLoader(app, articleView, carouselView, dwnBtn, getApplication()).execute();
        Ion.with(getApplicationContext()).load(icolink).intoImageView(appIcon);
    }
}
