package com.nuark.mobile.trashapps;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.adroitandroid.chipcloud.ChipCloud;
import com.adroitandroid.chipcloud.ChipListener;
import com.koushikdutta.ion.Ion;
import com.softw4re.views.InfiniteListAdapter;

import java.util.ArrayList;

import xyz.nuark.trashbox.AppsPage;
import xyz.nuark.trashbox.models.App;

public class AppAdapter extends InfiniteListAdapter<App> {
    private final Activity context;
    private ArrayList<App> appslist = new ArrayList<>();

    public AppAdapter(Activity context, ArrayList<App> applist) {
        super(context, R.layout.appitem, applist);
        this.context = context;
        this.appslist = applist;
    }

    @Override
    public int getCount() {
        return appslist.size();
    }

    @Override
    public App getItem(int position) {
        return appslist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.appitem, null, true);

        ChipCloud chipCloud = rowView.findViewById(R.id.chip_cloud);
        ImageView appIcon = rowView.findViewById(R.id.appIcon);
        TextView tvTit = rowView.findViewById(R.id.title);
        TextView tvAVer = rowView.findViewById(R.id.androver);

        tvTit.setText(appslist.get(position).getTitle());
        tvAVer.setText(appslist.get(position).getAndroidVersion());
        chipCloud.addChips(appslist.get(position).getTagList().toArray(new String[0]));
        new ChipCloud.Configure()
                .labels(appslist.get(position).getTagList().toArray(new String[0]))
                .chipCloud(chipCloud).chipListener(new ChipListener() {
            @Override
            public void chipSelected(int index) {
                String tag = appslist.get(position).getTagList().get(index);
                MainActivity.instance.loadContentWithTag(tag);
            }

            @Override
            public void chipDeselected(int index) {

            }
        }).build();

        Ion.with(context).load(appslist.get(position).getImageLink()).intoImageView(appIcon);

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                App app = appslist.get(position);
                context.startActivity(new Intent(context, ApplicationActivity.class)
                        .putExtra("TITLE", app.getTitle())
                        .putExtra("ANDROVER", app.getAndroidVersion())
                        .putExtra("LINK", app.getTopicLink())
                        .putExtra("ICON", app.getImageLink())
                        .putExtra("TAGS", app.getTagList())
                );
            }
        });
        return rowView;
    }

    @Override
    public void onNewLoadRequired() {
        MainActivity i = MainActivity.instance;
        if (i.all == null) {
            i.lv.stopLoading();
            return;
        }
        i.all.startLoading();
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onRefresh() {
        MainActivity i = MainActivity.instance;
        if (i.all == null){
            i.lv.stopLoading();
            return;
        }
        i.all.cancelLoadInBackground();
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                i.mLoader.startLoading();
                i.lv.stopLoading();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                i.worker = new AppsPage();
                return null;
            }
        }.execute();
    }

    @Override
    public void onItemClick(int i) {
        System.out.println("oic" + i);
    }

    @Override
    public void onItemLongClick(int i) {
        System.out.println("oilc" + i);
    }
}
