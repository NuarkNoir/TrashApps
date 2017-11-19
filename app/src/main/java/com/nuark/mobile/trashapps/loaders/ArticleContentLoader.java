package com.nuark.mobile.trashapps.loaders;

import android.app.Application;
import android.app.NotificationManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.ProgressCallback;
import com.nuark.mobile.trashapps.R;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;
import xyz.nuark.trashbox.AppArticlePage;
import xyz.nuark.trashbox.models.App;
import xyz.nuark.trashbox.models.AppArticle;
import xyz.nuark.trashbox.utils.Utils;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Nuark with love on 09.05.2017.
 * Protected by QPL-1.0
 */

public class ArticleContentLoader extends AsyncTask<Object, Void, Object> {

    private TextView articleText;
    private CarouselView carouselView;
    private Button downloadButton;
    private ArrayList<String> imagesList = new ArrayList<>();
    private Application context;
    private String link;
    private AppArticlePage aap;

    public ArticleContentLoader(App app, TextView articleText, CarouselView carouselView, Button downloadButton, Application context) {
        this.carouselView = carouselView;
        this.articleText = articleText;
        this.downloadButton = downloadButton;
        this.context = context;
        this.aap = new AppArticlePage(app.getTopicLink(), app.getImageLink());
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Object doInBackground(Object... objects) {
        try {
            AppArticle aa = aap.getArticle();
            Utils u = new Utils();
            link = aa.getDownloadLinks().entrySet().iterator().next().getValue();
            link = u.GenerateDownloadLink(link);
            imagesList = aa.getScreenshotsUrl();
            return aa.getArticleContent();
        } catch (IOException e) {
            e.printStackTrace();
            return e.getLocalizedMessage();
        }
    }

    private void configureDownloadButton(final String dwnlink) {
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp[] = dwnlink.split("/");
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TrasherDownloads/";
                if (!new File(path).exists()) if(!new File(path).mkdir()) Toasty.error(context, "Saving folder can't be created! Check you give application rights to do this!").show();
                final String filename = Uri.decode(tmp[tmp.length-1]);
                final NotificationCompat.Builder mNotificationBuilder = new NotificationCompat.Builder(context);
                final NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
                mNotificationBuilder.setContentTitle("Скачивание файла")
                        .setContentText("Название: " + filename).setSubText("Прогресс: ...")
                        .setSmallIcon(R.drawable.ic_icon).setColor(Color.YELLOW);
                notificationManager.notify(1337, mNotificationBuilder.build());
                Ion.with(context).load(dwnlink)
                        .progress(new ProgressCallback() {
                            @Override
                            public void onProgress(long l, long l1) {
                                mNotificationBuilder.setSubText("Прогресс: " + l + " из " + l1 + " байтов.");
                                notificationManager.notify(1337, mNotificationBuilder.build());
                                if (Math.round(l/l1*100) > 90) {
                                    mNotificationBuilder.setContentText("Файл скачан!").setSubText("Успех!").setColor(Color.GREEN);
                                    notificationManager.notify(1337, mNotificationBuilder.build());
                                }
                            }
                        })
                        .write(new File(path + filename))
                        .setCallback(new FutureCallback<File>() {
                            @Override
                            public void onCompleted(Exception e, File file) {
                                if (e == null) {
                                    mNotificationBuilder.setContentText("Файл скачан!").setSubText("Успех!").setColor(Color.GREEN);
                                    notificationManager.notify(1337, mNotificationBuilder.build());
                                } else {
                                    Toasty.error(context, e.getMessage()).show();
                                    mNotificationBuilder.setContentText(e.getMessage()).setSubText("Ошибка!").setColor(Color.RED);
                                    notificationManager.notify(1337, mNotificationBuilder.build());
                                }
                            }
                        });
            }
        });
        downloadButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onPostExecute(Object o) {
        articleText.setText(Html.fromHtml(o.toString()));
        carouselView.setImageListener(imageListener);
        carouselView.setPageCount(imagesList.size());
        configureDownloadButton(link);
        super.onPostExecute(o);
    }

    private ImageListener imageListener = new ImageListener() {
        @Override
        public void setImageForPosition(int position, ImageView imageView) {
            Glide.with(context).load(imagesList.get(position)).centerCrop().into(imageView);
        }
    };
}
