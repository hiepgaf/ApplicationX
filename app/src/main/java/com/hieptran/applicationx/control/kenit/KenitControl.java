package com.hieptran.applicationx.control.kenit;

import android.os.AsyncTask;
import android.util.Log;

import com.hieptran.applicationx.model.Feed;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.utils.Const;
import com.hieptran.applicationx.utils.its.JSONParser;
import com.hieptran.applicationx.view.adapter.KenITAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiepth on 27/04/2016.
 */
public class KenitControl extends AsyncTask<Void,Void,Void> implements Const {
    String url_get_product = SERVER_PATH+GET_PRODUCT_FILE;
    JSONParser jsonParser = new JSONParser();
    static ArrayList<KenITPost> mFeedArrayList = new ArrayList<>();
    KenITAdapter adapter;

    public KenitControl(KenITAdapter adapter) {
        this.adapter = adapter;
    }

    public KenitControl() {
    }

    @Override
    protected Void doInBackground(Void... params) {
        Log.d("HiepTHb", "doInBackground");
        List<NameValuePair> paramsd = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_get_product, "GET", paramsd);
        mFeedArrayList.clear();
        Log.d("HiepTHb","url "+json.toString());
        try {
            JSONArray products = json.getJSONArray("products");
            for(int i=0;i<products.length();i++) {
                JSONObject mObject = products.getJSONObject(i);
                KenITPost mFeed = new KenITPost();

                mFeed.setTitle(mObject.getString("post_author").toString() + " tai "+mObject.getString("post_location"));
                mFeed.setDescription(mObject.getString("post_content").toString() + "   \n" + mObject.getString("goiy"));
                mFeed.setTimeStamp(mObject.getString("post_date").toString());
                Log.d("HiepTHb", "date" + mFeed.getTimeStamp());
                JSONArray urls = mObject.getJSONArray("linkanh");
                ArrayList<String> linkanh = new ArrayList<>();

                for(int k=0;k< urls.length();k++) {
                    linkanh.add(urls.getJSONObject(k).getString("url"));
                }
                mFeed.setPostImageUrl(SERVER_NAME+"wp-content/uploads/"+linkanh.get(0));
                mFeed.setTitle(mObject.getString("post_author").toString());

                mFeedArrayList.add(mFeed);
            }
        } catch (Exception ex) {}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("HiepTHb", "mFeedArrayList" + mFeedArrayList.size());
        setmFeedArrayList(mFeedArrayList);
        adapter.notifyDataSetChanged();
    }


    public static ArrayList<KenITPost> getmFeedArrayList() {
        return mFeedArrayList;
    }

    public static void setmFeedArrayList(ArrayList<KenITPost> mFeedArrayList) {
        KenitControl.mFeedArrayList = mFeedArrayList;
    }
}
