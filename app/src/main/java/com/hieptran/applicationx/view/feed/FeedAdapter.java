package com.hieptran.applicationx.view.feed;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hieptran.applicationx.R;
import com.hieptran.applicationx.model.Feed;

import java.util.ArrayList;

/**
 * Created by hiepth on 26/04/2016.
 */
public class FeedAdapter extends BaseAdapter {
    private ArrayList<Feed> mFeeds;
    private Context mContext;


    public FeedAdapter(ArrayList<Feed> mFeeds, Context mContext) {
        this.mFeeds = mFeeds;
        this.mContext = mContext;
        //Log.d("HiepTHb", "Adapter Constructor "+ mFeeds.get(0).getPost_date());
    }

    @Override
    public int getCount() {
        return mFeeds.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView mTitle,mContent,mLink;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.feed_item,null);
        mTitle = (TextView) v.findViewById(R.id.title);
        mContent = (TextView) v.findViewById(R.id.content);
        mLink = (TextView) v.findViewById(R.id.linkanh);
        Feed item = mFeeds.get(position);
        Log.d("HiepTHb","Trong adap"+item.getPost_date());
        mTitle.setText(item.getPost_author()+" - " + item.getPost_location());
        mContent.setText(item.getPost_date()+" - "+item.getPost_content() + " - "+item.getPost_description() + " - "+item.getPost_weather() );
        String aaa ="";
        for (int k =0;k<item.getPost_attachment().size();k++) {
            aaa+= item.getPost_attachment().get(k) +"\n";
        }
        mLink.setText(aaa);
        return v;
    }
}
