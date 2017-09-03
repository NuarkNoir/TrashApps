package com.nuark.mobile.trashapps.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nuark with love on 19.07.2017.
 * Protected by QPL-1.0
 */

public class Globals {
    private static String currentUrl = com.nuark.trashbox.Globals.Statics.getProgsUrl();

    public static String getCurrentUrl() {
        return currentUrl;
    }

    public static void setCurrentUrl(String url) {
        currentUrl = url;
    }

    public static class Tagger {
        public static final Map<String, String> Tag = new HashMap<String, String>(){{
            put("Bluetooth", "progs_Bluetooth-os_android/");
            put("E-mail", "progs_mailer-os_android/");
            put("Python", "progs_phyton-os_android/");
            put("QR-ридеры", "progs_qr-os_android/");
            put("RSS-ридеры", "progs_rss-os_android/");
            put("SMS/MMS", "progs_sms-os_android/");
            put("VoIP", "progs_voip-os_android/");
            put("Wi-Fi", "progs_wifi-os_android/");
            put("Автоответчики", "progs_auto-os_android/");
            put("Акселерометр", "progs_accelerometer-os_android/");
            put("Антивирусы", "progs_antivirus-os_android/");
            put("Архиваторы", "progs_archives-os_android/");
            put("Аудиоплееры", "progs_audioplayer-os_android/");
            put("Безопасность", "progs_guardian-os_android/");
            put("Блокировка клавиатуры", "progs_block-os_android/");
            put("Браузеры", "progs_browser-os_android/");
            put("Будильники и напоминалки", "progs_clocks-os_android/");
            put("Видеоплееры", "progs_videoplayer-os_android/");
            put("Виджеты", "progs_widget-os_android/");
            put("Вызовы", "progs_calls-os_android/");
            put("Диктофоны", "progs_dict-os_android/");
            put("Диспетчеры задач", "progs_disp-os_android/");
            put("Другие", "progs_other-os_android/");
            put("Иконки и темы", "icons_themes-os_android/");
            put("Интернет-приложения", "progs_internet-os_android/");
            put("Интерфейс", "progs_interface-os_android/");
            put("Калькуляторы", "progs_calc-os_android/");
            put("Камера", "progs_camera-os_android/");
            put("Карты", "progs_maps-os_android/");
            put("Контакты", "progs_contacts-os_android/");
            put("Моды", "progs_mods-os_android/");
            put("Обои", "progs_wallpaper-os_android/");
            put("Общение", "progs_chat-os_android/");
            put("Офисные приложения", "progs_offices-os_android/");
            put("Планировщик/Органайзер", "progs_organaizer-os_android/");
            put("Погода и Новости", "progs_weather-os_android/");
            put("Поиск", "progs_search-os_android/");
            put("Работа с графикой", "progs_pieditors-os_android/");
            put("Системные утилиты", "progs_system_system-os_android/");
            put("Скачивание", "progs_downloader-os_android/");
            put("Скринсейверы", "progs_screen-os_android/");
            put("Словари", "progs_translators-os_android/");
            put("Сотовая связь", "progs_connect-os_android/");
            put("Текстовые редакторы", "progs_texteditors-os_android/");
            put("Файловые менеджеры", "progs_fileman-os_android/");
            put("Фильтры", "progs_filters-os_android/");
            put("Финансы", "progs_finnance-os_android/");
            put("Хобби", "progs_hobby-os_android/");
            put("Часы", "progs_clock-os_android/");
            put("Читалки", "progs_reader-os_android/");
            put("Эмуляторы консолей", "progs_emuls-os_android/");
            put("Bluetooth", "games_bluetooth-os_android/");
            put("Flash", "games_flash-os_android/");
            put("Online", "games_online-os_android/");
            put("RPG", "games_rpg-os_android/");
            put("Азартные", "games_azart-os_android/");
            put("Аркады", "games_arcades-os_android/");
            put("Бродилки/Экшены", "games_action-os_android/");
            put("Гонки", "games_races-os_android/");
            put("Для взрослых", "games_adult-os_android/");
            put("Для детей", "games_children-os_android/");
            put("Другое", "other_games-os_android/");
            put("Карточные", "games_cards-os_android/");
            put("Квесты", "games_qests-os_android/");
            put("Логические", "games_logic-os_android/");
            put("Настольные", "games_table-os_android/");
            put("Приключения", "games_adv-os_android/");
            put("Симуляторы", "games_simulation-os_android/");
            put("Спорт", "games_sport-os_android/");
            put("Стратегии", "games_strategy-os_android/");
            put("Стрелялки", "games_fire-os_android/");
            put("Файтинги", "games_figth-os_android/");
        }};

        public static String getTag(String tag){
            if (Tag.containsKey(tag)) return Tag.get(tag);
            return "progs_other-os_android/";
        }
    }
}
