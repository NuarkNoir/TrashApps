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
import com.nuark.trashbox.utils.Enumerators.Sort;

import es.dmoral.toasty.Toasty;

public class MainActivity extends Activity
{
    public static MainActivity instance;
    static String lastpage = "0";
    public static String currentpage = "0";
    public static Sort sortingType = Sort.Recomendation;
    Activity act = this;
    ListView lv;
    static Button gbk, gfw;
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
        instance = this;
		lv = findViewById(R.id.lvapp);
        loadingNotification = findViewById(R.id.loadingNotofiaction);
        navBar = findViewById(R.id.navigationBar);
        gbk = findViewById(R.id.navGOBACK);
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
        }
    }

    void contentLoader(){
        new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw).execute();
    }

    public void loadContentWithTag(String tag){
        if (Globals.getCurrentUrl().contains("progs"))
            Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getProgsUrl());
        else
            Globals.setCurrentUrl(com.nuark.trashbox.Globals.Statics.getGamesUrl());
        Globals.setCurrentUrl(Globals.getCurrentUrl().replace("os_android", TagResolver(tag)));
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
                new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw).execute();
                break;
            case R.id.navGOBACK:
                currentpage = String.valueOf(Integer.parseInt(getCurrentpage())+1);
                new AppListLoader(lv, act, loadingNotification, mainContent, pagesShw).execute();
                break;
        }
    }

    public static void p_comparer(){
        if (Integer.parseInt(lastpage) == Integer.parseInt(currentpage)) gbk.setVisibility(View.GONE);
            else gbk.setVisibility(View.VISIBLE);
    }

    public static void hardReset(){
        lastpage = "0";
        currentpage = "0";
    }

    public static String TagResolver(String tag){
        switch (tag){
            case "Bluetooth":
                tag = "progs_Bluetooth-os_android/";
                break;
            case "E-mail":
                tag = "progs_mailer-os_android/";
                break;
            case "Python":
                tag = "progs_phyton-os_android/";
                break;
            case "QR-ридеры":
                tag = "progs_qr-os_android/";
                break;
            case "RSS-ридеры":
                tag = "progs_rss-os_android/";
                break;
            case "SMS/MMS":
                tag = "progs_sms-os_android/";
                break;
            case "VoIP":
                tag = "progs_voip-os_android/";
                break;
            case "Wi-Fi":
                tag = "progs_wifi-os_android/";
                break;
            case "Автоответчики":
                tag = "progs_auto-os_android/";
                break;
            case "Акселерометр":
                tag = "progs_accelerometer-os_android/";
                break;
            case "Антивирусы":
                tag = "progs_antivirus-os_android/";
                break;
            case "Архиваторы":
                tag = "progs_archives-os_android/";
                break;
            case "Аудиоплееры":
                tag = "progs_audioplayer-os_android/";
                break;
            case "Безопасность":
                tag = "progs_guardian-os_android/";
                break;
            case "Блокировка клавиатуры":
                tag = "progs_block-os_android/";
                break;
            case "Браузеры":
                tag = "progs_browser-os_android/";
                break;
            case "Будильники и напоминалки":
                tag = "progs_clocks-os_android/";
                break;
            case "Видеоплееры":
                tag = "progs_videoplayer-os_android/";
                break;
            case "Виджеты":
                tag = "progs_widget-os_android/";
                break;
            case "Вызовы":
                tag = "progs_calls-os_android/";
                break;
            case "Диктофоны":
                tag = "progs_dict-os_android/";
                break;
            case "Диспетчеры задач":
                tag = "progs_disp-os_android/";
                break;
            case "Другие":
                tag = "progs_other-os_android/";
                break;
            case "Иконки и темы":
                tag = "icons_themes-os_android/";
                break;
            case "Интернет-приложения":
                tag = "progs_internet-os_android/";
                break;
            case "Интерфейс":
                tag = "progs_interface-os_android/";
                break;
            case "Калькуляторы":
                tag = "progs_calc-os_android/";
                break;
            case "Камера":
                tag = "progs_camera-os_android/";
                break;
            case "Карты":
                tag = "progs_maps-os_android/";
                break;
            case "Контакты":
                tag = "progs_contacts-os_android/";
                break;
            case "Моды":
                tag = "progs_mods-os_android/";
                break;
            case "Обои":
                tag = "progs_wallpaper-os_android/";
                break;
            case "Общение":
                tag = "progs_chat-os_android/";
                break;
            case "Офисные приложения":
                tag = "progs_offices-os_android/";
                break;
            case "Планировщик/Органайзер":
                tag = "progs_organaizer-os_android/";
                break;
            case "Погода и Новости":
                tag = "progs_weather-os_android/";
                break;
            case "Поиск":
                tag = "progs_search-os_android/";
                break;
            case "Работа с графикой":
                tag = "progs_pieditors-os_android/";
                break;
            case "Системные утилиты":
                tag = "progs_system_system-os_android/";
                break;
            case "Скачивание":
                tag = "progs_downloader-os_android/";
                break;
            case "Скринсейверы":
                tag = "progs_screen-os_android/";
                break;
            case "Словари":
                tag = "progs_translators-os_android/";
                break;
            case "Сотовая связь":
                tag = "progs_connect-os_android/";
                break;
            case "Текстовые редакторы":
                tag = "progs_texteditors-os_android/";
                break;
            case "Файловые менеджеры":
                tag = "progs_fileman-os_android/";
                break;
            case "Фильтры":
                tag = "progs_filters-os_android/";
                break;
            case "Финансы":
                tag = "progs_finnance-os_android/";
                break;
            case "Хобби":
                tag = "progs_hobby-os_android/";
                break;
            case "Часы":
                tag = "progs_clock-os_android/";
                break;
            case "Читалки":
                tag = "progs_reader-os_android/";
                break;
            case "Эмуляторы консолей":
                tag = "progs_emuls-os_android/";
                break;
        }
        return tag;
    }
}
