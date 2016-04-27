package com.hieptran.applicationx.view.feed;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.hieptran.applicationx.R;
import com.hieptran.applicationx.control.feed.AppController;
import com.hieptran.applicationx.model.Feed;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by hiepth on 26/04/2016.
 */
public class FeedAdapter extends BaseAdapter {
    private ArrayList<Feed> mFeeds;
    private Context mContext;
//    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

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
//        if (imageLoader == null)
//            imageLoader = AppController.getInstance().getImageLoader();
        View v = inflater.inflate(R.layout.feed_items,null);
        mTitle = (TextView) v.findViewById(R.id.name);
        mContent = (TextView) v.findViewById(R.id.feed_content);
        TextView mThoitiet = (TextView) v.findViewById(R.id.feed_thoitiet);
        TextView mGoiy = (TextView) v.findViewById(R.id.feed_goiy);
        TextView timestamp = (TextView) v
                .findViewById(R.id.timestamp);
        //mLink = (TextView) v.findViewById(R.id.linkanh);
        ImageView profilePic = (ImageView) v
                .findViewById(R.id.profilePic);
//        FeedImageView feedImageView = (FeedImageView) v
//                .findViewById(R.id.feedImage1);

        Feed item = mFeeds.get(position);
        Log.d("HiepTHb", "Trong adap" + item.getPost_date());
        mTitle.setText(formatTitle(item.getPost_author(), item.getPost_location()));
        mContent.setText(item.getPost_content());
        mGoiy.setText(item.getPost_description());
        mThoitiet.setText(item.getPost_weather());
        profilePic.setImageResource(R.mipmap.ic_launcher);
        Gallery gl = (Gallery) v.findViewById(R.id.gallery_img);
        gl.setAdapter(new ImageAdapter(mContext,item.getPost_attachment()));
        String aaa ="";
        for (int k =0;k<item.getPost_attachment().size();k++) {
            aaa+= item.getPost_attachment().get(k) +"\n";
        }
//        feedImageView.setImageUrl(item.getPost_attachment().get(0), imageLoader);
//        feedImageView.setVisibility(View.VISIBLE);
//        feedImageView
//                .setResponseObserver(new FeedImageView.ResponseObserver() {
//                    @Override
//                    public void onError() {
//                    }
//
//                    @Override
//                    public void onSuccess() {
//                    }
//                });
       // mLink.setText(aaa);
       /* WebView imageWeb = (WebView) v.findViewById(R.id.imageweb);
        imageWeb.setBackgroundColor(0);
        imageWeb.loadDataWithBaseURL("","<img src='"+item.getPost_attachment().get(0)+"'/>","text/html", "UTF-8", "");*/
        return v;
    }
    private Spanned formatTitle(String author,String loc){
        String a ="<b style=\"color:blue;\"><font color='red'>"+author+"</font></b> tại <small><font color='blue'><i>"+loc+"</i></font></small>";
        String text="<html>"+"<b>"+"Hell"+"</b>"+"<i>"+"oo"+"</i>"+"</html>";
        Spanned text2 = Html.fromHtml(a);

        return text2;
    }
    public class ImageAdapter extends BaseAdapter {
        private Context context;
        private int itemBackground;
        private ArrayList<String> mUrls;
        public ImageAdapter(Context c,ArrayList<String> urls) {
            context = c;
            mUrls = urls;
            // ---setting the style---
            TypedArray a = mContext.obtainStyledAttributes(R.styleable.Gallery1);
            itemBackground = a.getResourceId(
                    R.styleable.Gallery1_android_galleryItemBackground, 0);
            a.recycle();
        }

        // ---returns the number of images---
        public int getCount() {
            return mUrls.size();
        }

        // ---returns the ID of an item---
        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        // ---returns an ImageView view---
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView = new ImageView(context);
            // imageView.setImageResource(imageIDs[position]);
            URL url;
            try {
                url = new URL(mUrls.get(position));
                Log.d("HiepTHb","URL anh "+ mUrls.get(position));
                new mAsyn(imageView).execute(url);
//                Bitmap bmp = BitmapFactory.decodeStream(url.openConnection()
//                        .getInputStream());
//                imageView.setImageBitmap(bmp);
//                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//                imageView.setLayoutParams(new Gallery.LayoutParams(150, 120));
                imageView.setBackgroundResource(itemBackground);

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return imageView;
        }
    }
    class mAsyn extends AsyncTask<URL,Void,Bitmap> {
        private Bitmap bmp;
        ImageView mImageView;

        public mAsyn(ImageView mImageView) {
            this.mImageView = mImageView;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            mImageView.setImageBitmap(bitmap);
            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            mImageView.setLayoutParams(new Gallery.LayoutParams(300, 250));
            //mImageView.setBackgroundResource(itemBackground);
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {
                 bmp = BitmapFactory.decodeStream(params[0].openConnection()
                        .getInputStream());
            } catch (IOException e){}
            return this.bmp;
        }
    }
}
