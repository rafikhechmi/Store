package com.example.rafik.store;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by rafik on 4/14/2017.
 */

public class ListViewAdapter extends ArrayAdapter<ListItem> {
    private Context mContext;
    private int layoutResourceId;
    private ArrayList<ListItem> mGridData = new ArrayList<ListItem>();
    DownloadManager downloadManager;


    public ListViewAdapter(Context mContext, int layoutResourceId, ArrayList<ListItem> mGridData) {
        super(mContext, layoutResourceId, mGridData);
        this.layoutResourceId = layoutResourceId;
        this.mContext = mContext;
        this.mGridData = mGridData;
    }



    public void setGridData(ArrayList<ListItem> mGridData) {
        this.mGridData = mGridData;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        ViewHolder holder;


        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);
            holder = new ViewHolder();
            holder.titleTextView2 = (TextView) row.findViewById(R.id.list_item_title);
            holder.imageView2=(ImageView)row.findViewById(R.id.imageView2) ;
            holder.button=(Button)row.findViewById(R.id.button3);
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                    StrictMode.setThreadPolicy(policy);


                    downloadManager= (DownloadManager)mContext.getSystemService(Context.DOWNLOAD_SERVICE);

                    Uri uri = Uri.parse("http://192.168.137.139/sonic.apk");
                    DownloadManager.Request request = new DownloadManager.Request(uri);
                    request.addRequestHeader("Content-Type", "application/vnd.android.package-archive");

                    request.setMimeType("application/vnd.android.package-archive");

                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);

                }
            });




            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        ListItem item = mGridData.get(position);
        holder.titleTextView2.setText((item.getTitle()));
        Picasso.with(mContext).load(item.getImage()).into(holder.imageView2);

        return row;
    }

    static class ViewHolder {
        TextView titleTextView2;
        ImageView imageView2;
        Button button;
    }
}
