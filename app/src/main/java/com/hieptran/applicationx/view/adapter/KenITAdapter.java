package com.hieptran.applicationx.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.hieptran.applicationx.PostDetailActivity;
import com.hieptran.applicationx.R;
import com.hieptran.applicationx.model.HomePost;
import com.hieptran.applicationx.model.KenITPost;
import com.hieptran.applicationx.utils.Const;
import com.hieptran.applicationx.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by hiepth on 27/04/2016.
 */
public class KenITAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Const{
    private Context mContext;
    private ArrayList<HomePost> mKenITPosts;

    public class VIEW_TYPES {
        public static final int ITEM = 0; //Danh cho khi co item
        public static final int PROGRESS = 1; //Khi loading item
        public static final int PADDING = 2; //padding giua cac item
    }

    public KenITAdapter(Context mContext, ArrayList<HomePost> mKenITPosts) {
        this.mContext = mContext;
        this.mKenITPosts = mKenITPosts;
        Log.d("HiepTHb","KenITAdapter");
    }

/*    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (viewType) {
            case VIEW_TYPES.PROGRESS:
                break;
            View v = inflater.inflate(R.layout.progress, parent);
            return new RecyclerViewHolder(v);
            case VIEW_TYPES.PADDING:
                break;
            View v1 = inflater.inflate(R.layout.padding, parent);
            return new RecyclerViewHolder(v1);
            break;
            default:
                View v2 = inflater.inflate(R.layout.kenit_post, parent);
                return new KenITPostViewHolder(v2);
            break;
        }

    }*/
@Override
public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
   // Log.d("HiepTHb","onCreateViewHolder");

    switch (viewType) {
        case VIEW_TYPES.PROGRESS:
            return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.progress, viewGroup, false));
        case VIEW_TYPES.PADDING:
            return new RecyclerViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.padding, viewGroup, false));
        default:
            return new KenITPostViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.kenit_post, viewGroup, false));
    }
}


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
       // Log.d("HiepTH", "onBindViewHolder");
        //Xu ly cac tham so cua view nhu trong getView
        //mKenITPosts.clear();
        if(holder instanceof KenITPostViewHolder) {
       //     Log.d("HiepTH", "xyly view");

            final HomePost item = mKenITPosts.get(position);
            KenITPostViewHolder kenITPostViewHolder = (KenITPostViewHolder) holder;
            kenITPostViewHolder.title.setText(item.getTitle());
            kenITPostViewHolder.description.setText(item.getContent());
            kenITPostViewHolder.timestamp.setText(Utils.getTimeString(item.getDate()));
            kenITPostViewHolder.author.setText(item.getLink());
            //kenITPostViewHolder.post_image.setImageResource(item.getPostImageId());
            //Log.d("HiepLink",URLUtil.isValidUrl(item.getImage_url())+"");
            if (URLUtil.isValidUrl(item.getImage_url())) {
//                try {
//                    new mAsyn(kenITPostViewHolder.post_image).execute(new URL(item.getImage_url()));
//                } catch (Exception  ex) {}
              //  Picasso.with(mContext).load(R.drawable.kenit).into(kenITPostViewHolder.post_image);
//                String link = SERVER_NAME +"its/wp-content/uploads/2016/04/41-Flag-Map-1024x576.jpg";
//                Log.d("Link",link);

                new Picasso.Builder(mContext).listener(
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
                        .load(item.getImage_url())
                        .placeholder(android.R.drawable.gallery_thumb)
                        .error(android.R.drawable.stat_notify_error)
                        .resize(Utils.getScreenWidth(mContext),400)
                        .into(kenITPostViewHolder.post_image);



              // Picasso.with(mContext).load(item.getImage_url()).resize(600, 500).centerCrop().placeholder(R.drawable.placeholder).into(kenITPostViewHolder.post_image);
            } else {
                Picasso.with(mContext).load(R.drawable.logo).into(kenITPostViewHolder.post_image);            }


            kenITPostViewHolder.readmore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mContext, PostDetailActivity.class);
                    i.putExtra("READMORE", (Serializable) item);
                    mContext.startActivity(i);
                }
            });
        }

    }



    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mKenITPosts.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mKenITPosts.get(position)!=null?VIEW_TYPES.ITEM : VIEW_TYPES.PROGRESS;
    }

    /**
     * ViewHolder for item view of list
     */

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {


        public RecyclerViewHolder(View itemView) {
            super(itemView);
        }

    }

    public class KenITPostViewHolder extends RecyclerView.ViewHolder {
        protected ImageView  post_image;
        protected TextView title, description, timestamp, author;
        protected View parent;
        protected Button readmore;
        public KenITPostViewHolder(View itemView) {
            super(itemView);
            //Khai bao View nhu getView trong ListView
            this.parent = itemView;
            this.post_image = (ImageView) itemView.findViewById(R.id.source_icon);
            this.title = (TextView) itemView.findViewById(R.id.title);
            this.description = (TextView) itemView.findViewById(R.id.description);
            this.timestamp = (TextView) itemView.findViewById(R.id.timestamp);
            this.author = (TextView) itemView.findViewById(R.id.author);
            this.readmore = (Button) itemView.findViewById(R.id.readmore);
        }


        public View gePostImage() {
            return post_image;
        }

        public View getTitle() {
            return title;
        }

        public View getTimestamp() {
            return timestamp;
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

            mImageView.setImageBitmap(Bitmap.createScaledBitmap(bitmap,600,300,true));
//            mImageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            mImageView.setLayoutParams(new Gallery.LayoutParams(300, 250));
            //mImageView.setBackgroundResource(itemBackground);
            super.onPostExecute(bitmap);
        }

        @Override
        protected Bitmap doInBackground(URL... params) {
            try {
                Log.d("Link","khi thuc thi: "+params[0]);
                bmp = BitmapFactory.decodeStream(params[0].openConnection()
                        .getInputStream());
//                bmp = Bitmap.createScaledBitmap(bmp,600,300,true);
            } catch (IOException e){
                Log.d("Loi",e.getMessage());
            }
            return bmp;
        }
    }
}
