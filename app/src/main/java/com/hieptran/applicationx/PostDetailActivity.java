/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hieptran.applicationx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.applicationx.model.HomePost;
import com.hieptran.applicationx.utils.Utils;
import com.squareup.picasso.Picasso;


public class PostDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    TextView tv;
     HomePost cheeseName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
//        tv = (TextView) findViewById(R.id.post_content);
        Intent intent = getIntent();
         cheeseName = (HomePost) intent.getSerializableExtra("READMORE");

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName.getTitle());
        WebView webview = (WebView) this.findViewById(R.id.post_content);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadDataWithBaseURL("", cheeseName.getContent(), "text/html", "UTF-8", "");
//        tv.setText(cheeseName.getContent());
        loadBackdrop();
        getSupportActionBar().hide();

    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        if (URLUtil.isValidUrl(cheeseName.getImage_url())) {
//                try {
//                    new mAsyn(kenITPostViewHolder.post_image).execute(new URL(item.getImage_url()));
//                } catch (Exception  ex) {}
            //  Picasso.with(mContext).load(R.drawable.kenit).into(kenITPostViewHolder.post_image);
//                String link = SERVER_NAME +"its/wp-content/uploads/2016/04/41-Flag-Map-1024x576.jpg";
//                Log.d("Link",link);

            new Picasso.Builder(this).listener(
                    new Picasso.Listener()
                    {
                        @Override
                        public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception)
                        {
                            Log.e("Picasso", "loading " + uri + " failed: ", exception);
                        }
                    }
            )
                    .build()
                    .load(cheeseName.getImage_url())
                    .placeholder(android.R.drawable.gallery_thumb)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(Utils.getScreenWidth(this),400)
                    .into(imageView);



            // Picasso.with(mContext).load(item.getImage_url()).resize(600, 500).centerCrop().placeholder(R.drawable.placeholder).into(kenITPostViewHolder.post_image);
        } else {
            Picasso.with(this).load(R.drawable.logo).into(imageView);            }
       // Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }


}
