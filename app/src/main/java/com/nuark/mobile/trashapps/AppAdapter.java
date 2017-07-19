package com.nuark.mobile.trashapps;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.adroitandroid.chipcloud.ChipCloud;
import com.koushikdutta.ion.Ion;
import com.nuark.mobile.trashapps.*;
import com.nuark.mobile.trashapps.models.LApplication;

import java.util.*;

public class AppAdapter extends BaseAdapter
 {
    private final Activity context;
    private ChipCloud chipCloud;
    private ImageView appIcon;
    private ArrayList<LApplication> appslist = new ArrayList();

    public AppAdapter(Activity context, ArrayList applist) {
        this.context = context;
		this.appslist = applist;
    }

    @Override
    public int getCount() {
        return appslist.size();
    }

    @Override
    public LApplication getItem(int position) {
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

        chipCloud = rowView.findViewById(R.id.chip_cloud);
        appIcon = rowView.findViewById(R.id.appIcon);
		TextView tvTit = rowView.findViewById(R.id.title);
		TextView tvAVer = rowView.findViewById(R.id.androver);

		tvTit.setText(appslist.get(position).getTitle());
		tvAVer.setText(appslist.get(position).getAndroidVersion());
        chipCloud.addChips(appslist.get(position).getTagList().toArray(new String[0]));
        Ion.with(context).load(appslist.get(position).getImageLink()).intoImageView(appIcon);

        rowView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
                    context.startActivity(new Intent(context, ApplicationActivity.class)
                            .putExtra("TITLE", appslist.get(position).getTitle())
                            .putExtra("ANDROVER", appslist.get(position).getAndroidVersion())
                            .putExtra("LINK", appslist.get(position).getTopicLink())
                            .putExtra("ICON", appslist.get(position).getImageLink())
                    );
				}
			});
        return rowView;
    }
}
