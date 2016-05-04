package com.hieptran.applicationx.control.kenit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Gallery;
import android.widget.ImageView;

import com.hieptran.applicationx.model.Feed;
import com.hieptran.applicationx.model.HomePost;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.utils.Const;
import com.hieptran.applicationx.utils.its.JSONParser;
import com.hieptran.applicationx.view.adapter.KenITAdapter;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by hiepth on 27/04/2016.
 */
public class KenitControl extends AsyncTask<Void,Void,Void> implements Const {
    String url_get_product = "http://kenit.link/androidpart/getproduct.php";
    JSONParser jsonParser = new JSONParser();
    static ArrayList<HomePost> mFeedArrayList = new ArrayList<>();

    @Override
    protected Void doInBackground(Void... params) {
        Log.d("HiepTHb", "doInBackground - get data");
        List<NameValuePair> paramsd = new ArrayList<NameValuePair>();
        // getting JSON string from URL
        JSONObject json = jsonParser.makeHttpRequest(url_get_product, "GET", paramsd);
        mFeedArrayList.clear();
      //  Log.d("HiepTHb","url "+json.toString());
        try {
            JSONArray products = json.getJSONArray("products");
            for(int i=0;i<products.length();i++) {
                JSONObject mObject = products.getJSONObject(i);
                HomePost mFeed = new HomePost();
                mFeed.setTitle(mObject.getString("post_title").toString());
                mFeed.setContent(mObject.getString("post_content").toString());
                mFeed.setName(mObject.getString("post_name"));
                mFeed.setDate(mObject.getString("post_date"));
                mFeed.setLink(mObject.getString("post_link"));
                mFeed.setImage_url(mObject.getString("post_attach"));
                Log.d("HiepGa Link",mFeed.getImage_url());
                if(mFeed.getContent().length()>10)
                mFeedArrayList.add(mFeed);
            }
        } catch (Exception ex) {}
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        setmFeedArrayList(mFeedArrayList);
    }


    public static ArrayList<HomePost> getmFeedArrayList() {
        return mFeedArrayList;
    }

    public static void setmFeedArrayList(ArrayList<HomePost> mFeedArrayList) {
        KenitControl.mFeedArrayList = mFeedArrayList;
    }

}
