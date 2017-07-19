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

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity 
{

    static String lastpage = "0";
    public static String currentpage = "0";
    private static Button gbks;
    Activity act = this;
    ListView lv;
    Button gbk, gfw;
    TextView pagesShw;
    LinearLayout mainContent;
	LinearLayout loadingNotification, navBar;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        if (ContextCompat.checkSelfPermission(act, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            Toasty.info(act, "Запрашиваем доступ к записи на карту\n" +
                    "Пожалуйста, для корректной работы приложения, предоставьте разрешение").show();
            ActivityCompat.requestPermissions(
                    act,
                    new String[] { Manifest.permission.WRITE_EXTERNAL_STORAGE },
                    1
            );
        }
		lv = findViewById(R.id.lvapp);
        loadingNotification = findViewById(R.id.loadingNotofiaction);
        navBar = findViewById(R.id.navigationBar);
        gbk = findViewById(R.id.navGOBACK);
        gbks = gbk;
        gfw = findViewById(R.id.navGOFORW);
        pagesShw = findViewById(R.id.pagesShw);
        mainContent = findViewById(R.id.mainContent);
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
        switch (item.getItemId()){
            case R.id.menuLoader:
                contentLoader();
                break;
            case R.id.imi_progs:
                Globals.setCurrentUrl(Globals.Section.Programs);
                hardReset();
                contentLoader();
                p_comparer();
                break;
            case R.id.imi_games:
                Globals.setCurrentUrl(Globals.Section.Games);
                hardReset();
                contentLoader();
                p_comparer();
                break;
        }
    }

    void contentLoader(){
        new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw).execute();
    }

    public static String getLastpage() {
        return lastpage;
    }

    public static void setLastpage(String _lastpage) {
        if (lastpage.contentEquals("0")) lastpage = _lastpage;
        currentpage = _lastpage;
    }

    public static String getCurrentpage() {
        return currentpage;
    }

    public void navigationHandler(View view) {
        p_comparer();
        int tmp = Integer.parseInt(currentpage);
        switch (view.getId()){
            case R.id.navGOFORW:
                tmp--;
                currentpage = String.valueOf(tmp);
                new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw, currentpage).execute();
                break;
            case R.id.navGOBACK:
                tmp++;
                currentpage = String.valueOf(tmp);
                new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw, currentpage).execute();
                break;
        }
    }

    public static void p_comparer(){
        if (gbks != null)
            if (Integer.parseInt(lastpage) == Integer.parseInt(currentpage)) gbks.setVisibility(View.GONE);
                else gbks.setVisibility(View.VISIBLE);
    }

    public static void hardReset(){
        lastpage = "0";
        currentpage = "0";
    }
}
