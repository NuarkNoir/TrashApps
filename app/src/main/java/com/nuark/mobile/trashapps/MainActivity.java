package com.nuark.mobile.trashapps;

import android.Manifest;
import android.app.*;
import android.content.pm.PackageManager;
import android.os.*;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.nuark.mobile.trashapps.loaders.AppListLoader;
import com.nuark.mobile.trashapps.utils.Globals;
import com.nuark.trashbox.models.App;
import com.nuark.trashbox.utils.Enumerators.Sort;
import com.softw4re.views.InfiniteListView;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity
{
    public static MainActivity instance;
    static String lastpage = "0";
    public static String currentpage = "0";
    public static Sort sortingType = Sort.Recomendation;
    Activity act = this;
    public InfiniteListView lv;
    static Button gbk, gfw;
    public TextView pagesShw;
    public LinearLayout mainContent;
    LinearLayout navBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        debug();
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toasty.info(act, "Запрашиваем доступ к записи на карту\n" +
                    "Пожалуйста, для корректной работы приложения, предоставьте разрешение").show();
            ActivityCompat.requestPermissions(
                    act,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1
            );
        }
        instance = this;
		lv = findViewById(R.id.lvapp);
        navBar = findViewById(R.id.navigationBar);
        gbk = findViewById(R.id.navGOBACK);
        gfw = findViewById(R.id.navGOFORW);
        pagesShw = findViewById(R.id.pagesShw);
        mainContent = findViewById(R.id.mainContent);

        ArrayList<App> apps = new ArrayList<>();
        AppAdapter adapter = new AppAdapter(instance, apps);
        lv.setAdapter(adapter);
        lv.hasMore(true);

        contentLoader();
        p_comparer();
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.imi_progs:
                Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getProgsUrl());
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.imi_games:
                Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getGamesUrl());
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.srt_rec:
                sortingType = Sort.Recomendation;
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.srt_dat:
                sortingType = Sort.Date;
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.srt_rat:
                sortingType = Sort.Rating;
                hardReset();
                contentLoader();
                p_comparer();
                break;
        }
    }

    void contentLoader(){
        new AppListLoader().execute();
    }

    public void loadContentWithTag(String tag){
        if (Globals.getCurrentUrl().contains("progs"))
            Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getProgsUrl());
        else
            Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getGamesUrl());
        Globals.setCurrentUrl(Globals.getCurrentUrl().replace("os_android", Globals.Tagger.getTag(tag)));
        ArrayList<App> apps = new ArrayList<>();
        AppAdapter adapter = new AppAdapter(instance, apps);
        lv.setAdapter(adapter);
        hardReset();
        contentLoader();
        p_comparer();
    }

    public static String getLastpage() {
        return lastpage;
    }

    public static void setLastpage(String _lastpage) {
        if (lastpage.contentEquals("0")) lastpage = _lastpage;
        if (currentpage.contentEquals("0")) currentpage = _lastpage;
    }

    public static String getCurrentpage() {
        return currentpage;
    }

    public void navigationHandler(View view) {
        p_comparer();
        switch (view.getId()){
            case R.id.navGOFORW:
                currentpage = String.valueOf(Integer.parseInt(getCurrentpage())-1);
                break;
            case R.id.navGOBACK:
                currentpage = String.valueOf(Integer.parseInt(getCurrentpage())+1);
                break;
        }
        new AppListLoader().execute();
    }

    public static void p_comparer(){
        if (Integer.parseInt(lastpage) == Integer.parseInt(currentpage)) gbk.setVisibility(View.GONE);
            else gbk.setVisibility(View.VISIBLE);
        if (currentpage.contentEquals("1")) instance.lv.hasMore(false);
    }

    public static void hardReset(){
        lastpage = "0";
        currentpage = "0";
    }

    public void refreshList() {
        lv.clearList();
        contentLoader();
    }

    void debug(){
        System.out.println("+++++++++++++++++DEBUG");
        System.out.println(Globals.getCurrentUrl());
        System.out.println(Globals.Tagger.Tag.size());
        System.out.println("DEBUG+++++++++++++++++");
    }
}
