package com.hieptran.applicationx.control.feed;

import android.os.AsyncTask;
import android.util.Log;

import com.hieptran.applicationx.model.Feed;
import com.hieptran.applicationx.utils.Const;
import com.hieptran.applicationx.utils.JSONParser;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiepth on 26/04/2016.
 */
public class FeedControl extends AsyncTask<Void,Void,Void> implements Const {
    String url_get_product = SERVER_PATH+GET_PRODUCT_FILE;
    JSONParser jsonParser = new JSONParser();
   static ArrayList<Feed> mFeedArrayList = new ArrayList<>();
    @Override
    protected Void doInBackground(Void... params) {
        Log.d("HiepTHb1","Chay doing");
        List<NameValuePair> paramsd = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_get_product, "GET", paramsd);
        mFeedArrayList.clear();
        Log.d("HiepTHb","url "+json.toString());
        try {
            JSONArray products = json.getJSONArray("products");
            for(int i=0;i<products.length();i++) {
                JSONObject mObject = products.getJSONObject(i);
                Feed mFeed = new Feed();

                mFeed.setPost_author(mObject.getString("post_author").toString());
                mFeed.setPost_content(mObject.getString("post_content").toString());
                mFeed.setPost_date(mObject.getString("post_date").toString());
                Log.d("HiepTHb","date"+mFeed.getPost_date());
                JSONArray urls = mObject.getJSONArray("linkanh");
                ArrayList<String> linkanh = new ArrayList<>();

                for(int k=0;k< urls.length();k++) {
                    linkanh.add(SERVER_NAME+"wp-content/uploads/"+urls.getJSONObject(k).getString("url"));
                }
                mFeed.setPost_attachment(linkanh);
                mFeed.setPost_weather(mObject.getString("thoitiet"));
                mFeed.setPost_description(mObject.getString("goiy"));
                mFeed.setPost_location(mObject.getString("post_location"));
                mFeedArrayList.add(mFeed);
            }
        } catch (Exception ex) {}
        return null;
    }
    public static ArrayList<Feed> getFeeds() {
        new FeedControl().execute();
        return mFeedArrayList;
    }
}
