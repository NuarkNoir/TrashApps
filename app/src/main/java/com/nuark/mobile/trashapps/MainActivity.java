package com.nuark.mobile.trashapps;

import android.app.*;
import android.os.*;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;

import com.nuark.mobile.trashapps.loaders.AppListLoader;

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
		lv = (ListView) findViewById(R.id.lvapp);
        loadingNotification = (LinearLayout) findViewById(R.id.loadingNotofiaction);
        navBar = (LinearLayout) findViewById(R.id.navigationBar);
        gbk = (Button) findViewById(R.id.navGOBACK);
        gbks = gbk;
        gfw = (Button) findViewById(R.id.navGOFORW);
        pagesShw = (TextView) findViewById(R.id.pagesShw);
        mainContent = (LinearLayout) findViewById(R.id.mainContent);
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
        }
    }

    void contentLoader(){
        new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw).execute();
    }

    public static String getLastpage() {
        return lastpage;
    }

    public static void setLastpage(String _lastpage) {
        if (lastpage == "0") lastpage = _lastpage;
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
}
