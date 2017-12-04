package com.nuark.mobile.trashapps;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nuark.mobile.trashapps.loaders.AppListLoader;
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;

import xyz.nuark.trashbox.AppsPage;
import xyz.nuark.trashbox.models.App;

public class MainActivity extends FragmentActivity implements LoaderManager.LoaderCallbacks<ArrayList<App>> {
    public static MainActivity instance;
    public InfiniteListView lv;
    public LinearLayout mainContent;
    public AppsPage worker = null;
    public AppListLoader all;
    public Loader<ArrayList<App>> mLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        instance = this;
        if (ContextCompat.checkSelfPermission(instance, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(instance, "Запрашиваем доступ к записи на карту\n" +
                    "Пожалуйста, для корректной работы приложения, предоставьте разрешение", Toast.LENGTH_LONG).show();
            ActivityCompat.requestPermissions(
                    instance,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1
            );
        }

        lv = findViewById(R.id.lvapp);
        mainContent = findViewById(R.id.mainContent);

        AppAdapter adapter = new AppAdapter(instance, new ArrayList<>());
        lv.setAdapter(adapter);

        mLoader = getSupportLoaderManager().initLoader(1, null, this);
    }

    public void loadContentWithTag(String tag){
        AppAdapter adapter = new AppAdapter(instance, new ArrayList<>());
        lv.setAdapter(adapter);
        all.loadTag(tag);
        mLoader.startLoading();
    }

    @Override
    public Loader<ArrayList<App>> onCreateLoader(int i, Bundle bundle) {
        mLoader = new AppListLoader(instance);
        all = (AppListLoader) mLoader;
        return mLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<App>> loader, ArrayList<App> apps) {
        mLoader = loader;
        lv.addAll(apps);
        lv.hasMore(true);
        mainContent.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<App>> loader) {
        System.out.println("LOADER IS DEAD NOW");
    }
}
