package com.nuark.mobile.trashapps;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.*;

import com.nuark.mobile.trashapps.loaders.AppListLoader;

import xyz.nuark.trashbox.AppsPage;
import xyz.nuark.trashbox.Globals;
import xyz.nuark.trashbox.models.App;
import xyz.nuark.trashbox.utils.Enumerators;
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity
{
    public static MainActivity instance;
    public InfiniteListView lv;
    public LinearLayout mainContent;
    public AppsPage appsPage = null;
    public AppListLoader all;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        instance = this;
        if (ContextCompat.checkSelfPermission(instance, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toasty.info(instance, "Запрашиваем доступ к записи на карту\n" +
                    "Пожалуйста, для корректной работы приложения, предоставьте разрешение").show();
            ActivityCompat.requestPermissions(
                    instance,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1
            );
        }
		lv = findViewById(R.id.lvapp);
        mainContent = findViewById(R.id.mainContent);

        ArrayList<App> apps = new ArrayList<>();
        AppAdapter adapter = new AppAdapter(instance, apps);
        lv.setAdapter(adapter);
        lv.hasMore(true);

        all = new AppListLoader();
        contentLoader();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void menuHandler(MenuItem item) {
        ArrayList<App> apps = new ArrayList<>();
        AppAdapter adapter = new AppAdapter(instance, apps);
        lv.setAdapter(adapter);
		lv.hasMore(true);
        switch (item.getItemId()){
            case R.id.menuLoader:
                contentLoader();
                break;
            case R.id.imi_progs:
                all = new AppListLoader(Enumerators.Type.Apps);
                contentLoader();
                break;
            case R.id.imi_games:
                all = new AppListLoader(Enumerators.Type.Games);
                contentLoader();
                break;
            case R.id.srt_rec:
                all = new AppListLoader();
                all = all.setSorting(Enumerators.Sort.Recomendation);
                contentLoader();
                break;
            case R.id.srt_dat:
                all = new AppListLoader();
                all = all.setSorting(Enumerators.Sort.Date);
                contentLoader();
                break;
            case R.id.srt_rat:
                all = new AppListLoader();
                all = all.setSorting(Enumerators.Sort.Rating);
                contentLoader();
                break;
        }
    }

    void contentLoader(){
        all.execute();
    }

    public void loadContentWithTag(String tag){
        System.out.println(tag);
        ArrayList<App> apps = new ArrayList<>();
        AppAdapter adapter = new AppAdapter(instance, apps);
        lv.setAdapter(adapter);
        all = new AppListLoader(Globals.Tagger.getTag(tag));
        contentLoader();
    }

    public void refreshList() {
        lv.clearList();
        all = new AppListLoader();
        contentLoader();
    }
}
